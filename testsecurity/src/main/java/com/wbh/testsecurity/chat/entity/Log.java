package com.wbh.testsecurity.chat.entity;

public class Log {
    private long id;
    private String time;

    public Log(Long id, String now) {
        this.id = id;
        this.time = now;
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
