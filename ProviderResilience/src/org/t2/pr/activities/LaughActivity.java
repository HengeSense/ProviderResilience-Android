/*
 * 
 */
package org.t2.pr.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.t2.pr.R;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.SharedPref;

import ImageViewTouch.ImageViewTouch;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class LaughActivity.
 */
public class LaughActivity extends ABSActivity implements OnClickListener
{
	
	/** The awv. */
	public ImageViewTouch awv;
	
	/** The dilbert array. */
	ArrayList<DilbertObject> dilbertArray;
	
	/** The dilbert index. */
	private int dilbertIndex = 0;
	
	/** The btn_prev. */
	private Button btn_prev;
	
	/** The btn_next. */
	private Button btn_next;
	
	/** The db. */
	private DatabaseProvider db = new DatabaseProvider(this);
	
	/** The bmp dilbert. */
	private Bitmap bmpDilbert;
	
	/** The m_ progress dialog. */
	private ProgressDialog m_ProgressDialog = null;
	
	/** The tv title. */
	private TextView tvTitle;
	
	/** The comic title. */
	private String comicTitle = "";

	/** The Constant base64Array. */
	final static char base64Array [] = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
		'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		'w', 'x', 'y', 'z', '0', '1', '2', '3',
		'4', '5', '6', '7', '8', '9', '+', '/'
	};		

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.laugh);

		this.SetMenuVisibility(1);
		this.btnMainTools.setChecked(true);

		awv = (ImageViewTouch) this.findViewById(R.id.image);
		tvTitle = (TextView) this.findViewById(R.id.tv_title);

		btn_prev = (Button)this.findViewById(R.id.btn_prev);
		btn_prev.setOnClickListener(this);

		btn_next = (Button)this.findViewById(R.id.btn_next);
		btn_next.setOnClickListener(this);

		String date = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
		db.insertMisc("laugh", 1, date);

	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onStart()
	 */
	@Override
	public void onStart()
	{

		super.onStart();

		m_ProgressDialog = new ProgressDialog(this);
		m_ProgressDialog.setTitle("Please wait...");
		m_ProgressDialog.setIndeterminate(true);
		m_ProgressDialog.setMessage("Loading comic.");
		//m_ProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		m_ProgressDialog.show();
		QueryDilbertRSS();

	}

	/**
	 * Query dilbert rss.
	 */
	public void QueryDilbertRSS()
	{

		Runnable myRunnable = new Runnable() 
		{
			public void run() 
			{
				URL url = null;
				try {
					url = new URL("https://feedsservice.amuniversal.com/feeds/feature/dt.json");
				} catch (MalformedURLException e1) {}

				// Create a trust manager that does not validate certificate chains
				TrustManager[] trustAllCerts = new TrustManager[]
						{
						new X509TrustManager() 
						{
							public java.security.cert.X509Certificate[] getAcceptedIssuers() 
							{
								return null;
							}
							public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
							public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
						}
						};

				// Install the all-trusting trust manager
				try 
				{
					SSLContext sc = SSLContext.getInstance("SSL");
					sc.init(null, trustAllCerts, new java.security.SecureRandom());
					HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				} 
				catch (Exception e) {
					Log.v("ssl","");
				}


				try
				{
					URLConnection connection = url.openConnection();
					connection.setRequestProperty("Authorization", "Basic " + base64Encode("nct_feeds" + ":" + "!nctele"));


					HttpsURLConnection httpConnection = (HttpsURLConnection)connection;
					httpConnection.setRequestMethod("GET");
					httpConnection.setReadTimeout(10000);

					int responseCode = httpConnection.getResponseCode();

					if(responseCode == HttpURLConnection.HTTP_OK)
					{
						InputStream in = httpConnection.getInputStream();

						BufferedReader r = new BufferedReader(new InputStreamReader(in));
						StringBuilder sb = new StringBuilder();
						String s = null;
						while ((s = r.readLine()) != null) {
							sb.append(s);
						}

						dilbertArray = parseJSON(sb);
						runOnUiThread(GetDilbertRunnable);

					}
					if(responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT)
					{
						Log.v("HTTP_CLIENT_TIMEOUT","");
					}
				}
				catch(Exception ex){
					runOnUiThread(ErrorMsgRunnable);

				}
			}
		};

		Thread thread = new Thread(null, myRunnable, "ActivitiesThread");
		thread.start();
	}

	/** The Show dilbert runnable. */
	private Runnable ShowDilbertRunnable = new Runnable() {
		public void run() {
			ShowDilbert() ;
		}
	};

	/** The Get dilbert runnable. */
	private Runnable GetDilbertRunnable = new Runnable() {
		public void run() {
			GetDilbertComic() ;
			runOnUiThread(ShowDilbertRunnable);
		}
	};

	/**
	 * Parses the json.
	 *
	 * @param sb the sb
	 * @return the array list
	 */
	public ArrayList<DilbertObject> parseJSON(StringBuilder sb)
	{
		ArrayList<DilbertObject> markers = new ArrayList<DilbertObject>();

		try
		{
			JSONObject root;
			root = new JSONObject(sb.toString());

			JSONObject jsonEntry = root.getJSONObject("feature");
			if (jsonEntry != null) {

				JSONArray dataArray = null;
				JSONArray assetArray = null;

				dataArray = jsonEntry.getJSONArray("feature_items");
				if (dataArray == null)
					return null;
				int top = dataArray.length();
				for (int i = 0; i < top; i++) 
				{
					JSONObject jo = dataArray.getJSONObject(i);
					assetArray = jo.getJSONArray("assets");
					if (assetArray == null)
						return null;
					int atop = assetArray.length();
					for (int a = 0; a < atop; a++) 
					{
						JSONObject jo2 = assetArray.getJSONObject(a);
						if(jo2.getString("asset_type").trim().equals("mutable"))
						{
							if(jo2.getString("image_coloration").trim().equals("color"))
							{
								DilbertObject ma = new DilbertObject();
								ma.imageURL = jo2.getString("image_link");// + "/" + jo2.getString("filename");
								String[] dsplit = jo.getString("date").split("[T]");
								SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 
								Date dateObj = curFormater.parse(dsplit[0]); 
								SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy"); 
								String newDateStr = postFormater.format(dateObj); 
								ma.dateTime = newDateStr;
								markers.add(ma);
							}
						}
					}

				}
			}

		}
		catch(Exception ex){
			Log.v("parseJSON", ex.toString());
			if(m_ProgressDialog!=null)
				m_ProgressDialog.dismiss();
		}

		return markers;
	}

	/**
	 * Gets the dilbert comic.
	 */
	public void GetDilbertComic()
	{
		DilbertObject curDilbert = dilbertArray.get(dilbertIndex);
		comicTitle = curDilbert.dateTime;
		bmpDilbert = getBitmapFromURL(curDilbert.imageURL, false);
	}

	/**
	 * Show dilbert.
	 */
	public void ShowDilbert()
	{

		//Set date on title
		tvTitle.setText("Dilbert Comic Strip - " + comicTitle);

		// Get current dimensions AND the desired bounding box
		int width = bmpDilbert.getWidth();
		int height = bmpDilbert.getHeight();
		int boundingx = awv.getWidth() - 20;//dpToPx(awv.getWidth());
		int boundingy = awv.getHeight() - 20;//dpToPx(awv.getHeight());


		// Determine how much to scale: the dimension requiring less scaling is
		// closer to the its side. This way the image always stays inside your
		// bounding box AND either x/y axis touches it.  
		float xScale = ((float) boundingx) / width;
		float yScale = ((float) boundingy) / height;
		float scale = (xScale <= yScale) ? yScale : xScale;


		// Create a matrix for the scaling and add the scaling data
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);

		// Create a new bitmap and convert it to a format understood by the ImageView 
		Bitmap scaledBitmap = Bitmap.createBitmap(bmpDilbert, 0, 0, width, height, matrix, false);
		width = scaledBitmap.getWidth(); // re-use
		height = scaledBitmap.getHeight(); // re-use

		awv.setImageBitmap(bmpDilbert);
		awv.fakeInitialZoom();
		if(m_ProgressDialog!=null)
			m_ProgressDialog.dismiss();

	}

	/**
	 * Base64 encode.
	 *
	 * @param string the string
	 * @return the string
	 */
	private static String base64Encode (String string)    {
		String encodedString = "";
		byte bytes [] = string.getBytes ();
		int i = 0;
		int pad = 0;
		while (i < bytes.length) {
			byte b1 = bytes [i++];
			byte b2;
			byte b3;
			if (i >= bytes.length) {
				b2 = 0;
				b3 = 0;
				pad = 2;
			}
			else {
				b2 = bytes [i++];
				if (i >= bytes.length) {
					b3 = 0;
					pad = 1;
				}
				else
					b3 = bytes [i++];
			}
			byte c1 = (byte)(b1 >> 2);
			byte c2 = (byte)(((b1 & 0x3) << 4) | (b2 >> 4));
			byte c3 = (byte)(((b2 & 0xf) << 2) | (b3 >> 6));
			byte c4 = (byte)(b3 & 0x3f);
			encodedString += base64Array [c1];
			encodedString += base64Array [c2];
			switch (pad) {
			case 0:
				encodedString += base64Array [c3];
				encodedString += base64Array [c4];
				break;
			case 1:
				encodedString += base64Array [c3];
				encodedString += "=";
				break;
			case 2:
				encodedString += "==";
				break;
			}
		}
		return encodedString;
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
		case R.id.btn_prev:
			try
			{
				dilbertIndex--;
				if(dilbertIndex < 0)
					dilbertIndex=dilbertArray.size()-1;
				m_ProgressDialog.show();
				Thread thread = new Thread(null, GetDilbertRunnable, "DilbertThread");
				thread.start();
			}catch(Exception ex){}
			break;
		case R.id.btn_next:
			try
			{
				dilbertIndex++;
				if(dilbertIndex >= dilbertArray.size())
					dilbertIndex = 0;
				m_ProgressDialog.show();
				Thread thread = new Thread(null, GetDilbertRunnable, "DilbertThread");
				thread.start();
			}catch(Exception ex){}
			break;
		}
	}

	/**
	 * Gets the bitmap from url.
	 *
	 * @param src the src
	 * @param secure the secure
	 * @return the bitmap from url
	 */
	public Bitmap getBitmapFromURL(String src, boolean secure) {

		if(secure)
		{
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[]
					{
					new X509TrustManager() 
					{
						public java.security.cert.X509Certificate[] getAcceptedIssuers() 
						{
							return null;
						}
						public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
						public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
					}
					};

			// Install the all-trusting trust manager
			try 
			{
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} 
			catch (Exception e) {
				ErrorMsg();
			}

			try
			{
				URL url = new URL(src);
				URLConnection connection = url.openConnection();
				connection.setRequestProperty("Authorization", "Basic " + base64Encode("nct_feeds" + ":" + "!nctele"));

				HttpsURLConnection httpConnection = (HttpsURLConnection)connection;
				httpConnection.setRequestMethod("GET");

				int responseCode = httpConnection.getResponseCode();

				if(responseCode == HttpURLConnection.HTTP_OK)
				{
					InputStream in = httpConnection.getInputStream();

					Bitmap myBitmap = BitmapFactory.decodeStream(in);
					return myBitmap;

				}
			}
			catch(Exception ex){
				Log.v("except", ex.toString());
				ErrorMsg();
				return null;
			}

		}
		else
		{
			try {
				URL url = new URL(src);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				return myBitmap;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	/**
	 * The Class DilbertObject.
	 */
	public class DilbertObject
	{
		
		/** The date time. */
		public String dateTime = "";
		
		/** The image url. */
		public String imageURL = "";
	}

	/** The Error msg runnable. */
	private Runnable ErrorMsgRunnable = new Runnable() {
		public void run() {
			ErrorMsg();
		}
	};
	
	/**
	 * Error msg.
	 */
	public void ErrorMsg()
	{

		m_ProgressDialog.cancel();
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle("Error");
		myAlertDialog.setMessage("Please check your network connectivity.");
		myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}});

		myAlertDialog.show();
	}

}
