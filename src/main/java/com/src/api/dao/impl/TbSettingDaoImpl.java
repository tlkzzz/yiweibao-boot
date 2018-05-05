package com.src.api.dao.impl;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbSettingDao;
import com.src.api.entity.TbSetting;
import org.springframework.stereotype.Repository;

@Repository("tbSettingDao")
public class TbSettingDaoImpl extends BaseDaoMysqlImpl<TbSetting, Long> implements TbSettingDao {
	public TbSettingDaoImpl() {
		super(TbSetting.class);
	}
}
