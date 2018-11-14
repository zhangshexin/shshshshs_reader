package com.redread.libary;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.redread.Activity_home;
import com.redread.R;
import com.redread.base.BaseFragment;
import com.redread.databinding.FragmentLibaryBinding;
import com.redread.libary.adapter.Adapter_libaryModel;
import com.redread.login.Activity_generalLogin;
import com.redread.net.Api;
import com.redread.net.OkHttpManager;
import com.redread.net.netbean.NetBeanLibary;
import com.redread.net.netbean.NetBeanModel;
import com.redread.utils.GlideImageLoader;
import com.redread.utils.SharePreferenceUtil;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangshexin on 2018/8/31.
 * 馆藏
 */

public class Fragment_libary extends BaseFragment implements View.OnClickListener {

    private String TAG=getClass().getName();
    private FragmentLibaryBinding binding;
    private Context mContext;
    private List<NetBeanModel> modelList=new ArrayList<>();
    private Adapter_libaryModel adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_libary,container,false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.include3.titleTitle.setText(R.string.label_libary);
        binding.include3.titleRight.setImageResource(R.drawable.icon_search);
        binding.include3.titleRight.setOnClickListener(this);
        binding.include3.titleLeft.setImageResource(R.drawable.default_head);
        binding.include3.titleLeft.setOnClickListener(this);
        //专业阅读
        adapter=new Adapter_libaryModel(mContext,modelList);
        LinearLayoutManager manager=new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        binding.libaryModellist.setLayoutManager(manager);
        binding.libaryModellist.setAdapter(adapter);
        //去查看该模块的列表
        binding.libaryNewbook.setOnClickListener(this);
        binding.libaryThemine.setOnClickListener(this);
        binding.libaryTypesearch.setOnClickListener(this);
        //去通知页
        binding.libaryNotifydetailBnt.setOnClickListener(this);

        //banner的点击事件
        binding.libaryBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e(TAG, "OnBannerClick: ----"+position );
            }
        });

        //通知的点击事件
        binding.marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                startActivity(Activity_bookDetail.class,Activity_bookDetail.EXTR_BOOK,libaryInfo.getNotifyList().get(position));
                getActivity().overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
            }
        });
        loadDataFromNet();
    }



    private final int what_net_faile=-1;
    private final int what_net_success=0;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case what_net_faile:
                    binding.failLay.setVisibility(View.VISIBLE);
                    binding.showLay.setVisibility(View.GONE);
                    //显示失败提示，收起加载提示
                    binding.loadingPb.setVisibility(View.GONE);
                    binding.cryImgNotify.setVisibility(View.VISIBLE);
                    break;
                case what_net_success:
                    //直接收起失败布局
                    binding.failLay.setVisibility(View.GONE);
                    binding.showLay.setVisibility(View.VISIBLE);
                    //banner
                    List images=new ArrayList<String>();
                    List titles=new ArrayList<String>();
                    for (int i=0;i<libaryInfo.getBannerList().size();i++){
                        images.add(libaryInfo.getBannerList().get(i).getImageUrl());
                        titles.add(libaryInfo.getBannerList().get(i).getName());
                    }
                    binding.libaryBanner.setImageLoader(new GlideImageLoader());
                    binding.libaryBanner.setImages(images);
                    binding.libaryBanner.setBannerTitles(titles);
                    binding.libaryBanner.start();

                    //通知
                    List<String> info = new ArrayList<>();
                    for (int i=0;i<libaryInfo.getNotifyList().size();i++){
                        info.add(libaryInfo.getNotifyList().get(i).getName());
                    }
                    binding.marqueeView.startWithList(info);

                    //专业阅读
                    modelList.addAll(libaryInfo.getModelList());
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };
    private Call mCall;
    private NetBeanLibary libaryInfo;
    /**
     * 从网络请求数据
     */
    private void loadDataFromNet() {
        Request request= Api.libaryInfoGet();
        mCall= OkHttpManager.getInstance(mContext).getmOkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(what_net_faile);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json=response.body().string();
                    Log.e(TAG, "onResponse:页面信息"+ json);
                    libaryInfo= JSON.parseObject(json,NetBeanLibary.class);
                    mHandler.sendEmptyMessage(what_net_success);
                } catch (IOException e) {
                    e.printStackTrace();
                    //不管怎样，反正错了，提示吧
                    mHandler.sendEmptyMessage(what_net_faile);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                //打个人中心
                ((Activity_home)getActivity()).toggleSlidNav();
                break;
            case R.id.title_right://TODO
                //去搜索馆藏
                startActivity(Activity_libarySearch.class);
                break;
            case R.id.libary_newbook:
                //去查看该模块的列表
                startActivity(Activity_modeDetaillList.class,Activity_modeDetaillList.MODULE_ID,"9");
                break;
            case R.id.libary_themine:
                //去查看该模块的列表
                startActivity(Activity_modeDetaillList.class,Activity_modeDetaillList.MODULE_ID,"9");
                break;
            case R.id.libary_typesearch://TODO
                //去分类检索
                startActivity(Activity_typeSearch.class);
                break;
            case R.id.libary_notifydetail_bnt:
                //去通知页
                //TODO---暂不处理
                startActivity(Activity_notifyList.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.libaryBanner.startAutoPlay();
        binding.marqueeView.startFlipping();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.libaryBanner.stopAutoPlay();
        binding.marqueeView.stopFlipping();
    }



}
