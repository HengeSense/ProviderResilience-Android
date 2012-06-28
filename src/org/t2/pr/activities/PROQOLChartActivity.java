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
import org.joda.time.DateTime;
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

public class PROQOLChartActivity extends ABSActivity implements OnClickListener
{

	private static DatabaseProvider db = new DatabaseProvider(Global.appContext);
	private Button btnUpdate;
	GraphicalView mChartView;
	XYMultipleSeriesDataset dataset;
	XYMultipleSeriesRenderer renderer;
	//LinearLayout chartLayout;
	public DateChart dateChart;

	private static final double THREEDAYS = 81300000 *13;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proqolchart);

		dateChart = (DateChart)this.findViewById(R.id.datechart);
		dateChart.showGrid = true;
		dateChart.scrollGrid = false;
		dateChart.showStars = false;
		dateChart.transparentBackground = true;
		getCoolChartData();
		//chartLayout = (LinearLayout) findViewById(R.id.chart);

		try
		{
			this.SetMenuVisibility(1);
			this.btnMainTools.setChecked(true);
			btnUpdate = (Button)this.findViewById(R.id.btn_proqolbutton);
			btnUpdate.setOnClickListener(this);
		}
		catch(Exception ex){}

	}

	private void getCoolChartData()
	{
		ArrayList<String> qoldates = (ArrayList<String>) db.selectQOLDates();

		if(qoldates.size() > 0)
		{
			DateSeries qolSeries = new DateSeries();
			qolSeries.dashEffect = new float[] {10,20};
			qolSeries.lineColor = Color.RED;
			qolSeries.lineWidth = 5;

			DateSeries burnSeries = new DateSeries();
			burnSeries.dashEffect = new float[] {10,20};
			burnSeries.lineColor = Color.GREEN;
			burnSeries.lineWidth = 5;

			DateSeries stsSeries = new DateSeries();
			stsSeries.lineColor = Color.BLUE;
			stsSeries.lineWidth = 5;

			for(int cs = 0; cs < qoldates.size(); cs++)
			{
				String tdate = qoldates.get(cs);
				Date date = new java.util.Date(tdate);
				double score = Scoring.QOLCompassionScore(qoldates.get(cs));
				qolSeries.add(new DatePoint(date.getTime(), (int)score, ""+(int)score));
			}

			for(int burn = 0; burn < qoldates.size(); burn++)
			{

				String tdate = qoldates.get(burn);
				Date date = new java.util.Date(tdate);
				double score = Scoring.QOLBurnoutScore(qoldates.get(burn));
				burnSeries.add(new DatePoint(date.getTime(), (int)score, ""+(int)score));
			}

			for(int sts = 0; sts < qoldates.size(); sts++)
			{

				String tdate = qoldates.get(sts);
				Date date = new java.util.Date(tdate);
				double score = Scoring.QOLSTSScore(qoldates.get(sts));
				stsSeries.add(new DatePoint(date.getTime(), (int)score, ""+(int)score));
			}

			dateChart.AddSeries(qolSeries);
			dateChart.AddSeries(burnSeries);
			dateChart.AddSeries(stsSeries);
		}
	}

	private void getACEChartData()
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
		ArrayList<String> qoldates = (ArrayList<String>) db.selectQOLDates();

		if(qoldates.size() > 0)
		{
			//Create the CS series 
			TimeSeries csData = new TimeSeries ("Compassion/Satisfaction"); 
			for(int cs = 0; cs < qoldates.size(); cs++)
			{
				String tdate = qoldates.get(cs);
				Date date = new java.util.Date(tdate);
				double score = Scoring.QOLCompassionScore(qoldates.get(cs));
				csData.add(date, score);
			}

			//Create the burn series 
			TimeSeries burnData = new TimeSeries ("Burnout"); 
			for(int burn = 0; burn < qoldates.size(); burn++)
			{
				String tdate = qoldates.get(burn);
				Date date = new java.util.Date(tdate);
				double score = Scoring.QOLBurnoutScore(qoldates.get(burn));
				burnData.add(date, score);
			}

			//Create the burn series 
			TimeSeries stsData = new TimeSeries ("Secondary Traumatic Stress"); 
			for(int sts = 0; sts < qoldates.size(); sts++)
			{
				String tdate = qoldates.get(sts);
				Date date = new java.util.Date(tdate);
				double score = Scoring.QOLSTSScore(qoldates.get(sts));
				stsData.add(date, score);
			}

			dataset.addSeries (csData); 
			dataset.addSeries (burnData); 
			dataset.addSeries (stsData); 

			//Create a new renderer 
			XYSeriesRenderer csrenderer = new XYSeriesRenderer (); 
			csrenderer.setColor (Color.argb(255, 67, 97, 212)); 
			csrenderer.setLineWidth(5f);
			csrenderer.setStroke(BasicStroke.DASHED);
			csrenderer.setPointStyle(PointStyle.DIAMOND);
			//Create a new renderer 
			XYSeriesRenderer burnrenderer = new XYSeriesRenderer (); 
			burnrenderer.setColor (Color.argb(255, 18, 145, 18)); 
			burnrenderer.setLineWidth(5f);
			burnrenderer.setStroke(BasicStroke.DASHED);
			burnrenderer.setPointStyle(PointStyle.SQUARE);
			//Create a new renderer 
			XYSeriesRenderer stsrenderer = new XYSeriesRenderer (); 
			stsrenderer.setColor (Color.argb(255, 227, 20, 29)); 
			stsrenderer.setLineWidth(5f);
			stsrenderer.setStroke(BasicStroke.DASHED);
			stsrenderer.setPointStyle(PointStyle.TRIANGLE);
			renderer.addSeriesRenderer (csrenderer); 
			renderer.addSeriesRenderer (burnrenderer); 
			renderer.addSeriesRenderer (stsrenderer); 
			renderer.setChartTitle ("Quality of Life"); 
			//renderer.setXTitle ("Recorded Dates"); 
			renderer.setYTitle ("Assessment Score"); 
		}
	}

	protected void onResume() 
	{
		super.onResume();
		dateChart.onResume();
		//Re-enable for AChartEngine
		//		if (mChartView == null) 
		//		{
		//			getACEChartData();
		//			mChartView = ChartFactory.getTimeChartView(this,dataset,renderer, null);
		//			chartLayout.addView(mChartView);
		//
		//		} 
		//		else 
		//		{
		//			mChartView.repaint();
		//		}
	}

	@Override
	public void onPause()
	{
		super.onPause();
		dateChart.onPause();
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
		case R.id.btn_proqolbutton:
			this.startActivity(ActivityFactory.getUpdateQOLActivity(this));
			break;
		}
	}

}