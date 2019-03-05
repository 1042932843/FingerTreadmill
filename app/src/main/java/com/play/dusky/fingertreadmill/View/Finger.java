package com.play.dusky.fingertreadmill.View;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Finger {
    float x;//触控点x坐标
    float y;//触控点y坐标
    int id;//触控点id
    int[] color;//触控点的绘制颜色
    static final float RADIS = 80;//触控点绘制半径


    public Finger(float x,float y,int[] color,int id){
        this.x = x;
        this.y = y;
        this.color = color;
        this.id = id;
    }

    public void setLocation(float x,float y){
        this.x = x;
        this.y = y;
    }

    public void drawSelf(Paint paint, Paint paint1, Canvas canvas){
        paint.setARGB(180,color[1],color[2],color[3]);
        canvas.drawCircle(x,y,RADIS,paint);//绘制最外层的圆环

        paint.setARGB(150,color[1],color[2],color[3]);
        canvas.drawCircle(x,y,RADIS-10,paint);//绘制中间的圆环

        canvas.drawCircle(x,y,RADIS-18,paint1);
        canvas.drawText(id+"",x,y-100,paint1);//绘制id
    }
}

