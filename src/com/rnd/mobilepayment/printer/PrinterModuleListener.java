package com.rnd.mobilepayment.printer;

import android.bluetooth.BluetoothDevice;

public interface PrinterModuleListener {
	public void bluetoothActivation(boolean active);

	public void printerDeviceSelected(BluetoothDevice device, boolean cached);
}
