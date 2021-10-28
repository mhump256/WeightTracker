package com.mod6.cs360weighttracker;

public class UsernameDBHelperTest {
    private int id;
    private String userName;
    private String password;

    //Constructor

    public UsernameDBHelperTest(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public UsernameDBHelperTest() {
    }

    //toString


    @Override
    public String toString() {
        return "UsernameDBHelperTest{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    //Getter&Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
