/*
 * 
 */
package org.t2.pr.activities;

import java.util.Random;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.SharedPref;
import org.t2.pr.classes.SimpleGestureFilter;
import org.t2.pr.classes.SimpleGestureFilter.SimpleGestureListener;
import org.t2.pr.classes.TransitionView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * Displays images from a set of slides. Handles swipe navigation and transitions
 * @author stephenody
 *
 */
public class CardsActivity extends ABSActivity implements OnClickListener, SimpleGestureListener{


	/** The card names. */
	private String[] cardNames;
	
	/** The card index. */
	private int cardIndex = -1;
	
	/** The card side. */
	private int cardSide = 0;

	/** The randomize card. */
	private boolean randomizeCard = false;

	/** The iv card. */
	private TransitionView ivCard;
	
	/** The detector. */
	private SimpleGestureFilter detector;

	/** The btn done. */
	Button btnDone;
	
	/** The tv header. */
	TextView tvHeader;
	
	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cards);

		Intent intent = this.getIntent();
		randomizeCard = intent.getBooleanExtra("random", false);

		if(!randomizeCard)
		{
			this.SetMenuVisibility(View.VISIBLE);
			this.btnMainCards.setChecked(true);
		}
		else
		{
			this.SetMenuVisibility(View.GONE);
			btnDone = (Button) this.findViewById(R.id.btn_Done);
			btnDone.setVisibility(View.VISIBLE);
			btnDone.setOnClickListener(this);
			tvHeader = (TextView)this.findViewById(R.id.tv_header);
			tvHeader.setVisibility(View.VISIBLE);
		}

		detector = new SimpleGestureFilter(this,this);

		ivCard = (TransitionView)this.findViewById(R.id.ivcard);

		LoadCards();
		
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
		case R.id.btn_Done:
			this.finish();
			break;
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.classes.SimpleGestureFilter.SimpleGestureListener#onSwipe(int)
	 */
	@Override
	public void onSwipe(int direction) {

		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT : PrevCard();
		break;
		case SimpleGestureFilter.SWIPE_LEFT :  NextCard();
		break;
		case SimpleGestureFilter.SWIPE_DOWN :  FlipCard();
		break;
		case SimpleGestureFilter.SWIPE_UP :    FlipCard();
		break;

		}
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.classes.SimpleGestureFilter.SimpleGestureListener#onDoubleTap()
	 */
	@Override
	public void onDoubleTap() {
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause()
	{
		SharedPref.setCardIndex(cardIndex);
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onResume()
	 */
	@Override
	public void onResume()
	{
		if(randomizeCard)
		{
			Random rnd = new Random();
			cardIndex = rnd.nextInt(cardNames.length - 1);
		}
		else
		{
		cardIndex = (SharedPref.getCardIndex() - 1);
		}
		super.onResume();
		NextCard();
	}

	/**
	 * Next card.
	 */
	private void NextCard()
	{
		cardSide = 0;
		if(cardIndex < (cardNames.length -1))
			cardIndex++;
		else
			cardIndex = 0;

		int resID = getResources().getIdentifier(cardNames[cardIndex] + "_front", "drawable", "org.t2.pr");
		ivCard.changePage(resID, 0);
		ivCard.refreshDrawableState();
	}

	/**
	 * Prev card.
	 */
	private void PrevCard()
	{
		cardSide = 0;
		if(cardIndex > 0)
			cardIndex--;
		else
			cardIndex = (cardNames.length -1);

		int resID = getResources().getIdentifier(cardNames[cardIndex] + "_front", "drawable", "org.t2.pr");
		ivCard.changePage(resID, 1);
		ivCard.refreshDrawableState();
	}

	/**
	 * Flip card.
	 */
	private void FlipCard()
	{
		if(cardSide == 0)
		{
			cardSide = 1;
			int resID = getResources().getIdentifier(cardNames[cardIndex] + "_reverse", "drawable", "org.t2.pr");
			ivCard.changePage(resID, 2);
		}
		else
		{
			cardSide = 0;
			int resID = getResources().getIdentifier(cardNames[cardIndex] + "_front", "drawable", "org.t2.pr");
			ivCard.changePage(resID, 2);
		}
		ivCard.refreshDrawableState();

	}

	/**
	 * Load cards.
	 */
	private void LoadCards()
	{
		//Load cards
		cardNames = getResources().getStringArray(R.array.VirtueCards);    
	}

}
