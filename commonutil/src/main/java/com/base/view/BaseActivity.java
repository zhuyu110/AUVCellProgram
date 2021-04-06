package com.base.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ClickUtils;

public abstract class BaseActivity<DB extends ViewDataBinding> extends AppCompatActivity implements IBaseView<DB> {
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onDebouncingClick(v);
        }
    };

    public Activity mActivity;
    public ViewDataBinding rootDb;
    public DB childDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity=this;
        super.onCreate(savedInstanceState);
        initData(getIntent().getExtras());
        setContentView();
        initView(savedInstanceState, childDb,rootDb);
        doBusiness();
    }

    public void setContentView() {
        if(bindLayout()<=0) return;
        childDb= DataBindingUtil.setContentView(this,bindLayout());
        rootDb=childDb;

    }

    public void applyDebouncingClickListener(View... views) {
        ClickUtils.applyGlobalDebouncing(views, mClickListener);
        ClickUtils.applyPressedViewScale(views);
    }
}
