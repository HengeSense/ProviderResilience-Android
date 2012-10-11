/*
 * 
 */
package org.t2.pr.classes;

import android.content.Context;
import android.content.SharedPreferences;

// TODO: Auto-generated Javadoc
/**
 * The Class Global.
 */
public class Global {

	//Enables logging to console
	/** The Constant DebugOn. */
	public static final boolean DebugOn = false;

	/** The seen welcome. */
	public static boolean seenWelcome = false;
	
	/** The app context. */
	public static Context appContext = null;

	//Brightcove
	/** The Constant BRIGHTCOVE_READ_TOKEN. */
	public static final String BRIGHTCOVE_READ_TOKEN = "KqxnaC4wR_9Z7OoPadhidvvddOPPUIwPiHwIj_WZXqJWZohd9G1Mmw..";
	
	/** The Constant ANOTHER_BRIGHTCOVE_READ_TOKEN. */
	public static final String ANOTHER_BRIGHTCOVE_READ_TOKEN = "aEKaJd8fSOxJHDP_akYJVkjXLywOcC8jSESi13ZrmdY0VVI6r7FDEQ..";
	//1279636611001  one of the video id's
	
	//http://api.brightcove.com/services/library?command=find_all_playlists&token=KqxnaC4wR_9Z7OoPadhidvvddOPPUIwPiHwIj_WZXqJWZohd9G1Mmw..
	//http://api.brightcove.com/services/library?command=find_all_playlists&token=aEKaJd8fSOxJHDP_akYJVkjXLywOcC8jSESi13ZrmdY0VVI6r7FDEQ..
	
	//Flurry
	/** The Constant FLURRY_KEY. */
	public static final String FLURRY_KEY = "W3NK8836GSFB7N9N5W82"; //CZLAXW4ZETEQKTVP1TY2 - dev version key
	
	/** The shared pref. */
	public static SharedPreferences sharedPref;

	//Logging class
	/**
	 * The Class Log.
	 */
	public static class Log
	{
		
		/**
		 * V.
		 *
		 * @param tag the tag
		 * @param msg the msg
		 */
		public static void v(String tag, String msg)
		{
			if(DebugOn)
				android.util.Log.v(tag, msg);
		}
	}
}
