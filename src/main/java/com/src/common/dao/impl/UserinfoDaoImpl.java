package com.src.common.dao.impl;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.common.dao.UserinfoDao;
import com.src.common.entity.Userinfo;
import org.springframework.stereotype.Repository;


@Repository("userinfoDao")
public class UserinfoDaoImpl extends BaseDaoMysqlImpl<Userinfo, Long> implements
		UserinfoDao {
	
	public UserinfoDaoImpl(){
		super(Userinfo.class);
	}
	
}
