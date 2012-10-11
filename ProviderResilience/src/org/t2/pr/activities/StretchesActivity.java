/*
 * 
 */
package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.SimpleGestureFilter;
import org.t2.pr.classes.SimpleGestureFilter.SimpleGestureListener;
import org.t2.pr.classes.TransitionView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class StretchesActivity.
 */
public class StretchesActivity extends ABSActivity implements SimpleGestureListener{


	/** The card names. */
	private String[] cardNames;
	
	/** The card index. */
	private int cardIndex = -1;

	/** The iv card. */
	private TransitionView ivCard;
	
	/** The detector. */
	private SimpleGestureFilter detector;

	/** The tv_permission. */
	public TextView tv_permission;
	
	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cards);

		this.SetMenuVisibility(1);
		this.btnMainTools.setChecked(true);

		detector = new SimpleGestureFilter(this,this);

		ivCard = (TransitionView)this.findViewById(R.id.ivcard);

		tv_permission = (TextView)this.findViewById(R.id.tv_permission);
		tv_permission.setVisibility(View.GONE);
		
		LoadCards();
		NextCard();
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
		}
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.classes.SimpleGestureFilter.SimpleGestureListener#onDoubleTap()
	 */
	@Override
	public void onDoubleTap() {
	}



	/**
	 * Next card.
	 */
	private void NextCard()
	{
		if(cardIndex < (cardNames.length -1))
			cardIndex++;
		else
			cardIndex = 0;

		int resID = getResources().getIdentifier(cardNames[cardIndex], "drawable", "org.t2.pr");
		ivCard.changePage(resID, 0);
		ivCard.refreshDrawableState();
	}

	/**
	 * Prev card.
	 */
	private void PrevCard()
	{
		if(cardIndex > 0)
			cardIndex--;
		else
			cardIndex = (cardNames.length -1);

		int resID = getResources().getIdentifier(cardNames[cardIndex], "drawable", "org.t2.pr");
		ivCard.changePage(resID, 1);
		ivCard.refreshDrawableState();
	}

	/**
	 * Load cards.
	 */
	private void LoadCards()
	{
		//Load cards
		cardNames = getResources().getStringArray(R.array.StretchCards);    
	}

}
