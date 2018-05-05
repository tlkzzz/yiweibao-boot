package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbMessage;

public interface TbMessageServicer extends BaseService<TbMessage, Long>{

	HashMap<String, Object> findForJson(HashMap<String, String> params);

	List<Map<String, Object>> findCustomers(String account);

	List<Map<String, Object>> findEmployee(String account);

	HashMap<String, Object> findForJson1(HashMap<String, String> params);

	/**
	 * 根据消息标识码查询消息
	 * @param tmSign
	 * @return
	 */
	List<TbMessage> findMessage(String tmSign);

	HashMap<String, Object> findForJson2(HashMap<String, String> params);
	
	/**
	 * 根据消息标识码删除消息
	 * @param tmSign
	 */
	void delBySign(String tmSign);

	TbMessage findOne(TbMessage message);

	/**
	 * 查询消息表所有信息
	 */
	HashMap<String, Object> findForJsonOfMessage(HashMap<String, String> params, HttpSession session);

	/**
	 * 查询接收消息人的详细信息
	 * @param params
	 * @param session 
	 * @return
	 */
	HashMap<String, Object> findForJsonOfMessageDetail(HashMap<String, String> params);

	/**
	 * 根据接收人类型查询接收人姓名
	 * @param personType
	 * @return
	 */
	List<Map<String, Object>> findPersonNameByType(String personType, String tcId);

	TbMessage findTbMessage(TbMessage message);


}
