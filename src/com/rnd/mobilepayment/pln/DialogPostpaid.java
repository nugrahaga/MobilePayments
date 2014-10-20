package com.rnd.mobilepayment.pln;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.rnd.mobilepayment.R;

public class DialogPostpaid extends DialogFragment {

	private String postP_idPel;
	private String postP_nama;
	private String postP_totTag;
	private String postP_trfDaya;
	private String postP_blTh;
	private String postP_rpTagPln;
	private String postP_admBank;
	private String postP_totByr;

	private EditText idPel;
	private EditText nama;
	private EditText totTag;
	private EditText trfDaya;
	private EditText blTh;
	private EditText rpTagPln;
	private EditText admBank;
	private EditText totByr;
	private Button pay;

	/**
	 * Create a new instance of DialogPostpaid, providing "num" as an
	 * argument.
	 */
	public static DialogPostpaid newInstance(String postP_idPel,
			String postP_nama, String postP_totTag, String postP_trfDaya,
			String postP_blTh, String postP_rpTagPln, String postP_admBank,
			String postP_totByr) {
		DialogPostpaid dp = new DialogPostpaid();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString("IDPEL", postP_idPel);
		args.putString("NAMA", postP_nama);
		args.putString("TOTAL TAGIHAN", postP_totTag);
		args.putString("TARIF/DAYA", postP_trfDaya);
		args.putString("BL/TH", postP_blTh);
		args.putString("RP TAG PLN", postP_rpTagPln);
		args.putString("ADMIN BANK", postP_admBank);
		args.putString("TOTAL BAYAR", postP_totByr);
		dp.setArguments(args);

		return dp;
	}

	public static DialogPostpaid newInstance() {
		DialogPostpaid dp = new DialogPostpaid();

		return dp;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);
		if (savedInstanceState != null) {
			postP_idPel = getArguments().getString("IDPEL");
			postP_nama = getArguments().getString("NAMA");
			postP_totTag = getArguments().getString("TOTAL TAGIHAN");
			postP_trfDaya = getArguments().getString("TARIF/DAYA");
			postP_blTh = getArguments().getString("BL/TH");
			postP_rpTagPln = getArguments().getString("RP TAG PLN");
			postP_admBank = getArguments().getString("ADMIN BANK");
			postP_totByr = getArguments().getString("TOTAL BAYAR");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle("DATA TAGIHAN PLN POSTPAID");
		View rootView = inflater.inflate(R.layout.dialog_postpaid, container,
				false);

		idPel = (EditText) rootView.findViewById(R.id.postP_idPel);
		nama = (EditText) rootView.findViewById(R.id.postP_nama);
		totTag = (EditText) rootView.findViewById(R.id.postP_totTag);
		trfDaya = (EditText) rootView.findViewById(R.id.postP_trfDaya);
		blTh = (EditText) rootView.findViewById(R.id.postP_blTh);
		rpTagPln = (EditText) rootView.findViewById(R.id.postP_rpTagPln);
		admBank = (EditText) rootView.findViewById(R.id.postP_admBank);
		totByr = (EditText) rootView.findViewById(R.id.postP_totByr);

		pay = (Button) rootView.findViewById(R.id.postP_PAY);

		idPel.setText(postP_idPel);
		nama.setText(postP_nama);
		totTag.setText(postP_totTag);
		trfDaya.setText(postP_trfDaya);
		blTh.setText(postP_blTh);
		rpTagPln.setText(postP_rpTagPln);
		admBank.setText(postP_admBank);
		totByr.setText(postP_totByr);

		pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getActivity(), "Clicked",
				// Toast.LENGTH_LONG).show();

				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
//				DialogFragment dp = DialogPostapaidPrint.newInstance();
				DialogFragment dp = DialogFail.newInstance("Gagal Melakukan Pembayaran");
				dp.show(ft, "");
				dismiss();
			}
		});

		return rootView;
	}
}
