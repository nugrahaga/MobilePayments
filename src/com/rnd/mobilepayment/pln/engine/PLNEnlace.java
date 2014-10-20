package com.rnd.mobilepayment.pln.engine;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import android.content.Context;

import jpa.jpos.iso.ISOMsg;

public class PLNEnlace extends Enlace {
	private HashMap<String, byte[]> inq_stream;

	/**
	 * Constructor for PLNEnlace
	 * 
	 * @param ctx
	 */
	public PLNEnlace(Context ctx) {
		super(ctx);
		inq_stream = new HashMap<String, byte[]>();
	}

	/**
	 * This method called to do INQUIRY PLN POSTPAID
	 * 
	 * @param ip
	 * @param port
	 * @param idpel
	 * @return {@code HashMap<String, String>} RC, RESPONSE_MSG, IDPEL, NAMA,
	 *         TOTAL TAGIHAN, TARIF/DAYA, BL/TH, RP TAG PLN, ADMIN BANK, TOTAL
	 *         BAYAR
	 */
	public HashMap<String, String> INQPostpaid(String ip, int port, String idpel) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			int stan = engine.generateStan();
			long now = System.currentTimeMillis();

			msg.setMTI("0200");
			msg.set(2, "0" + idpel.substring(0, 2) + "501");
			msg.set(3, "380000");
			msg.set(7, String.format("%1$tm%1$td%1$tH%1$tM%1$tS", now));
			msg.set(11, String.format("%1$06d", stan));
			msg.set(12, String.format("%1$tH%1$tM%1$tS", now));
			msg.set(13, String.format("%1$tm%1$td", now));
			msg.set(15, String.format("%1$tm%1$td", now + 86400000));
			msg.set(18, "6012");
			msg.set(32, engine.getKodeBank());
			msg.set(37, "000000000014");
			msg.set(41, engine.getTerminalId());
			msg.set(42, engine.getAcceptorId());
			msg.set(48, idpel);
			msg.set(49, "360");

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			if (rc.equals("00")) {
				inq_stream.put(idpel, resp);

				String data48 = msg.getString(48);
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
				value.put("IDPEL", idpel);
				value.put("NAMA", data48.substring(47, 72).trim());

				String jumTagihan = data48.substring(12, 13);
				value.put("TOTAL TAGIHAN", jumTagihan + " BULAN");
				value.put("TARIF/DAYA", data48.substring(92, 96).trim() + "/"
						+ data48.substring(96, 105).trim());

				String blth = "";
				int numTagihan = Integer.parseInt(jumTagihan);
				int rpPln = 0;
				for (int i = 0; i < numTagihan; i++) {
					if (i > 0)
						blth += ",";
					blth += data48.substring(114 + i * 111, 120 + i * 111);
					rpPln += Integer.parseInt(data48.substring(136 + i * 111,
							147 + i * 111));
				}
				int biayaAdmin = Integer.parseInt(data48.substring(105, 114));

				value.put("BL/TH", blth);
				value.put("RP TAG PLN", engine.moneyFormat(rpPln));
				value.put("ADMIN BANK", engine.moneyFormat(biayaAdmin));
				value.put("TOTAL BAYAR", engine.moneyFormat(rpPln + biayaAdmin));

			} else {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
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
	 * This method called to do PAY INQUIRY PLN POSTPAID
	 * 
	 * @param ip
	 * @param port
	 * @param idpel
	 * @return {@code HashMap<String, String>} RC, RESPONSE_MSG, IDPEL, NAMA,
	 *         JPAREF, TOTAL TAGIHAN, TARIF/DAYA, STAND METER, BL/TH, RP TAG
	 *         PLN, ADMIN BANK, TOTAL BAYAR, FOOTER_MSG
	 */
	public HashMap<String, String> PAYPostpaid(String ip, int port, String idpel) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			byte[] stream = inq_stream.get(idpel);

			if (stream == null) {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
				return value;
			}

			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.unpack(stream);

			String data48 = msg.getString(48);
			data48 = data48.substring(0, 13) + data48.substring(12);

			msg.setMTI("0200");
			msg.set(3, "170000");
			msg.set(48, data48);
			msg.unset(39);

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			if (rc.equals("00")) {
				data48 = msg.getString(48);
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
				value.put("IDPEL", idpel);
				value.put("NAMA", data48.substring(80, 105).trim());
				value.put("JPAREF", data48.substring(48, 80));

				String jumTagihan = data48.substring(12, 13);
				value.put("TOTAL TAGIHAN", jumTagihan + " BULAN");
				value.put("TARIF/DAYA", data48.substring(125, 129).trim() + "/"
						+ data48.substring(129, 138).trim());

				String blth = "";
				int numTagihan = Integer.parseInt(jumTagihan);
				int rpPln = 0;
				int meterAwal = 0, meterAkhir = 0;
				for (int i = 0; i < numTagihan; i++) {
					if (i == 0)
						meterAwal = Integer.parseInt(data48.substring(
								242 + i * 111, 250 + i * 111));
					if (i == numTagihan - 1)
						meterAkhir = Integer.parseInt(data48.substring(
								250 + i * 111, 258 + i * 111));

					if (i > 0)
						blth += ",";
					blth += data48.substring(147 + i * 111, 153 + i * 111);
					rpPln += Integer.parseInt(data48.substring(169 + i * 111,
							180 + i * 111));
				}
				int biayaAdmin = Integer.parseInt(data48.substring(138, 147));

				value.put("STAND METER", meterAwal + " - " + meterAkhir);
				value.put("BL/TH", blth);
				value.put("RP TAG PLN", engine.moneyFormat(rpPln));
				value.put("ADMIN BANK", engine.moneyFormat(biayaAdmin));
				value.put("TOTAL BAYAR", engine.moneyFormat(rpPln + biayaAdmin));
				value.put("FOOTER_MSG", msg.getString(62));

			} else {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
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
	 * This method called to do REVERSAL PLN POSTPAID
	 * 
	 * @param ip
	 * @param port
	 * @param idpel
	 * @return {@code HashMap<String, String>} RC, RESPONSE_MSG
	 */
	public HashMap<String, String> REVPostpaid(String ip, int port, String idpel) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			byte[] stream = inq_stream.get(idpel);

			if (stream == null) {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
				return value;
			}

			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.unpack(stream);

			String data48 = msg.getString(48);
			data48 = data48.substring(0, 13) + data48.substring(12);

			msg.setMTI("0400");
			msg.set(3, "000200");
			msg.set(48, data48);
			msg.set(90,
					"0200"
							+ msg.getString(11)
							+ msg.getString(7)
							+ String.format("%1$011d",
									Integer.parseInt(msg.getString(32)))
							+ "00000000000");
			msg.unset(39);

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			if (rc.equals("00")) {
				data48 = msg.getString(48);
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
			} else {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
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
	 * This method called to do INQUIRY Non Tagihan Listrik.
	 * 
	 * @param ip
	 * @param port
	 * @param idpel
	 * @return {@code HashMap<String, String>} RC, RESPONSE_MSG, TRANSAKSI, RP
	 *         BAYAR, NO REGISTRASI, TGL REGISTRASI, IDPEL, BIAYA PLN, JPAREF,
	 *         ADMIN BANK,TOTAL BAYAR
	 */
	public HashMap<String, String> INQNonrek(String ip, int port, String idpel) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			int stan = engine.generateStan();
			long now = System.currentTimeMillis();

			msg.setMTI("0200");
			msg.set(2, "053504");
			msg.set(3, "380000");
			msg.set(7, String.format("%1$tm%1$td%1$tH%1$tM%1$tS", now));
			msg.set(11, String.format("%1$06d", stan));
			msg.set(12, String.format("%1$tH%1$tM%1$tS", now));
			msg.set(13, String.format("%1$tm%1$td", now));
			msg.set(15, String.format("%1$tm%1$td", now + 86400000));
			msg.set(18, "6012");
			msg.set(32, engine.getKodeBank());
			msg.set(37, "000000000014");
			msg.set(41, engine.getTerminalId());
			msg.set(42, engine.getAcceptorId());
			msg.set(48, idpel);
			msg.set(49, "360");

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			if (rc.equals("00")) {
				inq_stream.put(idpel, resp);

				String data48 = msg.getString(48);
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
				value.put("NAMA", data48.substring(66, 91).trim());
				value.put("TRANSAKSI", data48.substring(13, 38).trim());
				value.put("RP BAYAR", engine.moneyFormat(Integer
						.parseInt(data48.substring(143, 158))));
				value.put("NO REGISTRASI", data48.substring(0, 13));
				value.put("TGL REGISTRASI", data48.substring(38, 46));
				value.put("IDPEL", data48.substring(54, 66));
				value.put("BIAYA PLN", engine.moneyFormat(Integer
						.parseInt(data48.substring(160, 175))));//
				value.put("JPAREF", data48.substring(123, 143));
				value.put("ADMIN BANK", engine.moneyFormat(Integer
						.parseInt(data48.substring(177, 185))));
				value.put("TOTAL BAYAR", engine.moneyFormat(Integer
						.parseInt(data48.substring(143, 158))));
			} else {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
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
	 * This method called to do PAY Non Rekening.
	 * 
	 * @param ip
	 * @param port
	 * @param idpel
	 * @return {@code HashMap<String, String>} RC, RESPONSE_MSG, NO REGISTRASI,
	 *         TGL REGISTRASI, NAMA, IDPEL, BIAYA PLN, JPAREF, ADMIN BANK, TOTAL
	 *         BAYAR, FOOTER MSG
	 */
	public HashMap<String, String> PAYNonrek(String ip, int port, String idpel) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			byte[] stream = inq_stream.get(idpel);

			if (stream == null) {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
				return value;
			}

			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.unpack(stream);

			msg.setMTI("0200");
			msg.set(3, "170000");
			msg.unset(39);

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			if (rc.equals("00")) {
				String data48 = msg.getString(48);
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
				value.put("TRANSAKSI", data48.substring(13, 38).trim());
				value.put("NO REGISTRASI", data48.substring(0, 13));
				value.put("TGL REGISTRASI", data48.substring(38, 46));
				value.put("NAMA", data48.substring(66, 91).trim());
				value.put("IDPEL", data48.substring(54, 66));
				value.put("BIAYA PLN", engine.moneyFormat(Integer
						.parseInt(data48.substring(160, 175))));
				value.put("JPAREF", data48.substring(123, 143));
				value.put("ADMIN BANK", engine.moneyFormat(Integer
						.parseInt(data48.substring(177, 185))));
				value.put("TOTAL BAYAR", engine.moneyFormat(Integer
						.parseInt(data48.substring(143, 158))));
				value.put("FOOTER MSG", msg.getString(62));
			} else {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
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
	 * This method called to do REVERSAL Non Tagihan.
	 * 
	 * @param ip
	 * @param port
	 * @param idpel
	 * @return {@code HashMap<String, String>} RC, RESPONSE_MSG
	 */
	public HashMap<String, String> REVNonrek(String ip, int port, String idpel) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			byte[] stream = inq_stream.get(idpel);

			if (stream == null) {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
				return value;
			}

			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.unpack(stream);

			msg.setMTI("0400");
			msg.set(3, "000200");
			msg.set(90,
					"0200"
							+ msg.getString(11)
							+ msg.getString(7)
							+ String.format("%1$011d",
									Integer.parseInt(msg.getString(32)))
							+ "00000000000");
			msg.unset(39);

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			if (rc.equals("00")) {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
			} else {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
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
	 * This method called to do INQUIRY PLN PREPAID.
	 * 
	 * @param ip
	 * @param port
	 * @param nometer
	 * @param unsold
	 * @param denom
	 * @return {@code HashMap<String, String>} RC, RESPONSE_MSG, NO METER, NAMA,
	 *         TARIF/DAYA, TOKEN AVAIL1, TOKEN AVAIL2
	 */
	public HashMap<String, String> INQPrepaid(String ip, int port,
			String nometer, boolean unsold, int denom) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			int stan = engine.generateStan();
			long now = System.currentTimeMillis();

			String data48 = null;
			if (nometer.length() == 11)
				data48 = "JTL53L3" + nometer + "000000000000" + "0";// berdasarkan
																	// nometer
			else
				data48 = "JTL53L3" + "00000000000" + nometer + "1";// berdasarkan
																	// idpel

			msg.setMTI("0200");
			msg.set(2, "053502");
			msg.set(3, "380000");
			msg.set(4, String.format("%1$012d", denom));
			msg.set(7, String.format("%1$tm%1$td%1$tH%1$tM%1$tS", now));
			msg.set(11, String.format("%1$06d", stan));
			msg.set(12, String.format("%1$tH%1$tM%1$tS", now));
			msg.set(13, String.format("%1$tm%1$td", now));
			msg.set(15, String.format("%1$tm%1$td", now + 86400000));
			msg.set(18, "6012");
			msg.set(32, engine.getKodeBank());
			msg.set(37, "000000000014");
			msg.set(41, engine.getTerminalId());
			msg.set(42, engine.getAcceptorId());
			msg.set(48, data48);
			msg.set(49, "360");

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			if (rc.equals("00")) {
				inq_stream.put(nometer, resp);

				data48 = msg.getString(48);
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
				value.put("NO METER", data48.substring(7, 18));
				value.put("NAMA", data48.substring(95, 120).trim());
				value.put("TARIF/DAYA", data48.substring(120, 124).trim()
						+ " / " + data48.substring(124, 133).trim());

				int token1 = 0, token2 = 0;
				int numTokenUnsold = Integer.parseInt(msg.getString(62)
						.substring(27, 28));
				if (numTokenUnsold == 1)
					token1 = Integer.parseInt(msg.getString(62).substring(28,
							39));
				if (numTokenUnsold == 2)
					token2 = Integer.parseInt(msg.getString(62).substring(39,
							50));

				value.put("TOKEN AVAIL1", engine.moneyFormat(token1));
				value.put("TOKEN AVAIL2", engine.moneyFormat(token2));
			} else {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
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
	 * This method called to do PAY PLN PREPAID.
	 * 
	 * @param ip
	 * @param port
	 * @param nometer
	 * @param unsold
	 * @param denom
	 * @return {@code HashMap<String, String>} RC, RESPONSE_MSG, NO METER,
	 *         IDPEL, NAMA, TARIF/DAYA, JPAREF, RP BAYAR, ADMIN BANK, METERAI,
	 *         PPN, PPJ, ANGSURAN, RP STROOM/TOKEN, JML KWH, STROOM/TOKEN,
	 *         FOOTER MSG
	 */
	public HashMap<String, String> PAYPrepaid(String ip, int port,
			String nometer, boolean unsold, int denom) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			byte[] stream = inq_stream.get(nometer);

			if (stream == null) {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
				return value;
			}

			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.unpack(stream);

			msg.setMTI("0200");
			msg.set(3, "171000");
			msg.unset(39);

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			if (rc.equals("00")) {
				String data48 = msg.getString(48);
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
				value.put("NO METER", data48.substring(7, 18));
				value.put("IDPEL", data48.substring(18, 30));
				value.put("NAMA", data48.substring(103, 128).trim());
				value.put("TARIF/DAYA", data48.substring(128, 132).trim()
						+ " / " + data48.substring(132, 141).trim());
				value.put("JPAREF", data48.substring(63, 95).trim());
				value.put("RP BAYAR",
						engine.moneyFormat(Integer.parseInt(msg.getString(4))));
				value.put("ADMIN BANK", engine.moneyFormat(Integer
						.parseInt(data48.substring(143, 151))));
				value.put("METERAI", engine.moneyFormat(Integer.parseInt(data48
						.substring(154, 162))));
				value.put("PPN", engine.moneyFormat(Integer.parseInt(data48
						.substring(165, 173))));
				value.put("PPJ", engine.moneyFormat(Integer.parseInt(data48
						.substring(176, 184))));
				value.put("ANGSURAN", engine.moneyFormat(Integer
						.parseInt(data48.substring(187, 195))));
				value.put("RP STROOM/TOKEN", engine.moneyFormat(Integer
						.parseInt(data48.substring(198, 208))));
				value.put("JML KWH",
						Integer.parseInt(data48.substring(211, 219)) + ","
								+ Integer.parseInt(data48.substring(219, 221))
								+ " KWh");
				value.put("STROOM/TOKEN", data48.substring(221, 241).trim());
				value.put("FOOTER MSG", msg.getString(62));
			} else {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
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
	 * This method called to do ADVICE PLN PREPAID.
	 * 
	 * @param ip
	 * @param port
	 * @param nometer
	 * @param unsold
	 * @param denom
	 * @return {@code HashMap<String, String>} RC, RESPONSE_MSG, NO METER,
	 *         IDPEL, NAMA, TARIF/DAYA, JPAREF, RP BAYAR, ADMIN BANK, METERAI,
	 *         PPN, PPJ, ANGSURAN, RP STROOM/TOKEN, JML KWH, STROOM/TOKEN,
	 *         FOOTER MSG
	 */
	public HashMap<String, String> ADVPrepaid(String ip, int port,
			String nometer, boolean unsold, int denom) {
		HashMap<String, String> value = new HashMap<String, String>();
		try {
			byte[] stream = inq_stream.get(nometer);

			if (stream == null) {
				value.put("RC", "06");
				value.put("RESPONSE_MSG", getRCMessage("06"));
				return value;
			}

			ISOMsg msg = new ISOMsg();
			ISO1987APackagerJ packager = new ISO1987APackagerJ();
			msg.setPackager(packager);

			msg.unpack(stream);

			msg.setMTI("0220");
			msg.set(3, "172000");
			msg.unset(39);

			byte[] resp = communicateWithServer(ip, port, msg.pack());

			msg.clear();
			msg.unpack(resp);

			String rc = msg.getString(39);
			if (rc.equals("00")) {
				String data48 = msg.getString(48);
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
				value.put("NO METER", data48.substring(7, 18));
				value.put("IDPEL", data48.substring(18, 30));
				value.put("NAMA", data48.substring(103, 128).trim());
				value.put("TARIF/DAYA", data48.substring(128, 132).trim()
						+ " / " + data48.substring(132, 141).trim());
				value.put("JPAREF", data48.substring(63, 95).trim());
				value.put("RP BAYAR",
						engine.moneyFormat(Integer.parseInt(msg.getString(4))));
				value.put("ADMIN BANK", engine.moneyFormat(Integer
						.parseInt(data48.substring(143, 151))));
				value.put("METERAI", engine.moneyFormat(Integer.parseInt(data48
						.substring(154, 162))));
				value.put("PPN", engine.moneyFormat(Integer.parseInt(data48
						.substring(165, 173))));
				value.put("PPJ", engine.moneyFormat(Integer.parseInt(data48
						.substring(176, 184))));
				value.put("ANGSURAN", engine.moneyFormat(Integer
						.parseInt(data48.substring(187, 195))));
				value.put("RP STROOM/TOKEN", engine.moneyFormat(Integer
						.parseInt(data48.substring(198, 208))));
				value.put("JML KWH",
						Integer.parseInt(data48.substring(210, 218)) + ","
								+ Integer.parseInt(data48.substring(218, 220))
								+ " KWh");
				value.put("STROOM/TOKEN", data48.substring(220, 240).trim());
				value.put("FOOTER MSG", msg.getString(62));
			} else {
				value.put("RC", rc);
				value.put("RESPONSE_MSG", getRCMessage(rc));
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
	
	@Override
	public String getRCMessage(String RC) {
		if (RC.equals("00"))
			return "Sukses";
		else if (RC.equals("06"))
			return "Error tidak diketahui";
		else if (RC.equals("13"))
			return "Koneksi ke gateway bermasalah";
		else if (RC.equals("18"))
			return "Timeout";
		else if (RC.equals("14"))
			return "IDPEL tidak dikenali";
		else
			return "Error tidak diketahui";
	}
}
