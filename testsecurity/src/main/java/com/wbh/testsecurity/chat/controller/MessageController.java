package com.wbh.testsecurity.chat.controller;

import com.wbh.testsecurity.chat.entity.Message;
import com.wbh.testsecurity.chat.entity.RMessage;
import com.wbh.testsecurity.chat.entity.User;
import com.wbh.testsecurity.chat.function.SnowFlake;
import com.wbh.testsecurity.chat.function.Time;
import com.wbh.testsecurity.chat.service.MessageService;
import com.wbh.testsecurity.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/chat")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/test")
    public void test(Long id){
        messageService.test(id);
    }
    @RequestMapping(value = "/cast")
    public String cast(String message){
        Message message1 = new Message(message);
        User user = getUser();
        message1.setOwner(user.getId());
        SnowFlake snowFlake = SnowFlake.getInstance(0, 0);
        message1.setId(snowFlake.getid());
        message1.setTime(Time.getNow().toString());
        return messageService.add(message1) ? "success" : "fail";
    }
    @RequestMapping(value = "/delete")
    public String delete(Long id){
//        Message message = new Message(id);
//        Message message1 = messageService.find1(message);
//        if (message1.getId()!=0){//main
//            return messageService.delete1(message) ? "success" : "fail";
//        }
//        return "1";
        Message message = messageService.find1(new Message(id));
        if (messageService.cnt(id)==0){
            return messageService.delete1(message) ? "success" : "fail";
        }
        List<RMessage> list = messageService.find2(new Message((id)));
        for (int i=list.size()-1; i>=0; i--){
            messageService.delete2(list.get(i));
        }
        messageService.delete1(message);
        return "success";
    }
    @RequestMapping(value = "/reply")
    public String reply(Long id, String message){
        messageService.cntR(id);
        return newReply(id, message);
    }
    @RequestMapping(value = "/reply2")
    public String reply2(Long id, String message){
        RMessage temp = new RMessage(id);
        temp=messageService.find2byid(temp);
//        System.out.println(messageService.findcnt(temp));
        while(messageService.findcnt(temp)){//owner in m-2
//            System.out.println(temp.getReplyfrom());
            temp.setId(temp.getReplyfrom());
            temp = messageService.find2byid(temp);
//            System.out.println(temp.getId());
        }
        messageService.cntR(temp.getReplyfrom());
        return newReply(id, message);
    }

    private String newReply(Long id, String message) {
        RMessage rMessage = new RMessage(id, message);
        User user = getUser();
        rMessage.setOwner(user.getId());
        SnowFlake snowFlake = SnowFlake.getInstance(0, 0);
        rMessage.setId(snowFlake.getid());
        rMessage.setTime(Time.getNow().toString());
        return messageService.addR(rMessage) ? "success" : "fail";
    }

    @RequestMapping(value = "/show")
    public String show(Long id){
        Message message = new Message(id);
        Message mainMessage = messageService.find1(message);
        return mainMessage.getMessage();
    }
    @RequestMapping(value = "/showAll")
    public String showAll(Long id){
        List<RMessage> list = messageService.find2(new Message((id)));
        Message message = messageService.find1(new Message(id));
        String messages = userService.findId(message.getOwner()).getNickname()+":"+message.getMessage()+"<br>";
        for (int i=0; i<list.size(); i++){
            if (list.get(i).getMessage()!=null) {
                messages += "&nbsp" + userService.findId(list.get(i).getOwner()).getNickname()
                        + " reply " + getOwner(list.get(i)) +
                        ":" + list.get(i).getMessage() + "<br>";
            }
        }
        return messages;
    }
    private String getOwner(RMessage rMessage){//从1或2中找到id的所属者
        if (messageService.findcnt(rMessage)){//in 2
            RMessage message = messageService.find2byid(new RMessage(rMessage.getReplyfrom()));
            return userService.findId(message.getOwner()).getNickname();
        }else{//in 1
            Message message = messageService.find1(new Message(rMessage.getReplyfrom()));
            return userService.findId(message.getOwner()).getNickname();
        }
    }
    @RequestMapping(value = "setnull")
    public String setnull(Long id){//检查用户权限
        User user = getUser();
        if (!Objects.equals(user.getId(), messageService.find2byid(new RMessage(id)).getOwner())){
            return "Not your message!";
        }
        return messageService.setnull(new RMessage(id)) ? "success" : "fail";
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();
        return userService.findUser(currentUsername);
    }
}
