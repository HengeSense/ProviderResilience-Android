package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;

import com.brightcove.mobile.mediaapi.ReadAPI;
import com.brightcove.mobile.mediaapi.model.Video;
import com.brightcove.mobile.mediaapi.model.enums.MediaDeliveryTypeEnum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
				db.insertMisc("video", 1, answerDate);
				//Read the video url from brightcove and load with standard media player (sorry, brightcove's player is junk.)
				Video video = readAPI.findVideoById(Long.parseLong("1279636611001"), null, null);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getFlvUrl()));
				startActivity(intent);
			}
			catch(Exception ex){}

			break;
		case R.id.iv_trauma:
			try{
				//TODO: (Steveo) This video is called directly to the url as seen in the HTML5 version of this application.
				//		This is not the proper way of doing it, and bypasses the Brightcove api altogether. Obviously, at this
				//		point any authentication that may be needed is not being done (not using T2's token).
				
				//This was done because, although I found the video above in t2's brightcove playlist, I could not find this one.
				//Video video = readAPI.findVideoById(Long.parseLong("1279636611001"), null, null);
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
				db.insertMisc("video", 1, answerDate);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://brightcove.vo.llnwd.net/pd18/media/923136676001/923136676001_1279658779001_health-professional.mp4"));
				startActivity(intent);
			}
			catch(Exception ex){}

			break;
			//http://brightcove.vo.llnwd.net/pd18/media/923136676001/923136676001_1279658779001_health-professional.mp4
		}
	}

}
