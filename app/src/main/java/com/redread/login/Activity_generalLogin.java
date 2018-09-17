package com.redread.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLoginGeneralBinding;
import com.redread.rxbus.RxBus;
import com.redread.rxbus.RxSubscriptions;
import com.redread.rxbus.bean.FinishRX;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangshexin on 2018/9/14.
 * <p>
 * 验证码登录
 */

public class Activity_generalLogin extends BaseActivity implements View.OnClickListener {
    private LayoutLoginGeneralBinding binding;
    private int time = 60;//60秒限制
    private final int what_code = 0;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case what_code:
                    if (time <= 0) {
                        time = 60;
                        binding.loginGeneralVerificationCode.setClickable(true);
                        binding.loginGeneralVerificationCode.setText(R.string.login_verification_code);
                    } else {
                        binding.loginGeneralVerificationCode.setClickable(false);
                        binding.loginGeneralVerificationCode.setText("(" + time-- + ")秒后重新获取");
                        myHandler.sendEmptyMessageDelayed(what_code, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_login_general);
        initView();
    }

    private void initView() {
        binding.loginGeneralInclude.titleLeft.setOnClickListener(this);
        binding.loginGeneralInclude.titleTitle.setText(getTitle().toString());
        binding.loginGeneralVerificationCode.setOnClickListener(this);
        binding.loginSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                finish2();
                break;
            case R.id.login_general_verification_code://获取验证码,倒计时
                myHandler.sendEmptyMessage(what_code);
                break;
            case R.id.login_switch:
                startActivity(Activity_organizationLogin.class);
                break;
            case R.id.login_general_login:
                doLogin();
                break;
        }
    }

    private void doLogin(){
        finish2();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeMessages(what_code);
    }


    private Subscription mSubscription;
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscription = RxBus.getDefault().toObservable(FinishRX.class)
                .subscribe(new Action1<FinishRX>() {
                    @Override
                    public void call(FinishRX what) {
                        if (what.getWhat().getCode() == 0)
                            finish();
                    }
                });
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscription);
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mSubscription);
    }
}
