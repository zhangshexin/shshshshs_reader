package com.redread.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLoginOrganizationBinding;
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
 * 机构登录
 */

public class Activity_organizationLogin extends BaseActivity implements View.OnClickListener {
    private LayoutLoginOrganizationBinding binding;
    private String TAG = getClass().getName();
    private final int what_net_fail=1;//网络失败
    private final int what_success=2;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case what_success:
                    closeOtherLogin();
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
        binding = DataBindingUtil.setContentView(this, R.layout.layout_login_organization);
        initView();
    }

    private void initView() {
        binding.loginOrganizationInclude.titleLeft.setOnClickListener(this);
        binding.loginOrganizationInclude.titleTitle.setText(getTitle().toString());
        binding.loginOrganizationLogin.setOnClickListener(this);
        binding.loginOrganizationName.setVisibility(View.INVISIBLE);
        binding.loginOrganizationInclude.titleLeft.setVisibility(View.INVISIBLE);

        binding.loginOrganizationNo.setText("admin");
        binding.loginOrganizationPwd.setText("111111");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
//                finish2();
                break;
            case R.id.login_organization_login:
                doLogin();
                break;
            case R.id.login_organization_other:
                //去普通用户登录
                startActivity(Activity_generalLogin.class);
                break;
        }
    }

    private Call mCall;

    private void doLogin() {
        ableBtn(false);
        Editable phoneNum = binding.loginOrganizationNo.getText();
        Editable pwd = binding.loginOrganizationPwd.getText();
        if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(pwd)) {
            ableBtn(true);
            showToast("帐号和验证码为必填！");
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("username", phoneNum.toString());
        params.put("password", pwd.toString());
        Request request = Api.loginPost(this, params);
        mCall = OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myHandler.sendEmptyMessage(what_net_fail);
                ableBtn(true);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e(TAG, "onResponse: 登录了" + json);
                //记录用户信息
                //TODO
                SharePreferenceUtil.saveSimpleData(Activity_organizationLogin.this, Activity_generalLogin.USER_NAME, phoneNum.toString());
                finish2();
            }
        });
    }

    /**
     * 按钮是否可点击
     *
     * @param able
     */
    private void ableBtn(boolean able) {
        binding.loginOrganizationLogin.setClickable(able);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeMessages(what_net_fail);
        myHandler.removeMessages(what_success);
    }

    private void closeOtherLogin() {
        FinishRX finishRX = new FinishRX();
        finishRX.setWhat(FinishRX.ActivityName.organizationLogin);
        RxBus.getDefault().post(finishRX);
        finish2();
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
