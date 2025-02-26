package com.example.demo.common.utils;

/**
 * 工具类，提供保存loginUser到threadlocal的方法。
 */
public class LoginUserHolder {

    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();//threadlocal

    public static void setLoginUser(LoginUser loginUser) {
        threadLocal.set(loginUser);
    }

    public static LoginUser getLoginUser() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
