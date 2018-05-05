package com.src.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.ProductSpecDao;
import com.src.api.entity.TbProductSpec;
import com.src.api.service.ProductSpecService;

@Service
public class ProductSpecServiceImpl extends BaseServiceImpl<TbProductSpec, Long>
implements ProductSpecService{

	@Autowired
	ProductSpecDao productSpecDao;
	
	@Override
	public BaseDao<TbProductSpec, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return productSpecDao;
	}

	@Override
	public List<Map<String, Object>> findBytpIdAndSpec(String tpId, String valueOf) {
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tps_spec_value tpsSpecValue ");//规格值
		strSql.append("FROM tb_product_spec ");//查询表 tb_product_spec
		strSql.append("WHERE tps_product_id ="+tpId+" ");//查询条件
		strSql.append("GROUP BY tps_spec_value ");//排序
		
		return productSpecDao.searchForMap(strSql.toString(), null);
	}
	@Override
	public List<TbProductSpec> findByProduct(String tpId) {
		String sql = "SELECT tps_sku_id from tb_product_spec WHERE tps_product_id="+tpId+" GROUP BY tps_sku_id";
		return productSpecDao.search(sql, null);
	}

	@Override
	public void delBytpId(Long valueOf) {
		String sql = "DELETE FROM tb_product_spec WHERE tps_product_id = "+valueOf;
		productSpecDao.execute(sql);
		
	}

	@Override
	public List<Map<String, Object>> findBytpId(String tpId) {
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("p.tps_sku_id, ");//产品skuid
		strSql.append("(SELECT t.tps_price FROM tb_product_sku t WHERE t.tps_id = p.tps_sku_id) price, ");//价格
		strSql.append("(SELECT s.tps_store FROM tb_product_sku s WHERE s.tps_id = p.tps_sku_id) store ");//库存
		strSql.append("FROM tb_product_spec p ");//查询表 tb_product_spec
		strSql.append("WHERE p.tps_product_id = "+tpId+" ");//查询条件
		strSql.append("GROUP BY p.tps_sku_id ");//排序
		
		return productSpecDao.searchForMap(strSql.toString(), null);
	}
	
	@Override
	public List<Map<String, Object>> findBySku(Long tpsSkuId) {
		String sql = "SELECT ts_id as tsId,ts_name AS tsName,tps_product_id productId,tps_id as tpsId,tps_spec_value AS tpsSpecValue from tb_product_spec,tb_spec AS ts WHERE ts_id = tps_spec_id AND tps_sku_id = "+tpsSkuId;
		return productSpecDao.searchForMap(sql, null);
	}

	@Override
	public List<Map<String, Object>> getBytpId(Long tpId,Object tsId) {
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tps_spec_value tpsSpecValue ");//规格值
		strSql.append("FROM tb_product_spec ");//查询表 tb_product_spec
		strSql.append("WHERE tps_product_id ="+tpId+" ");//查询条件
		strSql.append("AND tps_spec_id = "+tsId+" ");
		
		return productSpecDao.searchForMap(strSql.toString(), null);
	}

	@Override
	public List<Map<String, Object>> getSkuBytpId(Long tpId) {
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tps_sku_id skuId, ");//产品skuid
		strSql.append("GROUP_CONCAT(CAST((SELECT ts_name FROM tb_spec WHERE ts_id = ps.tps_spec_id) AS CHAR)) specName, ");//商品购买规格名称
		strSql.append("GROUP_CONCAT(CAST((SELECT tsv_id FROM tb_spec_value WHERE tsv_spec_id = tps_spec_id AND tsv_value = tps_spec_value ) AS CHAR)) specValueId, ");//规格值id
		strSql.append("GROUP_CONCAT(tps_spec_value) specValue, ");//规格值
		strSql.append("sku.tps_price price, ");//价格
		strSql.append("sku.tps_store store ");//库存
		strSql.append("FROM tb_product_spec ps LEFT JOIN tb_product_sku sku ON sku.tps_id = ps.tps_sku_id ");//查询表 tb_product_spec,tb_product_sku
		strSql.append("WHERE ps.tps_product_id = "+tpId+" ");//查询条件
		strSql.append("GROUP BY tps_sku_id ");//排序
		
		return productSpecDao.searchForMap(strSql.toString(), null);
	}
}
