/*
 * 
 */
package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class RRatingActivity.
 */
public class RRatingActivity extends ABSActivity
{

	/** The btn pro qol. */
	public ImageView btnProQOL;
	
	/** The tv pro qol. */
	public TextView tvProQOL;

	/** The btn rr clock. */
	public ImageView btnRRClock;
	
	/** The tv rr clock. */
	public TextView tvRRClock;

	/** The btn builders killers. */
	public ImageView btnBuildersKillers;
	
	/** The tv builders killers. */
	public TextView tvBuildersKillers;

	/** The btn burnout. */
	public ImageView btnBurnout;
	
	/** The tv burnout. */
	public TextView tvBurnout;

	
	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
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
