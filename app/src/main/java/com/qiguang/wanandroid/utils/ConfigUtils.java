package com.qiguang.wanandroid.utils;

import com.blankj.utilcode.util.SPUtils;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-26 上午10:20
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class ConfigUtils {
    private static final String CONFIG = "config";
    private static final String IS_NIGHT_MODE = "isNightMode";
    private static final String IS_AUTO_CACHE = "isAutoCache";

    public static boolean isNightMode() {
        return SPUtils.getInstance(CONFIG).getBoolean(IS_NIGHT_MODE);
    }

    public static void setNightMode(boolean isNightMode) {
        SPUtils.getInstance(CONFIG).put(IS_NIGHT_MODE, isNightMode);
    }

    public static boolean isAutoCache() {
        return SPUtils.getInstance(CONFIG).getBoolean(IS_AUTO_CACHE);
    }

    public static void setAutoCache(boolean isAutoCache) {
        SPUtils.getInstance(CONFIG).put(IS_AUTO_CACHE, isAutoCache);
    }

}
