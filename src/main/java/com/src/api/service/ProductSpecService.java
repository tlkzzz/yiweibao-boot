package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbProductSpec;

public interface ProductSpecService extends BaseService<TbProductSpec, Long>{

	List<Map<String, Object>> findBytpIdAndSpec(String tpId, String valueOf);
	List<TbProductSpec> findByProduct(String tpId);
	/**
	 * 根据产品id删除
	 * */
	void delBytpId(Long valueOf);
	
	List<Map<String, Object>> findBySku(Long tpsSkuId);

	List<Map<String, Object>> findBytpId(String tpId);

	List<Map<String, Object>> getBytpId(Long tpId, Object tsId);

	List<Map<String, Object>> getSkuBytpId(Long tpId);

}
