package com.redread.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.Serializable;


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

    public void startActivity(Class<?> c,String name, Serializable extr){
        Intent intent=new Intent(this,c);
        intent.putExtra(name,extr);
        startActivity(intent);
    }


    public void startActivity(Class<?> c,String ex1,String ex1Value){
        Intent intent=new Intent(this,c);
        intent.putExtra(ex1,ex1Value);
        startActivity(intent);

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerRxBus();
    }

    public void registerRxBus() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeRxBus();
    }

    public void removeRxBus() {
    }

    public void showToast(int res){
        showToast(getString(res));
    }
    public void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
