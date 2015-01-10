package com.example.devilpace;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author 李选选 骆宇航 
 */
public class InfoFragment extends Fragment {
	private TextView infoText;
	private String infoString = "开发者信息\n" + "小组成员（4人）\n" + "李选选（编码）\n"
			+ "骆宇航（编码）\n" + "赵中玮（设计）\n" + "潘奕斐（设计）\n";

	public InfoFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.info_ui, container,
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
		infoText = (TextView) getActivity().findViewById(R.id.info_text);
		infoText.setText(infoString);
	}
}
