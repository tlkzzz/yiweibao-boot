package com.src.api.dao.impl;

import com.src.api.dao.TbSignDao;
import com.src.api.entity.TbSign;
import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;

@Repository
public class TbSignDaoImpl extends BaseDaoMysqlImpl<TbSign, Long>implements TbSignDao {
	
	public TbSignDaoImpl() {
		super(TbSign.class);
	}

}
