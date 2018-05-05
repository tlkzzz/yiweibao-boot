package com.src.common.service;

import com.src.common.base.service.BaseService;
import com.src.common.entity.UserEntity;
import org.apache.catalina.User;

import java.util.HashMap;
import java.util.List;

public interface UserService extends BaseService<UserEntity, Long> {

    //登陆查询
    public List<UserEntity> findByLoginName(String u);
    //修改
    public UserEntity edit(UserEntity u);
    //id查询
    public UserEntity findById(Long id);
    //新增
    public UserEntity add(UserEntity u);
    //条件查询
    public List<UserEntity> findByAll(UserEntity u);
    //分页
    public HashMap<String, Object> findForJson(HashMap<String, String> params);
}
