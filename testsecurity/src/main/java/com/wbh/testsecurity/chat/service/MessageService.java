package com.wbh.testsecurity.chat.service;

import com.wbh.testsecurity.chat.entity.Message;
import com.wbh.testsecurity.chat.entity.RMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    /**发布信息*/
    boolean add(Message message);

    /**通过id寻找发布的信息*/
    Message find1(Message message);

    /**通过id寻找发布的信息的所有回复*/
    List<RMessage> find2(Message message);

    /**删除发布的信息*/
    boolean delete1(Message message);//需要级联删除

    /**添加对回复的回复*/
    boolean addR(RMessage rMessage);

    /**更新发布信息的回复数*/
    void cntR(Long id);

    /**统计一个信息有多少回复*/
    int cnt(Long id);

    /**测试用*/
    void test(Long id);

    /**检查该记录回复的是否是回复*/
    boolean findcnt(RMessage rMessage);

    RMessage finddad(RMessage rMessage);

    /**通过回复信息的id寻找该信息*/
    RMessage find2byid(RMessage rMessage);

    /**删除回复中的记录*/
    boolean delete2(RMessage rMessage);

    /**回复置空*/
    boolean setnull(RMessage rMessage);
}
