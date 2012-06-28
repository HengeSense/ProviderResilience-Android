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
