package com.src.api.dao.impl;

import com.src.api.dao.TbQrcodeDao;
import com.src.api.entity.TbQrcode;
import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;

@Repository("TbQrcodeDao")
public class TbQrcodeDaoImpl extends BaseDaoMysqlImpl<TbQrcode, Long> implements TbQrcodeDao {
	
	TbQrcodeDaoImpl(){
		super(TbQrcode.class);
	}

}
