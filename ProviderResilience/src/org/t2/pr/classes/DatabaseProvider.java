/*
 * 
 */
package org.t2.pr.classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * Handles all database operations.
 *
 * @author Steve Ody (stephen.ody@tee2.org)
 */

public class DatabaseProvider 
{

	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "nrproviderres.db";
	
	/** The Constant DATABASE_VERSION. */
	private static final int DATABASE_VERSION = 7;

	/** The context. */
	private Context context;
	
	/** The db. */
	private SQLiteDatabase db;

	/**
	 * Instantiates a new database provider.
	 *
	 * @param context the context
	 */
	public DatabaseProvider(Context context) 
	{
		this.context = context;      
	}

	/**
	 * Scrub input.
	 *
	 * @param input the input
	 * @return the string
	 */
	public String scrubInput(String input)
	{
		//TODO: make robust
		String Output = input.replace("'", "''");
		return Output;
	}

	/**
	 * Select rb questions.
	 *
	 * @return the list
	 */
	public List<String[]> selectRBQuestions()
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		String query = "select rbid,rbQuestion from ResilienceBuilders order by rbid";

		List<String[]> list = new ArrayList<String[]>();

		Cursor cursor = this.db.rawQuery(query, null);
		if (cursor.moveToFirst()) 
		{
			do 
			{
				String[] row = new String[2];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				list.add(row); 
			} 
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) 
		{
			cursor.close();
		}

		db.close();

