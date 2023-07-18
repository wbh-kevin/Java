package com.wbh.testsecurity.chat.controller;

import com.wbh.testsecurity.chat.entity.Attempt;
import com.wbh.testsecurity.chat.entity.Log;
import com.wbh.testsecurity.chat.entity.User;
import com.wbh.testsecurity.chat.function.SnowFlake;
import com.wbh.testsecurity.chat.function.Time;
import com.wbh.testsecurity.chat.security.SecurityConfig;
import com.wbh.testsecurity.chat.service.AttemptService;
import com.wbh.testsecurity.chat.service.LogService;
import com.wbh.testsecurity.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/index")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private AttemptService attemptService;
    @RequestMapping(value = "/regist")//注册用户
    public String regist(String name, String password) throws Exception {
        SnowFlake snowFlake = SnowFlake.getInstance(0, 0);
        long id = snowFlake.getid();
        Attempt attempt = new Attempt(id, Time.getNow().toString(), 0);//添加attempt
        attemptService.addAtt(attempt);
        String time = Time.getNow().toString();
        User user = new User(id, name, password, time);
        if (id == 0){
            return "Account ERROR.\nPlease try again later.";
        }else if (!user.checkPassword(password)){
            return "Weak password, try another stronger.";
        }
        try{
            return userService.regist(user) ? "success" : "Name used, try another name";
        }catch (Exception e){
            return "Name used, try another name";
        }
    }
    @RequestMapping(value = "/delete")//管理员删除用户
    public String delete(String name, String password) {
        User user = new User(name, password);
        try{
            return userService.delete(user) ? "success" : "Name or Password ERROR.";
        }catch (Exception e){
            return "Name or Password ERROR.";
        }
    }
    @RequestMapping(value = "/autodelete")//强制清除用户
    public String autodelete() {
        try{
            return userService.autodelete() ? "success" : "fail";
        }catch (Exception e){
            return "ERROR";
        }
    }
    @RequestMapping(value = "/findP")//查询用户密码
    public String findPassword(String name){
        return userService.findUser(name).getPassword();
    }
    @RequestMapping(value = "/findUser")//查询用户名
    public User findUser(String name){
        return userService.findUser(name);
    }
    @RequestMapping(value = "/findUser_writeLog_Handle")//用户成功登陆
    public String fuwlh(String name) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();
        if (!currentUsername.equals(name)) {
            return "redirect:/main.html";
        }
        User user = userService.findUser(name);
        Attempt attempt = new Attempt(user.getId());//update or insert
        attemptService.addAtt(attempt);
        Log log = logService.getLastTime(user.getId());
        if (Time.comTime(log.getTime(), 5)) {
            userService.writeLog(user);
        }
        return "redirect:/main.html";
    }
    @RequestMapping(value = "/change")//改密码
    public String change(String password){
        User user = new User();
        user.setPassword(password);
        if (!user.checkPassword(password)){
            return "Weak password, try another stronger.";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();
        user = userService.findUser(currentUsername);

        SecurityConfig securityConfig = new SecurityConfig();
        String npassword=securityConfig.passwordEncoder().encode(password);
        user.setPassword(npassword);
        return userService.update(user) ? "success" : "fail";
    }
    @RequestMapping(value = "/release")//释放30天之前的登录日志
    public String release(){
        return logService.release(Time.getDelete(30).toString()) ? "success" : "fail";
    }
    @RequestMapping(value ="/deleteself")//自我注销
    public String deleteself(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();
        return userService.delete(userService.findUser(currentUsername)) ? "success" : "fail";
    }
    @RequestMapping(value = "/wrongP")//错误的用户名和密码
    public String wrongP(String name) {
        User user = userService.findUser(name);
        Attempt tattempt = new Attempt(user.getId());
        Attempt attempt = attemptService.findAtt(tattempt);
        if (!attempt.ableToAccess()){
            return "To many tries failed.";
        }
        attempt.addone();
        return attemptService.addAtt(attempt) ? "You have "+(5-attempt.getFalsecnt())+" chance." : "fail";
    }
    @RequestMapping(value = "setNN")//设置昵称
    public String setNN(String nickname){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();
        User user = userService.findUser(currentUsername);
        user.setNickname(nickname);
        return userService.update(user) ? "success" : "fail";
    }
}