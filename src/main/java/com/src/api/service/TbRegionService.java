package com.src.api.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbRegion;
import com.src.api.entity.TbRegionDetail;

public interface TbRegionService extends BaseService<TbRegion, Long>{

	Map<String, Object> getList();

	void addRegionAndDetail(TbRegion tbRegion, List<TbRegionDetail> tbRegionDetailList);

	void upRegionAndDetail(TbRegion tbRegion, List<TbRegionDetail> tbRegionDetailList);

	Map<String, Object> getByParam(Map<String, String> param, HttpSession session);

	TbRegion findOne(TbRegion tbRegionOne);

}
