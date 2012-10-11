/*
 * 
 */
package org.t2.pr.activities;

import org.t2.pr.classes.Global;
import org.t2.pr.classes.SharedPref;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

// TODO: Auto-generated Javadoc
/**
 * Flurry class provides analytics and should be the base class for all activities.
 * 
 * @author Steve Ody (stephen.ody@tee2.org)
 */
public class FlurryActivity extends Activity {
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Global.sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onStartSession(this, Global.FLURRY_KEY);
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onEndSession(this);
		}
	}

	/**
	 * On event.
	 *
	 * @param event the event
	 */
	public void onEvent(String event) 
	{
		if(SharedPref.getSendAnnonData())
		{
			Global.Log.v("", "onEvent:"+event);

			FlurryAgent.logEvent(event);
		}
	}

	/**
	 * On error.
	 *
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 */
	public void onError(String arg0, String arg1, String arg2)
	{
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onError(arg0, arg1, arg2);
		}
	}

	/**
	 * On page view.
	 */
	public void onPageView() 
	{
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onPageView();
		}
	}
}
