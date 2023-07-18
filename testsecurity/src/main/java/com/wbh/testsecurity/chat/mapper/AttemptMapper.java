package com.wbh.testsecurity.chat.mapper;

import com.wbh.testsecurity.chat.entity.Attempt;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttemptMapper {
    /**更新登录记录*/
    boolean update(Attempt attempt);

    /**新增登录记录*/
    boolean insert(Attempt attempt);

    /**根据用户id查找登录记录*/
    Attempt find(Attempt attempt);
}
