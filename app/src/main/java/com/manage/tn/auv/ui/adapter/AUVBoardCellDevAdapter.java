package com.manage.tn.auv.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.manage.tn.auv.R;

import java.text.SimpleDateFormat;


public class AUVBoardCellDevAdapter extends SingleTypeAdapter<AUVBoardCellDevice> {
    int srceenIndex=1000;
    String typeStr="";

    public AUVBoardCellDevAdapter(@NonNull Context context) {
        super(context, R.layout.celldev_view);
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public void setSrceenIndex(int srceenIndex) {
        this.srceenIndex = srceenIndex;
    }

    @Override
    public void convert(int position, RecyclerViewHolder holder, AUVBoardCellDevice bean) {
        if(position+1==srceenIndex){
            ((TextView)holder.get(R.id.cell_no)).setText("SCREEN");
            holder.getRootView().setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            ((TextView)holder.get(R.id.deviceNo)).setText("");
            return;
        }else if(position+1>srceenIndex){
            bean=getDatas().get(position-1);
        }
        String startTimeStr = SPUtils.getInstance().getString("beginTime", "");
        String endTimeStr = SPUtils.getInstance().getString("endTime", "");
        String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
        ((TextView)holder.get(R.id.cell_no)).setText(typeStr+"-"+bean.getCellNo());
        ((TextView)holder.get(R.id.deviceNo)).setText(StringUtils.isEmpty(bean.getPartNo())?"":"P/N"+bean.getPartNo());
        if(!TextUtils.isEmpty(bean.getDeviceNo())){
            if(bean.getCellGoodsExist()){
                if(bean.getIsGoodOrFail()){
                    //是否被锁
                    if(!TextUtils.isEmpty(bean.getReturnTime())){
                        long timeSpanByNow = TimeUtils.getTimeSpanByNow(bean.getReturnTime() ,TimeConstants.SEC);
                        if(timeSpanByNow<=0){
                            ((LinearLayout)holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        }else {
                            ((LinearLayout)holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                        }
                    }else {
                        ((LinearLayout)holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    }
                    //是否过期
                    if(!TextUtils.isEmpty(bean.getEndTime())){
                        long timeSpanByNow = TimeUtils.getTimeSpanByNow(bean.getEndTime()+" 00:00:00", TimeConstants.SEC);
                        if(timeSpanByNow<0){
                            ((LinearLayout)holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                        }
                    }

                }else {
                    ((LinearLayout)holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                }

            }else{
                ((LinearLayout)holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            }
        }else {
            ((LinearLayout)holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.init_gray));
        }




    }


}