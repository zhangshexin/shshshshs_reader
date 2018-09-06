package com.redread.publish;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redread.R;
import com.redread.base.BaseFragment;
import com.redread.databinding.FragmentPublishBinding;

/**
 * Created by zhangshexin on 2018/8/31.
 *
 * 出版
 */

public class Fragment_publish extends BaseFragment {
    private FragmentPublishBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_publish,container,false);
        return binding.getRoot();
    }
}
