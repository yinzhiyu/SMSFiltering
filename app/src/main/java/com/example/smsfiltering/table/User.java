package com.example.smsfiltering.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by si on 2018-3-13.
 */
@Entity
public class User {

    @Id(autoincrement = true)

    private Long id;

    private String phone;

    private int password;

    @Generated(hash = 549429693)
    public User(Long id, String phone, int password) {
        this.id = id;
        this.phone = phone;
        this.password = password;
    }

    @Generated(hash = 586692638)
    public User() {
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

    public int getPassword() {
        return this.password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

}
