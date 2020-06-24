package com.dongliang.shirologinmyauthorization.service;

import com.dongliang.shirologinmyauthorization.pojo.Users;
import org.apache.shiro.authc.AuthenticationException;

/**
 * @Author SAKURA
 * @create 2020/6/18 13:00
 * 处理用户信息
 */
public interface UserService {

    Boolean login(Users users) throws AuthenticationException;

}
