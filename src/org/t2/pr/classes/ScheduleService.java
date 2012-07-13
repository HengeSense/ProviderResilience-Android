package org.t2.pr.classes;

import java.util.Calendar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ScheduleService extends Service {

	public class ServiceBinder extends Binder {
		ScheduleService getService() {
			return ScheduleService.this;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private final IBinder mBinder = new ServiceBinder();

	public void setAlarm(Context ctx, Calendar c) {
		new AlarmTask(ctx, c).run();
	}
}