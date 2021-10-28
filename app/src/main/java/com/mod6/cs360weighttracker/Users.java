package com.mod6.cs360weighttracker;

import androidx.appcompat.app.AppCompatActivity;

public class Users {

    private String Username;
    private String Password;


    Users(String username, String password) {
        this.Username = username;
        this.Password = password;
    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
