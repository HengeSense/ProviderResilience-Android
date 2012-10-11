/*
 * 
 */
package org.t2.pr.activities;

import java.util.Calendar;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.SharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// TODO: Auto-generated Javadoc
/**
 * Displays an EULA screen.
 */
public class EulaActivity extends ABSWebViewActivity 
{
	
	/** The btn accept. */
	Button btnAccept;
	
	/** The btn deny. */
	Button btnDeny;

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSWebViewActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		btnAccept = (Button) this.findViewById(R.id.leftButton);
		btnAccept.setText(getString(R.string.eula_accept));
		btnAccept.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				AcceptPressed();
			}
		});

		btnDeny = (Button) this.findViewById(R.id.rightButton);
		btnDeny.setText("Decline");
		btnDeny.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				DeclinePressed();
			}
		});

		this.setTitle(getString(R.string.eula_title));
		this.setContent(getString(R.string.eula_content));
	}

	/**
	 * Accept pressed.
	 */
	public void AcceptPressed() 
	{
		SharedPref.setIsEulaAccepted(true);
		this.startActivity(ActivityFactory.getHomeActivity(this));
		Intent intent = ActivityFactory.getAboutActivity(this);
		intent.putExtra("shownav", false);
		this.startActivity(intent);
		this.finish();
	}

	/**
	 * Decline pressed.
	 */
	public void DeclinePressed() 
	{
		this.setResult(0);
		this.finish();
	}

}
