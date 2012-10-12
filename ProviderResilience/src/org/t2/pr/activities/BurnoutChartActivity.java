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

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.Scoring;

import zencharts.charts.DateChart;
import zencharts.charts.LineChart;
import zencharts.data.DatePoint;
import zencharts.data.DateSeries;
import zencharts.data.LinePoint;
import zencharts.data.LineSeries;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Charting activity that displays burnout data 
 * @author stephenody
 *
 */
public class BurnoutChartActivity extends ABSActivity implements OnClickListener
{

	private static DatabaseProvider db = new DatabaseProvider(Global.appContext);
	private Button btnUpdate;

	LinearLayout chartLayout;
	public DateChart dateChart;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.burnoutchart);

		dateChart = (DateChart)this.findViewById(R.id.datechart);
		
		
		
		dateChart.loadFont("Elronmonospace.ttf", 16, 2, 2);
		dateChart.maxValueManual = 100;
		getZenChartData();

		//chartLayout = (LinearLayout) findViewById(R.id.chart);
		try
		{
			this.SetMenuVisibility(1);
			this.btnMainTools.setChecked(true);
			btnUpdate = (Button)this.findViewById(R.id.btn_burnoutbutton);
			btnUpdate.setOnClickListener(this);
		}
		catch(Exception ex){}
	}

	private void getZenChartData()
	{

		ArrayList<String> qoldates = (ArrayList<String>) db.selectBURNOUTDates();

		if(qoldates.size() > 0)
		{
			//Create the burn series
			DateSeries boSeries = new DateSeries(Global.appContext, R.drawable.triangle);
			boSeries.dashEffect = new float[] {10,20};
			boSeries.lineColor = Color.RED;
			boSeries.lineWidth = 5;
			boSeries.title = "Burnout";
			boSeries.id = 1L;
			boSeries.visible = true;
			
			for(int burn = 0; burn < qoldates.size(); burn++)
			{
				String tdate = qoldates.get(burn);
				
				long date = new java.util.Date(tdate).getTime();
				
				double score = 100 - Scoring.RawBurnoutScore(tdate);
				//Log.v("Log", "date:"+date+" score:"+score);
				
				boSeries.add(new DatePoint(date, (int)score, ""));
			}

			dateChart.addSeries(boSeries);
			
			long sdate = new java.util.Date(qoldates.get(0)).getTime();
			long edate = new java.util.Date(qoldates.get(qoldates.size()-1)).getTime();
			
			DateTime startTime = new DateTime(sdate).withTime(0, 0, 0, 0);
			DateTime endTime = new DateTime(edate).withTime(0, 0, 0, 0).plusHours(24);
			dateChart.setPeriod(new Duration(startTime, endTime));
			dateChart.setPeriodStartTime(startTime);
		}

	}

	//

	@Override
	public void onPause()
	{
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onResume()
	 */
	protected void onResume() 
	{
		super.onResume();

	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onStart()
	 */
	@Override
	public void onStart()
	{

		super.onStart();
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
		case R.id.btn_burnoutbutton:
			this.startActivity(ActivityFactory.getUpdateBurnoutActivity(this));
			break;
		}
	}

}