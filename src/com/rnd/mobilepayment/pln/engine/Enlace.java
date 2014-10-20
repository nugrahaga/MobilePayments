package com.rnd.mobilepayment.pln.engine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.util.HashMap;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import jpa.jpos.iso.ISOMsg;
import android.content.Context;
import android.util.Log;

public abstract class Enlace {
	protected Engine engine;
	private SSLSocketFactory sslsocketfactory;

	/**
	 * Constructor for Enlace
	 * 
	 * @param ctx
	 */
	public Enlace(Context ctx) {
		engine = new Engine(ctx);
	}

	/**
	 * This method called to do User Registration.
	 * 
	 * @param ip
	 * @param port
	 * @param idpetugas
	 * @param password
	 * @param konfPassword
	 * @param namaPP
	 * @param alamatPP
	 * @param notelp
	 * @param email
	 * @param keterangan
	 * @param imei
	 * @return HashMap<String, String> of response
	 */
	public HashMap<String, String> doRegister(String ip, int port,
			String idpetugas, String password, String konfPassword,
			String namaPP, String alamatPP, String notelp, String email,
			String keterangan, String imei) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.setMTI("0100");
			msg.set(48,
					String.format("%1$-25s", imei)
							+ String.format("%1$-25s", idpetugas)
							+ String.format("%1$-32s", MD5(password))
							+ String.format("%1$-50s", namaPP)
							+ String.format("%1$-150s", alamatPP)
							+ String.format("%1$-25s", notelp)
							+ String.format("%1$-35s", email)
							+ String.format("%1$-8s", "") + // tid
							String.format("%1$-25s", "") + // kodebank
							String.format("%1$-50s", "") + // namaBank
							String.format("%1$-150s", keterangan));
			msg.set(70, "101");

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			value.put("RC", rc);
			value.put("RESPONSE_MSG", msg.getString(60));

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof SocketTimeoutException) {
				value.put("RC", "18");
				value.put("RESPONSE_MSG", getRCMessage("18"));
			} else if (e instanceof SocketException) {
				value.put("RC", "13");
				value.put("RESPONSE_MSG", getRCMessage("13"));
			} else {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
			}
		}
		return value;
	}

	/**
	 * This method called to do Activation Request
	 * 
	 * @param ip
	 * @param port
	 * @param idpetugas
	 * @param password
	 * @param kdbank
	 * @param tid
	 * @param namaBank
	 * @param namaPP
	 * @param alamatPP
	 * @param notelp
	 * @param email
	 * @param imei
	 * @return HashMap<String, String> of response
	 */
	public HashMap<String, String> doActivation(String ip, int port,
			String idpetugas, String password, String kdbank, String tid,
			String namaBank, String namaPP, String alamatPP, String notelp,
			String email, String imei) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.setMTI("0100");
			msg.set(48,
					String.format("%1$-25s", imei)
							+ String.format("%1$-25s", idpetugas)
							+ String.format("%1$-32s", MD5(password))
							+ String.format("%1$-50s", namaPP)
							+ String.format("%1$-150s", alamatPP)
							+ String.format("%1$-25s", notelp)
							+ String.format("%1$-35s", email)
							+ String.format("%1$-8s", tid) + // tid
							String.format("%1$-25s", kdbank) + // kodebank
							String.format("%1$-50s", namaBank) + // namaBank
							String.format("%1$-150s", ""));// keterangan
			msg.set(70, "101");

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			value.put("RC", rc);
			value.put("RESPONSE_MSG", msg.getString(60));

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof SocketTimeoutException) {
				value.put("RC", "18");
				value.put("RESPONSE_MSG", getRCMessage("18"));
			} else if (e instanceof SocketException) {
				value.put("RC", "13");
				value.put("RESPONSE_MSG", getRCMessage("13"));
			} else {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
			}
		}
		return value;
	}

	/**
	 * This method called to do Login
	 * 
	 * @param ip
	 * @param port
	 * @param idpetugas
	 * @param password
	 * @param versi
	 * @param imei
	 * @return HashMap<String, String> of response
	 */
	public HashMap<String, String> doLogin(String ip, int port,
			String idpetugas, String password, String versi, String imei) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.setMTI("0100");
			msg.set(32, engine.getKodeBank());
			msg.set(41, engine.getTerminalId());
			msg.set(42, engine.getAcceptorId());
			msg.set(48,
					String.format("%1$-25s", imei)
							+ String.format("%1$-25s", idpetugas)
							+ String.format("%1$-32s", MD5(password))
							+ String.format("%1$-20s", versi));//
			msg.set(70, "104");

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			String data48 = msg.getString(48);

			value.put("RC", rc);
			value.put("RESPONSE_MSG", data48.substring(55));
			if (rc.equals("00")) {
				String tid = msg.getString(41);
				String acceptor = msg.getString(42);
				String namaPP = data48.substring(0, 25).trim();
				String namaBank = data48.substring(25, 55).trim();

				engine.setTerminalId(tid);
				engine.setKodeBank("014");
				engine.setAcceptorId(acceptor);
				engine.flushToDatabase();

				value.put("TID", tid);
				value.put("ACCEPTOR_ID", acceptor);
				value.put("NAMA_PP", namaPP);
				value.put("NAMA_BANK", namaBank);
				value.put("IDPETUGAS", idpetugas);

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof SocketTimeoutException) {
				value.put("RC", "18");
				value.put("RESPONSE_MSG", getRCMessage("18"));
			} else if (e instanceof SocketException) {
				value.put("RC", "13");
				value.put("RESPONSE_MSG", getRCMessage("13"));
			} else {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
			}
		}
		return value;
	}

	/**
	 * This method called to do Logout.
	 * 
	 * @return HashMap<String, String> of response
	 */
	public HashMap<String, String> doLogout() {
		HashMap<String, String> value = new HashMap<String, String>();
		value.put("RC", "00");
		value.put("RESPONSE_MSG", "Sukses logout");
		return value;
	}

	/**
	 * This method called to do Password Changing.
	 * 
	 * @param ip
	 * @param port
	 * @param idpetugas
	 * @param passwordlama
	 * @param passwordbaru
	 * @param imei
	 * @return HashMap<String, String> of response
	 */
	public HashMap<String, String> doChangePassword(String ip, int port,
			String idpetugas, String passwordlama, String passwordbaru,
			String imei) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.setMTI("0100");
			msg.set(32, engine.getKodeBank());
			msg.set(41, engine.getTerminalId());
			msg.set(42, engine.getAcceptorId());
			msg.set(48,
					String.format("%1$-25s", imei)
							+ String.format("%1$-25s", idpetugas)
							+ String.format("%1$-32s", MD5(passwordlama))
							+ String.format("%1$-32s", MD5(passwordbaru)));//
			msg.set(70, "103");

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			value.put("RC", rc);
			value.put("RESPONSE_MSG", msg.getString(48));

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof SocketTimeoutException) {
				value.put("RC", "18");
				value.put("RESPONSE_MSG", getRCMessage("18"));
			} else if (e instanceof SocketException) {
				value.put("RC", "13");
				value.put("RESPONSE_MSG", getRCMessage("13"));
			} else {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
			}
		}
		return value;
	}

	/**
	 * This method called to communicate with server.
	 * 
	 * @param ip
	 * @param port
	 * @param msg
	 * @return byte[] response
	 * @throws Exception
	 */
	protected byte[] communicateWithServer(String ip, int port, byte[] msg)
			throws Exception {
		SSLSocket s = null;
		try {
			Log.e("Enlace Communicate With Server", "Trying To Connect " + ip
					+ ":" + port);

			if (sslsocketfactory == null)
				sslsocketfactory = new MySSLSocketFactory();
			s = (SSLSocket) sslsocketfactory.createSocket(ip, port);
			s.setSoTimeout(45000);
			s.setReuseAddress(true);

			BufferedOutputStream out = new BufferedOutputStream(
					s.getOutputStream());
			BufferedInputStream in = new BufferedInputStream(s.getInputStream());

			byte[] byteLength = new byte[2];
			int length = msg.length + 1;
			byteLength[0] = (byte) ((length >> 8) & 0xFF);
			byteLength[1] = (byte) (length & 0xFF);

			// Kirim
			out.write(byteLength);
			out.write(msg);
			out.write(3);
			out.flush();

			// Terima
			byteLength[0] = 0;
			byteLength[1] = 0;
			in.read(byteLength);

			length = ((byteLength[0] & 0xFF) << 8) + (byteLength[1] & 0xFF);
			if (length > 0) {
				msg = new byte[length - 1];
				in.read(msg);
				System.out.println(new String(msg));
				return msg;
			} else
				return null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	/**
	 * This method called to get Terminal ID.
	 * 
	 * @return String Terminal ID
	 */
	public String getTerminalId() {
		return engine.getTerminalId();
	}

	/**
	 * This method called to get Acceptor ID
	 * 
	 * @return String Acceptor ID
	 */
	public String getAcceptorId() {
		return engine.getAcceptorId();
	}

	/**
	 * This method called to get Kode Bank
	 * 
	 * @return String Kode Bank
	 */
	public String getKodeBank() {
		return engine.getKodeBank();
	}

	/**
	 * This method must implemented when extends this class. This method used to
	 * get RC Messages.
	 * 
	 * @param RC
	 * @return String RCMessage
	 */
	public abstract String getRCMessage(String RC);

	/**
	 * This method called to encrypt text using MD%
	 * 
	 * @param text
	 * @return String Encrypted text
	 */
	protected String MD5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] mdbytes = md.digest(text.getBytes());

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

			return sb.toString();
		} catch (Exception e) {
		}
		return "";
	}
}
