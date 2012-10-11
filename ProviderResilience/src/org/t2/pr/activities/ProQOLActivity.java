/*
 * 
 */
package org.t2.pr.activities;

import java.util.ArrayList;

import org.joda.time.DateMidnight;
import org.joda.time.Days;
import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.Scoring;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class ProQOLActivity.
 */
public class ProQOLActivity extends ABSActivity implements OnClickListener
{

	/** The btn update qol. */
	ImageButton btnUpdateQOL;

	/** The tv_qolcsvalue. */
	public TextView tv_qolcsvalue;
	
	/** The tv_qolbvalue. */
	public TextView tv_qolbvalue;
	
	/** The tv_qolstsvalue. */
	public TextView tv_qolstsvalue;
	
	/** The tv_qolcsdesc. */
	public TextView tv_qolcsdesc;
	
	/** The tv_qolbdesc. */
	public TextView tv_qolbdesc;
	
	/** The tv_qolstsdesc. */
	public TextView tv_qolstsdesc;
	
	/** The tv_timesince. */
	public TextView tv_timesince;
	
	/** The iv_csvalue. */
	public ImageView iv_csvalue;
	
	/** The iv_bvalue. */
	public ImageView iv_bvalue;
	
	/** The iv_stsvalue. */
	public ImageView iv_stsvalue;
	
	/** The db. */
	private DatabaseProvider db = new DatabaseProvider(this);

	/** The cs short l. */
	private String csShortL = "Low Score   (click here for more)\r\nYou've scored in the low range of Compassion Satisfaction.";
	
	/** The cs short a. */
	private String csShortA = "Average Score   (click here for more)\r\nYou've scored in the average range of Compassion Satisfaction.";
	
	/** The cs short h. */
	private String csShortH = "High Score   (click here for more)\r\nYou've scored in the high range of Compassion Satisfaction.";

	/** The bu short l. */
	private String buShortL = "Low Score   (click here for more)\r\nYour score on the Burnout subscale is in the low range.";
	
	/** The bu short a. */
	private String buShortA = "Average Score   (click here for more)\r\nYour score on the Burnout subscale is in the average range.";
	
	/** The bu short h. */
	private String buShortH = "High Score   (click here for more)\r\nYour score on the Burnout subscale is in the high range.";

	/** The st short l. */
	private String stShortL = "Low Score   (click here for more)\r\nYour score on the Secondary Traumatic Stress scale is in the low range.";
	
	/** The st short a. */
	private String stShortA = "Average Score   (click here for more)\r\nYour score on the Secondary Traumatic Stress scale is in the average range.";
	
	/** The st short h. */
	private String stShortH = "High Score   (click here for more)\r\nYour score on the Secondary Traumatic Stress scale is in the high range.";

	/** The cs long l. */
	private String csLongL = "You've scored in the low range of Compassion Satisfaction which means that more than seventy-five percent of those completing this scale scored higher than you did. This score suggests that you may either find problems with your current work, or there may be some other reason-for example, you might derive your satisfaction from activities other than your work.";
	
	/** The cs long a. */
	private String csLongA = "You've scored in the average range of Compassion Satisfaction. Approximately twenty-five percent of individuals completing this scale scored higher than you did, and about twenty-five percent scored lower than you. This suggests that you don't find your work to be consistently satisfying, but that you do derive some degree of satisfaction from your daily work activities.";
	
	/** The cs long h. */
	private String csLongH = "You've scored in the high range of Compassion Satisfaction which is higher than seventy-five percent of individuals completing this scale. This suggest that are experiencing considerable satisfaction with your work and that your daily activities tend to bring you pleasure through a sense of competence and accomplishment.";

	/** The bu long l. */
	private String buLongL = "You're score associated with Burnout is in a range that is lower than approximately seventy-five percent of the scores of those who have taken this scale. This low score suggests that you are energetic, like what you are doing and feel like you are making a difference in the work that you do.";
	
