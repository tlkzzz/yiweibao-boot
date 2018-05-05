package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbProductDao;
import com.src.api.entity.TbProduct;

@Repository
public class TbProductDaoImpl extends BaseDaoMysqlImpl<TbProduct, Long>
implements TbProductDao{

	public TbProductDaoImpl() {
		super(TbProduct.class);
	}
}
