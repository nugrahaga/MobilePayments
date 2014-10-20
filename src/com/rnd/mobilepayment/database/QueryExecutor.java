package com.rnd.mobilepayment.database;

import android.database.sqlite.SQLiteDatabase;

public interface QueryExecutor {

	public void run(SQLiteDatabase database);
}
