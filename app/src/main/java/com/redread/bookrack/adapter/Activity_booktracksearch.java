package com.redread.bookrack.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import com.redread.R;
import com.redread.base.BaseRecycelAdapter;
import com.redread.base.BaseViewHolder;
import com.redread.databinding.LayoutBooktrackSearchCellBinding;
import com.redread.model.bean.Book;

import java.util.List;

/**
 * Created by zhangshexin on 2018/9/19.
 */

public class Activity_booktracksearch extends BaseRecycelAdapter<BaseViewHolder> {

    private Context context;
    private  List<Book> books;
    public Activity_booktracksearch(Context context, List<Book> books) {
        super(context);
        this.context=context;
        this.books=books;
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutBooktrackSearchCellBinding binding= DataBindingUtil.inflate(inflater, R.layout.layout_booktrack_search_cell,parent,false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        LayoutBooktrackSearchCellBinding binding=(LayoutBooktrackSearchCellBinding) holder.getBinding();
        Book book=books.get(position);

    }
}
