package com.anshi.farmproject.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.entry.LoginEntry;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.DialogBuild;
import com.anshi.farmproject.utils.SharedPreferenceUtils;
import com.anshi.farmproject.utils.Utils;
import com.anshi.farmproject.view.main.MainCateActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {
    private EditText mUserNameEt;
    private EditText mPassWordEt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean autoLogin = SharedPreferenceUtils.getBoolean(mContext, "autoLogin");
        if (autoLogin){
            Intent intent = new Intent(mContext, MainCateActivity.class);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.activity_login);
            StatusBarUtil.setLightMode(this);
            StatusBarUtil.setTranslucentForCoordinatorLayout(this,0);
            initView();
        }
    }

    private void initView() {
        mUserNameEt = findViewById(R.id.userName_et);
        mPassWordEt = findViewById(R.id.password_et);
    }

    public void login(View view) {

        if (TextUtils.isEmpty(mUserNameEt.getText())){
            Toast.makeText(mContext, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mPassWordEt.getText())){
            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        loginApp(mUserNameEt.getText().toString(),mPassWordEt.getText().toString());
    }
    private KProgressHUD commonLoadDialog;
    private void loginApp(String userName,String password){
        if (!isFinishing()){
            commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this,"正在登录中...");
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.loginApp(requestBody)
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        try {
                            String string = responseBody.string();
                            if (Utils.isGoodJson(string)){
                                Gson gson = new Gson();
                                LoginEntry loginEntry = gson.fromJson(string, LoginEntry.class);
                                if (loginEntry.getCode()== Constants.SUCCESS_CODE){
                                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                                    SharedPreferenceUtils.saveBoolean(mContext,"autoLogin",true);
                                    SharedPreferenceUtils.saveInt(mContext,"userId",loginEntry.getData().getUserId());
                                    SharedPreferenceUtils.saveString(mContext,"userName",loginEntry.getData().getUserName());
                                    SharedPreferenceUtils.saveInt(mContext,"deptId",loginEntry.getData().getDept().getDeptId());
                                    SharedPreferenceUtils.saveString(mContext,"loginName" ,loginEntry.getData().getLoginName());
                                    SharedPreferenceUtils.saveString(mContext,"deptName",loginEntry.getData().getDept().getDeptName());
                                    SharedPreferenceUtils.saveString(mContext,"townName",loginEntry.getData().getTownName());
                                    SharedPreferenceUtils.saveInt(mContext,"townId",loginEntry.getData().getTownId());
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(mContext, MainCateActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    },1000);
                                }else {
                                    Toast.makeText(mContext, loginEntry.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        Toast.makeText(mContext, "网络超时", Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                    }
                });
    }

}
