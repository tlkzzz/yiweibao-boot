package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbEmployeePositionDao;
import com.src.api.entity.TbEmployeePosition;

@Repository("tbEmployeePositionDao")
public class TbEmployeePositionDaoImpl extends BaseDaoMysqlImpl<TbEmployeePosition, Long>
		implements TbEmployeePositionDao {

	public TbEmployeePositionDaoImpl() {
		super(TbEmployeePosition.class);
	}
}
