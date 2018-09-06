package com.redread.bookrack;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redread.R;
import com.redread.base.BaseFragment;
import com.redread.databinding.FragmentBooktrackBinding;

/**
 * Created by zhangshexin on 2018/8/31.
 *
 * 书架页
 */

public class Fragment_Booktrack extends BaseFragment {

    private String assetFile="yuanzun.txt";
    private String sdFile=Environment.getExternalStorageDirectory().getPath()+"/read";
    private FragmentBooktrackBinding binding;
//    putAssetsToSDCard(this,assetFile,sdFile);
//        Activity_txtReader.loadTxtFile(this, Environment.getExternalStorageDirectory().getPath()+"/read/yuanzun.txt");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_booktrack,container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.buttonTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
