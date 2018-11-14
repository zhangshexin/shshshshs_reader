package com.redread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;

import com.redread.base.BaseActivity;
import com.redread.bookrack.Fragment_Booktrack;
import com.redread.databinding.LayoutHomeBinding;
import com.redread.libary.Fragment_libary;
import com.redread.login.Activity_generalLogin;
import com.redread.login.Activity_organizationLogin;
import com.redread.publish.Fragment_publish;
import com.redread.setting.Activity_setting;
import com.redread.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshexin on 2018/8/31.
 */

public class Activity_home extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener,View.OnClickListener {

    private LayoutHomeBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.layout_home);
        initView();
    }

    private HomePageAdapter adapter;
    private List<Fragment> fragments=new ArrayList<>();
    private void initView() {
        Fragment_publish fragmentPublish=new Fragment_publish();
        Fragment_libary fragmentLibary=new Fragment_libary();
        Fragment_Booktrack fragmentBooktrack=new Fragment_Booktrack();
        fragments.add(fragmentPublish);
        fragments.add(fragmentLibary);
        fragments.add(fragmentBooktrack);
        adapter=new HomePageAdapter(getSupportFragmentManager(),fragments);
        binding.homeViewPager.setOffscreenPageLimit(3);
        binding.homeViewPager.setAdapter(adapter);
        binding.homeViewPager.addOnPageChangeListener(this);
        binding.homeBottomLayRadiogroup.setOnCheckedChangeListener(this);
        initSlidView();
    }

    private void initSlidView() {
        binding.percenterSetting.setOnClickListener(this);
        binding.percenterDefaultHead.setOnClickListener(this);
    }

    /**
     * 打开/关闭左侧滑
     */
    public void toggleSlidNav(){
        if(binding.homeDrawerLayout.isDrawerOpen(Gravity.START))
            binding.homeDrawerLayout.closeDrawer(Gravity.START);
        else
            binding.homeDrawerLayout.openDrawer(Gravity.START);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                binding.homeBottomLayPublish.setChecked(true);
                break;
            case 1:
                binding.homeBottomLayLibary.setChecked(true);
                break;
            case 2:
                binding.homeBottomLayBooktrack.setChecked(true);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断是否已登录，没登录跳转登录
        checkLogin();
    }
    //判断是否已登录，没登录跳转登录
    private void checkLogin() {
        String userName= (String)SharePreferenceUtil.getSimpleData(this,Activity_generalLogin.USER_NAME,"null");
        if(TextUtils.isEmpty(userName)||userName.equals("null")){
            startActivity(Activity_organizationLogin.class);
        }
        binding.userName.setText(userName);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        // 为Intent设置Action、Category属性
        intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
        startActivity(intent);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch(i){
            case R.id.home_bottom_lay_libary:
                binding.homeViewPager.setCurrentItem(1);
                break;
            case R.id.home_bottom_lay_booktrack:
                binding.homeViewPager.setCurrentItem(2);
                break;
            case R.id.home_bottom_lay_publish:
                binding.homeViewPager.setCurrentItem(0);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.percenter_setting:
                startActivity(Activity_setting.class);
                break;
            case R.id.percenter_default_head:
                startActivity(Activity_generalLogin.class);
                break;
        }
    }
}
