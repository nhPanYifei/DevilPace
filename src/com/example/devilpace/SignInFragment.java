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
 * @author 李选选 骆宇航
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
	 * 初始化界面
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
	 * 显示图表
	 */
	private void showChart() {
		XYMultipleSeriesDataset mDataSet = getDataSet();
		XYMultipleSeriesRenderer mRefender = getRefender();
		chartView = ChartFactory.getLineChartView(getActivity(), mDataSet,
				mRefender);
		weightChart.addView(chartView);
	}

	/**
	 * 获取数据
	 */
	private XYMultipleSeriesDataset getDataSet() {
		XYMultipleSeriesDataset seriesDataset = new XYMultipleSeriesDataset();
		XYSeries xySeries = new XYSeries("近一个月体重变化");
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
	 * 获取渲染器
	 */
	private XYMultipleSeriesRenderer getRefender() {
		/* 描绘器，设置图表整体效果，比如x,y轴效果，缩放比例，颜色设置 */
		XYMultipleSeriesRenderer seriesRenderer = new XYMultipleSeriesRenderer();

		seriesRenderer.setChartTitleTextSize(40);// 设置图表标题的字体大小(图的最上面文字)
		seriesRenderer.setMargins(new int[] { 40, 50, 50, 20 });// 设置外边距，顺序为：上左下右
		// 坐标轴设置
		seriesRenderer.setAxisTitleTextSize(35);// 设置坐标轴标题字体的大小
		seriesRenderer.setYAxisMin(data_min - data_min / 6);// 设置y轴的起始值
		seriesRenderer.setYAxisMax(data_max + data_max / 6);// 设置y轴的最大值
		seriesRenderer.setXAxisMin(-1);// 设置x轴起始值
		seriesRenderer.setXAxisMax(31);// 设置x轴最大值
		seriesRenderer.setXTitle("日期");// 设置x轴标题
		seriesRenderer.setYTitle("体重");// 设置y轴标题
		seriesRenderer.setYLabels(8);
		// 颜色设置
		seriesRenderer.setMarginsColor(TRIM_MEMORY_BACKGROUND);
		seriesRenderer.setApplyBackgroundColor(true);// 是应用设置的背景颜色
		seriesRenderer.setLabelsColor(Color.rgb(0x99, 0x99, 0xff));// 设置标签颜色
		seriesRenderer.setBackgroundColor(Color.rgb(0xBC, 0xD2, 0xEE));// 设置图表的背景颜色
		seriesRenderer.setAxesColor(Color.rgb(0x57, 0x57, 0x57));
		// 缩放设置
		seriesRenderer.setZoomButtonsVisible(false);// 设置缩放按钮是否可见
		seriesRenderer.setZoomEnabled(false, false); // 图表是否可以缩放设置
		// 图表移动设置
		seriesRenderer.setPanEnabled(false, false);// 图表是否可以移动
		// 网格设置
		seriesRenderer.setShowGrid(true);
		seriesRenderer.setGridColor(Color.rgb(0xAE, 0xEE, 0xEE));
		//seriesRenderer.setTextTypeface("songti")
		// legend(最下面的文字说明)设置
		seriesRenderer.setShowLegend(false);// 控制legend（说明文字 ）是否显示
		// seriesRenderer.setLegendHeight(80);//设置说明的高度，单位px
		// seriesRenderer.setLegendTextSize(16);//设置说明字体大小
		// 坐标轴标签设置
		seriesRenderer.setLabelsTextSize(30);// 设置标签字体大小
		seriesRenderer.setXLabelsAlign(Align.CENTER);
		seriesRenderer.setYLabelsAlign(Align.LEFT);
		seriesRenderer.setXLabels(0);// 显示的x轴标签的个数
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
		//seriesRenderer.setPointSize(10);// 设置坐标点大小

		seriesRenderer.setClickEnabled(false);
		seriesRenderer.setChartTitle("近一个月体重变化");

		/* 某一组数据的描绘器，描绘该组数据的个性化显示效果，主要是字体跟颜色的效果 */
		XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();

		xySeriesRenderer.setPointStrokeWidth(60);// 坐标点的大小
		xySeriesRenderer.setColor(Color.rgb(0x7C,0xFC,0x00));// 表示该组数据的图或线的颜色
		xySeriesRenderer.setDisplayChartValues(false);// 设置是否显示坐标点的y轴坐标值
		xySeriesRenderer.setChartValuesTextSize(35);// 设置显示的坐标点值的字体大小
		//数据线设置
		xySeriesRenderer.setLineWidth(10);
		// xySeriesRenderer.setPointStyle(null);
		seriesRenderer.addSeriesRenderer(xySeriesRenderer);
		return seriesRenderer;
	}

	/**
	 * 装载体重数据
	 */
	private int[] loadWeightData() {
		int[] data = InfoManager.loadWeightData();//获取体重数据
		//对中间无体重信息日期的数据进行插值
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
		rvalue[0] = rvalue[1];//第一个日期必须有值
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
