package com.redread.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by zhangshexin on 2018/8/31.
 */

public class BaseActivity extends AppCompatActivity {

    public void finish2(){
        finish();
    }

    public void startActivity(Class<?> c){
        Intent intent=new Intent(this,c);
        startActivity(intent);
    }
}
