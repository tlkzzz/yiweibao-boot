package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbServicePoints;
import com.src.common.entity.Userinfo;

public interface TbServicePointsService extends
		BaseService<TbServicePoints, Long> {
	
	public TbServicePoints searchOne(TbServicePoints t);
	
	TbServicePoints findPointByUserANDPhoneANDNameANDId(
            String loginUser, String Phone, String tspName, String tspId);
	
	public HashMap<String, Object> findForJson(HashMap<String, String> params);
	
	public List<TbServicePoints> selectList(HashMap<String, String> params);

	public TbServicePoints saveAndRet(TbServicePoints t);
	
	String getMaxId();

	public Map<String, Object> getByCompanyId(Long tcId);

	public TbServicePoints findOne(TbServicePoints tbServicePointsNum);

	public List<TbServicePoints> findByCompanyId(Long tcId);

	public Map<String, Object> getByparam(Map<String, String> param);

	public List<Map<String, Object>> findEmpByPointId(String pointsId);

	public void updatePoint(TbServicePoints tbServicePoints, Userinfo pointsUser)throws Exception;
}
