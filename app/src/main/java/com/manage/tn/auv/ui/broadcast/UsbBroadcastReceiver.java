package com.manage.tn.auv.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.manage.tn.auv.util.AUVCellController;

import static android.support.constraint.Constraints.TAG;

public class UsbBroadcastReceiver extends BroadcastReceiver{
    private  AUVCellController controller;
    public UsbBroadcastReceiver(AUVCellController controller) {
        this.controller=controller;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
            //设备插入
            controller.initUsbControl();
            controller.onDeviceStateChange();
            Log.e(TAG, "ACTION_USB_DEVICE_ATTACHED");
        } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
            //设备移除
            controller.onPause();
            Log.e(TAG, "ACTION_USB_DEVICE_DETACHED");
        }
    }
}
