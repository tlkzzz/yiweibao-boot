package com.src.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.src.common.base.entity.PageBean;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbOrderInsuranceDao;
import com.src.api.entity.TbOrderInsurance;
import com.src.api.service.TbOrderInsuranceService;

@Service("tbOrderInsuranceService")
public class TbOrderInsuranceServiceImpl extends BaseServiceImpl<TbOrderInsurance, Long>
		implements TbOrderInsuranceService {
	
	@Resource TbOrderInsuranceDao tbOrderInsuranceDao;

	@Override
	public BaseDao<TbOrderInsurance, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbOrderInsuranceDao;
	}
	
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		
		String sql = "select * from tb_order_insurance where toi_headid = " + params.get("toiHeadid");
		List<Object> values = new ArrayList<Object>();
		
		if (pageSize == 0) {
			List<TbOrderInsurance> list = tbOrderInsuranceDao.search(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<TbOrderInsurance> pageBean = new PageBean<TbOrderInsurance>(page, pageSize);
			if(params.get("orderBy") != null)
				pageBean.setOrderBy("toi_oper_date");
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbOrderInsuranceDao.search(sql, values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
	public void orderInsureOpt(TbOrderInsurance params)
	{
		
		tbOrderInsuranceDao.save(params);
		String	sql = "update tb_order_head set toh_end_date=? Where toh_id=?";
		List<Object> values = new ArrayList<Object>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		values.add(fmt.format(params.getToiEndDate()));
		values.add(params.getToiOrderId());
		tbOrderInsuranceDao.update(sql, values);
		
	}

}
