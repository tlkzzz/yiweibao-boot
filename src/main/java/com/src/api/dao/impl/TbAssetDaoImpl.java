package com.src.api.dao.impl;

import com.src.api.dao.TbAssetDao;
import com.src.api.entity.TbAsset;
import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;

@Repository
public class TbAssetDaoImpl extends BaseDaoMysqlImpl<TbAsset, Long> implements TbAssetDao {

	public TbAssetDaoImpl() {
		super(TbAsset.class);
	}
}
