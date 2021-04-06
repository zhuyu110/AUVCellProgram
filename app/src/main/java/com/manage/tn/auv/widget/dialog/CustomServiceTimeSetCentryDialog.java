package com.manage.tn.auv.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.R;
import com.manage.tn.auv.ui.adapter.ArrayWheelAdapter;
import java.util.Arrays;

import static com.manage.tn.auv.util.Config.TimeHour;
import static com.manage.tn.auv.util.Config.TimeMin;


public class CustomServiceTimeSetCentryDialog extends CenterPopupView {

    WheelView startTime1;
    WheelView startTime2;
    WheelView endTime1;
    WheelView endTime2;
    String str1="00";
    String str2="00";
    String str3="00";
    String str4="00";
    public CustomServiceTimeSetCentryDialog(@NonNull Context context) {
        super(context);

    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.servicetime_set_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        startTime1=findViewById(R.id.startTime1);
        startTime2=findViewById(R.id.startTime2);
        endTime1=findViewById(R.id.endTime1);
        endTime2=findViewById(R.id.endTime2);
        startTime1.setCyclic(true);
        startTime2.setCyclic(true);
        endTime1.setCyclic(true);
        endTime2.setCyclic(true);
        startTime1.setAdapter(new ArrayWheelAdapter(Arrays.asList(TimeHour)));
        startTime2.setAdapter(new ArrayWheelAdapter(Arrays.asList(TimeMin)));
        endTime1.setAdapter(new ArrayWheelAdapter(Arrays.asList(TimeHour)));
        endTime2.setAdapter(new ArrayWheelAdapter(Arrays.asList(TimeMin)));
        Button btn_ok=findViewById(R.id.btn_ok);
        Button canclebtn=findViewById(R.id.canclebtn);
        ImageView close_btn=findViewById(R.id.close_btn);




        btn_ok.setOnClickListener(view -> {
            str1= TimeHour[startTime1.getCurrentItem()];
            str2= TimeMin[startTime2.getCurrentItem()];
            str3= TimeHour[endTime1.getCurrentItem()];
            str4= TimeMin[endTime2.getCurrentItem()];
            LiveEventBus.get("StartTimeSet",String.class).post(str1+":"+str2);
            LiveEventBus.get("EndTimeSet",String.class).post(str3+":"+str4);
            dismiss();
        });
        canclebtn.setOnClickListener(view -> {
            dismiss();
        });
        close_btn.setOnClickListener(v->{
            dismiss();
        });




    }

    @Override
    protected void onShow() {
        super.onShow();
        String startTimeStr = SPUtils.getInstance().getString("beginTime", "");
        String endTimeStr = SPUtils.getInstance().getString("endTime", "");
        if(StringUtils.isEmpty(startTimeStr)||StringUtils.isEmpty(endTimeStr)){
            String nowString = TimeUtils.getNowString();
            String hour=nowString.substring(11,13);
            String min=nowString.substring(14,16);
            startTime1.setCurrentItem(getIndexHour(hour));
            startTime2.setCurrentItem(getIndexMin(min));

            endTime1.setCurrentItem(getIndexHour(hour));
            endTime2.setCurrentItem(getIndexMin(min));
        }else {
            String[] startT = startTimeStr.split(":");
            String[] endT = endTimeStr.split(":");
            startTime1.setCurrentItem(getIndexHour(startT[0]));
            startTime2.setCurrentItem(getIndexMin(startT[1]));
            endTime1.setCurrentItem(getIndexHour(endT[0]));
            endTime2.setCurrentItem(getIndexMin(endT[1]));
        }


    }
    public  int getIndexHour(String str){
        for(int i=0;i<TimeHour.length;i++){
            if(TimeHour[i].equals(str)){
                return i;
            }
        }
        return 0;
    }
    public  int getIndexMin(String str){
        for(int i=0;i<TimeMin.length;i++){
            if(TimeMin[i].equals(str)){
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();

    }

}
