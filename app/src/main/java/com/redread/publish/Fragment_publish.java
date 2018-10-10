package com.redread.publish;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redread.Activity_home;
import com.redread.R;
import com.redread.base.BaseFragment;
import com.redread.databinding.FragmentPublishBinding;
import com.redread.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshexin on 2018/8/31.
 *
 * 出版
 */

public class Fragment_publish extends BaseFragment {
    private FragmentPublishBinding binding;
    private List images=new ArrayList<Integer>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_publish,container,false);
        images.add(R.drawable.defalut_publish_banner_0);
        images.add(R.drawable.defalut_publish_banner_1);
        images.add(R.drawable.defalut_publish_banner_2);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.publishBanner.setImageLoader(new GlideImageLoader());
        binding.publishBanner.setImages(images);
        binding.publishBanner.start();
        //垂直滚动
        List<String> info = new ArrayList<>();
        info.add("最新图书《啦啦卡》");
        info.add("男生女生向前冲");
        info.add("开学了，让我们一起学新知识，读绘本");
        info.add("《三只小熊》外译版即将发售");
        info.add("《小狗比利》所有人都喜欢的书");
        info.add("9月18日北京大剧院上映《爱国不是英雄才行》");
        binding.marqueeView.startWithList(info);

        binding.include2.titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_home)getActivity()).toggleSlidNav();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.publishBanner.startAutoPlay();
        binding.marqueeView.startFlipping();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.publishBanner.stopAutoPlay();
        binding.marqueeView.stopFlipping();
    }

}
