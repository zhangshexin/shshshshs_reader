package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLibarySearchBinding;
import com.redread.libary.adapter.Adapter_libarySearch;
import com.redread.utils.RecyclerViewUtil;
import com.redread.utils.SystemUtil;

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

    private void initView() {
        util = new RecyclerViewUtil(this, binding.libarySearchResultList);
        util.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                startActivity(Activity_bookDetail.class);
            }
        });
        binding.include6.titleLeft.setImageResource(R.drawable.icon_back);

        adapter = new Adapter_libarySearch(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.libarySearchResultList.setLayoutManager(manager);

        binding.include6.titleSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    SystemUtil.hide_keyboard_from(Activity_libarySearch.this, binding.include6.titleSearch);
                    //"开始搜索");
                    doSearch();
                    return true;
                }
                return false;
            }
        });
    }


    private void doSearch() {
        binding.libarySearchResultList.setAdapter(adapter);
    }
}
