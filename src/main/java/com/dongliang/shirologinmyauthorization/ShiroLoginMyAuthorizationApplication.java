package com.dongliang.shirologinmyauthorization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SAKURA
 * 测试自定义realm
 * 并实现md5加密两次
 */
@SpringBootApplication
@MapperScan("com.dongliang.shirologinmyauthorization.mapper")
public class ShiroLoginMyAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroLoginMyAuthorizationApplication.class, args);
    }

}
