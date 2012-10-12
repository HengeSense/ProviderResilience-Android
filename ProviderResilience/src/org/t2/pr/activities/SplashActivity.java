/*
 * 
 * Provider Resilience
 * 
 * Copyright © 2009-2012 United States Government as represented by 
 * the Chief Information Officer of the National Center for Telehealth 
 * and Technology. All Rights Reserved.
 * 
 * Copyright © 2009-2012 Contributors. All Rights Reserved. 
 * 
 * THIS OPEN SOURCE AGREEMENT ("AGREEMENT") DEFINES THE RIGHTS OF USE, 
 * REPRODUCTION, DISTRIBUTION, MODIFICATION AND REDISTRIBUTION OF CERTAIN 
 * COMPUTER SOFTWARE ORIGINALLY RELEASED BY THE UNITED STATES GOVERNMENT 
 * AS REPRESENTED BY THE GOVERNMENT AGENCY LISTED BELOW ("GOVERNMENT AGENCY"). 
 * THE UNITED STATES GOVERNMENT, AS REPRESENTED BY GOVERNMENT AGENCY, IS AN 
 * INTENDED THIRD-PARTY BENEFICIARY OF ALL SUBSEQUENT DISTRIBUTIONS OR 
 * REDISTRIBUTIONS OF THE SUBJECT SOFTWARE. ANYONE WHO USES, REPRODUCES, 
 * DISTRIBUTES, MODIFIES OR REDISTRIBUTES THE SUBJECT SOFTWARE, AS DEFINED 
 * HEREIN, OR ANY PART THEREOF, IS, BY THAT ACTION, ACCEPTING IN FULL THE 
 * RESPONSIBILITIES AND OBLIGATIONS CONTAINED IN THIS AGREEMENT.
 * 
 * Government Agency: The National Center for Telehealth and Technology
 * Government Agency Original Software Designation: Provider Resilience001
 * Government Agency Original Software Title: Provider Resilience
 * User Registration Requested. Please send email 
 * with your contact information to: robert.kayl2@us.army.mil
 * Government Agency Point of Contact for Original Software: robert.kayl2@us.army.mil
 * 
 */
package org.t2.pr.activities;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.SharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Opens a (non-dismissible) timed splash screen
 * 
 * @author Steve Ody (stephen.ody@tee2.org)
 */

public class SplashActivity extends FlurryActivity implements OnClickListener 
{
	private Timer timeoutTimer;

	private Handler startHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			stopTimer();
			startStartActivity();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.splash_activity);
		int splashDelay = 3000;
		if(Global.DebugOn)
			splashDelay = 500;

		timeoutTimer = new Timer();
		timeoutTimer.schedule(new TimerTask() 
		{
			@Override
			public void run() 
			{
				startHandler.sendEmptyMessage(0);
			}
		}, splashDelay);

	}

	private void startStartActivity() 
	{
		if (SharedPref.getIsEulaAccepted()) 
		{
			startMainActivity();
			this.finish();
		} 
		else
		{
			startEulaActivity();
		}
	}

	private void startEulaActivity() 
	{
		this.stopTimer();
		this.startActivity(ActivityFactory.getEULAActivity(this));
		this.finish();
	}

	private void startMainActivity() 
	{
		this.stopTimer();

		this.startActivity(ActivityFactory.getHomeActivity(this));

		int dayofyear = SharedPref.getPopupCardDay();
		int cdayofyear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		if(dayofyear != cdayofyear)
		{
			SharedPref.setPopupCardDay(cdayofyear);
			Intent intent = new Intent(this, CardsActivity.class);
			intent.putExtra("random", true);
			this.startActivity(intent);
		}
		else if(SharedPref.getWelcomeMessage())
		{
			Intent intent = ActivityFactory.getAboutActivity(this);
			intent.putExtra("shownav", false);
			this.startActivity(intent);
		}

		this.finish();
	} 

	@Override
	protected void onStop() 
	{
		super.onStop();
		this.stopTimer();
	}

	private void stopTimer() 
	{
		if (timeoutTimer != null) 
		{
			timeoutTimer.cancel();
			timeoutTimer = null;
		}
	}

	@Override
	public void onClick(View v) 
	{
		startStartActivity();
	}
}
