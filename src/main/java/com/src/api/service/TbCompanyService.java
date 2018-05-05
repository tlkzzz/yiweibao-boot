package com.src.api.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbCompany;
import com.src.common.entity.Userinfo;

public interface TbCompanyService extends BaseService<TbCompany, Long>{

	TbCompany findOne(TbCompany tbCompany);

	List<TbCompany> getAll();

	TbCompany findByPhone(String phone);

	void saveCompany(HttpSession session, TbCompany company, Userinfo companyUser)throws Exception;

	List<Map<String, Object>> getById(Long tcId);

	void updatePass(TbCompany tbCompany, Userinfo companyUser)throws Exception;

	Map<String, Object> findCompanyInfo(String string);

	/**
	 * 用于忘记密码和修改密码,编辑信息
	 * @param tbCompany
	 * @param companyUser
	 * @throws Exception
	 */
	void updatePassANDInfo(TbCompany tbCompany, Userinfo companyUser)throws Exception;

	TbCompany findByCode(String tcCode);

	boolean checkCompanyByCode(String tcCode);

	TbCompany findByName(String tcName, String tcId);


}
