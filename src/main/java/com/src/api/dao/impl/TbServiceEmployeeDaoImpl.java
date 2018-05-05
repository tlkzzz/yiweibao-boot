package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbServiceEmployeeDao;
import com.src.api.entity.TbServiceEmployee;

@Repository("tbServiceEmployeeDao")
public class TbServiceEmployeeDaoImpl extends
		BaseDaoMysqlImpl<TbServiceEmployee, Long> implements
		TbServiceEmployeeDao {

	public TbServiceEmployeeDaoImpl()
	{
		super(TbServiceEmployee.class);
	}
}
