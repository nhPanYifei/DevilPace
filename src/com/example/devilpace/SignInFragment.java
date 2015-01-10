package com.example.devilpace;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.w3c.dom.Text;

import android.R.color;
import android.R.integer;
import android.R.string;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ��ѡѡ ���
 */
public class SignInFragment extends Fragment {
	private static TextView lightestText;
	private static TextView heavestText;
	private static Button conformButton;
	private NumberPicker weightNumberPicker;

	private static int[] mWeightDatas;
	private int data_max;
	private int data_min;
	private RelativeLayout weightChart;
	private GraphicalView chartView;

	public SignInFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.signin_ui, container,
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
		lightestText = (TextView) getActivity()
				.findViewById(R.id.lightest_text);
		heavestText = (TextView) getActivity().findViewById(R.id.heavest_text);
		weightChart = (RelativeLayout) getActivity().findViewById(
				R.id.weight_chart);
		conformButton = (Button) getActivity()
				.findViewById(R.id.conform_button);
		conformButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int t = weightNumberPicker.getValue();
				Toast.makeText(getActivity(), t + "", 1).show();
				WeightData wd = new WeightData(t);
				InfoManager.saveWeightData(wd);
				showChart();
				lightestText.setText(data_min + "");
				heavestText.setText(data_max + "");
			}
		});

		weightNumberPicker = (NumberPicker) getActivity().findViewById(
				R.id.weight_numberpicker);
		weightNumberPicker.setMinValue(20);
		weightNumberPicker.setMaxValue(100);
		weightNumberPicker.getChildAt(0).setFocusable(false);
		mWeightDatas = loadWeightData();
		showChart();
		lightestText.setText(data_min + "");
		heavestText.setText(data_max + "");
	}

	/**
	 * ��ʾͼ��
	 */
	private void showChart() {
		XYMultipleSeriesDataset mDataSet = getDataSet();
		XYMultipleSeriesRenderer mRefender = getRefender();
		chartView = ChartFactory.getLineChartView(getActivity(), mDataSet,
				mRefender);
		weightChart.addView(chartView);
	}

	/**
	 * ��ȡ����
	 */
	private XYMultipleSeriesDataset getDataSet() {
		XYMultipleSeriesDataset seriesDataset = new XYMultipleSeriesDataset();
		XYSeries xySeries = new XYSeries("��һ�������ر仯");
		data_max = mWeightDatas[0];
		data_min = mWeightDatas[0];
		for (int i = 0; i < mWeightDatas.length; i++) {
			xySeries.add(i, mWeightDatas[i]);
			if (data_max < mWeightDatas[i])
				data_max = mWeightDatas[i];
			if (data_min > mWeightDatas[i])
				data_min = mWeightDatas[i];
		}
		seriesDataset.addSeries(xySeries);

		return seriesDataset;
	}

	/**
	 * ��ȡ��Ⱦ��
	 */
	private XYMultipleSeriesRenderer getRefender() {
		/* �����������ͼ������Ч��������x,y��Ч�������ű�������ɫ���� */
		XYMultipleSeriesRenderer seriesRenderer = new XYMultipleSeriesRenderer();

		seriesRenderer.setChartTitleTextSize(40);// ����ͼ�����������С(ͼ������������)
		seriesRenderer.setMargins(new int[] { 40, 50, 50, 20 });// ������߾࣬˳��Ϊ����������
		// ����������
		seriesRenderer.setAxisTitleTextSize(35);// �����������������Ĵ�С
		seriesRenderer.setYAxisMin(data_min - data_min / 6);// ����y�����ʼֵ
		seriesRenderer.setYAxisMax(data_max + data_max / 6);// ����y������ֵ
		seriesRenderer.setXAxisMin(-1);// ����x����ʼֵ
		seriesRenderer.setXAxisMax(31);// ����x�����ֵ
		seriesRenderer.setXTitle("����");// ����x�����
		seriesRenderer.setYTitle("����");// ����y�����
		seriesRenderer.setYLabels(8);
		// ��ɫ����
		seriesRenderer.setMarginsColor(TRIM_MEMORY_BACKGROUND);
		seriesRenderer.setApplyBackgroundColor(true);// ��Ӧ�����õı�����ɫ
		seriesRenderer.setLabelsColor(Color.rgb(0x99, 0x99, 0xff));// ���ñ�ǩ��ɫ
		seriesRenderer.setBackgroundColor(Color.rgb(0xBC, 0xD2, 0xEE));// ����ͼ��ı�����ɫ
		seriesRenderer.setAxesColor(Color.rgb(0x57, 0x57, 0x57));
		// ��������
		seriesRenderer.setZoomButtonsVisible(false);// �������Ű�ť�Ƿ�ɼ�
		seriesRenderer.setZoomEnabled(false, false); // ͼ���Ƿ������������
		// ͼ���ƶ�����
		seriesRenderer.setPanEnabled(false, false);// ͼ���Ƿ�����ƶ�
		// ��������
		seriesRenderer.setShowGrid(true);
		seriesRenderer.setGridColor(Color.rgb(0xAE, 0xEE, 0xEE));
		//seriesRenderer.setTextTypeface("songti")
		// legend(�����������˵��)����
		seriesRenderer.setShowLegend(false);// ����legend��˵������ ���Ƿ���ʾ
		// seriesRenderer.setLegendHeight(80);//����˵���ĸ߶ȣ���λpx
		// seriesRenderer.setLegendTextSize(16);//����˵�������С
		// �������ǩ����
		seriesRenderer.setLabelsTextSize(30);// ���ñ�ǩ�����С
		seriesRenderer.setXLabelsAlign(Align.CENTER);
		seriesRenderer.setYLabelsAlign(Align.LEFT);
		seriesRenderer.setXLabels(0);// ��ʾ��x���ǩ�ĸ���
		seriesRenderer.setXLabelsColor(Color.rgb(0x8b,0x88,0x78));
		seriesRenderer.setYLabelsColor(0, Color.rgb(0x8b,0x88,0x78));
		String[] xLabel = new String[31];
		Calendar today = Calendar.getInstance();
		SimpleDateFormat myFormatter = new SimpleDateFormat("MM/dd");
		xLabel[30] = myFormatter.format(today.getTime());
		seriesRenderer.addXTextLabel(30, xLabel[30]);
		for (int i = 5; i > -1; i--) {
			today.add(Calendar.DATE, -5);
			xLabel[i] = myFormatter.format(today.getTime());
			seriesRenderer.addXTextLabel(5 * i, xLabel[i]);
		}
		//seriesRenderer.setPointSize(10);// ����������С

		seriesRenderer.setClickEnabled(false);
		seriesRenderer.setChartTitle("��һ�������ر仯");

		/* ĳһ�����ݵ�����������������ݵĸ��Ի���ʾЧ������Ҫ���������ɫ��Ч�� */
		XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();

		xySeriesRenderer.setPointStrokeWidth(60);// �����Ĵ�С
		xySeriesRenderer.setColor(Color.rgb(0x7C,0xFC,0x00));// ��ʾ�������ݵ�ͼ���ߵ���ɫ
		xySeriesRenderer.setDisplayChartValues(false);// �����Ƿ���ʾ������y������ֵ
		xySeriesRenderer.setChartValuesTextSize(35);// ������ʾ�������ֵ�������С
		//����������
		xySeriesRenderer.setLineWidth(10);
		// xySeriesRenderer.setPointStyle(null);
		seriesRenderer.addSeriesRenderer(xySeriesRenderer);
		return seriesRenderer;
	}

	/**
	 * װ����������
	 */
	private int[] loadWeightData() {
		int[] data = InfoManager.loadWeightData();//��ȡ��������
		//���м���������Ϣ���ڵ����ݽ��в�ֵ
		int[] rvalue = new int[31];
		int[] rindex = new int[31];
		int rlength = 1;
		for (int i = 0; i < 31; i++) {
			if (data[i] != 0) {
				rvalue[rlength] = data[i];
				rindex[rlength] = i;
				rlength++;
			}
		}
		rvalue[0] = rvalue[1];//��һ�����ڱ�����ֵ
		rindex[0] = 0;
		rvalue[rlength] = rvalue[rlength - 1];
		rindex[rlength] = 30;
		data[0] = rvalue[0];
		data[30] = rvalue[rlength];
		for (int i = 0; i < rlength; i++) {
			for (int j = rindex[i] + 1; j < rindex[i + 1]; j++) {
				data[j] = (int) ((j - rindex[i]) * (rvalue[i] - rvalue[i + 1]) / ((double) (rindex[i] - rindex[i + 1])))
						+ rvalue[i];
			}
		}
		return data;
	}
}
