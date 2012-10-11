/*
 * 
 */
package ImageViewTouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


// TODO: Auto-generated Javadoc
/**
 * Base View to manage image zoom/scrool/pinch operations.
 *
 * @author alessandro
 */
public class ImageViewTouchBase extends ImageView implements IDisposable {

	/**
	 * The listener interface for receiving onBitmapChanged events.
	 * The class that is interested in processing a onBitmapChanged
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnBitmapChangedListener<code> method. When
	 * the onBitmapChanged event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnBitmapChangedEvent
	 */
	public interface OnBitmapChangedListener {

		/**
		 * On bitmap changed.
		 *
		 * @param drawable the drawable
		 */
		void onBitmapChanged( Drawable drawable );
	};

	/** The Constant LOG_TAG. */
	public static final String LOG_TAG = "image";

	/** The m easing. */
	protected Easing mEasing = new Cubic();
	
	/** The m base matrix. */
	protected Matrix mBaseMatrix = new Matrix();
	
	/** The m supp matrix. */
	protected Matrix mSuppMatrix = new Matrix();
	
	/** The m handler. */
	protected Handler mHandler = new Handler();
	
	/** The m on layout runnable. */
	protected Runnable mOnLayoutRunnable = null;
	
	/** The m max zoom. */
	protected float mMaxZoom;
	
	/** The m display matrix. */
	protected final Matrix mDisplayMatrix = new Matrix();
	
	/** The m matrix values. */
	protected final float[] mMatrixValues = new float[9];
	
	/** The m this height. */
	protected int mThisWidth = -1, mThisHeight = -1;
	
	/** The m fit to screen. */
	protected boolean mFitToScreen = false;
	
	/** The max zoom. */
	final protected float MAX_ZOOM = 2.0f;

	/** The m bitmap rect. */
	protected RectF mBitmapRect = new RectF();
	
	/** The m center rect. */
	protected RectF mCenterRect = new RectF();
	
	/** The m scroll rect. */
	protected RectF mScrollRect = new RectF();

	/** The m listener. */
	private OnBitmapChangedListener mListener;

	/**
	 * Instantiates a new image view touch base.
	 *
	 * @param context the context
	 */
	public ImageViewTouchBase( Context context ) {
		super( context );
		init();
	}

	/**
	 * Instantiates a new image view touch base.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public ImageViewTouchBase( Context context, AttributeSet attrs ) {
		super( context, attrs );
		init();
	}

	/**
	 * Sets the on bitmap changed listener.
	 *
	 * @param listener the new on bitmap changed listener
	 */
	public void setOnBitmapChangedListener( OnBitmapChangedListener listener ) {
		mListener = listener;
	}

	/**
	 * Inits the.
	 */
	protected void init() {
		setScaleType( ImageView.ScaleType.MATRIX );
	}

	/**
	 * Clear.
	 */
	public void clear() {
		setImageBitmap( null, true );
	}

