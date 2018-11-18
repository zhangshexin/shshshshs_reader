package com.redread.login.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.redread.R;
import com.redread.base.BaseRecycelAdapter;
import com.redread.base.BaseViewHolder;
import com.redread.databinding.LayoutTypesearchCellBinding;
import com.redread.net.netbean.NetBeanDept;
import com.redread.net.netbean.NetBeanType;

import java.util.List;


/**
 * Created by zhangshexin on 2018/9/21.
 * 机构适配器
 */

public class Adapter_dept extends BaseRecycelAdapter<BaseViewHolder> {
    private  List<NetBeanDept> types;
    public Adapter_dept(Context context, List<NetBeanDept> types) {
        super(context);
        this.types=types;
    }

    @Override
    public int getItemCount() {
        return types.size();
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutTypesearchCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_typesearch_cell, parent, false);
        return new BaseViewHolder(binding);
    }


    private int clickPosition = 0;//默认选中第一个

    public void setClickPosition(int position){
        this.clickPosition=position;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        LayoutTypesearchCellBinding binding = (LayoutTypesearchCellBinding) holder.getBinding();
        NetBeanDept type=types.get(position);
//        if (position == clickPosition)
//            binding.typeSearchTag.setVisibility(View.VISIBLE);
//        else
        binding.typeSearchTag.setVisibility(View.GONE);

        binding.typeSearchName.setText(type.getName());
    }
}
