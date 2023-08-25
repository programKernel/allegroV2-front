package com.allegromini.front.dto;

public class AccountDTO {
    private String email = "";
    private String password = "";
    private String repeatPassword = "";
    private boolean tos;

    public AccountDTO(String email, String password, String repeatPassword, boolean tos) {
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.tos = tos;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public boolean isTos() {
        return tos;
    }
}
