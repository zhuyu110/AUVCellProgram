package com.manage.tn.auv.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.manage.tn.auv.R;

import javax.annotation.Nullable;


/**
 * Created by root on 2018/9/12.
 */

public class CycleProgress extends View {
    private Context mContext;
    private int cycleBgStrokeWidth;
    private int progressStrokeWidth;
    private boolean isProgressText;
    private int centerTextColor;
    private int centerTextSize;
    private int cycleBgColor;
    private int progressColor;
    private Paint progressPaint;
    private Paint cycleBgPaint;
    private Paint cycleBgPaint2;
    private Paint centerTextPaint;
    /**
     * 圆心x坐标
     */
    private float centerX;
    /**
     * 圆心y坐标
     */
    private float centerY;
    /**
     * 圆的半径
     */
    private float radius;
    /**
     * 进度
     */
    private float mProgress;

    /**
     * 当前进度
     */
    private float currentProgress;

    /**
     * 动画执行时间
     */
    private int duration = 1000;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 500;

    /*
    * 最大取值
    * */
    private float maxValue= Integer.MAX_VALUE;

    /**
     * 扇形所在矩形
     */
    private RectF rectF = new RectF();
    private ValueAnimator valueAnimator;

    public CycleProgress(Context context) {
        super(context);
        init(context, null);
    }

    public CycleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CycleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        getAttr(attrs);  // 获取控件属性，
        initPaint(); // 初始化圆圈画笔
        initTextPaint(); // 初始化文字画笔
    }

    /**
     * 获取控件属性（命名空间）
     */
    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CycleProgress);
        cycleBgStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.CycleProgress_circleBgStrokeWidth, 10);
        progressStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.CycleProgress_progressStrokeWidth, 10);
        isProgressText = typedArray.getBoolean(R.styleable.CycleProgress_isDrawCenterProgressText, false);
        centerTextColor = typedArray.getColor(R.styleable.CycleProgress_centerProgressTextColor, mContext.getResources().getColor(R.color.colorAccent));
        centerTextSize = typedArray.getDimensionPixelOffset(R.styleable.CycleProgress_centerProgressTextSize, 16);
        cycleBgColor = typedArray.getColor(R.styleable.CycleProgress_circleBgColor, mContext.getResources().getColor(R.color.colorPrimary));
        progressColor = typedArray.getColor(R.styleable.CycleProgress_progressColor, mContext.getResources().getColor(R.color.colorAccent));

        typedArray.recycle();
    }

    /**
     * 初始化圆圈画笔
     */
    private void initPaint() {
        progressPaint = getPaint(progressStrokeWidth, progressColor,1);
        cycleBgPaint= getPaint(cycleBgStrokeWidth, cycleBgColor,0);
        cycleBgPaint2 = getPaintBg(cycleBgStrokeWidth, cycleBgColor,0);
    }
    private Paint getPaintBg(int width, int color, int type) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(width);
        // Paint.Style.FILL:填充内部  Paint.Style.FILL_AND_STROKE:填充内部和描边Paint.Style.STROKE :描边
        paint.setStyle(Paint.Style.FILL);
        if(type==0){
            paint.setColor(color);
        }else{
           /* int[] colors = {getResources().getColor(R.color.cyclestart),getResources().getColor(R.color.cyclemidlle),getResources().getColor(R.color.cycleend)};
            Shader shader1 = new SweepGradient(centerX,centerY,colors,null);//均匀分布
            paint.setShader(shader1);*/
            paint.setColor(color);
        }

        paint.setAntiAlias(true); // 扛锯齿
        paint.setStrokeCap(Paint.Cap.ROUND); // 两端是圆角
        return paint;
    }
    private Paint getPaint(int width, int color, int type) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(width);
        // Paint.Style.FILL:填充内部  Paint.Style.FILL_AND_STROKE:填充内部和描边Paint.Style.STROKE :描边
        paint.setStyle(Paint.Style.STROKE);
        if(type==0){
            paint.setColor(color);
        }else{
           /* int[] colors = {getResources().getColor(R.color.cyclestart),getResources().getColor(R.color.cyclemidlle),getResources().getColor(R.color.cycleend)};
            Shader shader1 = new SweepGradient(centerX,centerY,colors,null);//均匀分布
            paint.setShader(shader1);*/
            paint.setColor(color);
        }

        paint.setAntiAlias(true); // 扛锯齿
        paint.setStrokeCap(Paint.Cap.ROUND); // 两端是圆角
        return paint;
    }

    /**
     * 初始化文字画笔
     */

    private void initTextPaint() {
        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setColor(centerTextColor);
        centerTextPaint.setTextAlign(Paint.Align.CENTER);  // 设置文字位置在中间
        centerTextPaint.setTextSize(centerTextSize);  // 设置文字大小
        centerTextPaint.setAntiAlias(true);
    }

    /**
     * 初始化动画
     */

    private void initAanimator() {
        valueAnimator = ValueAnimator.ofFloat(0, mProgress);
        valueAnimator.setDuration(duration);
        valueAnimator.setStartDelay(500);
        //  ——AccelerateInterpolator：动画从开始到结束，变化率是一个加速的过程。
        //——DecelerateInterpolator：动画从开始到结束，变化率是一个减速的过程。
        //——CycleInterpolator：动画从开始到结束，变化率是循环给定次数的正弦曲线。
        // ——AccelerateDecelerateInterpolator：动画从开始到结束，变化率是先加速后减速的过程。
        //——LinearInterpolator：动画从开始到结束，变化率是线性变化。
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { //
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float)valueAnimator.getAnimatedValue();
                mProgress = value;
                currentProgress = value * 360 / maxValue;
                invalidate();
            }
        });
    }

    /**
     * view发生改变的时候调用
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(w, h) / 2 - Math.max(cycleBgStrokeWidth, progressStrokeWidth); // 两数中的最小值 / 2 - 两数中的最大值

        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    /**
     * 画
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, cycleBgPaint);
        canvas.drawCircle(centerX, centerY, radius-5, cycleBgPaint2);
        canvas.drawArc(rectF, 270, currentProgress, false, progressPaint);
        if (isProgressText) {
            Paint.FontMetrics fontMetrics = centerTextPaint.getFontMetrics();
            int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
            canvas.drawText( (int)mProgress+"%", rectF.centerX(), baseline, centerTextPaint);
        }
    }

    /**
     * 设置动画
     * @param progress
     */
    public void setAnimator(float progress,float maxValue) {
        this.mProgress = progress;
        this.maxValue=maxValue;
        initAanimator();
    }

    /**
     * 开始动画
     */
    public void startAnimator() {
        valueAnimator.start();
    }

    public void stopAnimator() {
        valueAnimator.end();
    }
}
