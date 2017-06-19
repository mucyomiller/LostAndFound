package com.regismutangana.lostandfound.Model;


public class User {
    private String email;
    private String names;
    private String username;
    private String phone;
    private String sex;
    private String location;

    public User() {
    }

    public User(String email, String names, String username, String phone, String sex, String location) {
        this.email = email;
        this.names = names;
        this.username = username;
        this.phone = phone;
        this.sex = sex;
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
