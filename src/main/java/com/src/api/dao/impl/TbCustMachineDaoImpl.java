/**    
* @{#} TbCustMachineDaoImpl.java Create on 2015年9月24日 下午7:18:27    
* Copyright (c) 2015.    
*/
package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbCustMachineDao;
import com.src.api.entity.TbCustMachine;

/**
 * @author <a href="mailto:liwei.fly@gmail.com">author</a>
 * @version 1.0
 * @description
 */
@Repository("tbCustMachineDao")
public class TbCustMachineDaoImpl extends BaseDaoMysqlImpl<TbCustMachine, Long>implements TbCustMachineDao {

	public TbCustMachineDaoImpl() {
		super(TbCustMachine.class);
	}
}
