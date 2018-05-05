package com.src.api.service;

import java.util.HashMap;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbOrderInsurance;

public interface TbOrderInsuranceService extends BaseService<TbOrderInsurance, Long> {

	public void orderInsureOpt(TbOrderInsurance params);
	
	public HashMap<String, Object> findForJson(HashMap<String, String> params);
}
