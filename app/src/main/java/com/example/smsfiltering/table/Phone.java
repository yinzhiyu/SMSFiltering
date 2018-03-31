package com.example.smsfiltering.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Phone {
    @Id(autoincrement = true)
    private Long phoneId;
    private Long id;
    private String phone;
    @Generated(hash = 2053613892)
    public Phone(Long phoneId, Long id, String phone) {
        this.phoneId = phoneId;
        this.id = id;
        this.phone = phone;
    }
    @Generated(hash = 429398894)
    public Phone() {
    }
    public Long getPhoneId() {
        return this.phoneId;
    }
    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
