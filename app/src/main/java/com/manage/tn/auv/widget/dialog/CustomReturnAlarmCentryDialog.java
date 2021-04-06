package com.manage.tn.auv.widget.dialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.MyApplication;
import com.manage.tn.auv.R;
import com.manage.tn.auv.util.AUVCellController;
import com.manage.tn.auv.util.LockControlBoardUtils;


public class CustomReturnAlarmCentryDialog extends CenterPopupView {
    AUVBoardCellDevice cellDevice;
    Button btn_ok;
    Button btn_cancel;
    LinearLayout returnLayout;
    TextView serviceNo;
    TextView showText;
    TextView time_close;
    Handler handler;
    ImageView deviceImg;
    int time=20;
    public CustomReturnAlarmCentryDialog(@NonNull Context context, AUVBoardCellDevice device) {
        super(context);
        cellDevice=device;
    }


    public void setCellDevice(AUVBoardCellDevice cellDevice) {
        this.cellDevice = cellDevice;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.return_show;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        time_close=findViewById(R.id.time_close);
        ImageView clost_btn=findViewById(R.id.close_btn);
         btn_ok=findViewById(R.id.btn_ok);
         btn_cancel=findViewById(R.id.canclebtn);
         returnLayout=findViewById(R.id.returnLayout);
         serviceNo=findViewById(R.id.serviceNo);
         showText=findViewById(R.id.showText);
        deviceImg=findViewById(R.id.deviceImg);
        showText.setText("Do You Want To Return? \n  คุณต้องการคืนเครื่อง");
        btn_ok.setText("Confirm");
        btn_ok.setOnClickListener(view -> {
            //Todo 归还控制
            MyApplication.statue=1;
            if(MyApplication.isUSB){
                AUVCellController.getInstance(getContext()).openDoor(cellDevice.getCellNo());
            }else {
                LockControlBoardUtils.getInstances().openDoor(cellDevice.getCellNo());
            }
            dismiss();


        });
        btn_cancel.setOnClickListener(v->dismiss());
        clost_btn.setOnClickListener(v->dismiss());

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
        if(!StringUtils.isEmpty(cellDevice.getPartNo())){
            if(cellDevice.getPartNo().equals("001")){
                deviceImg.setBackground(getContext().getResources().getDrawable(R.mipmap.img1));
            }else  if(cellDevice.getPartNo().equals("002")){
                deviceImg.setBackground(getContext().getResources().getDrawable(R.mipmap.img2));
            }else  if(cellDevice.getPartNo().equals("003")){
                deviceImg.setBackground(getContext().getResources().getDrawable(R.mipmap.img3));
            }

        }
        returnLayout.setVisibility(VISIBLE);
        serviceNo.setText(cellDevice.getDeviceNo()+"");
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
