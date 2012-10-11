/*
 * 
 */
package org.t2.pr.classes;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleGestureFilter.
 */
public class SimpleGestureFilter extends SimpleOnGestureListener{

	/** The Constant SWIPE_UP. */
	public final static int SWIPE_UP    = 1;
	
	/** The Constant SWIPE_DOWN. */
	public final static int SWIPE_DOWN  = 2;
	
	/** The Constant SWIPE_LEFT. */
	public final static int SWIPE_LEFT  = 3;
	
	/** The Constant SWIPE_RIGHT. */
	public final static int SWIPE_RIGHT = 4;

	/** The Constant MODE_TRANSPARENT. */
	public final static int MODE_TRANSPARENT = 0;
	
	/** The Constant MODE_SOLID. */
	public final static int MODE_SOLID       = 1;
	
	/** The Constant MODE_DYNAMIC. */
	public final static int MODE_DYNAMIC     = 2;

	/** The Constant ACTION_FAKE. */
	private final static int ACTION_FAKE = -13; //just an unlikely number
	
	/** The swipe_ min_ distance. */
	private int swipe_Min_Distance = 50; //100
	
	/** The swipe_ max_ distance. */
	private int swipe_Max_Distance = 1350; //350
	
	/** The swipe_ min_ velocity. */
	private int swipe_Min_Velocity = 50; //100

	/** The mode. */
	private int mode      = MODE_DYNAMIC;
	
	/** The running. */
	private boolean running = true;
	
	/** The tap indicator. */
	private boolean tapIndicator = false;

	/** The context. */
	private Activity context;
	
	/** The detector. */
	private GestureDetector detector;
	
	/** The listener. */
	private SimpleGestureListener listener;

	/**
	 * Instantiates a new simple gesture filter.
	 *
	 * @param context the context
	 * @param sgl the sgl
	 */
	public SimpleGestureFilter(Activity context,SimpleGestureListener sgl) {

		this.context = context;
		this.detector = new GestureDetector(context, this);
		this.listener = sgl;
	}

	/**
	 * On touch event.
	 *
	 * @param event the event
	 */
	public void onTouchEvent(MotionEvent event){

		if(!this.running)
			return;  

		boolean result = this.detector.onTouchEvent(event); 

		if(this.mode == MODE_SOLID)
			event.setAction(MotionEvent.ACTION_CANCEL);
		else if (this.mode == MODE_DYNAMIC) {

			if(event.getAction() == ACTION_FAKE)
				event.setAction(MotionEvent.ACTION_UP);
			else if (result)
				event.setAction(MotionEvent.ACTION_CANCEL);
			else if(this.tapIndicator){
				event.setAction(MotionEvent.ACTION_DOWN);
				this.tapIndicator = false;
			}

		}
		//else just do nothing, it's Transparent
	}

	/**
	 * Sets the mode.
	 *
	 * @param m the new mode
	 */
	public void setMode(int m){
		this.mode = m;
	}

	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public int getMode(){
		return this.mode;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param status the new enabled
	 */
	public void setEnabled(boolean status){
		this.running = status;
	}

	/**
	 * Sets the swipe max distance.
	 *
	 * @param distance the new swipe max distance
	 */
	public void setSwipeMaxDistance(int distance){
		this.swipe_Max_Distance = distance;
	}

	/**
	 * Sets the swipe min distance.
	 *
	 * @param distance the new swipe min distance
	 */
	public void setSwipeMinDistance(int distance){
		this.swipe_Min_Distance = distance;
	}

	/**
	 * Sets the swipe min velocity.
	 *
	 * @param distance the new swipe min velocity
	 */
	public void setSwipeMinVelocity(int distance){
		this.swipe_Min_Velocity = distance;
	}

	/**
	 * Gets the swipe max distance.
	 *
	 * @return the swipe max distance
	 */
	public int getSwipeMaxDistance(){
		return this.swipe_Max_Distance;
	}

	/**
	 * Gets the swipe min distance.
	 *
	 * @return the swipe min distance
	 */
	public int getSwipeMinDistance(){
		return this.swipe_Min_Distance;
	}

	/**
	 * Gets the swipe min velocity.
	 *
	 * @return the swipe min velocity
	 */
	public int getSwipeMinVelocity(){
		return this.swipe_Min_Velocity;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.SimpleOnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		final float xDistance = Math.abs(e1.getX() - e2.getX());
		final float yDistance = Math.abs(e1.getY() - e2.getY());

		if(xDistance > this.swipe_Max_Distance || yDistance > this.swipe_Max_Distance)
			return false;

		velocityX = Math.abs(velocityX);
		velocityY = Math.abs(velocityY);
		boolean result = false;

		if(velocityX > this.swipe_Min_Velocity && xDistance > this.swipe_Min_Distance){
			if(e1.getX() > e2.getX()) // right to left
			this.listener.onSwipe(SWIPE_LEFT);
			else
				this.listener.onSwipe(SWIPE_RIGHT);

			result = true;
		}
		else if(velocityY > this.swipe_Min_Velocity && yDistance > this.swipe_Min_Distance){
			if(e1.getY() > e2.getY()) // bottom to up
				this.listener.onSwipe(SWIPE_UP);
			else
				this.listener.onSwipe(SWIPE_DOWN);

			result = true;
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.SimpleOnGestureListener#onSingleTapUp(android.view.MotionEvent)
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		this.tapIndicator = true;
		return false;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTap(android.view.MotionEvent)
	 */
	@Override
	public boolean onDoubleTap(MotionEvent arg0) {
		this.listener.onDoubleTap();;
		return true;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTapEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onDoubleTapEvent(MotionEvent arg0) {
		return true;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.SimpleOnGestureListener#onSingleTapConfirmed(android.view.MotionEvent)
	 */
	@Override
	public boolean onSingleTapConfirmed(MotionEvent arg0) {

		if(this.mode == MODE_DYNAMIC){        // we owe an ACTION_UP, so we fake an
			arg0.setAction(ACTION_FAKE);      //action which will be converted to an ACTION_UP later.
			this.context.dispatchTouchEvent(arg0);
		}   

		return false;
	}

	/**
	 * The listener interface for receiving simpleGesture events.
	 * The class that is interested in processing a simpleGesture
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addSimpleGestureListener<code> method. When
	 * the simpleGesture event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see SimpleGestureEvent
	 */
	public static interface SimpleGestureListener{
		
		/**
		 * On swipe.
		 *
		 * @param direction the direction
		 */
		void onSwipe(int direction);
		
		/**
		 * On double tap.
		 */
		void onDoubleTap();
	}

}
