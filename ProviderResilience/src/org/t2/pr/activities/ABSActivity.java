package org.t2.pr.activities;

import java.util.List;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.ToggledImageButton;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

/**
 * Base class that handles application navigation
 * @author stephenody
 *
 */
public abstract class ABSActivity extends FlurryActivity implements OnClickListener
{

	
	@SuppressWarnings("unused")
	private boolean isContentViewSet = false;
	
	private LinearLayout llMainMenu;
	public ToggledImageButton btnMainDashboard;
	public ToggledImageButton btnMainTools;
	public ToggledImageButton btnMainCards;
	public ToggledImageButton btnMainAbout;
	public ToggledImageButton btnMainSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		this.onPageView();
	}



	@Override
	public void setContentView(int layoutResID) 
	{
		super.setContentView(layoutResID);
		this.isContentViewSet = true;
		initButtons();
	}

	@Override
	public void setContentView(View view, LayoutParams params) 
	{
		super.setContentView(view, params);
		this.isContentViewSet = true;
		initButtons();
	}

	@Override
	public void setContentView(View view) 
	{
		super.setContentView(view);
		this.isContentViewSet = true;
		initButtons();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
	}

	@Override
	protected void onStart() 
	{
		super.onStart();
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
	}

	protected boolean isCallable(Intent intent) 
	{
		List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	
	/**
	 * Shows or hides the navigation toolbar
	 * @param visibility
	 */
	public void SetMenuVisibility(int visibility)
	{
		llMainMenu.setVisibility(visibility);
		llMainMenu.refreshDrawableState();
	}
	
	/**
	 * Initializes buttons on the main navigation toolbar
	 */
	protected void initButtons() 
	{

		try
		{
		llMainMenu = (LinearLayout)this.findViewById(R.id.llMainMenu);

		btnMainDashboard = (ToggledImageButton)this.findViewById(R.id.btnMainDashboard);
		btnMainDashboard.setOnClickListener(this);
		btnMainDashboard.onResource = R.drawable.dashboardon;
		btnMainDashboard.offResource = R.drawable.dashboardoff;
		btnMainDashboard.setChecked(false);

		btnMainTools = (ToggledImageButton)this.findViewById(R.id.btnMainTools);
		btnMainTools.setOnClickListener(this);
		btnMainTools.offResource = R.drawable.toolsoff;
		btnMainTools.onResource = R.drawable.toolson;
		btnMainTools.setChecked(false);
		
		btnMainCards = (ToggledImageButton)this.findViewById(R.id.btnMainCards);
		btnMainCards.setOnClickListener(this);
		btnMainCards.offResource = R.drawable.cardsoff;
		btnMainCards.onResource = R.drawable.cardson;
		btnMainCards.setChecked(false);
		
		btnMainAbout = (ToggledImageButton)this.findViewById(R.id.btnMainAbout);
		btnMainAbout.setOnClickListener(this);
		btnMainAbout.offResource = R.drawable.aboutoff;
		btnMainAbout.onResource = R.drawable.abouton;
		btnMainAbout.setChecked(false);
		
		btnMainSettings = (ToggledImageButton)this.findViewById(R.id.btnMainSettings);
		btnMainSettings.setOnClickListener(this);
		btnMainSettings.offResource = R.drawable.settingsoff;
		btnMainSettings.onResource = R.drawable.settingson;
		btnMainSettings.setChecked(false);
		}
		catch(Exception ex){}
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) 
	{
		switch(v.getId()) 
		{
		case R.id.btnMainDashboard:
			this.startActivity(ActivityFactory.getHomeActivity(this));
			if((this instanceof HomeActivity == false))
				this.finish();
			break;
		case R.id.btnMainTools:
			this.startActivity(ActivityFactory.getToolsActivity(this));
			if((this instanceof HomeActivity == false))
				this.finish();
			break;
		case R.id.btnMainCards:
			this.startActivity(ActivityFactory.getCardsActivity(this));
			if((this instanceof HomeActivity == false))
				this.finish();
			break;
		case R.id.btnMainAbout:
			this.startActivity(ActivityFactory.getAboutActivity(this));
			if((this instanceof HomeActivity == false))
				this.finish();
			break;
		case R.id.btnMainSettings:
			this.startActivity(ActivityFactory.getSettingsActivity(this));
			if((this instanceof HomeActivity == false))
				this.finish();
			break;
		}
	}
}