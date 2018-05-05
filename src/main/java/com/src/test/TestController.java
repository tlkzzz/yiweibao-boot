package com.src.test;


import com.src.common.entity.UserEntity;
import com.src.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserService userServiceImpl;

    @RequestMapping(value="/test")
    public String test(){
//    UserEntity u=this.userService.findByLoginUser("admin");
        return "hello";
    }
}
