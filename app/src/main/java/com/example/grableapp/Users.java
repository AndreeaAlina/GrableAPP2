package com.example.grableapp;

public class Users {
    private String email;
    private String password;
    private String Uid;

    public Users() {
    }

    public Users(String email, String password, String uid) {
        this.email = email;
        Uid = uid;
        this.password= password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    public String getUid() { return Uid; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) { this.password = password; }

    public void setUid(String uid) { Uid = uid; }


}
