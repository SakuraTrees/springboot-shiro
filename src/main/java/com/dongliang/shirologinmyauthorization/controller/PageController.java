package com.dongliang.shirologinmyauthorization.controller;

import com.dongliang.shirologinmyauthorization.service.impl.UserServiceImpl;
import com.dongliang.shirologinmyauthorization.pojo.Users;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author SAKURA
 * @create 2020/6/18 12:48
 * 测试Shiro的登陆功能
 */
@Controller
public class PageController {
    @Resource
    UserServiceImpl userServiceImpl;

    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping("users/login")
    public  String login(String name,String password){
        Users users = new Users();
        users.setUsername(name);
        users.setPassword(password);
        try {
            Boolean login = userServiceImpl.login(users);
            if (login) {
                return "redirect:/index";
            }
        } catch (AuthenticationException e) {
            return "login";
        }

        return "login";

    }

    @RequestMapping("refuse")
    public String refuse(){
        return "refuse";
    }

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("logout")
    @ResponseBody
    public String logout(){
        return "logout";
    }

    @RequestMapping("/permission")
    @RequiresPermissions("users:delete")
    public String permission(){
        return "permission";
    }

}
