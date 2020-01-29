package com.chenp_racheliv.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class Paddle {
    private float x,y;    // paddle LOCATION - up-left corner
    private float w,h;    // paddle width & height
    private float dx;    // paddle MOVEMENT - dx value to move on x asix
    private int color;	  // paddle color
    private boolean isMove;

    // Which ways can the paddle move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Is the paddle moving and in which direction
    private int paddleMoving = STOPPED;

    public Paddle(float x, float y, float w, float h, int color) {
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

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void draw(Canvas canvas)
    {
        Paint paddlePen = new Paint();
        paddlePen.setColor(color);
        canvas.drawRect(x,y, x+w, y+h, paddlePen);
    }

    /*int count =0;
    public void move(float x_orientation, int canvasW){
        Log.d("debug", "x_orientation: "+x_orientation +"Right: " + (x_orientation+(getW()/2)+5) + "Left: " +(x_orientation-(getX()/2)-5));
        if(x_orientation>maxMove || x_orientation<minMove) {
            if((x+100)>200 && (x+getW()/2+5)<canvasW){
                count++;
                x +=4;}
            else if((x-100)< 200 && (x-getX()/2-5)>0) {
                count--;
                x -= 4;
            }
            Log.d("debug", "x: "+x +"count: "+count);
        }

        //else if(x < 0 || x > canvasW)
        //    isMove = false;
    }*/

    public void update(int screenWidth) {
        //Log.i("paddle", "Movt: " + paddleMoving + " b4 x:" + rect.left + ", y:" + rect.right + " fps:" + fps);
        if((paddleMoving == LEFT && x > 0) || (paddleMoving == RIGHT && x+w < screenWidth) || paddleMoving == STOPPED) {
            x = x + dx;
        }
    }

    // This method will be used to change/set if the paddle is going left, right or nowhere
    public void setMovementState(int state){
        paddleMoving = state;
    }
}
