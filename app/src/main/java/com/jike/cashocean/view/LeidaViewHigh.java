package com.jike.cashocean.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.jike.cashocean.R;

/**
 * @author Ming
 * @Date on 2019/12/5
 * @Description https://www.jianshu.com/p/705a6cb6bfee
 */
public class LeidaViewHigh extends View {
    private Paint paint;

    public LeidaViewHigh(Context context) {
        super(context);
    }

    public LeidaViewHigh(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LeidaViewHigh(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LeidaViewHigh(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                         int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        cx = getWidth() / 2;
        cy = getHeight() / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    float cx;
    float cy;
    float innerCircle;
    float strokeWidth;
    int alpha = 223;

    //圆心点的位置
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制内圆
        paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setARGB(255, 138, 43, 226);
        paint.setColor(getResources().getColor(R.color.main_color));
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(alpha);
        innerCircle = cx / 2;
        strokeWidth = cx / 12;
        canvas.drawCircle(cx, cy, innerCircle, paint);//画内圆

        //设置画笔为画环
        paint.setStyle(Paint.Style.STROKE);
        //设置画笔宽
        paint.setStrokeWidth(strokeWidth);
        //画一层圆环，圆环半径=内圆半径+画笔宽/2
        alpha = alpha - 37;
        paint.setAlpha(alpha);
        innerCircle = innerCircle + strokeWidth / 2;
        canvas.drawCircle(cx, cy, innerCircle, paint);
        //第二层圆环半径，第一层半径+画笔宽
        for (int i = 0; i < 4; i++) {
            alpha = alpha - 37;
            paint.setAlpha(alpha);
            innerCircle = innerCircle + strokeWidth;
            canvas.drawCircle(cx, cy, innerCircle, paint);
        }
    }
}
