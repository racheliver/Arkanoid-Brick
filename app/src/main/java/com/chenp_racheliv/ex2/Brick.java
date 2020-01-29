package com.chenp_racheliv.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick {
    private float x,y;    // brick LOCATION - up-left corner
    private float w,h;    // brick width & height
    private int color;	  // brick color

    public Brick(float x, float y, float w, float h, int color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    public float getX() { return x; }

    public void setX(float x) { this.x = x; }

    public float getY() { return y; }

    public void setY(float y) { this.y = y; }

    public float getW() { return w; }

    public void setW(float w) { this.w = w; }

    public float getH() { return h; }

    public void setH(float h) { this.h = h; }

    public int getColor() { return color; }

    public void setColor(int color) { this.color = color; }

    public void draw(Canvas canvas)
    {
        Paint brickPen = new Paint();
        brickPen.setColor(color);
        canvas.drawRect(x,y, x+w, y+h, brickPen);
    }
}
