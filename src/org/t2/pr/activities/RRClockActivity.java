package org.t2.pr.activities;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.t2.pr.R;
import org.t2.pr.classes.Scoring;
import org.t2.pr.classes.SharedPref;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.ToggleButton;

public class RRClockActivity extends ABSActivity
{

	public TextView tv_Vacation;

	public TextView tvYearShadow;
	public TextView tvYearDigit;
	public TextView tvYearLabel;

	public TextView tvMonthShadow;
	public TextView tvMonthDigit;
	public TextView tvMonthLabel;

	public TextView tvDayShadow;
	public TextView tvDayDigit;
	public TextView tvDayLabel;

	public TextView tvHourShadow;
	public TextView tvHourDigit;
	public TextView tvHourLabel;

	public TextView tvMinuteShadow;
	public TextView tvMinuteDigit;
	public TextView tvMinuteLabel;

	Typeface tfDigitalNum;
	Typeface tfDigitalChr;

	private Button btnRRClock;
	private ToggleButton tbVacation;
	TextView tvToggleLabel;

	static final int DATE_DIALOG_ID = 0;
	private int cYear;
	private int cMonth;
	private int cDay;
	private int cHour;
	private int cMin;

	private int vYear;
	private int vMonth;
	private int vDay;

	//	private int dYear;
	//	private int dMonth;
	//	private int dDay;


	private Timer minTimer;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rrclock);

		this.SetMenuVisibility(1);
		this.btnMainDashboard.setChecked(true);

		btnRRClock = (Button)this.findViewById(R.id.btn_rrclock);
		btnRRClock.setOnClickListener(this);

		tfDigitalNum = Typeface.createFromAsset(getAssets(), "digitalkmono.ttf");
		tfDigitalChr = Typeface.createFromAsset(getAssets(), "digiitalic.ttf");

		tv_Vacation = (TextView)this.findViewById(R.id.tv_Vacation);
		//tv_Vacation.setTypeface(tfDigitalNum);

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

		tbVacation = (ToggleButton) this.findViewById(R.id.toggle_vacation);
		tbVacation.setOnClickListener(this);
		tbVacation.setChecked(SharedPref.getOnVacation());

		tvToggleLabel = (TextView) this.findViewById(R.id.tv_toggle);

		// get the current date
		final Calendar c = Calendar.getInstance();
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


		minTimer = new Timer();
		minTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerTick();
			}

		}, 0, 5000);

	}

	private void TimerTick()
	{
		runOnUiThread(new Runnable(){

			@Override
			public void run() {
				updateDisplay();

			}});
	}

	private void updateDisplay()
	{
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
		}

		//Set color based on leave score
		if(Scoring.LeaveClockScore() == 20)
		{
			tvYearDigit.setTextColor(Color.argb(255, 18, 145, 18));
			tvMonthDigit.setTextColor(Color.argb(255, 18, 145, 18));
			tvDayDigit.setTextColor(Color.argb(255, 18, 145, 18));
			tvHourDigit.setTextColor(Color.argb(255, 18, 145, 18));
			tvMinuteDigit.setTextColor(Color.argb(255, 18, 145, 18));
		}
		else if(Scoring.LeaveClockScore() == 10)
		{
			tvYearDigit.setTextColor(Color.argb(255, 67, 97, 212));
			tvMonthDigit.setTextColor(Color.argb(255, 67, 97, 212));
			tvDayDigit.setTextColor(Color.argb(255, 67, 97, 212));
			tvHourDigit.setTextColor(Color.argb(255, 67, 97, 212));
			tvMinuteDigit.setTextColor(Color.argb(255, 67, 97, 212));
		}
		else
		{
			tvYearDigit.setTextColor(Color.argb(255, 227, 20, 29));
			tvMonthDigit.setTextColor(Color.argb(255, 227, 20, 29));
			tvDayDigit.setTextColor(Color.argb(255, 227, 20, 29));
			tvHourDigit.setTextColor(Color.argb(255, 227, 20, 29));
			tvMinuteDigit.setTextColor(Color.argb(255, 227, 20, 29));
		}

	}

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

	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{
		case R.id.btn_rrclock:
			//this.startActivity(ActivityFactory.getRRClockActivity(this));
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.toggle_vacation:
			boolean isonVacation = tbVacation.isChecked();
			SharedPref.setOnVacation(isonVacation);
			if(!isonVacation)
			{
				//Update the clock date
				SharedPref.setVacationYear(cYear);
				SharedPref.setVacationMonth(cMonth);
				SharedPref.setVacationDay(cDay);
				// get the saved date
				vYear = SharedPref.getVacationYear();
				vMonth = SharedPref.getVacationMonth();
				vDay = SharedPref.getVacationDay();

			}
			updateDisplay();
			break;

		}
	}

}
