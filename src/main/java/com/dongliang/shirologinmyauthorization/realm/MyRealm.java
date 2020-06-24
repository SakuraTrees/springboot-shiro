package com.dongliang.shirologinmyauthorization.realm;

import com.dongliang.shirologinmyauthorization.mapper.UsersMapper;
import com.dongliang.shirologinmyauthorization.pojo.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;

/**
 * @Author SAKURA
 * @create 2020/6/20 13:37
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UsersMapper usersMapper;

    /**
     * 返回自定义realm的名称
     * @return
     */
    @Override
    public String getName() {
        return "MyRealm";
    }

    /**
     * 进行身份授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(MyRealm.class);

        //获取用户名
        if (principals.isEmpty()){
            return null;
        }
        String username = principals.getPrimaryPrincipal().toString();

        //查询该用户权限
        Users users = usersMapper.selByUsername(username);
        logger.info(username+"--->"+users.getPermissions());

        //给该用户授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(users.getPermissions());
        return info;
    }

    /**
     * 进行身份验证
     * @param token 身份信息
     * @return  认证信息
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Logger logger = LogManager.getLogger(MyRealm.class);

        //获取用户名和密码
        if(token == null && token.getPrincipal()==null){
            return null;
        }
        String username = token.getPrincipal().toString();

        //在数据库进行查询
        Users users = usersMapper.selByUsername(username);
        if (users != null) {
            logger.info(users.getUsername() + "--->" + users.getPassword());
            return new SimpleAuthenticationInfo(users.getUsername(),users.getPassword(),
                    ByteSource.Util.bytes(users.getPasswordSalt()),this.getName());
        }else{
            return null;
        }
    }
}
