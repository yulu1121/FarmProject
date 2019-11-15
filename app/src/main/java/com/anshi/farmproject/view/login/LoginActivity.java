package com.anshi.farmproject.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.view.main.MainCateActivity;
import com.jaeger.library.StatusBarUtil;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setTranslucentForCoordinatorLayout(this,0);
    }

    public void login(View view) {
        Intent intent = new Intent(this, MainCateActivity.class);
        startActivity(intent);
    }
}
