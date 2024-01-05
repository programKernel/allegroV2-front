package com.allegromini.front.session;

public class CurrentUser {
    private String login = "jjjj@gmail.com";
    private String password = "jjjj";

    public void setLogin(String newLogin) {
        login = newLogin;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
