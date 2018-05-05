package com.src.api.service;

import java.util.HashMap;
import java.util.List;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbMachine;

public interface TbMachineService extends BaseService<TbMachine, Long> {

	public TbMachine searchOne(TbMachine t);
	
	public HashMap<String, Object> findForJson(HashMap<String, String> params);
	
	public List<TbMachine> selectList();
	
	public List<TbMachine> canSelectGoods(HashMap<String, String> params);
	
	public List<TbMachine> canSelectGoods();
	
	public String getMaxId();
	
	public Long getMachineByName(String name);
}
