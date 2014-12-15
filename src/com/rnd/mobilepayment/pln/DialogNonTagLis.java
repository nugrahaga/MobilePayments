package com.rnd.mobilepayment.pln;

import java.util.HashMap;

import com.rnd.mobilepayment.R;
import com.rnd.mobilepayment.pln.engine.PLNManager;
import com.rnd.mobilepayment.utils.MobilePayments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class DialogNonTagLis extends DialogFragment {

	private ProgressDialog pDialog;
	private HashMap<String, String> response;

	private String namaPel;
	private String transaksi;
	private String rpBayar;
	private String noReg;
	private String tglReg;
	private String idPel;
	private String biayaPln;
	private String jpaRef;
	private String admCA;
	private String totByr;

	private EditText nonrek_nama;
	private EditText nonrek_transaksi;
	private EditText nonrek_rpBayar;
	private EditText nonrek_noRegistrasi;
	private EditText nonrek_tglRegistrasi;
	private EditText nonrek_idPel;
	private EditText nonrek_biayaPLN;
	private EditText nonrek_noRef;
	private EditText nonrek_admCA;
	private EditText nonrek_totBayar;
	private Button nonrek_INQ;

	/**
	 * Create a new instance of DialogPostpaid, providing "num" as an argument.
	 */

	public static DialogNonTagLis newInstance(String namaPel, String transaksi,
			String rpBayar, String noReg, String tglReg, String idPel,
			String biayaPln, String jpaRef, String admCA, String totByr) {

		DialogNonTagLis dp = new DialogNonTagLis();

		Bundle args = new Bundle();
		args.putString("NAMA", namaPel);
		args.putString("TRANSAKSI", transaksi);
		args.putString("RP BAYAR", rpBayar);
		args.putString("NO REGISTRASI", noReg);
		args.putString("TGL REGISTRASI", tglReg);
		args.putString("IDPEL", idPel);
		args.putString("BIAYA PLN", biayaPln);
		args.putString("JPAREF", jpaRef);
		args.putString("ADMIN BANK", admCA);
		args.putString("TOTAL BAYAR", totByr);

		dp.setArguments(args);

		return dp;
	}

	public static DialogNonTagLis newInstance() {
		DialogNonTagLis dp = new DialogNonTagLis();

		return dp;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);
		Log.e("onCreate", "Dialog Non Tagihan Listrik");

		namaPel = getArguments().getString("NAMA");
		transaksi = getArguments().getString("TRANSAKSI");
		rpBayar = getArguments().getString("RP BAYAR");
		noReg = getArguments().getString("NO REGISTRASI");
		tglReg = getArguments().getString("TGL REGISTRASI");
		idPel = getArguments().getString("IDPEL");
		biayaPln = getArguments().getString("BIAYA PLN");
		jpaRef = getArguments().getString("JPAREF");
		admCA = getArguments().getString("ADMIN BANK");
		totByr = getArguments().getString("TOTAL BAYAR");

		Log.e("onCreate Dialog Non Tagihan", namaPel + " - " + transaksi
				+ " - " + noReg);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle("DATA TAGIHAN PLN NON TAGIHAN LISTRIK");
		View rootView = inflater.inflate(R.layout.dialog_nontaglis, container,
				false);

		nonrek_nama = (EditText) rootView.findViewById(R.id.nonrek_nama);
		nonrek_transaksi = (EditText) rootView
				.findViewById(R.id.nonrek_transaksi);
		nonrek_rpBayar = (EditText) rootView.findViewById(R.id.nonrek_rpBayar);
		nonrek_noRegistrasi = (EditText) rootView
				.findViewById(R.id.nonrek_noRegistrasi);
		nonrek_tglRegistrasi = (EditText) rootView
				.findViewById(R.id.nonrek_tglRegistrasi);
		nonrek_idPel = (EditText) rootView.findViewById(R.id.nonrek_idPel);
		nonrek_biayaPLN = (EditText) rootView
				.findViewById(R.id.nonrek_biayaPLN);
		nonrek_noRef = (EditText) rootView.findViewById(R.id.nonrek_noRef);
		nonrek_admCA = (EditText) rootView.findViewById(R.id.nonrek_admCA);
		nonrek_totBayar = (EditText) rootView
				.findViewById(R.id.nonrek_totBayar);

		nonrek_INQ = (Button) rootView.findViewById(R.id.nonrek_INQ);

		nonrek_nama.setText(namaPel);
		nonrek_transaksi.setText(transaksi);
		nonrek_rpBayar.setText(rpBayar);
		nonrek_noRegistrasi.setText(noReg);
		nonrek_tglRegistrasi.setText(tglReg);
		nonrek_idPel.setText(idPel);
		nonrek_biayaPLN.setText(biayaPln);
		nonrek_noRef.setText(jpaRef);
		nonrek_admCA.setText(admCA);
		nonrek_totBayar.setText(totByr);

		nonrek_INQ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new payNonTagLis().execute();
			}
		});

		return rootView;
	}

	/**
	 * Async task inquiry postpaid
	 * 
	 * @author nugrahaga
	 *
	 */

	protected class payNonTagLis extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("PAY...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.e("payPostpaid", MobilePayments.getIpAddress() + " - "
					+ MobilePayments.getPortAddress() + " - "
					+ nonrek_noRegistrasi.getText().toString());

			response = PLNManager
					.getInstance()
					.doRequest()
					.PAYNonrek(MobilePayments.getIpAddress(),
							MobilePayments.getPortAddress(), noReg);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			Log.e("Response Pay Non Tagihan Listrik",
					response.get("RC") + " - " + response.get("RESPONSE_MSG")
							+ " - " + response.get("NAMA") + " - "
							+ response.get("TRANSAKSI") + " - "
							+ response.get("RP BAYAR") + " - "
							+ response.get("NO REGISTRASI") + " - "
							+ response.get("TGL REGISTRASI") + " - "
							+ response.get("IDPEL") + " - "
							+ response.get("BIAYA PLN") + " - "
							+ response.get("JPAREF") + " - "
							+ response.get("ADMIN BANK") + " - "
							+ response.get("TOTAL BAYAR") + " - "
							+ response.get("FOOTER MSG"));
			try {
				if (response.get("RC").equalsIgnoreCase("00")) {
					dismiss();
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();

					DialogNonTagLisPrint.newInstance(response.get("NAMA"),
							response.get("TRANSAKSI"),
							response.get("NO REGISTRASI"),
							response.get("TGL REGISTRASI"),
							response.get("IDPEL"), response.get("BIAYA PLN"),
							response.get("JPAREF"), response.get("ADMIN BANK"),
							response.get("TOTAL BAYAR")).show(ft, "");
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("Error Pay", e.toString());
			}
		}

	}

}
