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

public class DialogPrepaidPrint extends DialogFragment {

	private TextView prePrint_noMeter;
	private TextView prePrint_idPel;
	private TextView prePrint_namaPel;
	private TextView prePrint_trfDaya;
	private TextView prePrint_noRef;
	private TextView prePrint_rpBayar;
	private TextView prePrint_admCA;
	private TextView prePrint_materai;
	private TextView prePrint_ppn;
	private TextView prePrint_ppj;
	private TextView prePrint_angsuran;
	private TextView prePrint_rpStroomToken;
	private TextView prePrint_jmlKwh;
	private TextView prePrint_strToken_Value;
	private Button prePrint_cetak;

	private String noMeter;
	private String idPel;
	private String namaPel;
	private String trfDaya;
	private String noRef;
	private String rpBayar;
	private String admCA;
	private String materai;
	private String ppn;
	private String ppj;
	private String angsuran;
	private String rpStroomToken;
	private String jmlKwh;
	private String strToken_Value;

	/**
	 * Create a new instance of DialogPreapaidPrint.
	 */

	public static DialogPrepaidPrint newInstance(String noMeter, String idPel,
			String namaPel, String trfDaya, String noRef, String rpBayar,
			String admCA, String materai, String ppn, String ppj,
			String angsuran, String rpStroomToken, String jmlKwh,
			String strToken_Value) {

		DialogPrepaidPrint dp = new DialogPrepaidPrint();
		Bundle args = new Bundle();
		args.putString("NO METER", noMeter);
		args.putString("IDPEL", idPel);
		args.putString("NAMA", namaPel);
		args.putString("TARIF/DAYA", trfDaya);
		args.putString("NOREF", noRef);
		args.putString("RP BAYAR", rpBayar);
		args.putString("ADM BANK", admCA);
		args.putString("MATERAI", materai);
		args.putString("PPN", ppn);
		args.putString("PPJ", ppj);
		args.putString("ANGSURAN", angsuran);
		args.putString("RP STROOM TOKEN", rpStroomToken);
		args.putString("JML KWH", jmlKwh);
		args.putString("STR TOKEN", strToken_Value);

		dp.setArguments(args);

		return dp;
	}

	public static DialogPrepaidPrint newInstance() {
		DialogPrepaidPrint dp = new DialogPrepaidPrint();

		return dp;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog);

		noMeter = getArguments().getString("NO METER");
		idPel = getArguments().getString("IDPEL");
		namaPel = getArguments().getString("NAMA");
		trfDaya = getArguments().getString("TARIF/DAYA");
		noRef = getArguments().getString("NOREF");
		rpBayar = getArguments().getString("RP BAYAR");
		admCA = getArguments().getString("ADM BANK");
		materai = getArguments().getString("MATERAI");
		ppn = getArguments().getString("PPN");
		ppj = getArguments().getString("PPJ");
		angsuran = getArguments().getString("ANGSURAN");
		rpStroomToken = getArguments().getString("RP STROOM TOKEN");
		jmlKwh = getArguments().getString("JML KWH");
		strToken_Value = getArguments().getString("STR TOKEN");

		Log.e("onCreate Dialog Print", noMeter + " - " + idPel + " - "
				+ namaPel);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_prepaid_print,
				container, false);

		prePrint_noMeter = (TextView) rootView
				.findViewById(R.id.prePrint_noMeter);
		prePrint_idPel = (TextView) rootView
				.findViewById(R.id.prePrint_idPel);
		prePrint_namaPel = (TextView) rootView
				.findViewById(R.id.prePrint_namaPel);
		prePrint_trfDaya = (TextView) rootView
				.findViewById(R.id.prePrint_trfDaya);
		prePrint_noRef = (TextView) rootView
				.findViewById(R.id.prePrint_noRef);
		prePrint_rpBayar = (TextView) rootView
				.findViewById(R.id.prePrint_rpBayar);
		prePrint_admCA = (TextView) rootView
				.findViewById(R.id.prePrint_admCA);
		prePrint_materai = (TextView) rootView
				.findViewById(R.id.prePrint_materai);
		prePrint_ppn = (TextView) rootView.findViewById(R.id.prePrint_ppn);
		prePrint_ppj = (TextView) rootView.findViewById(R.id.prePrint_ppj);
		prePrint_angsuran = (TextView) rootView
				.findViewById(R.id.prePrint_angsuran);
		prePrint_rpStroomToken = (TextView) rootView
				.findViewById(R.id.prePrint_rpStroomToken);
		prePrint_jmlKwh = (TextView) rootView
				.findViewById(R.id.prePrint_jmlKwh);
		prePrint_strToken_Value = (TextView) rootView
				.findViewById(R.id.prePrint_strToken_Value);
		prePrint_cetak = (Button) rootView.findViewById(R.id.prePrint_cetak);
		
		prePrint_noMeter.setText("NO METER : "+noMeter);
		prePrint_idPel.setText("IDPEL : "+idPel);
		prePrint_namaPel.setText("NAMA : "+namaPel);
		prePrint_trfDaya.setText("TARIF / DAYA : "+trfDaya+" VA");
		prePrint_noRef.setText("JPA REF : "+noRef);
		prePrint_rpBayar.setText("RP BAYAR : Rp. "+rpBayar);
		prePrint_admCA.setText("ADMIN BANK : Rp. "+admCA);
		prePrint_materai.setText("MATERAI : Rp. "+materai);
		prePrint_ppn.setText("PPn : Rp. "+ppn);
		prePrint_ppj.setText("PPJ : Rp. "+ppj);
		prePrint_angsuran.setText("ANGSURAN : Rp. "+angsuran);
		prePrint_rpStroomToken.setText("RP STROOM/TOKEN : Rp. "+rpStroomToken);
		prePrint_jmlKwh.setText("JML KWH : "+jmlKwh);
		prePrint_strToken_Value.setText(strToken_Value);
		prePrint_cetak.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
			}
		});
		
		
		return rootView;
	}
}
