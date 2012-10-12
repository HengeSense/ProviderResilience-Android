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

import java.util.ArrayList;
import java.util.List;

import org.t2.pr.R;
import org.t2.pr.classes.DatabaseProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UpdateQOLActivity extends ABSActivity
{

	private DatabaseProvider db = new DatabaseProvider(this);
	private List<String[]> questionList;
	private ArrayList<int[]> answers;

	private int curQuestion = 0;

	public Button btnOne;
	public Button btnTwo;
	public Button btnThree;
	public Button btnFour;
	public Button btnFive;
	public TextView txtQuestion;
	public TextView txtNumbers;

	private ProgressDialog m_ProgressDialog = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateqol);

		this.SetMenuVisibility(1);
		this.btnMainDashboard.setChecked(true);

		btnOne = (Button)this.findViewById(R.id.btn_valone);
		btnOne.setOnClickListener(this);

		btnTwo = (Button)this.findViewById(R.id.btn_valtwo);
		btnTwo.setOnClickListener(this);

		btnThree = (Button)this.findViewById(R.id.btn_valthree);
		btnThree.setOnClickListener(this);

		btnFour = (Button)this.findViewById(R.id.btn_valfour);
		btnFour.setOnClickListener(this);

		btnFive = (Button)this.findViewById(R.id.btn_valfive);
		btnFive.setOnClickListener(this);

		txtQuestion = (TextView)this.findViewById(R.id.tv_question);
		txtNumbers = (TextView)this.findViewById(R.id.tv_numbers);

		populateQOLQuestions();
		showQuestion(0);
	}

	private void showQuestion(int answerValue)
	{
		try
		{
			if(answerValue > 0)
			{
				answers.get(curQuestion)[1] = answerValue;

				curQuestion++;
			}

			if(curQuestion < questionList.size())
			{
				txtQuestion.setText(questionList.get(curQuestion)[1]);
				txtNumbers.setText("" + (curQuestion + 1) + " of " + questionList.size());
			}
			else
			{
				saveAnswersThreaded();
			}
		}
		catch(Exception ex){}
	}

	public void saveAnswersThreaded()
	{
		m_ProgressDialog = ProgressDialog.show(this, "Please wait...", " Saving your ratings...", true);

		Runnable myRunnable = new Runnable() 
		{
			public void run() 
			{
				String date = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
				db.insertQOLAnswers(answers, date);
				runOnUiThread(terminate);
			}
		};

		Thread thread = new Thread(null, myRunnable, "SaveThread");
		thread.start();
	}

	private Runnable terminate = new Runnable() {
		public void run() {
			m_ProgressDialog.dismiss();
			finishActivity();
		}
	};

	private void finishActivity()
	{
		this.finish();
	}
	
	private void populateQOLQuestions()
	{
		questionList = db.selectQOLQuestions();
		answers = new ArrayList<int[]>();

		for(int i= 0; i< questionList.size(); i++)
		{
			int[] tmp = {Integer.parseInt(questionList.get(i)[0]), 0};
			answers.add(tmp);
		}
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
		case R.id.btn_valone:
			showQuestion(1);
			break;
		case R.id.btn_valtwo:
			showQuestion(2);
			break;
		case R.id.btn_valthree:
			showQuestion(3);
			break;
		case R.id.btn_valfour:
			showQuestion(4);
			break;
		case R.id.btn_valfive:
			showQuestion(5);
			break;
		}
	}

}
