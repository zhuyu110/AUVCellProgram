package com.manage.tn.auv.widget.dialog;

import android.app.TimePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;


import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.R;
import com.manage.tn.auv.widget.MClearEditText;

import java.util.Calendar;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;


public class CustomTimeSetCentryDialog extends CenterPopupView {
    MClearEditText serviceTime;
    MClearEditText lockingTime;
    String startTimeStr="";
    String endTimeStr="";
    CustomServiceTimeSetCentryDialog serviceTimeSetCentryDialog;
    public CustomTimeSetCentryDialog(@NonNull Context context) {
        super(context);

    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.time_set_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        Button btn_ok=findViewById(R.id.btn_ok);
        Button canclebtn=findViewById(R.id.canclebtn);
        ImageView close_btn=findViewById(R.id.close_btn);
        serviceTime =findViewById(R.id.serviceTime);
        lockingTime =findViewById(R.id.lockingTime);


        LiveEventBus.get("StartTimeSet",String.class).observe((LifecycleOwner) getContext(), result->{
            startTimeStr=result;
            serviceTime.setText(startTimeStr+"-"+endTimeStr);
        });
        LiveEventBus.get("EndTimeSet",String.class).observe((LifecycleOwner) getContext(), result->{
            endTimeStr=result;
            serviceTime.setText(startTimeStr+"-"+endTimeStr);
        });
        btn_ok.setOnClickListener(view -> {
            SPUtils.getInstance().put("beginTime",startTimeStr);
            SPUtils.getInstance().put("endTime",endTimeStr);
            SPUtils.getInstance().put("lockerHour",Integer.parseInt(lockingTime.getText().toString()));
            LiveEventBus.get("RefreshUI",String.class).post("RefreshUI");
            dismiss();
        });
        canclebtn.setOnClickListener(view -> {
            dismiss();
        });
        close_btn.setOnClickListener(v->{
            dismiss();
        });

        this.serviceTime.setOnClickListener(V->{
            if (serviceTimeSetCentryDialog == null) {
                serviceTimeSetCentryDialog = (CustomServiceTimeSetCentryDialog) new XPopup.Builder(getContext())
                        .asCustom(new CustomServiceTimeSetCentryDialog(getContext()))
                        .show();
            } else if (!serviceTimeSetCentryDialog.isShow()) {
                serviceTimeSetCentryDialog.show();
            }

        });


    }

    @Override
    protected void onShow() {
        super.onShow();
        startTimeStr = SPUtils.getInstance().getString("beginTime", "");
        endTimeStr = SPUtils.getInstance().getString("endTime", "");
        int lockingTimeStr = SPUtils.getInstance().getInt("lockerHour", 0);
        serviceTime.setText(StringUtils.isEmpty(startTimeStr)?"":startTimeStr+"-"+endTimeStr);
        lockingTime.setText(lockingTimeStr+"");

    }


    @Override
    protected void onDismiss() {
        super.onDismiss();

    }

}
