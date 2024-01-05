package com.allegromini.front.session;

public class CurrentUser {
    private String login = "";
    private String password = "";

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
