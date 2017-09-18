package com.dk.basepack.bseaapplication.mode;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/9/15.
 */

public class FragmentBean {

    // SlidingMenu主内容
    private Fragment fragment;

    // fragment参数列表
    private Bundle bundle;

    //fragment的标记
    private  String  fragmentTag;

    public FragmentBean(Fragment fragment, Bundle bundle, String fragmentTag) {
        this.fragment = fragment;
        this.bundle = bundle;
        this.fragmentTag = fragmentTag;
    }

    public FragmentBean() {
    }


    public FragmentBean(Fragment fragment, String fragmentTag) {
        this.fragment = fragment;
        this.fragmentTag = fragmentTag;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public String getFragmentTag() {
        return fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }
}
