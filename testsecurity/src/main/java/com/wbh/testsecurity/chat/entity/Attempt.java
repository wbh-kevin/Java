package com.wbh.testsecurity.chat.entity;

import com.wbh.testsecurity.chat.function.Time;

import java.text.ParseException;

public class Attempt {
    private long id;
    private String lasttry;
    private int falsecnt;

    public Attempt(long id, String lasttry, int falsecnt) {
        this.id = id;
        this.lasttry = lasttry;
        this.falsecnt = falsecnt;
    }

    private void renew(){
        if (Time.comTime(this.lasttry, 60)){
            this.falsecnt=0;
        }
    }

    public Attempt(Long id) {
        this.id = id;
        this.lasttry = Time.getNow().toString();
        this.falsecnt = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFalsecnt() {
        return falsecnt;
    }

    public void setFalsecnt(int falsecnt) {
        this.falsecnt = falsecnt;
    }

    public String getLasttry() {
        return lasttry;
    }

    public void setLasttry(String lasttry) {
        this.lasttry = lasttry;
    }
    public void addone(){
        this.falsecnt++;
        this.lasttry = Time.getNow().toString();
    }
    public boolean ableToAccess() {
        renew();
        if (falsecnt>=5 && !Time.comTime(this.lasttry, 60)){
            return false;
        }
        return true;
    }
    public void login(){
        this.falsecnt = 0;
        this.lasttry = Time.getNow().toString();
    }
}
