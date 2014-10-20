package com.rnd.mobilepayment.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rnd.mobilepayment.R;
import com.rnd.mobilepayment.model.PaymentPoint;

public class DatabaseDAO {

	public interface Table {
		// data_pp
		String COLUMN_ID = "id";
		String COLUMN_TERMINAL_ID = "tid";
		String COLUMN_ACCEPTOR_ID = "acceptorid";
		String COLUMN_KODE_BANK = "kdbank";
	}

	private SQLiteDatabase mDatabase;
	private Context mContext;

	public DatabaseDAO(SQLiteDatabase database, Context context) {
		// TODO Auto-generated constructor stub
		mDatabase = database;
		mContext = context;
	}

	public static String getCreateTable(Context context) {
		return context.getString(R.string.create_table_data_pp);
	}

	public static String getDropTable(Context context) {
		return context.getString(R.string.drop_table_data_pp);
	}

	public void deleteAllCustomer() {
		mDatabase.execSQL(mContext.getString(R.string.delete_all_data_pp));
	}

	public void insert_pp(List<PaymentPoint> ppList) {

		for (PaymentPoint pp : ppList) {
			String[] bindArgs = { pp.getTid(), pp.getAcceptorid(),
					pp.getKdbank() };
			mDatabase.execSQL(mContext.getString(R.string.insert_pp), bindArgs);
		}
	}

	public void insert_pp(PaymentPoint pp) {
		String[] bindArgs = { pp.getTid(), pp.getAcceptorid(), pp.getKdbank() };
		mDatabase.execSQL(mContext.getString(R.string.insert_pp), bindArgs);
	}

	public PaymentPoint selectPaymentPoint(String tid) {
		String[] selectionArgs = { tid };
		String query = mContext.getString(R.string.select_pp);
		Cursor cursor = mDatabase.rawQuery(query, selectionArgs);

		List<PaymentPoint> dataList = manageCursor(cursor);

		closeCursor(cursor);

		return dataList.get(0);
	}

	public List<PaymentPoint> selectAllPaymentPoint() {
		String[] selectionArgs = {};
		String query = mContext.getString(R.string.select_all_pp);
		Cursor cursor = mDatabase.rawQuery(query, selectionArgs);

		List<PaymentPoint> dataList = manageCursor(cursor);

		closeCursor(cursor);

		return dataList;
	}

	protected void closeCursor(Cursor cursor) {
		if (cursor != null) {
			cursor.close();
		}
	}

	protected List<PaymentPoint> manageCursor(Cursor cursor) {
		List<PaymentPoint> dataList = new ArrayList<PaymentPoint>();

		if (cursor != null) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				PaymentPoint pp = cursorToData(cursor);
				dataList.add(pp);
				cursor.moveToNext();
			}
		}
		return dataList;
	}

	protected PaymentPoint cursorToData(Cursor cursor) {
		int idIndex = cursor.getColumnIndex(Table.COLUMN_ID);
		int tidIndex = cursor.getColumnIndex(Table.COLUMN_TERMINAL_ID);
		int acceptoridIndex = cursor.getColumnIndex(Table.COLUMN_ACCEPTOR_ID);
		int kdbankIndex = cursor.getColumnIndex(Table.COLUMN_KODE_BANK);

		PaymentPoint pp = new PaymentPoint();
		pp.setId(cursor.getString(idIndex));
		pp.setTid(cursor.getString(tidIndex));
		pp.setAcceptorid(cursor.getString(acceptoridIndex));
		pp.setKdbank(cursor.getString(kdbankIndex));

		return pp;
	}

}
