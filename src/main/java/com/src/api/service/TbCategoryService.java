package com.src.api.service;

import java.util.HashMap;
import java.util.List;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbCategory;

public interface TbCategoryService extends BaseService<TbCategory, Long>{

	List<TbCategory> findList(Long tcId);

	List<TbCategory> getAll(Long tcId);
	HashMap<String, Object> list_order_product(HashMap<String, String> params);
	List<TbCategory> getLimit(Integer categoryPage, String tcId);

	TbCategory findOne(TbCategory tbCategoryOne);

}
