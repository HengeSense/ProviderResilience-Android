/*
 * 
 */
package ImageViewTouch;

import java.io.InputStream;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

// TODO: Auto-generated Javadoc
/**
 * Fast bitmap drawable. Does not support states. it only
 * support alpha and colormatrix
 * @author alessandro
 *
 */
public class FastBitmapDrawable extends Drawable implements IBitmapDrawable {

	/** The m bitmap. */
	protected Bitmap mBitmap;
	
	/** The m paint. */
	protected Paint mPaint;

	/**
	 * Instantiates a new fast bitmap drawable.
	 *
	 * @param b the b
	 */
	public FastBitmapDrawable( Bitmap b ) {
		mBitmap = b;
		mPaint = new Paint();
		mPaint.setDither( true );
		mPaint.setFilterBitmap( true );
	}
	
	/**
	 * Instantiates a new fast bitmap drawable.
	 *
	 * @param res the res
	 * @param is the is
	 */
	public FastBitmapDrawable( Resources res, InputStream is ){
		this(BitmapFactory.decodeStream(is));
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw( Canvas canvas ) {
		canvas.drawBitmap( mBitmap, 0.0f, 0.0f, mPaint );
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getOpacity()
	 */
	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#setAlpha(int)
	 */
	@Override
	public void setAlpha( int alpha ) {
		mPaint.setAlpha( alpha );
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#setColorFilter(android.graphics.ColorFilter)
	 */
	@Override
	public void setColorFilter( ColorFilter cf ) {
		mPaint.setColorFilter( cf );
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getIntrinsicWidth()
	 */
	@Override
	public int getIntrinsicWidth() {
		return mBitmap.getWidth();
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getIntrinsicHeight()
	 */
	@Override
	public int getIntrinsicHeight() {
		return mBitmap.getHeight();
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getMinimumWidth()
	 */
	@Override
	public int getMinimumWidth() {
		return mBitmap.getWidth();
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getMinimumHeight()
	 */
	@Override
	public int getMinimumHeight() {
		return mBitmap.getHeight();
	}
	
	/**
	 * Sets the anti alias.
	 *
	 * @param value the new anti alias
	 */
	public void setAntiAlias( boolean value ){
		mPaint.setAntiAlias( value );
		invalidateSelf();
	}

	/* (non-Javadoc)
	 * @see ImageViewTouch.IBitmapDrawable#getBitmap()
	 */
	@Override
	public Bitmap getBitmap() {
		return mBitmap;
	}
}