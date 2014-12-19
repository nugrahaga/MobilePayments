package com.rnd.mobilepayment.printer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.rnd.mobilepayment.R;
import com.rnd.mobilepayment.adapter.ListBTAdapter;

public class PrinterActivity extends ActionBarActivity {

	public static final int REQUEST_ENABLE_BT = 1;
	public static final String MY_UUID = "00001101-0000-1000-8000-00805f9b34fb";

	private BluetoothAdapter mBluetoothAdapter;
	private Set<BluetoothDevice> pairedDevices;
	private static ArrayList<BluetoothDevice> listPairedDevice;

	private ListView listView;
	private static ListBTAdapter mAdapter;

	private String textToPrint;
	private BluetoothSocket socket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_printer);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		textToPrint = getIntent().getStringExtra("TEXT");

		if (textToPrint == null) {
			finishActivity(RESULT_CANCELED);
		}
		
		listPairedDevice = new ArrayList<BluetoothDevice>();
		listView = (ListView) findViewById(R.id.listView);
		mAdapter = new ListBTAdapter(PrinterActivity.this, listPairedDevice);
		listView.setAdapter(mAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(PrinterActivity.this,
						"Print " + listPairedDevice.get(arg2).getName(),
						Toast.LENGTH_LONG).show();
				doPrint(listPairedDevice.get(arg2));
			}
		});

		try {
			enableBluetooth();
			getPairedDevices();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Error enableBluetooth", e.toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.printer_bt, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.onBackPressed();
		case R.id.action_scan:
			Toast.makeText(PrinterActivity.this, "Clicked", Toast.LENGTH_LONG)
					.show();
			DialogScanPrinter.newInstance(mBluetoothAdapter).show(
					getSupportFragmentManager(), "");
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private synchronized void enableBluetooth() throws Exception {

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null)
			throw new Exception(
					"Maaf, perangkat anda tidak mendukung bluetooth, transaksi tidak bisa dilanjutkan");
		else {
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			} else {
				// listener.bluetoothActivation(true);
			}
		}
	}

	private synchronized void getPairedDevices() {
		pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
			// Loop through paired devices
			for (BluetoothDevice device : pairedDevices) {
				// Add the name and address to an array adapter to show in a
				// ListView
				// mArrayAdapter.add(device.getName() + "\n" +
				// device.getAddress());
				listPairedDevice.add(device);
			}
			mAdapter.notifyDataSetChanged();
		}
	}

	public static ListBTAdapter getAdapter() {
		return mAdapter;
	}

	public static ArrayList<BluetoothDevice> getListBonded() {
		return listPairedDevice;
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

	public void doPrint(BluetoothDevice device) {
//		textToPrint = "Test Print";
		try {
			socket = connectToDevice(device);

			socket.getOutputStream().write(0);
			socket.getOutputStream().write(27);
			socket.getOutputStream().write("@".getBytes());
			socket.getOutputStream().write(textToPrint.getBytes());
			socket.getOutputStream().write("\r\n\r\n\r\n".getBytes());
			socket.getOutputStream().flush();

			// showDialogFinish();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Gagal Print ", e.toString());
		}
	}

}
