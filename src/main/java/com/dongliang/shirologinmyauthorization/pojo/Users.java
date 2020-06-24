package com.dongliang.shirologinmyauthorization.pojo;

import java.util.List;

/**
 * @Author SAKURA
 * @create 2020/6/18 12:50
 * 简单的用户实现
 */
public class Users {
    private String username;
    private String password;
    private String passwordSalt;
     private List<String> permissions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
