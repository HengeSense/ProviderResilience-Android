package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;

import com.brightcove.mobile.mediaapi.ReadAPI;
import com.brightcove.mobile.mediaapi.model.enums.MediaDeliveryTypeEnum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class RemindMeActivity extends ABSActivity implements OnClickListener
{

	private ImageView btnDepression;
	private ImageView btnAlcohol;
	private ImageView btnAnger;
	private ImageView btnSeekDepression;
	private ImageView btnStigma;
	private DatabaseProvider db = new DatabaseProvider(this);
	private ReadAPI readAPI;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remindme);

		this.SetMenuVisibility(1);
		this.btnMainTools.setChecked(true);

		btnDepression = (ImageView)this.findViewById(R.id.iv_depression);
		btnDepression.setOnClickListener(this);

		btnAlcohol = (ImageView)this.findViewById(R.id.iv_alcohol);
		btnAlcohol.setOnClickListener(this);
		
		btnAnger = (ImageView)this.findViewById(R.id.iv_anger);
		btnAnger.setOnClickListener(this);
		
		btnSeekDepression = (ImageView)this.findViewById(R.id.iv_seekdepression);
		btnSeekDepression.setOnClickListener(this);
		
		btnStigma = (ImageView)this.findViewById(R.id.iv_stigma);
		btnStigma.setOnClickListener(this);

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
		case R.id.iv_depression:
			try{
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
				db.insertMisc("remindme", 1, answerDate);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://brightcove.vo.llnwd.net/pd16/media/1041122098001/1041122098001_1125440692001_Depression---01-Reach-out-for-help.mp4"));
				startActivity(intent);
			}
			catch(Exception ex){}

			break;
		case R.id.iv_alcohol:
			try{
				try{
					String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
					db.insertMisc("remindme", 1, answerDate);
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://brightcove.vo.llnwd.net/pd16/media/1041122098001/1041122098001_1124152443001_Alcohol---03-Aiken.mp4?pubId=1041122098001&videoId=1124090468001"));
					startActivity(intent);
				}
				catch(Exception ex){}
			}
			catch(Exception ex){}

			break;
			
		case R.id.iv_anger:
			try{
				try{
					String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
					db.insertMisc("remindme", 1, answerDate);
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://brightcove.vo.llnwd.net/pd16/media/1041122098001/1041122098001_1125566806001_Anger---01-Anger-Compilation.mp4"));
					startActivity(intent);
				}
				catch(Exception ex){}
			}
			catch(Exception ex){}

			break;
			
		case R.id.iv_seekdepression:
			try{
				try{
					String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
					db.insertMisc("remindme", 1, answerDate);
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://brightcove.vo.llnwd.net/pd16/media/1041122098001/1041122098001_1125427781001_Depression---02-Seek-Support-for-Depression.mp4"));
					startActivity(intent);
				}
				catch(Exception ex){}
			}
			catch(Exception ex){}

			break;
			
		case R.id.iv_stigma:
			try{
				try{
					String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
					db.insertMisc("remindme", 1, answerDate);
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://brightcove.vo.llnwd.net/pd16/media/1041122098001/1041122098001_1128785184001_Stigma---01-Compilation.mp4"));
					startActivity(intent);
				}
				catch(Exception ex){}
			}
			catch(Exception ex){}

			break;
			
		}
	}

}
