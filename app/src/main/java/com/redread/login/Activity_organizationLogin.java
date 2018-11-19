package com.redread.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLoginOrganizationBinding;
import com.redread.login.adapter.Adapter_dept;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.net.netbean.NetBeanDept;
import com.redread.rxbus.RxBus;
import com.redread.rxbus.RxSubscriptions;
import com.redread.rxbus.bean.FinishRX;
import com.redread.utils.Constant;
import com.redread.utils.RecyclerViewUtil;
import com.redread.utils.SharePreferenceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private final int what_success_dept=3;

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case what_success:
                    break;
                case what_net_fail:
                    ableBtn(true);
                    showToast(loginMsg);
                    break;
                case what_success_dept:
                    adapter_dept.notifyDataSetChanged();
                    binding.loginOrganizationName.setText(depts.get(0).getName());
                break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_login_organization);
        initView();
        loadDeptList();
    }

    private List<NetBeanDept> depts=new ArrayList<>();
    private Adapter_dept adapter_dept;
    private int currentDept=0;//默认0
    private void initView() {
        binding.loginOrganizationInclude.titleLeft.setOnClickListener(this);
        binding.loginOrganizationInclude.titleTitle.setText(getTitle().toString());
        binding.loginOrganizationLogin.setOnClickListener(this);
        binding.loginOrganizationInclude.titleLeft.setVisibility(View.INVISIBLE);

        adapter_dept=new Adapter_dept(this,depts);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.deptList.setLayoutManager(manager);
        binding.deptList.setAdapter(adapter_dept);
        RecyclerViewUtil util=new RecyclerViewUtil(this,binding.deptList);
        util.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
               binding.loginOrganizationName.setText(depts.get(position).getName());;
               currentDept=position;
               binding.deptList.setVisibility(View.GONE);
            }
        });
        binding.loginOrganizationName.setOnClickListener(this);
        binding.loginOrganizationNo.setText("18149040053");
        binding.loginOrganizationPwd.setText("123456");

        binding.loginOrganizationOther.setOnClickListener(this);
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
            case R.id.login_organization_name:
                binding.deptList.setVisibility(binding.deptList.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
                break;

        }
    }

    private String loginMsg;
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
        params.put("deptId",depts.get(currentDept).getId()+"");
        Request request = Api.deptLoginPost(this, params);
        mCall = OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
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
                    SharePreferenceUtil.saveSimpleData(Activity_organizationLogin.this, Constant.USER_ID_INT,jsonObject.getInteger("id"));
                    SharePreferenceUtil.saveSimpleData(Activity_organizationLogin.this, Constant.USER_TOKEN_STR,jsonObject.getString("token"));
                    SharePreferenceUtil.saveSimpleData(Activity_organizationLogin.this, Constant.USER_DEPTID_INT,jsonObject.getString("deptId"));
                    SharePreferenceUtil.saveSimpleData(Activity_organizationLogin.this, Constant.USER_NAME_STR,jsonObject.getString("userNickname"));
                    FinishRX finishRX=new FinishRX();
                    finishRX.setWhat(FinishRX.ActivityName.generalLogin);
                    RxBus.getDefault().post(finishRX);
                    finish2();
                }
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

    private Call mCall2;
    /**
     * 取机构列表
     */
    private void loadDeptList(){
        Request request = Api.deptListGet();
        mCall2 = OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        mCall2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: 取机构列表失败了" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    Log.e(TAG, "onResponse: 取机构列表了" + json);
                    List<NetBeanDept> temp= JSON.parseArray(json,NetBeanDept.class);
                    depts.addAll(temp);
                    myHandler.sendEmptyMessage(what_success_dept);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
