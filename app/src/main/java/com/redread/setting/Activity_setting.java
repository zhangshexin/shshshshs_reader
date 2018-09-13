package com.redread.setting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutSettingBinding;

/**
 * Created by zhangshexin on 2018/9/12.
 * 设置
 */

public class Activity_setting extends BaseActivity implements View.OnClickListener {

    private LayoutSettingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.layout_setting);
        initView();
    }

    private void initView() {
        binding.include.titleTitle.setText(getTitle().toString());
        binding.include.titleLeft.setOnClickListener(this);
        binding.percenterCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                finish2();
                break;
            case R.id.percenter_cancel:
                finish2();
                break;
        }
    }
}
