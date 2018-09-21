package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutModelListBinding;
import com.redread.libary.adapter.Adapter_modelList;
import com.redread.utils.RecyclerViewUtil;

/**
 * Created by zhangshexin on 2018/9/20.
 * 根据不同的模块拉取不同数据列表
 */

public class Activity_modeDetaillList extends BaseActivity implements View.OnClickListener {
    public static  String EXTRA_TITLE="EXTRA_TITLE";
    private LayoutModelListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.layout_model_list);
        initView();
    }

    private Adapter_modelList adapter_modelList;
    private RecyclerViewUtil util;
    private void initView() {
        //设置标题
        String eTitle=getIntent().getStringExtra(EXTRA_TITLE);
        binding.include5.titleTitle.setText(eTitle);
        binding.include5.titleLeft.setOnClickListener(this);

        util=new RecyclerViewUtil(this,binding.modelListList);
        adapter_modelList=new Adapter_modelList(this);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        binding.modelListList.setLayoutManager(manager);
        binding.modelListList.setAdapter(adapter_modelList);

        util.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //显示图书详情
                startActivity(Activity_bookDetail.class);
            }
        });
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
