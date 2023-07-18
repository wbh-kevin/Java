package com.wbh.testsecurity.chat.mapper;

import com.wbh.testsecurity.chat.entity.Log;
import com.wbh.testsecurity.chat.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper//指定这是一个操作数据库的mapper
public interface UserMapper {
    /**注册用户*/
    boolean regist(User user);

    /**删除用户*/
    boolean delete(User user);

    /**遍历全部用户*/
    List<User> findAll();

    /**根据用户名查找用户*/
    User findUser(String name);

    /**写登录日志*/
    boolean writeLog(Log log);
    boolean deleteTime(User user);

    /**更新用户信息*/
    boolean update(User user);

    /**通过id查找用户*/
    User findId(Long id);
}