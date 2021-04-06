package com.manage.tn.auv.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/2/10.
 */
@SuppressLint("AppCompatCustomView")
public class ContDownTextView extends TextView {

    private static final String TAG = "ContDownTextView";

    private Context mContext;
    private long countdownTime = 60 * 1000;
    private long time;
    //点击前的内容
    private String clickBefore = "获取验证码";
    //点击后显示的内容
    private String clickAfter = "s  ";
    private Timer timer;
    private TimerTask timerTask;

    public ContDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ContDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ContDownTextView(Context context) {
        super(context);
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        this.mContext = context;
        this.setText(clickBefore);
    }

    /**
     * 初始化Timer类
     */
    private void initTimer() {
        time = countdownTime;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        };
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //每一秒执行一次
                    ContDownTextView.this.setText(time / 1000 + clickAfter);
                    time -= 1000;
                    if (time < 0) {
                        ContDownTextView.this.setEnabled(true);
                        ContDownTextView.this.setText(clickBefore);
                        clearTimer();
                        Log.i(TAG, "time=======" + time);
                    }
                    break;
            }
        }
    };

    /**
     * 清除Timer类
     */
    public void clearTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();//将原任务从列队中移除
        }
        timer = null;
    }

    /**
     * 开始、倒计时
     */
    public void startTime() {
        initTimer();
        //设置倒计时内容
        ContDownTextView.this.setText("  " + countdownTime / 1000 + clickAfter);
        //计时后取消点击
        ContDownTextView.this.setEnabled(false);
        //开始计时
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 结束 计时
     */
    public void stopTime() {
        if (timer != null) {
            timer.cancel();//将原任务从列队中移除
        }
        ContDownTextView.this.setText("获取验证码");
        ContDownTextView.this.setEnabled(true);
    }


}
