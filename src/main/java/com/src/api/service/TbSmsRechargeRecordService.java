package com.src.api.service;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbSmsRechargeRecord;

import java.util.List;

public interface TbSmsRechargeRecordService extends BaseService<TbSmsRechargeRecord, Long>{

    List<TbSmsRechargeRecord> findByNumber(String number);
}
