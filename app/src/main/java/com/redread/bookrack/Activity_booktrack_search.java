package com.redread.bookrack;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.redread.MyApplication;
import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.bookrack.adapter.Adapter_booktracksearch;
import com.redread.databinding.LayoutBooktrackSearchBinding;
import com.redread.model.bean.Book;
import com.redread.model.entity.DownLoad;
import com.redread.model.gen.DownLoadDao;
import com.redread.utils.Constant;
import com.redread.utils.RecyclerViewUtil;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshexin on 2018/9/17.
 * 本地图书搜索页
 */

public class Activity_booktrack_search extends BaseActivity implements View.OnClickListener {

    private LayoutBooktrackSearchBinding binding;
    private List<Book> books=new ArrayList<>();
    private Adapter_booktracksearch adapter;

    private DownLoadDao dao;

    private RecyclerViewUtil util;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.layout_booktrack_search);
        dao= MyApplication.getInstances().getDaoSession().getDownLoadDao();
        initView();
    }

    /**
     * 执行搜索
     */
    private void doSearch() {
        //清理一下记录
        cleanSearchList();
        //判断是否有内容
        Editable editable=binding.bookTrackInclude.titleSearch.getText();
        if(editable==null|| TextUtils.isEmpty(editable.toString())){
            //弹土司
            showToast("搜索内容不能为空");
            return;
        }
        String keyworld=editable.toString();
        List<DownLoad> searchResult=dao.queryBuilder().where(new WhereCondition.StringCondition("BOOK_NAME like '%"+keyworld+"%'")).list();
        if(searchResult.size()==0){
            //如果没有结果
            binding.bookNull.setVisibility(View.VISIBLE);
        }else{
            books.addAll(Book.conver2ListBook(searchResult));
            adapter.notifyDataSetChanged();
            binding.bookNull.setVisibility(View.GONE);
        }

    }
    private void cleanSearchList(){
        books.clear();
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        binding.bookTrackInclude.titleLeft.setImageResource(R.drawable.icon_back);
        binding.bookTrackInclude.titleLeft.setOnClickListener(this);
        binding.bookTrackInclude.titleRight.setImageResource(R.drawable.icon_search);
        binding.bookTrackInclude.titleRight.setOnClickListener(this);

        //处理list
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter=new Adapter_booktracksearch(this,books);
        binding.booktrackSearchList.setLayoutManager(manager);
        binding.booktrackSearchList.setAdapter(adapter);

        binding.bookTrackInclude.titleSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable content=binding.bookTrackInclude.titleSearch.getText();
                if(content==null||TextUtils.isEmpty(content.toString())){
                    binding.bookNull.setVisibility(View.GONE);
                    cleanSearchList();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        util=new RecyclerViewUtil(this,binding.booktrackSearchList);
        util.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Book book= books.get(position);
                //去阅读
                if(book.getBookType().equals(Constant.BOOK_TYPE_TXT))
                    Activity_txtReader.loadTxtFile(Activity_booktrack_search.this,book.getBookDir(),book.getBookName());
                else if(book.getBookType().equals(Constant.BOOK_TYPE_PDF))
                {
                    Intent intent=new Intent(Activity_booktrack_search.this,Activity_pdfReader.class);
                    intent.putExtra("book",book);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                finish2();
                break;
            case R.id.title_right:
                //搜索
                doSearch();
                break;
        }
    }
}
