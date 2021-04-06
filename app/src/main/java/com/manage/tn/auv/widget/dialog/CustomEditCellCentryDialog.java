package com.manage.tn.auv.widget.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.danmo.commonapi.AppDatabase;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.R;
import com.manage.tn.auv.widget.MClearEditText;

import java.util.Calendar;
import java.util.List;


public class CustomEditCellCentryDialog extends CenterPopupView {
    MClearEditText itemName;
    MClearEditText item_part_number;
    MClearEditText item_serial_number;
    MClearEditText expiration_date;
    AUVBoardCellDevice auvBoardCellDevice;
    public CustomEditCellCentryDialog(@NonNull Context context, AUVBoardCellDevice auvBoardCellDevice) {
        super(context);
        this.auvBoardCellDevice=auvBoardCellDevice;
    }

    public void setAuvBoardCellDevice(AUVBoardCellDevice auvBoardCellDevice) {
        this.auvBoardCellDevice = auvBoardCellDevice;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.edit_cell_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        Button btn_ok=findViewById(R.id.btn_ok);
        Button canclebtn=findViewById(R.id.canclebtn);
        ImageView close_btn=findViewById(R.id.close_btn);
        itemName=findViewById(R.id.itemName);
        item_part_number=findViewById(R.id.item_part_number);
        item_serial_number=findViewById(R.id.item_serial_number);
        expiration_date =findViewById(R.id.expiration_date);

        btn_ok.setOnClickListener(view -> {
            if(auvBoardCellDevice!=null){
                String TagNo = item_serial_number.getText().toString();
                List<AUVBoardCellDevice> deviceList=AppDatabase.getInstance(getContext()).getAllDevice();
               if(deviceList!=null){
                   for(AUVBoardCellDevice d:deviceList){
                       if(TagNo.equals(d.getDeviceNo())&&d.getCellNo()!=auvBoardCellDevice.getCellNo()){
                           ToastUtils.showShort("TAG no is Already exists");
                           return;
                       }
                   }
               }
               if(!StringUtils.isEmpty(expiration_date.getText().toString())&&!StringUtils.isEmpty(itemName.getText().toString())&&!StringUtils.isEmpty(item_serial_number.getText().toString())&&!StringUtils.isEmpty(item_part_number.getText().toString())){
                   auvBoardCellDevice.setName(itemName.getText().toString());
                   auvBoardCellDevice.setDeviceNo(item_serial_number.getText().toString());
                   auvBoardCellDevice.setPartNo(item_part_number.getText().toString());
                   auvBoardCellDevice.setEndTime(expiration_date.getText().toString());
                   auvBoardCellDevice.setIsGoodOrFail(true);
                   ThreadUtils.getFixedPool(3).execute(new Runnable() {
                       @Override
                       public void run() {
                           AppDatabase.getInstance(getContext()).inser(auvBoardCellDevice);
                           LiveEventBus.get("RefreshUI",String.class).post("RefreshUI");
                       }
                   });
                   dismiss();
               }else {
                   ToastUtils.showShort("Attribute must not null");
               }

            }

        });

        canclebtn.setOnClickListener(v->dismiss());
        close_btn.setOnClickListener(v->dismiss());

        expiration_date.setOnClickListener(V->{
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    expiration_date.setText(year+"-"+((month<9)?"0"+(month+1):(month+1))+"-"+((dayOfMonth<10)?"0"+dayOfMonth:dayOfMonth));

                }
            }, 2030,1,1).show();
        });
    }

    @Override
    protected void onShow() {
        super.onShow();
        if(auvBoardCellDevice!=null){
            itemName.setText(auvBoardCellDevice.getName());
            item_serial_number.setText(auvBoardCellDevice.getDeviceNo());
            item_part_number.setText(auvBoardCellDevice.getPartNo());
            expiration_date.setText(auvBoardCellDevice.getEndTime());
        }

    }


    @Override
    protected void onDismiss() {
        super.onDismiss();

    }

}
