package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbCategoryDao;
import com.src.api.entity.TbCategory;

@Repository
public class TbCategoryDaoImpl extends BaseDaoMysqlImpl<TbCategory, Long>
implements TbCategoryDao{

	public TbCategoryDaoImpl() {
		super(TbCategory.class);
	}
}
