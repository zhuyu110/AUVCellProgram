package com.danmo.commonutil.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.base.view.BaseActivity;
import com.blankj.swipepanel.SwipePanel;
import com.blankj.utilcode.util.SizeUtils;
import com.danmo.commonutil.dialog.CommonDialogLoading;
import com.luck.picture.lib.R;


public abstract class CommonActivity<DB extends ViewDataBinding> extends BaseActivity<DB> {

    private CommonActivityTitleView  mTitleView;
    private CommonActivityDrawerView mDrawerView;
    public View commonContentView;
    private CommonDialogLoading mDialogLoading;
    ///////////////////////////////////////////////////////////////////////////
    // title view
    ///////////////////////////////////////////////////////////////////////////
    public boolean isSwipeBack() {
        return true;
    }

    @StringRes
    public int bindTitleRes() {
        return View.NO_ID;
    }

    public CharSequence bindTitle() {
        return "";
    }

    public boolean isSupportScroll() {
        return true;
    }

    public CommonActivityTitleView bindTitleView() {
        return null;
    }
    ///////////////////////////////////////////////////////////////////////////
    // drawer view
    ///////////////////////////////////////////////////////////////////////////
    public CommonActivityDrawerView bindDrawerView() {
        return null;
    }

    public boolean bindDrawer() {
        return false;
    }

    /*step1*/
    @Override
    public void initData(@Nullable Bundle bundle) {
        mTitleView = bindTitleView();
        if (mTitleView == null) {
            int titleRes = bindTitleRes();
            if (titleRes != View.NO_ID) {
                mTitleView = new CommonActivityTitleView(this, titleRes, isSupportScroll());
            } else {
                CharSequence title = bindTitle();
                if (!TextUtils.isEmpty(title)) {
                    mTitleView = new CommonActivityTitleView(this, title, isSupportScroll());
                }
            }
        }

        mDrawerView = bindDrawerView();
        if (mDrawerView == null) {
            if (bindDrawer()) {
                mDrawerView = new CommonActivityDrawerView(this);
            }
        }
        findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.lightGrayDark));//设置显示内容背景色
        initSwipeBack();

    }

    /*step2*/
    @Override
    public void setContentView() {
        if (mTitleView != null) {
            rootDb=DataBindingUtil.setContentView(this,mTitleView.bindLayout());
            commonContentView = mTitleView.getContentView();
        } else if (mDrawerView != null) {
            rootDb=DataBindingUtil.setContentView(this,mDrawerView.bindLayout());
            commonContentView = mDrawerView.getContentView();
        } else {
            super.setContentView();
            commonContentView = null;
            return;
        }
        if (bindLayout() > 0) {

            //View inflate = LayoutInflater.from(this).inflate(bindLayout(), (ViewGroup) commonContentView,false);//返回commonContentView根布局，把bindlayout添加到commonContentView

            childDb=DataBindingUtil.inflate(LayoutInflater.from(this), bindLayout(), (ViewGroup) commonContentView, false);
        }


    }
    /*step3*/

    @Override
    public void initView(@Nullable Bundle savedInstanceState, DB childDb, @Nullable ViewDataBinding rootDb) {

    }

    /* step4*/
    @Override
    public void doBusiness() {
    }

    @Override
    public void onDebouncingClick(@NonNull View view) {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mTitleView != null) {
            return mTitleView.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    * loadingView
    * */

    public void showLoading() {
        showLoading(null);
    }

    public void showLoading(Runnable listener) {
        if (mDialogLoading != null) {
            dismissLoading();
        }
        mDialogLoading = new CommonDialogLoading().init(this, listener);
        mDialogLoading.show();
    }

    public void dismissLoading() {
        if (mDialogLoading != null) {
            mDialogLoading.dismiss();
            mDialogLoading = null;
        }
    }


    public CommonActivityTitleView getTitleView() {
        return mTitleView;
    }

    public CommonActivityDrawerView getDrawerView() {
        return mDrawerView;
    }

    /*
* 侧滑退出
* */
    private void initSwipeBack() {
        if (isSwipeBack()) {
            final SwipePanel swipeLayout = new SwipePanel(this);
            swipeLayout.setLeftDrawable(R.drawable.common_back);
            swipeLayout.setLeftEdgeSize(SizeUtils.dp2px(16));
            swipeLayout.setLeftSwipeColor(getResources().getColor(R.color.colorPrimary));
            swipeLayout.wrapView(findViewById(android.R.id.content));
            swipeLayout.setOnFullSwipeListener(new SwipePanel.OnFullSwipeListener() {
                @Override
                public void onFullSwipe(int direction) {
                    swipeLayout.close(direction);
                    finish();
                }
            });
        }
    }
}
