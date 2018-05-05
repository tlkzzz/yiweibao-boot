package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbQrcode;

public interface TbQrcodeService extends BaseService<TbQrcode, Long>{
	
	
		/**
		 * 新增
		 * @param t
		 * @return
		 */
	TbQrcode add(TbQrcode t);
		/**
		 * 修改
		 * @param t
		 * @return
		 */
	TbQrcode edit(TbQrcode t);
		/**
		 * 删除
		 * @param id
		 * @return
		 */
		int del(Long id);
		/**
		 * 条件查询
		 * @param t
		 * @return
		 */
		List<TbQrcode> search(TbQrcode t);


	public Map<String, Object> getByParam(Map<String, String> param);

}
