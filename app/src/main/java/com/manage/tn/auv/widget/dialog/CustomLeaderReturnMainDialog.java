package com.manage.tn.auv.widget.dialog;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.R;
import com.manage.tn.auv.ui.adapter.AUVBoardCellDevAdapter3;

import java.util.List;

public class CustomLeaderReturnMainDialog extends CenterPopupView {
    List<AUVBoardCellDevice> mDatas;
    RecyclerView gir_cy;
    AUVBoardCellDevAdapter3 adapter3;
    public CustomLeaderReturnMainDialog(@NonNull Context context, List<AUVBoardCellDevice> list ) {
        super(context);
        mDatas=list;
    }

    public void setmDatas(List<AUVBoardCellDevice> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.leader_main;
    }
    @Override
    protected void onCreate() {
        super.onCreate();
        ImageView clost_btn=findViewById(R.id.close_btn);
        gir_cy=findViewById(R.id.gir_cy);
        TextView title=findViewById(R.id.title);
        TextView title_head=findViewById(R.id.title_head);
        title.setText("Please Return The Item \n            โปรดคืนเครื่อง ");
        title_head.setText("Choose the cabinet and return the item to the correct compartment\n                            เเลือกเครื่องที่ต้องการคืน และ คืนกลับที่ตู้ ");
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        gir_cy.setLayoutManager(manager);
        if(adapter3==null){
            adapter3=new AUVBoardCellDevAdapter3(getContext());
        }
        adapter3.setDatas(mDatas);
        gir_cy.setAdapter(adapter3);
        clost_btn.setOnClickListener(v->{
            dismiss();
        });
        LiveEventBus.get("refresh",List.class).observe((LifecycleOwner) getContext(), result->{
            adapter3.setDatas(result);
            adapter3.notifyDataSetChanged();
        });

    }

    @Override
    protected void onShow() {
        super.onShow();
        if(adapter3==null){
            adapter3=new AUVBoardCellDevAdapter3(getContext());
        }
        adapter3.setDatas(mDatas);
        adapter3.notifyDataSetChanged();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }
}
