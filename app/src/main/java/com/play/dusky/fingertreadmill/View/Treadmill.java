/**
 * Copyright (C), 2018-2019, 创我科技
 * FileName: Treadmill
 * Author: Dusky
 * Date: 2019/3/4 17:16
 * Description: 跑步机
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.play.dusky.fingertreadmill.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @ClassName: Treadmill
 * @Description: 用来跑步的
 * @Author: Dusky
 * @Date: 2019/3/4 17:16
 */
public class Treadmill extends View implements View.OnTouchListener,GestureDetector.OnGestureListener {
    //定义手势检测器实例
    GestureDetector mGestureDetector;
    Context mContext;

    public Treadmill(Context context) {
        super(context);
        mContext=context;
        init();
    }

    public Treadmill(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    public Treadmill(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void init(){
        /*添加手势监听*/
        mGestureDetector = new GestureDetector(mContext, this);
        this.setOnTouchListener(this);

    }

    private int color_point = Color.rgb(255,215,0);//点的颜色
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint_blue = new Paint();
        paint_blue.setStyle(Paint.Style.FILL_AND_STROKE);
        paint_blue.setColor(color_point);
        paint_blue.setStrokeWidth(2);
        canvas.drawCircle(foot.footX, foot.footY, 8, paint_blue);
    }


    /**绘图通知handler*/
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) draw_canvas();
        }
    };
    /**
     * 重绘视图通知
     */
    private void draw_canvas() {
        this.invalidate();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);//分发手势通知
        return false;
    }

    Foot foot=new Foot();
    @Override
    public boolean onDown(MotionEvent e) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        foot.footX=e1.getX();
        foot.footY=e1.getY();
        mHandler.sendEmptyMessage(1);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }

    private class Foot{
        public float footX;
        public float footY;
    }
}
