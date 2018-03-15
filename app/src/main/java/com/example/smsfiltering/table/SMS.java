package com.example.smsfiltering.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yinzhiyu on 2018-3-15.
 */
@Entity
public class SMS {
    private Long id;
    private String sender;
    private String content;
    private String time;
    private int readType;
    private int usefulType;
    @Generated(hash = 799803224)
    public SMS(Long id, String sender, String content, String time, int readType,
            int usefulType) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.time = time;
        this.readType = readType;
        this.usefulType = usefulType;
    }
    @Generated(hash = 2144275990)
    public SMS() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSender() {
        return this.sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getReadType() {
        return this.readType;
    }
    public void setReadType(int readType) {
        this.readType = readType;
    }
    public int getUsefulType() {
        return this.usefulType;
    }
    public void setUsefulType(int usefulType) {
        this.usefulType = usefulType;
    }

}
