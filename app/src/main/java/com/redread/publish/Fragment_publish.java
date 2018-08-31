package com.redread.publish;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(getActivity(), R.layout.fragment_booktrack);
    }
}
