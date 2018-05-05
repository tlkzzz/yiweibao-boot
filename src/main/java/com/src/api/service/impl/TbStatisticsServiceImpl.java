package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.src.common.base.entity.PageBean;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.api.dao.TbServiceOrderDao;
import com.src.api.service.TbStatisticsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Service("TbStatisticsService")
public class TbStatisticsServiceImpl implements TbStatisticsService{
	
	
	@Resource
	TbServiceOrderDao tbServiceOrderDao;

	@Override
	public int getAllOrders(String companyid) {
		// TODO Auto-generated method stub

		List<Object> values = new ArrayList<Object>();
		
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append(" select * from tb_servcie_order ");
		sbSql.append("  where tso_status > 0 ");
		sbSql.append("  and  tso_company_id =" +companyid);
	
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		
		if(list == null )
			return 0;
		else 
			return list.size();

	}

	@Override
	public int getTodayOrder(String companyid) {
		// TODO Auto-generated method stub
		
		List<Object> values = new ArrayList<Object>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select * from tb_servcie_order ");
		sbSql.append("  where tso_status > 0 ");
		sbSql.append("  and  tso_company_id =" +companyid);
		sbSql.append(" and DATE_FORMAT(tso_add_date,'%Y%m%d')  = DATE_FORMAT(now(),'%Y%m%d') "); //今天的所有订单
		
	
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		if(list == null )
			return 0;
		else 
			return list.size();
		
	}

	@Override
	public int getTodayWaitAssign(String companyid) {
		// TODO Auto-generated method stub
		
		List<Object> values = new ArrayList<Object>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select * from tb_servcie_order ");
		sbSql.append("  where tso_status =1  ");   //待派工
		sbSql.append("  and  tso_company_id =" +companyid);
	//	sbSql.append(" and DATE_FORMAT(tso_add_date,'%Y%m%d')  = DATE_FORMAT(now(),'%Y%m%d') "); //今天的所有订单
	
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		if(list == null )
			return 0;
		else 
			return list.size();
	
	}

	@Override
	public int getTodayComplete(String companyid) {
		// TODO Auto-generated method stub
		List<Object> values = new ArrayList<Object>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select * from tb_servcie_order ");
		sbSql.append("  where tso_status = 7 ");   // 已完成
		sbSql.append("  and  tso_company_id =" +companyid);
	//	sbSql.append(" and DATE_FORMAT(tso_add_date,'%Y%m%d')  = DATE_FORMAT(now(),'%Y%m%d') "); //今天的所有订单
		
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		
		if(list == null )
			return 0;
		else 
			return list.size();

	}

	
	
