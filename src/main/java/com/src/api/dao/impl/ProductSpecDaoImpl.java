package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.ProductSpecDao;
import com.src.api.entity.TbProductSpec;

@Repository
public class ProductSpecDaoImpl extends BaseDaoMysqlImpl<TbProductSpec, Long>
implements ProductSpecDao{

	public ProductSpecDaoImpl() {
		super(TbProductSpec.class);
	}
}
