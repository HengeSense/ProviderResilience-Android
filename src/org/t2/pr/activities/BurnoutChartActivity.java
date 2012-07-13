package org.t2.pr.activities;

import java.util.ArrayList;
import java.util.Date;
import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.Scoring;

import zencharts.charts.LineChart;
import zencharts.data.LinePoint;
import zencharts.data.LineSeries;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
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
	public LineChart lineChart;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.burnoutchart);

		lineChart = (LineChart)this.findViewById(R.id.linechart);
		lineChart.gridLines = true;
		lineChart.loadFont("Elronmonospace.ttf", 16, 2, 2);

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
			LineSeries boSeries = new LineSeries(Global.appContext, 0);
			boSeries.dashEffect = new float[] {10,20};
			boSeries.lineColor = Color.RED;
			boSeries.lineWidth = 5;

			for(int burn = 0; burn < qoldates.size(); burn++)
			{
				String tdate = qoldates.get(burn);
				Date date = new java.util.Date(tdate);
				double score = Scoring.BurnoutScore(qoldates.get(burn));
				boSeries.add(new LinePoint((int)score, ""+(int)score, DateFormat.format("MM/dd/yy", date).toString()));
			}

			lineChart.addSeries(boSeries);

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