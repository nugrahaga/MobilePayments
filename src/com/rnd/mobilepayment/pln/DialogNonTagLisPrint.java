package com.rnd.mobilepayment.pln;

import com.rnd.mobilepayment.R;

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

public class DialogNonTagLisPrint extends DialogFragment {

	private TextView nonTagLisPrint_namaPel;
	private TextView nonTagLisPrint_transaksi;
	// private TextView nonTagLisPrint_rpBayar;
	private TextView nonTagLisPrint_noReg;
	private TextView nonTagLisPrint_tglReg;
	private TextView nonTagLisPrint_idPel;
	private TextView nonTagLisPrint_biayaPLN;
	private TextView nonTagLisPrint_noRef;
	private TextView nonTagLisPrint_admCA;
	private TextView nonTagLisPrint_totByr;
	private Button nonTagLisPrint_cetak;

	private String nama;
	private String transaksi;
	// private String rpBayar;
	private String noReg;
	private String tglReg;
	private String idPel;
	private String biayaPLN;
	private String noRef;
	private String admCA;
	private String totByr;

	/**
	 * Create a new instance of DialogNonTagLisPrint.
	 */

	public static DialogNonTagLisPrint newInstance(String nama,
			String transaksi, String noReg, String tglReg, String idPel,
			String biayaPLN, String noRef, String admCA, String totByr) {

		DialogNonTagLisPrint dp = new DialogNonTagLisPrint();
		Bundle args = new Bundle();
		args.putString("NAMA", nama);
		args.putString("TRANSAKSI", transaksi);
		args.putString("NO REGISTRASI", noReg);
		args.putString("TGL REGISTRASI", tglReg);
		args.putString("IDPEL", idPel);
		args.putString("BIAYA PLN", biayaPLN);
		args.putString("JPAREF", noRef);
		args.putString("ADMIN BANK", admCA);
		args.putString("TOTAL BAYAR", totByr);
		dp.setArguments(args);

		return dp;
	}

	public static DialogNonTagLisPrint newInstance() {
		DialogNonTagLisPrint dp = new DialogNonTagLisPrint();

		return dp;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog);

		this.nama = getArguments().getString("NAMA");
		this.transaksi = getArguments().getString("TRANSAKSI");
		// this.rpBayar = rpBayar;
		this.noReg = getArguments().getString("NO REGISTRASI");
		this.tglReg = getArguments().getString("TGL REGISTRASI");
		this.idPel = getArguments().getString("IDPEL");
		this.biayaPLN = getArguments().getString("BIAYA PLN");
		this.noRef = getArguments().getString("JPAREF");
		this.admCA = getArguments().getString("ADMIN BANK");
		this.totByr = getArguments().getString("TOTAL BAYAR");

		Log.e("onCreate Dialog Print", idPel + " - " + nama);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_nontaglis_print,
				container, false);

		nonTagLisPrint_namaPel = (TextView) rootView
				.findViewById(R.id.nonTagLisPrint_namaPel);
		nonTagLisPrint_transaksi = (TextView) rootView
				.findViewById(R.id.nonTagLisPrint_transaksi);
		// private TextView nonTagLisPrint_rpBayar = (TextView) rootView
		// .findViewById(R.id.nonTagLisPrint_rpBayar);
		nonTagLisPrint_noReg = (TextView) rootView
				.findViewById(R.id.nonTagLisPrint_noReg);
		nonTagLisPrint_tglReg = (TextView) rootView
				.findViewById(R.id.nonTagLisPrint_tglReg);
		nonTagLisPrint_idPel = (TextView) rootView
				.findViewById(R.id.nonTagLisPrint_idPel);
		nonTagLisPrint_biayaPLN = (TextView) rootView
				.findViewById(R.id.nonTagLisPrint_biayaPLN);
		nonTagLisPrint_noRef = (TextView) rootView
				.findViewById(R.id.nonTagLisPrint_noRef);
		nonTagLisPrint_admCA = (TextView) rootView
				.findViewById(R.id.nonTagLisPrint_admCA);
		nonTagLisPrint_totByr = (TextView) rootView
				.findViewById(R.id.nonTagLisPrint_totByr);
		nonTagLisPrint_cetak = (Button) rootView
				.findViewById(R.id.nonTagLisPrint_cetak);

		nonTagLisPrint_namaPel.setText(nama);
		nonTagLisPrint_transaksi.setText(transaksi);
		nonTagLisPrint_noReg.setText(noReg);
		nonTagLisPrint_tglReg.setText(tglReg);
		nonTagLisPrint_idPel.setText(idPel);
		nonTagLisPrint_biayaPLN.setText(biayaPLN);
		nonTagLisPrint_noRef.setText(noRef);
		nonTagLisPrint_admCA.setText(admCA);
		nonTagLisPrint_totByr.setText(totByr);

		nonTagLisPrint_cetak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG)
						.show();
			}
		});

		return rootView;
	}
}
