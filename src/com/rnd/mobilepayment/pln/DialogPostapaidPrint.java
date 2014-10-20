package com.rnd.mobilepayment.pln;

import com.rnd.mobilepayment.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DialogPostapaidPrint extends DialogFragment {

	private TextView postPrint_idPel;
	private TextView postPrint_namaPel;
	private TextView postPrint_trfDaya;
	private TextView postPrint_bltTh;
	private TextView postPrint_stdMtr;
	private TextView postPrint_rpTagPLN;
	private TextView postPrint_noRef;
	private TextView postPrint_admCA;
	private TextView postPrint_totByr;

	private String idPel;
	private String namaPel;
	private String trfDaya;
	private String bltTh;
	private String stdMtr;
	private String rpTagPLN;
	private String noRef;
	private String admCA;
	private String totByr;

	/**
	 * Create a new instance of DialogPostapaidPrint.
	 */

	public static DialogPostapaidPrint newInstance(String idPel,
			String namaPel, String trfDaya, String bltTh, String stdMtr,
			String rpTagPLN, String noRef, String admCA, String totByr) {
		DialogPostapaidPrint dp = new DialogPostapaidPrint();

		Bundle args = new Bundle();
		args.putString("IDPEL", idPel);
		args.putString("NAMA", namaPel);
		args.putString("TARIF/DAYA", trfDaya);
		args.putString("BL/TH", bltTh);
		args.putString("STAND METER", stdMtr);
		args.putString("RP TAG PLN", rpTagPLN);
		args.putString("JPA REF", noRef);
		args.putString("ADMIN BANK", admCA);
		args.putString("TOTAL BAYAR", totByr);
		dp.setArguments(args);

		return dp;
	}

	public static DialogPostapaidPrint newInstance() {
		DialogPostapaidPrint dp = new DialogPostapaidPrint();

		return dp;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog);
		idPel = getArguments().getString("IDPEL");
		namaPel = getArguments().getString("NAMA");
		trfDaya = getArguments().getString("TARIF/DAYA");
		bltTh = getArguments().getString("BL/TH");
		stdMtr = getArguments().getString("STAND METER");
		rpTagPLN = getArguments().getString("RP TAG PLN");
		noRef = getArguments().getString("JPA REF");
		admCA = getArguments().getString("ADMIN BANK");
		totByr = getArguments().getString("TOTAL BAYAR");
		Log.e("onCreate Dialog Print", idPel + " - " + namaPel);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_postpaid_print,
				container, false);

		postPrint_idPel = (TextView) rootView
				.findViewById(R.id.postPrint_idPel);
		postPrint_namaPel = (TextView) rootView
				.findViewById(R.id.postPrint_namaPel);
		postPrint_trfDaya = (TextView) rootView
				.findViewById(R.id.postPrint_trfDaya);
		postPrint_bltTh = (TextView) rootView
				.findViewById(R.id.postPrint_bltTh);
		postPrint_stdMtr = (TextView) rootView
				.findViewById(R.id.postPrint_stdMtr);
		postPrint_rpTagPLN = (TextView) rootView
				.findViewById(R.id.postPrint_rpTagPLN);
		postPrint_noRef = (TextView) rootView
				.findViewById(R.id.postPrint_noRef);
		postPrint_admCA = (TextView) rootView
				.findViewById(R.id.postPrint_admCA);
		postPrint_totByr = (TextView) rootView
				.findViewById(R.id.postPrint_totByr);
		
		postPrint_idPel.setText(idPel);
		postPrint_namaPel.setText(namaPel);
		postPrint_trfDaya.setText(trfDaya+ " VA");
		postPrint_bltTh.setText(bltTh);
		postPrint_stdMtr.setText(stdMtr);
		postPrint_rpTagPLN.setText("Rp. "+rpTagPLN);
		postPrint_noRef.setText(noRef);
		postPrint_admCA.setText("Rp. "+admCA);
		postPrint_totByr.setText("Rp. "+totByr);

		return rootView;
	}
}
