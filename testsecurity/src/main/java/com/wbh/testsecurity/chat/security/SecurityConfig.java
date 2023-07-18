package com.wbh.testsecurity.chat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                        .antMatchers("/index/autodelete").hasAuthority("admin");
        http.authorizeRequests()
                        .antMatchers("/index/wrongP").permitAll();
//        http.authorizeRequests()
//                .antMatchers("/index/findUser_writeLog_Handle").hasAuthority("admin");

        http.formLogin()
                //自定义的登录页面
                .loginPage("/login.html")
                //当发现是/login请求时，会去UserDetailsServiceImpl进行处理
                .loginProcessingUrl("/login")
                //当登录成功后会去跳转的页面，因为需登录要post请求，而直接跳页面是get请求
                // 所以需要去controller实现跳转
//                .successForwardUrl("/toMain")
                .successHandler(new SuccessHandlerImpl("localhost:8081/index/findUser_writeLog_Handle"))
                //与成功一样进行设置即可，需要去controller实现跳转，
                // 记得下面需要对error.html开放授权，否则还需要去登录哦
//                .failureForwardUrl("/toError")
                .failureHandler(new FailureHandlerImpl("/toError"))
                //设置用户名
                .usernameParameter("username_update")
                //设置用户密码
                .passwordParameter("password_update");

        //授权认证
        http.authorizeRequests()
                //若为login.html则不需要进行验证
                .antMatchers("/login.html").permitAll()
                .antMatchers("/error.html").permitAll()
                //所有请求必须认证（上面进行设置过的除外）
                .anyRequest().authenticated();

        //将csrf关掉，之后会说
        http.csrf().disable();
    }
}
