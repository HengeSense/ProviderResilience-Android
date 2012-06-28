package org.t2.pr.activities;

import org.t2.pr.classes.Global;
import org.t2.pr.classes.SharedPref;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Flurry class provides analytics and should be the base class for all activities.
 * 
 * @author Steve Ody (stephen.ody@tee2.org)
 */
public class FlurryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Global.sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onStartSession(this, Global.FLURRY_KEY);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onEndSession(this);
		}
	}

	public void onEvent(String event) 
	{
		if(SharedPref.getSendAnnonData())
		{
			Global.Log.v("", "onEvent:"+event);

			FlurryAgent.logEvent(event);
		}
	}

	public void onError(String arg0, String arg1, String arg2)
	{
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onError(arg0, arg1, arg2);
		}
	}

	public void onPageView() 
	{
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onPageView();
		}
	}
}
