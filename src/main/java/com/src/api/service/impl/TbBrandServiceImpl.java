package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.src.common.base.entity.PageBean;
import com.src.common.utils.GlobalStatic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbBrandDao;
import com.src.api.entity.TbBrand;
import com.src.api.entity.TbCategory;
import com.src.api.entity.TbCompany;
import com.src.api.service.TbBrandService;

@Service
public class TbBrandServiceImpl extends BaseServiceImpl<TbBrand, Long>
implements TbBrandService{

	@Autowired
	TbBrandDao tbBrandDao;
	@Override
	public BaseDao<TbBrand, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbBrandDao;
	}
	@Override
	public List<Map<String, Object>> getAll() {
		String sql = "SELECT tb_id AS tbId,tb_name AS tbName FROM tb_brand WHERE tb_status = 1";
		return tbBrandDao.searchForMap(sql, null);
	}
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
//		String sort = params.get("sort");
		String order = params.get("order");
		String tbName = params.get("tbName");
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tb_id tbId,tb_name tbName,tc_id tcId, ");
		sbSql.append("tc_name tcName, tb_desp tbDesp,u.`name` uName,");
		sbSql.append("DATE_FORMAT(tb_addtime,'%Y-%m-%d %H:%i:%s') tbAddtime ");
		sbSql.append("FROM tb_brand ,tb_category ,");
		sbSql.append("userinfo u WHERE tb_category_id = tc_id and ");
		sbSql.append("tb_adduser = u.id AND tb_status =1 AND tc_type =1 ");
		if (!StringUtils.isBlank(tbName)) {
			sbSql.append(" and tb_name like '%"+tbName+"%' ");
		}
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tb_addtime,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tb_addtime,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')");
		}
		
		if (!StringUtils.isBlank(order)) {
			sbSql.append(" order by tb_addtime " + order);
		} else {
			sbSql.append(" order by tb_addtime desc ");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbBrandDao.searchForMap(sbSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbBrandDao.searchForMap(sbSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	@Override
	public List<Map<String, Object>> getAll1(String categoryId) {
		String sql = "SELECT tb_id AS tbId,tb_name AS tbName FROM tb_brand WHERE tb_status = 1 and tb_category_id="+categoryId;
		return tbBrandDao.searchForMap(sql, null);
	}
	@Override
	public Map<String, Object> getList() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tb_id ,tb_name ,tb_desp ,tb_status,");
		sbSql.append("(SELECT tc_name FROM tb_category WHERE tc_id = tb_category_id) tcName,");
		sbSql.append("(SELECT count(0) from tb_product WHERE tp_brand_id = tb_id) useCount ");
		sbSql.append("FROM tb_brand WHERE tb_status <> 0 ORDER BY tb_addtime desc ");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(1, 10);
		pageBean = tbBrandDao.searchForMap(sbSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());
		map.put("count", pageBean.getRowCount());
		map.put("rows", pageBean.getPageCount());
		return map;
	}
	@Override
	public List<TbBrand> findList(Long tcId) {
		String sql = "select * from tb_brand WHERE tb_status =1 and tb_category_id = "+tcId;
		return tbBrandDao.search(sql, null);
	}
	@Override
	public Map<String, Object> getByParam(Map<String, String> param) {
		String tbName = param.get("tbName");
		String tbCategoryId =param.get("tbCategoryId");
		int page = param.get("page") == null ? 0 : Integer.parseInt(param.get("page"));
		int pageSize = param.get("pageSize") == null ? 0 : Integer.parseInt(param.get("pageSize"));
		String companyId = param.get("companyId");
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tb_id,");//品牌id
		strSql.append("tb_name,");//品牌名称
		strSql.append("tb_desp,");//描述
		strSql.append("tb_status,");//状态
		strSql.append("(SELECT tc_name FROM tb_category WHERE tc_id = tb_category_id) tcName,");//所属类型
		strSql.append("(SELECT count(0) from tb_product WHERE tp_brand_id = tb_id AND tp_status = 1) useCount ");//使用数量
		strSql.append("FROM tb_brand WHERE tb_status <> 0 AND tb_adduser = "+companyId+" ");//状态不是删除，登录单位下

		if(!StringUtils.isBlank(tbName)){
			strSql.append("AND tb_name LIKE '%"+tbName+"%' ");
		}
		if(!StringUtils.isBlank(tbCategoryId)){
			strSql.append("AND tb_category_id = "+tbCategoryId+" ");
		}

		strSql.append("ORDER BY tb_addtime DESC ");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, 10);//每页十条数据
		pageBean = tbBrandDao.searchForMap(strSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage",page);//分页
		return map;
	}
	@Override
	public TbBrand findOne(TbBrand tbBrand) {
		return tbBrandDao.searchOne(tbBrand);
	}
	@Override
	public TbBrand findBrandByTcIdAndTbName(String tcId, String tbName,
			String tbId, String addUserId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_brand ");
		sbSql.append("WHERE tb_category_id = ? AND tb_name = ? AND tb_status <>0 ");//根据类型id与品牌名称
		List<Object> values = new ArrayList<Object>();
		values.add(tcId);
		values.add(tbName);
		if(!StringUtils.isBlank(tbId)){
			sbSql.append(" AND tb_id != ? ");//获取不等于某品牌id的品牌
			values.add(tbId);
		}
		if(!StringUtils.isBlank(addUserId)){
			sbSql.append(" AND tb_adduser = ? ");//获取添加人id
			values.add(addUserId);
		}
		List<TbBrand> brandList = tbBrandDao.search(sbSql.toString(), values);
		if(brandList.size()>0){
			return brandList.get(0);
		}
		return null;
	}
	
	
}

