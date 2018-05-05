package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbRegionDao;
import com.src.api.entity.TbRegion;

@Repository
public class TbRegionDaoImpl extends BaseDaoMysqlImpl<TbRegion, Long>
implements TbRegionDao{

	public TbRegionDaoImpl() {
		super(TbRegion.class);
	}
}
