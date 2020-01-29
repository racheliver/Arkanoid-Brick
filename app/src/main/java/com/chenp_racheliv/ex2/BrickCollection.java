package com.chenp_racheliv.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BrickCollection {
    private int canvasW;
    private int canvasH;
    private int rows;
    private int cols;
    private int space;
    private int brickW;
    private int brickH;
    private int SpaceX;
    private int SpaceY;
    private boolean newGame;

    List<Brick> listBricks = new ArrayList<Brick>();
    RectF brick;

    public BrickCollection(int canvasW, int canvasH, int rows, int cols, int space) {
        this.canvasW = canvasW;
        this.canvasH = canvasH;
        this.rows = rows;
        this.cols = cols;
        this.space = space;
        this.brickW = (canvasW-space*(cols+1))/cols;
        this.brickH = canvasH/24;
        this.SpaceX = space;
        this.SpaceY = 130;
        this.newGame = false;
    }

    public int getCanvasW() { return canvasW; }

    public void setCanvasW(int canvasW) { this.canvasW = canvasW; }

    public int getCanvasH() { return canvasH; }

    public void setCanvasH(int canvasH) { this.canvasH = canvasH; }

    public int getRows() { return rows; }

    public void setRows(int rows) { this.rows = rows; }

    public int getCols() { return cols; }

    public void setCols(int cols) { this.cols = cols; }

    public int getSpace() { return space; }

    public void setSpace(int space) { this.space = space; }

    //create bricks in a loop
    public void create()
    {
        if(newGame){
            SpaceX = space;
            SpaceY = 130;
        }
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++)
            {
                listBricks.add(new Brick(SpaceX, SpaceY, brickW, brickH, Color.RED));
                SpaceX+=brickW+space;
                //brick.draw(canvas);
            }
            SpaceX = space;
            SpaceY+=brickH+space;
        }
        //Log.d("debug","SIZEEEEEEEE: isCollideWithBall : "+listBricks.size());
    }

    public boolean isCollideWithBall(Ball rect)
    {
        RectF ball = new RectF(rect.getCx()-rect.getRadius(), rect.getCy()-rect.getRadius(), rect.getCx()+rect.getRadius(), rect.getCy()+rect.getRadius());
        //Log.d("debug","SIZEEEEEEEE: isCollideWithBall : "+listBricks.size());
        for (int i = 0; i < listBricks.size(); i++) {
            brick = new RectF(listBricks.get(i).getX(), listBricks.get(i).getY(), listBricks.get(i).getX()+listBricks.get(i).getW(), listBricks.get(i).getY()+listBricks.get(i).getH());
            if(brick.intersect(ball)) {
                listBricks.remove(i);
                rect.setDy(-rect.getDy());
                return true;
            }
        }
        return false;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }
}