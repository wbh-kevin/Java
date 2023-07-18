package com.wbh.testsecurity.chat.service;

import com.wbh.testsecurity.chat.entity.Log;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
    /**获取最近一次写入时间*/
    Log getLastTime(Long id);

    /**释放该事件之前的日志*/
    boolean release(String time);
}
