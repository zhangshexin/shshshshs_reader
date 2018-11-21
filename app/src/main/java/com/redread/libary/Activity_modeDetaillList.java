package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLoadmoreBinding;
import com.redread.databinding.LayoutModelListBinding;
import com.redread.libary.adapter.Adapter_modelList;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.net.netbean.NetBeanBook;
import com.redread.net.netbean.NetBeanKindBook;
import com.redread.net.netbean.NetBeanKindPage;
import com.redread.net.netbean.NetBeanModel;
import com.redread.utils.RecyclerViewUtil;
import com.redread.widget.ProxyAdapter;

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
        binding = DataBindingUtil.setContentView(this, R.layout.layout_model_list);
        initView();
        loadData();
    }


    private Adapter_modelList adapter_modelList;
    private RecyclerViewUtil util;
    private List<NetBeanBook> books = new ArrayList<>();
    private NetBeanModel model;
    private NetBeanKindBook kindBook;

    private LayoutLoadmoreBinding loadmoreBinding;
    private LinearLayoutManager manager;

    private void initView() {

        binding.include5.titleLeft.setOnClickListener(this);

        util = new RecyclerViewUtil(this, binding.modelListList);
        adapter_modelList = new Adapter_modelList(this, books);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        binding.modelListList.setLayoutManager(manager);
        adapter_modelList.setAddedFooterView(true);
        binding.modelListList.setAdapter(adapter_modelList);

        util.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //显示图书详情
                startActivity(Activity_bookDetail.class, Activity_bookDetail.EXTR_BOOK, isKindBookList?kindBook.getBooks().getPageData().get(position):model.getBooks().getPageData().get(position));
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
            }
        });
    }

    /**
     * 是否要加载的是中图的书列表,用于区分加载的接口
     */
    public static String IS_KIND_BOOK="_iskindbook";
    public static String MODULE_ID = "_moduleid";

    ////////////////////分页参数///////////////////////
    private int pageCount=50;//一页多少条
    private int currentPage=0;//当前页
    ///////////////////////////////////////////
    private Call mCall;


    private final int WHAT_SUCCESS = 1;
    private final int WHAT_ERROR = -1;
    private final int WHAT_FAIL=0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //判断一下如保处理加载更多
            if(isKindBookList){
                if(!kindBook.getBooks().isHasNext()){
                    adapter_modelList.setAddedFooterView(false);
                }else{
                    adapter_modelList.setAddedFooterView(true);
                }
            }else{
                if(!model.getBooks().isHasNext()){
                    adapter_modelList.setAddedFooterView(false);
                }else{
                    adapter_modelList.setAddedFooterView(true);
                }
            }

            switch (msg.what) {
                case WHAT_SUCCESS:
                    adapter_modelList.notifyDataSetChanged();
                    //设置标题
                    String eTitle;
                    if(isKindBookList){
                        eTitle = kindBook.getName();
                    }else{
                        eTitle = model.getName();
                    }

                    binding.include5.titleTitle.setText(eTitle);
                    binding.pb.setVisibility(View.GONE);
                    break;
                case WHAT_ERROR:
                    showToast(R.string.net_service_fail);
                    if(books.size()==0) {
                        finish2();
                    }
                    loadmoreBinding.loadMore.setClickable(true);
                    break;
                case WHAT_FAIL:
                    if(books.size()==0) {
                        showToast("没找到书，请期待");
                        finish2();
                    }
                    break;
            }
            //加载更多按钮要收起加载中,且可以点击加载更多
            if (adapter_modelList.isAdded()) {
                View view = manager.findViewByPosition(books.size());
                LayoutLoadmoreBinding loadmoreBinding = DataBindingUtil.getBinding(view);
                loadmoreBinding.loadMorePB.setVisibility(View.GONE);
                loadmoreBinding.loadMore.setClickable(true);
            }
        }
    };
    private boolean isKindBookList;
    public void loadData() {
        isKindBookList=getIntent().getBooleanExtra(IS_KIND_BOOK,false);
        int id = getIntent().getIntExtra(MODULE_ID,-1);
        if (id<0) {
            finish2();
            return;
        }
        Request request;
        if(isKindBookList){
            request= Api.kindBookGet(id,books.size(),pageCount);
        }else{
            request= Api.modelListGet(id,books.size(),pageCount);
        }
        mCall = OkHttpManager.getInstance(this).getmOkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(WHAT_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();

                    if(isKindBookList){
                        kindBook = JSON.parseObject(json, NetBeanKindBook.class);
                        if (kindBook != null &&kindBook.getBooks()!=null&& kindBook.getBooks().getPageData()!=null&&kindBook.getBooks().getPageData().size() > 0) {
                            books.addAll(kindBook.getBooks().getPageData());
                            mHandler.sendEmptyMessage(WHAT_SUCCESS);
                        }else{
                            mHandler.sendEmptyMessageDelayed(WHAT_FAIL,2000);
                        }
                    }else{
                        //TODO
                        model = JSON.parseObject(json, NetBeanModel.class);
                        if (model != null &&model.getBooks()!=null&&model.getBooks().getPageData()!=null&& model.getBooks().getPageData().size() > 0) {
                            books.addAll(model.getBooks().getPageData());
                            mHandler.sendEmptyMessage(WHAT_SUCCESS);
                        }else{
                            mHandler.sendEmptyMessageDelayed(WHAT_FAIL,2000);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(WHAT_ERROR);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                finish2();
                break;
        }
    }
}
