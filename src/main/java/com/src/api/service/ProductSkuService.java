package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbProductSku;

public interface ProductSkuService extends BaseService<TbProductSku, Long>{

	/**
	 * 根据产品id删除
	 * */
	void delBytpId(Long valueOf);
	
	List<Map<String, Object>> findSpecJsonBySkuId(String skuId);

	/**
	 * 根据产品id查询
	 * */
	List<TbProductSku> findCountByProduct(Long tpId);

}
