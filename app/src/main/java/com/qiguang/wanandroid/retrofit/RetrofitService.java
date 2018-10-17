package com.qiguang.wanandroid.retrofit;

import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.BannerBean;
import com.qiguang.wanandroid.bean.BaseBean;
import com.qiguang.wanandroid.bean.CollectBean;
import com.qiguang.wanandroid.bean.CollectListBean;
import com.qiguang.wanandroid.bean.CommonNetAddressBean;
import com.qiguang.wanandroid.bean.HotSearchBean;
import com.qiguang.wanandroid.bean.LoginRegisterBean;
import com.qiguang.wanandroid.bean.ProjectDetailBean;
import com.qiguang.wanandroid.bean.ProjectTitleBean;
import com.qiguang.wanandroid.bean.TreeBean;
import com.qiguang.wanandroid.bean.NavigationBean;
import com.qiguang.wanandroid.bean.UpdateBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午8:53
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface RetrofitService {
    /**
     * 获取文章列表
     *
     * @param index
     * @return
     */
    @GET(API.INDEX + "{page}/json")
    Observable<ArticleBean> getArticles(@Path("page") int index);

    /**
     * 获取banner
     *
     * @return
     */
    @GET(API.BANNER)
    Observable<BannerBean> getBanner();

    /**
     * 获取知识体系
     *
     * @return
     */
    @GET(API.TREE)
    Observable<TreeBean> getTree();

    /**
     * 获取导航
     *
     * @return
     */
    @GET(API.NAVIGATION)
    Observable<NavigationBean> getNavigation();

    /**
     * 项目的标题
     *
     * @return
     */
    @GET(API.PROJECT_TITLE)
    Observable<ProjectTitleBean> getProjectTitles();

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST(API.LOGIN)
    Observable<LoginRegisterBean> login(@Field("username") String username,
                                        @Field("password") String password);

    /**
     * 退出登陆
     * @return
     */
    @GET(API.LOGOUT)
    Observable<BaseBean> logout();

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 重复密码
     * @return
     */
    @FormUrlEncoded
    @POST(API.REGISTER)
    Observable<LoginRegisterBean> register(@Field("username") String username,
                                           @Field("password") String password,
                                           @Field("repassword") String repassword);

    /**
     * 收藏
     *
     * @param id
     * @return
     */
    @POST(API.COLLECT + "{id}/json")
    Observable<CollectBean> collect(@Path("id") int id);


    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    @POST(API.UNCOLLCET + "{id}/json")
    Observable<CollectBean> uncollect(@Path("id") int id);

    /**
     * 根据id查询文章
     *
     * @param page 父文章的的页数
     * @param cid   文章的cid
     * @return
     */
    @GET(API.ARTICLE_BY_ID + "{page}/json")
    Observable<ArticleBean> articleById(@Path("page") int page,
                                        @Query("cid") int cid);

    /**
     * 根据id 查询项目页的文章详情
     *
     * @param page
     * @param cid
     * @return
     */
    @GET(API.PROJECT_DETAIL + "{page}/json")
    Observable<ProjectDetailBean> requestProjectDetail(@Path("page") int page,
                                                       @Query("cid") int cid);

    /**
     * 请求常用网站
     * @return
     */
    @GET(API.COMMON_NET_ADDRESS)
    Observable<CommonNetAddressBean> requestCommonNetAddress();

    /**requestCollect
     * 请求热搜
     * @return
     */
    @GET(API.HOT_SEARCH)
    Observable<HotSearchBean> requestHotSearch();

    /**
     * 根据关键字搜索文章
     * @param page
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST(API.SEARCH_ARTICLE+"{page}/json")
    Observable<ArticleBean> searchArticleByKey(@Path("page") int page,@Field("k") String key);

    /**
     * 请求收藏列表
     * @param page
     * @return
     */
    @GET(API.COLLECT_LIST+"{page}/json")
    Observable<CollectListBean> requestCollectList(@Path("page") int page);

    /**
     * 反馈 同时上传文本和多张图片
     * @param body
     * @return
     */
    @POST(API.FEEDBACK)
    Observable<BaseBean> feedback(@Body RequestBody body);

    /**
     * 检查更新
     * @return
     */
    @GET(API.CHECK_UPDATE)
    Observable<UpdateBean> checkUpdate();

    /**
     * 下载文件
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
