package com.douyuehan.doubao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.douyuehan.doubao.mapper")
public class DoubaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoubaoApplication.class, args);
    }

}
