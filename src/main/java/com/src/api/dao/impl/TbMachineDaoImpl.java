package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbMachineDao;
import com.src.api.entity.TbMachine;

@Repository("tbMachineDao")
public class TbMachineDaoImpl extends BaseDaoMysqlImpl<TbMachine, Long>implements TbMachineDao {

	public TbMachineDaoImpl() {
		super(TbMachine.class);
	}
}
