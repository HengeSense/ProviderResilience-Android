/*
 * 
 */
package org.t2.pr.classes;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.SeekBar;

// TODO: Auto-generated Javadoc
/**
 * The Class Slider.
 */
public class Slider extends SeekBar {

	/** The m thumb. */
	Drawable mThumb;
	
	/** The can slide. */
	private boolean canSlide = false;

	/**
	 * Instantiates a new slider.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public Slider(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Instantiates a new slider.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public Slider(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Instantiates a new slider.
	 *
	 * @param context the context
	 */
	public Slider(Context context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsSeekBar#setThumb(android.graphics.drawable.Drawable)
	 */
	@Override
	public void setThumb(Drawable thumb) {
		super.setThumb(thumb);
		mThumb = thumb;
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsSeekBar#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Rect bounds = new Rect();
			bounds.top = mThumb.getBounds().top - 30;
			bounds.left = mThumb.getBounds().left - 30;
			bounds.bottom = mThumb.getBounds().bottom + 30;
			bounds.right = mThumb.getBounds().right + 30;

			if (bounds.contains((int) event.getX(), (int) event.getY())) {
				canSlide = true;
			}
		} else {
			if (canSlide)
				super.onTouchEvent(event); // process horizontal slide
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			canSlide = false;
		}

		if (!canSlide) {
			ViewParent parent = (ViewParent) this.getParent();
			if (parent != null) {
				parent.requestDisallowInterceptTouchEvent(false);
			}
			
		}
		return true;
	}

}
