package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbBrandDao;
import com.src.api.entity.TbBrand;

@Repository
public class TbBrandDaoImpl extends BaseDaoMysqlImpl<TbBrand, Long>
implements TbBrandDao{

	public TbBrandDaoImpl() {
		super(TbBrand.class);
	}
}
