package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbSpecDao;
import com.src.api.entity.TbSpec;


@Repository
public class TbSpecDaoImpl extends BaseDaoMysqlImpl<TbSpec, Long>
implements TbSpecDao{

	public TbSpecDaoImpl() {
		super(TbSpec.class);
	}
}
