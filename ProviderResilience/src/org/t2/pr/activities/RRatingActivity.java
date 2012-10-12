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

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RRatingActivity extends ABSActivity
{

	public ImageView btnProQOL;
	public TextView tvProQOL;

	public ImageView btnRRClock;
	public TextView tvRRClock;

	public ImageView btnBuildersKillers;
	public TextView tvBuildersKillers;

	public ImageView btnBurnout;
	public TextView tvBurnout;

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rrating);

		this.SetMenuVisibility(1);
		this.btnMainDashboard.setChecked(true);
		
		btnProQOL = (ImageView)this.findViewById(R.id.btn_proqol);
		btnProQOL.setOnClickListener(this);
		tvProQOL = (TextView)this.findViewById(R.id.txt_proqol);
		tvProQOL.setOnClickListener(this);

		btnRRClock = (ImageView)this.findViewById(R.id.btn_rrclock);
		btnRRClock.setOnClickListener(this);
		tvRRClock = (TextView)this.findViewById(R.id.txt_rrclock);
		tvRRClock.setOnClickListener(this);

		btnBuildersKillers = (ImageView)this.findViewById(R.id.btn_builderskillers);
		btnBuildersKillers.setOnClickListener(this);
		tvBuildersKillers = (TextView)this.findViewById(R.id.txt_builderskillers);
		tvBuildersKillers.setOnClickListener(this);

		btnBurnout = (ImageView)this.findViewById(R.id.btn_burnout);
		btnBurnout.setOnClickListener(this);
		tvBurnout = (TextView)this.findViewById(R.id.txt_burnout);
		tvBurnout.setOnClickListener(this);

	}

	@Override
	public void onStart()
	{

		super.onStart();
	}

	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{
		case R.id.btn_proqol:
			this.startActivity(ActivityFactory.getProQOLActivity(this));
			break;
		case R.id.txt_proqol:
			this.startActivity(ActivityFactory.getProQOLActivity(this));
			break;
		case R.id.btn_rrclock:
			this.startActivity(ActivityFactory.getRRClockActivity(this));
			break;
		case R.id.txt_rrclock:
			this.startActivity(ActivityFactory.getRRClockActivity(this));
			break;
		case R.id.btn_builderskillers:
			this.startActivity(ActivityFactory.getRBQuestionsActivity(this));
			break;
		case R.id.txt_builderskillers:
			this.startActivity(ActivityFactory.getRBQuestionsActivity(this));
			break;
		case R.id.txt_burnout:
			this.startActivity(ActivityFactory.getUpdateBurnoutActivity(this));
			break;
		case R.id.btn_burnout:
			this.startActivity(ActivityFactory.getUpdateBurnoutActivity(this));
			break;
		}
	}
	
}
