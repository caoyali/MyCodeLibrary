package com.example.forev.mycodelibrary.simpleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BatteryView extends View {
    private boolean isCharge;
    private int mBattery = 50;
    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCharge(boolean isCharge){}

    public void setBattery(int battery){

    }
    private static final int STROKE_WIDTH = 1;//dp
    private static final int RADIUS = 2;//dp
    private static final int fengxikuandu = STROKE_WIDTH;//dp
    private static final double BATTERY_BODY_PER =  0.9;
    @Override
    protected void onDraw(Canvas canvas) {
        int dpStrokeWidth = STROKE_WIDTH * 3;
        int dpRadiusWidth = RADIUS * 3;
        //外部轮廓
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#FF000000"));
        paint.setStrokeWidth(dpStrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        int strokeWidth = (int) (getWidth() * BATTERY_BODY_PER);
        int strokeHeight = getHeight();
        RectF rect = new RectF(0, 0, strokeWidth, strokeHeight);
        canvas.drawRoundRect(rect, dpRadiusWidth, dpRadiusWidth, paint);

        //内部填充
        paint.reset();
        paint.setColor(Color.parseColor(isCharge ? "#FF00FFFF" : "#FFFF0000"));
        paint.setStyle(Paint.Style.FILL);
        int contentWidth = (strokeWidth - dpStrokeWidth - fengxikuandu) * mBattery/100;
        int contentHeight = strokeHeight - dpStrokeWidth - fengxikuandu;
        RectF rectF = new RectF(dpStrokeWidth + fengxikuandu, dpStrokeWidth + fengxikuandu, contentWidth, contentHeight);
        canvas.drawRoundRect(rectF, dpRadiusWidth, dpRadiusWidth, paint);

        //电池头
        paint.reset();
        paint.setColor(Color.parseColor("#FF00FFFF"));
        paint.setStyle(Paint.Style.FILL);
        int endImgWidth = (int)(getWidth() * (1 - BATTERY_BODY_PER)) - dpStrokeWidth;
        int endImgHeight = getHeight() / 2;
        int left = getRight() - endImgWidth;
        int top = (getBottom() - endImgHeight)/2;
        int right = left + endImgWidth;
        int bottom = top + endImgHeight;
        Path path = new Path();
        path.moveTo(left, top);//设置Path的起点
        path.lineTo(right - dpRadiusWidth, top);
        path.quadTo(right, top, right, top + dpRadiusWidth);
        path.lineTo(right, bottom - dpRadiusWidth);
        path.quadTo(right, bottom, right - dpRadiusWidth, bottom);
        path.lineTo(left, bottom);
        path.close();
        canvas.drawPath(path, paint);
    }
}
