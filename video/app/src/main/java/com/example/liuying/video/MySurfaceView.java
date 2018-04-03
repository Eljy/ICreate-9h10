package com.example.liuying.video;


import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import android.util.Pair;
import android.util.Log;
import android.view.View;

import static android.os.SystemClock.sleep;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private boolean flag;
    private boolean flag_pause;
    private boolean flag_touch;
    private Canvas canvas;
    private int screenW, screenH;
    //SensorManager
    private SensorManager sm;
    //Sensor
    private Sensor sensor;
    //SensorEventListener
    private SensorEventListener mySensorListener;
    // X,Y coordinates of circle
    private int arc_x, arc_y;
    private int count;
    private int tem_x,tem_y;
    //x,y,z of sensor
    private float x = 0, y = 0, z = 0;
    //ArrayList al = new ArrayList();




    /**
     * SurfaceView Initialization function
     */
    public MySurfaceView(Context context) {
        super(context);
        setBackgroundResource(R.drawable.background);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);

        setZOrderOnTop(true);//Put the surfaceview at the top
        getHolder().setFormat(PixelFormat.TRANSLUCENT);//Make windows support transparency

        sm = (SensorManager) SenActivity.instance.getSystemService(Service.SENSOR_SERVICE);
        //instance of gravity sensor
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //instance of sensor listener
        mySensorListener = new SensorEventListener() {
            @Override
            //when the value of sensor change
            public void onSensorChanged(SensorEvent event) {
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
                arc_x -= x;
                arc_y += y;
            }
            @Override
            //when the accuracy of the sensor changes
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        //register listener for sensor
        sm.registerListener(mySensorListener, sensor, SensorManager.SENSOR_DELAY_GAME);

    }

    /**
     * SurfaceView : creat surface view
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        flag = true;
        flag_pause = true;
        flag_touch = false;
        //实例线程
        th = new Thread(this);
        //启动线程
        th.start();
    }

    /**
     * Draw
     */
    public void myDraw() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                //canvas.drawColor(Color.BLACK);
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                paint.setColor(Color.RED);
                if(arc_x<0) arc_x = 0;
                if(arc_x>screenW-50) arc_x = screenW-50;
                if(arc_y<0) arc_y=0;
                if(arc_y>screenH-50) arc_y = screenH-50;
                tem_x = arc_x;
                tem_y = arc_y;
                canvas.drawArc(new RectF(arc_x, arc_y, arc_x + 50, arc_y + 50), 0, 360, true, paint);
                /*
                if (al.size()>1000) {
                    al.remove(0);
                }
                al.add(new Pair(arc_x,arc_y));
                */
                //canvas.save();
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    public void myDraw2() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                //canvas.drawColor(Color.BLACK);
                //to resolve the first point flash problem
                //clean the screen and initial the point 5 times
                if(flag_pause){
                    if(count <= 0) flag_pause = false;
                    arc_x = tem_x;
                    arc_y = tem_y;
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    count--;
                }
                //draw the points
                paint.setColor(Color.RED);
                if(arc_x<0) arc_x = 0;
                if(arc_x>screenW-50) arc_x = screenW-50;
                if(arc_y<0) arc_y=0;
                if(arc_y>screenH-50) arc_y = screenH-50;
                canvas.drawArc(new RectF(arc_x, arc_y, arc_x + 50, arc_y + 50), 0, 360, true, paint);
                /*
                if (al.size()>1000) {
                    al.remove(0);
                }
                al.add(new Pair(arc_x,arc_y));
                */
                //canvas.save();
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Touch Event
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {     // Get touch screen information
            //put down fingers
            case MotionEvent.ACTION_DOWN:
                flag_touch = true;
                //Make points continuous
                flag_pause = true;
                count = 5;
                //if(flag_pause) {sleep(100);logic();}
                break;
            //move the fingers
            //case MotionEvent.ACTION_MOVE:
            //    break;

            //take up fingers
            case MotionEvent.ACTION_UP:
                flag_touch = false;
                break;
            default:
                break;
        }
        //Log.i(String.valueOf(screenH),String.valueOf(screenW));
        return true;
    }


    /*
    public void print(){
        for(int i = 0,j = 0;i < al.size();i+=10,j++){
            Pair alEach = (Pair) al.get(i);
            Log.i(String.valueOf(j),alEach.first.toString()+" "+alEach.second.toString()+" ");
        }
    }
    */

    /**
     * Key Down
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * reset the background
     */
    private void logic() {

        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                //canvas.drawColor(Color.BLACK);
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }


    }

    public void clear()
    {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        invalidate();
    }

    @Override
    public void run() {
        if (flag_pause) {
            sleep(1000);
            flag_pause = false;
        }
        arc_x = 0;
        arc_y = 0;

        while (flag) {
            //if(flag_pause) {sleep(200); }
            long start = System.currentTimeMillis();
            //Detect whether there is a touch screen
            //Draw2 with trace and Draw without trace
            if(flag_touch) myDraw2();
            else myDraw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 20) {
                    Thread.sleep(20 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * SurfaceView Change
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * SurfaceView Destroye
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }
}

