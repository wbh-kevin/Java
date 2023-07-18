package com.wbh.testsecurity.chat.service.impl;

import com.wbh.testsecurity.chat.entity.Attempt;
import com.wbh.testsecurity.chat.entity.User;
import com.wbh.testsecurity.chat.function.Time;
import com.wbh.testsecurity.chat.mapper.AttemptMapper;
import com.wbh.testsecurity.chat.mapper.UserMapper;
import com.wbh.testsecurity.chat.service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("attemptService")
public class AttemptServiceimpl implements AttemptService {
    @Autowired
    private AttemptMapper attemptMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean addAtt(Attempt attempt){

        try{
            Attempt attempt1 = attemptMapper.find(attempt);
            Long id = attempt1.getId();
            updatetime(id);
            return attemptMapper.update(attempt);
        }catch (Exception e){
            return attemptMapper.insert(attempt);
        }
    }
    @Override
    public boolean setAtt(Attempt attempt){
        updatetime(attempt.getId());
        return attemptMapper.insert(attempt);
    }
    @Override
    public Attempt findAtt(Attempt attempt){
        updatetime(attempt.getId());
        return attemptMapper.find(attempt);
    }
    public boolean updatetime(Long id){
        User user = findId(id);
        user.setLasttime(Time.getNow().toString());
        return userMapper.update(user);
    }
    public User findId(Long id){
        return userMapper.findId(id);
    }
}
