/*
 * 
 */
package ImageViewTouch;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ViewConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageViewTouch.
 */
public class ImageViewTouch extends ImageViewTouchBase {

	/** The Constant MIN_ZOOM. */
	static final float MIN_ZOOM = 0.9f;
	
	/** The m scale detector. */
	protected ScaleGestureDetector mScaleDetector;
	
	/** The m gesture detector. */
	protected GestureDetector mGestureDetector;
	
	/** The m touch slop. */
	protected int mTouchSlop;
	
	/** The m current scale factor. */
	protected float mCurrentScaleFactor;
	
	/** The m scale factor. */
	protected float mScaleFactor;
	
	/** The m double tap direction. */
	protected int mDoubleTapDirection;
	
	/** The m gesture listener. */
	protected OnGestureListener mGestureListener;
	
	/** The m scale listener. */
	protected OnScaleGestureListener mScaleListener;
	
	/** The m double tap enabled. */
	protected boolean mDoubleTapEnabled = true;
	
	/** The m scale enabled. */
	protected boolean mScaleEnabled = true;
	
	/** The m scroll enabled. */
	protected boolean mScrollEnabled = true;

	/**
	 * Instantiates a new image view touch.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public ImageViewTouch( Context context, AttributeSet attrs ) {
		super( context, attrs );
	}

	/* (non-Javadoc)
	 * @see ImageViewTouch.ImageViewTouchBase#init()
	 */
	@Override
	protected void init() {
		super.init();
		mTouchSlop = ViewConfiguration.getTouchSlop();
		mGestureListener = getGestureListener();
		mScaleListener = getScaleListener();

		mScaleDetector = new ScaleGestureDetector( getContext(), mScaleListener );
		mGestureDetector = new GestureDetector( getContext(), mGestureListener, null, true );

		mCurrentScaleFactor = 1f;
		mDoubleTapDirection = 1;
	}

	/**
	 * Sets the double tap enabled.
	 *
	 * @param value the new double tap enabled
	 */
	public void setDoubleTapEnabled( boolean value ) {
		mDoubleTapEnabled = value;
	}

	/**
	 * Sets the scale enabled.
	 *
	 * @param value the new scale enabled
	 */
	public void setScaleEnabled( boolean value ) {
		mScaleEnabled = value;
	}

	/**
	 * Sets the scroll enabled.
	 *
	 * @param value the new scroll enabled
	 */
	public void setScrollEnabled( boolean value ) {
		mScrollEnabled = value;
	}

	/**
	 * Gets the double tap enabled.
	 *
	 * @return the double tap enabled
	 */
	public boolean getDoubleTapEnabled() {
		return mDoubleTapEnabled;
	}

	/**
	 * Gets the gesture listener.
	 *
	 * @return the gesture listener
	 */
	protected OnGestureListener getGestureListener() {
		return new GestureListener();
	}

	/**
	 * Gets the scale listener.
	 *
	 * @return the scale listener
	 */
	protected OnScaleGestureListener getScaleListener() {
		return new ScaleListener();
	}

	/* (non-Javadoc)
	 * @see ImageViewTouch.ImageViewTouchBase#onBitmapChanged(android.graphics.drawable.Drawable)
	 */
	@Override
	protected void onBitmapChanged( Drawable drawable ) {
		super.onBitmapChanged( drawable );

		float v[] = new float[9];
		mSuppMatrix.getValues( v );
		mCurrentScaleFactor = v[Matrix.MSCALE_X];
	}

