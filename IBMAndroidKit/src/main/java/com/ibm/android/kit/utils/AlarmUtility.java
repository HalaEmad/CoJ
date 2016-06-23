/**
 * 
 */
package com.ibm.android.kit.utils;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * @author bhamada
 */
public class AlarmUtility
{
	// repeating values
	public static final int REPEATING_NONE = 0;

	public static final int REPEATING_DAILY = 1;

	public static final int REPEATING_WEEKLY = 2;

	public static final int REPEATING_MONTHLY = 3;

	private AlarmUtility()
	{

	}

	/**
	 * set alarm to specific hour and minute
	 * 
	 * @param context
	 * @param alarmIntent
	 * : alarm receiver intent
	 * @param id
	 * : alarm id
	 * @param hour
	 * : alarm hour
	 * @param minute
	 * : alarm minute
	 * @param repeating
	 * : one of the Reappearing values (NONE, DAILY, WEEKLY, MONTHLY)
	 * @param dayOfWeek
	 * : must be set if repeating is WEEKLY, else set to 0
	 * @param dayOfMonth
	 * : must be set if repeating is MONTHLY, else set to 0
	 */
	public static void setAlarm(Context context, int reqCode,
			Intent alarmIntent, Calendar alarmCalendar, int repeating,
			int dayOfWeek, int dayOfMonth)
	{
		PendingIntent sender = PendingIntent.getBroadcast(context, reqCode,
				alarmIntent, 0);

		AlarmManager alarm = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		// current calendar for comparing (repeat)
		Calendar currentCalendar = Calendar.getInstance();

		switch (repeating)
		{
		case REPEATING_NONE :

			alarm.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(),
					sender);

		break;

		case REPEATING_DAILY :
			if (alarmCalendar.compareTo(currentCalendar) == -1
					|| alarmCalendar.compareTo(currentCalendar) == 0)
				alarmCalendar.add(Calendar.DAY_OF_WEEK, 1);

			alarm.setRepeating(AlarmManager.RTC_WAKEUP,
					alarmCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
					sender);
		break;

		case REPEATING_WEEKLY :
			alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

			if (alarmCalendar.compareTo(currentCalendar) == -1
					|| alarmCalendar.compareTo(currentCalendar) == 0)
				alarmCalendar.add(Calendar.WEEK_OF_MONTH, 1);

			alarm.setRepeating(AlarmManager.RTC_WAKEUP,
					alarmCalendar.getTimeInMillis(),
					(AlarmManager.INTERVAL_DAY * 7), sender);
		break;

		case REPEATING_MONTHLY :
			alarmCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			if (alarmCalendar.compareTo(currentCalendar) == -1
					|| alarmCalendar.compareTo(currentCalendar) == 0)
				alarmCalendar.add(Calendar.MONTH, 1);

			alarm.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar
					.getTimeInMillis(),
					(AlarmManager.INTERVAL_DAY * monthsDays[alarmCalendar
							.get(Calendar.MONTH)]), sender);
		break;
		}
	}

	/**
	 * Cancel alarm
	 * 
	 * @param context
	 * @param intent
	 * : alarm intent
	 * @param id
	 * : alarm id
	 */
	public static void cancelAlarms(Context context, Intent alarmIntent)
	{
		PendingIntent sender = PendingIntent.getBroadcast(context, 0,
				alarmIntent, 0);

		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		am.cancel(sender);
	}

	// days length on every month starting from January at index 0
	private final static byte[] monthsDays = {31, // January
			28, // February
			31, // March
			30, // April
			31, // May
			30, // June
			31, // July
			31, // August
			30, // September
			31, // October
			30, // November
			31, // December
	};
}
