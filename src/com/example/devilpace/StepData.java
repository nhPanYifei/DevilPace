package com.example.devilpace;

import java.util.Calendar;

/**
 * @author ��ѡѡ ���
 */
public class StepData {
	private int year, month, date;
	private int[] step_data_in_day;

	/**
	 * �޲ι��췽��
	 */
	public StepData() {
		// TODO Auto-generated constructor stub
		Calendar today = Calendar.getInstance();
		String s = "" + today.get(Calendar.YEAR) + " "
				+ (today.get(Calendar.MONTH) + 1) + " "
				+ today.get(Calendar.DATE) + " 0 0 0 0 0 0 0 0 0";
		step_data_in_day = new int[9];
		String step_data_str;
		String[] strlst;
		strlst = s.split(" ");
		this.year = Integer.parseInt(strlst[0]);
		this.month = Integer.parseInt(strlst[1]);
		this.date = Integer.parseInt(strlst[2]);
		this.step_data_in_day[8] = 0;
		for (int i = 0; i < strlst.length - 3; i++) {
			this.step_data_in_day[i] = Integer.parseInt(strlst[i + 3]);
			this.step_data_in_day[8] += this.step_data_in_day[i];
		}
	}

	/**
	 * ���ι��췽��
	 */
	public StepData(String s) {
		step_data_in_day = new int[9];
		String step_data_str;
		String[] strlst;
		strlst = s.split(" ");
		this.year = Integer.parseInt(strlst[0]);
		this.month = Integer.parseInt(strlst[1]);
		this.date = Integer.parseInt(strlst[2]);
		for (int i = 0; i < strlst.length - 3; i++) {
			this.step_data_in_day[i] = Integer.parseInt(strlst[i + 3]);
		}
	}

	public String toString() {
		String s = "" + year + " " + month + " " + date;
		for (int i = 0; i < 9; i++) {
			s = s + " " + step_data_in_day[i];
		}
		return s;
	}

	/**
	 * �Ƿ��ǽ���
	 */
	public boolean isToday() {
		Calendar today = Calendar.getInstance();
		if (year == today.get(Calendar.YEAR)
				&& month == (today.get(Calendar.MONTH) + 1)
				&& date == today.get(Calendar.DATE)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �Ƿ��ǵ���
	 */
	public boolean isTheDay(Calendar theDay) {
		if (year == theDay.get(Calendar.YEAR)
				&& month == (theDay.get(Calendar.MONTH) + 1)
				&& date == theDay.get(Calendar.DATE)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��ȡ����
	 */
	public Calendar toCalendar() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, date);
		return c;
	}

	/**
	 * ����Ʋ�����
	 */
	public boolean addStepNum(int rank, int num) {
		if (rank < 0 || rank > 7) {
			return false;
		} else {
			step_data_in_day[rank] += num;
			step_data_in_day[8] += num;
			return true;
		}
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDate() {
		return date;
	}

	public int[] getStep_data_in_day() {
		return step_data_in_day;
	}
}
