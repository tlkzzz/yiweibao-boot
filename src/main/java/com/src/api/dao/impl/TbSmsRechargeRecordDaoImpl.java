package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbSmsRechargeRecordDao;
import com.src.api.entity.TbSmsRechargeRecord;

@Repository
public class TbSmsRechargeRecordDaoImpl extends BaseDaoMysqlImpl<TbSmsRechargeRecord, Long>implements TbSmsRechargeRecordDao{
	public TbSmsRechargeRecordDaoImpl() {
		super(TbSmsRechargeRecord.class);
	}
}
