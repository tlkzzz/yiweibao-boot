package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbRegionDetailDao;
import com.src.api.entity.TbRegionDetail;

@Repository
public class TbRegionDetailDaoImpl extends BaseDaoMysqlImpl<TbRegionDetail, Long>
implements TbRegionDetailDao{

	public TbRegionDetailDaoImpl() {
		super(TbRegionDetail.class);
	}
}
