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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.rnd.mobilepayment.R;
import com.rnd.mobilepayment.pln.engine.PLNManager;
import com.rnd.mobilepayment.preferences.SessionManager;
import com.rnd.mobilepayment.utils.MobilePayments;

public class PLNNonTaglisFragment extends Fragment {

	private ProgressDialog pDialog;
	private HashMap<String, String> response;

	private TextView notag_TID;
	private EditText notag_noRegistrasi;
	private Button notag_inq;

	private boolean isValid;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_pln_nontaglis,
				container, false);

		notag_TID = (TextView) rootView.findViewById(R.id.notag_TID);
		notag_noRegistrasi = (EditText) rootView
				.findViewById(R.id.notag_noRegistrasi);
		notag_inq = (Button) rootView.findViewById(R.id.notag_inq);

		final InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/RobotoCondensed-Bold.ttf");
		notag_TID.setTypeface(roboto);
		HashMap<String, String> user = SessionManager.getInstance()
				.getSession().getUserDetails();
		notag_TID.setText("TERMINAL ID : " + user.get("tID"));

		notag_noRegistrasi.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validateNoReg(s.toString());
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

		notag_noRegistrasi
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub
						boolean isValidKey = event != null
								&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
						boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

						if (isValidKey || isValidAction) {
							// do login request
							new inqNonTagLis().execute();
						}

						return false;
					}
				});

		notag_inq.setEnabled(false);
		notag_inq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imm.hideSoftInputFromWindow(notag_inq.getWindowToken(), 0);
				new inqNonTagLis().execute();
			}
		});

		return rootView;
	}

	private void validateNoReg(String text) {
		isValid = !text.isEmpty() && text.length() == 13;
	}

	private void updateButtonState() {
		if (isValid) {
			notag_inq.setEnabled(true);
		} else {
			notag_inq.setEnabled(false);
		}
	}

	/**
	 * Async task inquiry non taglis
	 * 
	 * @author nugrahaga
	 *
	 */

	protected class inqNonTagLis extends AsyncTask<String, String, String> {

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
			Log.e("inqNonTagLis", MobilePayments.getIpAddress() + " - "
					+ MobilePayments.getPortAddress() + " - "
					+ notag_noRegistrasi.getText().toString());

			response = PLNManager
					.getInstance()
					.doRequest()
					.INQNonrek(MobilePayments.getIpAddress(),
							MobilePayments.getPortAddress(),
							notag_noRegistrasi.getText().toString());
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();

			Log.e("Response Inquiry",
					response.get("RC") + " - " + response.get("RESPONSE_MSG"));

			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						if (response.get("RC").equalsIgnoreCase("00")) {
							FragmentTransaction ft = getFragmentManager()
									.beginTransaction();

							DialogNonTagLis.newInstance(response.get("NAMA"),
									response.get("TRANSAKSI"),
									response.get("RP BAYAR"),
									response.get("NO REGISTRASI"),
									response.get("TGL REGISTRASI"),
									response.get("IDPEL"),
									response.get("BIAYA PLN"),
									response.get("JPAREF"),
									response.get("ADMIN BANK"),
									response.get("TOTAL BAYAR")).show(ft, "");
						}
						// Belum Reversal
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("Error Inquiry", e.toString());
					}
				}
			});
		}

	}
}
