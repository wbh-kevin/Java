package com.wbh.testsecurity.chat.entity;

import java.io.Serializable;
import com.wbh.testsecurity.chat.function.Code;
import com.wbh.testsecurity.chat.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class User implements Serializable {
    static final String normal="normal";
    private Long id;//编号
    private String name;//用户名
    private String password;//密码
    private String role = normal;
    private String lasttime = null;
    private String nickname;

    public User(){}

    public User(String name, String password) {
        this.name=name;
        this.password=password;
        this.id=0L;
    }

    public User(long id, String name, String password, String time) throws Exception {
        this.id=id;
        this.name=name;
        SecurityConfig securityConfig = new SecurityConfig();
        this.password=securityConfig.passwordEncoder().encode(password);
        this.lasttime=time;
        this.nickname=name;
    }
    public User(long id, String name, String password, String time, String nickname) {
        this.id=id;
        this.name=name;
        this.password=password;
        this.lasttime=time;
        this.nickname=nickname;
    }

    public User(String time) {
        this.lasttime = time;
    }

    public boolean checkPassword(String password){
        return password.matches("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$");
    }

    public User(Long id, String name, String password) {
        this.id=id;
        this.name=name;
        this.password=password;
    }
    public User(Long id){
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}