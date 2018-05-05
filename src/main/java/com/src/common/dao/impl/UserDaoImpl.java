package com.src.common.dao.impl;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.common.dao.UserDao;
import com.src.common.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository("UserDao")
public class UserDaoImpl extends BaseDaoMysqlImpl<UserEntity, Long> implements UserDao {

    public UserDaoImpl(){
        super(UserEntity.class);
    }


}
