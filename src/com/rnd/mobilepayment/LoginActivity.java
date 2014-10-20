package com.rnd.mobilepayment;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.rnd.mobilepayment.pln.engine.PLNManager;
import com.rnd.mobilepayment.preferences.SessionManager;
import com.rnd.mobilepayment.utils.MobilePayments;

public class LoginActivity extends Activity implements
		LoadingDialogFragment.OnDialogClosedListener {

	// Progress Dialog
	private ProgressDialog pDialog;
	private HashMap<String, String> response;
	LoadingDialogFragment dFragment;

	private EditText usernameField;
	private EditText passwordField;
	private Button btnLogin;
	private TextView register;

	private boolean isUsernameValid;
	private boolean isPasswordValid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		dFragment = LoadingDialogFragment.newInstance();

		usernameField = (EditText) findViewById(R.id.usernameLoginField);
		passwordField = (EditText) findViewById(R.id.passwordLoginField);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		register = (TextView) findViewById(R.id.link_to_register);

		usernameField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validateUsername(s.toString());
				updateLoginButtonState();
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

		usernameField.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				boolean isValidKey = event != null
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
				boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

				if (isValidKey || isValidAction) {
					// do login request
					new doLogin().execute();
				}
				return false;
			}
		});

		passwordField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validatePassword(s.toString());
				updateLoginButtonState();
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

		passwordField.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				boolean isValidKey = event != null
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
				boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

				if (isValidKey || isValidAction) {
					// do login request
					new doLogin().execute();
				}
				return false;
			}
		});

		btnLogin.setEnabled(false);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new doLogin().execute();
			}
		});

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "Register",
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void validatePassword(String text) {
		isPasswordValid = !text.isEmpty();
	}

	private void validateUsername(String text) {
		isUsernameValid = !text.isEmpty();
	}

	private void updateLoginButtonState() {
		if (isUsernameValid && isPasswordValid) {
			btnLogin.setEnabled(true);
		} else {
			btnLogin.setEnabled(false);
		}
	}

	/**
	 * Background Async Task to doLogin
	 * 
	 * @author nugrahaga
	 *
	 */
	protected class doLogin extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("LOGIN ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.e("doLogin", MobilePayments.getIpAddress() + "-"
					+ MobilePayments.getPortAddress() + "-"
					+ usernameField.getText().toString() + "-"
					+ passwordField.getText().toString() + "-" + "0.1" + "-"
					+ MobilePayments.getIMEI_ID());
			response = PLNManager
					.getInstance()
					.doRequest()
					.doLogin(MobilePayments.getIpAddress(),
							MobilePayments.getPortAddress(),
							usernameField.getText().toString(),
							passwordField.getText().toString(), "0.1",
							MobilePayments.getIMEI_ID());
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// dismiss the dialog after getting all products
			pDialog.dismiss();

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						if (response.get("RC").equalsIgnoreCase("00")) {
							SessionManager
									.getInstance()
									.getSession()
									.createLoginSession(
											response.get("IDPETUGAS"),
											response.get("TID"),
											response.get("NAMA_BANK"),
											response.get("NAMA_PP"));
							Intent myIntent = new Intent(LoginActivity.this,
									MainActivity.class);
							startActivity(myIntent);
						} else {
							String errorMsg = response.get("RC") + " - "
									+ response.get("RESPONSE_MSG");
							Toast.makeText(getApplicationContext(), errorMsg,
									Toast.LENGTH_LONG).show();
						}
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("Error do Login", e.toString());
					}
				}
			});
		}

	}

	@Override
	public void onDialogClosed() {
		// TODO Auto-generated method stub

	}
}
