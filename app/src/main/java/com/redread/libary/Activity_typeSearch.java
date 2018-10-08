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
import com.redread.utils.RecyclerViewUtil;

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
            }
        });

        typeContentClickUtil=new RecyclerViewUtil(this,binding.typeSearchTypeContent);
        typeContentClickUtil.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //类型内容单击，跳转到内容列表
                startActivity(Activity_modeDetaillList.class,Activity_modeDetaillList.EXTRA_TITLE,"中图分类-1");
            }
        });



        typeAdapter=new Adapter_typeSearch(this);//类型适配器
        typeContentAdapter=new Adapter_typeSearch(this);//类型内容适配器
        typeContentAdapter.setClickPosition(-1);

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
