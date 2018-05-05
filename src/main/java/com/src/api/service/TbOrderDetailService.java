package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbOrderDetail;

public interface TbOrderDetailService extends BaseService<TbOrderDetail, Long>{

	List<Map<String, Object>> findProductByOrder(String memberId, String page);

	TbOrderDetail findOrderDetail(String productId, String memberId,
                                  String orderId, String string);
	
	TbOrderDetail findByProductCode(String productCode, String todType);

    HashMap<String,Object> getOrderProductList(HashMap<String, Object> params);
}
