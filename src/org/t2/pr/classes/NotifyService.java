package org.t2.pr.classes;

import java.util.Calendar;

import org.t2.pr.R;
import org.t2.pr.activities.HomeActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class NotifyService extends Service {

	public class ServiceBinder extends Binder {
		NotifyService getService() {
			return NotifyService.this;
		}
	}

	private static final int NOTIFICATION = 123;
	public static final String INTENT_NOTIFY = "org.t2.pr.INTENT_NOTIFY";
	private NotificationManager mNM;

	@Override
	public void onCreate() {
		Log.i("NotifyService", "onCreate()");
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("LocalService", "Received start id " + startId + ": " + intent);

		if(intent.getBooleanExtra(INTENT_NOTIFY, false))
			showNotification();		

		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private final IBinder mBinder = new ServiceBinder();

	private void showNotification() {
		
		String cdate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
		if(SharedPref.getLastAssessmentDate() != cdate)
		{
			CharSequence title = "Provider Resilience";
			int icon = R.drawable.icon;
			CharSequence text = "You've not yet done today's assessment.";		
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, 1);
			c.set(Calendar.HOUR_OF_DAY, SharedPref.getNotifyHour());
			c.set(Calendar.MINUTE, SharedPref.getNotifyMinute());

			Notification notification = new Notification(icon, text, c.getTimeInMillis());
			Intent prIntent = new Intent(this, HomeActivity.class);
			//prIntent.putExtra("screen", "something"); //Used to open directly to assessment screen
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, prIntent, 0);
			notification.setLatestEventInfo(this, title, text, contentIntent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			

			mNM.notify(NOTIFICATION, notification);
		}
		stopSelf();
	}
}
