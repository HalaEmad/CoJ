/**
 * 
 */
package com.ibm.android.kit.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * @author bassam
 *
 */
public class PushUtility {

	// private static final String PUSH_SENDER_ID = "334112823056";

	public static String getPushRegId(Context context, String senderId) {

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);

		String regid = "";
		try {
			if (gcm == null) {
				gcm = GoogleCloudMessaging.getInstance(context);
			}

			regid = gcm.register(senderId);
			Log.i("##PUSH##", "Reg id: " + regid);

			// You should send the registration ID to your server over HTTP,
			// so it can use GCM/HTTP or CCS to send messages to your app.
			// The request to your server should be authenticated if your app
			// is using accounts.
			// sendRegistrationIdToBackend();

			// For this demo: we don't need to send it because the device
			// will send upstream messages to a server that echo back the
			// message using the 'from' address in the message.

			// Persist the registration ID - no need to register again.
			// storeRegistrationId(context, regid);
		} catch (IOException ex) {
			// If there is an error, don't just keep trying to register.
			// Require the user to click a button again, or perform
			// exponential back-off.
			ex.printStackTrace();
		}

		return regid;
	}

}
