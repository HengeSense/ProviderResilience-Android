package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.AccessibleWebView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;

/**
 * Shows a browser based information screen
 * @author stephenody
 *
 */
public class AboutActivity extends ABSActivity
{

	public AccessibleWebView awv;
	
	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		this.SetMenuVisibility(1);
		this.btnMainAbout.setChecked(true);
		
		awv = (AccessibleWebView) this.findViewById(R.id.webview);
		
		
		
		awv.setWebChromeClient(new WebChromeClient());
		awv.setBackgroundColor(Color.TRANSPARENT);
		awv.setVerticalScrollBarEnabled(true);
		WebSettings settings = awv.getSettings();
		settings.setJavaScriptEnabled(true);
		awv.loadData(getString(R.string.about_content), "text/html", "utf-8");
		awv.setVisibility(View.VISIBLE);

		
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onStart()
	 */
	@Override
	public void onStart()
	{

		super.onStart();
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{
		
		}
	}
	
}
