/*
 * 
 * Provider Resilience
 * 
 * Copyright � 2009-2012 United States Government as represented by 
 * the Chief Information Officer of the National Center for Telehealth 
 * and Technology. All Rights Reserved.
 * 
 * Copyright � 2009-2012 Contributors. All Rights Reserved. 
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

import org.t2.pr.R;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.NotificationService;
import org.t2.pr.classes.SharedPref;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.ToggleButton;

/**
 * Allows changing application wide settings
 * @author stephenody
 *
 */
public class SettingsActivity extends ABSActivity
{
	private DatabaseProvider db = new DatabaseProvider(this);
	Button btnReset;
	Button btnSetReset;
	Button btnFeedback;
	Button btnSetTime;
	private ToggleButton toggle_welcome;
	private ToggleButton toggle_reminders;
	private ToggleButton toggle_anondata;
	static final int TIME_DIALOG_ID = 1;
	static final int RESET_DIALOG_ID = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		this.SetMenuVisibility(1);
		this.btnMainSettings.setChecked(true);

		btnReset = (Button)this.findViewById(R.id.toggle_reset);
		btnReset.setOnClickListener(this);

		btnFeedback = (Button)this.findViewById(R.id.toggle_feedback);
		btnFeedback.setOnClickListener(this);

		btnSetTime = (Button)this.findViewById(R.id.toggle_setrtime);
		btnSetTime.setOnClickListener(this);

		toggle_welcome = (ToggleButton)this.findViewById(R.id.toggle_welcome);
		toggle_welcome.setOnClickListener(this);
		toggle_welcome.setChecked(SharedPref.getWelcomeMessage());

		toggle_reminders = (ToggleButton)this.findViewById(R.id.toggle_reminders);
		toggle_reminders.setOnClickListener(this);
		toggle_reminders.setChecked(SharedPref.getReminders());

		toggle_anondata = (ToggleButton)this.findViewById(R.id.toggle_anondata);
		toggle_anondata.setOnClickListener(this);
		toggle_anondata.setChecked(SharedPref.getAnonData());

		btnSetReset = (Button)this.findViewById(R.id.toggle_setreset);
		btnSetReset.setOnClickListener(this);

	}

	/**
	 * Confirmation dialog before clearing user data
	 */
	public void AskClearData()
	{
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle("Accessibility");
		myAlertDialog.setMessage("Are you sure you want to delete all your saved data?");
		myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				ClearData();
			}});
		myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				// do nothing
			}});
		myAlertDialog.show();
	}

	/**
	 * Clears all user entered data (QOL/VAS)
	 */
	public void ClearData()
	{
		//Clear the data
		db.ClearData();

		//Clear Vacation clock
		SharedPref.setVacationDay(0);
		SharedPref.setVacationMonth(0);
		SharedPref.setVacationYear(0);

		//If debug, ask insert test data
		if(Global.DebugOn)
		{
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
			myAlertDialog.setTitle("Accessibility");
			myAlertDialog.setMessage("Do you want to enter some test data?");
			myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					EnterTestData();
				}});
			myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					// do nothing
				}});
			myAlertDialog.show();
		}

	}

	/**
	 * Adds some test data in the database for chart display
	 */
	public void EnterTestData()
	{
		db.EnterTestData();
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onStart()
	 */
	@Override
	public void onStart()
	{

		super.onStart();
	}

	/**
	 * Opens and pre-populates an email intent
	 */
	public void Feedback()
	{

		try
		{
			//Send action_send to OS
			Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
			shareIntent.setType("plain/text");

			//populate content for message body and email recipients
			shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Provider Resilience Feedback");
			shareIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"info@t2health.org"});
			shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Type feedback here, thank you!");

			startActivity(Intent.createChooser(shareIntent, "Send Feedback"));
		}
		catch(Exception ex)
		{}
	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
			new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			SharedPref.setNotifyHour(hourOfDay);
			SharedPref.setNotifyMinute(minute);
			SetReminder();
			
		}
	};
	private TimePickerDialog.OnTimeSetListener mResetTimeSetListener =
			new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			SharedPref.setResetHour(hourOfDay);
			SharedPref.setResetMinute(minute);
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this,
					mTimeSetListener,
					SharedPref.getNotifyHour(), SharedPref.getNotifyMinute(), false);
		case RESET_DIALOG_ID:
			return new TimePickerDialog(this,
					mResetTimeSetListener,
					SharedPref.getResetHour(), SharedPref.getResetMinute(), false);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{
		case R.id.toggle_reset:
			AskClearData();
			break;
		case R.id.toggle_feedback:
			Feedback();
			break;
		case R.id.toggle_welcome:			
			SharedPref.setWelcomeMessage(toggle_welcome.isChecked());
			break;
		case R.id.toggle_reminders:			
			SharedPref.setReminders(toggle_reminders.isChecked());
			SetReminder();
			break;
		case R.id.toggle_anondata:			
			SharedPref.setAnonData(toggle_anondata.isChecked());
			break;
		case R.id.toggle_setrtime:
			showDialog(TIME_DIALOG_ID);
			break;
		case R.id.toggle_setreset:
			showDialog(RESET_DIALOG_ID);
			break;
		}
	}
	
	public void SetReminder()
	{
		final AlarmManager mgr =
				(AlarmManager)SettingsActivity.this.getSystemService(Context.ALARM_SERVICE);
		final Intent intent = new Intent(SettingsActivity.this,NotificationService.class);
		final PendingIntent pend =
				PendingIntent.getService(SettingsActivity.this, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
		
		if(SharedPref.getReminders())
		{
			Calendar nc = Calendar.getInstance();
			nc.set(Calendar.HOUR_OF_DAY, SharedPref.getNotifyHour());
			nc.set(Calendar.MINUTE, SharedPref.getNotifyMinute());
			nc.set(Calendar.SECOND, 0);

			// Schedule an alarm
			mgr.setRepeating(AlarmManager.RTC_WAKEUP, nc.getTimeInMillis(), (1000*60*60*24*7), pend);
		}
		else
		{
			mgr.cancel(pend);
		}
	}

}
