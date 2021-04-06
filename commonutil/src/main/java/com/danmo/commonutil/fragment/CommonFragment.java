package com.danmo.commonutil.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.base.view.BaseFragment;
import com.base.view.ViewHolder;

public abstract class CommonFragment<DB extends ViewDataBinding>extends BaseFragment<DB> {

    private ViewHolder mViewHolder;
    private View mRoot;
    /*
     * step1
     *
     * */
    @Override
    public void setContentView() {
        if(null!=mRoot){
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        }else {
            super.setContentView();
            mViewHolder=new ViewHolder(childDb);
            mRoot=mViewHolder.getRootView();

        }
        childDb=(DB)mViewHolder.getBinding();
    }
    /*
     * step2
     *
     * */
    @Override
    public void initView(@Nullable Bundle savedInstanceState, DB childDb, @Nullable ViewDataBinding rootDb) {

        initViews(mViewHolder, mViewHolder.getRootView());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mViewHolder.getRootView() != null) {
            ((ViewGroup) mViewHolder.getRootView().getParent()).removeView(mViewHolder.getRootView());
        }

    }

    protected abstract void initViews(ViewHolder holder, View root);
}
