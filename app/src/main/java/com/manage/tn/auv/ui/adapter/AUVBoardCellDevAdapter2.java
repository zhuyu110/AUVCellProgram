package com.manage.tn.auv.ui.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.manage.tn.auv.MyApplication;
import com.manage.tn.auv.R;
import com.manage.tn.auv.util.AUVCellController;
import com.manage.tn.auv.util.LockControlBoardUtils;
import com.manage.tn.auv.widget.dialog.CustomAlarmCentryDialog;
import com.manage.tn.auv.widget.dialog.CustomEditCellCentryDialog;

import java.text.SimpleDateFormat;


public class AUVBoardCellDevAdapter2 extends SingleTypeAdapter<AUVBoardCellDevice> {
    CustomEditCellCentryDialog dialog;
    CustomAlarmCentryDialog adialog;
    String BoardType = "";

    public AUVBoardCellDevAdapter2(@NonNull Context context) {
        super(context, R.layout.celldev_edit_view);
    }

    public void setBoardType(String boardType) {
        BoardType = boardType;
    }

    @Override
    public void convert(int position, RecyclerViewHolder holder, AUVBoardCellDevice bean) {
        ((TextView) holder.get(R.id.cell_no)).setText(BoardType + "-" + bean.getCellNo());
        ((TextView) holder.get(R.id.deviceNo)).setText((TextUtils.isEmpty(bean.getDeviceNo()) ? "" : "TagNo:" + bean.getDeviceNo()));
        ((TextView) holder.get(R.id.partNo)).setText((TextUtils.isEmpty(bean.getPartNo()) ? "" : "P/N:" + bean.getPartNo()));
        ((TextView) holder.get(R.id.item_name)).setText(bean.getName());
        ((TextView) holder.get(R.id.exprot_date)).setText(bean.getEndTime());
        String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
        String startTimeStr = SPUtils.getInstance().getString("beginTime", "");
        String endTimeStr = SPUtils.getInstance().getString("endTime", "");
        ((TextView) holder.get(R.id.operator_btn)).setText(TextUtils.isEmpty(bean.getDeviceNo()) ? "Anassign" : "Unassign");
        if (bean.isSel()) {
            holder.getRootView().setBackground(mContext.getResources().getDrawable(R.drawable.item_border_bg));
        } else {
            holder.getRootView().setBackground(mContext.getResources().getDrawable(R.drawable.item_border_bg2));
        }
        if (!TextUtils.isEmpty(bean.getDeviceNo())) {
            if (bean.getCellGoodsExist()) {
                if (bean.getIsGoodOrFail()) {
                    //设备是否在锁定期
                    if (!TextUtils.isEmpty(bean.getReturnTime())) {
                        long timeSpanByNow = TimeUtils.getTimeSpanByNow(bean.getReturnTime(), TimeConstants.SEC);
                        if (timeSpanByNow <= 0) {
                            ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        } else {
                            ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                        }
                    } else {
                        ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    }

                    //设备是否过期
                    if (!TextUtils.isEmpty(bean.getEndTime())) {
                        long timeSpanByNow = TimeUtils.getTimeSpanByNow(bean.getEndTime() + " 00:00:00", TimeConstants.SEC);
                        if (timeSpanByNow < 0) {
                            ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.violet));
                        }
                    }
                } else {
                    ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.red));
                }

            } else {
                if (!TextUtils.isEmpty(endTimeStr) && !TextUtils.isEmpty(startTimeStr)&& !TextUtils.isEmpty(bean.getBorrowTime())) {
                    if(TimeUtils.isToday(bean.getBorrowTime())){
                        long timeSpanByNow = TimeUtils.getTimeSpanByNow(nowString + " " + endTimeStr + ":00",TimeConstants.SEC);
                        long timeSpanByNow2 = TimeUtils.getTimeSpanByNow(nowString + " " + startTimeStr + ":00", TimeConstants.SEC);
                        if (timeSpanByNow < 0 || timeSpanByNow2 > 0) {
                            ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                        } else {
                            ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                        }
                    }else {
                        ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                    }


                } else {
                    ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                }

            }
        } else {
            ((RelativeLayout) holder.get(R.id.rootView)).setBackgroundColor(mContext.getResources().getColor(R.color.init_gray));
        }
        if (!TextUtils.isEmpty(bean.getDeviceNo())) {
            ((TextView) holder.get(R.id.operator_btn)).setBackground(mContext.getResources().getDrawable(R.drawable.btn_bg));
            ((TextView) holder.get(R.id.operator_btn)).setTextColor(Color.WHITE);
        } else {
            ((TextView) holder.get(R.id.operator_btn)).setBackground(mContext.getResources().getDrawable(R.drawable.btn_bg2));
            ((TextView) holder.get(R.id.operator_btn)).setTextColor(Color.BLACK);
        }


        ((ImageView) holder.get(R.id.edit_btn)).setOnClickListener(v -> {
            if (dialog == null) {
                dialog = (CustomEditCellCentryDialog) new XPopup.Builder(mContext)
                        .asCustom(new CustomEditCellCentryDialog(mContext, bean))
                        .show();
            } else if (!dialog.isShow()) {
                dialog.setAuvBoardCellDevice(bean);
                dialog.show();
            }
        });
        ((TextView) holder.get(R.id.operator_btn)).setOnClickListener(v -> {
            if (!TextUtils.isEmpty(bean.getDeviceNo())) {
                if (adialog == null) {
                    adialog = (CustomAlarmCentryDialog) new XPopup.Builder(mContext)
                            .asCustom(new CustomAlarmCentryDialog(mContext, bean,BoardType))
                            .show();
                } else if (!adialog.isShow()) {
                    adialog.setBoardType(BoardType);
                    adialog.setAuvBoardCellDevice(bean);
                    adialog.show();
                }
            } else {
                if (dialog == null) {
                    dialog = (CustomEditCellCentryDialog) new XPopup.Builder(mContext)
                            .asCustom(new CustomEditCellCentryDialog(mContext, bean))
                            .show();
                } else if (!dialog.isShow()) {
                    dialog.setAuvBoardCellDevice(bean);
                    dialog.show();
                }
                MyApplication.statue = 4;
                if(MyApplication.isUSB){
                    AUVCellController.getInstance(mContext).openDoor(bean.getCellNo());
                }else {
                    LockControlBoardUtils.getInstances().openDoor(bean.getCellNo());
                }


                //TODO 可控制
            }


        });

        holder.getRootView().setOnClickListener(v -> {
            if (bean.isSel()) {
                holder.getRootView().setBackground(mContext.getResources().getDrawable(R.drawable.item_border_bg2));
                bean.setSel(false);
            } else {
                holder.getRootView().setBackground(mContext.getResources().getDrawable(R.drawable.item_border_bg));
                bean.setSel(true);
            }
            LiveEventBus.get("SelDevice", AUVBoardCellDevice.class).post(bean);
        });


    }
}