package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbCompanySetDao;
import com.src.api.entity.TbCompanySet;

@Repository
public class TbCompanySetDaoImpl extends BaseDaoMysqlImpl<TbCompanySet, Long>
implements TbCompanySetDao{

	
	public TbCompanySetDaoImpl() {
		super(TbCompanySet.class);
	}
	
}
