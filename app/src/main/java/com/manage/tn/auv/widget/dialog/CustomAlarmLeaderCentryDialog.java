package com.manage.tn.auv.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.R;

import java.util.List;

public class CustomAlarmLeaderCentryDialog extends CenterPopupView {
    Button btn_ok;
    Button btn_cancel;
    TextView cell_no;
    TextView showText;
    List<AUVBoardCellDevice> list;
    TextView time_close;
    Handler handler;
    int time=20;
    public CustomAlarmLeaderCentryDialog(@NonNull Context context, List<AUVBoardCellDevice> list) {
        super(context);
        this.list=list;
    }

    public void setList(List<AUVBoardCellDevice> list) {
        this.list = list;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.alarm_centry_view;
    }
    @Override
    protected void onCreate() {
        super.onCreate();
        time_close=findViewById(R.id.time_close);
        ImageView clost_btn=findViewById(R.id.close_btn);
        btn_ok=findViewById(R.id.btn_ok);
        btn_cancel=findViewById(R.id.canclebtn);
        cell_no=findViewById(R.id.cell_no);
        showText=findViewById(R.id.message);
        cell_no.setVisibility(GONE);
        showText.setText("You have outstanding  Item \n         คุณมีเครื่องที่ยังไม่คืน ");
        btn_ok.setText("Borrow \n คืนเครื่อง");
        btn_ok.setBackgroundColor(getResources().getColor(R.color.yellow));
        btn_ok.setTextColor(Color.WHITE);
        btn_cancel.setText("Return \n ยืมเครื่อง");
        btn_cancel.setTextColor(Color.WHITE);
        btn_cancel.setBackgroundColor(getResources().getColor(R.color.blue));
        btn_ok.setOnClickListener(v->{
            LiveEventBus.get("ShowBorrow",String.class).post("ShowBorrow");
            dismiss();
        });
        btn_cancel.setOnClickListener(view -> {
            LiveEventBus.get("LeaderReturn",List.class).post(list);
            dismiss();
        });
        clost_btn.setOnClickListener(v->{
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
