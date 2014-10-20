package com.rnd.mobilepayment.network;

import java.util.HashMap;

import jpa.jpos.iso.ISOMsg;
import jpos.ISO1987APackagerJ;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.rnd.mobilepayment.utils.MobilePayments;

public class RequestProxy {

	private RequestQueue mRequestQueue;

	// Customer List
	// private String idToko;

	// package access constructor
	public RequestProxy(Context context) {
		// TODO Auto-generated constructor stub
		mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
	}

	public HashMap<String, String> doLogin(final String idpetugas,
			final String password, String versi, String imei,
			final Context context) {
		Log.e("doLogin", "Username : " + idpetugas + ", Password : " + password);

		// DSSalesTracker.showProgressDialog("Login", true, context);
		//
		String url = "https://" + MobilePayments.getIpAddress();
		HashMap<String, String> value = new HashMap<String, String>();

		try {

			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.setMTI("0100");
			// msg.set(32, MobilePayments.getKodeBank());
			// msg.set(41, MobilePayments.getTerminalID());
			// msg.set(42, MobilePayments.getAcceptorID());
			// msg.set(48,
			// String.format("%1$-25s", imei)
			// + String.format("%1$-25s", idpetugas)
			// + String.format("%1$-32s",
			// MobilePayments.MD5(password))
			// + String.format("%1$-20s", versi));
			msg.set(70, "104");

			// Response.Listener<byte[]> listener = new
			// Response.Listener<byte[]>() {
			//
			// @Override
			// public void onResponse(byte[] response) {
			// // TODO Auto-generated method stub
			// Log.e("Success", "Success Response: " + response.toString());
			// }
			//
			// };
			//
			// Response.ErrorListener errorListener = new
			// Response.ErrorListener() {
			//
			// @Override
			// public void onErrorResponse(VolleyError error) {
			// // TODO Auto-generated method stub
			// if (error.networkResponse != null) {
			// Log.e("Error", "Error Response code: "
			// + error.networkResponse.statusCode);
			// }
			// }
			//
			// };
			//
			// ByteRequest loginRequest = new ByteRequest(Request.Method.POST,
			// url, listener, errorListener) {
			// @Override
			// protected Map<String, String> getParams()
			// throws AuthFailureError {
			// // TODO Auto-generated method stub
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("idpetugas", idpetugas);
			// map.put("password", password);
			//
			// Log.e("Request", "Lalalala");
			// return map;
			// }
			// };
			//
			// mRequestQueue.add(loginRequest);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Error Do Login", e.toString());
		}
		//
		// Response.Listener<String> listener = new Response.Listener<String>()
		// {
		// @Override
		// public void onResponse(final String response) {
		// Log.e("Success", "Success Response: " + response.toString());
		//
		// // Parsing Response
		// if (!response.isEmpty()) {
		// try {
		// JSONObject jsonObj = new JSONObject(response);
		// Log.e("Response = ", jsonObj + "");
		// JSONArray result = jsonObj
		// .getJSONArray(DSSalesTracker.TAG_RESULT);
		// Log.e("Response = ", result + "");
		//
		// // looping through All Contacts
		// for (int i = 0; i < result.length(); i++) {
		// JSONObject r = result.getJSONObject(i);
		//
		// jId = r.getString(DSSalesTracker.TAG_ID);
		// jUser = r.getString(DSSalesTracker.TAG_USER);
		// jPosition = r
		// .getString(DSSalesTracker.TAG_POSITION);
		// jCrew = r.getString(DSSalesTracker.TAG_CREWID);
		// Log.e("Response = ", jId + jUser + jPosition);
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// Log.e("Error Parsing", e.toString());
		// }
		// }
		//
		// ((Activity) context).runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// // update view
		// Log.e("uithread", "masuk- " + jId + " -");
		// if (!jId.equalsIgnoreCase("null")
		// && !jUser.equalsIgnoreCase("null")) {
		// Log.e("uithread", "masuk kok");
		//
		// SessionManager
		// .getInstance()
		// .getSession()
		// .createLoginSession(jId, jUser, jPosition,
		// jCrew);
		//
		// RequestManager.getInstance().doRequest().init(jId, context);
		//
		// Intent i = new Intent(context, MainActivity.class);
		// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(i);
		// } else {
		// Toast.makeText(context, "username/password salah",
		// Toast.LENGTH_LONG).show();
		// }
		// }
		// });
		//
		// DSSalesTracker.hideProgressDialog();
		// }
		// };
		//
		// Response.ErrorListener errorListener = new Response.ErrorListener() {
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// if (error.networkResponse != null) {
		// Log.e("Error", "Error Response code: "
		// + error.networkResponse.statusCode);
		// }
		//
		// DSSalesTracker.hideProgressDialog();
		// }
		// };
		//
		// StringRequest loginRequest = new StringRequest(Request.Method.POST,
		// url, listener, errorListener) {
		// @Override
		// protected Map<String, String> getParams() throws AuthFailureError {
		// // TODO Auto-generated method stub
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("username", username);
		// map.put("password", password);
		//
		// Log.e("Request", "Lalalala");
		// return map;
		// }
		// };
		// loginRequest.setTag("login");
		//
		// mRequestQueue.add(loginRequest);

		return null;
	}

}
