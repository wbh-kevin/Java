package com.wbh.testsecurity.chat.service.impl;

import com.wbh.testsecurity.chat.entity.Log;
import com.wbh.testsecurity.chat.entity.User;
import com.wbh.testsecurity.chat.function.Time;
import com.wbh.testsecurity.chat.function.SnowFlake;
import com.wbh.testsecurity.chat.mapper.UserMapper;
import com.wbh.testsecurity.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@Service("userService")
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean regist(User user){
        try{
            return userMapper.regist(user);
        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }
    @Override
    public boolean delete(User user){
        try{
            return userMapper.delete(user);
        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }
    //自动删除一定时间之前的账户
    @Override
    public boolean autodelete() {
        String time = Time.getDelete(1).toString();
//        User user = new User(time);
//        return userMapper.deleteTime(user);
        List<User> users = findAll();
        SnowFlake snowFlake = SnowFlake.getInstance(0, 0);
        for (User user:users){
            if (Objects.equals(user.getRole(), "normal") && Time.comTime(user.getLasttime(), 5)){//自动删除10分钟前注册的账户
                userMapper.delete(user);
            }
        }
        return true;
    }
    //检索所有账户
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
    @Override
    public User findUser(String name){
        try {
            return userMapper.findUser(name);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean writeLog(User user) {
        try {
            String now = Time.getNow().toString();
            Log log = new Log(user.getId(), now);
            return userMapper.writeLog(log);
        }catch (Exception e) {
            return false;
        }
    }
    @Override
    public boolean update(User user){
        try{
            return userMapper.update(user);
        }catch (Exception e){
            return false;
        }
    }
    @Override
    public User findId(Long id){
        return userMapper.findId(id);
    }
    @Override
    public boolean updatetime(Long id){
        User user = findId(id);
        user.setLasttime(Time.getNow().toString());
        return userMapper.update(user);
    }
}