	@Override
	public int getTodayNoComplete(String companyid) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select * from tb_servcie_order ");
		sbSql.append("  where tso_status in(1,2,3,4,5,6) ");   // 没有到最后一步的都算未完成
		sbSql.append("  and  tso_company_id =" +companyid);
	//	sbSql.append(" and DATE_FORMAT(tso_add_date,'%Y%m%d')  = DATE_FORMAT(now(),'%Y%m%d') "); //今天的所有订单
		
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		if(list == null )
			return 0;
		else 
			return list.size();
	}
	
	
	@Override
	public HashMap<String, Object> list_order_baoxiu_detail(HashMap<String, String> params) {
		
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));

		String strOrderNum = params.get("order_num") == null ? "0":params.get("order_num");
		String strOrderPerson =params.get("order_person") == null ? "0":params.get("order_person");
		
		String strCompanyId = params.get("company_id") == null ? "0":params.get("company_id");
		
		String strStartDate = params.get("AddDate") == null ? "0":params.get("AddDate");
		String strEndDate  = params.get("AddDateEnd") == null ? "0":params.get("AddDateEnd");
	
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append(" select tso_number,tp_name tso_good_name,tc_name,tc_person,");
		sbSql.append(" (select pname from china_province where pid =tc_prov_id ) as tc_prov, ");
		sbSql.append(" (select cname from china_city where cid =tc_city_id ) as tc_city, ");
		sbSql.append(" tc_address,DATE_FORMAT(tso_add_date,'%Y-%m-%d %H:%i:%s') as tso_add_date,tso_status  " +
				"from tb_servcie_order ,tb_customers ,tb_product ");
		sbSql.append(" where  tso_customer_id = tc_id  and tso_good_id = tp_id ");
		sbSql.append(" and tso_company_id = '" + strCompanyId +"'");
		
		
		List<Object> values = new ArrayList<Object>();
		if (params.get("tsoStatus") != null && !params.get("tsoStatus").equalsIgnoreCase("")) {
			sbSql.append(" and tso_Status  like  '%" + params.get("tsoStatus")+"%'");
		}
		if ( strOrderNum != null && !strOrderNum.equalsIgnoreCase("")) {
			sbSql.append(" and tso_number like '%" + strOrderNum + "%'");
		}
		
		/* 根据客户名称 */
		if (strOrderPerson != null && !strOrderPerson.equalsIgnoreCase("")) {
			sbSql.append(" and tc_person like '%" + strOrderPerson + "%' ");
		}
		
		
		if (strStartDate != null && !strStartDate.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tso_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+strStartDate+"','%Y-%m-%d')");
		} 
		
		if (strEndDate!= null && !strEndDate.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tso_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+strEndDate+"','%Y-%m-%d')");
		}
		

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			if (params.get("orderBy") != null)
				pageBean.setOrderBy("tso_add_date");
			if (params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbServiceOrderDao.searchForMap(sbSql.toString(), values, pageBean);
		//	json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			json.put("rowcount", pageBean.getRowCount());//符合条件的记录数
			json.put("pageCount", pageBean.getPageCount());//总页数
			
			return json;
		}
		
	}
	
	
	
	/*
	 * Name:	getDayOrderCount
	 * Param	day   日期
	 * function： 获取某一天的报修工单数 （未来需要考虑各种状态的显示较好
	 * Data:    2016-07-18
	 */
	
	@Override
	public int getDayOrderCount(String day,String companyid) {
		// TODO Auto-generated method stub

		List<Object> values = new ArrayList<Object>();
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select * from tb_servcie_order ");
		sbSql.append("  where tso_status > 0 ");
		sbSql.append("  and  tso_company_id =" +companyid);
		sbSql.append(" and DATE_FORMAT(tso_add_date,'%Y%m%d')  ='" +day +"'" ); //今天的所有订单
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		
		if(list == null )
			return 0;
		else 
			return list.size();

	}
	
	
	
	/*
	 * Name:	getRepairProductTop5
	 * Param	
	 * function： 获取报修产品排行榜
	 * Data:    2016-07-18
	 */
	
	@Override
	public HashMap<String, Object> getRepairProductTop5(String companyid) {
		// TODO Auto-generated method stub

		HashMap<String, Object> hmResult = new HashMap<String, Object>();
		List<Object> values = new ArrayList<Object>();
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select tp_name,tp_logo,count(*) as count   from tb_servcie_order,tb_product ");
		sbSql.append("  where tp_id = tso_good_id ");
		sbSql.append("  and  tso_company_id =" +companyid);
		sbSql.append(" group by tso_good_id ");
		sbSql.append("order by count desc limit 5 ");
		
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		hmResult.put("total", list.size());
		hmResult.put("rows", list);
		return hmResult;

	}
	
	public HashMap<String, Object> statistics_employee_order(HashMap<String, String> params){
		
		HashMap<String, Object> hmResult = new HashMap<String, Object>();
		
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		
		String strCompanyId = params.get("companyid") == null ? "0":params.get("companyid");
		String strPxType =  params.get("pxType") == null ? "0":params.get("pxType");
		String strType =  params.get("type") == null ? "0":params.get("type");
		String strTjMonth =  params.get("tjMonth") == null ? "0":params.get("tjMonth");
		String strPointId = params.get("pointId") == null ? "0":params.get("pointId");
		
		StringBuffer sbSql = new StringBuffer();
		
		if(strType.equals("1")){ //根据接单数

			sbSql.append(" select tsod_worker_id,tse_name,tsp_name,tse_mobile,tse_photo,count(*) as 'tjresult' ");
			sbSql.append(" from tb_service_order_detail,tb_service_employee,tb_service_points ");
			sbSql.append(" where  tsod_worker_id = tse_id  and tse_unitid = tsp_id ");
			sbSql.append(" and  tsod_status = 5 "); //验收完成
			sbSql.append(" and DATE_FORMAT(tsod_allocate_date,'%Y%m')  ='" +strTjMonth +"'" ); //统计月份
			sbSql.append(" and tsp_company_id = " + strCompanyId);  //根据企业id
			
			//增加根据网点的判断条件
			if(!(strPointId == null || strPointId.equals("") || strPointId.equals("0")))
				sbSql.append(" and  tsp_id ="+strPointId); //验收完成

			sbSql.append(" group by tsod_worker_id  ");
			
			
			if(strPxType.equals("1"))
				sbSql.append(" order by tjresult desc   ");
			else
				sbSql.append(" order by tjresult asc  ");
			
			
			List<Object> values = new ArrayList<Object>();
			if (pageSize == 0) {
				List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
				hmResult.put("total", list.size());
				hmResult.put("rows", list);
				return hmResult;
			} else {
				PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
				if (params.get("orderBy") != null)
					pageBean.setOrderBy("tso_add_date");
				if (params.get("orderType") != null)
					pageBean.setOrderType(params.get("orderType"));
				pageBean = tbServiceOrderDao.searchForMap(sbSql.toString(), values, pageBean);
			//	json.put("total", pageBean.getRowCount());
				hmResult.put("rows", pageBean.getList());
				hmResult.put("rowcount", pageBean.getRowCount());//符合条件的记录数
				hmResult.put("pageCount", pageBean.getPageCount());//总页数
				return hmResult;
			}
			
			
			
			
			
		}else{ //根据评价分来排序
			
			
			return hmResult;
			
		}
	}
	
	
	//根据员工id获取相关条件下的评价平均分
	public String statistics_employee_evaluate(HashMap<String, String> params){
		
	
		String strEmployeeId = params.get("employeeid") == null ? "0":params.get("employeeid");
		String strTjMonth =  params.get("tjMonth") == null ? "0":params.get("tjMonth");
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select tsod_worker_id,AVG(te_maintain) as 'result' from tb_service_order_detail,tb_evaluate ");
		sbSql.append(" where tsod_head_id= te_order_id  ");
		sbSql.append(" and DATE_FORMAT(tsod_allocate_date,'%Y%m')  ='" +strTjMonth +"'" ); //统计月份
		sbSql.append(" and te_order_type = 3  ");  //维修工类型
		sbSql.append(" and tsod_worker_id =" + strEmployeeId);
		sbSql.append(" group by tsod_worker_id  ");
		
		List<Object> values = new ArrayList<Object>();
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		
		if(list == null || list.size() <=0)
			return "0.00";
		else{
			Map mapResult = (Map)list.get(0);
			return mapResult.get("result")+"";
		}
	}
	

	//获取完成工单量的前10名
	public HashMap<String, Object> getRepairCompleteTop(HashMap<String, String> params){
	
		HashMap<String, Object> hmResult = new HashMap<String, Object>();
		
		String strCompanyId = params.get("companyid") == null ? "0":params.get("companyid");
		String strTjMonth =  params.get("tjMonth") == null ? "0":params.get("tjMonth");
		String strCount = params.get("count") == null ? "0":params.get("count");
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select tsod_worker_id,tse_name,tsp_name,tse_mobile,tse_photo,count(*) as 'tjresult' ");
		sbSql.append(" from tb_service_order_detail,tb_service_employee,tb_service_points ");
		sbSql.append(" where  tsod_worker_id = tse_id  and tse_unitid = tsp_id ");
		sbSql.append(" and  tsod_status = 5 "); //验收完成
		sbSql.append(" and DATE_FORMAT(tsod_allocate_date,'%Y%m')  ='" +strTjMonth +"'" ); //统计月份
		sbSql.append(" and tsp_company_id = " + strCompanyId);  //根据企业id
		
		sbSql.append(" group by tsod_worker_id  ");
		sbSql.append(" order by tjresult desc   ");
		sbSql.append(" limit " + strCount);
		
		List<Object> values = new ArrayList<Object>();
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		hmResult.put("total", list.size());
		hmResult.put("rows", list);
		return hmResult;
	}
	
	
	//获取评价的前10名
		public HashMap<String, Object> getRepairEvaluateTop(HashMap<String, String> params){
			HashMap<String, Object> hmResult = new HashMap<String, Object>();	
			String strCompanyId = params.get("companyid") == null ? "0":params.get("companyid");
			String strTjMonth =  params.get("tjMonth") == null ? "0":params.get("tjMonth");
			String strCount = params.get("count") == null ? "0":params.get("count");
			
			StringBuffer sbSql = new StringBuffer();
			sbSql.append(" select tsod_worker_id,tse_name,tse_photo,AVG(te_maintain) as 'result'  ");
			sbSql.append(" from tb_service_order_detail,tb_evaluate,tb_service_employee,tb_service_points p ");
			
			sbSql.append(" where tsod_head_id= te_order_id and tse_id = tsod_worker_id ");
			sbSql.append("  and te_order_type = 3  ");
			if(!strCompanyId.equals(""))
				sbSql.append(" AND tse_unitid = p.tsp_id AND p.tsp_company_id="+strCompanyId+" ");
			if(!strTjMonth.equals(""))
				sbSql.append(" and DATE_FORMAT(tsod_allocate_date,'%Y%m')  ='"+ strTjMonth +"' ");
			
			sbSql.append(" group by tsod_worker_id ");
			sbSql.append(" order by result DESC ");
			if(!strCount.equals(""))
				sbSql.append(" limit " + strCount );
			
			List<Object> values = new ArrayList<Object>();
			List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
			hmResult.put("total", list.size());
			hmResult.put("rows", list);
			return hmResult;
			
		}

		
		//获取拒绝工单排行的前10名
		public HashMap<String, Object> getRepairRejectTop(HashMap<String, String> params){
			
			HashMap<String, Object> hmResult = new HashMap<String, Object>();
			String strCompanyId = params.get("companyid") == null ? "0":params.get("companyid");
			String strTjMonth =  params.get("tjMonth") == null ? "0":params.get("tjMonth");
			String strCount = params.get("count") == null ? "0":params.get("count");
			
			StringBuffer sbSql = new StringBuffer();
			sbSql.append(" select tsod_worker_id,tse_name,tsp_name,tse_mobile,tse_photo,count(*) as 'tjresult' ");
			sbSql.append(" from tb_service_order_detail,tb_service_employee,tb_service_points ");
			sbSql.append(" where  tsod_worker_id = tse_id  and tse_unitid = tsp_id ");
			sbSql.append(" and  tsod_status = 0 "); //拒绝工单
			sbSql.append(" and DATE_FORMAT(tsod_allocate_date,'%Y%m')  ='" +strTjMonth +"'" ); //统计月份
			sbSql.append(" and tsp_company_id = " + strCompanyId);  //根据企业id
			
			sbSql.append(" group by tsod_worker_id  ");
			sbSql.append(" order by tjresult desc   ");
			sbSql.append(" limit " + strCount);
			
			List<Object> values = new ArrayList<Object>();
			List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
			hmResult.put("total", list.size());
			hmResult.put("rows", list);
			return hmResult;
		}

	


}
