package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutTypesearchBinding;
import com.redread.libary.adapter.Adapter_typeSearch;

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
    private void initView() {
        binding.include4.titleTitle.setText(getTitle());

        binding.include4.titleLeft.setOnClickListener(this);

        typeAdapter=new Adapter_typeSearch(this);
        typeContentAdapter=new Adapter_typeSearch(this);

        LinearLayoutManager typeManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.typeSearchType.setLayoutManager(typeManager);
        binding.typeSearchType.setAdapter(typeAdapter);

        GridLayoutManager typeContentManager=new GridLayoutManager(this,3);
        binding.typeSearchTypeContent.setLayoutManager(typeContentManager);
        binding.typeSearchTypeContent.setAdapter(typeContentAdapter);

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
