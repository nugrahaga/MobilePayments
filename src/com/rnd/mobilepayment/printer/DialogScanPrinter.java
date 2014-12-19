package com.rnd.mobilepayment.printer;

import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rnd.mobilepayment.R;

public class DialogScanPrinter extends DialogFragment {

	private static BluetoothAdapter mBluetoothAdapter;

	private ListView listView;
	private BluetoothDiscoveryAdapter adapter;
	private ArrayList<BluetoothDevice> listDevice = new ArrayList<BluetoothDevice>();

	private ProgressBar bt_scanProgress;

	// Create a BroadcastReceiver for ACTION_FOUND
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				bt_scanProgress.setVisibility(View.VISIBLE);
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				// discovery finishes, dismis progress dialog
				bt_scanProgress.setVisibility(View.GONE);
			} else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// Add the name and address to an array adapter to show in a
				// ListView
				adapter.add(device);
				adapter.notifyDataSetChanged();
			}
		}

	};

	public static DialogScanPrinter newInstance(BluetoothAdapter mAdapter) {
		DialogScanPrinter dp = new DialogScanPrinter();
		mBluetoothAdapter = mAdapter;

		return dp;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle("SCAN PRINTER");
		View rootView = inflater.inflate(R.layout.bt_discover_dialog,
				container, false);

		bt_scanProgress = (ProgressBar) rootView
				.findViewById(R.id.bt_scanProgress);
		bt_scanProgress.setVisibility(View.GONE);

		listView = (ListView) rootView.findViewById(R.id.listview);
		adapter = new BluetoothDiscoveryAdapter(getActivity(),
				R.layout.bt_adapter_discovering, listDevice);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "List Clicked", Toast.LENGTH_LONG)
						.show();
				ArrayList<BluetoothDevice> tmp = PrinterActivity
						.getListBonded();
				if (tmp.contains(listDevice.get(arg2))) {
					dismiss();
				} else {
					PrinterActivity.getListBonded().add(listDevice.get(arg2));
					PrinterActivity.getAdapter().notifyDataSetChanged();
				}
				dismiss();
			}
		});

		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter();

		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

		getActivity().registerReceiver(mReceiver, filter); // Don't forget to
															// unregister during
															// onDestroy
		mBluetoothAdapter.startDiscovery();

		return rootView;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(mReceiver);
		super.onDestroy();
	}

}
