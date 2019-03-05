package com.play.dusky.fingertreadmill.View;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class TreadmillSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Activity activity;
    private Paint paint;//画笔
    private Paint paint1;
    private Paint paint2;
    private ArrayList<Finger> pointArrayList  = new ArrayList<>();//触控点列表
    private float distance;

    public  TreadmillSurfaceView(Activity activity){
        super(activity);

        this.activity = activity;
        this.getHolder().addCallback(this);

        paint = new Paint();
        paint.setAntiAlias(true);

        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setTextSize(35);

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(35);


        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        paint1.setColor(Color.WHITE);//设置画笔paint1的颜色
        paint1.setStrokeWidth(5);//设置画笔paint1的粗细度
        paint1.setStyle(Paint.Style.STROKE);//设置画笔paint1的风格

        paint2.setColor(Color.WHITE);//设置画笔paint1的颜色
        paint2.setStrokeWidth(5);//设置画笔paint1的粗细度
        paint2.setStyle(Paint.Style.STROKE);//设置画笔paint1的风格
    }
    String step="您的手指已经行走了";
    public void Draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        canvas.drawText(step+(int)distance/200+"步",540,500,paint2);//绘制id
        for (Finger point:pointArrayList){
            point.drawSelf(paint,paint1,canvas);//绘制所有触控点
        }
    }

    public int[] getColor(){
        int[] result = new int[4];

        result[0] = (int)(Math.random()*255);
        result[1] = (int)(Math.random()*255);
        result[2] = (int)(Math.random()*255);
        result[3] = (int)(Math.random()*255);

        return result;
    }

    public void repaint(){
        SurfaceHolder holder = this.getHolder();
        Canvas canvas = holder.lockCanvas();
        try {
            synchronized (holder){
                Draw(canvas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(canvas!=null){
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        //int action = event.getAction();//单点触控
        //int actionMasked = event.getAction()&MotionEvent.ACTION_MASK;//Android 1.6 和 Android 2.1中
        int actionMasked = event.getActionMasked();//获得多点触控检测点
        int id = (event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK) >>> MotionEvent.ACTION_POINTER_ID_SHIFT;//无符号右移获取触控点id

        switch (actionMasked){
            case MotionEvent.ACTION_DOWN://第一个触控点按下
                pointArrayList.add(id,new Finger(event.getX(id), event.getY(id),getColor(),id));//以id索引大小排序插入
                //顺序对应ACTION_MOVE时event.getX(i)取出坐标的顺序
                break;
            case MotionEvent.ACTION_POINTER_DOWN://第一个之后的触控点按下
                if(pointArrayList.size()<2){
                    pointArrayList.add(id,new Finger(event.getX(id),event.getY(id),getColor(),id));//触控点按下时，将获得(0 ~ 个数-1)中
                }
                //可用的最小的id号，即后续按下的点可以获得
                //先前已经抬起的触控点的id
                break;
            case MotionEvent.ACTION_MOVE://主、辅点移动
                for (int i = 0;i<pointArrayList.size();i++){
                    try {
                        //float x =event.getX(pointArrayList.get(i).id);//可能会下标越界，因为触控点的id不一定等于其下标
                        float x = event.getX(i);
                        float y = event.getY(i);
                        distance=distance+(y-pointArrayList.get(i).y);
                        pointArrayList.get(i).setLocation(x,y);

                    }catch (Exception e){
                        Log.d("MySurfaceView", "onTouchEvent: point.id=" + pointArrayList.get(i).id);
                        e.printStackTrace();
                    }
                }
                //pointArrayList.get(id).setLocation(event.getX(id),event.getY(id));//id=0，即如果没有循环遍历，则只会更新主控点
                break;
            case MotionEvent.ACTION_UP://最后一个点抬起
                pointArrayList.clear();
                break;
            case MotionEvent.ACTION_POINTER_UP://非最后一个点抬起
                try {
                    pointArrayList.remove(id);//删除一个触控点后，该触控点之后的点会向前移动，使得点的id不一定等于下标
                }catch (Exception e){
                    Log.d("MySurfaceView", "ACTION_POINTER_UP: point.id=" + id);
                }

                break;
        }
        repaint();
        return true;
    }

    public void surfaceCreated(SurfaceHolder holder){

    }

    public void surfaceChanged(SurfaceHolder holder,int arg1,int arg2,int arg3){
        this.repaint();
    }

    public void surfaceDestroyed(SurfaceHolder holder){

    }
}

