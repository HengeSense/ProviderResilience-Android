/*
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

// TODO: Auto-generated Javadoc
/**
 * The Class ToolsActivity.
 */
public class ToolsActivity extends ABSActivity implements OnClickListener
{

	/** The btn videos. */
	private ImageView btnVideos;
	
	/** The tv videos. */
	private TextView tvVideos;

	/** The btn exercise. */
	private ImageView btnExercise;
	
	/** The tv exercise. */
	private TextView tvExercise;

	/** The btn remind me. */
	private ImageView btnRemindMe;
	
	/** The tv remind me. */
	private TextView tvRemindMe;

	/** The btn laugh. */
	private ImageView btnLaugh;
	
	/** The tv laugh. */
	private TextView tvLaugh;

	/** The btn builders. */
	private ImageView btnBuilders;
	
	/** The tv builders. */
	private TextView tvBuilders;

	/** The btn pro qol. */
	private ImageView btnProQOL;
	
	/** The tv pro qol. */
	private TextView tvProQOL;

	/** The btn burnout. */
	private ImageView btnBurnout;
	
	/** The tv burnout. */
	private TextView tvBurnout;

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
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
