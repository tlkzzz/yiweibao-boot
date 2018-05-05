package com.src.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.api.dao.TbAssetTypeDao;
import com.src.api.entity.TbAssetType;
import com.src.api.entity.TbCompany;
import com.src.api.service.TbAssetTypeService;
import com.src.common.base.entity.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;

@Service
public class TbAssetTypeServiceImpl extends BaseServiceImpl<TbAssetType, Long>implements TbAssetTypeService {

	@Autowired
	TbAssetTypeDao tbAssetTypeDaoImpl;
	
	
	@Override
	public BaseDao<TbAssetType, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbAssetTypeDaoImpl;
	}

	@Override
	public List<TbAssetType> findByTbAssetType(TbAssetType t) {
		// TODO Auto-generated method stub
		return this.tbAssetTypeDaoImpl.search(t);
	}

	@Override
	public List<Map<String, Object>> serchAssetType(String companyId,String tseId) {
		String sql = " SELECT a.id,a.name  FROM tb_asset_type a WHERE a.company_id = "+companyId+" AND a.status=1 ";
		if(!StringUtils.isBlank(tseId)){
			sql+="AND  a.service_points_id ='"+tseId+"' ";
		}
		return this.tbAssetTypeDaoImpl.searchForMap(sql,null);
	}

	@Override
	public Map<String, Object> getByParam(Map<String, String> param) {
		String name =param.get("name");
		String companyId = param.get("companyId");
		int page = param.get("page") == null ? 0:Integer.parseInt(param.get("page"));
		int pageSize = param.get("pageSize") == null ? 0:Integer.parseInt(param.get("pageSize"));
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT a.id, a.name,a.service_points_id AS tspId,s.tsp_name AS tspName ,a.create_date AS ctime,a.create_person AS person FROM tb_asset_type a LEFT JOIN tb_service_points s ON a.service_points_id = s.tsp_id  WHERE   s.tsp_status='1' AND a.status='1'");
		strSql.append("AND s.tsp_company_id='"+companyId+"' ");
		if(!StringUtils.isBlank(name)){
			strSql.append("AND  a.name LIKE '%"+name+"%' ");
		}


		strSql.append("ORDER BY a.create_date DESC ");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, 10);//每页十条数据
		pageBean = tbAssetTypeDaoImpl.searchForMap(strSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage",page);//分页
		return map;
	}

//	@Override
//	public void add(TbAssetType t) {
//
//	}

}
