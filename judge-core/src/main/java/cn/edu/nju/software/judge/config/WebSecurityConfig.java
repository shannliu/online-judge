package cn.edu.nju.software.judge.config;

import cn.edu.nju.software.judge.aop.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 登录配置
 * Created by zhangkun 2018-12-26
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AccessInterceptor accessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(accessInterceptor).addPathPatterns("/loginOut.do");
    }
}
