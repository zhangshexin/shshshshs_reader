package com.redread.bookrack;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

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

//    putAssetsToSDCard(this,assetFile,sdFile);
//        Activity_txtReader.loadTxtFile(this, Environment.getExternalStorageDirectory().getPath()+"/read/yuanzun.txt");
    private FragmentBooktrackBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(getActivity(), R.layout.fragment_booktrack);
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
