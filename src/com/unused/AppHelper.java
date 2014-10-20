package com.unused;

import com.rnd.mobilepayment.pln.engine.Engine;

import android.content.Context;

public class AppHelper {

	public final static int PORT_ADDRESS = 14063;
	// public final static String IP_ADDRESS = "202.152.22.118";
	public final static String IP_ADDRESS = "10.254.254.12";
	public static Engine engine;

	public static Engine getEngine(Context ctx) {
		if (engine == null)
			engine = new Engine(ctx);
		return engine;
	}
}
