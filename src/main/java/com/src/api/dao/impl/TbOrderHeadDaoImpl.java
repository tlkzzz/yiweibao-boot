package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbOrderHeadDao;
import com.src.api.entity.TbOrderHead;

@Repository("tbOrderHeadDao")
public class TbOrderHeadDaoImpl extends BaseDaoMysqlImpl<TbOrderHead, Long> implements
TbOrderHeadDao {

	public TbOrderHeadDaoImpl(){
		super(TbOrderHead.class);
	}
}
