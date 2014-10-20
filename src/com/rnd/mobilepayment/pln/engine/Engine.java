package com.rnd.mobilepayment.pln.engine;

import java.text.NumberFormat;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Engine {
	private static final String DB_NAME = "jatelindo";
	private static final int DB_VERSION = 2;

	private SQLiteDatabase db;
	private DBOpenHelper dbOpenHelper;
	private boolean isWritable;

	private boolean isModified;
	private String terminalID, acceptorID, kdBank;

	private int stan;

	/**
	 * Contructor Engine
	 * 
	 * @param context
	 */
	public Engine(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context, DB_NAME, DB_VERSION);
	}

	/**
	 * This method called to establish database.
	 * 
	 * @param writable
	 */
	private void establishDb(boolean writable) {
		if (this.db == null) {
			if (writable)
				this.db = this.dbOpenHelper.getWritableDatabase();
			else
				this.db = this.dbOpenHelper.getReadableDatabase();
		} else // sudah ada db,cek apakah writable atau tidak
		{
			if (this.isWritable == false && writable == true) {
				cleanup();
				this.db = this.dbOpenHelper.getWritableDatabase();
			}
		}
		isWritable = writable;
	}

	/**
	 * This method called to close database
	 */
	public void cleanup() {
		if (this.db != null) {
			this.db.close();
			this.db = null;
		}
	}

	/**
	 * This method called to get Terminal ID
	 * 
	 * @return Terminal ID
	 */
	public String getTerminalId() {
		if (terminalID == null || terminalID.equals("") || isModified) {
			readDatabase();
		}
		return terminalID;
	}

	/**
	 * This method called to get Acceptor ID
	 * 
	 * @return Acceptor ID
	 */
	public String getAcceptorId() {
		if (acceptorID == null || acceptorID.equals("") || isModified) {
			readDatabase();
		}
		return acceptorID;
	}

	/**
	 * This method called to get Kode Bank
	 * 
	 * @return Kode Bank
	 */
	public String getKodeBank() {
		if (kdBank == null || kdBank.equals("") || isModified) {
			readDatabase();
		}
		return kdBank;
	}

	/**
	 * This method called to set Terminal ID
	 * 
	 * @param terminalID
	 */
	public void setTerminalId(String terminalID) {
		this.terminalID = terminalID;
	}

	/**
	 * This method called to set Acceptor ID
	 * 
	 * @param acceptorId
	 */
	public void setAcceptorId(String acceptorId) {
		this.acceptorID = acceptorId;
	}

	/**
	 * This method called to set Kode Bank
	 * 
	 * @param kdBank
	 */
	public void setKodeBank(String kdBank) {
		this.kdBank = kdBank;
	}

	/**
	 * This method called to flush database.
	 */
	public void flushToDatabase() {
		writeDatabase();
	}

	/**
	 * This method called to read database.
	 */
	private void readDatabase() {
		try {
			establishDb(false);
			Cursor c = this.db.rawQuery(
					"SELECT tid,acceptorid,kdbank FROM data_pp", null);
			boolean ada = c.moveToFirst();
			if (ada) {
				terminalID = c.getString(0);
				acceptorID = c.getString(1);
				kdBank = c.getString(2);
				isModified = false;
			} else {
				terminalID = acceptorID = kdBank = null;
			}

			c.close();
			cleanup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method called to flush database.
	 */
	private void writeDatabase() {
		try {
			establishDb(true);

			this.db.delete("data_pp", null, null);

			isModified = true;

			ContentValues values = new ContentValues();
			values.put("tid", terminalID);
			values.put("acceptorid", acceptorID);
			values.put("kdbank", kdBank);
			db.insert("data_pp", null, values);

			cleanup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method called to generate stan.
	 * @return stan
	 */
	public int generateStan() {
		while (stan == 0) {
			stan = (int) (Math.random() * 999999);
		}
		stan++;
		return stan;
	}

	/**
	 * This method called to set format of money.
	 * @param obj
	 * @return Formated String
	 */
	public String moneyFormat(Object obj) {
		NumberFormat moneyFormat = NumberFormat.getInstance(Locale.GERMAN);
		return moneyFormat.format(obj);
	}
}
