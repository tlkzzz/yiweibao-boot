package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbSign;

public interface TbSignService  extends BaseService<TbSign, Long> {

	List<Map<String, Object>> getDateTseId(String tseId, String date);
	
	TbSign add(TbSign t);
	
	TbSign edit(TbSign t);
	
	
	List<TbSign> findByTseidANDSignDate(String tseId, String date, String pointsId);
	
	//打卡统计
	List<Map<String, Object>>  getCount(String startTime, String endTime, String tseId, String pointsId, int type);


	public Map<String, Object> getByParam(Map<String, String> param);
}
