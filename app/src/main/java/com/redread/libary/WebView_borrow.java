package com.redread.libary;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.databinding.LayoutWebviewBinding;

/**
 * Created by zhangshexin on 2018/9/20.
 * 有纸书时进行借阅/banner活动页
 */

public class WebView_borrow extends BaseActivity implements View.OnClickListener {

    public static void goMe(Context mContext,String title,String url){
        Intent go=new Intent(mContext,WebView_borrow.class);
        go.putExtra(extra_title,title);
        go.putExtra(extra_url,url);
        mContext.startActivity(go);
    }
    /**
     * 标题
     */
    public static String extra_title = "ex_title";
    /**
     * url
     */
    public static String extra_url = "ex_url";
    private final String TAG = getClass().getName();
    private LayoutWebviewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_webview);
        initView();
    }

    private void initView() {
        String title = getIntent().getStringExtra(extra_title);
        String url = getIntent().getStringExtra(extra_url);
        binding.webTitleInclude.titleTitle.setText(title);
        binding.webTitleInclude.titleLeft.setOnClickListener(this);

        binding.webView.setWebViewClient(new WebViewClient(){
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 自动生成的方法存根
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings seting=binding.webView.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        binding.webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if(newProgress==100){
                    binding.loadingPb.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    binding.loadingPb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                }

            }
        });
        binding.webView.loadUrl(url);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                finish2();
                break;
        }
    }
}
