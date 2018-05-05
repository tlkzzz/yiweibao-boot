package com.src.api.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbOrderDao;
import com.src.api.entity.TbOrderHead;


@Repository
public class TbOrderDaoImpl extends BaseDaoMysqlImpl<TbOrderHead, Long>
implements TbOrderDao{

	public TbOrderDaoImpl() {
		super(TbOrderHead.class);
	}

}
