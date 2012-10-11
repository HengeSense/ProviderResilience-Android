/*
 * 
 */
package org.t2.pr.classes;

import org.t2.pr.activities.AboutActivity;
import org.t2.pr.activities.BurnoutChartActivity;
import org.t2.pr.activities.CardsActivity;
import org.t2.pr.activities.EulaActivity;
import org.t2.pr.activities.HelperCardActivity;
import org.t2.pr.activities.HomeActivity;
import org.t2.pr.activities.LaughActivity;
import org.t2.pr.activities.PROQOLChartActivity;
import org.t2.pr.activities.ProQOLActivity;
import org.t2.pr.activities.RBQuestionsActivity;
import org.t2.pr.activities.RKQuestionsActivity;
import org.t2.pr.activities.RRClockActivity;
import org.t2.pr.activities.RRatingActivity;
import org.t2.pr.activities.RemindMeActivity;
import org.t2.pr.activities.SettingsActivity;
import org.t2.pr.activities.SplashActivity;
import org.t2.pr.activities.StretchesActivity;
import org.t2.pr.activities.ToolsActivity;
import org.t2.pr.activities.UpdateBurnoutActivity;
import org.t2.pr.activities.UpdateQOLActivity;
import org.t2.pr.activities.VideosActivity;

import android.content.Context;
import android.content.Intent;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Activity objects.
 */
public class ActivityFactory 
{

	// Splash
	/**
	 * Gets the splash activity.
	 *
	 * @param c the c
	 * @return the splash activity
	 */
	public static Intent getSplashActivity(Context c) 
	{
		Intent intent = new Intent(c, SplashActivity.class);
		return intent;
	}

	/**
	 * Gets the eULA activity.
	 *
	 * @param c the c
	 * @return the eULA activity
	 */
	public static Intent getEULAActivity(Context c) 
	{
		Intent intent = new Intent(c, EulaActivity.class);
		return intent;
	}

	// Home
	/**
	 * Gets the home activity.
	 *
	 * @param c the c
	 * @return the home activity
	 */
	public static Intent getHomeActivity(Context c) 
	{
		Intent intent = new Intent(c, HomeActivity.class);
		return intent;
	}
	
	/**
	 * Gets the tools activity.
	 *
	 * @param c the c
	 * @return the tools activity
	 */
	public static Intent getToolsActivity(Context c) 
	{
		Intent intent = new Intent(c, ToolsActivity.class);
		return intent;
	}
	
	/**
	 * Gets the cards activity.
	 *
	 * @param c the c
	 * @return the cards activity
	 */
	public static Intent getCardsActivity(Context c) 
	{
		Intent intent = new Intent(c, CardsActivity.class);
		return intent;
	}
	
	/**
	 * Gets the about activity.
	 *
	 * @param c the c
	 * @return the about activity
	 */
	public static Intent getAboutActivity(Context c) 
	{
		Intent intent = new Intent(c, AboutActivity.class);
		return intent;
	}
	
	/**
	 * Gets the settings activity.
	 *
	 * @param c the c
	 * @return the settings activity
	 */
	public static Intent getSettingsActivity(Context c) 
	{
		Intent intent = new Intent(c, SettingsActivity.class);
		return intent;
	}
	
	/**
	 * Gets the rR clock activity.
	 *
	 * @param c the c
	 * @return the rR clock activity
	 */
	public static Intent getRRClockActivity(Context c) 
	{
		Intent intent = new Intent(c, RRClockActivity.class);
		return intent;
	}
	
	/**
	 * Gets the r rating activity.
	 *
	 * @param c the c
	 * @return the r rating activity
	 */
	public static Intent getRRatingActivity(Context c) 
	{
		Intent intent = new Intent(c, RRatingActivity.class);
		return intent;
	}
	
	/**
	 * Gets the pro qol activity.
	 *
	 * @param c the c
	 * @return the pro qol activity
	 */
	public static Intent getProQOLActivity(Context c) 
	{
		Intent intent = new Intent(c, ProQOLActivity.class);
		return intent;
	}
	
	/**
	 * Gets the videos activity.
	 *
	 * @param c the c
	 * @return the videos activity
	 */
	public static Intent getVideosActivity(Context c) 
	{
		Intent intent = new Intent(c, VideosActivity.class);
		return intent;
	}
	
	/**
	 * Gets the stretches activity.
	 *
	 * @param c the c
	 * @return the stretches activity
	 */
	public static Intent getStretchesActivity(Context c) 
	{
		Intent intent = new Intent(c, StretchesActivity.class);
		return intent;
	}
	
	/**
	 * Gets the remind me activity.
	 *
	 * @param c the c
	 * @return the remind me activity
	 */
	public static Intent getRemindMeActivity(Context c) 
	{
		Intent intent = new Intent(c, RemindMeActivity.class);
		return intent;
	}
	
	/**
	 * Gets the helper card activity.
	 *
	 * @param c the c
	 * @return the helper card activity
	 */
	public static Intent getHelperCardActivity(Context c) 
	{
		Intent intent = new Intent(c, HelperCardActivity.class);
		return intent;
	}
	
	/**
	 * Gets the rB questions activity.
	 *
	 * @param c the c
	 * @return the rB questions activity
	 */
	public static Intent getRBQuestionsActivity(Context c) 
	{
		Intent intent = new Intent(c, RBQuestionsActivity.class);
		return intent;
	}
	
	/**
	 * Gets the rK questions activity.
	 *
	 * @param c the c
	 * @return the rK questions activity
	 */
	public static Intent getRKQuestionsActivity(Context c) 
	{
		Intent intent = new Intent(c, RKQuestionsActivity.class);
		return intent;
	}
	
	/**
	 * Gets the update qol activity.
	 *
	 * @param c the c
	 * @return the update qol activity
	 */
	public static Intent getUpdateQOLActivity(Context c) 
	{
		Intent intent = new Intent(c, UpdateQOLActivity.class);
		return intent;
	}
	
	/**
	 * Gets the update burnout activity.
	 *
	 * @param c the c
	 * @return the update burnout activity
	 */
	public static Intent getUpdateBurnoutActivity(Context c) 
	{
		Intent intent = new Intent(c, UpdateBurnoutActivity.class);
		return intent;
	}
	
	/**
	 * Gets the pROQOL chart activity.
	 *
	 * @param c the c
	 * @return the pROQOL chart activity
	 */
	public static Intent getPROQOLChartActivity(Context c) 
	{
		Intent intent = new Intent(c, PROQOLChartActivity.class);
		return intent;
	}
	
	/**
	 * Gets the burnout chart activity.
	 *
	 * @param c the c
	 * @return the burnout chart activity
	 */
	public static Intent getBurnoutChartActivity(Context c) 
	{
		Intent intent = new Intent(c, BurnoutChartActivity.class);
		return intent;
	}
	
	/**
	 * Gets the laugh activity.
	 *
	 * @param c the c
	 * @return the laugh activity
	 */
	public static Intent getLaughActivity(Context c) 
	{
		Intent intent = new Intent(c, LaughActivity.class);
		return intent;
	}
	
}