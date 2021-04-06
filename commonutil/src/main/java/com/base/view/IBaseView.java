package com.base.view;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/*
* 基础Activity和Fragment接口
* */
public interface IBaseView<DB extends ViewDataBinding> {
 /*step1*/   void initData(@Nullable Bundle bundle);
 /*step2*/   void setContentView();
 /*step3*/  void initView(@Nullable Bundle savedInstanceState, DB childDb,@Nullable ViewDataBinding rootDb);
    int bindLayout();
    void doBusiness();

    void onDebouncingClick(@NonNull View view);
}
