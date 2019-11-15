package com.anshi.farmproject.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.anshi.farmproject.database.MySqlOpenHelper;
import com.anshi.farmproject.greendao.DaoMaster;
import com.anshi.farmproject.greendao.DaoSession;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.cookie.OkHttpUtils;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApplication extends Application {
    private Retrofit mAppRetrofit;
    public static BaseApplication instances;
    //static 代码段可以防止内存泄露
    public  Retrofit getAppRetrofit(){
        return mAppRetrofit;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        OkHttpUtils.initOkHttp(this);
        //http://47.128.149.8:8080/
        mAppRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.COMMON_URL_HEADER)
                .client(OkHttpUtils.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        setDatabase();
    }
    /**
     * 设置greenDao
     */
    private MySqlOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new MySqlOpenHelper(this,"farm-db",null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
    public static BaseApplication getInstances(){
        return instances;
    }
}
