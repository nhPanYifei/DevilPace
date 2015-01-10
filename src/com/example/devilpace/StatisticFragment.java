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
 * @author 李选选 骆宇航
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
	 * 初始化界面
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
	 * 绘制图表
	 */
	public void plotChart(XYMultipleSeriesDataset mDataset,
			XYMultipleSeriesRenderer mRenderer) {
		XYSeries series = new XYSeries("");// 定义XYSeries

		mDataset.addSeries(series);

		//System.out.println("-->num1");
		for (int i = 0; i < steps.length; i++) {
			series.add(i, steps[i]);
		}
		//System.out.println("-->num2");
		mDataset.addSeries(series);// 在XYMultipleSeriesDataset中添加XYSeries
		//System.out.println("-->num3");
		XYSeriesRenderer renderer = new XYSeriesRenderer();// 定义XYSeriesRenderer
		renderer.setDisplayChartValues(true);
		renderer.setColor(Color.rgb(0x99, 0x99, 0xff));
		renderer.setChartValuesTextSize(30);
		mRenderer.addSeriesRenderer(renderer);// 将单个XYSeriesRenderer增加到XYMultipleSeriesRenderer
		mRenderer.setZoomEnabled(false, false);
		mRenderer.setPanEnabled(false, false);
		mRenderer.setXAxisMin(-0.5);

		mRenderer.setYAxisMin(0);

		mRenderer.setApplyBackgroundColor(false);// 设置是否显示背景色
		mRenderer.setMarginsColor(TRIM_MEMORY_BACKGROUND);
		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));// 设置背景色
		mRenderer.setAxisTitleTextSize(10); // 设置轴标题文字的大小
		mRenderer.setChartTitleTextSize(30);// ?设置整个图表标题文字大小
		mRenderer.setLabelsTextSize(30);// 设置刻度显示文字的大小(XY轴都会被设置)
		//mRenderer.setLegendTextSize(20);// 图例文字大小
		mRenderer.setMargins(new int[] { 50, 0, 40, 0 });// 设置图表的外边框(上/左/下/右)
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
			mRenderer.addXTextLabel(i, xLabel[i]);// 这边是自定义自己的标签,显示自己想要的X轴的标签,需要注意的是需要setXLabels(0)放在标签重叠(就是自定义的标签与图表默认的标签)
		}
		mChartView = ChartFactory.getBarChartView(getActivity(), mDataset,
				mRenderer, Type.STACKED);
		layout.addView(mChartView);
	}

	/**
	 * 绘制今日图表
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
	 * 绘制本周图表
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
