package com.redread.bookrack;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.redread.Activity_home;
import com.redread.MyApplication;
import com.redread.R;
import com.redread.base.BaseFragment;
import com.redread.bookrack.adapter.Adapter_booktrack;
import com.redread.databinding.FragmentBooktrackBinding;
import com.redread.model.bean.Book;
import com.redread.model.entity.DownLoad;
import com.redread.model.gen.DownLoadDao;
import com.redread.rxbus.RxBus;
import com.redread.rxbus.RxSubscriptions;
import com.redread.rxbus.bean.RXRefreshBooktract;
import com.redread.utils.Constant;
import com.redread.utils.RecyclerViewUtil;
import com.redread.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangshexin on 2018/8/31.
 * <p>
 * 书架页
 */

public class Fragment_Booktrack extends BaseFragment implements View.OnClickListener {
    private String TAG=getClass().getName();
    //分页参数
    private int pageCount = 20;//一页几条
    private int currentPage = 1;//当前第几页

    private FragmentBooktrackBinding binding;
    private DownLoadDao dao;
    private List<Book> books = new ArrayList<>();

    private Adapter_booktrack adapter;

    private final int what_notify=1;
    private final int what_refresh=2;//刷新数据
    private final int what_cancel=3;//取消删除按钮
    private final int what_null=4;//没有书
    private Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case what_refresh:
                    refreshBookTrack();
                    break;
                case what_notify:
                    adapter.notifyDataSetChanged();
                    break;
                case what_cancel:
                    showBottomDeleteLay(false);
                    break;
                case what_null:
                    binding.bookNull.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private RecyclerViewUtil recyclerViewUtil;
    //是否可点击操作,在长按未归位前不可以执行
    private boolean clickEnable=true;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding==null)
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booktrack, container, false);
        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshBookTrack();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dao = MyApplication.getInstances().getDaoSession().getDownLoadDao();
        mContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewUtil=new RecyclerViewUtil(MyApplication.getInstances(),binding.bookTrack);
        binding.bookTrackInclude.titleTitle.setText(R.string.booktrack);
        binding.bookTrackInclude.titleLeft.setImageResource(R.drawable.default_head);
        binding.bookTrackInclude.titleLeft.setOnClickListener(this);
        binding.bookTrackInclude.titleRight.setImageResource(R.drawable.icon_search);
        binding.bookTrackInclude.titleRight.setOnClickListener(this);
        binding.bookTrackBtnDelete.setOnClickListener(this);
        binding.bookTrackBtnCancel.setOnClickListener(this);
        //隐藏显示底部操作
        binding.bookTrackDeleteLay.setVisibility(View.GONE);

        adapter = new Adapter_booktrack(mContext, books);
        //TODO
        //监听滑动是否到底部，准备加载更多
        recyclerViewUtil.setOnLoadMoreListener(new RecyclerViewUtil.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //翻页
                currentPage++;
            }
        });
        recyclerViewUtil.setOnItemClickListener(new RecyclerViewUtil.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if(clickEnable){
                    DownLoad book= books.get(position);
                    //去阅读
                    if(book.getBookType().equals(Constant.BOOK_TYPE_TXT))
                        Activity_txtReader.loadTxtFile(getContext(),book.getBookDir(),book.getBookName(),book.getId());
                    else if(book.getBookType().equals(Constant.BOOK_TYPE_PDF))
                    {
                        Intent intent=new Intent(getContext(),Activity_pdfReader.class);
                        Book _book=Book.conver2Book(book);
                        intent.putExtra("book",_book);
                        getActivity().startActivity(intent);
                    }
                }else{
                    CheckBox checkBox=view.findViewById(R.id.booktrack_cell_check);
                    checkBox.setChecked(!checkBox.isChecked());
                    books.get(position).setChecked(checkBox.isChecked());
                }
            }
        });
        recyclerViewUtil.setOnItemLongClickListener(new RecyclerViewUtil.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position, View view) {
                showBottomDeleteLay(true);
            }
        });
        //处理recyclerview
        GridLayoutManager manager = new GridLayoutManager(MyApplication.getInstances(), 3);
        binding.bookTrack.setLayoutManager(manager);
        binding.bookTrack.setAdapter(adapter);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }).start();
    }



    private  boolean isLoading=false;
    //加载书架中的书
    private void initData() {
        try {
            if(isLoading)
                return;
            isLoading=true;
            //重新初始化参数
            currentPage=1;
            recyclerViewUtil.setLoadMoreEnable(true);
            books.clear();
            List<DownLoad> temp = dao.queryBuilder().offset(currentPage == 1 ? 0 * pageCount : currentPage * pageCount).limit(pageCount).list();
            if (temp .size()!=0) {
                //没有更多了
                Log.e(TAG, "initData: ============="+ System.currentTimeMillis());
                books.addAll(Book.conver2ListBook(temp));
                Log.e(TAG, "initData: -------------"+ System.currentTimeMillis());
                //如果不足一页将不再加载更多
                if(temp.size() < pageCount)
                    recyclerViewUtil.setLoadMoreEnable(false);
                myHandler.sendEmptyMessage(what_notify);
            } else{
                myHandler.sendEmptyMessage(what_null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            isLoading=false;
        }
    }

    @Override
    public void onDestroy() {
        myHandler.removeMessages(what_notify);
        super.onDestroy();
    }

    private void showBottomDeleteLay(boolean show){
        clickEnable=!show;
        binding.bookTrackDeleteLay.setVisibility(show?View.VISIBLE:View.GONE);
        adapter.showCheckBtn(show);
    }

    /**
     * TODO
     * 刷新书架，当有下载新书或删除书时刷新
     */
    private void refreshBookTrack() {
        //重新加载
        initData();
        showBottomDeleteLay(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                ((Activity_home) getActivity()).toggleSlidNav();
                break;
            case R.id.title_right:
                //去搜索页
                ((Activity_home) getActivity()).startActivity(Activity_booktrack_search.class);
                break;
            case R.id.book_track_btn_cancel:
                //取消删除
                showBottomDeleteLay(false);
                break;
            case R.id.book_track_btn_delete:
                //删除选中的本地图书
                adapter.deleteSelectBooks();
                break;
        }
    }


    private Subscription mSubscription;
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscription = RxBus.getDefault().toObservable(RXRefreshBooktract.class)
                .subscribe(new Action1<RXRefreshBooktract>() {
                    @Override
                    public void call(RXRefreshBooktract what) {
                        if(what.getStatue()==RXRefreshBooktract.STATUE_REFRESH){
                            myHandler.sendEmptyMessage(what_refresh);
                        }else
                            myHandler.sendEmptyMessage(what_cancel);
                    }
                });
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscription);
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mSubscription);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
