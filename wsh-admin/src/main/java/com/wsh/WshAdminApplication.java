package com.wsh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wsh.mapper")
public class WshAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(WshAdminApplication.class, args);
    }
}
