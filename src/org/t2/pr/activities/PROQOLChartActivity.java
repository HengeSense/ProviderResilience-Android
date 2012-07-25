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
import org.joda.time.Duration;
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

public class PROQOLChartActivity extends ABSActivity implements OnClickListener
{

	private static DatabaseProvider db = new DatabaseProvider(Global.appContext);
	private Button btnUpdate;
	GraphicalView mChartView;
	XYMultipleSeriesDataset dataset;
	XYMultipleSeriesRenderer renderer;
	//LinearLayout chartLayout;
	public LineChart lineChart;

	private static final double THREEDAYS = 81300000 *13;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proqolchart);

		lineChart = (LineChart)this.findViewById(R.id.linechart);
		lineChart.gridLines = true;
		lineChart.loadFont("Elronmonospace.ttf", 16, 2, 2);
		
		getZenChartData();
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

	private void getZenChartData()
	{
		ArrayList<String> qoldates = (ArrayList<String>) db.selectQOLDates();

		if(qoldates.size() > 0)
		{
			LineSeries qolSeries = new LineSeries(this, R.drawable.circle);
			qolSeries.dashEffect = new float[] {10,20};
			qolSeries.lineColor = Color.RED;
			qolSeries.lineWidth = 5;

			LineSeries burnSeries = new LineSeries(this, R.drawable.triangle);
			burnSeries.dashEffect = new float[] {10,20};
			burnSeries.lineColor = Color.GREEN;
			burnSeries.lineWidth = 5;

			LineSeries stsSeries = new LineSeries(this, R.drawable.square);
			stsSeries.lineColor = Color.BLUE;
			stsSeries.lineWidth = 5;

			for(int cs = 0; cs < qoldates.size(); cs++)
			{
				String tdate = qoldates.get(cs);
				Date date = new java.util.Date(tdate);
				double score = Scoring.QOLCompassionScore(qoldates.get(cs));
				qolSeries.add(new LinePoint((int)score, "", DateFormat.format("MM/dd/yy", date).toString()));
			}

			for(int burn = 0; burn < qoldates.size(); burn++)
			{

				String tdate = qoldates.get(burn);
				Date date = new java.util.Date(tdate);
				double score = Scoring.QOLBurnoutScore(qoldates.get(burn));
				burnSeries.add(new LinePoint((int)score, "", "x"));
			}

			for(int sts = 0; sts < qoldates.size(); sts++)
			{

				String tdate = qoldates.get(sts);
				Date date = new java.util.Date(tdate);
				double score = Scoring.QOLSTSScore(qoldates.get(sts));
				stsSeries.add(new LinePoint((int)score, "", "x"));
			}

			lineChart.addSeries(qolSeries);
			lineChart.addSeries(burnSeries);
			lineChart.addSeries(stsSeries);
			
		}
	}

	protected void onResume() 
	{
		super.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
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