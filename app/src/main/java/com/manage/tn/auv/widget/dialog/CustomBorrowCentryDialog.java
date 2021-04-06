package com.manage.tn.auv.widget.dialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.R;


public class CustomBorrowCentryDialog extends CenterPopupView implements View.OnClickListener {
    LinearLayout type1;
    LinearLayout type2;
    LinearLayout type3;
    TextView time_close;
    Handler handler;
    int time=20;
    public CustomBorrowCentryDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.borrow_dialog_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        time_close=findViewById(R.id.time_close);
        ImageView clost_btn=findViewById(R.id.close_btn);
         type1=findViewById(R.id.type1);
         type2=findViewById(R.id.type2);
         type3=findViewById(R.id.type3);

        type1.setOnClickListener(this::onClick);
        type2.setOnClickListener(this::onClick);
        type3.setOnClickListener(this::onClick);
        clost_btn.setOnClickListener(this::onClick);
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
        NoselBtn(type1);
        NoselBtn(type2);
        NoselBtn(type3);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.type1:
                selBtn(type1);
                NoselBtn(type2);
                NoselBtn(type3);
                LiveEventBus.get("DeviceType",String.class).post("BZ1");
                dismiss();
                break;
            case R.id.type2:
                selBtn(type2);
                NoselBtn(type1);
                NoselBtn(type3);
                LiveEventBus.get("DeviceType",String.class).post("VP5-1");
                dismiss();
                break;
            case R.id.type3:
                selBtn(type3);
                NoselBtn(type2);
                NoselBtn(type1);
                LiveEventBus.get("DeviceType",String.class).post("VP5-2");
                dismiss();
                break;
            case R.id.close_btn:
                dismiss();
                break;

        }
    }
    public  void selBtn(LinearLayout view){
        view.setBackground(getResources().getDrawable(R.drawable.sel_border_bg));
    }
    public  void NoselBtn(LinearLayout view){
        view.setBackground(getResources().getDrawable(R.drawable.nosel_border_bg));
    }
}
