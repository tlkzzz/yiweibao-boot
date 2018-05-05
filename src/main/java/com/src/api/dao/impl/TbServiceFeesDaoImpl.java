package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbServiceFeesDao;
import com.src.api.entity.TbServiceFees;

@Repository
public class TbServiceFeesDaoImpl extends BaseDaoMysqlImpl<TbServiceFees, Long>
implements TbServiceFeesDao{

	public TbServiceFeesDaoImpl() {
		super(TbServiceFees.class);
	}
	
}
