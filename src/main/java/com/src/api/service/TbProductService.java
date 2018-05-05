package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbProduct;
import com.src.api.entity.TbProductSku;
import com.src.api.entity.TbProductSpec;

public interface TbProductService extends BaseService<TbProduct, Long>{

	HashMap<String, Object> findForJson(HashMap<String, String> params);

	Map<String, Object> findByCompanyId(Long tcId);

	void addProductAndSpec(TbProduct tbProduct, List<TbProductSku> tbProductSkuList,
                           List<List<TbProductSpec>> tbProductSpecListList);

	List<Map<String, Object>> getById(Long tpId);
	
	List<TbProduct>  getProductListByName_ajax(String product_name, Long id);
		
	void updateProductAndSpec(TbProduct tbProduct, List<Long> lList, List<TbProductSku> tbProductSkuList,
                              List<List<TbProductSpec>> tbProductSpecListList);

	Map<String, Object> getByParam(Map<String, String> param);
	/**
	 * 根据商品货号查询商品是否存在
	 * @param tpNumber 商品货号(编号)
	 * @param tpId 商品id
	 * @return
	 */
	boolean findProductByNumberBoolean(String tpNumber, String tpId);
	/**
	 * 根据商品货号查询商品
	 * @param tpNumber 商品货号
	 * @param tpId 商品id
	 * @return
	 */
	TbProduct findProductByNumber(String tpNumber, String tpId);
	
	public List<TbProduct> find(TbProduct tbProduct);
}
