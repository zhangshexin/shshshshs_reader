package com.redread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.redread.base.BaseActivity;
import com.redread.bookrack.Fragment_Booktrack;
import com.redread.databinding.LayoutHomeBinding;
import com.redread.libary.Fragment_libary;
import com.redread.publish.Fragment_publish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshexin on 2018/8/31.
 */

public class Activity_home extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

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
        binding.homeViewPager.setAdapter(adapter);
        binding.homeViewPager.addOnPageChangeListener(this);
        binding.homeBottomLayRadiogroup.setOnCheckedChangeListener(this);
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
}
