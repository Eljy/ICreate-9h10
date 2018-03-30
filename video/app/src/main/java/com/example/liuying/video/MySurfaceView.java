package com.example.liuying.video;


import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private boolean flag;
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
    //x,y,z of sensor
    private float x = 0, y = 0, z = 0;



    /**
     * SurfaceView Initialization function
     */
    public MySurfaceView(Context context) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);
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
                //canvas.restore();
                paint.setColor(Color.RED);
                canvas.drawArc(new RectF(arc_x, arc_y, arc_x + 50, arc_y + 50), 0, 360, true, paint);
                canvas.save();
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
        logic();
        return true;
    }


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

                canvas.drawColor(Color.BLACK);
                //canvas.save();
                //paint.setColor(Color.RED);
                //canvas.drawArc(new RectF(arc_x, arc_y, arc_x + 50, arc_y + 50), 0, 360, true, paint);
                //canvas.save();
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

        while (flag) {
            long start = System.currentTimeMillis();
            myDraw();
            //logic();
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

