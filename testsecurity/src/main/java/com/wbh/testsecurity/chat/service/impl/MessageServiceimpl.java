package com.wbh.testsecurity.chat.service.impl;

import com.wbh.testsecurity.chat.entity.Message;
import com.wbh.testsecurity.chat.entity.RMessage;
import com.wbh.testsecurity.chat.mapper.MessageMapper;
import com.wbh.testsecurity.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("messageService")
public class MessageServiceimpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Override
    public void test(Long id){
        System.out.println(messageMapper.cnt(new RMessage(id)));
    }
    @Override
    public boolean delete2(RMessage rMessage){
        return messageMapper.delete2(rMessage);
    }

    @Override
    public int cnt(Long id){
        return messageMapper.cnt(new RMessage(id));
    }
    @Override
    public boolean add(Message message){
        return messageMapper.add(message);
    }
    @Override
    public Message find1(Message message){
        return messageMapper.find1(message);
    }
    @Override
    public RMessage find2byid(RMessage rMessage){
        return messageMapper.find2byid(rMessage);
    }
    @Override
    public boolean delete1(Message message){
        try {
            messageMapper.delete1(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Override
    public boolean addR(RMessage rMessage){
        return messageMapper.addR(rMessage);
    }
    @Override
    public void cntR(Long id){
        Message message = find1(new Message(id));
        message.addcnt();
        messageMapper.update(message);
    }
    @Override
    public List<RMessage> find2(Message message){//在2中找回复1的内容，然后在2中递归寻找回复
        List<RMessage> list1 = messageMapper.find2in1(message);
        List<RMessage> list = new ArrayList<>();
        for (int i=0; i<list1.size(); i++){
            System.out.println(list1.get(i).getMessage());
            list.addAll(dfs(list1.get(i)));
        }
        return list;
    }

    public List<RMessage> dfs(RMessage root){
//        System.out.println(root.getId());
//        System.out.println(messageMapper.cnt(root));
        List<RMessage> sons;
        List<RMessage> ans = new ArrayList<>();
        ans.add(root);
//        System.out.println(ans.get(0).getId());
        if(messageMapper.cnt(root)>0){
            sons=messageMapper.find2in2(root);
            for (int i=0; i<sons.size(); i++){
//                System.out.println(sons.get(i).getMessage());
                if ( messageMapper.cnt( sons.get(i) )>0 ){
                    ans.addAll(dfs(sons.get(i)));
                }else{
                    ans.add(sons.get(i));
                }
            }
        }
        return ans;
    }
    @Override
    public boolean findcnt(RMessage rMessage){
        return messageMapper.findcnt(rMessage) > 0;
    }
    @Override
    public RMessage finddad(RMessage rMessage){
        return messageMapper.finddad(rMessage);
    }
    @Override
    public boolean setnull(RMessage rMessage){
        return messageMapper.setnull(rMessage);
    }
}
