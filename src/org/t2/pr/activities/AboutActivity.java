package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.AccessibleWebView;

import uk.co.jasonfry.android.tools.ui.PageControl;
import uk.co.jasonfry.android.tools.ui.SwipeView;
import uk.co.jasonfry.android.tools.ui.SwipeView.OnPageChangedListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Shows a browser based information screen
 * @author stephenody
 *
 */
public class AboutActivity extends ABSActivity
{
	boolean shownav = true;
	SwipeView mSwipeView;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.walkthrough);

		Intent intent = this.getIntent();
		shownav = intent.getBooleanExtra("shownav", true);
		
		PageControl mPageControl = (PageControl) findViewById(R.id.page_control);
		mSwipeView = (SwipeView) findViewById(R.id.swipe_view);

		//Add the views
		View view = getLayoutInflater().inflate(R.layout.aboutimage, null);
		LinearLayout ll = (LinearLayout)view.findViewById(R.id.container);
		AccessibleWebView awv = new AccessibleWebView(this);
		awv.setWebChromeClient(new WebChromeClient());
		awv.setBackgroundColor(Color.TRANSPARENT);
		awv.setVerticalScrollBarEnabled(true);
		WebSettings settings = awv.getSettings();
		settings.setJavaScriptEnabled(true);
		awv.loadDataWithBaseURL("fake:/blah", getString(R.string.about_content), "text/html", "utf-8", null);
		//awv.loadData(getString(R.string.about_content), "text/html", "utf-8");
		awv.setVisibility(View.VISIBLE);
		ll.addView(awv);
		mSwipeView.addView(view);
		
		View view2 = getLayoutInflater().inflate(R.layout.about, null);
		LinearLayout ll2 = (LinearLayout)view2.findViewById(R.id.container);
		ImageView v = new ImageView(this);
		v.setImageResource(R.drawable.aboutcard1);
		ll2.addView(v);
		mSwipeView.addView(view2);

		View view3 = getLayoutInflater().inflate(R.layout.about, null);
		LinearLayout ll3 = (LinearLayout)view3.findViewById(R.id.container);
		ImageView v2 = new ImageView(this);
		v2.setImageResource(R.drawable.aboutcard2);
		ll3.addView(v2);
		mSwipeView.addView(view3);

		mSwipeView.setPageControl(mPageControl);

		if(shownav)
		{
			this.SetMenuVisibility(View.VISIBLE);
			this.btnMainAbout.setChecked(true);
		}
		else
			this.SetMenuVisibility(View.GONE);
		
		Toast.makeText(this, "Swipe left/right to view additional instructions.", Toast.LENGTH_SHORT).show();
	}

	
}
