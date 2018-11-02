package com.redread.libary.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.ViewGroup;

import com.redread.Activity_home;
import com.redread.R;
import com.redread.base.BaseRecycelAdapter;
import com.redread.base.BaseViewHolder;
import com.redread.databinding.LayoutLibarymodelCellBinding;
import com.redread.libary.Activity_bookDetail;
import com.redread.libary.Activity_modeDetaillList;
import com.redread.net.netbean.NetBeanModel;

import java.util.List;

/**
 * Created by zhangshexin on 2018/9/20.
 */

public class Adapter_libaryModel extends BaseRecycelAdapter<BaseViewHolder> {
    private String TAG=getClass().getName();
    private List<NetBeanModel> models;

    public Adapter_libaryModel(Context context, List<NetBeanModel> models) {
        super(context);
        this.models = models;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutLibarymodelCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_libarymodel_cell, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        NetBeanModel model = models.get(0);
        LayoutLibarymodelCellBinding binding = (LayoutLibarymodelCellBinding) holder.getBinding();
        binding.setAdapter(this);
        binding.setPosition(0);
    }

    /**
     * 去图书详情页
     * @param position
     */
    public void goBookDetail(int position,int bookP){
        Log.e(TAG, bookP+"--------goBookDetail: =================="+position );
        Intent intent=new Intent(mContext,Activity_bookDetail.class);
        mContext.startActivity(intent);
        ((Activity_home)mContext).overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
    }

    /**
     * 去模块详情页
     * @param position
     */
    public void goModelDetailList(int position){
        Intent intent=new Intent(mContext,Activity_modeDetaillList.class);
        intent.putExtra(Activity_modeDetaillList.EXTRA_TITLE,"匠心大国");
        mContext.startActivity(intent);
    }
}
