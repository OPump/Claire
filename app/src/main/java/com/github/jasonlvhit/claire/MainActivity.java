package com.github.jasonlvhit.claire;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.math.BigInteger;


/**
 * Created by Jason Lyu on 2015/1/27.
 */

public class MainActivity extends ActionBarActivity
        implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static final int FLING_MIN_DISTANCE = 0;
    private static final int FLING_MIN_VELOCITY = 0;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static boolean inDialog = false;
    private static FragmentMain mFragment = new FragmentMain();

    private GestureDetectorCompat mDetector;

    private SensorManager sensorManager;
    private static Sensor mTemperatureSensor = null;
    private BigInteger mTemperature = BigInteger.ZERO;
    private boolean unSetted = true;
    private Vibrator vibrator;

    private static final int SENSOR_SHAKE = 10;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this, this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);
        mDetector.setIsLongpressEnabled(true);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Instantiate temperature sensor
        mTemperatureSensor=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(mTemperatureSensor == null){
            Toast.makeText(MainActivity.this, "No temperature sensor available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensorManager.registerListener(sensorShakeEventListener, sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            if(mTemperatureSensor != null) {
                sensorManager.registerListener(temperatureEventListener, mTemperatureSensor
                        , SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorShakeEventListener);
            if(mTemperatureSensor != null)
                sensorManager.unregisterListener(temperatureEventListener);
        }
    }

    private SensorEventListener sensorShakeEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            
            int minZValue = 15;
            double minYValue = 15;
            double minXValue = 11;
            if (Math.abs(x) > minXValue || Math.abs(y) > minYValue || Math.abs(z) > minZValue) {
                vibrator.vibrate(200);
                Message msg = new Message();
                msg.what = SENSOR_SHAKE;
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private final SensorEventListener temperatureEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE){
				/*温度传感器返回当前的温度，单位是摄氏度（°C）。*/
                float temperature=event.values[0];
                mTemperature = BigInteger.valueOf((int) temperature);
                if(unSetted) {
                    mFragment.setCelsius(mTemperature);
                    unSetted = false;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENSOR_SHAKE:
                    if(mTemperatureSensor == null) mFragment.setCelsius(BigInteger.ZERO);
                    else mFragment.setCelsius(mTemperature);
                    break;
            }
        }

    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE
                ) {
            // Scrolling up
            mFragment.increaseDegree();
        } else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE
                ) {
            // Scrolling down
            mFragment.decreaseDegree();
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {}

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        if(!inDialog) {
            if(event.getX() < screenWidth / 2) mFragment
                    .openInputDialog(getString(R.string.input_dialog_title_celsius));
            else mFragment
                    .openInputDialog(getString(R.string.input_dialog_title_fahrenheit));
            inDialog = true;
        }
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        return true;
    }

    public void setInDialog(boolean in){
        inDialog = in;
    }
}

