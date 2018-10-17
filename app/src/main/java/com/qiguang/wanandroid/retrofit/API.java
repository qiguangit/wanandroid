package com.qiguang.wanandroid.retrofit;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午8:53
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface API {

    String BASE_URL = "http://www.wanandroid.com/";
    /**
     * 首页
     */
    String INDEX = BASE_URL + "article/list/";
    /**
     * banner
     */
    String BANNER = BASE_URL + "banner/json";
    /**
     * 知识体系
     */
    String TREE = BASE_URL + "tree/json";
    /**
     * 导航栏
     */
    String NAVIGATION = BASE_URL + "navi/json";
    /**
     * 项目的标题
     */
    String PROJECT_TITLE = BASE_URL + "project/tree/json";

    /**
     * 登陆
     */
    String LOGIN = BASE_URL + "user/login";

    /**
     * 注册
     */
    String REGISTER = BASE_URL + "user/register";

    /**
     * 注销(取消登陆)
     */
    String LOGOUT=BASE_URL+"user/logout/json";

    /**
     * 收藏
     */
    String COLLECT=BASE_URL+"lg/collect/";

    /**
     * 取消收藏
     */
    String UNCOLLCET=BASE_URL+"lg/uncollect_originId/";

    /**
     * 根据id查询文章
     */
    String ARTICLE_BY_ID=BASE_URL+"article/list/";

    /**
     * 项目详情
     */
    String PROJECT_DETAIL=BASE_URL+"project/list/";

    /**
     * 常用网站
     */
    String COMMON_NET_ADDRESS =BASE_URL+"friend/json";

    /**
     * 热搜
     */
    String HOT_SEARCH=BASE_URL+"hotkey/json";

    /**
     * 搜索文章
     */
    String SEARCH_ARTICLE=BASE_URL+"article/query/";

    /**
     * 收藏列表
     */
    String COLLECT_LIST=BASE_URL+"lg/collect/list/";

    /**
     * 反馈的base url
     */
    String FEEDBACK_BASE_URL="http://www.qiguangit.com:8080/wanAndroid/";

    /**
     * 反馈
     */
    String FEEDBACK=FEEDBACK_BASE_URL+"feedback";

    /**
     * 检查更新
     */
    String CHECK_UPDATE=FEEDBACK_BASE_URL+"checkUpdate";
}
