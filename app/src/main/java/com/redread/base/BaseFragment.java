package com.redread.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by zhangshexin on 2018/8/31.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerRxBus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeRxBus();
    }

    public void registerRxBus() {
    }

    public void removeRxBus() {
    }


    public void startActivity(Class<?> c){
        Intent intent=new Intent(getContext(),c);
        startActivity(intent);
    }
}
