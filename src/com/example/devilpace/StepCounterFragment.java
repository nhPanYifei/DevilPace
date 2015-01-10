package com.example.devilpace;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 李选选 骆宇航
 */
public class StepCounterFragment extends Fragment {
	private static TextView activeTimeText;
	private static TextView burnedHeatText;
	private static TextView stepsText;
	private static TextView completenessText;
	private static TextView goalText;
	private static Button stepButton;
	private static FlushHandler mHandler;
	private static double heatStepRatio = 0.001;
	private static double activeTime = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.step_counter_ui,
				container, false);
		return contactsLayout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("-->onCreate");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("-->onStart");
		initView();
		mHandler = new FlushHandler();
		new Thread(new FlushThread()).start();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		activeTimeText = (TextView) getActivity().findViewById(
				R.id.active_time_text);
		burnedHeatText = (TextView) getActivity().findViewById(
				R.id.burned_heat_text);
		stepsText = (TextView) getActivity().findViewById(R.id.steps_text);
		completenessText = (TextView) getActivity().findViewById(
				R.id.completeness_text);
		goalText = (TextView) getActivity().findViewById(R.id.goal_text);
		stepButton = (Button) getActivity().findViewById(R.id.step_button);
		stepButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StepListener.switchFlag();
				if (StepListener.getStepFlag() == 1) {
					stepButton.setText("停止");
					Toast.makeText(getActivity(), "开始计步", 1).show();
				} else {
					stepButton.setText("开始");
					Toast.makeText(getActivity(), "停止计步", 1).show();
				}

			}
		});
	}

	/**
	 * 更新界面
	 */
	public static void updataView() {
		int step = InfoManager.getNum_step();
		int goal = InfoManager.getGoal_step();
		int step_today = InfoManager.getmStepData().getStep_data_in_day()[8];
		double heat = step * heatStepRatio;
		stepsText.setText(step + "");
		goalText.setText(goal + "");
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		burnedHeatText.setText(df.format(heat));
		activeTimeText.setText((int) activeTime + "");
		if (goal > 0) {
			int completeness = step_today / (goal * 10);
			completenessText.setText(completeness + "%");
		}
	}

	/**
	 * @author 李选选 骆宇航
	 */
	public static class FlushHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				updataView();
				if (StepListener.getStepFlag() == 1) {
					activeTime += 0.5 / 60;
				}

			}
		}
	}

	/**
	 * @author 李选选 骆宇航
	 * 
	 */
	public static class FlushThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				mHandler.sendEmptyMessage(1);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
