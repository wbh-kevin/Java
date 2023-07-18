package com.wbh.testsecurity.chat.entity;

import java.io.Serializable;

public class RMessage implements Serializable {
    private Long id;
    private Long owner;
    private Long replyfrom;
    private String message;
    private String time;
    public RMessage(Long replyfrom, String message){
        this.replyfrom = replyfrom;
        this.message = message;
    }

    public RMessage(Long id) {
        this.id = id;
    }
    public RMessage(Long id, Long owner, Long replyfrom, String message, String time){
        this.id= id;
        this.owner = owner;
        this.replyfrom = replyfrom;
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Long getOwner() {
        return owner;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReplyfrom() {
        return replyfrom;
    }

    public void setReplyfrom(Long replyfrom) {
        this.replyfrom = replyfrom;
    }
}
