package com.manage.tn.auv.widget.dialog;

import android.content.Context;
import android.os.Handler;
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

public class CustomHaveReturnAlarmDialog extends CenterPopupView {
    AUVBoardCellDevice auvBoardCellDevice;
    TextView time_close;
    Handler handler;
    int time=20;

    public CustomHaveReturnAlarmDialog(@NonNull Context context, AUVBoardCellDevice auvBoardCellDevice) {
        super(context);
        this.auvBoardCellDevice=auvBoardCellDevice;
    }

    public void setAuvBoardCellDevice(AUVBoardCellDevice auvBoardCellDevice) {
        this.auvBoardCellDevice = auvBoardCellDevice;
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
        TextView message=findViewById(R.id.message);
        TextView message2=findViewById(R.id.message2);
        Button btn_cancel=findViewById(R.id.canclebtn);
        Button btn_ok=findViewById(R.id.btn_ok);
        message.setText("You have borrowed items \n         please return first  ");
        message2.setText("คุณมีเครื่องค้างยังไม่คืน กรุณาคืนก่อน เบิกใหม่");
        btn_ok.setOnClickListener(view -> {
            LiveEventBus.get("ReturnGoodOrFail",AUVBoardCellDevice.class).post(auvBoardCellDevice);
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
