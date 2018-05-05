package com.src.api.service.impl;

import com.src.api.entity.TbSmsRechargeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbSmsRechargeRecordDao;
import com.src.api.service.TbSmsRechargeRecordService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbSmsRechargeRecordServiceImpl extends BaseServiceImpl<TbSmsRechargeRecord, Long>implements TbSmsRechargeRecordService{
	@Autowired
	TbSmsRechargeRecordDao tbSmsRechargeRecordDao;
	@Override
	public BaseDao<TbSmsRechargeRecord, Long> getGenericDao() {
		return tbSmsRechargeRecordDao;
	}

	@Override
	public List<TbSmsRechargeRecord> findByNumber(String number) {
		List<Object> values = new ArrayList<>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM `tb_sms_recharge_record` WHERE ");
		sbSql.append("`tsrr_number` = ? ");
		values.add(number);
		return tbSmsRechargeRecordDao.search(sbSql.toString(),values);
	}
}
