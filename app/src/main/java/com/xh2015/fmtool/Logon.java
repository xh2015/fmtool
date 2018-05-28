package com.xh2015.fmtool;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:
 * Date: 2018/5/28 下午4:59
 */
public class Logon {
    public Long userId;
    public Integer userType;
    public String access_token;
    public String refresh_token;
    public Long expires_in;

    @Override
    public String toString() {
        return "Logon{" +
                "userId=" + userId +
                ", userType=" + userType +
                ", access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                '}';
    }
}
