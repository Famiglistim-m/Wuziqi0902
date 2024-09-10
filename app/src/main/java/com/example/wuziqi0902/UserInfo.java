package com.example.wuziqi0902;

public class UserInfo {
    private int user_id;
    private String phone;
    private String username;
    private String password;

    public UserInfo(int user_id, String phone, String username, String password) {
        this.user_id = user_id;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
