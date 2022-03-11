package com.kabunx.core.util;

import com.kabunx.core.entity.AuthEntity;

public class ThreadLocalUtils {
    /**
     * 保存用户的ThreadLocal
     * 在拦截器操作 添加、删除相关用户数据
     */
    private static final ThreadLocal<AuthEntity> userThreadLocal = new ThreadLocal<>();

    /**
     * 添加当前登录用户方法
     * 在拦截器方法执行前调用设置获取用户
     *
     * @param auth 当前用户
     */
    public static void setAuth(AuthEntity auth) {
        userThreadLocal.set(auth);
    }

    /**
     * 获取当前登录用户方法
     */
    public static AuthEntity getAuth() {
        return userThreadLocal.get();
    }

    /**
     * 删除当前登录用户方法
     * 在拦截器方法执行后 移除当前用户对象
     */
    public static void removeAuth() {
        userThreadLocal.remove();
    }
}
