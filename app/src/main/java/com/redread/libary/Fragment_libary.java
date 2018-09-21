package com.redread.libary;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redread.Activity_home;
import com.redread.R;
import com.redread.base.BaseFragment;
import com.redread.databinding.FragmentLibaryBinding;
import com.redread.libary.adapter.Adapter_libaryModel;
import com.redread.net.netbean.NetBeanLibaryModel;
import com.redread.utils.GlideImageLoader;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshexin on 2018/8/31.
 * 馆藏
 */

public class Fragment_libary extends BaseFragment implements View.OnClickListener {

    private FragmentLibaryBinding binding;
    private Context mContext;
    private List<NetBeanLibaryModel> modelList=new ArrayList<>();
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
        loadDataFromNet();
    }

    /**
     * 从网络请求数据
     */
    private void loadDataFromNet() {
        //banner
        List images=new ArrayList<Integer>();
        images.add(R.drawable.defalut_publish_banner_0);
        images.add(R.drawable.defalut_publish_banner_1);
        images.add(R.drawable.defalut_publish_banner_2);
        binding.libaryBanner.setImageLoader(new GlideImageLoader());
        binding.libaryBanner.setImages(images);
        binding.libaryBanner.start();

        //通知
        List<String> info = new ArrayList<>();
        info.add("11111111111111");
        info.add("22222222222222");
        info.add("33333333333333");
        info.add("44444444444444");
        info.add("55555555555555");
        info.add("66666666666666");
        binding.marqueeView.startWithList(info);
        binding.marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                startActivity(Activity_bookDetail.class);
            }
        });

        //专业阅读
        for (int i=0;i<4;i++){
            NetBeanLibaryModel model=new NetBeanLibaryModel();
            modelList.add(model);
        }
        adapter.notifyDataSetChanged();
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
                startActivity(Activity_modeDetaillList.class,Activity_modeDetaillList.EXTRA_TITLE,"新书上架");
                break;
            case R.id.libary_themine:
                //去查看该模块的列表
                startActivity(Activity_modeDetaillList.class,Activity_modeDetaillList.EXTRA_TITLE,"主题阅读");
                break;
            case R.id.libary_typesearch://TODO
                startActivity(Activity_typeSearch.class);
                break;
            case R.id.libary_notifydetail_bnt:
                //去通知页
                //TODO
                startActivity(Activity_notifyList.class);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.libaryBanner.startAutoPlay();
        binding.marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.libaryBanner.stopAutoPlay();
        binding.marqueeView.stopFlipping();
    }



}
