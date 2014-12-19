package com.rnd.mobilepayment.pln;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rnd.mobilepayment.R;
import com.rnd.mobilepayment.printer.PrinterActivity;
import com.rnd.mobilepayment.printer.PrinterUtil;

public class DialogPostpaidPrint extends DialogFragment {

	private TextView postPrint_header;
	private TextView postPrint_idPel;
	private TextView postPrint_namaPel;
	private TextView postPrint_trfDaya;
	private TextView postPrint_bltTh;
	private TextView postPrint_stdMtr;
	private TextView postPrint_rpTagPLN;
	private TextView postPrint_noRef;
	private TextView postPrint_admCA;
	private TextView postPrint_totByr;
	private Button postPrint_cetak;

	private TextView postPrint_midStmnt;
	private TextView postPrint_midStmnt2;
	private TextView postPrint_footer1;
	private TextView postPrint_footer15;
	private TextView postPrint_footer2;
	private TextView postPrint_footer3;

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

	public static DialogPostpaidPrint newInstance(String idPel, String namaPel,
			String trfDaya, String bltTh, String stdMtr, String rpTagPLN,
			String noRef, String admCA, String totByr) {
		DialogPostpaidPrint dp = new DialogPostpaidPrint();

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

	public static DialogPostpaidPrint newInstance() {
		DialogPostpaidPrint dp = new DialogPostpaidPrint();

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
		Log.e("onCreate Dialog Print", idPel + " - " + namaPel + " - ");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_postpaid_print,
				container, false);

		postPrint_header = (TextView) rootView
				.findViewById(R.id.postPrint_header);
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

		postPrint_midStmnt = (TextView) rootView
				.findViewById(R.id.postPrint_midStmnt);
		postPrint_midStmnt2 = (TextView) rootView
				.findViewById(R.id.postPrint_midStmnt2);
		postPrint_footer1 = (TextView) rootView
				.findViewById(R.id.postPrint_footer1);
		postPrint_footer15 = (TextView) rootView
				.findViewById(R.id.postPrint_footer15);
		postPrint_footer2 = (TextView) rootView
				.findViewById(R.id.postPrint_footer2);
		postPrint_footer3 = (TextView) rootView
				.findViewById(R.id.postPrint_footer3);

		postPrint_cetak = (Button) rootView.findViewById(R.id.postPrint_cetak);

		postPrint_idPel.setText("IDPEL : " + idPel);
		postPrint_namaPel.setText("NAMA : " + namaPel);
		postPrint_trfDaya.setText("TARIF / DAYA : " + trfDaya + " VA");
		postPrint_bltTh.setText("BL/TH : " + bltTh);
		postPrint_stdMtr.setText("STAND METER : " + stdMtr);
		postPrint_rpTagPLN.setText("RP TAG PLN : Rp. " + rpTagPLN);
		postPrint_noRef.setText("JPA REF : " + noRef);
		postPrint_admCA.setText("ADMIN BANK : Rp. " + admCA);
		postPrint_totByr.setText("TOTAL BAYAR : Rp. " + totByr);
		postPrint_cetak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG)
						.show();
				Intent myIntent = new Intent(getActivity(), PrinterActivity.class);
				String strToPrint = PrinterUtil.smallFont()
						+ PrinterUtil.centerAlign() + PrinterUtil.bold()
						+ postPrint_header.getText().toString() + "\r\n\r\n"
						
						// Data 1
						+ PrinterUtil.smallFont()
						+ PrinterUtil.leftAlign() + PrinterUtil.cancelBold()
						+ postPrint_idPel.getText().toString() + "\r\n"
						+ postPrint_namaPel.getText().toString() + "\r\n"
						+ postPrint_trfDaya.getText().toString() + "\r\n"
						+ postPrint_bltTh.getText().toString() + "\r\n"
						+ postPrint_stdMtr.getText().toString() + "\r\n"
						+ postPrint_rpTagPLN.getText().toString() + "\r\n"
						+ postPrint_noRef.getText().toString() + "\r\n\r\n"
						
						// Mid Statement
						+ PrinterUtil.smallFont()
						+ PrinterUtil.centerAlign() + PrinterUtil.bold()
						+ postPrint_midStmnt.getText().toString() + "\r\n"
						+ postPrint_midStmnt2.getText().toString() + "\r\n\r\n"
						
						// Data 2
						+ PrinterUtil.smallFont()
						+ PrinterUtil.leftAlign() + PrinterUtil.cancelBold()
						+ postPrint_admCA.getText().toString() + "\r\n"
						+ postPrint_totByr.getText().toString() + "\r\n\r\n"
						
						// Footer Msg
						+ PrinterUtil.smallFont()
						+ PrinterUtil.centerAlign() + PrinterUtil.bold()
						+ postPrint_footer1.getText().toString() + "\r\n"
						+ postPrint_footer15.getText().toString() + "\r\n"
						+ postPrint_footer2.getText().toString() + "\r\n"
						+ postPrint_footer3.getText().toString() + "\r\n";
				myIntent.putExtra("TEXT", strToPrint);
//						PrinterUtil.smallFont() + PrinterUtil.centerAlign()
//								+ PrinterUtil.bold()
//								+ postPrint_header.getText().toString()
//								+ "\r\n" + postPrint_idPel.getText().toString()
//								+ "\r\n"
//								+ postPrint_namaPel.getText().toString()
//								+ "\r\n"
//								+ postPrint_trfDaya.getText().toString()
//								+ "\r\n" + postPrint_bltTh.getText().toString()
//								+ "\r\n"
//								+ postPrint_stdMtr.getText().toString()
//								+ "\r\n"
//								+ postPrint_rpTagPLN.getText().toString()
//								+ "\r\n" + postPrint_noRef.getText().toString()
//								+ "\r\n"
//								+ postPrint_midStmnt.getText().toString()
//								+ "\r\n"
//								+ postPrint_midStmnt2.getText().toString()
//								+ "\r\n" + postPrint_admCA.getText().toString()
//								+ "\r\n"
//								+ postPrint_totByr.getText().toString()
//								+ "\r\n"
//								+ postPrint_footer1.getText().toString()
//								+ "\r\n"
//								+ postPrint_footer2.getText().toString()
//								+ "\r\n"
//								+ postPrint_footer3.getText().toString()
//								+ "\r\n");
				startActivity(myIntent);
			}
		});

		return rootView;
	}
}
