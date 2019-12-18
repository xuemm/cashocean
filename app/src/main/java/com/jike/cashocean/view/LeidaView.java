package com.jike.cashocean.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.LogUtils;
import com.jike.cashocean.R;

/**
 * @author Ming
 * @Date on 2019/12/5
 * @Description
 */
public class LeidaView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;//
    private Canvas canvas;//
    private boolean mIsDrawing;//
    private int radius;//圆半径
    private String TAG = "zoneLog";//Log 日志的 tag
    private Matrix matrix;//view 的矩阵参数，用于旋转圆形
    private float sweepAngle;//
    private boolean isStart;//是否开始 valueanimator
    private int value1;//valueanimator 的渐变值
    private int totalAngle;//总旋转角度
    private Paint sweepPaint;//圆形画笔，绘制圆角渐变
    private Paint strokeWhitePaint;//描边白色画笔，用于绘制空心圆圈
    private Paint paint;//描边白色画笔，用于绘制空心圆圈


    public LeidaView(Context context) {
        super(context);
        init();
    }

    public LeidaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeidaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        sweepAngle = 8;//旋转角度
        matrix = new Matrix();
        post(runnable);//实现圆形的不断旋转
        isStart = true;
        radius = 300;
        sweepPaint = new Paint();
        sweepPaint.setAntiAlias(true);
        sweepPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        SweepGradient sweepGradient = new SweepGradient(0, 0, new int[]{Color.WHITE, 0X10000000},
                null);//角度渐变，由透明变为白色
        sweepPaint.setShader(sweepGradient);//设置 shader

        strokeWhitePaint = new Paint();
        strokeWhitePaint.setAntiAlias(true);
        strokeWhitePaint.setColor(getResources().getColor(R.color.main_color));
        strokeWhitePaint.setStyle(Paint.Style.STROKE);

        //画内圆的画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.main_color));
        paint.setStyle(Paint.Style.FILL);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LeidaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            drawCanvas();
        }
    }


    private void drawCanvas() {
        try {
            canvas = holder.lockCanvas();
            canvas.drawColor(getResources().getColor(R.color.white));//绘制背景颜色
            float strokeWidth = getWidth() / 20;
            float innerCircle = getWidth() / 4;
            float cx = getWidth() / 2;
            float cy = getHeight() / 2;
            int alpha = 223;
            //画内yuan
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 4, paint);
            //画圆环
            strokeWhitePaint.setStrokeWidth(strokeWidth);
            alpha = alpha - 20;
            paint.setAlpha(alpha);
            innerCircle = innerCircle + strokeWidth / 2;
            canvas.drawCircle(cx, cy, innerCircle, paint);
            //第二层圆环半径，第一层半径+画笔宽
            for (int i = 0; i < 4; i++) {
                alpha = alpha - 20;
                paint.setAlpha(alpha);
                innerCircle = innerCircle + strokeWidth;
                canvas.drawCircle(cx, cy, innerCircle, paint);
            }


            canvas.save();//在另外一个图层来绘制圆形，否则会影响到后续操作
            canvas.concat(matrix);//获取 view 的矩阵参数
            canvas.translate(getWidth() / 2, getHeight() / 2);//将原点移动至中心
            canvas.drawCircle(0, 0, getWidth() / 2, sweepPaint);//绘制渐变圆
//            canvas.drawCircle(0, 0, radius + 80, strokeWhitePaint);//以下是绘制描边圆圈
//            canvas.drawCircle(0, 0, radius - 80, strokeWhitePaint);//
//            canvas.drawCircle(0, 0, radius - 160, strokeWhitePaint);//
//            canvas.drawCircle(0, 0, radius - 240, strokeWhitePaint);//
            canvas.restore();//合并之前的操作，相当于 photoshop 中的图层合并

            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.restore();

        } catch (Exception e) {
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            totalAngle += sweepAngle;//统计总的旋转角度
            matrix.postRotate(sweepAngle, getWidth() / 2, getHeight() / 2);//旋转矩阵
            postInvalidate();//刷新
            postDelayed(runnable, 30);//调用自身，实现不断循环
        }
    };

}
