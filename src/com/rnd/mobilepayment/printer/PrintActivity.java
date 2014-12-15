package com.rnd.mobilepayment.printer;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.rnd.mobilepayment.R;

public class PrintActivity extends ActionBarActivity implements
		PrinterModuleListener {

	public static final int REQUEST_ENABLE_BT = 1;
	private PrinterModule module;
	private TextView status;
	private String textPrint;
	private BluetoothSocket socket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_print);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		status = (TextView) findViewById(R.id.status);
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		textPrint = getIntent().getStringExtra("TEXT");

		if (textPrint == null) {
			finishActivity(RESULT_CANCELED);
		}

		status.setText("Sedang proses cetak struk...");

		if (module == null) {
			module = new PrinterModule(this);
		}

		try {
			module.ensureBluetoothIsActive(PrintActivity.this);
		} catch (Exception e) {
			// TODO: handle exception
			status.setText("Gagal cetak: " + e.getMessage());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		module.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void bluetoothActivation(boolean active) {
		// TODO Auto-generated method stub
		if (active) {
			try {
				module.getPrinterDevice(PrintActivity.this, false);
			} catch (Exception e) {
				Toast.makeText(PrintActivity.this, e.toString(),
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		} else {
			status.setText("Bluetooth anda harus diaktifkan untuk mencetak struk..");
		}
	}

	@Override
	public void printerDeviceSelected(BluetoothDevice device, boolean cached) {
		// TODO Auto-generated method stub
		try {
			socket = module.connectToDevice(device);

			socket.getOutputStream().write(0);
			socket.getOutputStream().write(27);
			socket.getOutputStream().write("@".getBytes());
			socket.getOutputStream().write(textPrint.getBytes());
			socket.getOutputStream().write("\r\n\r\n\r\n".getBytes());
			socket.getOutputStream().flush();

			showDialogFinish();
		} catch (Exception e) {
			// TODO: handle exception
			status.setText("Gagal cetak struk: " + e);
		}
	}

	private void showDialogFinish() {
		new AlertDialog.Builder(this)
				.setTitle("Status cetak struk")
				.setMessage("Apakah struk telah tercetak?")
				.setNegativeButton("Ulangi lagi",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								try {
									try {
										socket.close();
									} catch (Exception e) {
									}
									module.ensureBluetoothIsActive(PrintActivity.this);
								} catch (Exception e) {
									status.setText("Gagal cetak: "
											+ e.getMessage());
								}
							}

						})
				.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						try {
							socket.close();
						} catch (Exception e) {
						}
						PrintActivity.this.finishActivity(0);
					}

				}).show();
	}

}
