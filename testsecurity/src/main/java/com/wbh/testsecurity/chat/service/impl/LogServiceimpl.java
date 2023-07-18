package com.wbh.testsecurity.chat.service.impl;

import com.wbh.testsecurity.chat.entity.Log;
import com.wbh.testsecurity.chat.function.Time;
import com.wbh.testsecurity.chat.mapper.LogMapper;
import com.wbh.testsecurity.chat.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("logService")
public class LogServiceimpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public Log getLastTime(Long id){
        try {
            Log log = logMapper.getLastTime(id);
            return log.getTime() == null ? new Log(id, Time.getOrigin().toString()) : log;
        }catch (Exception e){
            return new Log(id, Time.getOrigin().toString());
        }
    }
    @Override
    public boolean release(String time){
        try{
            return logMapper.release(time);
        }catch (Exception e){
            return false;
        }
    }
}
