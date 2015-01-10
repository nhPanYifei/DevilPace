package com.example.devilpace;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

/**
 * @author ��ѡѡ ���
 */

public class LauncherUI extends Activity implements OnClickListener {

	private StepCounterFragment stepCounterFragment;
	private StatisticFragment statisticFragment;
	private SignInFragment signInFragment;
	private SetupFragment setupFragment;
	private InfoFragment infoFragment;

	private View stepCounterLayout;
	private View statisticLayout;
	private View signInLayout;
	private View setUpLayout;
	private View infoLayout;

	private ImageView stepCounterImage;
	private ImageView statisticImage;
	private ImageView infoImage;
	private ImageView setupImage;
	private ImageView signinImage;

	private TextView stepCounterText;
	private TextView statisticText;
	private TextView infoText;
	private TextView setupText;
	private TextView signinText;

	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher_ui);
		initViews();
		fragmentManager = getFragmentManager();
		setTabSelection(2);
		startBackgroundTask();
	}

	/**
	 * ��ʼ������
	 */
	private void initViews() {
		stepCounterLayout = (RelativeLayout) findViewById(R.id.step_counter_ui);
		statisticLayout = (RelativeLayout) findViewById(R.id.statistic_ui);
		signInLayout = (RelativeLayout) findViewById(R.id.signin_ui);
		setUpLayout = (RelativeLayout) findViewById(R.id.setup_ui);
		infoLayout = (RelativeLayout) findViewById(R.id.info_ui);
		stepCounterImage = (ImageView) findViewById(R.id.step_counter_image);
		statisticImage = (ImageView) findViewById(R.id.statistic_image);
		signinImage = (ImageView) findViewById(R.id.signin_image);
		setupImage = (ImageView) findViewById(R.id.setup_image);
		infoImage = (ImageView) findViewById(R.id.info_image);
		stepCounterText = (TextView) findViewById(R.id.step_counter_text);
		statisticText = (TextView) findViewById(R.id.statistic_text);
		signinText = (TextView) findViewById(R.id.signin_text);
		setupText = (TextView) findViewById(R.id.setup_text);
		infoText = (TextView) findViewById(R.id.info_text);
		stepCounterLayout.setOnClickListener(this);
		statisticLayout.setOnClickListener(this);
		signInLayout.setOnClickListener(this);
		setUpLayout.setOnClickListener(this);
		infoLayout.setOnClickListener(this);
	}

	/**
	 * ��ʾѡ�еı�ǩҳ
	 */
	private void setTabSelection(int index) {
		clearSelection();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			setupImage.setImageResource(R.drawable.icon_setup_sel);
			setupText.setTextColor(Color.WHITE);
			if (setupFragment == null) {
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				setupFragment = new SetupFragment();
				transaction.add(R.id.content, setupFragment);
			} else {
				// ���setupFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(setupFragment);
			}
			break;
		case 1:
			signinImage.setImageResource(R.drawable.icon_signin_sel);
			signinText.setTextColor(Color.WHITE);
			if (signInFragment == null) {
				// ���signInFragmentΪ�գ��򴴽�һ������ӵ�������
				signInFragment = new SignInFragment();
				transaction.add(R.id.content, signInFragment);
			} else {
				// ���signInFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(signInFragment);
			}
			break;
		case 2:
			stepCounterImage.setImageResource(R.drawable.icon_step_counter_sel);
			stepCounterText.setTextColor(Color.WHITE);
			if (stepCounterFragment == null) {
				// ���stepCounterFragmentΪ�գ��򴴽�һ������ӵ�������
				stepCounterFragment = new StepCounterFragment();
				transaction.add(R.id.content, stepCounterFragment);
			} else {
				// ���stepCounterFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(stepCounterFragment);
			}
			break;
		case 3:
			statisticImage.setImageResource(R.drawable.icon_statistic_sel);
			statisticText.setTextColor(Color.WHITE);
			if (statisticFragment == null) {
				// ���statisticFragmentΪ�գ��򴴽�һ������ӵ�������
				statisticFragment = new StatisticFragment();
				transaction.add(R.id.content, statisticFragment);
			} else {
				// ���statisticFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(statisticFragment);
			}
			break;

		case 4:
			infoImage.setImageResource(R.drawable.icon_info_sel);
			infoText.setTextColor(Color.WHITE);
			if (infoFragment == null) {
				// ���infoFragmentΪ�գ��򴴽�һ������ӵ�������
				infoFragment = new InfoFragment();
				transaction.add(R.id.content, infoFragment);
			} else {
				// ���infoFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(infoFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * ���ѡ�м�¼
	 */
	private void clearSelection() {
		stepCounterImage.setImageResource(R.drawable.icon_step_counter_nor);
		stepCounterText.setTextColor(Color.parseColor("#82858b"));
		statisticImage.setImageResource(R.drawable.icon_statistic_nor);
		statisticText.setTextColor(Color.parseColor("#82858b"));
		infoImage.setImageResource(R.drawable.icon_info_nor);
		infoText.setTextColor(Color.parseColor("#82858b"));
		setupImage.setImageResource(R.drawable.icon_setup_nor);
		setupText.setTextColor(Color.parseColor("#82858b"));
		signinImage.setImageResource(R.drawable.icon_signin_nor);
		signinText.setTextColor(Color.parseColor("#82858b"));

	}

	/**
	 * ������Fragment��Ϊ����״̬
	 * 
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (stepCounterFragment != null) {
			transaction.hide(stepCounterFragment);
		}
		if (statisticFragment != null) {
			transaction.hide(statisticFragment);
		}
		if (infoFragment != null) {
			transaction.hide(infoFragment);
		}
		if (setupFragment != null) {
			transaction.hide(setupFragment);
		}
		if (signInFragment != null) {
			transaction.hide(signInFragment);
		}
	}

	/**
	 * ������̨���񣬰���InfoManager�����StepListener����
	 */
	public void startBackgroundTask() {
		Intent intent1 = new Intent(LauncherUI.this, StepListener.class);
		startService(intent1);
		Intent intent2 = new Intent(LauncherUI.this, InfoManager.class);
		startService(intent2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher_ui, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(getApplicationContext(), "EXIT", 1).show();
			InfoManager.saveStepDayData();
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setup_ui:
			setTabSelection(0);
			break;
		case R.id.signin_ui:
			setTabSelection(1);
			break;
		case R.id.step_counter_ui:
			setTabSelection(2);
			break;
		case R.id.statistic_ui:
			setTabSelection(3);
			break;
		case R.id.info_ui:
			setTabSelection(4);
			break;
		default:
			break;
		}
	}

}
