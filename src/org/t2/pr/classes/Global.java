package org.t2.pr.classes;

import android.content.Context;
import android.content.SharedPreferences;

public class Global {

	//Enables logging to console
	public static final boolean DebugOn = true;

	public static boolean seenWelcome = false;
	
	public static Context appContext = null;

	//Brightcove
	public static final String BRIGHTCOVE_READ_TOKEN = "KqxnaC4wR_9Z7OoPadhidvvddOPPUIwPiHwIj_WZXqJWZohd9G1Mmw..";
	//1279636611001  one of the video id's
	
	//Flurry
	public static final String FLURRY_KEY = "CZLAXW4ZETEQKTVP1TY2";
	
	public static SharedPreferences sharedPref;

	//Logging class
	public static class Log
	{
		public static void v(String tag, String msg)
		{
			if(DebugOn)
				android.util.Log.v(tag, msg);
		}
	}
}
