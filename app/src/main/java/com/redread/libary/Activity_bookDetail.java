package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutBookdetailBinding;

/**
 * Created by zhangshexin on 2018/9/20.
 * 图书详情页
 */

public class Activity_bookDetail extends BaseActivity implements View.OnClickListener {

    private LayoutBookdetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.layout_bookdetail);
        binding.bookDetailBorrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bookDetail_borrow://推荐馆茂
                startActivity(Activity_recommend.class);
                break;
        }
    }
    // overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
}
