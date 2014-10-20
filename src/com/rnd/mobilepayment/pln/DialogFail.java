package com.rnd.mobilepayment.pln;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rnd.mobilepayment.R;

public class DialogFail extends DialogFragment {
	
	private String message;
	
	private TextView fail_message;
	private Button OK_FAIL;
	
	/**
	 * Create a new instance of DialogPostapaidPrint.
	 */
	public static DialogFail newInstance(String message) {
		DialogFail dp = new DialogFail();
		Bundle args = new Bundle();
		args.putString("MSG", message);
		dp.setArguments(args);
		
		return dp;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog);
		
		message = getArguments().getString("MSG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_fail, container,
				false);
		
		fail_message = (TextView) rootView.findViewById(R.id.fail_message);
		fail_message.setText(message);
		
		OK_FAIL = (Button) rootView.findViewById(R.id.OK_FAIL);
		OK_FAIL.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		
		return rootView;
	}
}
