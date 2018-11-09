package com.redread.libary.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import com.redread.R;
import com.redread.base.BaseRecycelAdapter;
import com.redread.base.BaseViewHolder;
import com.redread.databinding.LayoutCellBookBinding;
import com.redread.databinding.LayoutModelListBinding;
import com.redread.net.Api;
import com.redread.net.netbean.NetBeanBook;
import com.redread.utils.GlideUtils;

import java.util.List;

/**
 * Created by zhangshexin on 2018/9/21.
 * 模块列表
 */

public class Adapter_modelList extends BaseRecycelAdapter<BaseViewHolder> {
    private List<NetBeanBook> books;
    private Context mContext;

    public Adapter_modelList(Context context, List<NetBeanBook> books) {
        super(context);
        this.mContext = context;
        this.books = books;
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutCellBookBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_cell_book, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        LayoutCellBookBinding binding = (LayoutCellBookBinding) holder.getBinding();
        NetBeanBook book = books.get(position);
        binding.bookAuthor.setText(book.getAuthor());
        binding.bookDes.setText(book.getIntroduction());
        binding.bookName.setText(book.getName());
        GlideUtils.LoadImage(mContext, Api.downUrl + book.getCoverPath(), binding.bookCover);
    }
}
