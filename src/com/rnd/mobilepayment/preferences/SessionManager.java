package com.rnd.mobilepayment.preferences;

import android.content.Context;

public class SessionManager {

	private static SessionManager instance;
	private Session mSession;

	public SessionManager(Context context) {
		// TODO Auto-generated constructor stub
		mSession = new Session(context);
	}

	public Session getSession() {
		return mSession;
	}

	// This method should be called first to do singleton initialization
	public static synchronized SessionManager getInstance(Context context) {
		if (instance == null) {
			instance = new SessionManager(context);
		}
		return instance;
	}

	public static synchronized SessionManager getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
					SessionManager.class.getSimpleName()
							+ " is not initialized, call getInstance(..) method first.");
		}
		return instance;
	}
}
