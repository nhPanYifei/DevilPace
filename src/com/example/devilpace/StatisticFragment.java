package com.example.devilpace;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import com.example.devilpace.StepCounterFragment.FlushHandler;
import com.example.devilpace.StepCounterFragment.FlushThread;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ��ѡѡ ���
 */
public class StatisticFragment extends Fragment {
	private static TextView averageText;
	private static TextView totalText;
	private static Button dayButton;
	private static Button weekButton;
	private GraphicalView mChartView;

	private int[] steps;
	private String[] xLabel;
	private RelativeLayout layout;
	private int averageStep = 0;
	private int totalStep = 0;

	public StatisticFragment() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.statistic_ui,
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
		totalStep = InfoManager.getmStepData().getStep_data_in_day()[8];
		averageStep = totalStep / 8;
		plotDayChart();
	}

	/**
	 * ��ʼ������
	 */
	private void initView() {
		averageText = (TextView) getActivity().findViewById(
				R.id.average_steps_text);
		totalText = (TextView) getActivity()
				.findViewById(R.id.total_steps_text);
		dayButton = (Button) getActivity().findViewById(R.id.day_button);
		weekButton = (Button) getActivity().findViewById(R.id.week_button);
		dayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				totalStep = InfoManager.getmStepData().getStep_data_in_day()[8];
				averageStep = totalStep / 8;
				averageText.setText("" + averageStep);
				totalText.setText("" + totalStep);
				plotDayChart();
			}
		});
		weekButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				steps = InfoManager.loadWeekData();
				totalStep = 0;
				for (int i = 0; i < steps.length; i++) {
					totalStep += steps[i];
				}
				averageStep = totalStep / 8;
				averageText.setText("" + averageStep);
				totalText.setText("" + totalStep);
				plotWeekChart();
			}
		});
	}

	/**
	 * ����ͼ��
	 */
	public void plotChart(XYMultipleSeriesDataset mDataset,
			XYMultipleSeriesRenderer mRenderer) {
		XYSeries series = new XYSeries("");// ����XYSeries

		mDataset.addSeries(series);

		//System.out.println("-->num1");
		for (int i = 0; i < steps.length; i++) {
			series.add(i, steps[i]);
		}
		//System.out.println("-->num2");
		mDataset.addSeries(series);// ��XYMultipleSeriesDataset�����XYSeries
		//System.out.println("-->num3");
		XYSeriesRenderer renderer = new XYSeriesRenderer();// ����XYSeriesRenderer
		renderer.setDisplayChartValues(true);
		renderer.setColor(Color.rgb(0x99, 0x99, 0xff));
		renderer.setChartValuesTextSize(30);
		mRenderer.addSeriesRenderer(renderer);// ������XYSeriesRenderer���ӵ�XYMultipleSeriesRenderer
		mRenderer.setZoomEnabled(false, false);
		mRenderer.setPanEnabled(false, false);
		mRenderer.setXAxisMin(-0.5);

		mRenderer.setYAxisMin(0);

		mRenderer.setApplyBackgroundColor(false);// �����Ƿ���ʾ����ɫ
		mRenderer.setMarginsColor(TRIM_MEMORY_BACKGROUND);
		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));// ���ñ���ɫ
		mRenderer.setAxisTitleTextSize(10); // ������������ֵĴ�С
		mRenderer.setChartTitleTextSize(30);// ?��������ͼ��������ִ�С
		mRenderer.setLabelsTextSize(30);// ���ÿ̶���ʾ���ֵĴ�С(XY�ᶼ�ᱻ����)
		//mRenderer.setLegendTextSize(20);// ͼ�����ִ�С
		mRenderer.setMargins(new int[] { 50, 0, 40, 0 });// ����ͼ�����߿�(��/��/��/��)
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setShowLegend(false);
		mRenderer.setXLabelsColor(Color.rgb(0x7d, 0x26, 0xcd));
		mRenderer.setYLabelsColor(0, Color.rgb(0x7d, 0x26, 0xcd));
		mRenderer.setBarSpacing(0.2f);
		mRenderer.setYLabelsPadding(0.5f);
		mRenderer.setShowAxes(false);
		mRenderer.setXLabels(0);
		mRenderer.setYLabels(0);
		for (int i = 0; i < xLabel.length; i++) {
			mRenderer.addXTextLabel(i, xLabel[i]);// ������Զ����Լ��ı�ǩ,��ʾ�Լ���Ҫ��X��ı�ǩ,��Ҫע�������ҪsetXLabels(0)���ڱ�ǩ�ص�(�����Զ���ı�ǩ��ͼ��Ĭ�ϵı�ǩ)
		}
		mChartView = ChartFactory.getBarChartView(getActivity(), mDataset,
				mRenderer, Type.STACKED);
		layout.addView(mChartView);
	}

	/**
	 * ���ƽ���ͼ��
	 */
	public void plotDayChart() {
		XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		layout = (RelativeLayout) getActivity().findViewById(
				R.id.statistic_chart);
		layout.removeAllViews();
		steps = new int[8];
		xLabel = new String[] { "6--8", "8--10", "10--12", "12--14", "14--16",
				"16--18", "18--20", "20--22" };
		steps = InfoManager.getmStepData().getStep_data_in_day();
		mRenderer.setXAxisMax(7.5);
		plotChart(mDataset, mRenderer);
	}

	/**
	 * ���Ʊ���ͼ��
	 */
	public void plotWeekChart() {
		XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		layout = (RelativeLayout) getActivity().findViewById(
				R.id.statistic_chart);
		layout.removeAllViews();
		xLabel = new String[7];
		Calendar today = Calendar.getInstance();
		SimpleDateFormat myFormatter = new SimpleDateFormat("MM/dd");
		xLabel[6] = myFormatter.format(today.getTime());
		for (int i = 5; i > -1; i--) {
			today.add(Calendar.DATE, -1);
			xLabel[i] = myFormatter.format(today.getTime());
		}
		mRenderer.setXAxisMax(6.5);
		plotChart(mDataset, mRenderer);

	}
}
