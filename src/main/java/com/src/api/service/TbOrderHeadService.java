package com.src.api.service;

import java.util.HashMap;
import java.util.List;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbCustomers;
import com.src.api.entity.TbMachine;
import com.src.api.entity.TbOrderHead;

public interface TbOrderHeadService extends BaseService<TbOrderHead, Long> {

	public HashMap<String, Object> findForJson(HashMap<String, String> params) ;
	
	public TbOrderHead searchOne(String tohId);
	
	public List<TbOrderHead> selectList(String tcId);
	
	public Boolean canDelete(TbMachine t);
	
	public Boolean canDelete(TbCustomers t);
}
