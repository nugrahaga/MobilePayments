package com.rnd.mobilepayment.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class ByteRequest extends Request<byte[]> {

	private final Listener<byte[]> mListener;

	public ByteRequest(int method, String url, Listener<byte[]> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		// TODO Auto-generated constructor stub
		mListener = listener;
	}

	@Override
	protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		return Response.success(response.data,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	protected void deliverResponse(byte[] response) {
		// TODO Auto-generated method stub
		mListener.onResponse(response);
	}

}
