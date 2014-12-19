package com.rnd.mobilepayment.printer;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

public class PrinterHelper {

	public static final int REQUEST_ENABLE_BT = 1;
	public static final String MY_UUID = "00001101-0000-1000-8000-00805f9b34fb";

	private BluetoothAdapter mBluetoothAdapter;
	private static BluetoothDevice printerDevice;
	
	private Set<BluetoothDevice> pairedDevices;
	private ArrayList<BluetoothDevice> listPairedDevices = new ArrayList<BluetoothDevice>();
	
	
	private PrinterModuleListener listener;

	public PrinterHelper(PrinterModuleListener listener) {
		// TODO Auto-generated constructor stub
		this.listener = listener;
	}

	public synchronized void enableBluetooth(Activity activity)
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

	public synchronized ArrayList<BluetoothDevice> getPairedDevice() throws Exception {
		pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
//		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		    	listPairedDevices.add(device);
		    }
		    return listPairedDevices;
		}
		
		return null;
	}
}
