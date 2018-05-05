package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbOrderDetailDao;
import com.src.api.entity.TbOrderDetail;

@Repository("tbOrderDetailDao")
public class TbOrderDetailDaoImpl extends BaseDaoMysqlImpl<TbOrderDetail, Long>
implements TbOrderDetailDao{
	public TbOrderDetailDaoImpl() {
		super(TbOrderDetail.class);
	}
}
