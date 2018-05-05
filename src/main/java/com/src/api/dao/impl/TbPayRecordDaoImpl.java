package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbPayRecordDao;
import com.src.api.entity.TbPayRecord;

@Repository
public class TbPayRecordDaoImpl extends BaseDaoMysqlImpl<TbPayRecord, Long> implements TbPayRecordDao {

	public TbPayRecordDaoImpl() {
		super(TbPayRecord.class);
	}

}
