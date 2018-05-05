package com.src.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.entity.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbProductCategoryDao;
import com.src.api.entity.TbProductCategory;
import com.src.api.service.TbProductCategoryService;


@Service
public class TbProductCategoryServiceImpl extends BaseServiceImpl<TbProductCategory, Long>
implements TbProductCategoryService{

	@Autowired
	TbProductCategoryDao tbProductCategoryDao;
	
	@Override
	public BaseDao<TbProductCategory, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbProductCategoryDao;
	}

	@Override
	public List<Map<String, Object>> findByparentId(Long valueOf) {
		String sql = "SELECT tpc_id,tpc_name FROM tb_product_category WHERE tpc_parent_id="+valueOf;
		return tbProductCategoryDao.searchForMap(sql, null);
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
//		String sort = params.get("sort");
		String order = params.get("order");
		String tpcName = params.get("tpcName");
		String tpcParentId = params.get("tpcParentId");
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		String companyId = params.get("companyId");
		
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tpc_id tpcId, ");//分类id
		strSql.append("tpc_name tpcName, ");//分类名称
		strSql.append("(SELECT tpc2.tpc_name FROM tb_product_category tpc2 WHERE tpc2.tpc_id = tpc1.tpc_parent_id) parentName, ");//父级分类名称
		strSql.append("u.`name` uName, ");//添加人姓名
		strSql.append("DATE_FORMAT(tpc1.tpc_addtime,'%Y-%m-%d %H:%i:%s') tpcAddtime, ");//添加时间
		strSql.append("tpc_status tpcStatus ");//状态
		strSql.append("FROM tb_product_category tpc1,userinfo u ");//查询表 tb_product_category,userinfo
		strSql.append("WHERE tpc_adduser = u.id AND tpc_status !=0 ");//查询条件
		
		if (!StringUtils.isBlank(tpcName)) {
			strSql.append("AND tpc1.tpc_name LIKE '%"+tpcName+"%' ");
		}
		if (!StringUtils.isBlank(companyId)) {
			strSql.append("AND tpc1.tpc_company_id = "+companyId+" ");
		}
		
		if (!StringUtils.isBlank(tpcParentId)) {
			strSql.append("AND tpc1.tpc_parent_id = "+tpcParentId+" ");
		}
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			strSql.append("AND DATE_FORMAT(tpc1.tpc_addtime,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d') ");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			strSql.append("AND DATE_FORMAT(tpc1.tpc_addtime,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d') ");
		}
		
		if (!StringUtils.isBlank(order)) {
			strSql.append("ORDER BY tpc1.tpc_addtime "+order+" ");
		} else {
			strSql.append("ORDER BY tc_addtime DESC ");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbProductCategoryDao.searchForMap(strSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbProductCategoryDao.searchForMap(strSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<TbProductCategory> listByParent(Long parentId) {
		String sql = "SELECT * FROM tb_product_category WHERE tpc_status !=0 AND tpc_parent_id ="+parentId; 
		return tbProductCategoryDao.search(sql, null);
	}

	@Override
	@Transactional
	public void upmore(TbProductCategory tbProductCategory, List<TbProductCategory> tbProductCategoryList) {
		tbProductCategory.setTpcStatus(0);
		tbProductCategoryDao.update(tbProductCategory);
		
		if(tbProductCategoryList!=null&&tbProductCategoryList.size()>0){
			int in = 1;
			String sql = "update tb_product_category set tpc_status =0 where tpc_id in (";
			for (TbProductCategory tbProductCategory2 : tbProductCategoryList) {
				sql += ""+tbProductCategory2.getTpcId();
				if(in<tbProductCategoryList.size()){
					sql += ",";
					in++;
				}
			}
			sql += ")";
			tbProductCategoryDao.update(sql, null);
			
			int in1 = 1;
			String sql1 = "select*from tb_product_category where tpc_status !=0 and tpc_parent_id in (";
			for (TbProductCategory tbProductCategory2 : tbProductCategoryList) {
				sql1 += ""+tbProductCategory2.getTpcId();
				if(in1<tbProductCategoryList.size()){
					sql1 += ",";
					in1++;
				}
			}
			sql1 += ")";
			List<TbProductCategory> tbProductCategoryList1 = tbProductCategoryDao.search(sql1, null);
			
			
			if(tbProductCategoryList1!=null&&tbProductCategoryList1.size()>0){
				int in2 = 1;
				String sql2 = "update tb_product_category set tpc_status =0 where tpc_id in (";
				for (TbProductCategory tbProductCategory3 : tbProductCategoryList1) {
					sql2 += ""+tbProductCategory3.getTpcId();
					if(in2<tbProductCategoryList.size()){
						sql2 += ",";
						in2++;
					}
				}
				sql2 += ")";
				tbProductCategoryDao.update(sql2, null);
			}
		}
		
	}

	@Override
	public TbProductCategory findOne(TbProductCategory tbProductCategoryOne) {
		// TODO Auto-generated method stub
		return tbProductCategoryDao.searchOne(tbProductCategoryOne);
	}

	@Override
	public Map<String, Object> findByCompanyId(Long tcId) {
		StringBuffer sbSql = new StringBuffer("SELECT tpc_id,tpc_name,tpc_desp,tpc_status,"
				+ "IF(tpc_parent_id=0,'父级',(SELECT tpc.tpc_name FROM tb_product_category tpc WHERE tpc.tpc_id = t.tpc_parent_id)) parentName  ");
		sbSql.append("FROM tb_product_category t ");
		sbSql.append("WHERE tpc_status <> 0 AND tpc_company_id = "+tcId+" order by tpc_addtime desc" );
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(1, 10);
		pageBean = tbProductCategoryDao.searchForMap(sbSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());
		map.put("count", pageBean.getRowCount());
		map.put("rows", pageBean.getPageCount());
		return map;
	}

	@Override
	public List<Map<String, Object>> findByCompanyIdAjax(Long tcId) {
		String sql = "SELECT tpc_id tpcId,tpc_name tpcName FROM tb_product_category WHERE tpc_parent_id = 0 AND tpc_status =1 AND tpc_company_id = "+tcId;
		List<Map<String, Object>> categoryList = tbProductCategoryDao.searchForMap(sql, null);
		for (Map<String, Object> map : categoryList) {
			String sql1 = "SELECT tpc_id tpcId,tpc_name tpcName FROM tb_product_category WHERE tpc_parent_id ="+map.get("tpcId")+" AND tpc_status =1 AND tpc_company_id = "+tcId; 
			map.put("list", tbProductCategoryDao.searchForMap(sql1,null));
		}
		return categoryList;
	}

	@Override
	public List<TbProductCategory> getByparentId(Long tpcId,Long tcId) {
		String sql = "SELECT * FROM tb_product_category WHERE tpc_status =1 AND tpc_parent_id = "+tpcId+" AND tpc_company_id ="+tcId;
		return tbProductCategoryDao.search(sql, null);
	}

	@Override
	public Map<String, Object> getByParam(Map<String, String> param) {
		String companyId = param.get("companyId");
		String tpcName = param.get("tpcName");
		String tpcStatus = param.get("tpcStatus");
		String page = param.get("page");//分页
		Integer nowPage = 1;
		StringBuffer sbSql = new StringBuffer("SELECT tpc_id,left(tpc_name,8) tpc_name,left(tpc_desp,30) tpc_desp,tpc_status,(SELECT count(0) FROM tb_product WHERE tp_pc_id =tpc_id and tp_status<>0) productCount,(SELECT count(0) FROM tb_product_category pc WHERE pc.tpc_parent_id = t.tpc_id AND pc.tpc_status != 0) nextCount,"
				+ "left(IF(tpc_parent_id=0,'父级',(SELECT tpc.tpc_name FROM tb_product_category tpc WHERE tpc.tpc_id = t.tpc_parent_id)),8) parentName  ");
		sbSql.append("FROM tb_product_category t ");
		sbSql.append("WHERE tpc_status <> 0 AND tpc_company_id = "+companyId+" " );
		if(!StringUtils.isBlank(tpcName)){
			sbSql.append(" and tpc_name like '%"+tpcName+"%'");
		}
		if(!StringUtils.isBlank(tpcStatus)){
			sbSql.append(" and tpc_status ="+tpcStatus);
		}
		if(!StringUtils.isBlank(page)){
			nowPage = Integer.valueOf(page);
		}
		if(StringUtils.isBlank(page)||Integer.valueOf(page)<1){
			nowPage = 1;
		}
		sbSql.append(" order by tpc_addtime desc");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(nowPage, 10);
		pageBean = tbProductCategoryDao.searchForMap(sbSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage", nowPage);//当前页
		return map;
	}
	
}

