package com.example.devilpace;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author ��ѡѡ ��� 
 */
public class InfoFragment extends Fragment {
	private TextView infoText;
	private String infoString = "��������Ϣ\n" + "С���Ա��4�ˣ�\n" + "��ѡѡ�����룩\n"
			+ "��������룩\n" + "�����⣨��ƣ�\n" + "����쳣���ƣ�\n";

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
	 * ��ʼ������
	 */
	private void initView() {
		infoText = (TextView) getActivity().findViewById(R.id.info_text);
		infoText.setText(infoString);
	}
}
