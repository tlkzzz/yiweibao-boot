package com.src.api.service;

import java.util.HashMap;
import java.util.List;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbCompanyCustomer;
import com.src.api.entity.TbCustomers;
import com.src.common.entity.ShiroUser;
import com.src.common.entity.Userinfo;

public interface TbCustomersService extends BaseService<TbCustomers, Long> {
	public HashMap<String, Object> findForJson(HashMap<String, String> params);
	
	public TbCustomers searchOne(TbCustomers t);
	
	public List<TbCustomers> selectList(String tcServicePoint);
	
	public List<TbCustomers> findLoginUser();
	
	public String getMaxId();
	
	public TbCustomers searchOne(String acount);

	public HashMap<String, Object> findForJsonOfMember(
            HashMap<String, String> params);

	public void saveMember(TbCustomers userinfo, Userinfo userinfo2, ShiroUser loginUser, TbCompanyCustomer tbCompanyCustomer)throws Exception;

	public void updateMemberStatus(TbCustomers tbcustomers, Userinfo userinfo)throws Exception;


	public HashMap<String, Object> findForJsonMemberOrder(
            HashMap<String, String> params);

	public TbCustomers findOne(TbCustomers tbCustomers);

	public void saveCustomer(TbCustomers tbCustomer, Userinfo customerUser, TbCompanyCustomer companyCustomer)throws Exception;
	/**
	 * 批量删除
	 * @param arrTcIds
	 * @return
	 * @throws Exception
	 */
	public String deleteCustomersByIds(String[] arrTcIds)throws Exception;
	/**
	 * 获取单位下是否有同一帐号
	 * @param tcId
	 * @param tcLoginUser
	 * @return
	 */
	public TbCustomers findCustomerByAccountAndCompanyId(Long tcId,
                                                         String tcLoginUser);
	/**
	 * 重置密码
	 * @param customers
	 * @param customerUser
	 * @throws Exception
	 */
	public void updateMemberPass(TbCustomers customers, Userinfo customerUser)throws Exception;
	
}
