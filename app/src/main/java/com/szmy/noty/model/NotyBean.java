package com.szmy.noty.model;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class NotyBean extends LitePalSupport {

    private int id;
    private String time;
    private String content;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NotyBean(){}

    public NotyBean(String time, String content, String title) {
        this.time = time;
        this.content = content;
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
