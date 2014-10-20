package com.rnd.mobilepayment.adapter;

import java.util.ArrayList;

import com.rnd.mobilepayment.R;
import com.rnd.mobilepayment.model.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListViewHistoryAdapter extends ArrayAdapter<ArrayList<History>> {

	private final Context context;
	private final ArrayList<History> data;

	public ListViewHistoryAdapter(Context context, int resource,
			ArrayList<History> data) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.history_list_item, parent,
				false);
		TextView textNo = (TextView) rowView.findViewById(R.id.historyNumber);
		textNo.setText(data.get(position).getNo());

		return rowView;
	}

}
