package com.src.api.dao.impl;


import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbLogisticsDao;
import com.src.api.entity.TbLogistics;

@Repository
public class TbLogisticsDaoImpl extends BaseDaoMysqlImpl<TbLogistics, Long> implements TbLogisticsDao {

	public TbLogisticsDaoImpl() {
		super(TbLogistics.class);
	}

}
