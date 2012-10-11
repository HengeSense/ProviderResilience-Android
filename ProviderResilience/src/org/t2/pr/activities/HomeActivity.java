/*
 * 
 */
package org.t2.pr.activities;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.Scoring;
import org.t2.pr.classes.SharedPref;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * Create screen provides navigation to all activity choosing functionality.
 *
 * @author stephenody
 */
public class HomeActivity extends ABSActivity implements OnClickListener
{
	
	/** The pending intent. */
	private PendingIntent pendingIntent;

	/** The m handler. */
	private Handler mHandler = new Handler();

	/** The tv_ vacation. */
	public TextView tv_Vacation;

	/** The tv_rratingvalue. */
	public TextView tv_rratingvalue;
	
	/** The tv_rratinglabel. */
	public TextView tv_rratinglabel;

	/** The tv_qolcsvalue. */
	public TextView tv_qolcsvalue;
	
	/** The tv_qolbvalue. */
	public TextView tv_qolbvalue;
	
	/** The tv_qolstsvalue. */
	public TextView tv_qolstsvalue;

	/** The iv_qolcsvalue. */
	public ImageView iv_qolcsvalue;
	
	/** The iv_qolbvalue. */
	public ImageView iv_qolbvalue;
	
	/** The iv_qolstsvalue. */
	public ImageView iv_qolstsvalue;

	/** The tv year shadow. */
	public TextView tvYearShadow;
	
	/** The tv year digit. */
	public TextView tvYearDigit;
	
	/** The tv year label. */
	public TextView tvYearLabel;

	/** The tv month shadow. */
	public TextView tvMonthShadow;
	
	/** The tv month digit. */
	public TextView tvMonthDigit;
	
	/** The tv month label. */
	public TextView tvMonthLabel;

	/** The tv day shadow. */
	public TextView tvDayShadow;
	
	/** The tv day digit. */
	public TextView tvDayDigit;
	
	/** The tv day label. */
	public TextView tvDayLabel;

	/** The tv hour shadow. */
	public TextView tvHourShadow;
	
	/** The tv hour digit. */
	public TextView tvHourDigit;
	
	/** The tv hour label. */
	public TextView tvHourLabel;

	/** The tv minute shadow. */
	public TextView tvMinuteShadow;
	
	/** The tv minute digit. */
	public TextView tvMinuteDigit;
	
	/** The tv minute label. */
	public TextView tvMinuteLabel;

	/** The tf digital num. */
	Typeface tfDigitalNum;
	
	/** The tf digital chr. */
	Typeface tfDigitalChr;

	/** The btn rr clock. */
	private ImageButton btnRRClock;
	
	/** The btn r rating. */
	private ImageButton btnRRating;
	
	/** The btn pro qol. */
	private ImageButton btnProQOL;

	/** The Constant DATE_DIALOG_ID. */
	static final int DATE_DIALOG_ID = 0;
	
	/** The c year. */
	private int cYear;
	
	/** The c month. */
	private int cMonth;
	
	/** The c day. */
	private int cDay;
	
	/** The c hour. */
	private int cHour;
	
	/** The c min. */
	private int cMin;

	/** The v year. */
	private int vYear;
	
	/** The v month. */
	private int vMonth;
	
	/** The v day. */
	private int vDay;

	//	private int dYear;
	//	private int dMonth;
	//	private int dDay;

	/** The s date. */
	private String sDate = "";

	/** The min timer. */
	private Timer minTimer;

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		this.SetMenuVisibility(1);
		this.btnMainDashboard.setChecked(true);
        
