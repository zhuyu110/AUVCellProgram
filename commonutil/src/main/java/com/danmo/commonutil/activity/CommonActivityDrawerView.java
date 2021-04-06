package com.danmo.commonutil.activity;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.BarUtils;
import com.luck.picture.lib.R;

/**
 * <pre>
 *     drawerLayout
 * </pre>
 */
public class CommonActivityDrawerView{

    public AppCompatActivity mBaseActivity;
    public DrawerLayout mBaseDrawerRootLayout;
    public FrameLayout mBaseDrawerContainerView;

    private NavigationView.OnNavigationItemSelectedListener mListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.baseDrawerActionGitHub) {
                return false;
            } else if (id == R.id.baseDrawerActionBlog) {
                return false;
            }
            return false;
        }
    };

    public CommonActivityDrawerView(@NonNull AppCompatActivity activity) {
        mBaseActivity = activity;
    }

    public int bindLayout() {
        return R.layout.common_activity_drawer;
    }

    public View getContentView() {
        mBaseDrawerRootLayout = mBaseActivity.findViewById(R.id.baseDrawerRootLayout);
        mBaseDrawerContainerView = mBaseActivity.findViewById(R.id.baseDrawerContainerView);
        NavigationView nav = mBaseActivity.findViewById(R.id.baseDrawerNavView);
        nav.setNavigationItemSelectedListener(mListener);
        BarUtils.setStatusBarVisibility(mBaseActivity.getWindow(),false);
        return mBaseDrawerContainerView;
    }


}
