/**
 * 
 */
package com.ibm.android.kit.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

import com.ibm.android.kit.receivers.ProximityReceiver;


/**
 * @author bahamada
 */
public class LocationUtility {
	private static final String LOC_PROXIMITY_INTENT_ACTION = "com.courier.android.action.PROXIMITY_ALERT";
	private static final String LOC_UPDATE_INTENT_ACTION = "com.courier.android.action.LOCATION_UPDATE";
	private static final String LOC_SINGLE_UPDATE_INTENT_ACTION = "com.courier.android.action.SINGLE_LOCATION_UPDATE";

	public static final String TYPE_KEY = "type.key";
	public static final String SESSION_ID_KEY = "session.id.key";

	public static void requestLocationUpdates(Context context, int type, String provider, long minTime,
			float minDistance, LocationListener listener) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(provider)) {
			locationManager.requestLocationUpdates(provider, minTime, minDistance, listener);
		}
	}

	public static void requestLocationUpdates(Context context, int type, String sessionId, String provider,
			long minTime, float minDistance) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(provider)) {
			// create pending intent
			Intent intent = new Intent(LOC_UPDATE_INTENT_ACTION);
			intent.putExtra(TYPE_KEY, type);
			intent.putExtra(SESSION_ID_KEY, sessionId);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

			locationManager.requestLocationUpdates(provider, minTime, minDistance, pendingIntent);
		}
	}

	public static Location getLastLocation(Context context, String provider) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		return locationManager.getLastKnownLocation(provider);
	}

	public static void requestSingleUpdate(Context context, int type, String provider, LocationListener listener) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		locationManager.requestSingleUpdate(provider, listener, null);
	}

	public static void requestSingleUpdate(Context context, int type, String sessionId, String provider) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// create pending intent
		Intent intent = new Intent(LOC_SINGLE_UPDATE_INTENT_ACTION);
		intent.putExtra(TYPE_KEY, type);
		intent.putExtra(SESSION_ID_KEY, sessionId);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

		locationManager.requestSingleUpdate(provider, pendingIntent);
	}

	public static void removeLocationUpdate(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// create pending intent
		Intent intent = new Intent(LOC_UPDATE_INTENT_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		locationManager.removeUpdates(pendingIntent);
	}

	public static void removeSingleLocationUpdate(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// create pending intent
		Intent intent = new Intent(LOC_SINGLE_UPDATE_INTENT_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		locationManager.removeUpdates(pendingIntent);
	}

	public static void addProximityAlert(Context context, int id, double lat, double lng, float radius, boolean enter,
			boolean exit) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// create pending intent
		Intent intent = new Intent(LOC_PROXIMITY_INTENT_ACTION);
		intent.putExtra(ProximityReceiver.PROXIMITY_ID_KEY, id);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

		locationManager.addProximityAlert(lat, lng, radius, -1, pendingIntent);
	}

	public static void removeProximityAlert(Context context, int id) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// create pending intent
		Intent intent = new Intent(LOC_PROXIMITY_INTENT_ACTION);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

		locationManager.removeProximityAlert(pendingIntent);
	}

	public static float getDistanceBetween(Location start, Location end) {
		float[] results = new float[5];

		if (start != null && end != null) {
			Location.distanceBetween(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude(),
					results);
		}
		return results[0];
	}

	public static void turnGPSOn(Context context) {
		// Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		// intent.putExtra("enabled", true);
		// context.sendBroadcast(intent);

		String provider = Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.contains("gps")) { // if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			context.sendBroadcast(poke);

		}
	}

	// automatic turn off the gps
	public static void turnGPSOff(Context context) {
		String provider = Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (provider.contains("gps")) { // if gps is enabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			context.sendBroadcast(poke);
		}
	}

	public static boolean canToggleGPS(Context context) {
		PackageManager pacman = context.getPackageManager();
		PackageInfo pacInfo = null;

		try {
			pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
		} catch (NameNotFoundException e) {
			return false; // package not found
		}

		if (pacInfo != null) {
			for (ActivityInfo actInfo : pacInfo.receivers) {
				// test if recevier is exported. if so, we can toggle GPS.
				if (actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported) {
					return true;
				}
			}
		}

		return false; // default
	}

	public static boolean isProviderEnabled(Context context, String provider) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(provider);
	}

	public static void showLocationSettings(Context context) {
		Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		context.startActivity(gpsOptionsIntent);
	}

	public static void enableGps(Context context) {
		// Enable GPS
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		context.sendBroadcast(intent);
	}

	public static void disableGps(Context context) {
		// Disable GPS
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", false);
		context.sendBroadcast(intent);
	}

	public static void getDirections(Context context, String postalDes) {

		Uri gmmIntentUri = Uri.parse("google.navigation:q=" + postalDes);
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		mapIntent.setPackage("com.google.android.apps.maps");
		context.startActivity(mapIntent);
	}

	public static void getDirections(Context context, double latDes, double lngDes) {

		// Uri gmmIntentUri =
		// Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");
		Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latDes + ", " + lngDes);
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		mapIntent.setPackage("com.google.android.apps.maps");
		context.startActivity(mapIntent);
	}
}
