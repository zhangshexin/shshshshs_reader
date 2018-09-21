package com.redread;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutSplashBinding;
import com.redread.model.entity.DownLoad;
import com.redread.model.gen.DownLoadDao;
import com.redread.utils.Constant;
import com.redread.utils.IOUtile;
import com.redread.utils.SharePreferenceUtil;

import java.util.Date;

/**
 * Created by zhangshexin on 2018/8/31.
 */

public class Activity_splash extends BaseActivity implements View.OnClickListener {
    private LayoutSplashBinding binding;
    private int plus = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            plus++;
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (plus > 5) {
                        Intent intent = new Intent(Activity_splash.this, Activity_home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        binding.circleIndicator.setProgress(plus);
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_splash);
        binding.splashJump.setOnClickListener(this);
        binding.circleIndicator.setOnClickListener(this);
        initDBData();
    }


    /**
     * 初始化一些必要数据，写入一本书
     */
    private synchronized void initDBData(){
        Boolean isFirstEntry=(Boolean)SharePreferenceUtil.getSimpleData(this,Constant.KEY_BOOLEAN_FIRST_USE,true);
        if(!isFirstEntry)
            return;
        //把书放到sd卡下
        IOUtile.putAssetsToSDCard(this, "defaultbook.txt",Constant.bookPath);
        DownLoad downLoad=new DownLoad();
        downLoad.setBookName("元尊");
        downLoad.setBookDir(Constant.bookPath+ "/defaultbook.txt");
        downLoad.setUpDate(new Date(System.currentTimeMillis()));
        downLoad.setBookType(Constant.BOOK_TYPE_TXT);
        DownLoadDao dao= MyApplication.getInstances().getDaoSession().getDownLoadDao();
        dao.insert(downLoad);
        SharePreferenceUtil.saveSimpleData(this,Constant.KEY_BOOLEAN_FIRST_USE,false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(0);
    }

    @Override
    public void onBackPressed() {
        mHandler.removeMessages(0);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.splash_jump:
            case R.id.circleIndicator:
                plus=10;
                mHandler.sendEmptyMessage(0);
                break;
        }
    }
}
