package com.redread.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLoginOrganizationBinding;
import com.redread.rxbus.RxBus;
import com.redread.rxbus.bean.FinishRX;

/**
 * Created by zhangshexin on 2018/9/14.
 *
 * 机构登录
 */

public class Activity_organizationLogin extends BaseActivity implements View.OnClickListener {
    private LayoutLoginOrganizationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.layout_login_organization);
        initView();
    }

    private void initView() {
        binding.loginOrganizationInclude.titleLeft.setOnClickListener(this);
        binding.loginOrganizationInclude.titleTitle.setText(getTitle().toString());
        binding.loginOrganizationLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                finish2();
                break;
            case R.id.login_organization_login:
                doLogin();
                break;
        }
    }

    private void doLogin(){
        FinishRX finishRX= new FinishRX();
        finishRX.setWhat(FinishRX.ActivityName.organizationLogin);
        RxBus.getDefault().post(finishRX);
        finish2();
    }


}
