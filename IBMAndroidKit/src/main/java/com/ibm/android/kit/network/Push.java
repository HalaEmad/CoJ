/**
 * 
 */
package com.ibm.android.kit.network;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;


/**
 * @author bassam
 *
 */
public abstract class Push {

	protected Object input;

	protected abstract void preAction();

	protected abstract void postAction();

	protected abstract Object getInput(String json);

	protected Context context;

	public Push(Context context) {
		this.context = context;
	}

	public void execute(String json) {

		this.input = getInput(json);

		preAction();

		showNotification();

		postAction();
	}

	private void showNotification() {

		String text = getNotificationText();

		if (text != null && !text.trim().isEmpty()) {

			NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
			builder.setSmallIcon(getAppIcon());
			builder.setContentTitle(text);
			builder.setContentText(text);
			builder.setAutoCancel(true);
			builder.setContentIntent(getNotificationIntent());
			builder.setDefaults(Notification.DEFAULT_ALL);

			NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(getNotificationId(), builder.build());
		}
	}

	protected abstract int getAppIcon();

	protected String getNotificationText() {
		return null;
	}

	protected PendingIntent getNotificationIntent() {
		return null;
	}

	protected int getNotificationId() {
		return 0;
	}
}
