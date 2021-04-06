package com.manage.tn.auv.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.view.ViewHolder;
import com.blankj.utilcode.util.ToastUtils;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.danmo.commonapi.model.device.Device;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.manage.tn.auv.R;
import com.manage.tn.auv.base.BaseFragment;
import com.manage.tn.auv.databinding.CellGridViewBinding;
import com.manage.tn.auv.ui.adapter.AUVBoardCellDevAdapter2;

import java.util.ArrayList;
import java.util.List;

public class AUVCellGrildFragment extends BaseFragment<CellGridViewBinding> {
    RecyclerView recyclerView;
    AUVBoardCellDevAdapter2 adapter;
    List<AUVBoardCellDevice> deviceList=new ArrayList<>();
    String boardType="";

    public void setDeviceList(List<AUVBoardCellDevice> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public boolean isLazy() {
        return true;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        super.initData(bundle);
        if(getArguments()!=null){
            deviceList=(List<AUVBoardCellDevice>)getArguments().get("devices");
            boardType=(String) getArguments().get("type");
        }
    }



    @Override
    protected void initViews(ViewHolder holder, View root) {
        recyclerView= childDb.rcyView;
        GridLayoutManager manager = new GridLayoutManager(mActivity, 5);
        if(adapter==null){
            adapter=new AUVBoardCellDevAdapter2(mActivity);
        }
        adapter.setBoardType(boardType);
        adapter.setDatas(deviceList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        LiveEventBus.get("RefreshUI",String.class).observe(this,(result)->{
            adapter.setDatas(deviceList);
            adapter.notifyDataSetChanged();
        });

        LiveEventBus.get("RefreshDevice",AUVBoardCellDevice.class).observe(this,(result)->{
            for (AUVBoardCellDevice d:deviceList){
                if(d.getCellNo()==result.getCellNo()){
                    d.setCellGoodsExist(result.getCellGoodsExist());
                    break;
                }
            }
            adapter.setDatas(deviceList);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public int bindLayout() {
        return R.layout.cell_grid_view;
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onDebouncingClick(@NonNull View view) {

    }
}
