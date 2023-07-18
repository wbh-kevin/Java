package com.wbh.testsecurity.chat.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FailureHandlerImpl implements AuthenticationFailureHandler {
    private String url;

    public FailureHandlerImpl(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException, ServletException, IOException {
        String name = httpServletRequest.getParameter("username_update");
        url= "http://localhost:8081/index/wrongP?name="+name;
        httpServletResponse.sendRedirect(url);
//        httpServletRequest.getRequestDispatcher(url).forward(httpServletRequest,httpServletResponse);
    }
}
