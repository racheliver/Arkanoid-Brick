package com.chenp_racheliv.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Objects;
import java.util.Random;

public class GameView extends View{
    private int canvasW, canvasH;

    // game objects
    private Ball ball;
    private Paddle paddle;
    private Brick brick;
    private BrickCollection bricksCollection;

    private int score = 0;
    private int lives = 3;

    // pens
    private Paint scorePen;
    private Paint livesPen;
    private Paint startPen;

    // brickCollection finals
    public static final int ROWS = 2;
    public static final int COLS = 5;
    public static final int SPACE = 5;

    private int action;
    private boolean running;
    volatile boolean threadIsStopped = true;
    private long speed;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        scorePen = new Paint();
        scorePen.setColor(Color.WHITE);
        scorePen.setTextSize(50);
        scorePen.setTextAlign(Paint.Align.LEFT);

        livesPen = new Paint();
        livesPen.setColor(Color.WHITE);
        livesPen.setTextSize(50);
        livesPen.setTextAlign(Paint.Align.RIGHT);

        startPen = new Paint();
        startPen.setColor(Color.WHITE);
        startPen.setTextSize(55);
        startPen.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.DKGRAY);

        // Draw elements on screen
        // ===================================================
        for (int i = 0; i < bricksCollection.listBricks.size(); i++) {
            brick = bricksCollection.listBricks.get(i);
            brick.draw(canvas);
        }
        ball.draw(canvas);
        paddle.draw(canvas);
        canvas.drawText("Score: "+score, 10,80, scorePen);
        canvas.drawText("Lives: "+lives, canvasW-10,80, livesPen);
        // ===================================================

        if(getReady() && lives > 0 || running == false && lives > 0 && !bricksCollection.listBricks.isEmpty()){
            canvas.drawText("Click to PLAY! ", canvasW/2,canvasH/2, startPen);
        }

        ball.isCollideWithRect(paddle);

        if(bricksCollection.isCollideWithBall(ball)) {
            MainActivity.mediaPlayer.start();
            score += 5 * lives;
        }

        if(lives == 0 && !bricksCollection.listBricks.isEmpty()) {
            canvas.drawText("GAME OVER - You Loss! ", canvasW / 2, canvasH / 2, startPen);
            startOver();
        }
        else if(bricksCollection.listBricks.isEmpty()) {
            canvas.drawText("GAME OVER - You Win! ", canvasW / 2, canvasH / 2, startPen);
            startOver();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN) {
            if(running==false){
                ball.setMove(true);
                randomBallAngle();
            }
            running = true;
            Log.d("debug", "action: "+action);
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        canvasW = w;
        canvasH = h;

        ball = new Ball(canvasW/2, canvasH-70, 15);

        paddle = new Paddle((canvasW/2)-(canvasW/14), canvasH-50, canvasW/7, 20, Color.BLUE);

        bricksCollection = new BrickCollection(canvasW, canvasH, ROWS, COLS, SPACE);
        bricksCollection.create();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(!Thread.currentThread().isInterrupted())
                {
                    if(!threadIsStopped) {
                        moveBall();
                        SystemClock.sleep(speed * 2);
                    }
                }
            }
        }).start();
    }

    public void moveBall()
    {
        if(running) {
            ball.move(canvasW, canvasH);
            paddle.update(canvasW);
        }
        postInvalidate();
    }

    public void randomBallAngle(){
        Random r = new Random();
        int x;
        do{
        x = (-5) + r.nextInt(10);}while (x == 0);
        int y = 2 + r.nextInt(5); y*=(-1);

        //Log.d("debug", "x = "+x);
        //Log.d("debug", "y = "+y);
        ball.setDx(x);
        ball.setDy(y);

        speed = (long)Math.sqrt((x*x)+(y*y));
    }

    public void update(float y){
        if(y > 1.5) {
            //Log.i("paddle", "\t taking right");
            paddle.setMovementState(paddle.RIGHT);
            paddle.setDx(2);
        }
        else if (y < -1.5) {
            //Log.i("paddle", "\t taking left");
            paddle.setMovementState(paddle.LEFT);
            paddle.setDx(-2);
        }
        else {
            paddle.setMovementState(paddle.STOPPED);
            paddle.setDx(0);
        }
    }

    public boolean getReady(){
        if (ball.getCy() + ball.getRadius() > canvasH){
            lives--;
            ball.setCx(canvasW / 2);
            ball.setCy(canvasH - 70);
            paddle.setX((canvasW/2)-(canvasW/14));
            paddle.setY(canvasH-50);
            ball.setDx(0);
            ball.setDy(0);
            running = false;
            return true;
        }
        return false;
    }

    public void startOver(){
        if(lives == 0 || bricksCollection.listBricks.isEmpty()){
            ball.setCx(canvasW / 2);
            ball.setCy(canvasH - 70);
            paddle.setX((canvasW / 2) - (canvasW / 14));
            paddle.setY(canvasH - 50);
            ball.setDx(0);
            ball.setDy(0);
            running = false;
            if(action == 0) {
                if (lives == 0)
                    bricksCollection.listBricks.clear();
                bricksCollection.setNewGame(true);
                bricksCollection.create();
                lives = 3;
                score = 0;
            }
        }
    }
}
