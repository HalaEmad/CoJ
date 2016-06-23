package com.ibm.android.kit.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Base64;

@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class GeneralUtility {

	public static int compareTo(long l1, long l2) {

		// AkLog.i("long 1",l1+"");
		// AkLog.i("long 2",l2+"");

		int compare = 0;
		if (l1 < l2)
			compare = -1;
		else if (l1 > l2)
			compare = 1;
		// AkLog.i("long compare",compare+"");

		return compare;
	}

	/**
	 * check if the string match specific regular expression
	 */

	public static boolean isMatch(String string, String regularExpString) {

		Pattern pttrn = Pattern.compile(regularExpString);
		// Match the given string with the pattern
		Matcher match = pttrn.matcher(string);

		return match.matches();
	}

	/**
	 * Check if text is empty or equal null
	 * 
	 * @param text
	 * @return true if is empty, false otherwise
	 */
	public static boolean isEmptyString(String text) {

		return (text == null || text.equals("") || text.equals("null"));
	}

	public static String convertStreamToString(InputStream is, String charset) {

		if (is == null)
			return null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, charset));
		} catch (UnsupportedEncodingException e1) {
			return null;
		}
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException ex) {

			ex.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		return sb.toString();

	}

	public static String convertStreamToString(InputStream is) {

		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		if (is == null)
			return null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"/* "Windows-1252" */));
		} catch (UnsupportedEncodingException e1) {
			try {
				reader = new BufferedReader(new InputStreamReader(is));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException ex) {

			ex.printStackTrace();
		} finally {
			try {
				reader.close();
				is.close();

			} catch (IOException e) {
			}
		}
		return sb.toString();
	}

	/**
	 * Get application version
	 * 
	 * @return
	 */
	public static String getApplicationVersion(Context context) {

		String versionName = "";
		try {
			versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {

			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * Get device id
	 * 
	 * @return
	 */
	public static String getDeviceId(Context context) {

		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * Get application build number
	 * 
	 * @return
	 */
	public static int getApplicationBuildNumber(Context context) {

		int versionCode = 0;
		try {
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		// AkLog.i("VERSION CODE", versionCode+"");
		return versionCode;
	}

	static final String DISPLAY_MESSAGE_ACTION = "com.shell.sitibv.retailsitemanager.pushnotification.DISPLAY_MESSAGE";

	static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	static void displayMessage(Context context, String message) {

		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	/*
	 * static public String[] getDateAndTime(long time, boolean trimYear) {
	 * 
	 * String[] res = new String[2];
	 * 
	 * try { SimpleDateFormat sdf;
	 * 
	 * sdf.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
	 * Date resultdate = new Date(time); String info = sdf.format(resultdate);
	 * sdf.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
	 * Date resultdate = new Date(time); String info = sdf.format(resultdate);
	 * 
	 * res = info.split(" - "); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return res; }
	 */

	static public String getClockPart(Context myAct, long time) {

		String clockStr = "";

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
			String info = sdf.format(new Date(time));
			String secondsPart = "";
			String[] timeParts = info.split(":");

			if (timeParts.length == 3) {
				secondsPart = timeParts[2];
			}

			String[] clockParts = DateFormat.getTimeFormat(myAct).format(time).split(" ");

			if (clockParts.length == 1 && clockParts[0].contains(":")) {
				clockStr = clockParts[0] + ":" + secondsPart;
			} else if (clockParts.length == 2 && clockParts[0].contains(":")) {
				clockStr = clockParts[0] + ":" + secondsPart + " " + clockParts[1];
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return clockStr;
	}

	static public String getExpClockPart(Context myAct, long time) {

		String clockStr = "";

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			sdf.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
			String info = sdf.format(new Date(time));

			clockStr = info;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clockStr;
	}

	static public String getDatePart(Context myAct, long time) {

		String clockStr = "";

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			sdf.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
			clockStr = sdf.format(new Date(time));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return clockStr;
	}

	static public String getDateAndTime(Context myAct, long time) {

		String res = "";

		try {

			res = DateFormat.getDateFormat(myAct).format(time) + " " + getClockPart(myAct, time);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	static public String getExceptionalDateAndTime(Context myAct, long time) {

		String res = "";

		try {

			res = getDatePart(myAct, time) + " " + getExpClockPart(myAct, time);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	static public long getStringDateAndTimeToLong(Context myAct, String time) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyyHH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
		Date fromDate = null;

		try {
			fromDate = (Date) sdf.parse(time);
			return fromDate.getTime();
			// System.out.println(fromDate.getTime());

			// System.out.println(toDate.getTime());
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		return 978307200000L;
	}

	static public String[] getDateAndTimeArray(Activity myAct, long time) {

		String[] res = new String[] { "", "" };

		try {
			res[0] = DateFormat.getDateFormat(myAct).format(time);
			res[1] = getClockPart(myAct, time);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	private static String pad(String c) {

		try {
			int val = Integer.valueOf(c);
			if (val >= 10)
				return val + "";
			else
				return "0" + val;
		} catch (Exception e) {
			e.printStackTrace();
			return c;
		}
	}

	public static String buildTimeStructure(String hours, String minutes) {

		int hoursVal = 0;
		int minutesVal = 0;
		boolean errorFound = false;

		try {
			hoursVal = Integer.valueOf(hours);
		} catch (Exception e) {
			errorFound = true;
		}
		try {
			minutesVal = Integer.valueOf(minutes);
		} catch (Exception e) {
			e.printStackTrace();
			errorFound = true;
		}

		if (errorFound) {
			return hours + ":" + minutes;
		} else if (hoursVal >= 0 && minutesVal >= 0) {

			return (new StringBuilder().append(pad(hours)).append(":").append(pad(minutes))).toString();
		}

		return "";
	}

	public static String getTimeIfIsValid(String hour, String minutes) {

		try {

			int hourVal = 0;
			try {
				hourVal = Integer.valueOf(hour);
			} catch (Exception e) {
				e.printStackTrace();
				hourVal = 0;
			}

			int minutesVal = 0;
			try {
				minutesVal = Integer.valueOf(minutes);
			} catch (Exception e) {
				e.printStackTrace();
				minutesVal = 0;
			}

			return (new StringBuilder().append(pad(hourVal + "")).append(":").append(pad(minutesVal + ""))).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// public static double PRICE_STEP_VALUE = 0.001;

	public enum Controller_Type {
		BTN_PLUS, BTN_MINUS, TEXTBOX
	}

	public static String extractExpDateFromString(Activity myAct, long lastChanged, String stamp, String tag1,
			String tag2) {

		return subTitleBuilder(tag1, tag2, DateFormat.getDateFormat(myAct).format(lastChanged),
				getClockPart(myAct, lastChanged), stamp);

	}

	public static String subTitleBuilder(String tag1, String tag2, String date, String time, String strStamp) {

		return (strStamp.replace(tag1, date)).replace(tag2, time);
	}

	public static String getFPSUpdateDateTime(boolean withTimeZone) {

		String timeZone = "";
		if (withTimeZone) {
			timeZone = (new SimpleDateFormat("Z")).format(Calendar.getInstance().getTime());
		}

		return "/Date(" + System.currentTimeMillis() + timeZone + ")/";
	}

	@SuppressLint("DefaultLocale")
	public static boolean loclizationNumberStringTest() {

		return String.format("%.3f", 1.1).contains(",");
	}

	public static String getFPSUpdateDateTime(boolean withTimeZone, long time) {

		String timeZone = "";
		if (withTimeZone) {
			timeZone = (new SimpleDateFormat("Z")).format(time);
		}

		return "/Date(" + time + timeZone + ")/";
	}

	public static String valueCutOff(String val, int afterDotNumbersCoun) {

		String res = val;

		try {
			if (!(val.matches("\\d*.[\\d]{0,3}"))) {
				BigDecimal fDecimal = new BigDecimal(Double.valueOf(val));
				BigDecimal cutted = fDecimal.setScale(afterDotNumbersCoun, RoundingMode.DOWN);
				res = String.format("%.3f", cutted.doubleValue());
			} else {
				res = String.format("%.3f", Double.parseDouble(val));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * Format number based on decimal points
	 * 
	 * @param val
	 * @param afterDotNumbersCoun
	 * @return
	 */
	public static String valueFormat(String val, int afterDotNumbersCoun, boolean englishLocal) {

		String res = val;
		if (afterDotNumbersCoun < 0)
			afterDotNumbersCoun = 3;

		try {
			if (!(val.matches("\\d*.[\\d]{0," + afterDotNumbersCoun + "}"))) {
				BigDecimal fDecimal = new BigDecimal(Double.valueOf(val));
				BigDecimal cutted = fDecimal.setScale(afterDotNumbersCoun, RoundingMode.DOWN);
				if (englishLocal)
					res = String.format(Locale.ENGLISH, "%." + afterDotNumbersCoun + "f", cutted.doubleValue());
				else

					res = String.format("%." + afterDotNumbersCoun + "f", cutted.doubleValue());
			} else {
				if (englishLocal)
					res = String.format(Locale.ENGLISH, "%." + afterDotNumbersCoun + "f", Double.parseDouble(val));
				else
					res = String.format("%." + afterDotNumbersCoun + "f", Double.parseDouble(val));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public static float getDecimalStep(int decimalPoint) {

		float step = 1 / (float) (Math.pow(10, decimalPoint));

		return step;

	}

	public static String valueRound(String val, int afterDotNumbersCoun) {

		String res = "0.000";

		try {
			res = String.format("%.3f", Float.valueOf(val));
		} catch (Exception e) {
			e.printStackTrace();
			res = "0.000";
			e.printStackTrace();
		}

		return res;
	}

	public static double valueCutOff(double val, int afterDotNumbersCoun) {

		double floatVal = val;

		try {

			if (!((floatVal + "").matches("\\d*.[\\d]{0," + afterDotNumbersCoun + "}"))) {
				BigDecimal fDecimal = new BigDecimal(floatVal);
				BigDecimal cutted = fDecimal.setScale(afterDotNumbersCoun, RoundingMode.DOWN);
				floatVal = cutted.floatValue();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return floatVal;
	}

	public static InputFilter localizationEditTextFilter = new InputFilter() {

		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

			if (source.length() > 0) {

				if (loclizationNumberStringTest()) {
					if (source.charAt(0) == '.') {
						if (dest.toString().contains(",")) {
							return "";
						} else {
							return ",";
						}
					}
				} else {
					if (source.charAt(0) == '.') {
						if (dest.toString().contains(".")) {
							return "";
						} else {
							return ".";
						}
					}
				}
			}

			return null;
		}
	};

	public static long dateStrToLong(String dateStr) {

		// mfarag @ 14-9-2013 ,updated to set the default value in case of null
		long res = 978307200000L;

		if (!GeneralUtility.isEmptyString(dateStr)) {

			try {
				String[] timeParts = dateStr.split("[(]");
				timeParts = timeParts[1].split("[)]");

				if (timeParts[0].contains("+")) {
					timeParts = timeParts[0].split("[+]");
				} else if (timeParts[0].contains("-")) {
					timeParts = timeParts[0].split("[-]");
				}

				res = Long.valueOf(timeParts[0]);

			} catch (Exception e) {

				res = 978307200000L;

				e.printStackTrace();
			}
		}

		return res;
	}

	public static String fileNameFromPath(String path) {

		return path.substring(path.lastIndexOf('/') + 1);
	}

	public static String twoDigitsLeadingZero(int number) {

		return String.format(Locale.ENGLISH, "%02d", number);
	}

	public static boolean isEmptyList(List<?> list) {

		return (list == null || list.size() == 0);
	}

	public static boolean isEmptyArray(Object[] array) {

		return (array == null || array.length == 0);
	}

	public static String computeHash(String input) {

		String hashCode = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();

			byte[] inputByte = digest.digest(input.getBytes("UTF-8"));

			// StringBuffer sb = new StringBuffer();
			//
			// // for (int i = 0; i < byteData.length; i++) {
			// // sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
			// // .substring(1));
			// // }
			//
			// hashCode = sb.toString();

			hashCode = Base64.encodeToString(inputByte, Base64.DEFAULT).trim();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return hashCode;
	}

	public static int parseInteger(String str) {
		// if (str.matches("[0-9]+"))
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// else
		// AkLog.w("CANNOT PARSE INTEGER", str);
		return 0;
	}

	public static double parseDouble(String str) {
		// if (Pattern.matches("([0-9]*)\\.([0-9]*)", str))
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// else
		// AkLog.w("CANNOT PARSE DOUBLE", str);
		return 0.00;
	}

	public static long parseLong(String str) {
		// if (str.matches("[0-9]+"))
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// else
		// AkLog.w("CANNOT PARSE LONG", str);
		return 0L;
	}

	public static String join(List<String> array, String separator) {

		String str = "";

		if (array != null) {
			for (int i = 0; i < array.size(); i++) {

				String item = array.get(i);
				str += item;
				if (i < array.size() - 1) {
					str += separator;
				}
			}
		}

		return str;
	}

	public static List<String> split(String str, String separator) {

		List<String> array = new ArrayList<String>();

		if (str != null && !str.trim().isEmpty()) {
			String[] strArray = str.split(separator);
			array = new ArrayList<String>(Arrays.asList(strArray));
		}

		return array;

	}

	public static String getIMEI(Context context) {
		String imei = "";
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		imei = telephonyManager.getDeviceId();

		return imei;
	}

}
