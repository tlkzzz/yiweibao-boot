package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbAssetType;
import com.src.api.entity.TbSetting;

public interface TbSettingService extends BaseService<TbSetting, Long> {
	
	List<TbSetting> findByCompanyId(Long companyId, Long cus);


	public Map<String, Object> getByParam(Map<String, String> param);


}
