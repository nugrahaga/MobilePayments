package com.rnd.mobilepayment.pln.engine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper 
{
	private static final String CREATE_TABLE_DATA_PP = "CREATE TABLE "
														+ "data_pp"
														+ " (tid TEXT,acceptorid TEXT,kdbank TEXT);";
		
											
	
	public DBOpenHelper(Context context, String dbName, int version) 
	{
		super(context, dbName, null, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			
			db.execSQL(DBOpenHelper.CREATE_TABLE_DATA_PP);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		//onUpgrade(db,1,3);
		super.onOpen(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS data_pp");
		this.onCreate(db);
	}
}
