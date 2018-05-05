package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbAssetType;

import javax.servlet.http.HttpSession;

public interface TbAssetTypeService  extends BaseService<TbAssetType, Long>{
	
	
	public List<TbAssetType> findByTbAssetType(TbAssetType t);

//	void add(TbAssetType t);
//	void update(TbAssetType t);

	public List<Map<String,Object>> serchAssetType(String companyId, String tspId);



	public Map<String, Object> getByParam(Map<String, String> param);
}
