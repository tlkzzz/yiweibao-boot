package com.src.api.service;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbCompanySet;

public interface TbCompanySetService extends BaseService<TbCompanySet, Long>{

	
	TbCompanySet findByCompanyid(String id);
	
	
}
