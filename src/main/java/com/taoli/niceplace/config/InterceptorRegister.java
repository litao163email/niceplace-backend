package com.taoli.niceplace.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * todo
 * 拦截器 注册
 * @author taoli
 */
@Slf4j
@Component
@Aspect
public class InterceptorRegister implements WebMvcConfigurer {

    @Autowired
    MyInterceptor myInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册MyInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(myInterceptor);
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //排除对一些路径的拦截
        registration.excludePathPatterns("/user/login","/user/register","/user/current");
    }


}
