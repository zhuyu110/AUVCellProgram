package com.manage.tn.auv.widget.dialog;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TimePicker;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.MyApplication;
import com.manage.tn.auv.R;
import com.manage.tn.auv.widget.MClearEditText;

import java.math.BigDecimal;
import java.util.Calendar;


public class CustomSetingCentryDialog extends CenterPopupView {
    MClearEditText cellCount;
    MClearEditText serialPort;
    public CustomSetingCentryDialog(@NonNull Context context) {
        super(context);

    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.seting_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        Button btn_ok=findViewById(R.id.btn_ok);
        Button canclebtn=findViewById(R.id.canclebtn);
        ImageView close_btn=findViewById(R.id.close_btn);
        TableRow spTable=findViewById(R.id.spTable);
        if(MyApplication.isUSB){
            spTable.setVisibility(GONE);
        }else {
            spTable.setVisibility(VISIBLE);
        }
        cellCount =findViewById(R.id.cellCount);
        serialPort=findViewById(R.id.serialPort);
        serialPort.setOnClickListener(v->{
            SelectSerialPortDialog selectSerialPortDialog = new SelectSerialPortDialog();
            selectSerialPortDialog.setOnSerialPortSelectedListener(result -> {
                serialPort.setText(result);
            });
            selectSerialPortDialog.show(((FragmentActivity)getContext()).getSupportFragmentManager(), "");
        });
        btn_ok.setOnClickListener(view -> {
            int count = Integer.parseInt(cellCount.getText().toString());
            int mainCount = Integer.parseInt(MyApplication.strMainCellTotal);
            int secondaryCount=Integer.parseInt(MyApplication.strSecondaryCellTotal);
            MyApplication.cell_count=count;
            SPUtils.getInstance().put("cellCount",count);
            MyApplication.getStrSecondaryCount=(int)Math.ceil(deciMal((count-mainCount),secondaryCount))/1+"";
            SPUtils.getInstance().put("StrSecondaryCount", MyApplication.getStrSecondaryCount);
            SPUtils.getInstance().put("serialPort",serialPort.getText().toString());
            LiveEventBus.get("refreshData",String.class).post("refreshData");
            dismiss();
        });
        canclebtn.setOnClickListener(view -> {
            dismiss();
        });
        close_btn.setOnClickListener(v->{
            dismiss();
        });


    }
    private double deciMal(int top, int below) {
        double result = new BigDecimal((float)top / below).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    @Override
    protected void onShow() {
        super.onShow();


    }


    @Override
    protected void onDismiss() {
        super.onDismiss();

    }

}
