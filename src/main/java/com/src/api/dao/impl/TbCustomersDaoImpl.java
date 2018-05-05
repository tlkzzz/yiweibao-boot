package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbCustomersDao;
import com.src.api.entity.TbCustomers;

@Repository("tbCustomersDao")
public class TbCustomersDaoImpl extends BaseDaoMysqlImpl<TbCustomers, Long> implements TbCustomersDao  {
	
	public TbCustomersDaoImpl()
	{
		super(TbCustomers.class);
	}

}
