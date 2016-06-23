package com.ibm.android.kit.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionUtility
{
	private ConnectionUtility()
	{
	}

	/**
	 * check for Internet connection availability
	 * 
	 * @return
	 */
	public static boolean isInternetConnectionExist()
	{

		// http://www.msftncsi.com/ncsi.txt used by Microsoft to check for
		// Internet access in
		// windows
		return ping("http://www.msftncsi.com/ncsi.txt", 1000);
	}

	/**
	 * check network connection availability
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnectionExist(Context context)
	{
		try
		{
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo active = connectivityManager.getActiveNetworkInfo();
			if (active == null
					|| active.getState() != NetworkInfo.State.CONNECTED)
				return false;
		}
		catch (Exception ex)
		{
			return false;
		}

		return true;
	}

	/**
	 * check network WIFI connection availability
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkWIFIConnectionExist(Context context)
	{
		if (getActiveConnectionType(context) == ConnectivityManager.TYPE_WIFI)
			return true;
		else
			return false;
	}

	/**
	 * get active connection type
	 * 
	 * @param context
	 * @return
	 */
	public static int getActiveConnectionType(Context context)
	{
		int connectionType = -1;

		try
		{
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork = connectivityManager
					.getActiveNetworkInfo();
			if (activeNetwork == null
					|| activeNetwork.getState() != NetworkInfo.State.CONNECTED)
				connectionType = -1;
			else
				connectionType = activeNetwork.getType();
		}
		catch (Exception ex)
		{
			connectionType = -1;
		}

		return connectionType;
	}

	/**
	 * ping for specific URL
	 * 
	 * @param url
	 * @param timeout
	 * @return
	 */
	public static boolean ping(String url, int timeout)
	{
		url = url.replace("https", "http"); // Otherwise an exception may be
											// thrown on invalid SSL
											// certificates.
		try
		{
			HttpURLConnection connection = (HttpURLConnection) new URL(url)
					.openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("HEAD");
			int responseCode = connection.getResponseCode();
			return (200 <= responseCode && responseCode <= 399);
		}
		catch (IOException exception)
		{
			return false;
		}
	}
}
