package com.src.api.service;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbCustMachine;

public interface TbCustMachineService extends BaseService<TbCustMachine, Long> {

	TbCustMachine searchOne(TbCustMachine t);

}
