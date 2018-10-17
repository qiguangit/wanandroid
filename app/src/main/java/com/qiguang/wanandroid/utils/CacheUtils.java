package com.qiguang.wanandroid.utils;

import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;

import java.io.File;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-26 上午10:37
 * @Description: 缓存采用文件方式进行
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class CacheUtils {
    /**
     * 添加json缓存
     *
     * @param url
     * @param json
     */
    public static void putJsonCache(String url, String json) {
        String urlMd5 = EncryptUtils.encryptMD5ToString(url);
        File file = new File(Environment.getExternalStorageDirectory(), AppUtils.getAppPackageName() + "/cache/" + urlMd5);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileIOUtils.writeFileFromString(file, json);
    }

    /**
     * 获取缓存
     *
     * @param url
     * @return
     */
    public static String getJsonCache(String url) {
        String urlMd5 = EncryptUtils.encryptMD5ToString(url);
        File file = new File(Environment.getExternalStorageDirectory(), AppUtils.getAppPackageName() + "/cache/" + urlMd5);
        if (file.exists()) {
            return FileIOUtils.readFile2String(file);
        }
        return "";
    }
}
