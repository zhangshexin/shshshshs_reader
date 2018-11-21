package com.redread.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutSetpwdBinding;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.utils.Constant;
import com.redread.utils.SharePreferenceUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangshexin on 2018/11/18.
 */

public class Activity_setpassword extends BaseActivity implements View.OnClickListener {
    private String TAG = getClass().getName();
    private LayoutSetpwdBinding binding;
    private int userId;//用户id
    private int deptId;//机构id。普通用户为1

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_setpwd);
        userId = (Integer) SharePreferenceUtil.getSimpleData(this, Constant.USER_ID_INT, 0);
        deptId = (Integer) SharePreferenceUtil.getSimpleData(this, Constant.USER_DEPTID_INT, 1);
        iniView();
    }

    private void iniView() {
        binding.includetitle.titleLeft.setOnClickListener(this);
        binding.loginGeneralSetpwd.setOnClickListener(this);
        binding.includetitle.titleTitle.setText(getTitle());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                finish2();
                break;
            case R.id.login_general_setpwd:
                doSetPwd();
                break;
        }
    }

    private Call mCall;

    /**
     * 执行设置密码请求
     */
    private void doSetPwd() {
        //判断用户密是否为空，两次密码是否相同
        Editable unameEdi = binding.setpwdUname.getText();
        Editable pwd = binding.setpwdPwd.getText();
        Editable pwdRe = binding.setpwdRepwd.getText();

        if (unameEdi == null || unameEdi.toString() == null || unameEdi.toString().equals("")) {
            showToast("用户名不能为空");
            return;
        } else if (pwd == null || pwd.toString() == null || pwd.toString().equals("")) {
            showToast("密码不能为空");
            return;
        } else if (pwdRe == null || pwdRe.toString() == null || pwdRe.toString().equals("")) {
            showToast("确认密码不能为空");
            return;
        }

        if (!pwdRe.toString().equals(pwd.toString())) {
            showToast("两次密码密须相同");
            return;
        }
        lockRightBtn(true);
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId + "");
        params.put("username", unameEdi.toString());
        params.put("password", pwd.toString());
        params.put("deptId", deptId + "");
        Request request = Api.setPasswordPost(this, params);
        mCall = OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e(TAG, "onResponse: 密码设置失败" + e.getMessage());
                setMsg = getString(R.string.net_notify_fail);
                mHandler.sendEmptyMessage(what_net_fail);
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    Log.e(TAG, "onResponse: 密码设置完成" + json);
                    JSONObject jsonObject = JSON.parseObject(json);
                    if (jsonObject.containsKey("code") && jsonObject.getInteger("code") == 0) {
                        mHandler.sendEmptyMessage(what_set_success);
                    } else {
                        setMsg = jsonObject.getString("msg");
                        mHandler.sendEmptyMessage(what_net_fail);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    setMsg = getString(R.string.net_notify_fail);
                    mHandler.sendEmptyMessage(what_net_fail);
                }
            }
        });
    }

    private String setMsg = "";
    private final int what_net_fail = 0;
    private final int what_set_success = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case what_net_fail:
                    showToast(setMsg);
                    lockRightBtn(false);
                    break;
                case what_set_success:
                    showToast("设置成功");
                    finish2();
                    break;
            }
        }
    };

    private void lockRightBtn(boolean lock) {
        binding.includetitle.titleRight.setClickable(!lock);
        binding.setpwdPb.setVisibility(lock?View.VISIBLE:View.GONE);
    }
}
