/**
 * 
 */
package com.ibm.android.kit.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

//import com.pointify.android.engine.SpeakerAppManager;

/**
 * @author bahamada
 */
public class ProximityReceiver extends BroadcastReceiver {
	public static final String PROXIMITY_ID_KEY = "proximity.id.key";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("ProximityReceiver", "proximity location fired");

		boolean entering = intent.getExtras().getBoolean(LocationManager.KEY_PROXIMITY_ENTERING);

		if (entering)
			Log.d("ProximityReceiver", "proximity entering");
		else
			Log.d("ProximityReceiver", "proximity exiting");

		// get proximity entry
		int id = intent.getIntExtra(PROXIMITY_ID_KEY, -1);
		Log.d("ProximityReceiver", "entery id: " + id);

		// SpeakerAppManager.getInstance(context)
		// .triggerNotification(id, entering);

	}

	// private void writeProximityInfo(Context context, ProximityInfo info,
	// boolean entering)
	// {
	// String proximityStatus = "";
	//
	// proximityStatus += context.getString(R.string.proximity_alert_status,
	// (entering
	// ? context.getString(R.string.pref_proximity_enter)
	// : context.getString(R.string.pref_proximity_exit)));
	// proximityStatus += "\n"
	// + context
	// .getString(R.string.proximity_alert_lat, info.getLat());
	// proximityStatus += "\n"
	// + context
	// .getString(R.string.proximity_alert_lng, info.getLng());
	// proximityStatus += "\n"
	// + context.getString(R.string.proximity_alert_radius,
	// info.getRadius());
	// proximityStatus += "\n"
	// + context.getString(R.string.proximity_alert_enter,
	// info.isEnter());
	// proximityStatus += "\n"
	// + context.getString(R.string.proximity_alert_exit,
	// info.isExit());
	//
	// FileUtility.writeFileToSD(context,
	// LocationController.PROXIMITY_FOLDER_NAME,
	// LocationController.PROXIMITY_FILE_NAME, proximityStatus, true);
	// }

	// private void showNotification(Context context, boolean entering)
	// {
	// String notificationText = context.getString(
	// R.string.proximity_alert_status,
	// (entering
	// ? context.getString(R.string.pref_proximity_enter)
	// : context.getString(R.string.pref_proximity_exit)));
	// NotificationInfo info = new NotificationInfo();
	// info.id = NOTIFICATION_ID;
	// info.defaultSound = true;
	// info.defaultVibrate = true;
	// info.icon = R.drawable.ic_launcher;
	// info.text = notificationText;
	// info.tickerText = notificationText;
	// info.title = context.getString(R.string.proximity_alert);
	// NotificationUtility.showNotification(context, info);
	// }
}
