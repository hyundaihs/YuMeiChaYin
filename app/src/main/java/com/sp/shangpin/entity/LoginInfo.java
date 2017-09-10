package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/9/009.
 */

public class LoginInfo {
    private String login_verf;

    public LoginInfo() {
        super();
    }

    public LoginInfo(String login_verf) {
        this.login_verf = login_verf;
    }

    public String getLogin_verf() {
        return login_verf;
    }

    public void setLogin_verf(String login_verf) {
        this.login_verf = login_verf;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "login_verf='" + login_verf + '\'' +
                '}';
    }
}
