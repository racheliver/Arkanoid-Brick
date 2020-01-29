package com.chenp_racheliv.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class Ball {
    private float cx, cy;    // ball LOCATION - center point
    private float radius;    // ball radius
    private float dx, dy;    // ball MOVEMENT - dx,dx values to move on x & y asix
    private int color;	     // ball color
    private boolean isMove;

    public Ball(float cx, float cy, float radius) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.color = Color.WHITE;
    }

    public float getCx() { return cx; }

    public void setCx(float cx) { this.cx = cx; }

    public float getCy() { return cy; }

    public void setCy(float cy) { this.cy = cy; }

    public float getRadius() { return radius; }

    public void setRadius(float radius) { this.radius = radius; }

    public float getDx() { return dx; }

    public void setDx(float dx) { this.dx = dx; }

    public float getDy() { return dy; }

    public void setDy(float dy) { this.dy = dy; }

    public int getColor() { return color; }

    public void setColor(int color) { this.color = color; }

    public boolean isMove() { return isMove; }

    public void setMove(boolean move) { isMove = move; }

    public void draw(Canvas canvas)
    {
        Paint ballPen = new Paint();
        ballPen.setColor(color);
        canvas.drawCircle(cx,cy, radius, ballPen);

        // for block rect
        Paint rect = new Paint();
        rect.setColor(Color.DKGRAY);
        rect.setStyle(Paint.Style.STROKE);
        rect.setStrokeWidth(0);
        canvas.drawRect(cx-radius,cy-radius, cx+radius, cy+radius, rect);
    }

    public void move(int canvasW, int canvasH)
    {
        if(isMove)
        {
            cx = cx + dx;
            cy = cy + dy;

            if(cx-radius<0 || cx+radius>canvasW)
                dx = -dx;

            if(cy-radius<0)
                dy = -dy;
        }
    }


    public void isCollideWithRect(Paddle rect)
    {
        RectF ball = new RectF(cx-radius, cy-radius, cx+radius, cy+radius);
        RectF paddle = new RectF(rect.getX(), rect.getY(), rect.getX()+rect.getW(),rect.getY()+rect.getH());

        if(ball.intersect(paddle))
            dy = -dy;
    }

}
