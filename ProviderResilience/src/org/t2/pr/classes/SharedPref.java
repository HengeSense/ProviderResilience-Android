/*
 * 
 */
package org.t2.pr.classes;

// TODO: Auto-generated Javadoc
/**
 * The Class SharedPref.
 */
public class SharedPref {

	/**
	 * Gets the popup card day.
	 *
	 * @return the popup card day
	 */
	public static int getPopupCardDay() {
		return Global.sharedPref.getInt("card_day", 0);
	}

	/**
	 * Sets the popup card day.
	 *
	 * @param dayofyear the new popup card day
	 */
	public static void setPopupCardDay(int dayofyear) {
		Global.sharedPref.edit().putInt("card_day", dayofyear).commit();
	}
	
	/**
	 * Gets the checks if is eula accepted.
	 *
	 * @return the checks if is eula accepted
	 */
	public static boolean getIsEulaAccepted() {
		return Global.sharedPref.getBoolean("eula_accepted", false);
	}

	/**
	 * Sets the checks if is eula accepted.
	 *
	 * @param enabled the new checks if is eula accepted
	 */
	public static void setIsEulaAccepted(boolean enabled) {
		Global.sharedPref.edit().putBoolean("eula_accepted", enabled).commit();
	}

	/**
	 * Gets the send annon data.
	 *
	 * @return the send annon data
	 */
	public static boolean getSendAnnonData() {
		return Global.sharedPref.getBoolean("send_anon_data", true);
	}

	/**
	 * Sets the send annon data.
	 *
	 * @param enabled the new send annon data
	 */
	public static void setSendAnnonData(boolean enabled) {
		Global.sharedPref.edit().putBoolean("send_anon_data", enabled).commit();
	}

	/**
	 * Gets the vacation year.
	 *
	 * @return the vacation year
	 */
	public static int getVacationYear() {
		return Global.sharedPref.getInt("vacation_year", 0);
	}

	/**
	 * Sets the vacation year.
	 *
	 * @param year the new vacation year
	 */
	public static void setVacationYear(int year) {
		Global.sharedPref.edit().putInt("vacation_year", year).commit();
	}

	/**
	 * Gets the vacation month.
	 *
	 * @return the vacation month
	 */
	public static int getVacationMonth() {
		return Global.sharedPref.getInt("vacation_month", 0);
	}

	/**
	 * Sets the vacation month.
	 *
	 * @param month the new vacation month
	 */
	public static void setVacationMonth(int month) {
		Global.sharedPref.edit().putInt("vacation_month", month).commit();
	}

	/**
	 * Gets the vacation day.
	 *
	 * @return the vacation day
	 */
	public static int getVacationDay() {
		return Global.sharedPref.getInt("vacation_day", 0);
	}

	/**
	 * Sets the vacation day.
	 *
	 * @param day the new vacation day
	 */
	public static void setVacationDay(int day) {
		Global.sharedPref.edit().putInt("vacation_day", day).commit();
	}

	/**
	 * Gets the on vacation.
	 *
	 * @return the on vacation
	 */
	public static boolean getOnVacation() {
		return Global.sharedPref.getBoolean("on_vacation", false);
	}

	/**
	 * Sets the on vacation.
	 *
	 * @param enabled the new on vacation
	 */
	public static void setOnVacation(boolean enabled) {
		Global.sharedPref.edit().putBoolean("on_vacation", enabled).commit();
	}

	/**
	 * Gets the last assessment date.
	 *
	 * @return the last assessment date
	 */
	public static String getLastAssessmentDate() {
		return Global.sharedPref.getString("last_assessment_date", "null");
	}
	
	/**
	 * Sets the last assessment date.
	 *
	 * @param date the new last assessment date
	 */
	public static void setLastAssessmentDate(String date) {
		Global.sharedPref.edit().putString("last_assessment_date", date).commit();
	}
	
	/**
	 * Gets the welcome message.
	 *
	 * @return the welcome message
	 */
	public static boolean getWelcomeMessage() {
		return Global.sharedPref.getBoolean("welcome", false);
	}

	/**
	 * Sets the welcome message.
	 *
	 * @param enabled the new welcome message
	 */
	public static void setWelcomeMessage(boolean enabled) {
		Global.sharedPref.edit().putBoolean("welcome", enabled).commit();
	}
	
	/**
	 * Gets the reminders.
	 *
	 * @return the reminders
	 */
	public static boolean getReminders() {
		return Global.sharedPref.getBoolean("reminders", false);
	}

	/**
	 * Sets the reminders.
	 *
	 * @param enabled the new reminders
	 */
	public static void setReminders(boolean enabled) {
		Global.sharedPref.edit().putBoolean("reminders", enabled).commit();
	}
	
	/**
	 * Gets the notify hour.
	 *
	 * @return the notify hour
	 */
	public static int getNotifyHour() {
		return Global.sharedPref.getInt("notify_hour", 1);
	}

	/**
	 * Sets the notify hour.
	 *
	 * @param hour the new notify hour
	 */
	public static void setNotifyHour(int hour) {
		Global.sharedPref.edit().putInt("notify_hour", hour).commit();
	}
	
	/**
	 * Gets the notify minute.
	 *
	 * @return the notify minute
	 */
	public static int getNotifyMinute() {
		return Global.sharedPref.getInt("notify_minute", 1);
	}

	/**
	 * Sets the notify minute.
	 *
	 * @param minute the new notify minute
	 */
	public static void setNotifyMinute(int minute) {
		Global.sharedPref.edit().putInt("notify_minute", minute).commit();
	}

	/**
	 * Gets the reset hour.
	 *
	 * @return the reset hour
	 */
	public static int getResetHour() {
		return Global.sharedPref.getInt("reset_hour", 1);
	}

	/**
	 * Sets the reset hour.
	 *
	 * @param hour the new reset hour
	 */
	public static void setResetHour(int hour) {
		Global.sharedPref.edit().putInt("reset_hour", hour).commit();
	}
	
	/**
	 * Gets the reset minute.
	 *
	 * @return the reset minute
	 */
	public static int getResetMinute() {
		return Global.sharedPref.getInt("reset_minute", 0);
	}

	/**
	 * Sets the reset minute.
	 *
	 * @param minute the new reset minute
	 */
	public static void setResetMinute(int minute) {
		Global.sharedPref.edit().putInt("reset_minute", minute).commit();
	}
	
	/**
	 * Gets the card index.
	 *
	 * @return the card index
	 */
	public static int getCardIndex() {
		return Global.sharedPref.getInt("card_index", 0);
	}

	/**
	 * Sets the card index.
	 *
	 * @param card the new card index
	 */
	public static void setCardIndex(int card) {
		Global.sharedPref.edit().putInt("card_index", card).commit();
	}
	
	/**
	 * Gets the anon data.
	 *
	 * @return the anon data
	 */
	public static boolean getAnonData() {
		return Global.sharedPref.getBoolean("anondata", true);
	}

	/**
	 * Sets the anon data.
	 *
	 * @param enabled the new anon data
	 */
	public static void setAnonData(boolean enabled) {
		Global.sharedPref.edit().putBoolean("anondata", enabled).commit();
	}
}
