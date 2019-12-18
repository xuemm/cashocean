package com.jike.cashocean.net;

import com.jike.cashocean.model.BaseBean;
import com.jike.cashocean.model.BillAppBean;
import com.jike.cashocean.model.ClickAppListEntity;
import com.jike.cashocean.model.FindHomeBean;
import com.jike.cashocean.model.FindInfoBean;
import com.jike.cashocean.model.HomeListData;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.OssBackEntity;
import com.jike.cashocean.model.SystemAppBean;
import com.jike.cashocean.model.UserBillInfoBean;
import com.jike.cashocean.model.UserLevelBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author Ming
 * @Date on 2019/12/9
 * @Description
 */
public interface RequestApi {
    //账单提醒
    //获取用户记账笔数，应还总金额
    @GET(URL.GET_USER_BILL)
    Observable<UserBillInfoBean> getUserBill(
            @QueryMap Map<String, String> mapInfo
    );


    // 获取记账列表接口
    @GET
    Observable<BillAppBean> getAppList(
            @Url String url,
            @QueryMap Map<String, Object> mapInfo

    );

    //获取所有系统支持所有app
    @GET(URL.GET_ALL_APP)
    Observable<SystemAppBean> getSystemApp(
            @QueryMap Map<String, String> mapInfo
    );

    @FormUrlEncoded
    @POST
    Observable<NormalBean> postRequest(
            @Url String url,
            @FieldMap Map<String, String> mapInfo
    );


    //获取发现模块首页列表
    // 获取发现列表
    @GET
    Observable<FindHomeBean> getFindList(
            @Url String url,
            @QueryMap Map<String, Object> mapInfo

    );

    //获取推荐产品
    @GET(URL.RECOMMEND_PRODUCT)
    Observable<HomeListData> getUserCommendApp(
            @QueryMap Map<String, Object> mapInfo

    );

    // 获取发现详情
    @GET(URL.FIND_INDEX_INFO)
    Observable<FindInfoBean> getFindInfo(
            @QueryMap Map<String, Object> mapInfo

    );

    //上传app列表
    //上传用户包信息
    @FormUrlEncoded
    @POST
    Observable<NormalBean> uploadUserInfo(
            @Url String url,
            @FieldMap Map<String, String> mapInfo
    );

    //用户打分
    @FormUrlEncoded
    @POST(URL.SCOREBOARD)
    Observable<UserLevelBean> getUserLevel(
            @FieldMap Map<String, String> mapInfo
    );

    @FormUrlEncoded
    @POST(URL.MY_APP_CLICK)
    Observable<ClickAppListEntity> clickItemApp(@FieldMap Map<String, String> mapInfo);

    //请求af链接
    @GET
    Observable<ResponseBody> RequestAf(@Url String url);

    /**
     * 获取OSSTOKEN
     *
     * @param mapInfo
     * @return
     */
    @FormUrlEncoded
    @POST(URL.GET_OSS_TOKEN)
    Observable<OssBackEntity> getOssToken(@FieldMap Map<String, String> mapInfo);
}
