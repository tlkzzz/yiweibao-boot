package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbProductCategory;

public interface TbProductCategoryService extends BaseService<TbProductCategory, Long>{

	/**
	 * 获取商品分类列表
	 * @param valueOf
	 * @return
	 */
	List<Map<String, Object>> findByparentId(Long valueOf);

	HashMap<String, Object> findForJson(HashMap<String, String> params);

	//根据父类id查询子集
	List<TbProductCategory> listByParent(Long parentId);

	void upmore(TbProductCategory tbProductCategory, List<TbProductCategory> tbProductCategoryList);

	TbProductCategory findOne(TbProductCategory tbProductCategoryOne);

	Map<String, Object> findByCompanyId(Long tcId);

	List<Map<String, Object>> findByCompanyIdAjax(Long tcId);

	List<TbProductCategory> getByparentId(Long tpcId, Long tcId);

	Map<String, Object> getByParam(Map<String, String> param);

}
