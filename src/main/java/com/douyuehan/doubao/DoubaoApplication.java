package com.douyuehan.doubao;

import com.douyuehan.doubao.jwt.JwtAuthenticationFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.douyuehan.doubao.mapper")
public class DoubaoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    @Bean //自动被初始化
    public FilterRegistrationBean jwtFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();//注册filter对象
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();//创建过滤器对象
        registrationBean.setFilter(filter);
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(DoubaoApplication.class, args);
    }

}
