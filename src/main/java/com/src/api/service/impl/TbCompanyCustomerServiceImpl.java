package com.src.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbCompanyCustomerDao;
import com.src.api.entity.TbCompanyCustomer;
import com.src.api.service.TbCompanyCustomerService;


@Service
public class TbCompanyCustomerServiceImpl extends BaseServiceImpl<TbCompanyCustomer, Long>
implements TbCompanyCustomerService{

	@Autowired
	TbCompanyCustomerDao tbCompanyCustomerDao;
	
	@Override
	public BaseDao<TbCompanyCustomer, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbCompanyCustomerDao;
	}
}
