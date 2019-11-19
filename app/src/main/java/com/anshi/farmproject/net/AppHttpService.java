package com.anshi.farmproject.net;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface AppHttpService {
    @POST("login")
    Observable<ResponseBody> loginApp(@Body RequestBody requestBody);//登录
    @GET("getMyBotanyList")
    Observable<ResponseBody> getBotanyList();//获取植物类型
    @GET("getMyCureList")
    Observable<ResponseBody> getMyCureList();//获取处置类型列表
    @POST("getRelationDeptList")
    Observable<ResponseBody> getRelationDeptList(@Body RequestBody requestBody);//获取部门或村子列表
    @POST("uploadImg")
    Observable<ResponseBody> uploadImg(@Body RequestBody requestBody);//上传图片

}
