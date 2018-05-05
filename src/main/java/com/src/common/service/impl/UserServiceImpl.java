package com.src.common.service.impl;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.common.dao.UserDao;
import com.src.common.entity.UserEntity;
import com.src.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl  extends BaseServiceImpl<UserEntity, Long> implements UserService{

    @Resource
    UserDao userDaoImpl;



    @Override
    public BaseDao<UserEntity, Long> getGenericDao() {
        return userDaoImpl;
    }

    @Override
    public List<UserEntity> findByLoginName(String loginName) {
        String sql="select * from t_user where login_user=? AND state ='0'";
        List<Object> list=new ArrayList<Object>();
        list.add(loginName);
        //设置有效状态
        return this.userDaoImpl.search(sql,list);
    }

    @Override
    public UserEntity edit(UserEntity u) {
        return null;
    }

    @Override
    public UserEntity findById(Long id) {
        return null;
    }

    @Override
    public UserEntity add(UserEntity u) {
        return null;
    }

    @Override
    public List<UserEntity> findByAll(UserEntity u) {
        return null;
    }

    @Override
    public HashMap<String, Object> findForJson(HashMap<String, String> params) {
        return null;
    }
}
