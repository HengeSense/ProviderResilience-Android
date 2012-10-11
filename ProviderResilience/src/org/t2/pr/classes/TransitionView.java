/*
 * 
 */
package org.t2.pr.classes;

/**
 * Transition view with 3 direction animations
 * @author stephenody
 * 
 * based on transitionview.java from Nathan Scandella
 * Copyright 2011 Enscand, Inc.
 */

import org.t2.pr.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;

// TODO: Auto-generated Javadoc
/**
 * A transition view provides animated switching of 
 * a predefined set of image resources.
 */
public class TransitionView extends RelativeLayout {

   /** One of the two in-memory art images. */
   private ImageView _artView1;
   
   /** The other of the two in-memory art images. */
   private ImageView _artView2;
   
   /** Length of art view transition animation, in milliseconds. */
   private final int ANIMATION_DURATION_MSEC = 300;
   
   /** The underlying ImageSwitcher that performs transitions. */
   private ImageSwitcher _imageSwitcher;

   /** The anim r in. */
   private Animation animRIn;
   
   /** The anim r out. */
   private Animation animROut;
   
   /** The anim l in. */
   private Animation animLIn;
   
   /** The anim l out. */
   private Animation animLOut;
   
   /** The anim f in. */
   private Animation animFIn;
   
   /** The anim f out. */
   private Animation animFOut;

   
   /**
    * Create a new instance.
    * @param context The parent context
    */
   public TransitionView(Context context) {
      super(context); 
      customInit(context);
   }
   
   /**
    * Initialize a new instance.
    * @param context The parent context
    */
   private void customInit(Context context) {

      _imageSwitcher = new ImageSwitcher(context);
      animRIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
      animRIn.setDuration(ANIMATION_DURATION_MSEC);
      animROut = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
      animROut.setDuration(ANIMATION_DURATION_MSEC);
      animLIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
      animLIn.setDuration(ANIMATION_DURATION_MSEC);
      animLOut = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
      animLOut.setDuration(ANIMATION_DURATION_MSEC);
      
      animFIn = AnimationUtils.loadAnimation(context, R.anim.grow_from_middle);
      animFIn.setDuration(ANIMATION_DURATION_MSEC);
      animFOut = AnimationUtils.loadAnimation(context, R.anim.shrink_to_middle);
      animFOut.setDuration(ANIMATION_DURATION_MSEC);
      
      _imageSwitcher.setInAnimation(animLIn);
      _imageSwitcher.setOutAnimation(animLOut);

      _artView1 = new ImageView(context);
      //_artView1.setImageResource(_imageIds[_currentImage]);

      _artView2 = new ImageView(context);
      //_artView2.setImageResource(_imageIds[_currentImage + 1]);

      LayoutParams fullScreenLayout = new LayoutParams(LayoutParams.FILL_PARENT, 
            LayoutParams.FILL_PARENT);
      _imageSwitcher.addView(_artView1, 0, fullScreenLayout);
      _imageSwitcher.addView(_artView2, 1, fullScreenLayout);
      _imageSwitcher.setDisplayedChild(0);
      addView(_imageSwitcher, fullScreenLayout);
   }

   /**
    * Instantiates a new transition view.
    *
    * @param context the context
    * @param attrs the attrs
    * @see android.view.View#View(Context, AttributeSet)
    */
   public TransitionView(Context context, AttributeSet attrs) {
      super(context, attrs);
      customInit(context);
   }

   /**
    * Instantiates a new transition view.
    *
    * @param context the context
    * @param attrs the attrs
    * @param defStyle the def style
    * @see android.view.View#View(Context, AttributeSet, int)
    */
   public TransitionView(Context context, AttributeSet attrs, int defStyle) {
      super(context, attrs, defStyle);
      customInit(context);
   }

   /**
    * Change the currently displayed image.
    *
    * @param newRESID the new resid
    * @param dir the dir
    */
   public void changePage(int newRESID, int dir) {
	   
	   if(dir == 0)
	   {
		   _imageSwitcher.setInAnimation(animRIn);
		      _imageSwitcher.setOutAnimation(animROut);
	   }
	   else if(dir == 1)
	   {
		   _imageSwitcher.setInAnimation(animLIn);
		      _imageSwitcher.setOutAnimation(animLOut);
	   }
	   else 
	   {
		   _imageSwitcher.setInAnimation(animFIn);
		      _imageSwitcher.setOutAnimation(animFOut);
	   }
	   
      if (_imageSwitcher.getCurrentView() == _artView1) {
         _artView2.setImageResource(newRESID);
         _imageSwitcher.showNext();
      } else {
         _artView1.setImageResource(newRESID);
         _imageSwitcher.showPrevious();
      }
   }
}
