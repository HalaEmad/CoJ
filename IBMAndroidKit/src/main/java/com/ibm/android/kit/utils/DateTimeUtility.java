package com.ibm.android.kit.utils;

/**
 * 
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;

/**
 * @author Bassam
 * 
 */
public class DateTimeUtility {
	/**
	 * get date\time format based on current date\time if current year equals
	 * date year, hide year and show only day, month and time if current month
	 * equals date month, hide month and show only week day and time if current
	 * day equals date day, hide day and show only time
	 * 
	 * @return return formated date time
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getFormattedDateTime(long dateTimeMillis, boolean displayTime) {
		// get today calendar
		Calendar today = Calendar.getInstance();

		// get date calendar
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(dateTimeMillis);

		SimpleDateFormat dateformat = new SimpleDateFormat();

		// if today year is not equal date year, show year
		if (date.get(Calendar.YEAR) != today.get(Calendar.YEAR)) {
			dateformat.applyPattern(displayTime ? "dd/MM/yy, hh:mma" : "dd/MM/yyyy");
		}
		// if today month is not equal date month, show month
		else if (date.get(Calendar.MONTH) != today.get(Calendar.MONTH)) {
			dateformat.applyPattern(displayTime ? "dd/MM, hh:mma" : "dd/MM");
		}
		// if today day is not equal date day, show day
		else if (date.get(Calendar.DAY_OF_MONTH) != today.get(Calendar.DAY_OF_MONTH)) {
			dateformat.applyPattern(displayTime ? "E, hh:mma" : "E");
		}
		// otherwise, show time only
		else {
			dateformat.applyPattern("hh:mma");
		}

		return dateformat.format(date.getTime());
	}

	@SuppressLint("SimpleDateFormat")
	public static String getFormattedDay(long dateTimeMillis) {
		// get today calendar
		Calendar today = Calendar.getInstance();

		// get date calendar
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(dateTimeMillis);

		SimpleDateFormat dateformat = new SimpleDateFormat();

		// if today year is not equal date year, show year
		if (date.get(Calendar.YEAR) != today.get(Calendar.YEAR)) {
			dateformat.applyPattern("dd/MM/yyyy");
		}
		// if today month is not equal date month, show month
		else if (date.get(Calendar.MONTH) != today.get(Calendar.MONTH)) {
			dateformat.applyPattern("dd/MM");
		}
		// if today day is not equal date day, show day
		else if (date.get(Calendar.DAY_OF_MONTH) != today.get(Calendar.DAY_OF_MONTH)) {
			dateformat.applyPattern("E");
		}
		// otherwise, show nothing
		else {
			return "";
		}

		return dateformat.format(date.getTime());
	}

	@SuppressLint("SimpleDateFormat")
	public static String getFormattedTime(long dateTimeMillis) {

		// get date calendar
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(dateTimeMillis);

		SimpleDateFormat dateformat = new SimpleDateFormat();

		// apply time only pattern
		dateformat.applyPattern("hh:mma");

		return dateformat.format(date.getTime());
	}

}
