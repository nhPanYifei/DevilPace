package com.example.devilpace;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

/**
 * @author 李选选 骆宇航
 */
public class StepListener extends Service implements SensorEventListener {
	private double threshold = 12.0;
	private int sample_interval = 50000;
	private double ax;
	private double ay;
	private double az;
	private double a;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private Messenger mMessenger;
	private MyHandler mHandler;
	private static int stepFlag;
	private static int test_num = 0;

	/**
	 * 获取计步允许标志位
	 */
	public static int getStepFlag() {
		return stepFlag;
	}

	public StepListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("-->onCreate");
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		mHandler = new MyHandler();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("-->onStartCommand");
		new Thread(new MyThread()).start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		ax = event.values[0];
		ay = event.values[1];
		az = event.values[2];
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	/**
	 * 更改计步允许标志位
	 */
	public static void switchFlag() {
		stepFlag = 1 - stepFlag;
	}

	/**
	 * @author 李选选 骆宇航 处理计步
	 */
	public class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1 && stepFlag == 1) {
				InfoManager.oneMoreStep();
				test_num++;
				System.out.println("-->steps: " + test_num);
			}
		}
	}

	/**
	 * @author 李选选 骆宇航 计步线程
	 */
	public class MyThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				a = Math.sqrt(ax * ax + ay * ay + az * az);
				if (a > threshold) {
					mHandler.sendEmptyMessage(1);
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
