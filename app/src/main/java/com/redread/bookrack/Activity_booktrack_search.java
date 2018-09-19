package com.redread.bookrack;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutBooktrackSearchBinding;

/**
 * Created by zhangshexin on 2018/9/17.
 * 本地图书搜索页
 */

public class Activity_booktrack_search extends BaseActivity {

    private LayoutBooktrackSearchBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.layout_booktrack_search);
        initView();
    }

    private void initView() {

    }
}
