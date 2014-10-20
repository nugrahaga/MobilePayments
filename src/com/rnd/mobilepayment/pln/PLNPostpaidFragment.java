package com.rnd.mobilepayment.pln;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.rnd.mobilepayment.R;
import com.rnd.mobilepayment.pln.engine.PLNManager;
import com.rnd.mobilepayment.preferences.SessionManager;
import com.rnd.mobilepayment.utils.MobilePayments;

public class PLNPostpaidFragment extends Fragment {

	private ProgressDialog pDialog;
	private HashMap<String, String> response;

	private TextView postpaid_TID;
	private EditText idPel;
	private Button postInq;

	private boolean isValid;

	// private Dialog resultDialog;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_pln_postpaid,
				container, false);

		postpaid_TID = (TextView) rootView.findViewById(R.id.postpaid_TID);
		idPel = (EditText) rootView.findViewById(R.id.post_idPelanggan);
		postInq = (Button) rootView.findViewById(R.id.post_inq);

		Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/RobotoCondensed-Bold.ttf");
		postpaid_TID.setTypeface(roboto);
		HashMap<String, String> user = SessionManager.getInstance()
				.getSession().getUserDetails();
		postpaid_TID.setText("TERMINAL ID : " + user.get("tID"));

		idPel.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validateIdPel(s.toString());
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

		idPel.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				boolean isValidKey = event != null
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
				boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

				if (isValidKey || isValidAction) {
					// do login request
					new inqPostpaid().execute();
				}
				return false;
			}
		});

		postInq.setEnabled(false);
		postInq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// resultDialog = new Dialog(getActivity());
				// resultDialog.setContentView(R.layout.dialog_postpaid);
				// resultDialog.setTitle("DATA TAGIHAN PLN POSTPAID");
				// resultDialog.show();

				// FragmentTransaction ft = getFragmentManager()
				// .beginTransaction();
				// DialogFragment dp = DialogPostpaid.newInstance();
				// dp.show(ft, "");
				new inqPostpaid().execute();
			}
		});

		return rootView;
	}

	private void validateIdPel(String text) {
		isValid = !text.isEmpty() && text.length() >= 12;
	}

	private void updateButtonState() {
		if (isValid) {
			postInq.setEnabled(true);
		} else {
			postInq.setEnabled(false);
		}
	}

	/**
	 * Async task inquiry postpaid
	 * 
	 * @author nugrahaga
	 *
	 */
	protected class inqPostpaid extends AsyncTask<String, String, String> {

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
					+ idPel.getText().toString());
			response = PLNManager
					.getInstance()
					.doRequest()
					.INQPostpaid(MobilePayments.getIpAddress(),
							MobilePayments.getPortAddress(),
							idPel.getText().toString());
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
							DialogFragment dp = DialogPostpaid.newInstance(
									response.get("IDPEL"),
									response.get("NAMA"),
									response.get("TOTAL TAGIHAN"),
									response.get("TARIF/DAYA"),
									response.get("BL/TH"),
									response.get("RP TAG PLN"),
									response.get("ADMIN BANK"),
									response.get("TOTAL BAYAR"));
							dp.show(ft, "");
						}
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("Error Inquiry", e.toString());
					}
				}
			});
		}

	}
}
