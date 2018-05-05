package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.src.common.base.entity.PageBean;
import com.src.common.utils.StringUtil;
import com.src.common.utils.hjtech.util.LogTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbServiceEmployeeDao;
import com.src.api.dao.TbServiceOrderDao;
import com.src.api.entity.TbItem;
import com.src.api.entity.TbMessage;
import com.src.api.entity.TbServiceOrder;
import com.src.api.entity.TbServiceOrderDetail;
import com.src.api.service.TbMessageServicer;
import com.src.api.service.TbServiceOrderDetailService;
import com.src.api.service.TbServiceOrderService;

@Service("tbServiceOrderService")
public class TbServiceOrderServiceImpl extends BaseServiceImpl<TbServiceOrder, Long>implements TbServiceOrderService {

	@Resource
	TbServiceOrderDao tbServiceOrderDao;

	@Resource
	TbServiceEmployeeDao tbServiceEmployeeDao; // 网点员工dao
	@Resource
	TbServiceOrderDetailService tbServiceOrderDetailService;
	@Resource
	TbMessageServicer tbMessageServicer;
	
	@Override
	public BaseDao<TbServiceOrder, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbServiceOrderDao;
	}

	public Boolean canDelete(TbItem t) {
		String sql = "select * from tb_servcie_order t where tso_item_id = " + t.getTiId() + " AND tso_status <> 0 ";
		List<TbServiceOrder> tiList = tbServiceOrderDao.search(sql, null);
		if (tiList.equals(null) || tiList.size() == 0)
			return true;
		else
			return false;
	}

	@Override
	public TbServiceOrder searchOne(TbServiceOrder t) {
		return tbServiceOrderDao.searchOne(t);
	}

	@Override
	public List<TbServiceOrder> findServiceOrderByDate(String date) {
		String year = "";
		String month = "";
		if(!StringUtil.isEmptyNull(date)){
			year = date.substring(0, 4);
			month = date.substring(4, 6);
		}
		
		String sql = "SELECT o.* FROM tb_servcie_order o WHERE SUBSTRING(o.tso_number,4,4)='"+year+"' AND SUBSTRING(o.tso_number,8,2)='"+month+"' ORDER BY o.tso_add_date DESC";
		
		return tbServiceOrderDao.searchp(sql, null);
	}
	
	@Override
	public HashMap<String, Object> findForJsonEx(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(
				" select tso_id, tso_number,tc_name,(select ti_name from tb_item where ti_id=s2.ti_uplevel_id) uplevel_name,tm_id,tm_name,ti_name,DATE_FORMAT(tso_add_date,'%Y-%m-%d %H:%i:%s') as tso_add_date,tso_status  from tb_servcie_order t,tb_customers s,tb_item s2,tb_machine ");
		sbSql.append(" where tso_customer_id=tc_id and tso_item_id = ti_id and tso_good_id = tm_id  ");
		sbSql.append("  ");
		List<Object> values = new ArrayList<Object>();
		if (params.get("tsoStatus") != null && !params.get("tsoStatus").equalsIgnoreCase("")) {
			sbSql.append(" and tso_Status = " + params.get("tsoStatus"));
		}
		if (params.get("tsoNumber") != null && !params.get("tsoNumber").equalsIgnoreCase("")) {
			sbSql.append(" and tso_number = " + params.get("tsoNumber"));
		}
		/* 根据服务网点 */
		if (params.get("tcServicePoint") != null && !params.get("tcServicePoint").equalsIgnoreCase("")) {
			sbSql.append(" and tc_service_point = " + params.get("tcServicePoint"));
		}

		/* 根据省市 */
		if (params.get("tcProvId") != null && !params.get("tcProvId").equalsIgnoreCase("")) {
			sbSql.append(" and tc_prov_id = " + params.get("tcProvId"));
		}
		if (params.get("tcCityId") != null && !params.get("tcCityId").equalsIgnoreCase("")) {
			sbSql.append(" and tc_city_id = " + params.get("tcCityId"));
		}

		/* 根据客户id */
		if (params.get("tcId") != null && !params.get("tcId").equalsIgnoreCase("")) {
			sbSql.append(" and tc_id = " + params.get("tcId"));
		}
		
		/* 根据客户名称 */
		if (params.get("tcName") != null && !params.get("tcName").equalsIgnoreCase("")) {
			sbSql.append(" and tc_name like '%" + params.get("tcName") + "%' ");
		}

		/* 根据维修项目 */
		if (params.get("tsoItemId") != null && !params.get("tsoItemId").equalsIgnoreCase("")) {
			sbSql.append(" and tso_item_id = " + params.get("tsoItemId"));
		}
		if (params.get("tsoStartDate") != null && !params.get("tsoStartDate").equalsIgnoreCase("")) {
			sbSql.append(" and tso_add_date >= '" + params.get("tsoStartDate") + "'");
		}
		if (params.get("tsoEndDate") != null && !params.get("tsoEndDate").equalsIgnoreCase("")) {
			sbSql.append(" and tso_add_date <= '" + params.get("tsoEndDate") + "'");
		}

		/* 根据维修工进行查询 */
		if (params.get("tsodWorkerId") != null && !params.get("tsodWorkerId").equalsIgnoreCase("")) {
			sbSql.append("  and tsod_worker_id =" + params.get("tsodWorkerId"));
		}

		/* 根据产品id进行查询 */
		if (params.get("tgName") != null && !params.get("tgName").equalsIgnoreCase("")) {
			sbSql.append(" and tm_id =" + params.get("tgName"));
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
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
 
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tsoNumber = params.get("tsoNumber");
		String tmName = params.get("tmName");
		String tsoStatus = params.get("tsoStatus");
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		String tsoType = params.get("tsoType");
		String keywords = params.get("keywords");//报修人或工单号
		String companyId = params.get("companyId");//单位
		LogTool.WriteLog("AddDate:"+AddDate);
		LogTool.WriteLog("AddDateEnd:"+AddDateEnd);
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tso_id tsoId,tso_number tsoNumber,");
//		sbSql.append("(SELECT tc_person FROM tb_customers WHERE ) tmName,");
		sbSql.append("tc_person tmName,tc_mobile tcMobile,");
		sbSql.append("(SELECT tp_name FROM tb_product WHERE tp_id = tso_good_id) tpName,");
		sbSql.append("(SELECT tp_logo FROM tb_product WHERE tp_id = tso_good_id) tpLogo,");
		sbSql.append("DATE_FORMAT(tso_add_date,'%Y-%m-%d %H:%i:%s') tsoAddDate,");
		sbSql.append("(SELECT GROUP_CONCAT(ps.tps_spec_value SEPARATOR ' ') FROM tb_product_spec ps");
		sbSql.append(",tb_order_detail od2 WHERE ps.tps_sku_id=od2.tod_sku_id AND ");
		sbSql.append("od2.tod_id=o.tso_order_detail_id ) as tgModel,");
		sbSql.append("od.tod_spec_json tod_spec_json,");
		sbSql.append("(SELECT pname FROM china_province WHERE pid = tc_prov_id) pname,");
		sbSql.append("(SELECT cname FROM china_city WHERE cid = tc_city_id) cname,");
		sbSql.append("(SELECT oname FROM china_county WHERE oid = tc_region_id) oname,");
		sbSql.append("tc_address tcAddress,IF(od.tod_end_time>now(),0,1) ifOutTime,");
		sbSql.append("tso_status tsoStatus FROM tb_servcie_order o,tb_customers,tb_order_detail od ");
		sbSql.append("where tso_status<>0 AND tc_id = o.tso_customer_id AND od.tod_id = o.tso_order_detail_id ");
		if(!StringUtils.isBlank(tsoType)){
			if(tsoType.equals("2")){
				sbSql.append(" and tso_type = 2");
			}else{
				sbSql.append(" and tso_type = 1");
			}
		}
		if(!StringUtils.isBlank(keywords)){
			sbSql.append(" and (tso_number like '%"+keywords+"%' OR tc_person like '%"+keywords+"%')");
		}
		
		if (!StringUtils.isBlank(tsoNumber)) {
			sbSql.append(" and tso_number like '%"+tsoNumber+"%'");
		}
		if (!StringUtils.isBlank(tsoStatus)) {
			sbSql.append(" and tso_status = "+tsoStatus);
		}
		
		if (!StringUtils.isBlank(tmName)) {
			sbSql.append(" and tso_customer_id in (SELECT tc_id FROM tb_customers  WHERE tc_person like '%"+tmName+"%')");
		}
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tso_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tso_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')");
		}
		if(!StringUtils.isBlank(companyId)){
			sbSql.append(" AND tso_company_id = '"+companyId+"' ");
		}
		if (!StringUtils.isBlank(order)) {
			sbSql.append(" order by tso_add_date " + order);
		} else {
			sbSql.append(" order by tso_add_date desc ");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), null);
			for (Map<String, Object> map : list) {
			    String specJsonValue = "";
				//tod_spec_json中截取value的值空格拼接
				String todSpecJson = map.get("tod_spec_json").toString();
				//todSpecJson不为空的时候进行以下操作
				if(!StringUtils.isBlank(todSpecJson)){
					//示例：    []
					todSpecJson = todSpecJson.replaceAll(" ", "");//去空格
					todSpecJson = todSpecJson.replaceAll("\\[", "");
					todSpecJson = todSpecJson.replaceAll("\\]", "");//{tsName=内存, value=8GB}, {tsName=内存, value=8GB}
					todSpecJson = todSpecJson.replaceAll("\\{", "");
					todSpecJson = todSpecJson.replaceAll("\\}", "");
					todSpecJson = todSpecJson.replaceAll("\"", "");//去掉双引号
					//tsName=内存, value=8GB, tsName=内存, value=8GB
					String[] arrTodSpecJson = todSpecJson.split(",");
					for (int i = 0; i < arrTodSpecJson.length; i++) {
						//如果是数组第奇数个元素则获取值
						if(i%2==1){
							String[] arrSpec = arrTodSpecJson[i].split("=");//value=8GB
							//如果字符串用逗号分割后数组长度没有变为2则用:分割
							if(arrSpec.length!=2){
								arrSpec = arrTodSpecJson[i].split(":");
							}
							
							if(specJsonValue.equals("")){
								specJsonValue = arrSpec[1];//获取第二个元素
							}else{
								specJsonValue = specJsonValue + " " +arrSpec[1];//获取第二个元素用空格隔开
							}
						}
					}
				}
				map.put("specJsonValue", specJsonValue);//返回商品规格值
			}
			json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbServiceOrderDao.searchForMap(sbSql.toString(), null, pageBean);
			for (Map<String, Object> map : pageBean.getList()) {
				String specJsonValue = "";
				//tod_spec_json中截取value的值空格拼接([{"tsName":"硬盘","value":"500G"},{"tsName":"cpu","value":"双核"}])
				String todSpecJson = map.get("tod_spec_json").toString();
				LogTool.WriteLog("todSpecJson:"+todSpecJson);
				//todSpecJson不为空的时候进行以下操作
				if(!StringUtils.isBlank(todSpecJson)){
					//示例：    []
					todSpecJson = todSpecJson.replaceAll(" ", "");//去空格
					todSpecJson = todSpecJson.replaceAll("\\[", "");
					todSpecJson = todSpecJson.replaceAll("\\]", "");//{tsName=内存, value=8GB}, {tsName=内存, value=8GB}
					todSpecJson = todSpecJson.replaceAll("\\{", "");
					todSpecJson = todSpecJson.replaceAll("\\}", "");
					todSpecJson = todSpecJson.replaceAll("\"", "");//去掉双引号
					//tsName=内存, value=8GB, tsName=内存, value=8GB
					String[] arrTodSpecJson = todSpecJson.split(",");
					for (int i = 0; i < arrTodSpecJson.length; i++) {
						//如果是数组第奇数个元素则获取值
						if(i%2==1){
							String[] arrSpec = arrTodSpecJson[i].split("=");//value=8GB
							//如果字符串用逗号分割后数组长度没有变为2则用:分割
							if(arrSpec.length!=2){
								arrSpec = arrTodSpecJson[i].split(":");
							}
							if(specJsonValue.equals("")){
								specJsonValue = arrSpec[1];//获取第二个元素
							}else{
								specJsonValue = specJsonValue + " " +arrSpec[1];//获取第二个元素用空格隔开
							}
						}
					}
				}
				map.put("specJsonValue", specJsonValue);//返回商品规格值
			}
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	/* 统计所有服务网点的工作业绩绩效 暂时不用 */
	@Override
	public HashMap<String, Object> GetWorkerJx(HashMap<String, String> params) {
		HashMap<String, Object> hmResult = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		String sort = params.get("sort");
		String order = params.get("order");

		StringBuffer sbSql = new StringBuffer();

		sbSql.append(
				" SELECT tsp_name,Tse_Name,tc_name,tso_number,DATE_FORMAT(tso_add_date,'%Y-%m-%d %h-%i-%s') as tso_add_date,Tm_name,Ti_name,TIMESTAMPDIFF(MINUTE,Tsod_start_date,Tsod_end_date) AS usertime ,Tsod_result,Tsod_evaluation  ");
		sbSql.append(
				" from   tb_servcie_order ,Tb_service_order_detail,Tb_service_employee,Tb_service_points,tb_customers,tb_machine,tb_item ");
		sbSql.append(
				" where Tsod_worker_id = Tse_id and  Tse_unitid = tsp_id  and tso_id = tsod_head_id  and  tso_customer_id = tc_id  and tso_good_id = tm_id  and tso_item_id = ti_id ");
		sbSql.append(" and tsod_status > 1  ");

		List<Object> values = new ArrayList<Object>();

		/* 根据客户id */
		if (params.get("tcId") != null && !params.get("tcId").equalsIgnoreCase("")) {
			sbSql.append(" and tc_id = " + params.get("tcId"));
		}

		/* 根据服务网点 */
		if (params.get("servicepoint") != null && !params.get("servicepoint").equalsIgnoreCase("")) {
			sbSql.append(" and tsp_id = " + params.get("servicepoint"));
		}

		if (params.get("startDate") != null && !params.get("startDate").equalsIgnoreCase("")) {
			sbSql.append(" and tso_add_date >= '" + params.get("startDate") + "'");
		}
		if (params.get("endDate") != null && !params.get("endDate").equalsIgnoreCase("")) {
			sbSql.append(" and tso_add_date <= '" + params.get("endDate") + "'");
		}

		/* 根据维修工进行查询 */
		if (params.get("workerid") != null && !params.get("workerid").equalsIgnoreCase("")) {
			sbSql.append("  and tsod_worker_id =" + params.get("workerid"));
		}

		if (!StringUtils.isBlank(sort)) {
			sbSql.append(" order by tso_add_date " + order);
		} else {
			sbSql.append(" order by Tso_id desc");
		}

		System.out.println("----->" + sbSql.toString());

		if (pageSize == 0) {
			// List<TbServiceOrder> list =
			// tbServiceOrderDao.search(sbSql.toString(), values);
			List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
			hmResult.put("total", list.size());
			hmResult.put("rows", list);
			return hmResult;
		} else {
			// PageBean<TbServiceOrder> pageBean = new
			// PageBean<TbServiceOrder>(page, pageSize);
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			if (params.get("orderBy") != null)
				pageBean.setOrderBy("tso_add_date");
			if (params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			// pageBean = tbServiceOrderDao.search(sbSql.toString(), values,
			// pageBean);
			pageBean = tbServiceOrderDao.searchForMap(sbSql.toString(), values, pageBean);

			hmResult.put("total", pageBean.getRowCount());
			hmResult.put("rows", pageBean.getList());
			return hmResult;
		}
	}

	/* 统计所有服务网点的y员工的未完成的工单任务 */
	@Override
	public HashMap<String, Object> GetWorkerHistory(HashMap<String, String> params) {
		HashMap<String, Object> hmResult = new HashMap<String, Object>();

		StringBuffer sbSql = new StringBuffer();

		sbSql.append("  select tsp_name,tse_id,tse_name,count(CASE WHEN tsod_status in( 1,2,3) THEN 1 ELSE null END) as tse_count from ( ");
		sbSql.append(" select tsp_name,tse_id,tse_name  from  Tb_service_employee,Tb_service_points  where  tsp_id = Tse_unitid and tse_work_type in(0,2) ");
		/* 根据服务网点 */
	/*	if (params.get("tseUnitid") != null && !params.get("tseUnitid").equalsIgnoreCase("")) {
			sbSql.append(" and tsp_id = " + params.get("tseUnitid"));
		}
		*/sbSql.append(" and tse_status=1 ) t   ");
		sbSql.append("  left join Tb_service_order_detail on Tsod_worker_id = tse_id  group by  Tse_Name order by  tse_count");

		List<Object> values = new ArrayList<Object>();

		System.out.println("----->" + sbSql.toString());
		List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), values);
		hmResult.put("total", list.size());
		hmResult.put("rows", list);
		return hmResult;

	}
	/**
	 * 
	* Title: findTbServiceOrderDetail 
	* Description:  根据工单id查询报修维护明细列表
	* @param tsoId
	* @return 
	* @see com.spring.api.service.TbServiceOrderDetailService#findTbServiceOrderDetail(long)
	 */
	@Override
	public List<Map<String, Object>> findTbServiceOrderDetail(long tsoId,String type) {
		int type0 = Integer.parseInt(type);
		type0++;
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT o.tso_id as tsoId,o.tso_number as tsoNumber,o.tso_audio_file as tsoAudioFile,");
		sbSql.append("o.tso_price tsoPrice,(SELECT SUM(od1.tod_price) FROM tb_order_detail od1 ");
		sbSql.append("WHERE o.tso_id=od1.tod_head_id AND od1.tod_type='"+type+"') todPrice,");
		sbSql.append("o.tso_pics_file tsoPicsFile,o.tso_pay_type tsoPayType,");
		sbSql.append("c.tc_person as tcName,c.tc_address as tcAddress,o.tso_good_name as tgSimpleName,o.tso_type tsoType,");
		sbSql.append("(SELECT p.pname FROM china_province p WHERE c.tc_prov_id=p.pid) as tcProv,");
		sbSql.append("(SELECT y.cname FROM china_city y WHERE y.cid=c.tc_city_id) as tcCity,");
		sbSql.append("(SELECT u.oname FROM china_county u WHERE u.oid=c.tc_region_id) as tcRegion,");
		sbSql.append("(SELECT GROUP_CONCAT(ps.tps_spec_value SEPARATOR ' ') FROM tb_product_spec ps");
		sbSql.append(",tb_order_detail od2 WHERE ps.tps_sku_id=od2.tod_sku_id AND ");
		sbSql.append("od2.tod_id=o.tso_order_detail_id ) as tgModel,");
		sbSql.append("(SELECT br.tb_name FROM tb_brand br WHERE br.tb_id=g.tp_brand_id) as tgBrand,");
		sbSql.append("(SELECT gc.tc_name FROM tb_category gc WHERE gc.tc_id=g.tp_category_id) as tgcName,");
		sbSql.append("i.ti_name as tiName,c.tc_mobile as tcMobile,g.tp_name tpName,");
		sbSql.append("(SELECT m.ti_name FROM tb_item m WHERE i.ti_uplevel_id=m.ti_id) as tiParentName,");
		sbSql.append("o.tso_question as tsoQuestion,o.tso_status as tsoStatus,o.tso_add_date as tsoAddDate,");
		sbSql.append("d.tsod_allocate_date as tsodAllocateDate,d.tsod_visit_date as tsodVisitDate,");
		sbSql.append("d.tsod_maintenance_number as tsodMaintenanceNumber,d.tsod_sure_date as tsodSureDate,");
		sbSql.append("d.tsod_start_date as tsodStartDate,d.tsod_end_date tsodEndDate,d.tsod_ok_date as tsodOkDate,d.Tsod_abort_date tsodAbortDate,");
		sbSql.append("e.tse_name as tseName,e.tse_mobile as tseMobile,e.tse_photo tsePhoto,");
		sbSql.append("(SELECT tw.tw_name FROM tb_words tw WHERE e.tse_position=tw.tw_id) positionName,");
		sbSql.append("(SELECT COUNT(*) FROM tb_servcie_order o1 WHERE o1.tso_status=7 AND d.tsod_head_id = o1.tso_id) employOrderCount,");
		sbSql.append("o.tso_evaluate_manner as tsoEvaluateManner,");
		sbSql.append("o.tso_evaluate_result as tsoEvaluateResult,c.tc_latitude as tcLatitude,c.tc_longtude as tcLongtude,");
		sbSql.append("ep.tep_latitude tepLatitude,ep.tep_longtude tepLongtude,od1.tod_end_time todEndTime,g.tp_warranty tpWarranty,");
		sbSql.append("ev.te_logistics,ev.te_service,ev.te_maintain,ev.te_content,ev.te_tag,eev.tev_empiric_value, ");
		sbSql.append("od1.tod_spec_json tod_spec_json, ");//规格值
		sbSql.append("(UNIX_TIMESTAMP(d.tsod_end_date)-UNIX_TIMESTAMP(d.tsod_start_date))/60 tim,");//维修耗时
		sbSql.append("(UNIX_TIMESTAMP(d.tsod_start_date)-UNIX_TIMESTAMP(d.tsod_sure_date))/60 reachTime,");//到达耗时（维修）
		sbSql.append("(SELECT COUNT(*) FROM tb_service_order_detail d1 WHERE d1.tsod_head_id=o.tso_id) orderDetailCount ");//是否有派工记录
		sbSql.append("FROM tb_servcie_order o ");
		sbSql.append("LEFT JOIN tb_service_order_detail d ON d.tsod_head_id=o.tso_id AND d.tsod_status<>0 AND d.tsod_status<>6 ");//不获取维修失败与拒绝接单
		sbSql.append("LEFT JOIN tb_service_employee e ON d.tsod_worker_id=e.tse_id ");
		sbSql.append("LEFT JOIN tb_customers c ON o.tso_customer_id=c.tc_id ");
		sbSql.append("LEFT JOIN tb_product g ON o.tso_good_id=g.tp_id ");
		sbSql.append("LEFT JOIN tb_item i ON o.tso_item_id=i.ti_id ");
		sbSql.append("LEFT JOIN tb_order_detail od ON (o.tso_id=od.tod_head_id AND od.tod_type='"+type+"') ");
		sbSql.append("LEFT JOIN tb_order_detail od1 ON (o.tso_order_detail_id=od1.tod_id) ");
		sbSql.append("LEFT JOIN tb_employee_position ep ON ep.tep_employee_id=e.tse_id ");
		sbSql.append("LEFT JOIN tb_evaluate ev ON ev.te_order_id=o.tso_id AND ev.te_order_type = 3 ");
		sbSql.append("LEFT JOIN tb_employee_empiric_value eev ON eev.tev_employee_id=d.tsod_worker_id ");
		sbSql.append("WHERE o.tso_id = '"+tsoId+"'  ORDER BY o.tso_id DESC ");
		
		return tbServiceOrderDao.searchForMap(sbSql.toString(), null);
	}
	@Override
	public void updateOrderDispatch(TbServiceOrder tbServiceOrder,
			TbServiceOrderDetail tbServiceOrderDetail, TbMessage message)
			throws Exception {
		tbServiceOrderDao.update(tbServiceOrder);
		tbServiceOrderDetailService.save(tbServiceOrderDetail);
		tbMessageServicer.save(message);
	}
	
	@Override
	public HashMap<String, Object> findOrderEmpForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		String tsoId = params.get("tsoId");
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT DATE_FORMAT(tsod_allocate_date,'%Y-%m-%d %H:%i:%s') tsodAllocateDate,");
		sbSql.append("(SELECT tse_name FROM tb_service_employee WHERE tse_id = tsod_worker_id) tseName,");
		sbSql.append("(SELECT tse_mobile FROM tb_service_employee WHERE tse_id = tsod_worker_id) tseMobile,");
		sbSql.append("DATE_FORMAT(tsod_sure_date,'%Y-%m-%d %H:%i:%s') tsodSureDate,");
		sbSql.append("DATE_FORMAT(tsod_start_date,'%Y-%m-%d %H:%i:%s') tsodStartDate,");
		sbSql.append("DATE_FORMAT(tsod_end_date,'%Y-%m-%d %H:%i:%s') tsodEndDate,");
		sbSql.append("DATE_FORMAT(tsod_ok_date,'%Y-%m-%d %H:%i:%s') tsodOkDate,");
		sbSql.append("(UNIX_TIMESTAMP(tsod_end_date)-UNIX_TIMESTAMP(tsod_start_date))/60 tim,");//维修耗时
		sbSql.append("(UNIX_TIMESTAMP(tsod_start_date)-UNIX_TIMESTAMP(tsod_sure_date))/60 reachTime,");//到达耗时（维修）
		sbSql.append("(SELECT tso_price FROM tb_servcie_order WHERE tso_id = "+tsoId+" AND tsod_status<>0) tsoPrice,");
		sbSql.append("tsod_status tsodStatus,tsod_allocate_person tsodAllocatePerson,");
		sbSql.append("(SELECT u.name FROM userinfo u WHERE tsod_allocate_person = u.id ) uname,");
		sbSql.append("(SELECT te_maintain FROM tb_evaluate WHERE te_order_type = 3 AND te_order_id = "+tsoId+" AND tsod_status>4 AND tsod_status<>6) teMaintain,");
		sbSql.append("(SELECT te_content FROM tb_evaluate WHERE te_order_type = 3 AND te_order_id = "+tsoId+" AND tsod_status>4 AND tsod_status<>6) teContent ");
		sbSql.append("FROM tb_service_order_detail WHERE tsod_head_id =  "+tsoId);//根据工单id
		
			List<Map<String, Object>> list = tbServiceOrderDao.searchForMap(sbSql.toString(), null);
			for (Map<String, Object> map : list) {
				if (map.get("tim") != null)
					map.put("tim", String.format("%.2f", map.get("tim")));
				if (map.get("reachTime") != null)
					map.put("reachTime", String.format("%.2f", map.get("reachTime")));
				if (map.get("tsoPrice") != null)
					map.put("tsoPrice", String.format("%.2f", map.get("tsoPrice")));
			}
			json.put("rows", list);
			return json;
	}

	@Override
	public List<Map<String, Object>> getfitting(String tsoId, String type) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT p.tp_id,p.tp_name,d.tod_price,d.tod_count,pc.tc_id,pc.tc_name,d.tod_id,p.tp_number ");
		sbSql.append("FROM tb_order_detail d ,tb_product p LEFT JOIN tb_category pc ");
		sbSql.append("ON pc.tc_id = p.tp_category_id WHERE d.tod_product_id = p.tp_id ");
		sbSql.append("AND d.tod_head_id = '"+tsoId+"' AND d.tod_type = '2' ");
		return tbServiceOrderDao.searchForMap(sbSql.toString(), null);
	}

	@Override
	public List<Map<String, Object>> getOrderSpec(String tsoId, String tpId,
			String type, String todId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT ts_name,tps_spec_id specId,tps_id specValueId,tps_spec_value value ");
		sbSql.append("FROM tb_product_spec ,tb_spec,tb_order_detail ");
		sbSql.append("WHERE tod_sku_id=tps_sku_id AND tps_product_id=tod_product_id AND tod_type = "+type+" ");
		sbSql.append("AND tod_product_id='"+tpId+"' AND ts_id=tps_spec_id AND tod_head_id = '"+tsoId+"' ");
		sbSql.append("AND tod_id="+todId+" ");
		return tbServiceOrderDao.searchForMap(sbSql.toString(), null);
	}
}
