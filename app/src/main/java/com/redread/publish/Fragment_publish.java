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
        info.add("11111111111111");
        info.add("22222222222222");
        info.add("33333333333333");
        info.add("44444444444444");
        info.add("55555555555555");
        info.add("66666666666666");
        binding.marqueeView.startWithList(info);

        binding.include2.titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_home)getActivity()).toggleSlidNav();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.publishBanner.startAutoPlay();
        binding.marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.publishBanner.stopAutoPlay();
        binding.marqueeView.stopFlipping();
    }
}
