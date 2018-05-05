package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbItemDao;
import com.src.api.entity.TbItem;

@Repository("tbItemDao")
public class TbItemDaoImpl extends BaseDaoMysqlImpl<TbItem, Long> implements
		TbItemDao {

	public TbItemDaoImpl(){
		super(TbItem.class);
	}
}
