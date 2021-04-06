package com.manage.tn.auv.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.manage.tn.auv.ui.service.LongRunningService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       // Log.d("AlarmReceiver", "haha");
        Log.d("AlarmReceiver", intent.getAction());
        if(intent.getAction().equals("Update date")){
            LiveEventBus.get("Update",int.class).post(1);
        }
        Intent i = new Intent(context, LongRunningService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //这是8.0以后的版本需要这样跳转
            context.startForegroundService(i);

        } else {

            context.startService(i);

        }
    }
}
