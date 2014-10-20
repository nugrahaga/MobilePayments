package com.rnd.mobilepayment;

import java.util.HashMap;

import com.rnd.mobilepayment.preferences.SessionManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		TextView tId = (TextView) rootView.findViewById(R.id.reg_TID);
		Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/RobotoCondensed-Bold.ttf");
		tId.setTypeface(roboto);
		HashMap<String, String> user = SessionManager.getInstance()
				.getSession().getUserDetails();
		tId.setText("TERMINAL ID : "+user.get("tID"));

		Button call = (Button) rootView.findViewById(R.id.home_callAssist);
		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent call = new Intent(Intent.ACTION_DIAL);
				call.setData(Uri.parse("tel:08112030885"));
				startActivity(call);
			}
		});

		Button logout = (Button) rootView.findViewById(R.id.home_logout);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return rootView;
	}
}
