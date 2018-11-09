package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutModelListBinding;
import com.redread.libary.adapter.Adapter_modelList;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.net.netbean.NetBeanBook;
import com.redread.net.netbean.NetBeanModel;
import com.redread.utils.RecyclerViewUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangshexin on 2018/9/20.
 * 根据不同的模块拉取不同数据列表
 */

public class Activity_modeDetaillList extends BaseActivity implements View.OnClickListener {
    private LayoutModelListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.layout_model_list);
        initView();
        loadData();
    }

    private Adapter_modelList adapter_modelList;
    private RecyclerViewUtil util;
    private List<NetBeanBook> books=new ArrayList<>();
    private NetBeanModel model;
    private void initView() {

        binding.include5.titleLeft.setOnClickListener(this);

        util=new RecyclerViewUtil(this,binding.modelListList);
        adapter_modelList=new Adapter_modelList(this,books);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        binding.modelListList.setLayoutManager(manager);
        binding.modelListList.setAdapter(adapter_modelList);

        util.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //显示图书详情
                startActivity(Activity_bookDetail.class,Activity_bookDetail.EXTR_BOOK,model.getBooks().get(position));
                overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
            }
        });
    }
    public static String MODULE_ID="_moduleid";
    private Call mCall;


    private final int WHAT_SUCCESS=1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_SUCCESS:
                    adapter_modelList.notifyDataSetChanged();
                    //设置标题
                    String eTitle=model.getName();
                    binding.include5.titleTitle.setText(eTitle);
                    binding.pb.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void loadData(){
        String moduleId=getIntent().getStringExtra(MODULE_ID);
        if(TextUtils.isEmpty(moduleId)){
            finish2();
            return;
        }
        Request request= Api.modelListGet(this,moduleId);
        mCall= OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finish2();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json=response.body().string();
                    model= JSON.parseObject(json,NetBeanModel.class);
                    books.addAll(model.getBooks());
                    mHandler.sendEmptyMessage(WHAT_SUCCESS);
                } catch (IOException e) {
                    e.printStackTrace();
                    finish2();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                finish2();
                break;
        }
    }
}
