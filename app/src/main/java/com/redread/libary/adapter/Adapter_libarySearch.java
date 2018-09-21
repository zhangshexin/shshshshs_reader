package com.redread.libary.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import com.redread.R;
import com.redread.base.BaseRecycelAdapter;
import com.redread.base.BaseViewHolder;
import com.redread.databinding.LayoutCellBookBinding;

/**
 * Created by zhangshexin on 2018/9/21.
 */

public class Adapter_libarySearch extends BaseRecycelAdapter<BaseViewHolder> {
    public Adapter_libarySearch(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutCellBookBinding binding= DataBindingUtil.inflate(inflater, R.layout.layout_cell_book,parent,false);
        return new BaseViewHolder(binding);
    }
}
