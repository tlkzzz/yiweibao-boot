package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbCompanyCustomerDao;
import com.src.api.entity.TbCompanyCustomer;


@Repository
public class TbCompanyCustomerDaoImpl extends BaseDaoMysqlImpl<TbCompanyCustomer, Long>
implements TbCompanyCustomerDao{

	public TbCompanyCustomerDaoImpl() {
		super(TbCompanyCustomer.class);
	}
}
