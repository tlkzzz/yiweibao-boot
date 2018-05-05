package com.src.api.dao.impl;

import org.springframework.stereotype.Service;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbProductCategoryDao;
import com.src.api.entity.TbProductCategory;

@Service
public class TbProductCategoryDaoImpl extends BaseDaoMysqlImpl<TbProductCategory, Long> 
implements TbProductCategoryDao{

	public TbProductCategoryDaoImpl() {
		super(TbProductCategory.class);
	}
}
