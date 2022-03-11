package com.kabunx.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.kabunx.app.mapper")
@EnableAsync
public class LaborerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaborerApplication.class, args);
    }
}
