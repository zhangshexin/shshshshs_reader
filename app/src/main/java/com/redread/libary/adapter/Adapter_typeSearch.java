package com.redread.libary.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.redread.R;
import com.redread.base.BaseRecycelAdapter;
import com.redread.base.BaseViewHolder;
import com.redread.databinding.LayoutTypesearchCellBinding;
import com.redread.net.netbean.NetBeanType;

import java.util.List;


/**
 * Created by zhangshexin on 2018/9/21.
 */

public class Adapter_typeSearch extends BaseRecycelAdapter<BaseViewHolder> {
    private  List<NetBeanType> types;
    public Adapter_typeSearch(Context context, List<NetBeanType> types) {
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
        NetBeanType type=types.get(position);
        if (position == clickPosition)
            binding.typeSearchTag.setVisibility(View.VISIBLE);
        else
            binding.typeSearchTag.setVisibility(View.GONE);

        binding.typeSearchName.setText(type.getName());
    }
}
