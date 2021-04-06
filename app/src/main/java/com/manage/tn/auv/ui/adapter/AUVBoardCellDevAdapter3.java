package com.manage.tn.auv.ui.adapter;


import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.manage.tn.auv.R;

import java.text.SimpleDateFormat;


public class AUVBoardCellDevAdapter3 extends SingleTypeAdapter<AUVBoardCellDevice> {
    public AUVBoardCellDevAdapter3(@NonNull Context context) {
        super(context, R.layout.leader_celldev_view);
    }

    @Override
    public void convert(int position, RecyclerViewHolder holder, AUVBoardCellDevice bean) {
        ((TextView)holder.get(R.id.deviceNo)).setText(bean.getDeviceNo());
        if(!StringUtils.isEmpty(bean.getPartNo())){
            if(bean.getPartNo().equals("001")){
                ((ImageView) holder.get(R.id.deviceImg)).setBackground(mContext.getResources().getDrawable(R.mipmap.img1));
            }else  if(bean.getPartNo().equals("002")){
                ((ImageView) holder.get(R.id.deviceImg)).setBackground(mContext.getResources().getDrawable(R.mipmap.img2));
            }else  if(bean.getPartNo().equals("003")){
                ((ImageView) holder.get(R.id.deviceImg)).setBackground(mContext.getResources().getDrawable(R.mipmap.img3));
            }
        }
        ((Button)holder.get(R.id.btn_ok)).setOnClickListener(v->{
            LiveEventBus.get("ReturnGoodOrFail",AUVBoardCellDevice.class).post(bean);
        });

    }


}