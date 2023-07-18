package com.wbh.testsecurity.chat.service;

import com.wbh.testsecurity.chat.entity.Attempt;
import org.springframework.stereotype.Service;

@Service
public interface AttemptService {
    /**添加登录记录，如果有记录则更新*/
    boolean addAtt(Attempt attempt);
    boolean setAtt(Attempt attempt);

    /**查找登录记录*/
    Attempt findAtt(Attempt attempt);
}
