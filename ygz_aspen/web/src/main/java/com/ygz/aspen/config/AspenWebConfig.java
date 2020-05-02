package com.ygz.aspen.config;

import com.ygz.aspen.context.AspenHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AspenWebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter(){
        return new WebMvcConfigurer(){
            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new AspenHandlerInterceptor());
            }
        };
    }

}