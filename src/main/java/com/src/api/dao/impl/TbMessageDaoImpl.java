package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbMessageDao;
import com.src.api.entity.TbMessage;

@Repository
public class TbMessageDaoImpl extends BaseDaoMysqlImpl<TbMessage, Long>
implements TbMessageDao{

	public TbMessageDaoImpl() {
		super(TbMessage.class);
	}
}
