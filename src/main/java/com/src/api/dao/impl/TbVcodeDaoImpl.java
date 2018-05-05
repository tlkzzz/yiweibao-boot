package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbVcodeDao;
import com.src.api.entity.TbVcode;


@Repository("tbVCodeDao")
public class TbVcodeDaoImpl extends BaseDaoMysqlImpl<TbVcode, Long>
implements TbVcodeDao{

	TbVcodeDaoImpl(){
		super(TbVcode.class);
	}
}
