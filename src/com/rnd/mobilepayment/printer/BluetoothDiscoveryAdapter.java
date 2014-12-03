package com.rnd.mobilepayment.printer;

import java.util.ArrayList;
import java.util.List;

import com.rnd.mobilepayment.R;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BluetoothDiscoveryAdapter extends ArrayAdapter<BluetoothDevice> {

	private ArrayList<BluetoothDevice> listBTDevice;
	private final Context context;
	private LayoutInflater inflater;

	public BluetoothDiscoveryAdapter(Context context, int resource,
			List<BluetoothDevice> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listBTDevice = (ArrayList<BluetoothDevice>) objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null)
			convertView = inflater.inflate(R.layout.bt_adapter_discovering, null);
		
		TextView bt = (TextView) convertView.findViewById(R.id.firstLine);
		BluetoothDevice btDevice = listBTDevice.get(position);
		bt.setText(btDevice.getName());

		return convertView;
	}
}
