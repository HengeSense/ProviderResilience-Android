/*
 * 
 */
package org.t2.pr.activities;

import java.util.Calendar;

import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.SharedPref;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

// TODO: Auto-generated Javadoc
/**
 * The Class StartupActivity.
 */
public class StartupActivity extends Activity {
	/** Called when the activity is first created. */

	Button btnStart;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		this.startActivity(ActivityFactory.getSplashActivity(this));
		this.finish();
	}
}