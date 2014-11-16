package com.rnd.mobilepayment.adapter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.rnd.mobilepayment.R;
import com.rnd.mobilepayment.model.History;

public class HistoryAdapter extends ArrayAdapter<ArrayList<History>> {

	private final Context context;
	private LayoutInflater inflater;
	private final ArrayList<History> data;

	public HistoryAdapter(Context context, int resource, ArrayList<History> data) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null)
			convertView = inflater.inflate(R.layout.history_item, null);

		ImageView historyThumbnail = (ImageView) convertView
				.findViewById(R.id.historyThumbnail);
		TextView historyTitle = (TextView) convertView
				.findViewById(R.id.historyTitle);
		TextView historyJenis = (TextView) convertView
				.findViewById(R.id.historyJenis);
		TextView historyStatus = (TextView) convertView
				.findViewById(R.id.historyStatus);
		TextView historyDate = (TextView) convertView
				.findViewById(R.id.historyDate);

		switch (data.get(position).getType()) {
		case 0:
			historyThumbnail.setImageResource(R.drawable.pln);
			break;

		default:
			break;
		}

		historyTitle.setText(data.get(position).getName());
		historyJenis.setText(data.get(position).getTransaction());
		historyStatus.setText(data.get(position).getStatus());
		historyDate.setText(data.get(position).getStatus());

		return convertView;
	}

}
