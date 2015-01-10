package com.example.devilpace;

import java.util.Calendar;

/**
 * @author ��ѡѡ ���
 */
public class WeightData {
	private int year, month, date;
	private static int weight;

	/**
	 * ���췽��1
	 * 
	 * @param t
	 */
	public WeightData(int t) {
		Calendar today = Calendar.getInstance();
		this.year = today.get(Calendar.YEAR);
		this.month = today.get(Calendar.MONTH) + 1;
		this.date = today.get(Calendar.DATE);
		this.weight = t;
	}

	/**
	 * ���췽��2
	 * 
	 * @param s
	 */
	public WeightData(String s) {
		String[] strlst;
		strlst = s.split(" ");
		this.year = Integer.parseInt(strlst[0]);
		this.month = Integer.parseInt(strlst[1]);
		this.date = Integer.parseInt(strlst[2]);
		this.weight = Integer.parseInt(strlst[3]);
	}

	public String toString() {
		String s = "" + year + " " + month + " " + date + " " + weight;
		return s;
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
	 * �Ƿ��ǽ���
	 * 
	 * @param theDay
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
	public static int getWeight() {
		return weight;
	}
}
