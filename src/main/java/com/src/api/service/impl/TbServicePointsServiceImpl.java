package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.src.common.base.entity.PageBean;
import com.src.common.dao.UserinfoDao;
import com.src.common.entity.Userinfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbServicePointsDao;
import com.src.api.entity.TbServicePoints;
import com.src.api.service.TbServicePointsService;

@Service("tbServicePointsService")
public class TbServicePointsServiceImpl extends
	BaseServiceImpl<TbServicePoints, Long> implements
		TbServicePointsService {

	
	@Resource TbServicePointsDao tbServicePointsDao;
	@Resource
	UserinfoDao userinfoDao;
	@Override
	public BaseDao<TbServicePoints, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbServicePointsDao;
	}
	
	@Override
	public TbServicePoints searchOne(TbServicePoints t)
	{
		return tbServicePointsDao.searchOne(t);
	}
	
	@Override
	public TbServicePoints findPointByUserANDPhoneANDNameANDId(
			String loginUser, String Phone, String tspName, String tspId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_service_points ");
		sbSql.append("WHERE tsp_status <> 0 ");
		
		List<Object> values = new ArrayList<Object>();
		if(!StringUtils.isBlank(tspName)){
			sbSql.append("AND tsp_name = ? ");
			values.add(tspName);
		}
		if(!StringUtils.isBlank(tspId)){
			sbSql.append("AND tsp_id != ? ");
			values.add(tspId);
		}
		if(!StringUtils.isBlank(Phone)){
			sbSql.append("AND tsp_charge_phone = ? ");//获取联系电话
			values.add(Phone);
		}
		if(!StringUtils.isBlank(loginUser)){
			sbSql.append("AND tsp_company_id = ? ");//获取登陆帐号
			values.add(loginUser);
		}
		List<TbServicePoints> list = tbServicePointsDao.search(sbSql.toString(), values);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override 
	public List<TbServicePoints> selectList(HashMap<String, String> params)
	{
		String sql = "select t.* from tb_service_points t where tsp_status = 1 ";
		if(params.get("tspProvId") != null && !params.get("tspProvId").equalsIgnoreCase(""))
		{
			sql += " and tsp_prov_id = "+params.get("tspProvId");
		}
		if(params.get("tspCityId") != null && !params.get("tspCityId").equalsIgnoreCase(""))
		{
			sql += " and tsp_city_id = "+params.get("tspCityId");
		}
		if(params.get("tbCompanyId") != null && !params.get("tbCompanyId").equalsIgnoreCase(""))
		{
			sql += " and tsp_company_id = "+params.get("tbCompanyId");
		}
		List<Object> values = new ArrayList<Object>();
		return tbServicePointsDao.search(sql, values);
	}
	

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		
		String sql = "select t.*,c.cname cname,p.pname pname,(select tw_name from tb_words where  tw_id=t.tsp_type) tw_name ";
		sql += " from tb_service_points t left join china_city c on t.tsp_city_id = c.cid left join china_province p on t.tsp_prov_id = p.pid  where tsp_status != 0 and tsp_company_id ="+params.get("tbCompanyId");
		List<Object> values = new ArrayList<Object>();
		
		if(params.get("tspStatus") != null && !params.get("tspStatus").equalsIgnoreCase(""))
		{
			sql += " and tsp_status = "+params.get("tspStatus");
		}
		if(params.get("tspProvId") != null && !params.get("tspProvId").equalsIgnoreCase(""))
		{
			sql += " and tsp_prov_id = "+params.get("tspProvId");
		}
		if(params.get("tspCityId") != null && !params.get("tspCityId").equalsIgnoreCase(""))
		{
			sql += " and tsp_city_id = "+params.get("tspCityId");
		}
//		if(params.get("tspCountyId") != null && !params.get("tspCountyId").equalsIgnoreCase(""))
//		{
//			sql += " and tsp_county_id = "+params.get("tspCountyId");
//		}
		if(params.get("tspName") != null && !params.get("tspName").equalsIgnoreCase(""))
		{
			sql += " and tsp_name  like '%"+params.get("tspName") + "%' ";
		}
		if(params.get("twName") != null && !params.get("twName").equalsIgnoreCase(""))
		{
			sql += " and tsp_type  = "+params.get("twName");
		}
//		if(params.get("tspChargePerson") != null && !params.get("tspChargePerson").equalsIgnoreCase(""))
//		{
//			sql += " and tsp_charge_person like '%"+params.get("tspChargePerson") + "%' ";
//		}
//		if(params.get("tspChargePhone") != null && !params.get("tspChargePhone").equalsIgnoreCase(""))
//		{
//			sql += " and tsp_charge_phone like '%"+params.get("tspChargePhone") + "%' ";
//		}
//		
		if (pageSize == 0) {
			List<TbServicePoints> list = tbServicePointsDao.search(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<TbServicePoints> pageBean = new PageBean<TbServicePoints>(page, pageSize);
			if(params.get("orderBy") != null)
				pageBean.setOrderBy("tsp_add_date");
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbServicePointsDao.search(sql, values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
	@Override
	public TbServicePoints saveAndRet(TbServicePoints t)
	{
		TbServicePoints s = tbServicePointsDao.save(t);
		return s;
	}

	@Override
	public String getMaxId() {
		// TODO Auto-generated method stub
		String sql = "select  CONCAT('S', LPAD((right(ifnull(max(tsp_service_num),'S000000'), 6) + 1), 6, '0')) tsp_service_num from tb_service_points where tsp_service_num REGEXP 'S[[:digit:]]{6}'";
		return tbServicePointsDao.getDouble(sql);
	}

	@Override
	public Map<String, Object> getByCompanyId(Long tcId) {
		StringBuffer sbSql = new StringBuffer("SELECT tsp_id,tsp_name,tsp_charge_person,"
				+ "tsp_charge_phone,tsp_address ,tsp_status,"
				+ "DATE_FORMAT(tsp_add_date,'%Y-%m-%d %H:%i:%s') tspAddDate,");
		sbSql.append("(SELECT tw_name FROM tb_words where tw_id = tsp_type) tspType,");
		sbSql.append("(SELECT pname FROM china_province WHERE pid = tsp_prov_id) pName,");
		sbSql.append("(SELECT cname FROM china_city WHERE cid = tsp_city_id) cName, ");
		sbSql.append("(SELECT oname FROM china_county WHERE oid = tsp_county_id) oName ");
		sbSql.append("FROM tb_service_points ");
		sbSql.append("where tsp_status <> 0 and tsp_company_id = "+tcId+" order by tsp_add_date desc" );
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(1, 10);
		pageBean = tbServicePointsDao.searchForMap(sbSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());
		map.put("count", pageBean.getRowCount());
		map.put("rows", pageBean.getPageCount());
		return map;
	}

	@Override
	public TbServicePoints findOne(TbServicePoints tbServicePointsNum) {
		// TODO Auto-generated method stub
		return tbServicePointsDao.searchOne(tbServicePointsNum);
	}

	@Override
	public List<TbServicePoints> findByCompanyId(Long tcId) {
		String sql = "SELECT *FROM tb_service_points WHERE tsp_status <> 0 and tsp_company_id =" +tcId+" AND tsp_status = 1" ;
		return tbServicePointsDao.search(sql, null);
	}

	@Override
	public Map<String, Object> getByparam(Map<String, String> param) {
		String companyId = param.get("companyId");
		String tspType  = param.get("tspType");
		String tspStatus = param.get("tspStatus");
		String tspName = param.get("tspName");
		int page = param.get("page") == null ? 0:Integer.parseInt(param.get("page"));
		int pageSize = param.get("pageSize") == null ? 0:Integer.parseInt(param.get("pageSize"));
		StringBuffer sbSql = new StringBuffer("SELECT tsp_id,left(tsp_name,8) tsp_name,tsp_charge_person,"
				+ "tsp_charge_phone,left(CONCAT((SELECT pname FROM china_province WHERE pid = tsp_prov_id),(SELECT cname FROM china_city WHERE cid = tsp_city_id),(SELECT oname FROM china_county WHERE oid = tsp_county_id),tsp_address),26) tsp_address,tsp_status,"
				+ "DATE_FORMAT(tsp_add_date,'%Y-%m-%d %H:%i:%s') tspAddDate,");
		sbSql.append("(SELECT tw_name FROM tb_words where tw_id = tsp_type) tspType,");
		sbSql.append("(SELECT pname FROM china_province WHERE pid = tsp_prov_id) pName,");
		sbSql.append("(SELECT cname FROM china_city WHERE cid = tsp_city_id) cName, ");
		sbSql.append("(SELECT oname FROM china_county WHERE oid = tsp_county_id) oName ");
		sbSql.append("FROM tb_service_points ");
		sbSql.append("where tsp_status <> 0 and tsp_company_id = "+companyId+" " );
		
		if(!StringUtils.isBlank(tspName)){
			sbSql.append(" and tsp_name like '%"+tspName+"%'");
		}
		if(!StringUtils.isBlank(tspType)){
			sbSql.append(" and tsp_type = "+tspType);
		}
		if(!StringUtils.isBlank(tspStatus)){
			sbSql.append(" and tsp_status = "+tspStatus);
		}

		sbSql.append(" order by tsp_add_date desc");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, 10);
		pageBean = tbServicePointsDao.searchForMap(sbSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage",page);//当前页
		return map;
	}

	@Override
	public List<Map<String, Object>> findEmpByPointId(String pointsId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_service_employee e WHERE e.tse_unitid = ? AND e.tse_status in(1,2)");
		List<Object> values = new ArrayList<Object>();
		values.add(pointsId);
		return tbServicePointsDao.searchForMap(sbSql.toString(), values);
	}

	@Override
	public void updatePoint(TbServicePoints tbServicePoints, Userinfo pointsUser)
			throws Exception {
		tbServicePointsDao.update(tbServicePoints);
		userinfoDao.update(pointsUser);
		
	}
	
}