	/* (non-Javadoc)
	 * @see ImageViewTouch.ImageViewTouchBase#_setImageDrawable(android.graphics.drawable.Drawable, boolean, android.graphics.Matrix, float)
	 */
	@Override
	protected void _setImageDrawable( final Drawable drawable, final boolean reset, final Matrix initial_matrix, final float maxZoom ) {
		super._setImageDrawable( drawable, reset, initial_matrix, maxZoom );
		mScaleFactor = getMaxZoom() / 3;
	}

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent( MotionEvent event ) {
		mScaleDetector.onTouchEvent( event );
		if ( !mScaleDetector.isInProgress() ) mGestureDetector.onTouchEvent( event );
		int action = event.getAction();
		switch ( action & MotionEvent.ACTION_MASK ) {
			case MotionEvent.ACTION_UP:
				if ( getScale() < 1f ) {
					zoomTo( 1f, 50 );
				}
				break;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see ImageViewTouch.ImageViewTouchBase#onZoom(float)
	 */
	@Override
	protected void onZoom( float scale ) {
		super.onZoom( scale );
		if ( !mScaleDetector.isInProgress() ) mCurrentScaleFactor = scale;
	}

	/**
	 * Fake initial zoom.
	 */
	public void fakeInitialZoom()
	{
		float scale = getScale();
		float targetScale = scale;
		targetScale = onDoubleTapPost( scale, getMaxZoom() );
		targetScale = Math.min( getMaxZoom(), Math.max( targetScale, MIN_ZOOM ) );
		mCurrentScaleFactor = targetScale;
		zoomTo( targetScale, 0, 0, 200 );
		invalidate();
	}
	
	/**
	 * On double tap post.
	 *
	 * @param scale the scale
	 * @param maxZoom the max zoom
	 * @return the float
	 */
	protected float onDoubleTapPost( float scale, float maxZoom ) {
		if ( mDoubleTapDirection == 1 ) {
			if ( ( scale + ( mScaleFactor * 2 ) ) <= maxZoom ) {
				return scale + mScaleFactor;
			} else {
				mDoubleTapDirection = -1;
				return maxZoom;
			}
		} else {
			mDoubleTapDirection = 1;
			return 1f;
		}
	}

	/**
	 * The listener interface for receiving gesture events.
	 * The class that is interested in processing a gesture
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addGestureListener<code> method. When
	 * the gesture event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see GestureEvent
	 */
	public class GestureListener extends GestureDetector.SimpleOnGestureListener {

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTap(android.view.MotionEvent)
		 */
		@Override
		public boolean onDoubleTap( MotionEvent e ) {
			Log.i( LOG_TAG, "onDoubleTap. double tap enabled? " + mDoubleTapEnabled );
			if ( mDoubleTapEnabled ) {
				float scale = getScale();
				float targetScale = scale;
				targetScale = onDoubleTapPost( scale, getMaxZoom() );
				targetScale = Math.min( getMaxZoom(), Math.max( targetScale, MIN_ZOOM ) );
				mCurrentScaleFactor = targetScale;
				zoomTo( targetScale, e.getX(), e.getY(), 200 );
				invalidate();
			}
			return super.onDoubleTap( e );
		}

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.SimpleOnGestureListener#onLongPress(android.view.MotionEvent)
		 */
		@Override
		public void onLongPress( MotionEvent e ) {
			if ( isLongClickable() ) {
				if ( !mScaleDetector.isInProgress() ) {
					setPressed( true );
					performLongClick();
				}
			}
		}

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.SimpleOnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
		 */
		@Override
		public boolean onScroll( MotionEvent e1, MotionEvent e2, float distanceX, float distanceY ) {
			if ( !mScrollEnabled ) return false;

			if ( e1 == null || e2 == null ) return false;
			if ( e1.getPointerCount() > 1 || e2.getPointerCount() > 1 ) return false;
			if ( mScaleDetector.isInProgress() ) return false;
			if ( getScale() == 1f ) return false;
			scrollBy( -distanceX, -distanceY );
			invalidate();
			return super.onScroll( e1, e2, distanceX, distanceY );
		}

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.SimpleOnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
		 */
		@Override
		public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY ) {
			if ( !mScrollEnabled ) return false;

			if ( e1.getPointerCount() > 1 || e2.getPointerCount() > 1 ) return false;
			if ( mScaleDetector.isInProgress() ) return false;

			float diffX = e2.getX() - e1.getX();
			float diffY = e2.getY() - e1.getY();

			if ( Math.abs( velocityX ) > 800 || Math.abs( velocityY ) > 800 ) {
				scrollBy( diffX / 2, diffY / 2, 300 );
				invalidate();
			}
			return super.onFling( e1, e2, velocityX, velocityY );
		}
	}

	/**
	 * The listener interface for receiving scale events.
	 * The class that is interested in processing a scale
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addScaleListener<code> method. When
	 * the scale event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ScaleEvent
	 */
	public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

		/* (non-Javadoc)
		 * @see android.view.ScaleGestureDetector.SimpleOnScaleGestureListener#onScale(android.view.ScaleGestureDetector)
		 */
		@SuppressWarnings("unused")
		@Override
		public boolean onScale( ScaleGestureDetector detector ) {
			float span = detector.getCurrentSpan() - detector.getPreviousSpan();
			float targetScale = mCurrentScaleFactor * detector.getScaleFactor();
			if ( mScaleEnabled ) {
				targetScale = Math.min( getMaxZoom(), Math.max( targetScale, MIN_ZOOM ) );
				zoomTo( targetScale, detector.getFocusX(), detector.getFocusY() );
				mCurrentScaleFactor = Math.min( getMaxZoom(), Math.max( targetScale, MIN_ZOOM ) );
				mDoubleTapDirection = 1;
				invalidate();
				return true;
			}
			return false;
		}
	}
}
