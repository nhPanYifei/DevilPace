package com.example.devilpace;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.R.string;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

/**
 * @author 李选选 骆宇航
 */
public class InfoManager extends Service {
	private static int num_step;
	private static int goal_step;
	private static long time_start;
	private static StepData mStepData;
	private static String dataDir = Environment.getExternalStorageDirectory()
			+ File.separator + "Steper";
	private static String dataDay = "data_day.txt";
	private static String dataAll = "data_all.txt";
	private static String dataWeightAll = "data_weight_all.txt";
	private static String personalInfo = "person_info.txt";
	private static File dataDirFile;
	private static File dataDayFile;
	private static File dataAllFile;
	private static File dataWeightAllFile;
	private static File personInfoFile;

	public InfoManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取本次步数
	 */
	public static int getNum_step() {
		return num_step;
	}

	/**
	 * 获取今日计步数据
	 */
	public static StepData getmStepData() {
		return mStepData;
	}

	/**
	 * 获取目标步数
	 */
	public static int getGoal_step() {
		return goal_step;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("-->InfoManager onStartCommand");
		time_start = System.currentTimeMillis();
		dataDirFile = new File(dataDir);
		dataDayFile = new File(dataDir, dataDay);
		dataAllFile = new File(dataDir, dataAll);
		dataWeightAllFile = new File(dataDir, dataWeightAll);
		personInfoFile = new File(dataDir, personalInfo);

		if (dataDirFile.exists()) {
			if (dataDayFile.exists()) {
				InputStream in = null;
				try {
					in = new BufferedInputStream(new FileInputStream(
							dataDayFile));
					byte[] b = new byte[in.available()];
					in.read(b);
					String bStr = new String(b);
					mStepData = new StepData(bStr);
					boolean l = mStepData.isToday();
					if (mStepData.isToday()) {

					} else {
						OutputStream out = null;
						try {
							out = new BufferedOutputStream(
									new FileOutputStream(dataAllFile, true));
							out.write((mStepData.toString() + '\n').getBytes());

						} finally {
							if (out != null) {
								out.close();
							}
							mStepData = new StepData();
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			} else {
				StepData mStepData = new StepData();
				Toast.makeText(getApplicationContext(), mStepData.toString(), 1)
						.show();
			}
			loadPersonalInfo();
		} else {
			dataDirFile.mkdir();
			mStepData = new StepData();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 保存今日计步数据
	 */
	public static void saveStepDayData() {
		OutputStream out = null;
		try {
			try {
				out = new BufferedOutputStream(new FileOutputStream(
						dataDayFile, false));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out.write((mStepData.toString()).getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			if (out != null) {
				try {
					out.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 装载本周计步数据
	 */
	public static int[] loadWeekData() {
		int data_week[] = { 0, 0, 0, 0, 0, 0, 0 };
		int index = 0;
		data_week[6] = mStepData.getStep_data_in_day()[8];
		Calendar firstDay = Calendar.getInstance();
		firstDay.add(Calendar.DATE, -6);
		if (dataAllFile.exists()) {
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(dataAllFile));
				String bStr;
				try {
					while ((bStr = in.readLine()) != null) {
						StepData tData = new StepData(bStr);
						Calendar tDay = tData.toCalendar();
						if (tDay.before(firstDay)) {
							continue;
						}
						if (tData.isTheDay(firstDay) || tDay.after(firstDay)) {

							while (!tData.isTheDay(firstDay)) {
								firstDay.add(Calendar.DATE, 1);
								index++;
							}
							;
							data_week[index] = tData.getStep_data_in_day()[8];
							// bStr = in.readLine();

						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		return data_week;
	}

	/**
	 * 装载体重历史信息
	 */
	public static int[] loadWeightData() {
		int wd[] = new int[31];
		int index = 0;
		Calendar firstDay = Calendar.getInstance();
		firstDay.add(Calendar.DATE, -30);
		if (dataWeightAllFile.exists()) {
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(dataWeightAllFile));
				String bStr;
				try {
					while ((bStr = in.readLine()) != null) {
						WeightData _wd = new WeightData(bStr);
						Calendar tDay = _wd.toCalendar();
						if (tDay.before(firstDay)) {
							continue;
						}
						if (tDay.after(firstDay)) {
							while (!_wd.isTheDay(firstDay)) {
								firstDay.add(Calendar.DATE, 1);
								index++;
							}
							;
							wd[index] = _wd.getWeight();
						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		return wd;
	}

	/**
	 * 保存此次体重数据
	 */
	public static void saveWeightData(WeightData wd) {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(
					dataWeightAllFile, true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.write((wd.toString() + '\n').getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 保存个人设定信息
	 */
	public static void savePersonalInfo(String s) {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(personInfoFile,
					false));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.write(s.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 装载个人设定信息
	 */
	private static void loadPersonalInfo() {
		if (personInfoFile.exists()) {
			InputStream in = null;
			try {
				in = new BufferedInputStream(
						new FileInputStream(personInfoFile));
				byte[] b;
				b = new byte[in.available()];
				in.read(b);
				String bStr = new String(b);
				goal_step = Integer.parseInt(bStr);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

		}

	}

	/**
	 * 增加一步
	 */
	public static void oneMoreStep() {
		num_step++;
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		System.out.println("-->hour is " + hour);
		if (hour >= 6 && hour < 8) {
			mStepData.addStepNum(0, 1);
		} else if (hour >= 8 && hour < 10) {
			mStepData.addStepNum(1, 1);
		} else if (hour >= 10 && hour < 12) {
			mStepData.addStepNum(2, 1);
		} else if (hour >= 12 && hour < 14) {
			mStepData.addStepNum(3, 1);
		} else if (hour >= 14 && hour < 16) {
			mStepData.addStepNum(4, 1);
		} else if (hour >= 16 && hour < 18) {
			mStepData.addStepNum(5, 1);
		} else if (hour >= 18 && hour < 20) {
			mStepData.addStepNum(6, 1);
		} else if (hour >= 20 && hour < 22) {
			mStepData.addStepNum(7, 1);
		} else {

		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
