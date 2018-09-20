package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutLibaryRecommendBinding;

/**
 * Created by zhangshexin on 2018/9/20.
 * 推荐馆藏
 */

public class Activity_recommend extends BaseActivity {
    private LayoutLibaryRecommendBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_libary_recommend);
        initView();
    }

    private void initView() {
        binding.recommendInclude.titleTitle.setText(getTitle());
    }
}
