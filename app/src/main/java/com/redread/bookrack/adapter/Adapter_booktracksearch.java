package com.redread.bookrack.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.redread.R;
import com.redread.base.BaseRecycelAdapter;
import com.redread.base.BaseViewHolder;
import com.redread.databinding.LayoutBooktrackSearchCellBinding;
import com.redread.model.bean.Book;
import com.redread.utils.DateUtil;
import com.redread.utils.GlideUtils;

import java.util.List;

/**
 * Created by zhangshexin on 2018/9/19.
 */

public class Adapter_booktracksearch extends BaseRecycelAdapter<BaseViewHolder> {

    private Context context;
    private  List<Book> books;
    public Adapter_booktracksearch(Context context, List<Book> books) {
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
        binding.booktracksearchCellBookname.setText(book.getBookName());
        //阅读进度
        binding.booktracksearchCellProgressInt.setText(book.getReadProgress() + "%");
        binding.booktracksearchCellProgress.setMax(100);
        binding.booktracksearchCellProgress.setProgress(book.getReadProgress());
        //作者
        String author=book.getAuthor();
        if(TextUtils.isEmpty(author)){
            binding.booktracksearchCellAuthor.setVisibility(View.INVISIBLE);
        }else{
            binding.booktracksearchCellAuthor.setVisibility(View.VISIBLE);
            binding.booktracksearchCellAuthor.setText(author);
        }
        //如果有封面则显示
        if (!TextUtils.isEmpty(book.getCoverUrl())) {
            GlideUtils.glideLoader(mContext, book.getCoverUrl(), R.drawable.side_nav_bar, R.drawable.side_nav_bar, binding.booktracksearchCellCover);
        }
        //日期
        String redDate= DateUtil.dateFormat.format(book.getUpDate());
        binding.booktracksearchCellDate.setText(redDate);
    }
}
