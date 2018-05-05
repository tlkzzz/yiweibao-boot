package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbOrderDetail;
import com.src.api.entity.TbOrderHead;

public interface TbOrderService extends BaseService<TbOrderHead, Long>{

	HashMap<String, Object> findForJson(HashMap<String, String> params);
	
	HashMap<String, Object> list_order_product(HashMap<String, String> params);

	HashMap<String, Object> findForJson1(HashMap<String, String> params);

	List<Map<String, Object>> getById(Long id);
	/**
	 * 订单详情
	 * @param toId
	 * @return
	 */
	Map<String, Object> findDetail(String toId);
	
	void addOrder(TbOrderHead tbOrderHead, List<TbOrderDetail> tbOrderDetailList) throws Exception;

}
