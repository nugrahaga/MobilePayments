package com.rnd.mobilepayment.printer;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.rnd.mobilepayment.R;

public class PrinterModule {

	public static final int REQUEST_ENABLE_BT = 1;
	public static final String MY_UUID = "00001101-0000-1000-8000-00805f9b34fb";

	private static BluetoothDevice printerDevice;

	private BluetoothAdapter mBluetoothAdapter;
	private ListView listView;
	private BluetoothDiscoveryAdapter adapter;
	private ArrayList<BluetoothDevice> listDevice;

	private ProgressBar mProgressBar;
	private Button scan;

	private PrinterModuleListener listener;

	public PrinterModule(PrinterModuleListener listener) {
		this.listener = listener;
	}

	public synchronized void ensureBluetoothIsActive(Activity activity)
			throws Exception {

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null)
			throw new Exception(
					"Maaf, perangkat anda tidak mendukung bluetooth, transaksi tidak bisa dilanjutkan");
		else {
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				activity.startActivityForResult(enableBtIntent,
						REQUEST_ENABLE_BT);
			} else
				listener.bluetoothActivation(true);
		}
	}

	public synchronized void getPrinterDevice(Activity activity, boolean cached)
			throws Exception {

		if (printerDevice == null || !cached) {
			discoveringDevice(activity, listener);
			// wait();
		} else
			listener.printerDeviceSelected(printerDevice, true);
	}

	private void discoveringDevice(Activity activity,
			final PrinterModuleListener listener) {
		listDevice = new ArrayList<BluetoothDevice>();

		IntentFilter filter1 = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		IntentFilter filter2 = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		// Don't forget to unregister during onDestroy
		activity.registerReceiver(mReceiver, filter1);
		activity.registerReceiver(mReceiver, filter2);
		activity.registerReceiver(mReceiver, filter3);

		final Dialog dialog = new Dialog(activity) {
			public void onBackPressed() {
				mBluetoothAdapter.cancelDiscovery();
				dismiss();
			}

		};

		dialog.setContentView(R.layout.bt_dialog_discovering);
		dialog.setTitle("Silahkan pilih perangkat printer bluetooth");
		dialog.setCancelable(false);

		mProgressBar = (ProgressBar) dialog.findViewById(R.id.bt_scanProgress);

		scan = (Button) dialog.findViewById(R.id.btnScan);
		scan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mBluetoothAdapter.isDiscovering())
					mBluetoothAdapter.startDiscovery();
			}
		});

		listView = (ListView) dialog.findViewById(R.id.listview);

		adapter = new BluetoothDiscoveryAdapter(activity,
				R.layout.bt_adapter_discovering, listDevice);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int i, long arg3) {
				// TODO Auto-generated method stub

				printerDevice = listDevice.get(i);
				dialog.dismiss();
				listener.printerDeviceSelected(printerDevice, false);
			}
		});
		dialog.show();

		mBluetoothAdapter.startDiscovery();
	}

	public BluetoothSocket connectToDevice(BluetoothDevice device)
			throws Exception {
		if (mBluetoothAdapter != null)
			mBluetoothAdapter.cancelDiscovery();
		Method m = device.getClass().getMethod("createRfcommSocket",
				new Class[] { int.class });
		BluetoothSocket socket = (BluetoothSocket) m.invoke(device, 1);

		// BluetoothSocket
		// socket=device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
		socket.connect();
		return socket;
	}

	public void resetCache() {
		printerDevice = null;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			synchronized (this) {
				if (resultCode == Activity.RESULT_OK)
					listener.bluetoothActivation(true);
				else
					listener.bluetoothActivation(false);
				notifyAll();
			}

		}
	}

	// Create a BroadcastReceiver for ACTION_FOUND
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// Add the name and address to an array adapter to show in a
				// ListView
				adapter.add(device);
				adapter.notifyDataSetChanged();
			} else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				adapter.clear();
				adapter.notifyDataSetChanged();
				scan.setText("Sedang mencari perangkat printer...");
				mProgressBar.setVisibility(View.VISIBLE);
				scan.setEnabled(false);
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				scan.setText("Scan perangkat printer");
				mProgressBar.setVisibility(View.GONE);
				scan.setEnabled(true);
			}

		}
	};
}
