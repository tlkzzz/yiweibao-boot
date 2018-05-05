package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbMemberAddressDao;
import com.src.api.entity.TbAddress;

@Repository
public class TbMemberAddressDaoImpl extends BaseDaoMysqlImpl<TbAddress, Long>
implements TbMemberAddressDao{

	public TbMemberAddressDaoImpl() {
		super(TbAddress.class);
	}
	
}
