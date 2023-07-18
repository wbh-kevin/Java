package com.wbh.testsecurity.chat.security;

import com.wbh.testsecurity.chat.controller.UserController;
import com.wbh.testsecurity.chat.entity.User;
import com.wbh.testsecurity.chat.mapper.UserMapper;
import com.wbh.testsecurity.chat.service.UserService;
import com.wbh.testsecurity.chat.service.impl.UserServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessHandlerImpl implements AuthenticationSuccessHandler {
    private String url;

    public SuccessHandlerImpl(String url) {
        this.url = url;
    }

    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication  登录成功后的用户信息
     * @return void
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String name = userDetails.getUsername();
        url = "http://localhost:8081/index/findUser_writeLog_Handle?name="+name;
        httpServletResponse.sendRedirect(url);
    }
}
