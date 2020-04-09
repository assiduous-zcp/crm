package com.shsxt.crm.config;

import com.shsxt.crm.interceptors.NoLoginInterceptors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public NoLoginInterceptors noLoginInterceptors(){
        return new NoLoginInterceptors();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noLoginInterceptors())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/index","/static/**");
    }
}
