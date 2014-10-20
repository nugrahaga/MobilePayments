package com.rnd.mobilepayment.pln;

import com.rnd.mobilepayment.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DialogPrepaid extends DialogFragment {

	private String preP_idPel;
	private String preP_nama;
	private String preP_trfDaya;
	private String preP_blTh;
	private String preP_rpTagPln;
	private String preP_admBank;
	private String preP_totByr;

	private EditText idPel;
	private EditText nama;
	private EditText trfDaya;
	private EditText blTh;
	private EditText rpTagPln;
	private EditText admBank;
	private EditText totByr;
	private Button pay;

	/**
	 * Create a new instance of DialogPostpaid, providing "num" as an argument.
	 */
	public static DialogPrepaid newInstance(String preP_idPel,
			String preP_nama, String preP_trfDaya, String preP_blTh,
			String preP_rpTagPln, String preP_admBank, String preP_totByr) {
		DialogPrepaid dp = new DialogPrepaid();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString("IDPEL", preP_idPel);
		args.putString("NAMA", preP_nama);
		args.putString("TARIF/DAYA", preP_trfDaya);
		args.putString("BL/TH", preP_blTh);
		args.putString("RP TAG PLN", preP_rpTagPln);
		args.putString("ADMIN BANK", preP_admBank);
		args.putString("TOTAL BAYAR", preP_totByr);
		dp.setArguments(args);

		return dp;
	}

	public static DialogPrepaid newInstance() {
		DialogPrepaid dp = new DialogPrepaid();

		return dp;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);
		if (savedInstanceState != null) {
			preP_idPel = getArguments().getString("IDPEL");
			preP_nama = getArguments().getString("NAMA");
			preP_trfDaya = getArguments().getString("TARIF/DAYA");
			preP_blTh = getArguments().getString("BL/TH");
			preP_rpTagPln = getArguments().getString("RP TAG PLN");
			preP_admBank = getArguments().getString("ADMIN BANK");
			preP_totByr = getArguments().getString("TOTAL BAYAR");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle("DATA TAGIHAN PLN POSTPAID");
		View rootView = inflater.inflate(R.layout.dialog_prepaid, container,
				false);

		idPel = (EditText) rootView.findViewById(R.id.preP_idPel);
		nama = (EditText) rootView.findViewById(R.id.preP_nama);
		trfDaya = (EditText) rootView.findViewById(R.id.preP_trfDaya);
		blTh = (EditText) rootView.findViewById(R.id.preP_blTh);
		rpTagPln = (EditText) rootView.findViewById(R.id.preP_rpTagPln);
		admBank = (EditText) rootView.findViewById(R.id.preP_admBank);
		totByr = (EditText) rootView.findViewById(R.id.preP_totByr);

		pay = (Button) rootView.findViewById(R.id.postP_PAY);

		idPel.setText(preP_idPel);
		nama.setText(preP_nama);
		trfDaya.setText(preP_trfDaya);
		blTh.setText(preP_blTh);
		rpTagPln.setText(preP_rpTagPln);
		admBank.setText(preP_admBank);
		totByr.setText(preP_totByr);

		pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getActivity(), "Clicked",
				// Toast.LENGTH_LONG).show();

				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				// DialogFragment dp = DialogPostapaidPrint.newInstance();
				DialogFragment dp = DialogFail
						.newInstance("Gagal Melakukan Pembayaran");
				dp.show(ft, "");
				dismiss();
			}
		});

		return rootView;
	}

}
