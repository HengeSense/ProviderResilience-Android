package org.t2.pr.activities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.t2.pr.R;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;
import org.t2.pr.saxrssreader.RssFeed;
import org.t2.pr.saxrssreader.RssItem;
import org.t2.pr.saxrssreader.RssReader;
import org.xml.sax.SAXException;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class LaughActivity extends ABSActivity implements OnClickListener
{
	ArrayList<RssItem> rssItems;
	public WebView awv;
	private int dilbertIndex = 0;
	private Button btn_prev;
	private Button btn_next;
	private DatabaseProvider db = new DatabaseProvider(this);

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.laugh);

		this.SetMenuVisibility(1);
		this.btnMainTools.setChecked(true);

		awv = (WebView) this.findViewById(R.id.webview);
		awv.setBackgroundColor(Color.TRANSPARENT); 

		String outHTML = "<html><body><center><font color=#ffffff><BR><BR><BR>Loading, please wait...</center></body></html>";
		//awv.loadData(outHTML, "text/html", "utf-8");
		awv.loadDataWithBaseURL("fake-url", "<html></html>", "text/html", "UTF-8", null);
		awv.loadData(outHTML, "text/html", "UTF-8");

		btn_prev = (Button)this.findViewById(R.id.btn_prev);
		btn_prev.setOnClickListener(this);

		btn_next = (Button)this.findViewById(R.id.btn_next);
		btn_next.setOnClickListener(this);
		
		String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date());
		db.insertMisc("laugh", 1, answerDate);

	}

	@Override
	public void onStart()
	{

		super.onStart();
		QueryDilbertRSS();

	}

	public void QueryDilbertRSS()
	{

		Runnable myRunnable = new Runnable() 
		{
			public void run() 
			{
				URL url = null;
				try {url = new URL("http://feed.dilbert.com/dilbert/daily_strip?format=xml");} catch (MalformedURLException e) {}
				RssFeed feed = null;
				try {
					feed = RssReader.read(url);
					rssItems = feed.getRssItems();
				} catch (SAXException e) {} catch (IOException e) {}

				runOnUiThread(ShowDilbertRunnable);
			}
		};

		Thread thread = new Thread(null, myRunnable, "ActivitiesThread");
		thread.start();
	}

	private Runnable ShowDilbertRunnable = new Runnable() {
		public void run() {
			ShowDilbert() ;
		}
	};

	public void ShowDilbert()
	{

		try
		{
			if(rssItems.size() > 0)
			{
				String outHTML = "<html><body>";
				outHTML += "<font color=#ffffff><center><b>Dilbert Comic Strip</b><BR><BR></center>" + rssItems.get(dilbertIndex).getTitle() +"<BR>";
				Global.Log.v("RSS Reader", rssItems.get(dilbertIndex).getDescription());
				outHTML += rssItems.get(dilbertIndex).getDescription();

				outHTML += "</body></html>";

				awv.loadDataWithBaseURL("fake-url", "<html></html>", "text/html", "UTF-8", null);
				awv.loadData(outHTML, "text/html", "UTF-8");
			}
			else
				ShowError();
		}
		catch(Exception ex){

			ShowError();
		}
	}

	public void ShowError()
	{
		String outHTML = "<html><body><center><font color=#ffffff><BR><BR><BR>Error loading data...<BR>Please check your network connection.</center></body></html>";
		//awv.loadData(outHTML, "text/html", "utf-8");
		awv.loadDataWithBaseURL("fake-url", "<html></html>", "text/html", "UTF-8", null);
		awv.loadData(outHTML, "text/html", "UTF-8");
	}

	@Override
	public void onClick(View v) 
	{
		super.onClick(v);
		switch(v.getId()) 
		{
		case R.id.btn_prev:
			try
			{
			dilbertIndex--;
			if(dilbertIndex < 0)
				dilbertIndex=rssItems.size()-1;
			ShowDilbert();
			}catch(Exception ex){}
			break;
		case R.id.btn_next:
			try
			{
			dilbertIndex++;
			if(dilbertIndex >= rssItems.size())
				dilbertIndex = 0;
			ShowDilbert();
			}catch(Exception ex){}
			break;
		}
	}
}
