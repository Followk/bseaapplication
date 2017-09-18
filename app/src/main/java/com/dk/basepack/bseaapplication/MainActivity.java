package com.dk.basepack.bseaapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dk.basepack.bseaapplication.fragment.HomePageFragment;
import com.dk.basepack.bseaapplication.mode.FragmentBean;
import com.dk.basepack.bseaapplication.weight.CustomizedBottomNavigationBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private List<FragmentBean> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();

        CustomizedBottomNavigationBar navigationBar= (CustomizedBottomNavigationBar) findViewById(R.id.navigation_bar);
        //确定那种Fragment的切换模式
        navigationBar.setFragmentChangeMode(CustomizedBottomNavigationBar.CHANGE_FRAGMENT_REPLACE_MODE);
        //设置操作Fragment 数据集合
        navigationBar.setChangedFragmentLists(fragments,getSupportFragmentManager(),R.id.fragment_container);

    }

    private void initFragments() {//初始化操作的Fragment
        fragments = new ArrayList<>();
        fragments.add(new FragmentBean(new HomePageFragment(),"tab0"));
        fragments.add(new FragmentBean(new  ClassifyFragment(),"tab1"));
        fragments.add(new FragmentBean(new  ShoppingCartFragment(),"tab2"));
        fragments.add(new FragmentBean(new  InformationFragment(),"tab3"));
        fragments.add(new FragmentBean(new  PersonalCenterFragment(),"tab4"));
    }
}
