package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbSpecValueDao;
import com.src.api.entity.TbSpecValue;

@Repository
public class TbSpecValueDaoImpl extends BaseDaoMysqlImpl<TbSpecValue, Long>
implements TbSpecValueDao{

	public TbSpecValueDaoImpl() {
		super(TbSpecValue.class);
	}
}
