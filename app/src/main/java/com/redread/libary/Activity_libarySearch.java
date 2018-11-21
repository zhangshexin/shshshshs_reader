package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLibarySearchBinding;
import com.redread.databinding.LayoutLoadmoreBinding;
import com.redread.libary.adapter.Adapter_libarySearch;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.net.netbean.NetBeanBook;
import com.redread.net.netbean.NetBeanBookPage;
import com.redread.net.netbean.NetBeanKindBook;
import com.redread.net.netbean.NetBeanModel;
import com.redread.net.netbean.NetBeanModelPage;
import com.redread.utils.RecyclerViewUtil;
import com.redread.utils.SystemUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangshexin on 2018/9/21.
 * 馆藏搜索
 */

public class Activity_libarySearch extends BaseActivity {
    private LayoutLibarySearchBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_libary_search);
        initView();
    }


    private Adapter_libarySearch adapter;
    private RecyclerViewUtil util;
    private LinearLayoutManager manager;

    private void initView() {
        util = new RecyclerViewUtil(this, binding.libarySearchResultList);
        util.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                startActivity(Activity_bookDetail.class, Activity_bookDetail.EXTR_BOOK, searchResultList.get(position));
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
            }
        });
        binding.include6.titleLeft.setImageResource(R.drawable.icon_back);

        adapter = new Adapter_libarySearch(this, searchResultList);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.libarySearchResultList.setLayoutManager(manager);
        binding.libarySearchResultList.setAdapter(adapter);

        binding.include6.titleSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                CharSequence text = v.getText();
                if (text == null || TextUtils.isEmpty(text.toString())) {
                    showToast("请输入书名");
                    return false;
                }
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    searchResultList.clear();//清除内容重新搜
                    SystemUtil.hide_keyboard_from(Activity_libarySearch.this, binding.include6.titleSearch);
                    //"开始搜索");
                    doSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private final int WHAT_SUCCESS = 1;
    private final int WHAT_ERROR = -1;
    private final int WHAT_FAIL = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if(!page.isHasNext()){
                adapter.setAddedFooterView(false);
            }else{
                adapter.setAddedFooterView(true);
            }

            binding.loadingPb.setVisibility(View.GONE);

            switch (msg.what) {
                case WHAT_SUCCESS:
                    adapter.notifyDataSetChanged();
                    break;
                case WHAT_ERROR:
                    if (searchResultList.size() == 0)
                        showNUllView();
                    break;
                case WHAT_FAIL:
                    if (searchResultList.size() == 0)
                        showNUllView();
                    break;
            }
            //加载更多按钮要收起加载中,且可以点击加载更多
            if (adapter.isAdded()) {
                View view = manager.findViewByPosition(searchResultList.size());
                LayoutLoadmoreBinding loadmoreBinding = DataBindingUtil.getBinding(view);
                loadmoreBinding.loadMorePB.setVisibility(View.GONE);
                loadmoreBinding.loadMore.setClickable(true);
            }

        }
    };

    /**
     * 显示空提示
     */
    private void showNUllView() {
        showToast("没有找到这本书");
    }

    private int pageCount = 50;
    private NetBeanBookPage page;
    private List<NetBeanBook> searchResultList = new ArrayList<>();
    private Call mCall;
    /**
     * 搜索
     */
    public void doSearch() {
        String keyword = binding.include6.titleSearch.getText().toString();
        binding.loadingPb.setVisibility(View.VISIBLE);
        try {
            Request request = Api.searchBookGet(keyword, searchResultList.size(), pageCount);
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
                        page = JSON.parseObject(json, NetBeanBookPage.class);
                        if (page != null && page.getPageData() != null && page.getPageData().size() > 0) {
                            searchResultList.addAll(page.getPageData());
                            mHandler.sendEmptyMessage(WHAT_SUCCESS);
                        } else {
                            mHandler.sendEmptyMessage(WHAT_FAIL);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        mHandler.sendEmptyMessage(WHAT_ERROR);
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(WHAT_ERROR);
        }
    }
}
