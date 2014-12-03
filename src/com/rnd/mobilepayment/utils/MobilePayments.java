package com.rnd.mobilepayment.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.rnd.mobilepayment.pln.engine.PLNManager;
import com.rnd.mobilepayment.preferences.SessionManager;

public class MobilePayments extends Application {

	private static String IMEI_ID;
	private final static int PORT_ADDRESS = 14063;
	private final static String IP_ADDRESS = "10.254.254.12";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// Initialize singleton
		PLNManager.getInstance(getApplicationContext());
		SessionManager.getInstance(getApplicationContext());
		// Get IMEI ID
		TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		IMEI_ID = telemamanger.getDeviceId();
	}

	public static boolean isNetworkOn(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connMgr != null) {
			NetworkInfo[] info = connMgr.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}

	public static String getIMEI_ID() {
		return IMEI_ID;
	}

	public static int getPortAddress() {
		return PORT_ADDRESS;
	}

	public static String getIpAddress() {
		return IP_ADDRESS;
	}

}
