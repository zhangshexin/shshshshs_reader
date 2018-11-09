package com.redread;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutSplashBinding;
import com.redread.model.entity.DownLoad;
import com.redread.model.gen.DownLoadDao;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.utils.Constant;
import com.redread.utils.GlideUtils;
import com.redread.utils.IOUtile;
import com.redread.utils.SharePreferenceUtil;

import org.greenrobot.greendao.query.WhereCondition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangshexin on 2018/8/31.
 */

public class Activity_splash extends BaseActivity implements View.OnClickListener {
    private final String TAG=getClass().getName();
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
                case 1:
                    GlideUtils.glideLoader(Activity_splash.this,Constant.picture+"/splash.jpg",R.drawable.ad,R.drawable.ad,binding.splashImg);
                    mHandler.sendEmptyMessageDelayed(0, 1000);
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
        File splashFile=new File(Constant.picture+"/splash.jpg");
        if(splashFile.exists()){
            GlideUtils.glideLoader(this,Constant.picture+"/splash.jpg",R.drawable.ad,R.drawable.ad,binding.splashImg);
        }else{
            GlideUtils.LoadImageWithLocation(this,R.drawable.ad,binding.splashImg);
        }
        initDBData();
        loadSplashPic();
    }

    /**
     * 下载启动图
     */
    private void loadSplashPic() {
        Request request= new Request.Builder().url(Api.downUrl + "splash.jpg").build();
        Call mCall= OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    File picDir=new File(Constant.picture);
                    if(!picDir.isDirectory())
                        picDir.mkdirs();
                    File splashFile=new File(picDir,"splash.jpg");
                    InputStream ins=response.body().byteStream();
                    FileOutputStream fos=new FileOutputStream(splashFile);
                    byte[] buf=new byte[1024];
                    int length;
                    while((length=ins.read(buf))!=-1){
                        fos.write(buf,0,length);
                    }
                    fos.flush();
                    ins.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        });
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
        downLoad.setStatus(Constant.DOWN_STATUS_SUCCESS);
        downLoad.setBookName("元尊");
        downLoad.setBookDir(Constant.bookPath+ "/defaultbook.txt");
        downLoad.setUpDate(new Date(System.currentTimeMillis()));
        downLoad.setBookType(Constant.BOOK_TYPE_TXT);
        DownLoadDao dao= MyApplication.getInstances().getDaoSession().getDownLoadDao();
        dao.insert(downLoad);
        SharePreferenceUtil.saveSimpleData(this,Constant.KEY_BOOLEAN_FIRST_USE,false);

        //把所有等待下载的更新为暂停
        List<DownLoad> searchResult = dao.queryBuilder().where(new WhereCondition.StringCondition("status =" + Constant.DOWN_STATUS_WAIT )).list();
        for (int i=0;i<searchResult.size();i++){
            searchResult.get(i).setStatus(Constant.DOWN_STATUS_PAUS);
        }
        dao.updateInTx(searchResult);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
