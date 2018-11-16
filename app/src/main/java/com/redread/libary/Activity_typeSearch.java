package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutTypesearchBinding;
import com.redread.libary.adapter.Adapter_typeSearch;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.net.netbean.NetBeanModel;
import com.redread.net.netbean.NetBeanType;
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
 * 分类检索页
 */

public class Activity_typeSearch extends BaseActivity implements View.OnClickListener {
    private LayoutTypesearchBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.layout_typesearch);
        initView();
    }

    private Adapter_typeSearch typeAdapter;
    private Adapter_typeSearch typeContentAdapter;

    private RecyclerViewUtil typeClickUtil;
    private RecyclerViewUtil typeContentClickUtil;
    private void initView() {
        binding.include4.titleTitle.setText(getTitle());

        binding.include4.titleLeft.setOnClickListener(this);


        typeClickUtil=new RecyclerViewUtil(this,binding.typeSearchType);
        typeClickUtil.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //类型单击事件,更换类型
                typeAdapter.setClickPosition(position);
                //刷新类型显示
                //TODO
                clickPositon=position;
                loadLittleTypeList(bigTypeList.get(position).getId()+"");
            }
        });

        typeContentClickUtil=new RecyclerViewUtil(this,binding.typeSearchTypeContent);
        typeContentClickUtil.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //类型内容单击，跳转到内容列表
                startActivity(Activity_modeDetaillList.class,Activity_modeDetaillList.MODULE_ID,"9");
            }
        });



        typeAdapter=new Adapter_typeSearch(this,bigTypeList);//类型适配器
        typeContentAdapter=new Adapter_typeSearch(this,littleTypeList);//类型内容适配器
        typeContentAdapter.setClickPosition(-1);

        LinearLayoutManager typeManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.typeSearchType.setLayoutManager(typeManager);
        binding.typeSearchType.setAdapter(typeAdapter);

        GridLayoutManager typeContentManager=new GridLayoutManager(this,3);
        binding.typeSearchTypeContent.setLayoutManager(typeContentManager);
        binding.typeSearchTypeContent.setAdapter(typeContentAdapter);

        loadBigTypeList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                finish2();
                break;
        }
    }
    private int clickPositon=0;
    //大类列表
    private List<NetBeanType> bigTypeList=new ArrayList<>();
    //小类列表
    private List<NetBeanType> littleTypeList=new ArrayList<>();

    private final int what_bigType_success=0;//大类加载成功
    private final int what_bigType_fail=1;//大类加载失败
    private final int what_littleType_success=10;//小类加载成功
    private final int what_littleType_faile=11;//小类加载失败
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            visiblityPB(false);
            switch (msg.what){
                case what_bigType_fail:
                    showToast(R.string.net_notify_fail);
                    finish2();
                    break;
                case what_bigType_success:
                    visiblityPB(true);
                    typeAdapter.notifyDataSetChanged();
                    typeAdapter.setClickPosition(0);
                    loadLittleTypeList(bigTypeList.get(0).getId()+"");
                    break;
                case what_littleType_faile:
                    showToast(R.string.net_notify_fail);
                    break;
                case what_littleType_success:
                    typeAdapter.setClickPosition(clickPositon);
                    typeContentAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private void visiblityPB(boolean show){
        binding.loadingPb.setVisibility(show?View.VISIBLE:View.GONE);
    }
    private Call bigCall;
    /**
     * 加载左侧的大类列表
     */
    private void loadBigTypeList(){
        visiblityPB(true);
        Request request= Api.typeBigGet();
        bigCall= OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        bigCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               mHandler.sendEmptyMessage(what_bigType_fail);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json=response.body().string();
                    List<NetBeanType> temp=JSON.parseArray(json,NetBeanType.class);
                    bigTypeList.addAll(temp);
                    mHandler.sendEmptyMessage(what_bigType_success);
                } catch (IOException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(what_bigType_fail);
                }
            }
        });
    }
    private Call littleCall;
    /**
     * 加载右侧的小类列表
     * @param id
     */
    private void loadLittleTypeList(String id){
        visiblityPB(true);
        Request request= Api.typeLittleGet(id);
        littleCall= OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        littleCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(what_littleType_faile);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json=response.body().string();
                    NetBeanType temp=JSON.parseObject(json,NetBeanType.class);
                    littleTypeList.clear();
                    littleTypeList.addAll(temp.getSubKindList());
                    mHandler.sendEmptyMessage(what_littleType_success);
                } catch (IOException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(what_littleType_faile);
                }
            }
        });
    }
}