	/**
	 * Sets the fit to screen.
	 *
	 * @param value the new fit to screen
	 */
	public void setFitToScreen( boolean value ) {
		if ( value != mFitToScreen ) {
			mFitToScreen = value;
			requestLayout();
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout( boolean changed, int left, int top, int right, int bottom ) {
		super.onLayout( changed, left, top, right, bottom );
		mThisWidth = right - left;
		mThisHeight = bottom - top;
		Runnable r = mOnLayoutRunnable;
		if ( r != null ) {
			mOnLayoutRunnable = null;
			r.run();
		}
		if ( getDrawable() != null ) {
			if ( mFitToScreen )
				getProperBaseMatrix2( getDrawable(), mBaseMatrix );
			else
				getProperBaseMatrix( getDrawable(), mBaseMatrix );
			setImageMatrix( getImageViewMatrix() );
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ImageView#setImageBitmap(android.graphics.Bitmap)
	 */
	@Override
	public void setImageBitmap( Bitmap bm ) {
		setImageBitmap( bm, true );
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ImageView#setImageResource(int)
	 */
	@Override
	public void setImageResource( int resId ) {
		setImageDrawable( getContext().getResources().getDrawable( resId ) );
	}

	/**
	 * Set the new image to display and reset the internal matrix.
	 * 
	 * @param bitmap
	 *           - the {@link Bitmap} to display
	 * @param reset
	 *           - if true the image bounds will be recreated, otherwise the old {@link Matrix} is used to display the new bitmap
	 * @see #setImageBitmap(Bitmap)
	 */
	public void setImageBitmap( final Bitmap bitmap, final boolean reset ) {
		setImageBitmap( bitmap, reset, null );
	}

	/**
	 * Similar to {@link #setImageBitmap(Bitmap, boolean)} but an optional view {@link Matrix} can be passed to determine the new
	 * bitmap view matrix.<br />
	 * This method is useful if you need to restore a Bitmap with the same zoom/pan values from a previous state
	 *
	 * @param bitmap - the {@link Bitmap} to display
	 * @param reset the reset
	 * @param matrix - the {@link Matrix} to be used to display the new bitmap
	 * @see #setImageBitmap(Bitmap, boolean)
	 * @see #setImageBitmap(Bitmap)
	 * @see #getImageViewMatrix()
	 * @see #getDisplayMatrix()
	 */
	public void setImageBitmap( final Bitmap bitmap, final boolean reset, Matrix matrix ) {
		setImageBitmap( bitmap, reset, matrix, -1 );
	}

	/**
	 * Sets the image bitmap.
	 *
	 * @param bitmap the bitmap
	 * @param reset the reset
	 * @param matrix the matrix
	 * @param maxZoom - maximum zoom allowd during zoom gestures
	 * @see #setImageBitmap(Bitmap, boolean, Matrix)
	 */
	public void setImageBitmap( final Bitmap bitmap, final boolean reset, Matrix matrix, float maxZoom ) {

		Log.i( LOG_TAG, "setImageBitmap: " + bitmap );

		if ( bitmap != null )
			setImageDrawable( new FastBitmapDrawable( bitmap ), reset, matrix, maxZoom );
		else
			setImageDrawable( null, reset, matrix, maxZoom );
	}

	/* (non-Javadoc)
	 * @see android.widget.ImageView#setImageDrawable(android.graphics.drawable.Drawable)
	 */
	@Override
	public void setImageDrawable( Drawable drawable ) {
		setImageDrawable( drawable, true, null, -1 );
	}

	/**
	 * Sets the image drawable.
	 *
	 * @param drawable the drawable
	 * @param reset the reset
	 * @param initial_matrix the initial_matrix
	 * @param maxZoom the max zoom
	 */
	public void setImageDrawable( final Drawable drawable, final boolean reset, final Matrix initial_matrix, final float maxZoom ) {

		final int viewWidth = getWidth();

		if ( viewWidth <= 0 ) {
			mOnLayoutRunnable = new Runnable() {

				@Override
				public void run() {
					setImageDrawable( drawable, reset, initial_matrix, maxZoom );
				}
			};
			return;
		}

		_setImageDrawable( drawable, reset, initial_matrix, maxZoom );
	}

	/**
	 * _set image drawable.
	 *
	 * @param drawable the drawable
	 * @param reset the reset
	 * @param initial_matrix the initial_matrix
	 * @param maxZoom the max zoom
	 */
	protected void _setImageDrawable( final Drawable drawable, final boolean reset, final Matrix initial_matrix, final float maxZoom ) {

		if ( drawable != null ) {
			if ( mFitToScreen )
				getProperBaseMatrix2( drawable, mBaseMatrix );
			else
				getProperBaseMatrix( drawable, mBaseMatrix );
			super.setImageDrawable( drawable );
		} else {
			mBaseMatrix.reset();
			super.setImageDrawable( null );
		}

		if ( reset ) {
			mSuppMatrix.reset();
			if ( initial_matrix != null ) {
				mSuppMatrix = new Matrix( initial_matrix );
			}
		}

		setImageMatrix( getImageViewMatrix() );

		if ( maxZoom < 1 )
			mMaxZoom = maxZoom();
		else
			mMaxZoom = maxZoom;

		onBitmapChanged( drawable );
	}

	/**
	 * On bitmap changed.
	 *
	 * @param bitmap the bitmap
	 */
	protected void onBitmapChanged( final Drawable bitmap ) {
		if ( mListener != null ) {
			mListener.onBitmapChanged( bitmap );
		}
	}

	/**
	 * Max zoom.
	 *
	 * @return the float
	 */
	protected float maxZoom() {
		final Drawable drawable = getDrawable();

		if ( drawable == null ) {
			return 1F;
		}

		float fw = (float) drawable.getIntrinsicWidth() / (float) mThisWidth;
		float fh = (float) drawable.getIntrinsicHeight() / (float) mThisHeight;
		float max = Math.max( fw, fh ) * 4;
		return max;
	}

	/**
	 * Gets the max zoom.
	 *
	 * @return the max zoom
	 */
	public float getMaxZoom() {
		return mMaxZoom;
	}

	/**
	 * Gets the image view matrix.
	 *
	 * @return the image view matrix
	 */
	public Matrix getImageViewMatrix() {
		mDisplayMatrix.set( mBaseMatrix );
		mDisplayMatrix.postConcat( mSuppMatrix );
		return mDisplayMatrix;
	}

	/**
	 * Returns the current image display matrix. This matrix can be used in the next call to the
	 *
	 * @return the display matrix
	 * {@link #setImageBitmap(Bitmap, boolean, Matrix)} to restore the same view state of the previous {@link Bitmap}
	 */
	public Matrix getDisplayMatrix() {
		return new Matrix( mSuppMatrix );
	}

	/**
	 * Setup the base matrix so that the image is centered and scaled properly.
	 *
	 * @param drawable the drawable
	 * @param matrix the matrix
	 * @return the proper base matrix
	 */
	protected void getProperBaseMatrix( Drawable drawable, Matrix matrix ) {
		float viewWidth = getWidth();
		float viewHeight = getHeight();
		float w = drawable.getIntrinsicWidth();
		float h = drawable.getIntrinsicHeight();
		matrix.reset();

		if ( w > viewWidth || h > viewHeight ) {
			float widthScale = Math.min( viewWidth / w, 2.0f );
			float heightScale = Math.min( viewHeight / h, 2.0f );
			float scale = Math.min( widthScale, heightScale );
			matrix.postScale( scale, scale );
			float tw = ( viewWidth - w * scale ) / 2.0f;
			float th = ( viewHeight - h * scale ) / 2.0f;
			matrix.postTranslate( tw, th );
		} else {
			float tw = ( viewWidth - w ) / 2.0f;
			float th = ( viewHeight - h ) / 2.0f;
			matrix.postTranslate( tw, th );
		}
	}

	/**
	 * Setup the base matrix so that the image is centered and scaled properly.
	 *
	 * @param bitmap the bitmap
	 * @param matrix the matrix
	 * @return the proper base matrix2
	 */
	protected void getProperBaseMatrix2( Drawable bitmap, Matrix matrix ) {
		float viewWidth = getWidth();
		float viewHeight = getHeight();
		float w = bitmap.getIntrinsicWidth();
		float h = bitmap.getIntrinsicHeight();
		matrix.reset();
		float widthScale = Math.min( viewWidth / w, MAX_ZOOM );
		float heightScale = Math.min( viewHeight / h, MAX_ZOOM );
		float scale = Math.min( widthScale, heightScale );
		matrix.postScale( scale, scale );
		matrix.postTranslate( ( viewWidth - w * scale ) / MAX_ZOOM, ( viewHeight - h * scale ) / MAX_ZOOM );
	}

	/**
	 * Gets the value.
	 *
	 * @param matrix the matrix
	 * @param whichValue the which value
	 * @return the value
	 */
	protected float getValue( Matrix matrix, int whichValue ) {
		matrix.getValues( mMatrixValues );
		return mMatrixValues[whichValue];
	}

	/**
	 * Gets the bitmap rect.
	 *
	 * @return the bitmap rect
	 */
	protected RectF getBitmapRect() {
		final Drawable drawable = getDrawable();

		if ( drawable == null ) return null;
		Matrix m = getImageViewMatrix();
		mBitmapRect.set( 0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() );
		m.mapRect( mBitmapRect );
		return mBitmapRect;
	}

	/**
	 * Gets the scale.
	 *
	 * @param matrix the matrix
	 * @return the scale
	 */
	protected float getScale( Matrix matrix ) {
		return getValue( matrix, Matrix.MSCALE_X );
	}

	/**
	 * Gets the rotation.
	 *
	 * @return the rotation
	 */
	public float getRotation() {
		return 0;
	}

	/**
	 * Gets the scale.
	 *
	 * @return the scale
	 */
	public float getScale() {
		return getScale( mSuppMatrix );
	}

	/**
	 * Center.
	 *
	 * @param horizontal the horizontal
	 * @param vertical the vertical
	 */
	protected void center( boolean horizontal, boolean vertical ) {
		// Log.i(LOG_TAG, "center");
		final Drawable drawable = getDrawable();

		if ( drawable == null ) return;
		RectF rect = getCenter( horizontal, vertical );
		if ( rect.left != 0 || rect.top != 0 ) {
			postTranslate( rect.left, rect.top );
		}
	}

	/**
	 * Gets the center.
	 *
	 * @param horizontal the horizontal
	 * @param vertical the vertical
	 * @return the center
	 */
	protected RectF getCenter( boolean horizontal, boolean vertical ) {
		final Drawable drawable = getDrawable();

		if ( drawable == null ) return new RectF( 0, 0, 0, 0 );

		RectF rect = getBitmapRect();
		float height = rect.height();
		float width = rect.width();
		float deltaX = 0, deltaY = 0;
		if ( vertical ) {
			int viewHeight = getHeight();
			if ( height < viewHeight ) {
				deltaY = ( viewHeight - height ) / 2 - rect.top;
			} else if ( rect.top > 0 ) {
				deltaY = -rect.top;
			} else if ( rect.bottom < viewHeight ) {
				deltaY = getHeight() - rect.bottom;
			}
		}
		if ( horizontal ) {
			int viewWidth = getWidth();
			if ( width < viewWidth ) {
				deltaX = ( viewWidth - width ) / 2 - rect.left;
			} else if ( rect.left > 0 ) {
				deltaX = -rect.left;
			} else if ( rect.right < viewWidth ) {
				deltaX = viewWidth - rect.right;
			}
		}
		mCenterRect.set( deltaX, deltaY, 0, 0 );
		return mCenterRect;
	}

	/**
	 * Post translate.
	 *
	 * @param deltaX the delta x
	 * @param deltaY the delta y
	 */
	protected void postTranslate( float deltaX, float deltaY ) {
		mSuppMatrix.postTranslate( deltaX, deltaY );
		setImageMatrix( getImageViewMatrix() );
	}

	/**
	 * Post scale.
	 *
	 * @param scale the scale
	 * @param centerX the center x
	 * @param centerY the center y
	 */
	protected void postScale( float scale, float centerX, float centerY ) {
		mSuppMatrix.postScale( scale, scale, centerX, centerY );
		setImageMatrix( getImageViewMatrix() );
	}

	/**
	 * Zoom to.
	 *
	 * @param scale the scale
	 */
	protected void zoomTo( float scale ) {
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;
		zoomTo( scale, cx, cy );
	}

	/**
	 * Zoom to.
	 *
	 * @param scale the scale
	 * @param durationMs the duration ms
	 */
	public void zoomTo( float scale, float durationMs ) {
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;
		zoomTo( scale, cx, cy, durationMs );
	}

	/**
	 * Zoom to.
	 *
	 * @param scale the scale
	 * @param centerX the center x
	 * @param centerY the center y
	 */
	protected void zoomTo( float scale, float centerX, float centerY ) {
		// Log.i(LOG_TAG, "zoomTo");

		if ( scale > mMaxZoom ) scale = mMaxZoom;
		float oldScale = getScale();
		float deltaScale = scale / oldScale;
		postScale( deltaScale, centerX, centerY );
		onZoom( getScale() );
		center( true, true );
	}

	/**
	 * On zoom.
	 *
	 * @param scale the scale
	 */
	protected void onZoom( float scale ) {}

	/**
	 * Scroll by.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void scrollBy( float x, float y ) {
		panBy( x, y );
	}

	/**
	 * Pan by.
	 *
	 * @param dx the dx
	 * @param dy the dy
	 */
	protected void panBy( double dx, double dy ) {
		RectF rect = getBitmapRect();
		mScrollRect.set( (float) dx, (float) dy, 0, 0 );
		updateRect( rect, mScrollRect );
		postTranslate( mScrollRect.left, mScrollRect.top );
		center( true, true );
	}

	/**
	 * Update rect.
	 *
	 * @param bitmapRect the bitmap rect
	 * @param scrollRect the scroll rect
	 */
	protected void updateRect( RectF bitmapRect, RectF scrollRect ) {
		float width = getWidth();
		float height = getHeight();

		if ( bitmapRect.top >= 0 && bitmapRect.bottom <= height ) scrollRect.top = 0;
		if ( bitmapRect.left >= 0 && bitmapRect.right <= width ) scrollRect.left = 0;
		if ( bitmapRect.top + scrollRect.top >= 0 && bitmapRect.bottom > height ) scrollRect.top = (int) ( 0 - bitmapRect.top );
		if ( bitmapRect.bottom + scrollRect.top <= ( height - 0 ) && bitmapRect.top < 0 )
			scrollRect.top = (int) ( ( height - 0 ) - bitmapRect.bottom );
		if ( bitmapRect.left + scrollRect.left >= 0 ) scrollRect.left = (int) ( 0 - bitmapRect.left );
		if ( bitmapRect.right + scrollRect.left <= ( width - 0 ) ) scrollRect.left = (int) ( ( width - 0 ) - bitmapRect.right );
		// Log.d( LOG_TAG, "scrollRect(2): " + scrollRect.toString() );
	}

	/**
	 * Scroll by.
	 *
	 * @param distanceX the distance x
	 * @param distanceY the distance y
	 * @param durationMs the duration ms
	 */
	protected void scrollBy( float distanceX, float distanceY, final double durationMs ) {
		final double dx = distanceX;
		final double dy = distanceY;
		final long startTime = System.currentTimeMillis();
		mHandler.post( new Runnable() {

			double old_x = 0;
			double old_y = 0;

			@Override
			public void run() {
				long now = System.currentTimeMillis();
				double currentMs = Math.min( durationMs, now - startTime );
				double x = mEasing.easeOut( currentMs, 0, dx, durationMs );
				double y = mEasing.easeOut( currentMs, 0, dy, durationMs );
				panBy( ( x - old_x ), ( y - old_y ) );
				old_x = x;
				old_y = y;
				if ( currentMs < durationMs ) {
					mHandler.post( this );
				} else {
					RectF centerRect = getCenter( true, true );
					if ( centerRect.left != 0 || centerRect.top != 0 ) scrollBy( centerRect.left, centerRect.top );
				}
			}
		} );
	}

	/**
	 * Zoom to.
	 *
	 * @param scale the scale
	 * @param centerX the center x
	 * @param centerY the center y
	 * @param durationMs the duration ms
	 */
	protected void zoomTo( float scale, final float centerX, final float centerY, final float durationMs ) {
		// Log.i( LOG_TAG, "zoomTo: " + scale + ", " + centerX + ": " + centerY );
		final long startTime = System.currentTimeMillis();
		final float incrementPerMs = ( scale - getScale() ) / durationMs;
		final float oldScale = getScale();
		mHandler.post( new Runnable() {

			@Override
			public void run() {
				long now = System.currentTimeMillis();
				float currentMs = Math.min( durationMs, now - startTime );
				float target = oldScale + ( incrementPerMs * currentMs );
				zoomTo( target, centerX, centerY );
				if ( currentMs < durationMs ) {
					mHandler.post( this );
				} else {
					// if ( getScale() < 1f ) {}
				}
			}
		} );
	}

	/* (non-Javadoc)
	 * @see ImageViewTouch.IDisposable#dispose()
	 */
	@Override
	public void dispose() {
		clear();
	}
}
