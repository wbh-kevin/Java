package com.wbh.testsecurity.chat.entity;

import java.io.Serializable;

public class Message implements Serializable {
    public Message(String message){
        this.message = message;
    }
    private Long id;
    private Long owner;
    private int repliescnt = 0;
    private String message;
    private String time;

    public Message(Long id) {
        this.id = id;
    }
    public Message(Long id, Long owner, int repliescnt, String message, String time){
        this.id = id;
        this.owner = owner;
        this.repliescnt = repliescnt;
        this.message = message;
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public void setRepliescnt(int repliescnt) {
        this.repliescnt = repliescnt;
    }

    public String getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }

    public int getRepliescnt() {
        return repliescnt;
    }

    public String getMessage() {
        return message;
    }

    public Long getOwner() {
        return owner;
    }

    public void addcnt() {
        this.repliescnt = this.repliescnt+1;
    }
}
