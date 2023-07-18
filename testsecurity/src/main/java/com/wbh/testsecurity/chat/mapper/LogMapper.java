package com.wbh.testsecurity.chat.mapper;

import com.wbh.testsecurity.chat.entity.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    /**获取最近一次写入时间*/
    Log getLastTime(Long id);

    /**释放该事件之前的日志*/
    boolean release(String time);
}
