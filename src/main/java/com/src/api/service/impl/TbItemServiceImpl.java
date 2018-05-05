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
import com.src.api.dao.TbItemDao;
import com.src.api.entity.TbItem;
import com.src.api.service.TbItemService;

@Service("tbItemService")
public class TbItemServiceImpl extends BaseServiceImpl<TbItem, Long> implements
TbItemService {

	@Resource TbItemDao tbItemDao;

	@Override
	public BaseDao<TbItem, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbItemDao;
	}
	
	@Override
	public TbItem searchOne(TbItem t)
	{
		return tbItemDao.searchOne(t);
	}
	
	@Override
	public List<TbItem> selectList()
	{
		String sql = "select * from tb_item where ti_status = 1 and (ti_uplevel_id = -1 or ti_uplevel_id = 0)";
		//String sql = "select Ti_id as tiId,Ti_name as tiName from tb_item where ti_status = 1 and (ti_uplevel_id != -1 or ti_uplevel_id != 0) ";
		return tbItemDao.search(sql,null);
	}
	
	@Override
	public List<TbItem> findUpLevel(String tiId)
	{
		String sql = "select * from tb_item where ti_status = 1 and ti_uplevel_id =  " + tiId;
		return tbItemDao.search(sql,null);
	}

	@Override
	public List<TbItem> findUpLevel(HashMap<String, String> params)
	{
		String tiId = params.get("tiId") == null ? "":params.get("tiId");
		String tspId = params.get("tspId") == null ? "":params.get("tspId");
		String AddDate = params.get("AddDate") == null ? "":params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd") == null ? "":params.get("AddDateEnd");
		String sql = "select *,(SELECT COUNT(0) FROM tb_servcie_order ";
		if (!StringUtils.isBlank(tspId))
			sql += ",tb_customers ";
		sql += "WHERE tso_status IN(1,2,3,4,5,6,7) AND tso_item_id = ti_id ";
		if (!StringUtils.isBlank(tspId))
			sql += "AND tc_id = tso_customer_id AND tc_service_point = "+tspId+" ";
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sql += " AND DATE_FORMAT(tso_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')";
		}

		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sql += " AND DATE_FORMAT(tso_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')";
		}
		sql += ") itemOrderCount from tb_item where ti_status = 1 and ti_uplevel_id =  " + tiId;
		return tbItemDao.search(sql,null);
	}


	@Override
	public List<TbItem> findItemByCategoryId(String categoryId,String tiCompanyId) {
	//	String sql = "SELECT * FROM tb_item WHERE ti_category_id = "+categoryId+" AND ti_uplevel_id = 0 AND ti_status = 1";

		StringBuffer sbSql = new StringBuffer();

		sbSql.append(" SELECT * FROM tb_item where ti_uplevel_id = 0 AND ti_status = 1  AND ti_company_id="+tiCompanyId);
		if(!categoryId.equals(""))
			sbSql.append(" AND ti_category_id=" +categoryId );

		return tbItemDao.search(sbSql.toString(), null);
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String orderBy = params.get("orderBy");
		String order = params.get("orderType");
		List<Object> values = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select t.ti_id tiId,t.ti_company_id tiCompanyId,");
		sql.append("t.ti_uplevel_id tiUplevelId,t.ti_name tiName,");
		sql.append("t.ti_desp tiDesp,");
		sql.append("DATE_FORMAT(t.ti_adddate,'%Y-%m-%d') tiAdddate,");
		sql.append("t.ti_addperson tiAddperson,s.ti_status as tiStatus,");
		sql.append("t.ti_category_id tiCategoryId,s.ti_name as Ti_upLevel_name,s.ti_name tiUplevelName,");
		sql.append("(SELECT COUNT(1) FROM tb_item i WHERE i.ti_uplevel_id = t.ti_id AND i.ti_status=1) smallCount,");//查询大类下是否有小类
		sql.append("(SELECT c.tc_name FROM tb_category c WHERE c.tc_id = t.ti_category_id) tcName ");
		sql.append("from tb_item t left join tb_item s on t.ti_uplevel_id = s.ti_id where t.ti_status = 1");
		if(params.get("tiName") != null&&!params.get("tiName").equalsIgnoreCase(""))
			sql.append(" and (s.ti_name like '%"+params.get("tiName")+"%' or t.ti_name like '%"+params.get("tiName") + "%')");
		if(!StringUtils.isBlank(params.get("tcId"))){
			sql.append(" and t.ti_category_id = ? ");
			values.add(params.get("tcId"));
		}
		System.out.println("companyId:"+params.get("companyId"));
		if(!StringUtils.isBlank(params.get("companyId"))){
			sql.append(" and t.ti_company_id = ? ");
			values.add(params.get("companyId"));
		}
		if (!StringUtils.isBlank(params.get("itemStartDate"))) {
//			sql.append(" and DATE_FORMAT(t.ti_adddate, '%Y-%c-%d') >= DATE_FORMAT(?, '%Y-%c-%d %H:%i:%s')");
			sql.append(" and DATE_FORMAT(t.ti_adddate, '%Y-%c-%d') >= DATE_FORMAT(?, '%Y-%c-%d')");
			values.add(params.get("itemStartDate"));
		}
		if (!StringUtils.isBlank(params.get("itemEndDate"))) {
//			sql.append(" and DATE_FORMAT(t.ti_adddate, '%Y-%c-%d') <= DATE_FORMAT(?, '%Y-%c-%d %H:%i:%s')");
			sql.append(" and DATE_FORMAT(t.ti_adddate, '%Y-%c-%d') <= DATE_FORMAT(?, '%Y-%c-%d')");
			values.add(params.get("itemEndDate"));
		}
		if (!StringUtils.isBlank(order)) {
			sql.append(" order by t.ti_adddate " + order);
		} else {
			sql.append(" order by t.ti_adddate desc ");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbItemDao.searchForMap(sql.toString(), values);
			json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
//			if(params.get("orderBy") != null)
//				pageBean.setOrderBy("ti_adddate");
//			if(params.get("orderType") != null)
//				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbItemDao.searchForMap(sql.toString(), values, pageBean);
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<TbItem> selectSubList() {
		// TODO Auto-generated method stub
		String sql = "select * from tb_item where ti_status = 1 and (ti_uplevel_id != -1 or ti_uplevel_id != 0)";
		//String sql = "select Ti_id as tiId,Ti_name as tiName from tb_item where ti_status = 1 and (ti_uplevel_id != -1 or ti_uplevel_id != 0) ";
		return tbItemDao.search(sql,null);
	}

	@Override
	public List<TbItem> selectListByCategory(String categoryId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from tb_item where ti_status = 1 and ");
		sbSql.append("(ti_uplevel_id = -1 or ti_uplevel_id = 0) ");
		sbSql.append("AND ti_category_id = ? ");
		List<Object> values = new ArrayList<Object>();
		values.add(categoryId);
		return tbItemDao.search(sbSql.toString(),values);
	}

	@Override
	public List<TbItem> findItemByCategoryId(String categoryId) {
		//	String sql = "SELECT * FROM tb_item WHERE ti_category_id = "+categoryId+" AND ti_uplevel_id = 0 AND ti_status = 1";

		StringBuffer sbSql = new StringBuffer();

		sbSql.append(" SELECT * FROM tb_item where ti_uplevel_id = 0 AND ti_status = 1");
		if(!categoryId.equals(""))
			sbSql.append(" AND ti_category_id=" +categoryId );

		return tbItemDao.search(sbSql.toString(), null);
	}

	@Override
	public List<TbItem> findXiaoItemByCategoryId(String itemId) {
		String sql = "SELECT * FROM tb_item WHERE ti_status = 1 ";
		if(!StringUtils.isBlank(itemId)){
			sql += "AND ti_uplevel_id = "+itemId+" ";
		}else{
			sql += "AND ti_uplevel_id <> 0 ";
		}
		return tbItemDao.search(sql, null);
	}

	@Override
	public List<Map<String, Object>> findByUptiId(String tiId) {
		String sql = "select ti_id id,ti_name text from tb_item where ti_status = 1 and ti_uplevel_id =  " + tiId;
		return tbItemDao.searchForMap(sql,null);
	}
}
