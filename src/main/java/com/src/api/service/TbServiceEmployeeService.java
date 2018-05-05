package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbServiceEmployee;
import com.src.api.entity.TbServicePoints;

public interface TbServiceEmployeeService extends
		BaseService<TbServiceEmployee, Long> {

	HashMap<String, Object> findForJson(HashMap<String, String> params);
	
	TbServiceEmployee searchOne(TbServiceEmployee t);
	
	public List<TbServiceEmployee> selectItem(TbServiceEmployee t);
	
	public Boolean canDelete(TbServicePoints t);
	
	
	// add by paul 2014-11-30
	public TbServiceEmployee searchById(long id);
	
	public String getMaxId();
	
	public List<TbServiceEmployee> getWorkList(String ServicePointId, int type);
	
	public List<TbServiceEmployee> queryEmployeeByFitingOrder(String orderid);

	TbServiceEmployee findOne(TbServiceEmployee searchPars1);

	Map<String, Object> getByCompanyId(Long tcId);

	HashMap<String, Object> findEmployee(HashMap<String, String> params);

	Map<String, Object> getByparam(Map<String, String> param);

	List<TbServiceEmployee> findByNumberAndCompany(String tseNumber, Long tcId);

	List<TbServiceEmployee> findByMobileAndCompany(String tseMobile, Long tcId);
}
