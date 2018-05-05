package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.ProductSkuDao;
import com.src.api.entity.TbProductSku;
@Repository
public class ProductSkuDaoImpl extends BaseDaoMysqlImpl<TbProductSku, Long>
implements ProductSkuDao{
	
	public ProductSkuDaoImpl() {
		super(TbProductSku.class);
	}
}
