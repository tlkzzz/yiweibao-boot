package com.src.api.service;


import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbPayRecord;

public interface TbPayRecordService extends BaseService<TbPayRecord, Long> {

	TbPayRecord findByNumber(String number);

	Map<String, Object> saveRecord(String toId, String way, Integer type,
                                   String payResource)throws Exception;
}