		Global.appContext = this.getApplicationContext();
		sDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());

		tv_Vacation = (TextView)this.findViewById(R.id.tv_Vacation);

		tfDigitalNum = Typeface.createFromAsset(getAssets(), "digitalkmono.ttf");
		tfDigitalChr = Typeface.createFromAsset(getAssets(), "digiitalic.ttf");

		tvYearShadow = (TextView)this.findViewById(R.id.tv_year_shadow);
		tvYearDigit = (TextView)this.findViewById(R.id.tv_year_digit);
		tvYearLabel = (TextView)this.findViewById(R.id.tv_year_label);
		tvYearShadow.setTypeface(tfDigitalNum);
		tvYearDigit.setTypeface(tfDigitalNum);
		tvYearLabel.setTypeface(tfDigitalChr);

		tvMonthShadow = (TextView)this.findViewById(R.id.tv_month_shadow);
		tvMonthDigit = (TextView)this.findViewById(R.id.tv_month_digit);
		tvMonthLabel = (TextView)this.findViewById(R.id.tv_month_label);
		tvMonthShadow.setTypeface(tfDigitalNum);
		tvMonthDigit.setTypeface(tfDigitalNum);
		tvMonthLabel.setTypeface(tfDigitalChr);

		tvDayShadow = (TextView)this.findViewById(R.id.tv_day_shadow);
		tvDayDigit = (TextView)this.findViewById(R.id.tv_day_digit);
		tvDayLabel = (TextView)this.findViewById(R.id.tv_day_label);
		tvDayShadow.setTypeface(tfDigitalNum);
		tvDayDigit.setTypeface(tfDigitalNum);
		tvDayLabel.setTypeface(tfDigitalChr);

		tvHourShadow = (TextView)this.findViewById(R.id.tv_hour_shadow);
		tvHourDigit = (TextView)this.findViewById(R.id.tv_hour_digit);
		tvHourLabel = (TextView)this.findViewById(R.id.tv_hour_label);
		tvHourShadow.setTypeface(tfDigitalNum);
		tvHourDigit.setTypeface(tfDigitalNum);
		tvHourLabel.setTypeface(tfDigitalChr);

		tvMinuteShadow = (TextView)this.findViewById(R.id.tv_minute_shadow);
		tvMinuteDigit = (TextView)this.findViewById(R.id.tv_minute_digit);
		tvMinuteLabel = (TextView)this.findViewById(R.id.tv_minute_label);
		tvMinuteShadow.setTypeface(tfDigitalNum);
		tvMinuteDigit.setTypeface(tfDigitalNum);
		tvMinuteLabel.setTypeface(tfDigitalChr);

		btnRRClock = (ImageButton)this.findViewById(R.id.btn_rrclock);
		btnRRClock.setOnClickListener(this);
		btnRRating = (ImageButton)this.findViewById(R.id.btn_rrating);
		btnRRating.setOnClickListener(this);
		btnProQOL = (ImageButton)this.findViewById(R.id.btn_qolupdate);
		btnProQOL.setOnClickListener(this);

		// get the current date
		Calendar c = Calendar.getInstance();
		cYear = c.get(Calendar.YEAR);
		cMonth = c.get(Calendar.MONTH);
		cDay = c.get(Calendar.DAY_OF_MONTH);
		cHour = c.get(Calendar.HOUR_OF_DAY);
		cMin = c.get(Calendar.MINUTE);

		if(SharedPref.getVacationYear() == 0)
		{
			// get the current date
			vYear = cYear;
			vMonth = cMonth;
			vDay = cDay;
		}
		else
		{
			// get the saved date
			vYear = SharedPref.getVacationYear();
			vMonth = SharedPref.getVacationMonth();
			vDay = SharedPref.getVacationDay();
		}

		tv_rratingvalue = (TextView)this.findViewById(R.id.tv_rratingvalue);
		tv_rratinglabel = (TextView)this.findViewById(R.id.tv_rratinglabel);

		tv_qolcsvalue = (TextView)this.findViewById(R.id.tv_qolcsvalue);
		tv_qolbvalue = (TextView)this.findViewById(R.id.tv_qolbvalue);
		tv_qolstsvalue = (TextView)this.findViewById(R.id.tv_qolstsvalue);

		iv_qolcsvalue = (ImageView)this.findViewById(R.id.iv_qolcsvalue);
		iv_qolbvalue = (ImageView)this.findViewById(R.id.iv_qolbvalue);
		iv_qolstsvalue = (ImageView)this.findViewById(R.id.iv_qolstsvalue);

		updateDisplay();

		//ShowWelcome();

		//Show daily card (once a day)
		/*int dayofyear = SharedPref.getPopupCardDay();
		int cdayofyear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		if(dayofyear != cdayofyear)
		{
			SharedPref.setPopupCardDay(cdayofyear);
			mHandler.postDelayed(startCardsRunnable, 500);
		}*/



	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.FlurryActivity#onStop()
	 */
	@Override
    protected void onStop() {
    	
    	super.onStop();
    }

	/**
	 * A timer to handle the display of the vacation clock updates.
	 */
	private void TimerTick()
	{
		runOnUiThread(new Runnable(){

			@Override
			public void run() {
				updateDisplay();
			}});
	}

	/**
	 * Shows a welcome message on start of application, provides a checkbox to disable.
	 */
	public void ShowWelcome()
	{
		if(!Global.seenWelcome)
		{
			if(SharedPref.getWelcomeMessage())
			{
				//Build welcome HTML
				String results = "<HTML>";
				results += "<head>";

				results += "<BODY>";
				results += "<P>This app has been desinged for healthcare professionals who treat service members, veterans and their families. <P>It's purpose is to provide you with a set of tools that support resilience and fight burnout and compassion fatigue. Healthcare professionals know that they need to take care of themselves and they've told us they tend to know what do do to fight stress - that they just don't take the time to do it.";
				results += "<P>That's where this app comes in. We've developed the things providers have asked for: tools to help you keep track of how you're doing, reminders to do the things you already know you should be doing, and some interesting things to do that support resilience. <P>We even remind you on a daily basis to use the app. It only takes a few minutes a day to take care of yourself.";

				results += "";

				results += "</BODY>";
				results += "</HTML>";

				final Dialog dialog = new Dialog(this);
				dialog.setContentView(R.layout.popup);
				dialog.setTitle("Welcome:");
				dialog.setCancelable(true);

				TextView text = (TextView) dialog.findViewById(R.id.dialogbody);
				text.setText(Html.fromHtml(results));
				text.setTextSize(14f);
				text.setMovementMethod(new ScrollingMovementMethod());
				final CheckBox chk = (CheckBox) dialog.findViewById(R.id.dontShowAgain);
				Button button = (Button) dialog.findViewById(R.id.btnOK);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(chk.isChecked())
							SharedPref.setWelcomeMessage(false);
						Global.seenWelcome = true;
						dialog.cancel();
					}
				});
				dialog.show();
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override 
	public void onPause()
	{
		super.onPause();
		minTimer.cancel();
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onResume()
	 */
	@Override
	public void onResume()
	{
		super.onResume();

		minTimer = new Timer();
		minTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerTick();
			}

		}, 0, 5000);
		
	}

	/**
	 * Updates the scoring display and the vacation clock display.
	 */
	private void updateDisplay()
	{
		sDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
		
		if(SharedPref.getOnVacation())
		{
			tv_Vacation.setVisibility(View.VISIBLE);
			tvYearDigit.setVisibility(View.GONE);
			tvMonthDigit.setVisibility(View.GONE);
			tvDayDigit.setVisibility(View.GONE);
			tvHourDigit.setVisibility(View.GONE);
			tvMinuteDigit.setVisibility(View.GONE);

			tvYearShadow.setVisibility(View.GONE);
			tvMonthShadow.setVisibility(View.GONE);
			tvDayShadow.setVisibility(View.GONE);
			tvHourShadow.setVisibility(View.GONE);
			tvMinuteShadow.setVisibility(View.GONE);

			tvYearLabel.setVisibility(View.GONE);
			tvMonthLabel.setVisibility(View.GONE);
			tvDayLabel.setVisibility(View.GONE);
			tvHourLabel.setVisibility(View.GONE);
			tvMinuteLabel.setVisibility(View.GONE);

			tvYearDigit.setTextColor(Color.argb(255, 18, 145, 18));
			tvMonthDigit.setTextColor(Color.argb(255, 18, 145, 18));
			tvDayDigit.setTextColor(Color.argb(255, 18, 145, 18));
			tvHourDigit.setTextColor(Color.argb(255, 18, 145, 18));
			tvMinuteDigit.setTextColor(Color.argb(255, 18, 145, 18));
		}
		else
		{
			tv_Vacation.setVisibility(View.GONE);
			tvYearDigit.setVisibility(View.VISIBLE);
			tvMonthDigit.setVisibility(View.VISIBLE);
			tvDayDigit.setVisibility(View.VISIBLE);
			tvHourDigit.setVisibility(View.VISIBLE);
			tvMinuteDigit.setVisibility(View.VISIBLE);

			tvYearShadow.setVisibility(View.VISIBLE);
			tvMonthShadow.setVisibility(View.VISIBLE);
			tvDayShadow.setVisibility(View.VISIBLE);
			tvHourShadow.setVisibility(View.VISIBLE);
			tvMinuteShadow.setVisibility(View.VISIBLE);

			tvYearLabel.setVisibility(View.VISIBLE);
			tvMonthLabel.setVisibility(View.VISIBLE);
			tvDayLabel.setVisibility(View.VISIBLE);
			tvHourLabel.setVisibility(View.VISIBLE);
			tvMinuteLabel.setVisibility(View.VISIBLE);

			if(SharedPref.getVacationYear() == 0)
			{
				// get the current date
				vYear = cYear;
				vMonth = cMonth;
				vDay = cDay;
			}
			else
			{
				// get the saved date
				vYear = SharedPref.getVacationYear();
				vMonth = SharedPref.getVacationMonth();
				vDay = SharedPref.getVacationDay();
			}

			DateTime v = new DateTime(vYear, vMonth + 1, vDay, 0, 0);
			DateTime c = new DateTime(cYear, cMonth + 1, cDay, 0, 0);
			Period p = new Period(v, c, PeriodType.yearMonthDay());

			if(p.getYears() < 0)
				tvYearDigit.setText("00");
			else if(p.getYears() < 10)
				tvYearDigit.setText("0" + p.getYears());
			else
				tvYearDigit.setText("" + p.getYears());

			if(p.getMonths() < 0)
				tvMonthDigit.setText("00");
			else if(p.getMonths() < 10)
				tvMonthDigit.setText("0" + p.getMonths());
			else
				tvMonthDigit.setText("" + p.getMonths());

			if(p.getDays() < 0)
				tvDayDigit.setText("00");
			else if(p.getDays() < 10)
				tvDayDigit.setText("0" + p.getDays());
			else
				tvDayDigit.setText("" + p.getDays());

			if(cHour < 0)
				tvHourDigit.setText("00");
			else if(cHour < 10)
				tvHourDigit.setText("0" + cHour);
			else
				tvHourDigit.setText("" + cHour);

			if(cMin < 0)
				tvMinuteDigit.setText("00");
			else if(cMin < 10)
				tvMinuteDigit.setText("0" + cMin);
			else
				tvMinuteDigit.setText("" + cMin);

			int totalResScore = Scoring.TotalResilienceScore(sDate);
			tv_rratingvalue.setText("" + totalResScore);
			tv_rratinglabel.setText("" + Scoring.TotalResilienceString(totalResScore));
			if(Scoring.TotalResilienceString(totalResScore) == "HIGH")
				tv_rratinglabel.setBackgroundResource(R.drawable.gaugehoriz_green);
			else if(Scoring.TotalResilienceString(totalResScore) == "LOW")
				tv_rratinglabel.setBackgroundResource(R.drawable.gaugehoriz_red);
			else
				tv_rratinglabel.setBackgroundResource(R.drawable.gaugehoriz_blue);

			String lastQOL = Scoring.getLastQOLDate();
			
			tv_qolcsvalue.setText(Scoring.AcuityString(Scoring.QOLCompassionScore(lastQOL)) + "\r\n" + (Scoring.QOLCompassionScore(lastQOL) ));
			tv_qolbvalue.setText(Scoring.AcuityString(Scoring.QOLBurnoutScore(lastQOL)) + "\r\n" + (Scoring.QOLBurnoutScore(lastQOL)));
			tv_qolstsvalue.setText(Scoring.AcuityString(Scoring.QOLSTSScore(lastQOL)) + "\r\n" + (Scoring.QOLSTSScore(lastQOL) ));

			if(Scoring.AcuityString(Scoring.QOLCompassionScore(lastQOL)) == "LOW")
				iv_qolcsvalue.setImageResource(R.drawable.gaugevert_red);
			else if(Scoring.AcuityString(Scoring.QOLCompassionScore(lastQOL)) == "HIGH")
				iv_qolcsvalue.setImageResource(R.drawable.gaugevert_green);
			else
				iv_qolcsvalue.setImageResource(R.drawable.gaugevert_blue);

			if(Scoring.AcuityString(Scoring.QOLBurnoutScore(lastQOL)) == "HIGH")
				iv_qolbvalue.setImageResource(R.drawable.gaugevert_red);
			else if(Scoring.AcuityString(Scoring.QOLBurnoutScore(lastQOL)) == "LOW")
				iv_qolbvalue.setImageResource(R.drawable.gaugevert_green);
			else
				iv_qolbvalue.setImageResource(R.drawable.gaugevert_blue);

			if(Scoring.AcuityString(Scoring.QOLSTSScore(lastQOL)) == "HIGH")
				iv_qolstsvalue.setImageResource(R.drawable.gaugevert_red);
			else if(Scoring.AcuityString(Scoring.QOLSTSScore(lastQOL)) == "LOW")
				iv_qolstsvalue.setImageResource(R.drawable.gaugevert_green);
			else
				iv_qolstsvalue.setImageResource(R.drawable.gaugevert_blue);

			//Set color based on leave score
			if(Scoring.LeaveClockScore() == 20)
			{
				tvYearDigit.setTextColor(Color.GREEN);
				tvMonthDigit.setTextColor(Color.GREEN);
				tvDayDigit.setTextColor(Color.GREEN);
				tvHourDigit.setTextColor(Color.GREEN);
				tvMinuteDigit.setTextColor(Color.GREEN);
			}
			else if(Scoring.LeaveClockScore() == 15)
			{
				tvYearDigit.setTextColor(Color.YELLOW);
				tvMonthDigit.setTextColor(Color.YELLOW);
				tvDayDigit.setTextColor(Color.YELLOW);
				tvHourDigit.setTextColor(Color.YELLOW);
				tvMinuteDigit.setTextColor(Color.YELLOW);
			}
			else if(Scoring.LeaveClockScore() == 10)
			{
				tvYearDigit.setTextColor(Color.YELLOW);
				tvMonthDigit.setTextColor(Color.YELLOW);
				tvDayDigit.setTextColor(Color.YELLOW);
				tvHourDigit.setTextColor(Color.YELLOW);
				tvMinuteDigit.setTextColor(Color.YELLOW);
			}
			else if(Scoring.LeaveClockScore() == 5)
			{
				tvYearDigit.setTextColor(Color.YELLOW);
				tvMonthDigit.setTextColor(Color.YELLOW);
				tvDayDigit.setTextColor(Color.YELLOW);
				tvHourDigit.setTextColor(Color.YELLOW);
				tvMinuteDigit.setTextColor(Color.YELLOW);
			}
			else
			{
				tvYearDigit.setTextColor(Color.RED);
				tvMonthDigit.setTextColor(Color.RED);
				tvDayDigit.setTextColor(Color.RED);
				tvHourDigit.setTextColor(Color.RED);
				tvMinuteDigit.setTextColor(Color.RED);
			}
		}

	}

	/** The m date set listener. */
	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, 
				int monthOfYear, int dayOfMonth) {
			vYear = year;
			vMonth = monthOfYear;
			vDay = dayOfMonth;
			SharedPref.setVacationYear(vYear);
			SharedPref.setVacationMonth(vMonth);
			SharedPref.setVacationDay(vDay);
			updateDisplay();
		}
	};

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,
					mDateSetListener,
					vYear, vMonth, vDay);
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
		case R.id.btn_rrclock:
			this.startActivity(ActivityFactory.getRRClockActivity(this));
			//showDialog(DATE_DIALOG_ID);
			break;
		case R.id.btn_rrating:
			this.startActivity(ActivityFactory.getRRatingActivity(this));
			break;
		case R.id.btn_qolupdate:
			this.startActivity(ActivityFactory.getProQOLActivity(this));
			break;
		}
	}

	/** The start cards runnable. */
	private Runnable startCardsRunnable = new Runnable() {
		public void run() {
			startCardsActivity();
		}
	};

	/**
	 * Start cards activity.
	 */
	private void startCardsActivity() 
	{
		Intent intent = new Intent(this, CardsActivity.class);
		intent.putExtra("random", true);
		this.startActivity(intent);
	}

}
