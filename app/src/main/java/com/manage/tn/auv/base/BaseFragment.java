package com.manage.tn.auv.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.View;


import com.base.view.ViewHolder;
import com.danmo.commonutil.fragment.CommonFragment;


public abstract class BaseFragment<DB extends ViewDataBinding> extends CommonFragment<DB> {
  /*
   * 获取传入Fragment的 argument数据
   * */
    @Override
    public void initData(@Nullable Bundle bundle) {

    }
    /*
    *//*
    * 视图View绑定
    * *//*
    @Override
    protected void initViews(ViewHolder holder, View root) {

    }

    *//*
    * 懒加载数据或加载数据方法
    * *//*
    @Override
    public void doBusiness() {

    }*/

    /*@Override是否开启懒加载
    public boolean isLazy() {
        return super.isLazy();
    }*/
   /* *//*
    * 界面绑定
    * *//*
    @Override
    public int bindLayout() {
        return 0;
    }*/
}
