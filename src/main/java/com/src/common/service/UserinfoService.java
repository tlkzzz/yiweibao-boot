package com.src.common.service;


import com.src.api.entity.TbCustomers;
import com.src.common.base.entity.PageBean;
import com.src.common.base.service.BaseService;
import com.src.common.entity.Userinfo;

import java.util.HashMap;
import java.util.List;

public interface UserinfoService extends BaseService<Userinfo, Long> {

	/**
	 * 分页查询
	 * @param params 条件参数
	 * @param pageBean 
	 * @return
	 */
	PageBean<Userinfo> findByPage(Userinfo params, PageBean<Userinfo> pageBean);
	
	Userinfo findUserinfo(Userinfo searchParams);
	
	List<Userinfo> findAll(Userinfo searchParams);
	
	HashMap<String, Object> findForJson(HashMap<String, String> params);
	HashMap<String, Object> findForJson1(HashMap<String, String> params);
	
	List<Userinfo> findUserGroup();
	
	Userinfo findUserinfoById(Long id);
	
	Userinfo findUserByName(String account);

	Userinfo findUserByNameANDGroupId(String userName, String company_groupId);

	void updateMemberStatus(Userinfo search, TbCustomers tbCustomers)throws Exception;
}
