package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.ProductSkuDao;
import com.src.api.entity.TbProductSku;
import com.src.api.service.ProductSkuService;


@Service
public class ProductSkuServiceImpl extends BaseServiceImpl<TbProductSku, Long>
implements ProductSkuService{

	@Autowired
	ProductSkuDao productSkuDao;
	
	@Override
	public BaseDao<TbProductSku, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return productSkuDao;
	}

	@Override
	public void delBytpId(Long valueOf) {
		String sql = "DELETE FROM tb_product_sku WHERE tps_product_id = "+valueOf;
		productSkuDao.execute(sql);
		
	}
	
	@Override
    public List<Map<String, Object>> findSpecJsonBySkuId(String skuId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT ts_name tsName,tps_spec_value value FROM tb_product_spec,tb_spec ");
		sbSql.append("WHERE tps_spec_id = ts_id AND tps_sku_id = ? ");
		List<Object> values = new ArrayList<Object>();
		values.add(skuId);
		return productSkuDao.searchForMap(sbSql.toString(), values);
    }

	@Override
	public List<TbProductSku> findCountByProduct(Long tpId) {
		String strSql = "SELECT * FROM  tb_product_sku WHERE tps_product_id="+tpId;
		return productSkuDao.search(strSql, null);
	}
}
