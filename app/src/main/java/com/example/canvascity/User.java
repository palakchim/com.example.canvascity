package com.example.canvascity;

public class User {
    private String uid;
    private String name;
    private String email;
    private String mobile;

    // Required empty constructor for Firebase
    public User() {
    }

    public User(String uid, String name, String email, String mobile) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    // Getters and setters
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
