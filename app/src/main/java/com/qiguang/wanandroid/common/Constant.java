package com.qiguang.wanandroid.common;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-16 下午12:53
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface Constant {
    String USERNAME = "username";
    String PASSWORD = "password";
    String LAST_LOGIN = "lost_login";
    String DATA = "data";
    String KEY = "key";
    int COOKIE_TIME_OUT=1000*60*30;
    String IS_LOGIN = "is_login";

    /**
     * 是否第一次进入APP
     */
    String IS_FIRST_INTO="is_first_into";


    interface Tag{
        String TAG_MAIN="main";
        String TAG_COLLECT="collect";
        String TAG_SETTING="setting";
    }

    interface Db {
        String DB_NAME = "wanAndroid.db";
        int DB_VERSION = 1;
    }

    interface Error {
        String REQUEST_ERROR = "联网失败，请检查网络";
    }

    interface Setting{
        /**
         * 自动缓存
         */
        String IS_AUTO_CACHE="is_auto_cache";
        /**
         * 夜间模式
         */
        String IS_NIGHT_MODE="is_night_mode";

        /**
         * 是否不接受推送
         */
        String IS_NO_PUSH="is_no_push";
    }

    interface Feedback{
        /**
         * 反馈的主体
         */
        String CONTENT="content";

        /**
         * 联系人
         */
        String CONTACT="contact";

        /**
         * 手机型号
         */
        String PHONE_TYPE="phone_type";
    }
}
