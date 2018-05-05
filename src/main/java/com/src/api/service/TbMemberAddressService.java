package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbAddress;

public interface TbMemberAddressService extends BaseService<TbAddress, Long>{

	/**
	 * 查询会员收货地址信息
	 * @return
	 */
	List<Map<String, Object>> findByStatus(Long tmId);

	/**
	 * 查询会员地址详情
	 * @param tmaId
	 * @return
	 */
	List<Map<String, Object>> getById(Long tmaId);

	/**
	 * 去掉以前的默认地址   然后加入新的默认地址
	 * @param tbMemberAddress
	 */
	void upAddressDefault(TbAddress tbMemberAddress);
	/**
	 * 设置默认地址
	 * @param tbMemberAddress
	 * @throws Exception
	 */
	void updateAddressDefault(TbAddress tbMemberAddress)throws Exception;

}
