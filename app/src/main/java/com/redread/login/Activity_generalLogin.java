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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLoginGeneralBinding;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.rxbus.RxBus;
import com.redread.rxbus.RxSubscriptions;
import com.redread.rxbus.bean.FinishRX;
import com.redread.utils.Constant;
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

    private LayoutLoginGeneralBinding binding;
    private int time = 60;//60秒限制
    private final int what_code = 0;
    private final int what_code_fail = 10;//验证码失败
    private final int what_code_success=11;//验证码成功
    private final int what_net_fail=1;//网络失败
    private final int what_success=2;

    private String code;
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
                case what_code_fail:
                    showToast("生成验证码失败");
                    myHandler.removeMessages(what_code);
                    binding.loginGeneralVerificationCode.setClickable(true);
                    binding.loginGeneralVerificationCode.setText(R.string.login_verification_code);
                    break;
                case what_code_success:
                    binding.loginGeneralInputVerificationCode.setText(code);
                    break;
                case what_net_fail:
                    ableBtn(true);
                    showToast(loginMsg);
                    if(binding.loginGeneralVerificationCode.getVisibility()==View.VISIBLE){
                        //验证码登录需要把验证码按钮放开
                        myHandler.removeMessages(what_code);
                        binding.loginGeneralVerificationCode.setClickable(true);
                        binding.loginGeneralVerificationCode.setText(R.string.login_verification_code);
                    }
                    break;
                case what_success:
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
//        binding.loginGeneralInclude.titleLeft.setVisibility(View.INVISIBLE);
        binding.loginGeneralInclude.titleLeft.setOnClickListener(this);
        binding.loginGeneralInclude.titleTitle.setText(getTitle().toString());
        binding.loginGeneralVerificationCode.setOnClickListener(this);
        binding.loginOther.setOnClickListener(this);

        binding.loginGeneralPhone.setText("18149040056");
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
                getCode();
                break;
            case R.id.login_other:
                //切换成验证码登录
                switchUiToRegister();
                break;
            case R.id.login_general_login:
                //判断是登录还是下一步,验证码显示就是验证码登录
                doLogin(binding.loginGeneralVerificationCode.getVisibility()==View.VISIBLE);
                break;
        }
    }

    /**
     * 切换ui到注册
     */
    private void switchUiToRegister() {
        binding.loginOther.setVisibility(View.GONE);
        binding.loginGeneralPhone.setHint(R.string.login_phone);
        binding.loginGeneralInputVerificationCode.setHint(R.string.login_input_verification_code);
        binding.loginGeneralVerificationCode.setVisibility(View.VISIBLE);

        binding.loginGeneralPhone.setText("18149040053");
    }

    private Call codeCall;
    /**
     * 取验证码
     */
    private void getCode() {
        Editable phoneNum=binding.loginGeneralPhone.getText();
        if(TextUtils.isEmpty(phoneNum))
        {
            ableBtn(true);
            showToast("帐号不能为空");
            return;
        }
        Request request=Api.codeGenPost(this,phoneNum.toString());
        codeCall= OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        codeCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: 取验证码失败"+ e.getMessage());
                myHandler.sendEmptyMessage(what_code_fail);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                Log.e(TAG, "onResponse: 取验证码了"+ json);
                //TODO
                try {
                    JSONObject obj=JSON.parseObject(json);
                    code=obj.getString("kaptcha");
                    myHandler.sendEmptyMessage(what_code_success);
                } catch (Exception e) {
                    e.printStackTrace();
                    myHandler.sendEmptyMessage(what_code_fail);
                }
            }
        });
    }


    private String loginMsg;

    /**
     * 普通用户帐号登录
     * @param isVarCode 是否为验证码登录
     */
    private void doLogin(boolean isVarCode){
        ableBtn(false);
        Editable username=binding.loginGeneralPhone.getText();
        Editable pwd=binding.loginGeneralInputVerificationCode.getText();
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(pwd))
        {
            ableBtn(true);
            showToast(isVarCode?"手机号和验证码不能为空!":"帐号和密码不能为空！");
            return;
        }

        HashMap<String,String> params=new HashMap<>();
        if(isVarCode){
            params.put("phone",username.toString());
            params.put("kaptcha",pwd.toString());
        }else{
            params.put("username",username.toString());
            params.put("password",pwd.toString());
        }
        Request request=isVarCode?Api.kaptchaLoginPost(this,params,username.toString(),pwd.toString()):Api.loginPost(this,params);
        Call mCall= OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loginMsg=getString(R.string.net_notify_fail);
                myHandler.sendEmptyMessage(what_net_fail);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                Log.e(TAG, "onResponse: 登录了"+ json);
                JSONObject jsonObject=JSON.parseObject(json);
                if(jsonObject.containsKey("msg")){
                    //失败
                    loginMsg=jsonObject.getString("msg");
                    myHandler.sendEmptyMessage(what_net_fail);
                }else{
                    //记录用户信息
                    //TODO
                    SharePreferenceUtil.saveSimpleData(Activity_generalLogin.this, Constant.USER_ID_INT,jsonObject.getInteger("id"));
                    SharePreferenceUtil.saveSimpleData(Activity_generalLogin.this, Constant.USER_TOKEN_STR,jsonObject.getString("token"));
                    SharePreferenceUtil.saveSimpleData(Activity_generalLogin.this, Constant.USER_DEPTID_INT,jsonObject.getString("deptId"));
                    SharePreferenceUtil.saveSimpleData(Activity_generalLogin.this, Constant.USER_NAME_STR,jsonObject.getString("userNickname"));
                    FinishRX finishRX=new FinishRX();
                    finishRX.setWhat(FinishRX.ActivityName.organizationLogin);
                    RxBus.getDefault().post(finishRX);

                    //如果是验证码登录需要去设置密码
                    if(isVarCode){
                        startActivity(Activity_setpassword.class);
                    }
                    finish2();
                }
            }
        });
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
                        if (what.getWhat().getCode() == 1)
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
