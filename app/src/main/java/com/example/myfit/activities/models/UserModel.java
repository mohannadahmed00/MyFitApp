package com.example.myfit.activities.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    String name,email,phone,password,verify;

    public UserModel() {
        this.name=null;
        this.email = null;
        this.phone = null;
        this.password = null;
        this.verify = null;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getVerify() {
        return verify;
    }
}
