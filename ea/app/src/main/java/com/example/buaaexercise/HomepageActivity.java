package com.example.buaaexercise;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.homepagefragments.MyFragment;
import com.google.android.material.tabs.TabLayout;


public class HomepageActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 这个布局就是分三层：上层一个碎片, 中间一条割线, 下层一个选择tableLayout
        setContentView(R.layout.activity_homepage);
        // 拿到4个碎片实例对象
        mFragments = DataGenerator.getFragments("TabLayout Tab");
        // 拿到页面中的tableLayout控件
        mTabLayout = findViewById(R.id.bottom_tab_layout);
        initView();
        // 尝试显示<我的>
        Intent argIntent = getIntent();
        String fragmentTagToShow = argIntent.getStringExtra("fragmentToShow");
        if (fragmentTagToShow != null && fragmentTagToShow.equals("我的")) {
            showMyFragment();
        }
    }

    void initView() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());

                //改变Tab 状态
                // for(int i=0;i< mTabLayout.getTabCount();i++){
                //     if(i == tab.getPosition()){
                //         mTabLayout.getTabAt(i).setIcon(getResources().getDrawable(DataGenerator.mTabResPressed[i]));
                //     }else{
                //         mTabLayout.getTabAt(i).setIcon(getResources().getDrawable(DataGenerator.mTabRes[i]));
                //     }
                // }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // 给这个layout加四个格子, 每个格子包含 图标+文字
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_home)).setText(DataGenerator.mTabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_publish)).setText(DataGenerator.mTabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_group)).setText(DataGenerator.mTabTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_my)).setText(DataGenerator.mTabTitle[3]));
    }

    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mFragments[0];
                break;
            case 1:
                fragment = mFragments[1];
                break;
            case 2:
                fragment = mFragments[2];
                break;
            case 3:
                fragment = mFragments[3];
                break;
        }
        if (position == 3) {
            showMyFragment();
            return;
        }

        if(fragment != null) {
            // 本来Homepage上层的碎片是一个空白的占位的碎片, 现在把这个碎片替换成我们想要的碎片
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment).commit();
        }
    }

    public static boolean isFirstVisit = true;

    private void showMyFragment() {
//        if (!isFirstVisit) {
//            // 对运动记录进行修改
//            ((MyFragment) mFragments[3]).updateCurrentShowTimeHistory();
//        }
//        isFirstVisit = false;
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container, mFragments[3]).commit();
    }
}