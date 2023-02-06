package com.example.thirdpartydemo.oppo.download;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thirdpartydemo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OppoDownloadActivityViewPager extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private List<String> list = new ArrayList<>();
    private TabLayout tabLayout2;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_oppo_download_viewpager);

        list.add("下载");
        list.add("上传");
        initTabLayout();
        initTabViewPager();
    }

    private void initTabViewPager() {
        viewPager2 = findViewById(R.id.viewPager2);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this.getLifecycle(), list);
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout2.getTabAt(position).select();
            }
        });
    }

    private void changeTab(int position){
        //用来选中图标之类的
    }

    private void initTabLayout() {
        tabLayout2 = findViewById(R.id.tabLayout2);
        tabLayout2.addTab(tabLayout2.newTab().setText(list.get(0)));
        tabLayout2.addTab(tabLayout2.newTab().setText(list.get(1)));
        tabLayout2.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager2.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
