package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbOrderInsuranceDao;
import com.src.api.entity.TbOrderInsurance;

@Repository("tbOrderInsuranceDao")
public class TbOrderInsuranceDaoImpl extends
		BaseDaoMysqlImpl<TbOrderInsurance, Long> implements
		TbOrderInsuranceDao {

	public TbOrderInsuranceDaoImpl()
	{
		super(TbOrderInsurance.class);
	}
}
