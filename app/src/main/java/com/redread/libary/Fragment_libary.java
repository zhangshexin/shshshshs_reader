package com.redread.libary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.redread.R;
import com.redread.base.BaseFragment;
import com.redread.databinding.FragmentLibaryBinding;

/**
 * Created by zhangshexin on 2018/8/31.
 * 馆藏
 */

public class Fragment_libary extends BaseFragment {

    private FragmentLibaryBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(getActivity(), R.layout.fragment_booktrack);
    }
}
