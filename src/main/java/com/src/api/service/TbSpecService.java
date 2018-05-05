package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbSpec;
import com.src.api.entity.TbSpecValue;

public interface TbSpecService extends BaseService<TbSpec, Long>{

	Map<String, Object> findListByCategory(Long tcId);

	void addSpecAndValue(TbSpec tbSpec, List<TbSpecValue> tbSpecValueList);

	Map<String, Object> findByParam(Map<String, String> param);

	List<Map<String, Object>> getByCategoryId(Long tcId);

	List<TbSpecValue> findValueBytsId(Object object);

	TbSpec findOne(TbSpec tbSpec);

	/** 
	 * @Title: isDel 
	 * @Description:
	 * @param @param tsId    
	 * @return void    返回类型 
	 * @throws 
	 */
	public List<Map<String,Object>> findSpecRelated(Long tsId);

}
