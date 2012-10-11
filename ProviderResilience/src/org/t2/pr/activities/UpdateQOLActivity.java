/*
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

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateQOLActivity.
 */
public class UpdateQOLActivity extends ABSActivity
{

	/** The db. */
	private DatabaseProvider db = new DatabaseProvider(this);
	
	/** The question list. */
	private List<String[]> questionList;
	
	/** The answers. */
	private ArrayList<int[]> answers;

	/** The cur question. */
	private int curQuestion = 0;

	/** The btn one. */
	public Button btnOne;
	
	/** The btn two. */
	public Button btnTwo;
	
	/** The btn three. */
	public Button btnThree;
	
	/** The btn four. */
	public Button btnFour;
	
	/** The btn five. */
	public Button btnFive;
	
	/** The txt question. */
	public TextView txtQuestion;
	
	/** The txt numbers. */
	public TextView txtNumbers;

	/** The m_ progress dialog. */
	private ProgressDialog m_ProgressDialog = null;
	
	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
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

	/**
	 * Show question.
	 *
	 * @param answerValue the answer value
	 */
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

	/**
	 * Save answers threaded.
	 */
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

	/** The terminate. */
	private Runnable terminate = new Runnable() {
		public void run() {
			m_ProgressDialog.dismiss();
			finishActivity();
		}
	};

	/**
	 * Finish activity.
	 */
	private void finishActivity()
	{
		this.finish();
	}
	
	/**
	 * Populate qol questions.
	 */
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
