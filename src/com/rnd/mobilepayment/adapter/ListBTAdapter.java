package com.rnd.mobilepayment.adapter;

import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rnd.mobilepayment.R;

public class ListBTAdapter extends BaseAdapter {
	
	private Activity activity;
    private LayoutInflater inflater;
    private List<BluetoothDevice> btDevices;
    
    public ListBTAdapter(Activity activity, List<BluetoothDevice> btDevices) {
        this.activity = activity;
        this.btDevices = btDevices;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return btDevices.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return btDevices.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (inflater == null)
            inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.bt_item, null);
        
        TextView name = (TextView) convertView.findViewById(R.id.bt_name);
        TextView mac = (TextView) convertView.findViewById(R.id.bt_mac);
        
        name.setText(btDevices.get(position).getName());
        mac.setText(btDevices.get(position).getAddress());
        
		return convertView;
	}

}
