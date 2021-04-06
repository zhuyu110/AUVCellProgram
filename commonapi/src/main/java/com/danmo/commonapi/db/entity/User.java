package com.danmo.commonapi.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Index(unique = true)//设置唯一性
    private String _id;//用户
    private String username;//用户名
    private String role;//角色
    @Generated(hash = 214807497)
    public User(String _id, String username, String role) {
        this._id = _id;
        this.username = username;
        this.role = role;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public String get_id() {
        return this._id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRole() {
        return this.role;
    }
    public void setRole(String role) {
        this.role = role;
    }



}
