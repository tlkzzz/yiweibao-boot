package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbServiceOrderDao;
import com.src.api.entity.TbServiceOrder;

@Repository("tbServiceOrderDao")
public class TbServiceOrderDaoImpl extends
		BaseDaoMysqlImpl<TbServiceOrder, Long> implements TbServiceOrderDao {

	public TbServiceOrderDaoImpl(){
		super(TbServiceOrder.class);
	}
}
