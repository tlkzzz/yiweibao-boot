/**    
* @{#} TbCustMachineServiceImpl.java Create on 2015年9月24日 下午7:33:52    
* Copyright (c) 2015.    
*/     
package com.src.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbCustMachineDao;
import com.src.api.entity.TbCustMachine;
import com.src.api.service.TbCustMachineService;

/**     
* @author <a href="mailto:liwei.fly@gmail.com">author</a>    
* @version 1.0     
* @description
*/
@Service("tbCustMachineService")
public class TbCustMachineServiceImpl extends BaseServiceImpl<TbCustMachine, Long> implements TbCustMachineService{

	@Resource 
	TbCustMachineDao tbCustMachineDao;
	
	@Override
	public TbCustMachine searchOne(TbCustMachine t) {
		// TODO Auto-generated method stub
		return tbCustMachineDao.searchOne(t)
				;
	}

	@Override
	public BaseDao<TbCustMachine, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbCustMachineDao;
	}

}
