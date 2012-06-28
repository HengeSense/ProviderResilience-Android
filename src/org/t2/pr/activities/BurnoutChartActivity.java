package org.t2.pr.activities;

import java.util.ArrayList;
import java.util.Date;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.Scoring;

import coolcharts.charts.DateChart;
import coolcharts.data.DatePoint;
import coolcharts.data.DateSeries;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Charting activity that displays burnout data using the open-source achartengine
 * @author stephenody
 *
 */
public class BurnoutChartActivity extends ABSActivity implements OnClickListener
{

	private static DatabaseProvider db = new DatabaseProvider(Global.appContext);
	private Button btnUpdate;
	GraphicalView mChartView;
	XYMultipleSeriesDataset dataset;
	XYMultipleSeriesRenderer renderer;
	LinearLayout chartLayout;
	public DateChart dateChart;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.burnoutchart);

		this.SetMenuVisibility(1);
		this.btnMainTools.setChecked(true);

		dateChart = (DateChart)this.findViewById(R.id.datechart);
		dateChart.showGrid = true;
		dateChart.scrollGrid = false;
		dateChart.showStars = false;
		dateChart.transparentBackground = true;
		getCoolChartData();
		
		//chartLayout = (LinearLayout) findViewById(R.id.chart);
		btnUpdate = (Button)this.findViewById(R.id.btn_burnoutbutton);
		btnUpdate.setOnClickListener(this);

	}

	private void getCoolChartData()
	{

		ArrayList<String> qoldates = (ArrayList<String>) db.selectBURNOUTDates();

		if(qoldates.size() > 0)
		{
			//Create the burn series
			DateSeries boSeries = new DateSeries();
			boSeries.dashEffect = new float[] {10,20};
			boSeries.lineColor = Color.RED;
			boSeries.lineWidth = 5;
			
			for(int burn = 0; burn < qoldates.size(); burn++)
			{
				String tdate = qoldates.get(burn);
				Date date = new java.util.Date(tdate);
				double score = Scoring.BurnoutScore(qoldates.get(burn));
				boSeries.add(new DatePoint(date.getTime(), (int)score, ""+(int)score));
			}
			
			dateChart.AddSeries(boSeries);
			
			/*TimeSeries burnData = new TimeSeries ("Burnout"); 
			for(int burn = 0; burn < qoldates.size(); burn++)
			{
				String tdate = qoldates.get(burn);
				Date date = new java.util.Date(tdate);
				double score = Scoring.BurnoutScore(qoldates.get(burn));
				burnData.add(date, score);
			}

			dataset.addSeries (burnData); 

			XYSeriesRenderer burnrenderer = new XYSeriesRenderer (); 
			burnrenderer.setColor (Color.argb(255, 18, 145, 18)); 
			burnrenderer.setLineWidth(5f);
			burnrenderer.setStroke(BasicStroke.DASHED);
			burnrenderer.setPointStyle(PointStyle.SQUARE);
			renderer.addSeriesRenderer (burnrenderer); 
			renderer.setChartTitle ("Burnout"); 
			renderer.setXTitle ("Recorded Dates"); 
			renderer.setYTitle ("Burnout Score"); */
		}
		//else maybe show something else when there is no data
		
	}
	
	/**
	 * Pulls chart data from database and sets up achartengine renderer
	 */
	private void getChartData()
	{
		//  Setup the chart 
		dataset = new XYMultipleSeriesDataset (); 
		renderer = new XYMultipleSeriesRenderer (); 
		renderer.setShowAxes (true); 
		renderer.setShowGrid(true);
		renderer.setAxesColor (0x000000); 
		renderer.setAntialiasing (true); 
		renderer.setBackgroundColor (0xDDDDDD); 
		renderer.setApplyBackgroundColor (true); 
		renderer.setShowLabels (true); 
		renderer.setPanLimits(new double[] {0,0,0,40});
		renderer.setLabelsTextSize(16f);
		renderer.setAxisTitleTextSize(16f);
		renderer.setChartTitleTextSize(16f);
		renderer.setLegendTextSize(20f);

		ArrayList<String> qoldates = (ArrayList<String>) db.selectBURNOUTDates();

		if(qoldates.size() > 0)
		{
			//Create the burn series 
			TimeSeries burnData = new TimeSeries ("Burnout"); 
			for(int burn = 0; burn < qoldates.size(); burn++)
			{
				String tdate = qoldates.get(burn);
				Date date = new java.util.Date(tdate);
				double score = Scoring.BurnoutScore(qoldates.get(burn));
				burnData.add(date, score);
			}

			dataset.addSeries (burnData); 

			XYSeriesRenderer burnrenderer = new XYSeriesRenderer (); 
			burnrenderer.setColor (Color.argb(255, 18, 145, 18)); 
			burnrenderer.setLineWidth(5f);
			burnrenderer.setStroke(BasicStroke.DASHED);
			burnrenderer.setPointStyle(PointStyle.SQUARE);
			renderer.addSeriesRenderer (burnrenderer); 
			renderer.setChartTitle ("Burnout"); 
			renderer.setXTitle ("Recorded Dates"); 
			renderer.setYTitle ("Burnout Score"); 
		}
		//else maybe show something else when there is no data
		
	}

	@Override
	public void onPause()
	{
		super.onPause();
		dateChart.onPause();
	}
	
	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onResume()
	 */
	protected void onResume() 
	{
		super.onResume();

		dateChart.onResume();
		/*if (mChartView == null) 
		{
			getChartData();
			mChartView = ChartFactory.getTimeChartView(this,dataset,renderer, null);
			chartLayout.addView(mChartView);

		} 
		else 
		{
			mChartView.repaint();
		}*/
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