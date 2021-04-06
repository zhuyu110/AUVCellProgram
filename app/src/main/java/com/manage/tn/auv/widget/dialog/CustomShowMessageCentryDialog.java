package com.manage.tn.auv.widget.dialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.MyApplication;
import com.manage.tn.auv.R;

public class CustomShowMessageCentryDialog extends CenterPopupView {
    AUVBoardCellDevice currentDevice;
    String message;
    TextView time_close;
    Handler handler;
    int time=20;
    TextView showMessage;
    TextView cell_no;
    TextView cell_no2;
    LinearLayout showParamMessage;
    LinearLayout showParamMessage2;
    public CustomShowMessageCentryDialog(@NonNull Context context, AUVBoardCellDevice device,String message) {
        super(context);
        currentDevice=device;
        this.message=message;
    }

    public void setCurrentDevice(AUVBoardCellDevice currentDevice) {
        this.currentDevice = currentDevice;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.show_message_centry_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        time_close=findViewById(R.id.time_close);
         showMessage=findViewById(R.id.showMessage);
         cell_no=findViewById(R.id.cell_no);
         cell_no2=findViewById(R.id.cell_no2);
         showParamMessage=findViewById(R.id.showParamMessage);
         showParamMessage2=findViewById(R.id.showParamMessage2);
        ImageView clost_btn=findViewById(R.id.close_btn);

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
        if(currentDevice!=null){
            showParamMessage.setVisibility(VISIBLE);
            showParamMessage2.setVisibility(VISIBLE);
            showMessage.setVisibility(GONE);
            String location="";
            if( MyApplication.cell_count==51){
                location="A-";
            }else  if(MyApplication.cell_count==75){
               if(currentDevice.getCellNo()<=51){
                   location="A-";
               }else {
                   location="B-";
               }
            }
            else  if(MyApplication.cell_count==21){
                location="A-";
            }else  if(MyApplication.cell_count==113){
                if(currentDevice.getCellNo()<=51){
                    location="A-";
                }else if(currentDevice.getCellNo()<=105){
                    location="B-";
                }else {
                    location="C-";
                }
            }
            cell_no.setText(location+currentDevice.getCellNo()+"");
            cell_no2.setText(location+currentDevice.getCellNo()+"");
        }else {
            showParamMessage.setVisibility(GONE);
            showParamMessage2.setVisibility(GONE);
            showMessage.setVisibility(VISIBLE);
            showMessage.setText(message);

        }
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
