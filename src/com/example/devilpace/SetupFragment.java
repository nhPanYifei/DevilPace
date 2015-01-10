package com.example.devilpace;

import android.R.integer;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * @author 李选选 骆宇航
 */
public class SetupFragment extends Fragment {
	private static RadioGroup sexRadioGroup;
	private static RadioButton manRadioButton;
	private static RadioButton womanRadioButton;
	private static NumberPicker ageNumberPicker;
	private static NumberPicker goalNumberPicker;
	private static Button conformButton;

	public SetupFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.setup_ui, container,
				false);
		return contactsLayout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		sexRadioGroup = (RadioGroup) getActivity().findViewById(
				R.id.sex_radiogroup);
		manRadioButton = (RadioButton) getActivity().findViewById(
				R.id.man_radio);
		womanRadioButton = (RadioButton) getActivity().findViewById(
				R.id.womam_radio);
		ageNumberPicker = (NumberPicker) getActivity().findViewById(
				R.id.age_numberpicker);
		ageNumberPicker.setMinValue(1);
		ageNumberPicker.setMaxValue(100);
		ageNumberPicker.getChildAt(0).setFocusable(false);
		goalNumberPicker = (NumberPicker) getActivity().findViewById(
				R.id.goal_numberpicker);
		goalNumberPicker.setMinValue(0);
		goalNumberPicker.setMaxValue(20);
		goalNumberPicker.getChildAt(0).setFocusable(false);
		conformButton = (Button) getActivity().findViewById(
				R.id.setup_conform_button);
		conformButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int age = ageNumberPicker.getValue();
				int goal = goalNumberPicker.getValue();
				Toast.makeText(getActivity(), "确定", 1).show();
				Toast.makeText(getActivity(), "age: " + age + " goal: " + goal,
						1).show();
				InfoManager.savePersonalInfo(goal + "");
			}
		});
	}

}
