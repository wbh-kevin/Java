package com.wbh.testsecurity;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wbh.testsecurity.chat.mapper")
public class TestsecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestsecurityApplication.class, args);
    }

}
