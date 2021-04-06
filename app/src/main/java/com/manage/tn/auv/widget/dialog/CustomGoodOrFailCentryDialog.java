package com.manage.tn.auv.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ThreadUtils;
import com.danmo.commonapi.AppDatabase;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.R;

public class CustomGoodOrFailCentryDialog extends CenterPopupView {
    AUVBoardCellDevice auvBoardCellDevice;
    TextView time_close;
    Handler handler;
    int time=20;
    public CustomGoodOrFailCentryDialog(@NonNull Context context, AUVBoardCellDevice auvBoardCellDevice) {
        super(context);
        this.auvBoardCellDevice=auvBoardCellDevice;
    }

    public void setAuvBoardCellDevice(AUVBoardCellDevice auvBoardCellDevice) {
        this.auvBoardCellDevice = auvBoardCellDevice;
    }
    @Override
    protected int getImplLayoutId() {
        return R.layout.good_fail_centry_view;
    }
    @Override
    protected void onCreate() {
        super.onCreate();
        time_close=findViewById(R.id.time_close);
        ImageView close_btn=findViewById(R.id.close_btn);
        RelativeLayout sucess_btn=findViewById(R.id.sucess_btn);
        RelativeLayout fail_btn=findViewById(R.id.fail_btn);
        close_btn.setOnClickListener(v->dismiss());
        sucess_btn.setOnClickListener(v->{
            auvBoardCellDevice.setIsGoodOrFail(true);
            LiveEventBus.get("Return",AUVBoardCellDevice.class).post(auvBoardCellDevice);
            ThreadUtils.getFixedPool(3).execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getInstance(getContext()).inser(auvBoardCellDevice);
                    LiveEventBus.get("RefreshUI",String.class).post("RefreshUI");
                }
            });
            dismiss();
        });
        fail_btn.setOnClickListener(v->{
            auvBoardCellDevice.setIsGoodOrFail(false);
            LiveEventBus.get("Return",AUVBoardCellDevice.class).post(auvBoardCellDevice);
            ThreadUtils.getFixedPool(3).execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getInstance(getContext()).inser(auvBoardCellDevice);
                    LiveEventBus.get("RefreshUI",String.class).post("RefreshUI");
                }
            });
            dismiss();
        });
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(time>0){
                time--;
                String str="CLose page after <font color = '#EC2F61'>"+time+"</font> seconds";
                time_close.setText(Html.fromHtml(str));
                handler.postDelayed(this,1000);
            }else{
                dismiss();
            }
        }
    };
    @Override
    protected void onShow() {
        super.onShow();
        if(handler==null){
            handler=new Handler();
        }
        time=20;
        handler.postDelayed(runnable,1000);
    }


    @Override
    protected void onDismiss() {
        super.onDismiss();
        if(handler!=null){
            handler.removeCallbacks(runnable);
        }
    }
}
