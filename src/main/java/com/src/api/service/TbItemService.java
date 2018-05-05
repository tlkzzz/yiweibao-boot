package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbItem;

public interface TbItemService extends BaseService<TbItem, Long> {

	TbItem searchOne(TbItem t);
	
	List<TbItem> findXiaoItemByCategoryId(String itemId);
	
	List<TbItem> findItemByCategoryId(String categoryId, String tiCompanyId);
	
	HashMap<String, Object> findForJson(HashMap<String, String> params);
	
	List<TbItem> findUpLevel(String tiId);
	List<TbItem> findUpLevel(HashMap<String, String> params);
	
	List<TbItem> selectList();
	
	List<TbItem> selectSubList();

	List<TbItem> selectListByCategory(String categoryId);

	List<TbItem> findItemByCategoryId(String categoryId);

	List<Map<String,Object>> findByUptiId(String tiId);
}
