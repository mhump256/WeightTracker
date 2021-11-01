package com.mod6.cs360weighttracker;

public class Users {

    private String Username;
    private String Password;
    private int id;

    /*public Users(String username, String password, int id) {
        this.Username = username;
        this.Password = password;
        this.id = id;
    }

    public Users() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public Users(String username, String password) {
        this.Username = username;
        this.Password = password;
    }

    public Users(){

    }


    public String getUsername() {

        return Username;
    }

    public void setUsername(String username) {

        this.Username = username;
    }

    public String getPassword() {

        return Password;
    }

    public void setPassword(String password) {

        this.Password = password;
    }
}
