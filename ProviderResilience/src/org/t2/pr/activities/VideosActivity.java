/*
 * 
 * Provider Resilience
 * 
 * Copyright © 2009-2012 United States Government as represented by 
 * the Chief Information Officer of the National Center for Telehealth 
 * and Technology. All Rights Reserved.
 * 
 * Copyright © 2009-2012 Contributors. All Rights Reserved. 
 * 
 * THIS OPEN SOURCE AGREEMENT ("AGREEMENT") DEFINES THE RIGHTS OF USE, 
 * REPRODUCTION, DISTRIBUTION, MODIFICATION AND REDISTRIBUTION OF CERTAIN 
 * COMPUTER SOFTWARE ORIGINALLY RELEASED BY THE UNITED STATES GOVERNMENT 
 * AS REPRESENTED BY THE GOVERNMENT AGENCY LISTED BELOW ("GOVERNMENT AGENCY"). 
 * THE UNITED STATES GOVERNMENT, AS REPRESENTED BY GOVERNMENT AGENCY, IS AN 
 * INTENDED THIRD-PARTY BENEFICIARY OF ALL SUBSEQUENT DISTRIBUTIONS OR 
 * REDISTRIBUTIONS OF THE SUBJECT SOFTWARE. ANYONE WHO USES, REPRODUCES, 
 * DISTRIBUTES, MODIFIES OR REDISTRIBUTES THE SUBJECT SOFTWARE, AS DEFINED 
 * HEREIN, OR ANY PART THEREOF, IS, BY THAT ACTION, ACCEPTING IN FULL THE 
 * RESPONSIBILITIES AND OBLIGATIONS CONTAINED IN THIS AGREEMENT.
 * 
 * Government Agency: The National Center for Telehealth and Technology
 * Government Agency Original Software Designation: Provider Resilience001
 * Government Agency Original Software Title: Provider Resilience
 * User Registration Requested. Please send email 
 * with your contact information to: robert.kayl2@us.army.mil
 * Government Agency Point of Contact for Original Software: robert.kayl2@us.army.mil
 * 
 */
package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;

import com.brightcove.mobile.mediaapi.ReadAPI;
import com.brightcove.mobile.mediaapi.model.ItemCollection;
import com.brightcove.mobile.mediaapi.model.Playlist;
import com.brightcove.mobile.mediaapi.model.Video;
import com.brightcove.mobile.mediaapi.model.enums.MediaDeliveryTypeEnum;
import com.brightcove.mobile.mediaapi.model.enums.SortByTypeEnum;
import com.brightcove.mobile.mediaapi.model.enums.SortOrderTypeEnum;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class VideosActivity extends ABSActivity implements OnClickListener
{

	private ImageView btnVidCompFat;
	private ImageView btnVidTrauma;
	private ReadAPI readAPI;
	private DatabaseProvider db = new DatabaseProvider(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videos);

		this.SetMenuVisibility(1);
		this.btnMainTools.setChecked(true);

		btnVidCompFat = (ImageView)this.findViewById(R.id.iv_compfat);
		btnVidCompFat.setOnClickListener(this);

		btnVidTrauma = (ImageView)this.findViewById(R.id.iv_trauma);
		btnVidTrauma.setOnClickListener(this);

		readAPI = new ReadAPI(Global.BRIGHTCOVE_READ_TOKEN);
		readAPI.setMediaDeliveryType(MediaDeliveryTypeEnum.HTTP);
	}

	@Override
	public void onStart()
	{

		super.onStart();
	}

	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{
		case R.id.iv_compfat:
			try{
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
				db.insertMisc("video", 1, answerDate);
				//Read the video url from brightcove and load with standard media player (sorry, brightcove's player is junk.)
				Video video = readAPI.findVideoById(Long.parseLong("1279636611001"), null, null);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(video.getFlvUrl()), "video/*");
				startActivity(intent);
			}
			catch(Exception ex){
				ErrorMsg();
			}

			break;
		case R.id.iv_trauma:
			try{
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
				db.insertMisc("video", 1, answerDate);
				//Read the video url from brightcove and load with standard media player (sorry, brightcove's player is junk.)
				Video video = readAPI.findVideoById(Long.parseLong("1279636609001"), null, null);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(video.getFlvUrl()), "video/*");
				startActivity(intent);
			}
			catch(Exception ex){ErrorMsg();}

			break;
			//http://brightcove.vo.llnwd.net/pd18/media/923136676001/923136676001_1279658779001_health-professional.mp4
		}
	}

	public void ErrorMsg()
	{
		
		
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle("Error");
		myAlertDialog.setMessage("Please check your network connectivity.");
		myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				
			}});
		
		myAlertDialog.show();
	}
}
