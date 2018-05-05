package com.src.api.service;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbMachineDetails;

public interface TbMachineDetailsService extends BaseService<TbMachineDetails, Long> {

	TbMachineDetails getMachineById(long id);
}
