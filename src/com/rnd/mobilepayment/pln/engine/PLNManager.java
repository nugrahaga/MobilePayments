package com.rnd.mobilepayment.pln.engine;

import android.content.Context;

public class PLNManager {

	private static PLNManager instance;
	private PLNEnlace mEnlace;

	private PLNManager(Context context) {
		mEnlace = new PLNEnlace(context);
	}

	/**
	 * This method called to do request for PLNEnlace
	 * 
	 * @return PLNEnlace to do request method
	 */
	public PLNEnlace doRequest() {
		return mEnlace;
	}

	/**
	 * This method should be called first to do singleton initialization
	 * 
	 * @param context
	 * @return instance
	 */
	public static synchronized PLNManager getInstance(Context context) {
		if (instance == null) {
			instance = new PLNManager(context);
		}
		return instance;
	}

	/**
	 * This method called to get instance
	 * 
	 * @return instance
	 */
	public static synchronized PLNManager getInstance() {
		if (instance == null) {
			throw new IllegalStateException(PLNManager.class.getSimpleName()
					+ " is not initialized, call getInstance(..) method first.");
		}
		return instance;
	}
}
