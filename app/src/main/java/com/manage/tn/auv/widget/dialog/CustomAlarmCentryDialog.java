package com.manage.tn.auv.widget.dialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ThreadUtils;
import com.danmo.commonapi.AppDatabase;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.R;



public class CustomAlarmCentryDialog extends CenterPopupView {
    AUVBoardCellDevice auvBoardCellDevice;
    TextView time_close;
    Handler handler;
    int time=20;
    String BoardType = "";
    TextView textView;
    public CustomAlarmCentryDialog(@NonNull Context context, AUVBoardCellDevice auvBoardCellDevice,String type) {
        super(context);
       this.auvBoardCellDevice=auvBoardCellDevice;
        BoardType=type;
    }

    public void setAuvBoardCellDevice(AUVBoardCellDevice auvBoardCellDevice) {
        this.auvBoardCellDevice = auvBoardCellDevice;

    }

    public void setBoardType(String boardType) {
        BoardType = boardType;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.alarm_centry_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        time_close=findViewById(R.id.time_close);
        ImageView close_btn=findViewById(R.id.close_btn);
         textView=findViewById(R.id.cell_no);
        Button btn_cancel=findViewById(R.id.canclebtn);
        Button btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(view -> {
            if(auvBoardCellDevice!=null){
                auvBoardCellDevice.setEndTime("");
                auvBoardCellDevice.setPartNo("");
                auvBoardCellDevice.setDeviceNo("");
                auvBoardCellDevice.setName("");
                auvBoardCellDevice.setReturnTime("");
                auvBoardCellDevice.setCellGoodsExist(false);
            }
            ThreadUtils.getFixedPool(3).execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getInstance(getContext()).inser(auvBoardCellDevice);
                    LiveEventBus.get("RefreshUI",String.class).post("RefreshUI");
                }
            });
            dismiss();
        });
        btn_cancel.setOnClickListener(v-> dismiss());
        close_btn.setOnClickListener(v-> dismiss());

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
        textView.setText(BoardType+"-"+auvBoardCellDevice.getCellNo());
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
