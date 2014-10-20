package com.rnd.mobilepayment.preferences;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.rnd.mobilepayment.LoginActivity;
import com.rnd.mobilepayment.MainActivity;

@SuppressLint("CommitPrefEdits")
public class Session {

	// Shared Preferences
	SharedPreferences pref;
	// Editor for Shared preferences
	Editor editor;
	// Context
	Context _context;
	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "JPA-PAYMENT";
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	// KEY_MEMBERID (make variable public to access from outside)
	public static final String KEY_MEMBERID = "username";
	// KEY_TERMINALID (make variable public to access from outside)
	public static final String KEY_TERMINALID = "tID";
	// KEY_KODEBANK (make variable public to access from outside)
	public static final String KEY_KODEBANK = "cBank";
	// KEY_KODEMITRA (make variable public to access from outside)
	public static final String KEY_KODEMITRA = "cMitra";

	// Constructor
	public Session(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String username, String tID, String cBank,
			String cMitra) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		// Storing name in pref
		editor.putString(KEY_MEMBERID, username);
		// Storing email in pref
		editor.putString(KEY_TERMINALID, tID);
		// Storing email in pref
		editor.putString(KEY_KODEBANK, cBank);
		// Storing email in pref
		editor.putString(KEY_KODEMITRA, cMitra);
		// commit changes
		editor.commit();
	}

	/**
	 * Check login method wil check user login status If false it will redirect
	 * user to login page Else won't do anything
	 * */
	public void checkLogin() {
		// Check login status
		if (!this.isLoggedIn()) {
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}
	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_MEMBERID, pref.getString(KEY_MEMBERID, null));

		user.put(KEY_TERMINALID, pref.getString(KEY_TERMINALID, null));

		// user email id
		// user.put(KEY_NAME, pref.getString(KEY_NAME, null));

		// return user
		return user;
	}

	/**
	 * Clear session details
	 * */
	public void logoutUser() {
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Login Activity
		Intent i = new Intent(_context, MainActivity.class);

		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);

	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn() {
		return pref.getBoolean(IS_LOGIN, false);
	}
}
