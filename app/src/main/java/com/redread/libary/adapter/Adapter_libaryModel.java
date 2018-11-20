package com.redread.libary.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.redread.Activity_home;
import com.redread.R;
import com.redread.base.BaseRecycelAdapter;
import com.redread.base.BaseViewHolder;
import com.redread.databinding.LayoutLibarymodelCellBinding;
import com.redread.libary.Activity_bookDetail;
import com.redread.libary.Activity_modeDetaillList;
import com.redread.net.Api;
import com.redread.net.netbean.NetBeanModel;
import com.redread.utils.GlideUtils;

import java.util.List;

/**
 * Created by zhangshexin on 2018/9/20.
 */

public class Adapter_libaryModel extends BaseRecycelAdapter<BaseViewHolder> {
    private String TAG = getClass().getName();
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
        binding.libarymodelCellModelName.setText(model.getName());
        binding.libarymodelCellDescript.setText(model.getDescription());
        //处理一行的三本书的显示
        if (model.getBooks().size() >= 1) {
            GlideUtils.glideLoader(mContext, Api.downUrl + model.getBooks().get(0).getCoverPath(), R.drawable.side_nav_bar, R.drawable.side_nav_bar, binding.libarymodelCellCover1);
            binding.libarymodelCellName1.setText(model.getBooks().get(0).getName());
        } else {
            binding.libarymodelCell1.setVisibility(View.GONE);
        }
        if (model.getBooks().size() >= 2) {
            GlideUtils.glideLoader(mContext, Api.downUrl + model.getBooks().get(1).getCoverPath(), R.drawable.side_nav_bar, R.drawable.side_nav_bar, binding.libarymodelCellCover2);
            binding.libarymodelCellName1.setText(model.getBooks().get(1).getName());
        } else {
            binding.libarymodelCell2.setVisibility(View.GONE);
        }
        if (model.getBooks().size() >= 3) {
            GlideUtils.glideLoader(mContext, Api.downUrl + model.getBooks().get(2).getCoverPath(), R.drawable.side_nav_bar, R.drawable.side_nav_bar, binding.libarymodelCellCover2);
            binding.libarymodelCellName1.setText(model.getBooks().get(2).getName());
        } else {
            binding.libarymodelCell3.setVisibility(View.GONE);
        }
    }

    /**
     * 去图书详情页
     *
     * @param position
     */
    public void goBookDetail(int position, int bookP) {
        Log.e(TAG, bookP + "--------goBookDetail: ==================" + position);
        Intent intent = new Intent(mContext, Activity_bookDetail.class);
        intent.putExtra(Activity_bookDetail.EXTR_BOOK,models.get(position).getBooks().get(bookP-1));
        mContext.startActivity(intent);
        ((Activity_home) mContext).overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
    }

    /**
     * 去模块详情页
     *
     * @param position
     */
    public void goModelDetailList(int position) {
        Intent goModelIntent = new Intent(mContext, Activity_modeDetaillList.class);
        goModelIntent.putExtra(Activity_modeDetaillList.MODULE_ID, models.get(position).getId());
        mContext.startActivity(goModelIntent);
    }
}
