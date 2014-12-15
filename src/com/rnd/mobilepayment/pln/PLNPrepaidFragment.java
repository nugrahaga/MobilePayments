package com.rnd.mobilepayment.pln;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.rnd.mobilepayment.R;
import com.rnd.mobilepayment.pln.engine.PLNManager;
import com.rnd.mobilepayment.preferences.SessionManager;
import com.rnd.mobilepayment.utils.MobilePayments;

public class PLNPrepaidFragment extends Fragment {

	private ProgressDialog pDialog;
	private HashMap<String, String> response;

	private TextView prepaid_TID;
	private TextView pre_noMeter;
	private Spinner spinnerUnsold;
	private TextView pre_nominal;
	private Button pre_inq;

	private boolean isValidNoMeter;
	private boolean isValidNominal;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_pln_prepaid,
				container, false);

		prepaid_TID = (TextView) rootView.findViewById(R.id.prepaid_TID);
		pre_noMeter = (TextView) rootView.findViewById(R.id.pre_noMeter);
		spinnerUnsold = (Spinner) rootView.findViewById(R.id.pre_unsold);
		pre_nominal = (TextView) rootView.findViewById(R.id.pre_nominal);
		pre_inq = (Button) rootView.findViewById(R.id.pre_inq);

		final InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/RobotoCondensed-Bold.ttf");
		prepaid_TID.setTypeface(roboto);
		HashMap<String, String> user = SessionManager.getInstance()
				.getSession().getUserDetails();
		prepaid_TID.setText("TERMINAL ID : " + user.get("tID"));

		pre_noMeter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				validateNoMeter(arg0.toString());
				updateButtonState();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		pre_noMeter.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				boolean isValidKey = event != null
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
				boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

				if (isValidKey || isValidAction) {
					// do login request
					new inqPrepaid().execute();
				}
				return false;
			}
		});

		pre_nominal.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validateNominal(s.toString());
				updateButtonState();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		pre_nominal.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				boolean isValidKey = event != null
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
				boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

				if (isValidKey || isValidAction) {
					// do login request
					new inqPrepaid().execute();
				}
				return false;
			}
		});

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.unsold,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerUnsold.setAdapter(adapter);

		pre_inq.setEnabled(false);
		pre_inq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imm.hideSoftInputFromWindow(pre_inq.getWindowToken(), 0);
				new inqPrepaid().execute();
			}
		});

		return rootView;
	}

	private void validateNoMeter(String text) {
		// postpaid 12-13
		// prepaid 11-12
		// nonreg 13
		isValidNoMeter = !text.isEmpty() && text.length() >= 11;
	}

	private void validateNominal(String text) {
		isValidNominal = !text.isEmpty();
	}

	private void updateButtonState() {
		if (isValidNoMeter && isValidNominal) {
			pre_inq.setEnabled(true);
		} else {
			pre_inq.setEnabled(false);
		}
	}

	/**
	 * Async task inquiry prepaid
	 * 
	 * @author nugrahaga
	 *
	 */

	protected class inqPrepaid extends AsyncTask<String, String, String> {

		private HashMap<String, String> responsePAY;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("INQUIRY...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.e("inqPostpaid", MobilePayments.getIpAddress() + " - "
					+ MobilePayments.getPortAddress() + " - "
					+ pre_noMeter.getText().toString() + " - "
					+ pre_nominal.getText().toString() + " - "
					+ spinnerUnsold.getSelectedItem().toString());

			boolean type = false;

			if (spinnerUnsold.getSelectedItem().toString()
					.equalsIgnoreCase("Token Baru"))
				type = false;
			else
				type = true;

			response = PLNManager
					.getInstance()
					.doRequest()
					.INQPrepaid(MobilePayments.getIpAddress(),
							MobilePayments.getPortAddress(),
							pre_noMeter.getText().toString(), type,
							Integer.parseInt(pre_nominal.getText().toString()));
			Log.e("Response Inquiry",
					response.get("RC") + " - " + response.get("RESPONSE_MSG"));
			if (response.get("RC").equalsIgnoreCase("00")) {
				responsePAY = PLNManager
						.getInstance()
						.doRequest()
						.PAYPrepaid(
								MobilePayments.getIpAddress(),
								MobilePayments.getPortAddress(),
								pre_noMeter.getText().toString(),
								type,
								Integer.parseInt(pre_nominal.getText()
										.toString()));
			}
			// Belum ADV
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			Log.e("Response PAY",
					response.get("RC") + " - " + response.get("RESPONSE_MSG"));

			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						if (responsePAY.get("RC").equalsIgnoreCase("00")) {
							// Bayar
							FragmentTransaction ft = getFragmentManager()
									.beginTransaction();
							
							Log.e("Response", responsePAY.toString());
							Log.e("Footer MSG", response.get("FOOTER MSG"));
							
							DialogPrepaidPrint.newInstance(
									responsePAY.get("NO METER"),
									responsePAY.get("IDPEL"),
									responsePAY.get("NAMA"),
									responsePAY.get("TARIF/DAYA"),
									responsePAY.get("JPAREF"),
									responsePAY.get("RP BAYAR"),
									responsePAY.get("ADMIN BANK"),
									responsePAY.get("MATERAI"),
									responsePAY.get("PPN"), response.get("PPJ"),
									responsePAY.get("ANGSURAN"),
									responsePAY.get("RP STROOM/TOKEN"),
									responsePAY.get("JML KWH"),
									responsePAY.get("STROOM/TOKEN")).show(ft, "");

						}
						// Belum ADV
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("Error Inquiry", e.toString());
					}
				}
			});
		}

	}

}
