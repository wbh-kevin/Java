package com.wbh.testsecurity.chat.mapper;

import com.wbh.testsecurity.chat.entity.Message;
import com.wbh.testsecurity.chat.entity.RMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    /**发布信息*/
    boolean add(Message message);//添加一条m-1

    /**通过id寻找发布的信息*/
    Message find1(Message message);

    /**通过id寻找回复信息*/
    RMessage find2byid(RMessage rMessage);

    /**删除发布的信息*/
    boolean delete1(Message message);//删除一条m-1

    /**对发布的信息添加回复*/
    boolean addR(RMessage rMessage);//对1添加一条回复到2中

    /**更新发布的信息的回复数*/
    void update(Message message);//更新记录

    /**寻找发布的信息的回复*/
    List<RMessage> find2in1(Message message);//查找m-2中回复了m-1的记录

    /**统计一条回复有多少回复*/
    int cnt(RMessage rMessage);//统计m-2中有多少条回复了m-2中该条的记录

    /**统计所有回复了回复的回复*/
    List<RMessage> find2in2(RMessage root);//统计m-2中有多少条回复了m-2中该条的记录

    /**检查该记录是否被回复过*/
    int findcnt(RMessage rMessage);//统计m-2中是否存在该条记录的replyfrom

    /**寻找该记录回复的内容*/
    RMessage finddad(RMessage rMessage);

    /**删除回复的信息*/
    boolean delete2(RMessage rMessage);

    /**回复置空*/
    boolean setnull(RMessage rMessage);
}
