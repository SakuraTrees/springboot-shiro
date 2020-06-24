package com.dongliang.shirologinmyauthorization.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author SAKURA
 * @create 2020/6/23 19:08
 * 因为注解验证权限报错  通过全局异常捕获进行异常跳转
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(AuthorizationException ex, Model model){
        model.addAttribute("ex",ex.toString());
        return "refuse";
    }
}
