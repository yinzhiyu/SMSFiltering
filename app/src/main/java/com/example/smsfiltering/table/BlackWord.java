package com.example.smsfiltering.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 垃圾短信集
 */
@Entity
public class BlackWord {
    private Long id;
    private String keyword;
    private int number;
    @Generated(hash = 1411647835)
    public BlackWord(Long id, String keyword, int number) {
        this.id = id;
        this.keyword = keyword;
        this.number = number;
    }
    @Generated(hash = 1744752256)
    public BlackWord() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKeyword() {
        return this.keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public int getNumber() {
        return this.number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}