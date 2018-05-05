package com.src.common.shiro;

import com.src.api.entity.TbCompany;
import com.src.api.service.TbCompanyService;
import com.src.common.entity.UserEntity;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实现AuthorizingRealm
 */

@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

//    @Autowired
//    UserService userServiceImpl;
    @Autowired
    TbCompanyService tbCompanyService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得loginName，用于和数据库进行对比
        String loginName = JWTUtil.getLoginName(token);
        if (loginName == null) {
            throw new AuthenticationException("token invalid");
        }
        TbCompany company= this.tbCompanyService.findByPhone(loginName);
        //用户不存在
        if (company==null) {
            throw new AuthenticationException("User didn't existed!");
        }
        //校验token是否正确 也就是验证是登陆状态
        if (! JWTUtil.verify(token, loginName, company.getTcLoginPass())) {
            throw new AuthenticationException("登陆失效啦!~~~");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