	/** The bu long a. */
	private String buLongA = "Your score on the Burnout subscale is in the average range. Approximately twenty-five percent of individuals completing this scale scored higher than you did, and about twenty-five percent scored lower than you. This may indicate that you currently experience some frustration in your work and may be feeling somewhat discouraged or ineffective at times. It may be worth reviewing your answers to see if you can pin down the source of these feelings and determine how you might improve your experience at work.";
	
	/** The bu long h. */
	private String buLongH = "Your score associated with Burnout is higher than about seventy-five percent of your colleagues who have completed this scale. You may wish to think about what aspect of your work makes you feel like you are not effective in your position. Your score may reflect your mood; perhaps you are having a 'bad day' or are in need of some time off. If the high score persists or if it is reflective of other worries, it may be a cause for concern.";

	/** The st long l. */
	private String stLongL = "You're score associated with Secondary Traumatic Stress is in a range that is lower than approximately seventy-five percent of the scores of those who have taken this scale. You may not be working with individuals who are reporting and describing traumatic events or if you are, you are doing a good job creating necessary emotional boundaries.";
	
	/** The st long a. */
	private String stLongA = "Your score on the Secondary Traumatic Stress subscale is in the average range. Approximately twenty-five percent of individuals completing this scale scored higher than you did, and about twenty-five percent scored lower. If you are working with clients or patients who are describing highly traumatic experiences, be sure to focus on self-care which includes maintaining physical, emotional, social, and spiritual supports.";
	
	/** The st long h. */
	private String stLongH = "Your score associated with Secondary Traumatic Stress is higher than about seventy-five percent of your colleagues who have completed this scale. You may want to take some time to think about what at work may be frightening to you or if there is some other reason for the elevated score. While higher scores do not mean that you do have a problem, they are an indication that you may want to examine how you feel about your work and your work environment. You may wish to discuss this with your supervisor, a colleague, or a health care professional.";

