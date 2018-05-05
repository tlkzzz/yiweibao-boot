package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.src.common.base.entity.PageBean;
import com.src.common.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbCompanyDao;
import com.src.api.dao.TbServiceEmployeeDao;
import com.src.api.entity.TbCompany;
import com.src.api.entity.TbServiceEmployee;
import com.src.api.entity.TbServicePoints;
import com.src.api.service.TbServiceEmployeeService;


@Service("tbServiceEmployeeService")
public class TbServiceEmployeeServiceImpl extends
		BaseServiceImpl<TbServiceEmployee, Long> implements
		TbServiceEmployeeService {
	
@Override
	public String getMaxId() {
		// TODO Auto-generated method stub
		String sql = "select CONCAT('E', LPAD((right(ifnull(max(tse_number),'E000000'), 6) + 1), 6, '0')) tse_number from tb_service_employee where tse_number REGEXP 'E[[:digit:]]{6}'";
		return tbServiceEmployeeDao.getDouble(sql);
	}


@Resource TbServiceEmployeeDao tbServiceEmployeeDao;
@Resource TbCompanyDao tbCompanyDao;
	
	@Override
	public BaseDao<TbServiceEmployee, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbServiceEmployeeDao;
	}
	
	
	@Override
	public TbServiceEmployee searchOne(TbServiceEmployee t)
	{
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select t.*,s.tsp_name from tb_service_employee t,");
		sbSql.append("tb_service_points s where t.tse_unitid = s.tsp_id and tse_status = 1 ");
		sbSql.append(" and tse_login_user = '" + t.getTseLoginUser() + "'");
		List<TbServiceEmployee> list = tbServiceEmployeeDao.search(sbSql.toString(), null);
		if(!list.equals(null)&&list.size()>0)
		{
			return list.get(0);
		}
		return null;
	
	}
	
	@Override
	public Boolean canDelete(TbServicePoints t)
	{
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from tb_service_employee t where tse_unitid = " + t.getTspId() + " and tse_status = 1");
		List<TbServiceEmployee> list = tbServiceEmployeeDao.search(sbSql.toString(), null);
		if(list.equals(null)||list.size()==0)
		{
			return true;
		}
		else
			return false;
	}
	
	@Override
	public List<TbServiceEmployee> selectItem(TbServiceEmployee t)
	{
		return tbServiceEmployeeDao.search(t);
	}
	
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		
		StringBuilder sql = new StringBuilder("select t.*,s.tsp_name,(select tw_name from tb_words where tw_id = t.tse_department) tw_name, ");
		sql.append("(select tw_name from tb_words where tw_id = t.tse_position ) dept ");
		sql.append(" from tb_service_employee t,tb_service_points s where t.tse_unitid = s.tsp_id and tse_status != 0 ");
		List<Object> values = new ArrayList<Object>();
		
		if(params.get("tseProvId") != null && !params.get("tseProvId").equalsIgnoreCase(""))
		{
			sql.append(" and tsp_prov_id = ").append(params.get("tseProvId"));
		}
		if(params.get("tseCityId") != null && !params.get("tseCityId").equalsIgnoreCase(""))
		{
			sql.append(" and tsp_city_id = ").append(params.get("tseCityId"));
		}
		if(params.get("tseUnitid") != null && !params.get("tseUnitid").equalsIgnoreCase(""))
		{
			sql.append(" and tse_unitid = ").append(params.get("tseUnitid"));
		}else if(params.get("company")!=null&&!params.get("company").equalsIgnoreCase(""))
		{
			TbCompany tbCompany = new TbCompany();
			tbCompany.setTcLoginUser(params.get("company"));
			tbCompany.setTcStatus(1);
			tbCompany = tbCompanyDao.searchOne(tbCompany);
			if(tbCompany!=null){
				sql.append(" and tse_unitid in (SELECT tsp_id FROM tb_service_points WHERE tsp_status =1 AND tsp_company_id = "+tbCompany.getTcId()+")");
			}
		}
		if(params.get("tse_name") != null && !params.get("tse_name").equalsIgnoreCase(""))
		{
			sql.append(" and tse_name like '%").append(params.get("tse_name") + "%' ");
		}
		if(params.get("tseDepartment") != null && !params.get("tseDepartment").equalsIgnoreCase(""))
		{
			sql.append(" and tse_department = ").append(params.get("tseDepartment"));
		}
		if (!StringUtils.isBlank(params.get("startDate"))) {
			sql.append(" and tse_add_date > DATE_FORMAT(?, '%Y-%c-%d %H:%i:%s')");
			values.add(params.get("startDate"));
		}
		if (!StringUtils.isBlank(params.get("endDate"))) {
			sql.append(" and tse_add_date < DATE_FORMAT(?, '%Y-%c-%d %H:%i:%s')");
			values.add(params.get("endDate"));
		}
		
		
		
		if (pageSize == 0) {
			List<TbServiceEmployee> list = tbServiceEmployeeDao.search(sql.toString(), values);
			json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<TbServiceEmployee> pageBean = new PageBean<TbServiceEmployee>(page, pageSize);
			if(params.get("orderBy") != null)
				pageBean.setOrderBy("tse_add_date");
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbServiceEmployeeDao.search(sql.toString(), values, pageBean);
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
	
	/*根据单位id获取单位员工*/
	public TbServiceEmployee searchById(long id){
//		String sql = "select * from tb_service_employee t where tse_unitid = " + id + " and tse_status = 1";
		String sql = "select * from tb_service_employee t where tse_id = " + id + " and tse_status != 0";
		System.out.println("sql-->"+ sql);
		List<TbServiceEmployee> list =  tbServiceEmployeeDao.search(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return new TbServiceEmployee();
		}
	}
	
	
	@Override
	public List<TbServiceEmployee> getWorkList(String ServicePointId,int type){
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from tb_service_employee  where tse_status = 1 ");
		if(ServicePointId== null || ServicePointId.equals(""))
			;
		else{
			sbSql.append(" and tse_unitid = " + ServicePointId );
		}
		if(type == 0 ){  //派工
			sbSql.append(" and tse_work_type in (0,2) " );
			
		}
		else if(type == 1){ //派单
			
			sbSql.append(" and tse_work_type in (1,2) " );
		}
		return  tbServiceEmployeeDao.search(sbSql.toString(), null);

	}
	
	@Override
	public List<TbServiceEmployee> queryEmployeeByFitingOrder(String orderid){		
			StringBuffer sbSql = new StringBuffer();
			sbSql.append(" select * from tb_service_employee   ");
			sbSql.append(" where  tse_id in (select tsod_worker_id from tb_fitting_dispatch where tfod_order_id = " +orderid +"  )   ");
			return  tbServiceEmployeeDao.search(sbSql.toString(), null);
	}


	@Override
	public TbServiceEmployee findOne(TbServiceEmployee searchPars1) {
		
		return tbServiceEmployeeDao.searchOne(searchPars1);
	}


	@Override
	public Map<String, Object> getByCompanyId(Long tcId) {
		StringBuffer sbSql = new StringBuffer("SELECT tse_id,tse_name,tse_number,"
				+ "tse_mobile,tse_address ,tse_status,"
				+ "DATE_FORMAT(tse_add_date,'%Y-%m-%d %H:%i:%s') tseAddDate,");
		sbSql.append("(SELECT tsp_name FROM tb_service_points where tsp_id = tse_unitid) tseUnitid,");
		sbSql.append("(SELECT pname FROM china_province WHERE pid = tse_province_id) pName,");
		sbSql.append("(SELECT cname FROM china_city WHERE cid = tse_city_id) cName, ");
		sbSql.append("(SELECT oname FROM china_county WHERE oid = tse_county_id) oName ");
		sbSql.append("FROM tb_service_employee ");
		sbSql.append("where tse_status <> 0 and tse_unitid in (select tsp_id from tb_service_points where tsp_company_id = "+tcId+") order by tse_add_date desc" );
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(1, 10);
		pageBean = tbServiceEmployeeDao.searchForMap(sbSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());
		map.put("count", pageBean.getRowCount());
		map.put("rows", pageBean.getPageCount());
		return map;
	}
	@Override
	public HashMap<String, Object> findEmployee(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tcId = params.get("tcId");
		String tseName = params.get("tseName");
		String tseMobile = params.get("tseMobile");
		String ifLogin = params.get("ifLogin");
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tse_id tseId, tse_name tseName,tse_mobile tseMobile,");
		sbSql.append("(SELECT count(0) FROM tb_service_order_detail ");
//		sbSql.append("WHERE tsod_worker_id = tse_id AND tsod_status in (2,3,4)) counts ");
		sbSql.append("WHERE tsod_worker_id = tse_id AND tsod_status in (2)) counts ");//待处理工单数
		sbSql.append("FROM tb_service_employee WHERE tse_status= 1 and ");
		sbSql.append("tse_unitid in (SELECT tsp_id FROM tb_service_points ");
		sbSql.append("WHERE tsp_company_id = "+tcId+")");
		

		if(!StringUtils.isBlank(tseName)){
			sbSql.append(" and tse_name like '%"+tseName+"%'");
		}
		if(!StringUtils.isBlank(tseMobile)){
			sbSql.append(" and tse_mobile like '%"+tseMobile+"%'");
		}
		if(!StringUtils.isBlank(ifLogin)){
			sbSql.append(" and tse_if_login = '"+ifLogin+"'");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbServiceEmployeeDao.searchForMap(sbSql.toString(), null);
			json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbServiceEmployeeDao.searchForMap(sbSql.toString(), null, pageBean);
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}


	@Override
	public Map<String, Object> getByparam(Map<String, String> param) {
		Map<String, Object> json = new HashMap<String, Object>();
		String companyId = param.get("companyId");
		String tseNumber  = param.get("tseNumber");
		String tseStatus = param.get("tseStatus");
		String tseUnitid = param.get("tseUnitid");
		int page = param.get("page") == null ? 0:Integer.parseInt(param.get("page"));
		int pageSize = param.get("pageSize") == null ? 0:Integer.parseInt(param.get("pageSize"));
//		Integer nowPage = 1;
		StringBuffer sbSql = new StringBuffer("SELECT tse_id,left(tse_name,8) tse_name,tse_number,"
				+ "tse_mobile,tse_address ,tse_status,tse_photo,"
				+ "DATE_FORMAT(tse_add_date,'%Y-%m-%d %H:%i:%s') tseAddDate,");
		sbSql.append("(SELECT tsp_name FROM tb_service_points where tsp_id = tse_unitid) tseUnitid,");
		sbSql.append("(SELECT pname FROM china_province WHERE pid = tse_province_id) pName,");
		sbSql.append("(SELECT cname FROM china_city WHERE cid = tse_city_id) cName, ");
		sbSql.append("(SELECT oname FROM china_county WHERE oid = tse_county_id) oName ");
		sbSql.append("FROM tb_service_employee ");
		sbSql.append("where tse_status <> 0 and tse_unitid in (select tsp_id from tb_service_points where tsp_company_id = "+companyId+") " );//单位网点下的所有员工
//		sbSql.append("and tse_if_login=1 ");//是否登陆： 0 未登录，1 登陆     员工列表不需获取是否登陆员工
		if(!StringUtils.isBlank(tseNumber)){
			sbSql.append(" and tse_name like '%"+tseNumber+"%'");
		}
//		if(!StringUtils.isBlank(tseStatus)&&Integer.valueOf(tseStatus)!=1){
			sbSql.append(" and tse_status = "+tseStatus);
//		}
		if(!StringUtils.isBlank(tseUnitid)){
			sbSql.append(" and tse_unitid = "+tseUnitid);
		}

		sbSql.append(" order by tse_add_date desc");
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbServiceEmployeeDao.searchForMap(sbSql.toString(), null);
			json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbServiceEmployeeDao.searchForMap(sbSql.toString(), null, pageBean);
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}


	@Override
	public List<TbServiceEmployee> findByNumberAndCompany(String tseNumber, Long tcId) {
		//tse_status in (1,2,3) 状态为正常 、休息、离职、
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select*from tb_service_employee ");
		sbSql.append("where tse_status in (1,2,3) and tse_unitid ");
		sbSql.append("in (select tsp_id from tb_service_points ");
		sbSql.append("where tsp_company_id = "+tcId+") and tse_number = '"+tseNumber+"'");
		//tse_unitid in (select tsp_id from tb_service_points where tsp_company_id = "+tcId+") and
		return tbServiceEmployeeDao.search(sbSql.toString(), null);
	}


	@Override
	public List<TbServiceEmployee> findByMobileAndCompany(String tseMobile, Long tcId) {
		//tse_status in (1,2,3) 状态为正常 、休息、离职、
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select*from tb_service_employee ");
		sbSql.append("where tse_status in (1,2,3) and  tse_mobile = '"+tseMobile+"'");
		//tse_unitid in (select tsp_id from tb_service_points where tsp_company_id = "+tcId+") and
		return tbServiceEmployeeDao.search(sbSql.toString(), null);
	}
}
