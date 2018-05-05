package com.src.api.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.src.common.base.entity.PageBean;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbServiceEmployeeDao;
import com.src.api.dao.TbServiceOrderDao;
import com.src.api.dao.TbServiceOrderDetailDao;
import com.src.api.entity.TbServiceEmployee;
import com.src.api.entity.TbServiceOrder;
import com.src.api.entity.TbServiceOrderDetail;
import com.src.api.service.TbServiceOrderDetailService;

@Service("tbServiceOrderDetailService")
public class TbServiceOrderDetailServiceImpl extends BaseServiceImpl<TbServiceOrderDetail, Long>
		implements TbServiceOrderDetailService {

	@Resource TbServiceOrderDetailDao tbServiceOrderDetailDao;
	@Resource TbServiceOrderDao tbServiceOrderDao;
	@Resource TbServiceEmployeeDao tbServiceEmployeeDao;

	@Override
	public BaseDao<TbServiceOrderDetail, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbServiceOrderDetailDao;
	}
	
	@Override
	public TbServiceOrderDetail searchOne(TbServiceOrderDetail t)
	{
		return tbServiceOrderDetailDao.searchOne(t);
	}
	
	@Override
	public TbServiceOrderDetail searchOne(String tsoId)
	{
		String sql = "select t.*,tse_name from tb_service_order_detail t,tb_service_employee where tsod_head_id = " + tsoId + " and tsod_worker_id = tse_id order by tsod_id desc limit 0,1";
		return tbServiceOrderDetailDao.search(sql, null).get(0);
	}
	
	@Override
	public Boolean canDelete(TbServiceEmployee t)
	{
		String sql = "select t.* from tb_service_order_detail t where t.tsod_status = 1 and tsod_worker_id = " + t.getTseId();
		List<TbServiceOrderDetail> tseList = tbServiceOrderDetailDao.search(sql, null);
		if(tseList.equals(null)||tseList.size()==0)
			return true;
		else
			return false;
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		
		String sql = "select t.*,tse_name from tb_service_order_detail t,tb_service_employee where tsod_head_id = "+params.get("tsodHeadid") + " and tsod_worker_id = tse_id ";
		List<Object> values = new ArrayList<Object>();
		
		if (pageSize == 0) {
			List<TbServiceOrderDetail> list = tbServiceOrderDetailDao.search(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<TbServiceOrderDetail> pageBean = new PageBean<TbServiceOrderDetail>(page, pageSize);
			if(params.get("orderBy") != null)
				pageBean.setOrderBy("Tsod_allocate_date");
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbServiceOrderDetailDao.search(sql, values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
	public void tsodOpt(TbServiceOrderDetail params)
	{
		String	sql = "";
		if(params.getTsodStatus()==1)
		{
			TbServiceOrderDetail retBean = tbServiceOrderDetailDao.save(params);
			sql = "update tb_servcie_order set tso_status=2 Where tso_id="+params.getTsodHeadId();
			tbServiceOrderDetailDao.update(sql, null);
			TbServiceOrder tempBean = tbServiceOrderDao.get(params.getTsodHeadId());
			System.out.println(tempBean.getTsoNumber());
//			JPush.msgSend(retBean.getTsodId().toString(), "您有新的派工", "您有新的派工，编号："+tempBean.getTsoNumber(),params.getTsodWorkerId().toString(),"1");
			HashMap custom = new HashMap();
			custom.put("tsodId", retBean.getTsodId());
			custom.put("type", "1");
			TbServiceEmployee employee = tbServiceEmployeeDao.get(params.getTsodWorkerId());
//			if (employee!=null) {
//				JSONObject ret = XGPushUtil.pushSingleAccount(employee.getTseLoginUser(), "您有新的派工", "您有一条新的维修工单，请尽快确认。编号："+tempBean.getTsoNumber(), custom);
//				System.out.println("推送结果："+ret);
//
//				try {
//					Client client = new Client(new URL("http://smsapi.hjtechcn.cn:6080/smsWs/sms.ws?wsdl"));
//					Object[] o = client.invoke("sendSMS", new Object[]{"tuzi","e10adc3949ba59abbe56e057f20f883e",employee.getTseMobile(),"您有一条新的维修工单，请尽快确认。编号："+tempBean.getTsoNumber()+"【易维保】","10659800","shcmcc"});
//					System.out.println("短信发送："+o[0].toString());
//				} catch (MalformedURLException e) {
//					e.printStackTrace();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
		}
		else
		{
			tbServiceOrderDetailDao.update(params);
			if(params.getTsodResult())
				sql = "update tb_servcie_order set tso_status=6 Where tso_id="+params.getTsodHeadId();
			else
				sql = "update tb_servcie_order set tso_status=1 Where tso_id="+params.getTsodHeadId();
			tbServiceOrderDetailDao.update(sql, null);
		}
		
	}
}
