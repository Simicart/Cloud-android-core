/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.simicart.core.notification;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.simicart.R;
import com.simicart.MainActivity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.notification.common.CommonUtilities;
import com.simicart.core.notification.common.ServerUtilities;
import com.simicart.core.notification.entity.NotificationEntity;
import com.simicart.core.notification.gcm.GCMBaseIntentService;
import com.simicart.core.notification.gcm.GCMRegistrar;
import com.simicart.core.shortcutbadger.ShortcutBadgeException;
import com.simicart.core.shortcutbadger.ShortcutBadger;
import com.simicart.core.splashscreen.SplashActivity;

/**
 * IntentService responsible for handling GCM messages.
 */
@SuppressLint("NewApi")
public class GCMIntentService extends GCMBaseIntentService {

	public static NotificationEntity notificationData;

	public GCMIntentService() {
		super(Config.getInstance().getSenderId());
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		CommonUtilities.displayMessage(context,
				"From GCM: device successfully registered!");
		ServerUtilities.register(context, registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		CommonUtilities.displayMessage(context,
				"From GCM: device successfully unregistered!");
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			ServerUtilities.unregister(context, registrationId);
		}
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String content = intent.getExtras().getString("message");
		String action = intent.getAction();
		NotificationEntity notificationTemp = null;

		if (action.equals("com.google.android.c2dm.intent.RECEIVE")) {
			try {
				JSONObject json = null;
				if (Utils.validateString(content)) {
					json = new JSONObject(content);
				}
				if (json != null) {
					notificationTemp = new NotificationEntity();
					if (json.has(Constants.MESSAGE)) {
						notificationTemp.setMessage(json
								.getString(Constants.MESSAGE));
					}
					if (json.has(Constants.TITLE)) {
						notificationTemp.setTitle(json
								.getString(Constants.TITLE));
					}
					if (json.has(Constants.URL)) {
						notificationTemp.setUrl(json.getString(Constants.URL));
					}
					if (json.has("type")) {
						notificationTemp.setType(json.getString("type"));
					}
					if(json.has("category")){
						JSONObject categoryObj = json.getJSONObject("category");
						if (categoryObj.has("category_id")) {
							notificationTemp.setCategoryID(categoryObj
									.getString("category_id"));
						}
						if (categoryObj.has("name")) {
							notificationTemp.setCategoryName(categoryObj
									.getString("name"));
						}
						if (categoryObj.has("has_children")) {
							notificationTemp.setHasChild(categoryObj
									.getString("has_children"));
						}
					}
					if (json.has("imageUrl")) {
						notificationTemp.setImage(json.getString("imageUrl"));
					}
					if (json.has("productID")) {
						notificationTemp.setProductID(json
								.getString("productID"));
					}
					if (json.has("show_popup")) {
						notificationTemp.setShowPopup(json
								.getString("show_popup"));
					}
				} else {
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (notificationTemp != null) {
				if (notificationData == null) {
					notificationData = notificationTemp;
					if (DataLocal.enableNotification(context)) {
						onRecieveMessage(context);
					}
				} else {
					if (!notificationTemp.equals(notificationData)) {
						notificationData = notificationTemp;
						if (DataLocal.enableNotification(context)) {
							onRecieveMessage(context);
						}
					}
				}
			}
		}

	}

	private void onRecieveMessage(Context context) {
		// Sang man hinh khi co notification
		PowerManager pm = (PowerManager) getApplicationContext()
				.getSystemService(Context.POWER_SERVICE);
		WakeLock wakeLock = pm
				.newWakeLock(
						(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
								| PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP),
						"TAG");
		wakeLock.acquire(5000);


		// check app open or close
		if (MainActivity.context != null
				&& MainActivity.state != MainActivity.PAUSE
				&& notificationData.getShowPopup().equals("1")) {
			createNotification(context);
		} else {
			generateNotification(context, notificationData);
		}
	}

	private void createNotification(Context context) {
		Intent i = new Intent(context, NotificationActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.putExtra("NOTIFICATION_DATA", notificationData);
		context.startActivity(i);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		String message = ("From GCM: server deleted %1$d pending messages!" + total);
		CommonUtilities.displayMessage(context, message);
		// notifies user
		NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setMessage(message);
		notificationEntity.setTitle("DELETE MES");
		notificationEntity.setUrl("");
		generateNotification(context, notificationEntity);
	}

	@Override
	public void onError(Context context, String errorId) {
		CommonUtilities.displayMessage(context, ("From GCM: error" + errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		CommonUtilities.displayMessage(context,
				("From GCM: recoverable error (%1$s)." + errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	// private static int numMessages = 0;
	public static int notifyID = 0;

	private void generateNotification(Context context,
									  NotificationEntity notificationData) {
		// Invoking the default notification service
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Sets an ID for the notification, so it can be updated

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setContentTitle(notificationData.getTitle())
				.setContentText(notificationData.getMessage())
				.setSmallIcon(R.drawable.default_icon);

		mBuilder.setDefaults(Notification.DEFAULT_ALL);

		// mBuilder.setNumber(++numMessages);
		Intent resultIntent = new Intent(context, SplashActivity.class);
		if (notificationData.getShowPopup().equals("1")) {
			resultIntent.putExtra("NOTIFICATION_DATA", notificationData);
		}
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(SplashActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack

		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
				notifyID, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);

		mBuilder.setAutoCancel(true);// cancel after click notification
		// mId allows you to update the notification later on.
		mNotificationManager.notify(++notifyID, mBuilder.build());

		// Update badge
		try {
			ShortcutBadger.setBadge(context, notifyID);
		} catch (ShortcutBadgeException e) {
		}
	}
}
