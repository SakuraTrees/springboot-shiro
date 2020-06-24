package com.dongliang.shirologinmyauthorization.mapper;

import com.dongliang.shirologinmyauthorization.pojo.Users;
import org.apache.ibatis.annotations.Param;

/**
 * @Author SAKURA
 * @create 2020/6/20 14:01
 */
public interface UsersMapper {
    /**
     * 根据username查询密码
     * @param username 用户名
     * @return 密码
     */
    Users selByUsername(@Param("username") String username);
}
