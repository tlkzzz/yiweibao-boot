/**    
* @{#} TbInformationCataService.java Create on 2015��8��17�� ����3:44:44    
* Copyright (c) 2015.    
*/
package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.entity.PageBean;
import com.src.common.base.service.BaseService;
import com.src.api.entity.TbVersion;

/**
 * @author <a href="mailto:liwei.flyf@gmail.com">author</a>
 * @version 1.0
 * @description
 */
public interface TbVersionService extends BaseService<TbVersion, Integer> {
	PageBean<TbVersion> findByPage(TbVersion params, PageBean<TbVersion> pageBean);

	TbVersion findUserinfo(TbVersion searchParams);

	List<TbVersion> findAll(TbVersion searchParams);

	HashMap<String, Object> findForJson(HashMap<String, String> params);
	public Map<String, Object> getByParam(Map<String, String> param);
	List<TbVersion> findAllUser();
	
	Long getIdByName(int type);
}
