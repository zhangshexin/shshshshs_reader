package com.redread.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by zhangshexin on 2018/9/17.
 */

public class BaseRecycelAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


    public LayoutInflater inflater;
    public Context mContext;
    public BaseRecycelAdapter(Context context) {
        this.mContext = context;
        inflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
