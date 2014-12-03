package com.rnd.mobilepayment.general;

import java.util.Set;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.rnd.mobilepayment.R;

public class PrinterSettingFragment extends FragmentActivity {

	private ProgressDialog pDialog;

	private Switch bluetoothSwitch;
	private ListView listAttachedPrinter;
	private ListView listDetectedDevices;
	private Button printerScan;

	private BluetoothAdapter mBluetoothAdapter;
	private Set<BluetoothDevice> pairedDevices;
	private ArrayAdapter<String> BTArrayAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.dialog_printer_setting);
		
		//Setup bluetooth
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// Set up action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Switch
		bluetoothSwitch = (Switch) findViewById(R.id.bluetoothSwitch);

		if (mBluetoothAdapter.isEnabled()) {
			bluetoothSwitch.setChecked(true);
		} else {
			bluetoothSwitch.setChecked(false);
		}

		bluetoothSwitch
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							// Toast.makeText(getApplicationContext(), "ON",
							// Toast.LENGTH_LONG).show();
							if (!mBluetoothAdapter.isEnabled()) {
								Intent enableBtIntent = new Intent(
										BluetoothAdapter.ACTION_REQUEST_ENABLE);
								startActivityForResult(enableBtIntent, 1);
							}
							printerScan.setEnabled(true);
						} else {
							mBluetoothAdapter.disable();
							printerScan.setEnabled(false);
						}
					}
				});

		// List Paired
		listAttachedPrinter = (ListView) findViewById(R.id.listAttachedPrinter);
		BTArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		listAttachedPrinter.setAdapter(BTArrayAdapter);
		getPairedDevices();

		// Button
		printerScan = (Button) findViewById(R.id.printerScan);
		if (bluetoothSwitch.isChecked())
			printerScan.setEnabled(true);
		else
			printerScan.setEnabled(false);

		printerScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				scanDevices();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	public void getPairedDevices() {
		pairedDevices = mBluetoothAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			// Loop through paired devices
			for (BluetoothDevice device : pairedDevices) {
				// Add the name and address to an array adapter to show in a
				// ListView
				BTArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress());

			}
			BTArrayAdapter.notifyDataSetChanged();
		}
		Toast.makeText(getApplicationContext(), "Show Paired Devices",
				Toast.LENGTH_SHORT).show();
	}

	// Create a BroadcastReceiver for ACTION_FOUND
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				// discovery starts, we can show progress dialog or perform
				// other tasks
				pDialog = new ProgressDialog(PrinterSettingFragment.this);
				pDialog.setMessage("SEARCHING...");
				pDialog.setCancelable(false);
				pDialog.show();
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				// discovery finishes, dismis progress dialog
				pDialog.dismiss();
			} else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// Add the name and address to an array adapter to show in a
				// ListView
				// mArrayAdapter.add(device.getName() + "\n" +
				// device.getAddress());
//				Toast.makeText(PrinterSettingFragment.this,
//						device.getName() + "\n" + device.getAddress(),
//						Toast.LENGTH_LONG).show();
			}
		}
	};

	public void scanDevices() {
		mBluetoothAdapter.startDiscovery();
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		
		registerReceiver(mReceiver, filter);
	}
}
