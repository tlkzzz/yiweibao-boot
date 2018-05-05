package com.src.common.web;


import com.src.api.entity.TbCompany;
import com.src.api.entity.TbServicePoints;
import com.src.api.service.TbCompanyService;
import com.src.api.service.TbServicePointsService;
import com.src.common.entity.UserEntity;
import com.src.common.entity.Userinfo;
import com.src.common.service.UserinfoService;
import com.src.common.shiro.config.JWTUtil;

import com.src.common.service.UserService;
import com.src.common.shiro.config.ResponseRestful;
import com.src.common.utils.StringUtil;
import com.src.common.utils.hjtech.util.LogTool;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value="/login")
public class LoginController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Resource
    UserinfoService userinfoService;
    @Resource
    TbCompanyService tbCompanyService;
    @Resource
    TbServicePointsService tbServicePointsService;
    @Autowired
    UserService userServiceImpl;

    /**
     * 登陆
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping("/sigin")
    public ResponseRestful sigin(@RequestParam("loginName") String loginName, @RequestParam("password") String password) {
        //根据登陆账号查询用户信息
        List<UserEntity> list = this.userServiceImpl.findByLoginName(loginName);
        if(list.size()==1){
            if(list.get(0).getPassword().equals(password.trim())){
                return new ResponseRestful(200, "Login success", JWTUtil.sign(loginName, password));
            }else{
//                throw new UnauthorizedException();
                return new ResponseRestful(100, "密码错误", null);
            }
        }else {
            return new ResponseRestful(100, "用户不存在", null);
        }

    }

    /**
     * 用户有没有登陆都能访问
     * @return
     */
    @GetMapping("/article")
    public ResponseRestful article(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
          String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
        System.out.println(name);
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseRestful(200, "You are 已经登陆", null);
        } else {
            return new ResponseRestful(200, "You are 没有登陆", null);
        }
    }

    /**
     * 用户有没有登陆都能访问
     * @return
     */
    @GetMapping("/add")
    public ResponseRestful add() {
       return new ResponseRestful(200, "You are 有没有登陆呀", null);
    }



    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseRestful unauthorized() {
        return new ResponseRestful(401, "Unauthorized", null);
    }
    /**
     * 登陆
     * @param loginName
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/signin",method = RequestMethod.POST)
    public ResponseRestful signin(@RequestParam("loginName") String loginName, @RequestParam("password") String password) {
        LOGGER.info("进入singin方法啦");

        try {
            int ss=9;
            Userinfo userinfo = null;
            //判断用户名是否存在
            userinfo = new Userinfo();
            userinfo.setAccount(loginName);
            userinfo.setGroupId(9);//企业
            userinfo.setDeleteMark(1);
            userinfo = userinfoService.findUserinfo(userinfo);
            if(userinfo==null){
                userinfo = new Userinfo();
                userinfo.setAccount(loginName);
                userinfo.setGroupId(2);//网点
                userinfo.setDeleteMark(1);
                userinfo = userinfoService.findUserinfo(userinfo);
                ss=2;
               // userinfo.setGroupId(2);
                if(userinfo==null){
                    return new ResponseRestful(100, "用户不存在", null);
                }
            }
            //判断密码是否正确
            if(!userinfo.getPassword().equals(StringUtil.getMD5Str(password))){
                return new ResponseRestful(100, "密码错误", null);
            }
            TbCompany tbCompanyBytcLoginUser = null;
            if(ss==9){//单位
                tbCompanyBytcLoginUser = new TbCompany();
                tbCompanyBytcLoginUser.setTcLoginUser(loginName);
                tbCompanyBytcLoginUser.setTcStatus(1);
                tbCompanyBytcLoginUser = tbCompanyService.findOne(tbCompanyBytcLoginUser);
            }else {
                TbServicePoints tbServicePoints = new TbServicePoints();
                tbServicePoints.setTspLoginUser(loginName);
                tbServicePoints.setTspStatus(1);
                tbServicePoints = tbServicePointsService.findOne(tbServicePoints);
                if(tbServicePoints == null){
                    return new ResponseRestful(100, "用户不存在", null);
                }
                tbCompanyBytcLoginUser = tbCompanyService.findById(tbServicePoints.getTspCompanyId());
            }

            if(tbCompanyBytcLoginUser==null){
                return new ResponseRestful(100, "用户不存在", null);
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("token",JWTUtil.sign(tbCompanyBytcLoginUser.getTcLoginUser(), tbCompanyBytcLoginUser.getTcLoginPass()));
            map.put("uInfo",tbCompanyBytcLoginUser);

            return new ResponseRestful(200, "登陆成功",map);

        } catch (Exception e) {
            LogTool.WriteLog("[loginAuth]错误，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100, "登陆异常请稍后再试", null);
        }
    }
}
