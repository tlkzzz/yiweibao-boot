package com.src.api.dao.impl;

import com.src.api.dao.TbAssetTypeDao;
import com.src.api.entity.TbAssetType;
import org.springframework.stereotype.Repository;


import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
@Repository
public class TbAssetTypeDaoImpl extends BaseDaoMysqlImpl<TbAssetType, Long> implements TbAssetTypeDao {

	public TbAssetTypeDaoImpl() {
		super(TbAssetType.class);
	}
}
