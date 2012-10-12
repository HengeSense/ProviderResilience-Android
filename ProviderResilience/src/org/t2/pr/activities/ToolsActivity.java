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
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ToolsActivity extends ABSActivity implements OnClickListener
{

	private ImageView btnVideos;
	private TextView tvVideos;

	private ImageView btnExercise;
	private TextView tvExercise;

	private ImageView btnRemindMe;
	private TextView tvRemindMe;

	private ImageView btnLaugh;
	private TextView tvLaugh;

	private ImageView btnBuilders;
	private TextView tvBuilders;

	private ImageView btnProQOL;
	private TextView tvProQOL;

	private ImageView btnBurnout;
	private TextView tvBurnout;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools);

		this.SetMenuVisibility(1);
		this.btnMainTools.setChecked(true);

		btnVideos = (ImageView)this.findViewById(R.id.btn_videos);
		btnVideos.setOnClickListener(this);

		tvVideos = (TextView)this.findViewById(R.id.txt_videos);
		tvVideos.setOnClickListener(this);

		btnExercise = (ImageView)this.findViewById(R.id.btn_exercise);
		btnExercise.setOnClickListener(this);

		tvExercise = (TextView)this.findViewById(R.id.txt_exercise);
		tvExercise.setOnClickListener(this);

		btnRemindMe = (ImageView)this.findViewById(R.id.btn_remindme);
		btnRemindMe.setOnClickListener(this);

		tvRemindMe = (TextView)this.findViewById(R.id.txt_remindme);
		tvRemindMe.setOnClickListener(this);

		btnLaugh = (ImageView)this.findViewById(R.id.btn_laugh);
		btnLaugh.setOnClickListener(this);

		tvLaugh = (TextView)this.findViewById(R.id.txt_laugh);
		tvLaugh.setOnClickListener(this);

		btnBuilders = (ImageView)this.findViewById(R.id.btn_builderskillers);
		btnBuilders.setOnClickListener(this);

		tvBuilders = (TextView)this.findViewById(R.id.txt_builderskillers);
		tvBuilders.setOnClickListener(this);

		btnProQOL = (ImageView)this.findViewById(R.id.btn_proqol);
		btnProQOL.setOnClickListener(this);

		tvProQOL = (TextView)this.findViewById(R.id.txt_proqol);
		tvProQOL.setOnClickListener(this);

		tvBurnout = (TextView)this.findViewById(R.id.txt_burnout);
		tvBurnout.setOnClickListener(this);
		btnBurnout = (ImageView)this.findViewById(R.id.btn_burnout);
		btnBurnout.setOnClickListener(this);
		

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
		case R.id.btn_videos:
			this.startActivity(ActivityFactory.getVideosActivity(this));
			break;
		case R.id.txt_videos:
			this.startActivity(ActivityFactory.getVideosActivity(this));
			break;

		case R.id.btn_exercise:
			this.startActivity(ActivityFactory.getStretchesActivity(this));
			break;
		case R.id.txt_exercise:
			this.startActivity(ActivityFactory.getStretchesActivity(this));
			break;

		case R.id.btn_remindme:
			this.startActivity(ActivityFactory.getRemindMeActivity(this));
			break;
		case R.id.txt_remindme:
			this.startActivity(ActivityFactory.getRemindMeActivity(this));
			break;

		case R.id.btn_laugh:
			this.startActivity(ActivityFactory.getLaughActivity(this));
			break;
		case R.id.txt_laugh:
			this.startActivity(ActivityFactory.getLaughActivity(this));
			break;

		case R.id.btn_builderskillers:
			this.startActivity(ActivityFactory.getHelperCardActivity(this));
			break;
		case R.id.txt_builderskillers:
			this.startActivity(ActivityFactory.getHelperCardActivity(this));
			break;

		case R.id.btn_proqol:
			this.startActivity(ActivityFactory.getPROQOLChartActivity(this));
			break;
		case R.id.txt_proqol:
			this.startActivity(ActivityFactory.getPROQOLChartActivity(this));
			break;

		case R.id.btn_burnout:
			this.startActivity(ActivityFactory.getBurnoutChartActivity(this));
			break;
		case R.id.txt_burnout:
			this.startActivity(ActivityFactory.getBurnoutChartActivity(this));
			break;

		}
	}

}
