package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbRegionDetail;

public interface TbRegionDetailService extends BaseService<TbRegionDetail, Long>{

	List<Map<String, Object>> getByTrId(Long trId);

	void delBytrId(Long trId);

	TbRegionDetail findOne(TbRegionDetail tbRegionDetailOne);

	List<TbRegionDetail> findByCompanyAndCity(Integer valueOf, Long tcId);

}
