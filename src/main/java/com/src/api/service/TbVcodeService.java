package com.src.api.service;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbVcode;


public interface TbVcodeService extends BaseService<TbVcode, Long>{

	void saveOrUpdate(String mobile, String code, String type);
	
	/**
	 * 
	* @Title: checkCode 
	* @Description: 验证通过返回1  过期返回0  错误返回-1 
	* @param @param mobile
	* @param @param captcha
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	int checkCode(String mobile, String captcha);
}
