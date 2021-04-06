package com.manage.tn.auv.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.view.ViewHolder;
import com.danmo.commonapi.model.BaseResponse;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.manage.tn.auv.R;
import com.manage.tn.auv.base.BaseFragment;
import com.manage.tn.auv.base.refresh.RefreshRecyclerFragment;

public class TestFragment extends RefreshRecyclerFragment {

    @Override
    public void initDataAdapter(HeaderFooterAdapter adapter) {
        setLoadMoreEnable(true);
        loadHeader();
        loadMiddle();

    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {

    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        return null;
    }

    @NonNull
    @Override
    protected String request(int offset, int limit) {
        return null;
    }

    @NonNull
    @Override
    protected String requestHeader() {
        return null;
    }

    @NonNull
    @Override
    protected String requestMiddle() {
        return null;
    }

    @Override
    protected void onRefresh(BaseResponse event, HeaderFooterAdapter adapter) {

    }

    @Override
    protected void onLoadMore(BaseResponse event, HeaderFooterAdapter adapter) {

    }

    @Override
    protected void onLoadHeader(BaseResponse event, HeaderFooterAdapter adapter) {

    }

    @Override
    protected void onLoadMiddle(BaseResponse event, HeaderFooterAdapter adapter) {

    }

    @Override
    protected void onError(BaseResponse event, String postType) {

    }

}
