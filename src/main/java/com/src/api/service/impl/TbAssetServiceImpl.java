package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.api.dao.TbAssetDao;
import com.src.api.entity.TbAsset;
import com.src.api.service.TbAssetService;
import com.src.common.base.entity.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;

@Service
public class TbAssetServiceImpl extends BaseServiceImpl<TbAsset, Long>implements TbAssetService {

	@Autowired
	TbAssetDao tbAssetDaoImpl;
	
	@Override
	public BaseDao<TbAsset, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbAssetDaoImpl;
	}
	
	
	
	
	@Override
	public TbAsset add(TbAsset t) {
		// TODO Auto-generated method stub
		return this.tbAssetDaoImpl.save(t);
	}

	@Override
	public TbAsset edit(TbAsset t) {
		// TODO Auto-generated method stub
		return this.tbAssetDaoImpl.update(t);
	}

	@Override
	public int del(Long id) {
		// TODO Auto-generated method stub
		return this.tbAssetDaoImpl.del(id);
	}

	@Override
	public List<TbAsset> search(TbAsset t) {
		// TODO Auto-generated method stub
		return this.tbAssetDaoImpl.search(t);
	}




	@Override
	public List<Map<String, Object>> asseSearch(String goodsId) {
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		String sql="select t.id as assetTypeId, t.name as assetTypeName,a.id as assetId,a.name as assetName,a.simple_name as simpleName,"+ 
				"a.position ,a.desp,a.pics from tb_asset_type t left join tb_asset a on   t.id=a.asset_type_id where  a.status=1 AND"
				+ " a.id="+goodsId ;
		list = tbAssetDaoImpl.searchForMap(sql, null);
		
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public Map<String, Object> getByParam(Map<String, String> param) {
		String name =param.get("name");
		String companyId = param.get("companyId");
		int page = param.get("page") == null ? 0:Integer.parseInt(param.get("page"));
		int pageSize = param.get("pageSize") == null ? 0:Integer.parseInt(param.get("pageSize"));
		StringBuffer strSql = new StringBuffer();
		strSql.append(" SELECT a.*,t.name AS typeName,s.tsp_name AS tspName FROM tb_asset   a LEFT JOIN tb_asset_type t ON a.asset_type_id= t.id LEFT JOIN tb_service_points s ON a.service_points_id = s.tsp_id WHERE a.status ='1'");
		strSql.append("AND  a.company_id='"+companyId+"'");

		if(!StringUtils.isBlank(name)){
			strSql.append("AND  a.name LIKE '%"+name+"%' ");
		}

//		if(!StringUtils.isBlank(tspId)){
//			strSql.append("AND a.service_points_id = "+tspId+" ");
//		}
//		if(!StringUtils.isBlank(type)){
//			strSql.append("AND a.asset_type_id = "+type+" ");
//		}

//		if(!StringUtils.isBlank(page)){
//			nowPage = Integer.valueOf(page);
//		}
//		if(StringUtils.isBlank(page)||Integer.valueOf(page)<1){//分页为空或小于1
//			nowPage = 1;
//		}



		strSql.append("ORDER BY a.create_date DESC ");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, 10);//每页十条数据
		pageBean = tbAssetDaoImpl.searchForMap(strSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage",page);//分页
		return map;

	}


}
