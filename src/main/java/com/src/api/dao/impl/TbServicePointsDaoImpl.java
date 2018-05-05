package com.src.api.dao.impl;


import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbServicePointsDao;
import com.src.api.entity.TbServicePoints;

@Repository("tbServicePointsDao")
public class TbServicePointsDaoImpl extends
		BaseDaoMysqlImpl<TbServicePoints, Long> implements TbServicePointsDao {

	public TbServicePointsDaoImpl()
	{
		super(TbServicePoints.class);
	}

}
