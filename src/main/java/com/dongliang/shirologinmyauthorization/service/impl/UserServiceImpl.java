package com.dongliang.shirologinmyauthorization.service.impl;

import com.dongliang.shirologinmyauthorization.service.UserService;
import com.dongliang.shirologinmyauthorization.mapper.UsersMapper;
import com.dongliang.shirologinmyauthorization.pojo.Users;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author SAKURA
 * @create 2020/6/18 13:01
 * 测试自定义Realm
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UsersMapper usersMapper;

    @Resource
    private SecurityManager securityManager;

    @Override
    public Boolean login(Users users) throws AuthenticationException {


        SecurityUtils.setSecurityManager(this.securityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(users.getUsername(),users.getPassword()));

        if ( subject.isPermitted("users:delete")) {
            System.out.println(users.getUsername()+"具有users:delete权限");
        }else {
            System.out.println(users.getUsername()+"没有users:delete权限");
        }
        return subject.isAuthenticated();

    }



}
