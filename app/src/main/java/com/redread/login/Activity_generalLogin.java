package com.redread.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLoginGeneralBinding;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.rxbus.RxBus;
import com.redread.rxbus.RxSubscriptions;
import com.redread.rxbus.bean.FinishRX;
import com.redread.utils.SharePreferenceUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangshexin on 2018/9/14.
 * <p>
 * 验证码登录
 */

public class Activity_generalLogin extends BaseActivity implements View.OnClickListener {

    private String TAG=getClass().getName();

    public static String USER_NAME="user_name";
    private LayoutLoginGeneralBinding binding;
    private int time = 60;//60秒限制
    private final int what_code = 0;
    private final int what_net_fail=1;//网络失败
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
                case what_net_fail:
                    showToast("网络开小差了！");
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

        binding.loginGeneralPhone.setText("guoguoping");
        binding.loginGeneralInputVerificationCode.setText("123456");

        binding.loginGeneralLogin.setOnClickListener(this);
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


    private Call mCall;
    private void doLogin(){
        ableBtn(false);
        Editable phoneNum=binding.loginGeneralPhone.getText();
        Editable pwd=binding.loginGeneralInputVerificationCode.getText();
        if(TextUtils.isEmpty(phoneNum)||TextUtils.isEmpty(pwd))
        {
            ableBtn(true);
            showToast("帐号和验证码为必填！");
            return;
        }

        HashMap<String,String> params=new HashMap<>();
        params.put("username",phoneNum.toString());
        params.put("password",pwd.toString());
        Request request=Api.loginPost(this,params);
        mCall= OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myHandler.sendEmptyMessage(what_net_fail);
                ableBtn(true);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                Log.e(TAG, "onResponse: 登录了"+ json);
                //记录用户信息
                //TODO
                SharePreferenceUtil.saveSimpleData(Activity_generalLogin.this,USER_NAME,pwd);
                finish2();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //回home
        Intent intent = new Intent();
        // 为Intent设置Action、Category属性
        intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
        startActivity(intent);
    }

    /**
     * 按钮是否可点击
     * @param able
     */
    private void ableBtn(boolean able){
        binding.loginGeneralLogin.setClickable(able);
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
