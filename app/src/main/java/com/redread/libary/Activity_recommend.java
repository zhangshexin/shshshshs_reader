package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLibaryRecommendBinding;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangshexin on 2018/9/20.
 * 推荐馆藏
 */

public class Activity_recommend extends BaseActivity implements View.OnClickListener {
    private final String TAG=getClass().getName();
    private LayoutLibaryRecommendBinding binding;
    private final int response_fail = 0;
    private final int response_success = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case response_fail:
                    showToast(R.string.net_notify_fail);
                    lockBtn(false);
                    break;
                case response_success:
                    showToast(R.string.net_notify_success);
                    finish2();
                    break;
            }
        }
    };



    private Call mCall;

    /**
     * 热行馆藏推荐
     */
    private void doRecommend() {
        lockBtn(true);
        Request mRequest = Api.recommendPost(this, "11");
        mCall = OkHttpManager.getInstance(this).getmOkHttpClient().newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(response_fail);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e(TAG, "onResponse: 推荐成功" + json);
                mHandler.sendEmptyMessage(response_success);
            }
        });
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_libary_recommend);
        initView();
    }

    private void initView() {
        binding.recommendInclude.titleTitle.setText(getTitle());

        binding.recommendInclude.titleLeft.setOnClickListener(this);
        binding.recommendSure.setOnClickListener(this);
    }


    private void lockBtn(boolean lock){
        binding.recommendSure.setClickable(!lock);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                finish2();
                break;
            case R.id.recommend_sure:
                //提交馆藏
                doRecommend();
                break;
        }
    }
}