	/** The more text. */
	private String moreText = "";
	
	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proqol);

		this.SetMenuVisibility(1);
		this.btnMainDashboard.setChecked(true);

		btnUpdateQOL = (ImageButton) this.findViewById(R.id.btn_upproqol);
		btnUpdateQOL.setOnClickListener(this);

		tv_qolcsvalue = (TextView)this.findViewById(R.id.tv_qolcsvalue);
		tv_qolbvalue = (TextView)this.findViewById(R.id.tv_qolbvalue);
		tv_qolstsvalue = (TextView)this.findViewById(R.id.tv_qolstsvalue);
		tv_qolcsdesc = (TextView)this.findViewById(R.id.tv_qolcsdesc);
		tv_qolcsdesc.setOnClickListener(this);
		tv_qolbdesc = (TextView)this.findViewById(R.id.tv_qolbdesc);
		tv_qolbdesc.setOnClickListener(this);
		tv_qolstsdesc = (TextView)this.findViewById(R.id.tv_qolstsdesc);
		tv_qolstsdesc.setOnClickListener(this);
		tv_timesince = (TextView)this.findViewById(R.id.tv_timesince);
		iv_csvalue = (ImageView)this.findViewById(R.id.iv_csrating);
		iv_bvalue = (ImageView)this.findViewById(R.id.iv_burnoutrating);
		iv_stsvalue = (ImageView)this.findViewById(R.id.iv_stsrating);

		UpdateDisplay();

	}

	/**
	 * Update display.
	 */
	public void UpdateDisplay()
	{
		String sDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
		ArrayList<String> qoldates = (ArrayList<String>) db.selectQOLDates();
		String lastDate = "";
		if(qoldates.size()>0)
			lastDate = qoldates.get(0);
		else
			lastDate = sDate;

		DateMidnight start = new DateMidnight(android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date(lastDate)));
		DateMidnight end = new DateMidnight(android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date(sDate)));
		Global.Log.v("DaysBetween", "start: " + start.toString() + " end: " + end.toString());
		int numDays = Days.daysBetween(start, end).getDays();
		tv_timesince.setText("It's been " + numDays + " days since your last update.");

		tv_qolcsvalue.setText(Scoring.AcuityString(Scoring.QOLCompassionScore(lastDate)) + "\r\n" + (Scoring.QOLCompassionScore(lastDate)));
		tv_qolbvalue.setText(Scoring.AcuityString(Scoring.QOLBurnoutScore(lastDate)) + "\r\n" + (Scoring.QOLBurnoutScore(lastDate)));
		tv_qolstsvalue.setText(Scoring.AcuityString(Scoring.QOLSTSScore(lastDate)) + "\r\n" + (Scoring.QOLSTSScore(lastDate)));

		if(Scoring.AcuityString(Scoring.QOLCompassionScore(lastDate)) == "LOW")
		{
			iv_csvalue.setImageResource(R.drawable.gaugevert_red);
			tv_qolcsdesc.setText(csShortL);
			moreText = csLongL;
		}
		else if(Scoring.AcuityString(Scoring.QOLCompassionScore(lastDate)) == "HIGH")
		{
			iv_csvalue.setImageResource(R.drawable.gaugevert_green);
			tv_qolcsdesc.setText(csShortH);
			moreText = csLongH;
		}
		else
		{
			iv_csvalue.setImageResource(R.drawable.gaugevert_blue);
			tv_qolcsdesc.setText(csShortA);
			moreText = csLongA;
		}

		if(Scoring.AcuityString(Scoring.QOLBurnoutScore(lastDate)) == "HIGH")
		{
			iv_bvalue.setImageResource(R.drawable.gaugevert_red);
			tv_qolbdesc.setText(buShortH);
			moreText = buLongH;
		}
		else if(Scoring.AcuityString(Scoring.QOLBurnoutScore(lastDate)) == "LOW")
		{
			iv_bvalue.setImageResource(R.drawable.gaugevert_green);
			tv_qolbdesc.setText(buShortL);
			moreText = buLongL;
		}
		else
		{
			iv_bvalue.setImageResource(R.drawable.gaugevert_blue);
			tv_qolbdesc.setText(buShortA);
			moreText = buLongA;
		}

		if(Scoring.AcuityString(Scoring.QOLSTSScore(lastDate)) == "HIGH")
		{
			iv_stsvalue.setImageResource(R.drawable.gaugevert_red);
			tv_qolstsdesc.setText(stShortH);
			moreText = stLongH;
		}
		else if(Scoring.AcuityString(Scoring.QOLSTSScore(lastDate)) == "LOW")
		{
			iv_stsvalue.setImageResource(R.drawable.gaugevert_green);
			tv_qolstsdesc.setText(stShortL);
			moreText = stLongL;
		}
		else
		{
			iv_stsvalue.setImageResource(R.drawable.gaugevert_blue);
			tv_qolstsdesc.setText(stShortA);
			moreText = stLongA;
		}
	}

	/**
	 * Show popup.
	 *
	 * @param inTitle the in title
	 * @param inText the in text
	 */
	public void ShowPopup(String inTitle, String inText)
	{
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.popup);
		dialog.setTitle(inTitle);
		dialog.setCancelable(true);

		TextView text = (TextView) dialog.findViewById(R.id.dialogbody);
		text.setText(Html.fromHtml(inText));
		text.setTextSize(14f);
		final CheckBox chk = (CheckBox) dialog.findViewById(R.id.dontShowAgain);
		chk.setVisibility(View.GONE);
		Button button = (Button) dialog.findViewById(R.id.btnOK);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.show();
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
	 * @see org.t2.pr.activities.ABSActivity#onResume()
	 */
	@Override
	public void onResume()
	{
		UpdateDisplay();
		super.onResume();
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
		case R.id.btn_upproqol:
			this.startActivity(ActivityFactory.getUpdateQOLActivity(this));
			break;
		case R.id.tv_qolcsdesc:
			ShowPopup("Compassion/Satisfaction", moreText + "<BR><BR>");
			break;
		case R.id.tv_qolbdesc:
			ShowPopup("Burnout", moreText + "<BR><BR>");
			break;
		case R.id.tv_qolstsdesc:
			ShowPopup("Secondary Traumatic Stress", moreText + "<BR><BR>");
			break;
		}
	}

}
