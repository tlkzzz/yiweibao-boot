package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbMachineDetailsDao;
import com.src.api.entity.TbMachineDetails;

@Repository("tbMachineDetailsDao")
public class TbMachineDetailsDaoImpl extends BaseDaoMysqlImpl<TbMachineDetails, Long>implements TbMachineDetailsDao {

	public TbMachineDetailsDaoImpl() {
		super(TbMachineDetails.class);
	}
}
