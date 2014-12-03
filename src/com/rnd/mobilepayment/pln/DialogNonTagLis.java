package com.rnd.mobilepayment.pln;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
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
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
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
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}

}
