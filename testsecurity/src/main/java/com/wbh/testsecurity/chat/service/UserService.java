package com.wbh.testsecurity.chat.service;

import com.wbh.testsecurity.chat.entity.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface UserService {

    /**注册用户*/
    boolean regist(User user);

    /**删除用户*/
    boolean delete(User user);

    /**定期清除用户*/
    boolean autodelete();//定期清除用户

    /**遍历全部用户*/
    List<User> findAll();

    /**根据用户名查找用户*/
    User findUser(String name);

    /**写登录日志*/
    boolean writeLog(User user);

    /**更新用户信息*/
    boolean update(User user);

    /**通过id查找用户*/
    User findId(Long id);

    /**更新时间*/
    boolean updatetime(Long id);

}