package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbServiceFees;

public interface TbServiceFeesService extends BaseService<TbServiceFees, Long>{


	Map<String, Object> getByCompanyId(Long tcId);

	List<Map<String, Object>> findByCompany(Long tcId);

	Map<String, Object> getByParam(Map<String, String> param);

	TbServiceFees findOne(TbServiceFees tbServiceFeesOne);

	TbServiceFees findByNameAndFeesId(String tsfName, String feeId, String companyId);

	/** 
	 * @Title: findRelate 
	 * @Description:
	 * @param @param tsfIds    
	 * @return void    返回类型 
	 * @throws 
	 */
	List<Map<String, Object>> findRelate(String tsfIds);

}
