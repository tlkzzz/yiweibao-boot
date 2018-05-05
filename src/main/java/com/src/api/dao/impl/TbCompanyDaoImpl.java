package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbCompanyDao;
import com.src.api.entity.TbCompany;

@Repository
public class TbCompanyDaoImpl extends BaseDaoMysqlImpl<TbCompany, Long>
implements TbCompanyDao{
	
	public TbCompanyDaoImpl() {
		super(TbCompany.class);
	}

}
