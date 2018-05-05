package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbServiceOrderDetailDao;
import com.src.api.entity.TbServiceOrderDetail;

@Repository("tbServiceOrderDetailDao")
public class TbServiceOrderDetailDaoImpl extends
		BaseDaoMysqlImpl<TbServiceOrderDetail, Long> implements
		TbServiceOrderDetailDao {

	public TbServiceOrderDetailDaoImpl(){
		super(TbServiceOrderDetail.class);
	}
	
}