		return list;
	}

	/**
	 * Insert rb question.
	 *
	 * @param inQuestion the in question
	 */
	public void insertRBQuestion(String inQuestion)
	{
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();

		//Only allow one custom RB
		db.execSQL("delete from ResilienceBuilders where custom = 1");

		try
		{
			db.execSQL("insert into ResilienceBuilders (rbQuestion, custom) values('" + scrubInput(inQuestion.trim()) + "', 1)");
		}
		catch(Exception ex)
		{

		}

		db.close();
	}

	/**
	 * Gets the start date.
	 *
	 * @param inDate the in date
	 * @return the start date
	 */
	public String getStartDate(String inDate)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new java.util.Date(inDate));
		} catch (Exception e) {
			Log.v("PARSEERROR!", e.toString());
		}
		
		if(c.get(Calendar.HOUR_OF_DAY) <  SharedPref.getResetHour())
		{
			c.add(Calendar.DAY_OF_YEAR, -1);  
			c.set(Calendar.HOUR_OF_DAY, SharedPref.getResetHour());
			c.set(Calendar.MINUTE, SharedPref.getResetMinute());
		}
		else if(c.get(Calendar.HOUR_OF_DAY) ==  SharedPref.getResetHour())
		{
			if(c.get(Calendar.MINUTE) <  SharedPref.getResetMinute())
			{
				c.add(Calendar.DAY_OF_YEAR, -1);  
				c.set(Calendar.HOUR_OF_DAY, SharedPref.getResetHour());
				c.set(Calendar.MINUTE, SharedPref.getResetMinute());
			}
			else
			{
				c.set(Calendar.HOUR_OF_DAY, SharedPref.getResetHour());
				c.set(Calendar.MINUTE, SharedPref.getResetMinute());
			}
		}
		else
		{
			c.set(Calendar.HOUR_OF_DAY, SharedPref.getResetHour());
			c.set(Calendar.MINUTE, SharedPref.getResetMinute());
		}
		
		
		
		return dateFormat.format(c.getTime());
		
	}
	
	/**
	 * Gets the end date.
	 *
	 * @param inDate the in date
	 * @return the end date
	 */
	public String getEndDate(String inDate)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new java.util.Date(inDate));
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		if(c.get(Calendar.HOUR_OF_DAY) <  SharedPref.getResetHour())
		{
			c.set(Calendar.HOUR_OF_DAY, SharedPref.getResetHour());
			c.set(Calendar.MINUTE, SharedPref.getResetMinute());
		}
		else if(c.get(Calendar.HOUR_OF_DAY) ==  SharedPref.getResetHour())
		{
			if(c.get(Calendar.MINUTE) <  SharedPref.getResetMinute())
			{
				c.set(Calendar.HOUR_OF_DAY, SharedPref.getResetHour());
				c.set(Calendar.MINUTE, SharedPref.getResetMinute());
			}
			else
			{
				c.add(Calendar.DAY_OF_YEAR, 1);  
				c.set(Calendar.HOUR_OF_DAY, SharedPref.getResetHour());
				c.set(Calendar.MINUTE, SharedPref.getResetMinute());
			}
		}
		else
		{
			c.add(Calendar.DAY_OF_YEAR, 1);  
			c.set(Calendar.HOUR_OF_DAY, SharedPref.getResetHour());
			c.set(Calendar.MINUTE, SharedPref.getResetMinute());
		}
		
		return dateFormat.format(c.getTime());
		
	}
	
	/**
	 * Insert rb answers.
	 *
	 * @param inAnswers the in answers
	 * @param answerDate the answer date
	 */
	public void insertRBAnswers(List<int[]> inAnswers, String answerDate)
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		
		//Only allow one set of answers per day
		db.execSQL("delete from RBAnswers where answerDate >= '" + getStartDate(answerDate) + "' and answerdate <= '" + getEndDate(answerDate) + "'");

		int aLen = inAnswers.size();
		for(int a=0; a< aLen; a++)
		{
			int[] answer = inAnswers.get(a);
			try
			{
				String insSQL = "insert into RBAnswers (rbQuestion, rbAnswer, answerDate) values(" + answer[0] + "," + answer[1] + ",'" + answerDate + "')";
				db.execSQL(insSQL);
			}
			catch(Exception ex)
			{

			}
		}

		db.close();

	}

	/**
	 * Select rk questions.
	 *
	 * @return the list
	 */
	public List<String[]> selectRKQuestions()
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		String query = "select rkid,rkQuestion from ResilienceKillers order by rkid";

		List<String[]> list = new ArrayList<String[]>();

		Cursor cursor = this.db.rawQuery(query, null);
		if (cursor.moveToFirst()) 
		{
			do 
			{
				String[] row = new String[2];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				list.add(row); 
			} 
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) 
		{
			cursor.close();
		}

		db.close();

		return list;
	}

	/**
	 * Insert rk question.
	 *
	 * @param inQuestion the in question
	 */
	public void insertRKQuestion(String inQuestion)
	{
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();

		//Only allow one custom RB
		db.execSQL("delete from ResilienceKillers where custom = 1");

		try
		{
			db.execSQL("insert into ResilienceKillers (rkQuestion, custom) values('" + scrubInput(inQuestion.trim()) + "', 1)");
		}
		catch(Exception ex)
		{

		}

		db.close();
	}

	/**
	 * Insert rk answers.
	 *
	 * @param inAnswers the in answers
	 * @param answerDate the answer date
	 */
	public void insertRKAnswers(List<int[]> inAnswers, String answerDate)
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();

		//Only allow one set of answers per day
		db.execSQL("delete from RKAnswers where answerDate >= '" + getStartDate(answerDate) + "' and answerdate <= '" + getEndDate(answerDate) + "'");

		int aLen = inAnswers.size();
		for(int a=0; a< aLen; a++)
		{
			int[] answer = inAnswers.get(a);
			try
			{
				String insSQL = "insert into RKAnswers (rkQuestion, rkAnswer, answerDate) values(" + answer[0] + "," + answer[1] + ",'" + answerDate + "')";
				db.execSQL(insSQL);
			}
			catch(Exception ex)
			{

			}
		}

		db.close();

	}

	/**
	 * Select qol questions.
	 *
	 * @return the list
	 */
	public List<String[]> selectQOLQuestions()
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		String query = "select qolid,qolQuestion from PROQOL order by qolid";

		List<String[]> list = new ArrayList<String[]>();

		Cursor cursor = this.db.rawQuery(query, null);
		if (cursor.moveToFirst()) 
		{
			do 
			{
				String[] row = new String[2];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				list.add(row); 
			} 
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) 
		{
			cursor.close();
		}

		db.close();

		return list;
	}

	/**
	 * Insert qol answers.
	 *
	 * @param inAnswers the in answers
	 * @param answerDate the answer date
	 */
	public void insertQOLAnswers(List<int[]> inAnswers, String answerDate)
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();

		//Only allow one set of answers per day
		db.execSQL("delete from QOLAnswers where answerDate >= '" + getStartDate(answerDate) + "' and answerdate <= '" + getEndDate(answerDate) + "'");

		int aLen = inAnswers.size();
		for(int a=0; a< aLen; a++)
		{
			int[] answer = inAnswers.get(a);
			try
			{
				String insSQL = "insert into QOLAnswers (QOLQuestion, QOLAnswer, answerDate) values(" + answer[0] + "," + answer[1] + ",'" + answerDate + "')";
				db.execSQL(insSQL);
			}
			catch(Exception ex)
			{

			}
		}

		db.close();

	}

	/**
	 * Select burnout questions.
	 *
	 * @return the list
	 */
	public List<String[]> selectBurnoutQuestions()
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		String query = "select bid,bQuestion from Burnout order by bid";

		List<String[]> list = new ArrayList<String[]>();

		Cursor cursor = this.db.rawQuery(query, null);
		if (cursor.moveToFirst()) 
		{
			do 
			{
				String[] row = new String[2];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				list.add(row); 
			} 
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) 
		{
			cursor.close();
		}

		db.close();

		return list;
	}

	/**
	 * Select burnout score.
	 *
	 * @param answerDate the answer date
	 * @return the int
	 */
	public int selectBurnoutScore(String answerDate)
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		String query = "select banswer from BurnoutAnswers where answerDate >= '" + getStartDate(answerDate) + "' and answerdate <= '" + getEndDate(answerDate) + "'";
		//Log.v("query", query);
		
		int scoreTotal = 0;

		try
		{
			Cursor cursor = this.db.rawQuery(query, null);
			if (cursor.moveToFirst()) 
			{
				do 
				{
					scoreTotal += cursor.getInt(0);
				} 
				while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed()) 
			{
				cursor.close();
			}
		}catch(Exception ex){}

		db.close();

		return scoreTotal;
	}

	/**
	 * Insert burnout answers.
	 *
	 * @param inAnswers the in answers
	 * @param answerDate the answer date
	 */
	public void insertBurnoutAnswers(List<int[]> inAnswers, String answerDate)
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();

		//Only allow one set of answers per day
		db.execSQL("delete from BurnoutAnswers where answerDate >= '" + getStartDate(answerDate) + "' and answerdate <= '" + getEndDate(answerDate) + "'");

		int aLen = inAnswers.size();
		for(int a=0; a< aLen; a++)
		{
			int[] answer = inAnswers.get(a);
			try
			{
				String insSQL = "insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(" + answer[0] + "," + answer[1] + ",'" + answerDate + "')";
				db.execSQL(insSQL);
			}
			catch(Exception ex)
			{

			}
		}

		db.close();

	}

	/**
	 * Select burnout dates.
	 *
	 * @return the list
	 */
	public List<String> selectBURNOUTDates()
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		String query = "select answerDate from BurnoutAnswers order by bID asc";

		List<String> list = new ArrayList<String>();

		Cursor cursor = this.db.rawQuery(query, null);
		if (cursor.moveToFirst()) 
		{
			do 
			{
				
				//String date = (String) android.text.format.DateFormat.format("MM/dd/yyyy", Date.parse(cursor.getString(0)));
				
				if(!list.contains(cursor.getString(0)))
					list.add(cursor.getString(0)); 
				
			} 
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) 
		{
			cursor.close();
		}

		db.close();

		return list;
	}
	
	/**
	 * Select builders killers score.
	 *
	 * @param answerDate the answer date
	 * @return the int
	 */
	public int selectBuildersKillersScore(String answerDate)
	{
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();

		//boolean foundKiller = false;
		
		String bquery = "select rbanswer from RBAnswers where answerDate >= '" + getStartDate(answerDate) + "' and answerdate <= '" + getEndDate(answerDate) + "'";
		String kquery = "select rkanswer from RKAnswers where answerDate >= '" + getStartDate(answerDate) + "' and answerdate <= '" + getEndDate(answerDate) + "'";

		int scoreTotal = 0;

		

		
		
		//if(foundKiller) scoreTotal -= 5;
		
		try
		{
			Cursor bcursor = this.db.rawQuery(bquery, null);

			//Six points for first answer, 1 point each additional
			//if(bcursor.getCount() > 0)
			//	scoreTotal += 5;

			if (bcursor.moveToFirst()) 
			{
				do 
				{
					if(bcursor.getInt(0) == 1)
					{
						if(scoreTotal == 0)
							scoreTotal = 5;
						scoreTotal++;
					}
				} 
				while (bcursor.moveToNext());
			}
			if (bcursor != null && !bcursor.isClosed()) 
			{
				bcursor.close();
			}
		}catch(Exception ex){}

		try
		{
			Cursor kcursor = this.db.rawQuery(kquery, null);

			//if any killer, subtract 5 pts
			if (kcursor.moveToFirst()) 
			{
				do 
				{
					if(kcursor.getInt(0) == 1)
						scoreTotal -= 5;
						//foundKiller = true;
				} 
				while (kcursor.moveToNext());
			}
			if (kcursor != null && !kcursor.isClosed()) 
			{
				kcursor.close();
			}
		}catch(Exception ex){}
		
		db.close();

		if(scoreTotal < -10) scoreTotal = -10;
		if(scoreTotal > 10) scoreTotal = 10;
		
		return scoreTotal;
	}

	/**
	 * Select qol answers.
	 *
	 * @param answerDate the answer date
	 * @return the list
	 */
	public List<String[]> selectQOLAnswers(String answerDate)
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		String query = "select a.qolAnswer, q.custom from QOLAnswers a join PROQOL q on q.qolID = a.qolQuestion where a.answerDate = '" + answerDate + "' order by q.custom";

		List<String[]> list = new ArrayList<String[]>();

		Cursor cursor = this.db.rawQuery(query, null);
		if (cursor.moveToFirst()) 
		{
			do 
			{
				String[] row = new String[2];
				row[0] = cursor.getString(1); //Question number
				row[1] = cursor.getString(0); //Answer value
				list.add(row); 
			} 
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) 
		{
			cursor.close();
		}

		db.close();

		return list;
	}

	/**
	 * Select qol dates.
	 *
	 * @return the list
	 */
	public List<String> selectQOLDates()
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		String query = "select answerDate from QOLAnswers order by qolaID asc";

		List<String> list = new ArrayList<String>();

		Cursor cursor = this.db.rawQuery(query, null);
		if (cursor.moveToFirst()) 
		{
			do 
			{
				if(!list.contains(cursor.getString(0)))
				list.add(cursor.getString(0)); 
			} 
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) 
		{
			cursor.close();
		}

		db.close();

		return list;
	}

	/**
	 * Clear data.
	 */
	public void ClearData()
	{
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		db.execSQL("delete from burnoutanswers");
		db.execSQL("delete from QOLAnswers");
		db.execSQL("delete from RKAnswers");
		db.execSQL("delete from RBAnswers");
		db.execSQL("delete from MiscData");
		db.close();
	}

	/**
	 * Enter test data.
	 */
	public void EnterTestData()
	{
		Random rnd = new Random();
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		for(int i = 1; i < 20;i++)
		{
			db.execSQL("insert into RBAnswers (rbQuestion, rbAnswer, answerDate) values(0,1,'4/" + rnd.nextInt(1) + "/2012')");
			db.execSQL("insert into RBAnswers (rbQuestion, rbAnswer, answerDate) values(0,2,'4/" + rnd.nextInt(1) + "/2012')");
			db.execSQL("insert into RBAnswers (rbQuestion, rbAnswer, answerDate) values(0,3,'4/" + rnd.nextInt(1) + "/2012')");
			db.execSQL("insert into RBAnswers (rbQuestion, rbAnswer, answerDate) values(0,4,'4/" + rnd.nextInt(1) + "/2012')");
			db.execSQL("insert into RBAnswers (rbQuestion, rbAnswer, answerDate) values(0,5,'4/" + rnd.nextInt(1) + "/2012')");

			db.execSQL("insert into RKAnswers (rkQuestion, rkAnswer, answerDate) values(0,1,'4/" + rnd.nextInt(1) + "/2012')");
			db.execSQL("insert into RKAnswers (rkQuestion, rkAnswer, answerDate) values(0,2,'4/" + rnd.nextInt(1) + "/2012')");

			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,0,'4/" + rnd.nextInt(10) + "/2012')");
			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,1,'4/" + rnd.nextInt(10) + "/2012')");
			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,2,'4/" + rnd.nextInt(10) + "/2012')");
			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,3,'4/" + rnd.nextInt(10) + "/2012')");
			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,4,'4/" + rnd.nextInt(10) + "/2012')");
			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,5,'4/" + rnd.nextInt(10) + "/2012')");
			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,6,'4/" + rnd.nextInt(10) + "/2012')");
			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,7,'4/" + rnd.nextInt(10) + "/2012')");
			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,8,'4/" + rnd.nextInt(10) + "/2012')");
			db.execSQL("insert into BurnoutAnswers (bQuestion, bAnswer, answerDate) values(0,9,'4/" + rnd.nextInt(10) + "/2012')");

			for(int a =0; a< 30; a++)
			{

				db.execSQL("insert into QOLAnswers (QOLQuestion, QOLAnswer, answerDate) values(" + a + "," + rnd.nextInt(4) + ",'4/" + i + "/2012')");
			}

		}
		db.close();

	}

	/**
	 * Insert misc.
	 *
	 * @param type the type
	 * @param value the value
	 * @param answerDate the answer date
	 */
	public void insertMisc(String type, int value, String answerDate)
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();

		try
		{
			String insSQL = "insert into MiscData (mtype, mValue, answerDate) values('" + type + "'," + value + ",'" + answerDate + "')";
			db.execSQL(insSQL);
		}
		catch(Exception ex)
		{

		}

		db.close();

	}

	/**
	 * Select misc.
	 *
	 * @param type the type
	 * @param answerDate the answer date
	 * @return the list
	 */
	public List<Integer> selectMisc(String type, String answerDate)
	{

		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		String query = "select mValue from MiscData where mtype = '" + type + "' and answerDate >= '" + getStartDate(answerDate) + "' and answerdate <= '" + getEndDate(answerDate) + "'";

		List<Integer> list = new ArrayList<Integer>();

		Cursor cursor = this.db.rawQuery(query, null);
		if (cursor.moveToFirst()) 
		{
			do 
			{
				list.add(cursor.getInt(0)); 
			} 
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) 
		{
			cursor.close();
		}

		db.close();

		return list;
	}

	/**
	 * The Class OpenHelper.
	 */
	private static class OpenHelper extends SQLiteOpenHelper 
	{

		/**
		 * Instantiates a new open helper.
		 *
		 * @param context the context
		 */
		OpenHelper(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/* (non-Javadoc)
		 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
		 */
		@Override
		public void onCreate(SQLiteDatabase db) 
		{

			String createRB = "CREATE TABLE IF NOT EXISTS ResilienceBuilders (rbID INTEGER PRIMARY KEY, rbQuestion STRING, custom INTEGER );";
			db.execSQL(createRB);
			db.execSQL("insert into ResilienceBuilders (rbQuestion, custom) values ('Did you take a short walk?', 0)");
			db.execSQL("insert into ResilienceBuilders (rbQuestion, custom) values ('Did you perform at-desk stretching or isometrics?', 0)");
			db.execSQL("insert into ResilienceBuilders (rbQuestion, custom) values ('Are you focusing on today''s Resilience Practice using affirmations?', 0)");
			db.execSQL("insert into ResilienceBuilders (rbQuestion, custom) values ('Have you taken a day off within the last 60 working days?', 0)");
			db.execSQL("insert into ResilienceBuilders (rbQuestion, custom) values ('Have you laughed at something today?', 0)");

			String createRBA = "CREATE TABLE IF NOT EXISTS RBAnswers (rbaID INTEGER PRIMARY KEY, rbQuestion INTEGER, rbAnswer INTEGER, answerDate DATETIME );";
			db.execSQL(createRBA);

			String createRK = "CREATE TABLE IF NOT EXISTS ResilienceKillers (rkID INTEGER PRIMARY KEY, rkQuestion STRING, custom INTEGER );";
			db.execSQL(createRK);
			db.execSQL("insert into ResilienceKillers (rkQuestion, custom) values ('Did you come to work sick today?', 0)");
			db.execSQL("insert into ResilienceKillers (rkQuestion, custom) values ('Did you skip lunch?', 0)");
			db.execSQL("insert into ResilienceKillers (rkQuestion, custom) values ('Did you eat lunch at your desk?', 0)");
			db.execSQL("insert into ResilienceKillers (rkQuestion, custom) values ('Have you eaten junk food?', 0)");
			db.execSQL("insert into ResilienceKillers (rkQuestion, custom) values ('Do you work on weekends?', 0)");

			String createRKA = "CREATE TABLE IF NOT EXISTS RKAnswers (rKaID INTEGER PRIMARY KEY, rKQuestion INTEGER, rKAnswer INTEGER, answerDate DATETIME );";
			db.execSQL(createRKA);

			String createQOL = "CREATE TABLE IF NOT EXISTS PROQOL (qolID INTEGER PRIMARY KEY, qolQuestion STRING, custom INTEGER );";
			db.execSQL(createQOL);
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I am happy.', 1)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I am preoccupied with more than one person I treat.', 2)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I get satisfaction from being able to help people.', 3)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I feel connected to others.', 4)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I jump or am startled by unexpected sounds.', 5)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I feel invigorated after working with those I treat.', 6)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I find it difficult to separate my personal life from my life as a therapist/health care provider.', 7)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I am not as productive at work because I am loosing sleep over traumatic experiences of a person I treat.', 8)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I think I might have been affected by the traumatic stress of those I treat.', 9)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I feel trapped by my job as a therapist/health care provider.', 10)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('Because of my work, I have felt ''on edge'' about various things.', 11)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I like my work as a therapist/health care provider.', 12)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I feel depressed because of the traumatic experiences of the people I treat.', 13)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I feel as though I am experiencing the trauma of someone I have treated.', 14)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I have beliefs that sustain me.', 15)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I am pleased with how I am able to keep up with treatment techniques and protocols.', 16)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I am the person I always wanted to be.', 17)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('My work makes me feel satisfied.', 18)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I feel worn out because of my work as a therapist/health care provider.', 19)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I have happy thoughts and feelings about those I treat and how I could help them.', 20)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I feel overwhelmed because my case load seems endless.', 21)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I believe I can make a difference through my work.', 22)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I avoid certain activities or situations because they remind me of frightening experiences of the people I treat.', 23)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I am proud of what I can do to help.', 24)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('As a result of my work, I have intrusive, frightening thoughts.', 25)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I feel ''bogged down'' by the system.', 26)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I have thoughts that I am a ''success'' as a therapist/health care provider.', 27)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I can''t recall important parts of my work with trauma victims.', 28)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I am a very caring person.', 29)");
			db.execSQL("insert into PROQOL (qolQuestion, custom) values ('I am happy that I chose to do this work.', 30)");

			String createQOLA = "CREATE TABLE IF NOT EXISTS QOLAnswers (qolaID INTEGER PRIMARY KEY, qolQuestion INTEGER, qolAnswer INTEGER, answerDate DATETIME );";
			db.execSQL(createQOLA);


			String createBurn = "CREATE TABLE IF NOT EXISTS Burnout (bID INTEGER PRIMARY KEY, bQuestion STRING, custom INTEGER );";
			db.execSQL(createBurn);
			db.execSQL("insert into Burnout (bQuestion, custom) values ('Happy', 0)");
			db.execSQL("insert into Burnout (bQuestion, custom) values ('Trapped', 0)");
			db.execSQL("insert into Burnout (bQuestion, custom) values ('Satisfied', 0)");
			db.execSQL("insert into Burnout (bQuestion, custom) values ('Preoccupied', 0)");
			db.execSQL("insert into Burnout (bQuestion, custom) values ('Connected', 0)");
			db.execSQL("insert into Burnout (bQuestion, custom) values ('Worn out', 0)");
			db.execSQL("insert into Burnout (bQuestion, custom) values ('Caring', 0)");
			db.execSQL("insert into Burnout (bQuestion, custom) values ('On edge', 0)");
			db.execSQL("insert into Burnout (bQuestion, custom) values ('Valuable', 0)");
			db.execSQL("insert into Burnout (bQuestion, custom) values ('Traumatized', 0)");

			String createBurnA = "CREATE TABLE IF NOT EXISTS BurnoutAnswers (bID INTEGER PRIMARY KEY, bQuestion INTEGER, bAnswer INTEGER, answerDate DATETIME );";
			db.execSQL(createBurnA);

			String createMisc = "CREATE TABLE IF NOT EXISTS MiscData (mID INTEGER PRIMARY KEY, mType STRING, mValue INTEGER, answerDate DATETIME );";
			db.execSQL(createMisc);
		}

		/* (non-Javadoc)
		 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			try
			{
				db.execSQL("drop table ResilienceBuilders");
				db.execSQL("drop table ResilienceKillers");
				db.execSQL("drop table PROQOL");
				db.execSQL("drop table Burnout");
			}
			catch(Exception ex)
			{}
			onCreate(db);
		}
	}
}