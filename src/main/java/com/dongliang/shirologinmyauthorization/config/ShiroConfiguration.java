package com.dongliang.shirologinmyauthorization.config;

import com.dongliang.shirologinmyauthorization.realm.MyRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author SAKURA
 * @create 2020/6/19 19:41
 * 测试shiro的验证策略
 */
@Configuration
public class ShiroConfiguration {


    /**
     * 配置securityManager
     * 测试自定义Realm
     * @return
     */
    @Bean
    public SecurityManager getSecurityManager(){
        //创建securityManmger
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();

        //设置验证策略   ****需要注意验证策略一定要配置在多realm前面

        manager.setAuthenticator(getAuthenticator());

        manager.setRealm(getRealm());

        return manager;
    }

    /**
     * 这里使用配置的原因是被spring管理的对象是不能new的
     * @return
     */
    @Bean
    public Realm getRealm(){
        MyRealm myRealm = new MyRealm();

        myRealm.setCredentialsMatcher(getCredentialsMatcher());
        return myRealm;
    }


    /**
     * 配置凭证匹配器(加密算法)
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     * HashedCredentialsMatcher说明：
     * 用户传入的token先经过shiroRealm的doGetAuthenticationInfo方法
     * 此时token中的密码为明文。
     * 再经由HashedCredentialsMatcher加密password与查询用户的结果password做对比。
     * new SimpleHash("SHA-256", password, null, 1024).toHex();
     * @return
     */
    @Bean
    public CredentialsMatcher getCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置加密算法
        matcher.setHashAlgorithmName("md5");
        //设置迭代次数
        matcher.setHashIterations(2);
        return matcher;
    }

    /**
     * 设置验证策略  认证器
     */
    @Bean
    public Authenticator getAuthenticator(){
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return authenticator;
    }

    /**
     * 配置shiro过滤器
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFactoryBean(){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(getSecurityManager());
        Map<String,String> map = new HashMap<>(10);

        //设置登陆url  如果用户没有授权会跳转到这里授权
        factoryBean.setLoginUrl("/login");
        //factoryBean.setSuccessUrl();如果不配置自动向下一个url走
        //配置用户没有权限时跳转的页面
        factoryBean.setUnauthorizedUrl("/refuse");
        //登出设置使用 logout过滤器
        map.put("/logout","logout");
        //登陆url 任何人可以访问
        map.put("/users/login","anon");
        map.put("/**","authc");

        factoryBean.setFilterChainDefinitionMap(map);
        return factoryBean;

    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持.
 　　* 使用代理方式;所以需要开启代码支持;
 　　*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(getSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }
}

