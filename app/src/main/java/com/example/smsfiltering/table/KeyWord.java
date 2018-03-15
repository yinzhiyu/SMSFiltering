package com.example.smsfiltering.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class KeyWord {
    private Long id;
    private String keyword;
    @Generated(hash = 674720375)
    public KeyWord(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }
    @Generated(hash = 617591908)
    public KeyWord() {
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
}