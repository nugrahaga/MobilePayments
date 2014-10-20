package com.rnd.mobilepayment.pln.engine;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MySSLSocketFactory extends SSLSocketFactory {
	SSLContext sslContext = SSLContext.getInstance("TLS");

	/**
	 * Constructor MySSLSocketFactory().
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
	public MySSLSocketFactory() throws NoSuchAlgorithmException,
			KeyManagementException, KeyStoreException,
			UnrecoverableKeyException {
		super();

		TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sslContext.init(null, new TrustManager[] { tm }, null);
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(socket, host, port,
				autoClose);
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}

	@Override
	public String[] getDefaultCipherSuites() {
		throw new UnsupportedOperationException("Not supported yet.");
		// To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String[] getSupportedCipherSuites() {
		throw new UnsupportedOperationException("Not supported yet.");
		// To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Socket createSocket(String string, int i) throws IOException,
			UnknownHostException {
		return sslContext.getSocketFactory().createSocket(string, i);
	}

	@Override
	public Socket createSocket(String string, int i, InetAddress ia, int i1)
			throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(string, i, ia, i1);
	}

	@Override
	public Socket createSocket(InetAddress ia, int i) throws IOException {
		return sslContext.getSocketFactory().createSocket(ia, i);
	}

	@Override
	public Socket createSocket(InetAddress ia, int i, InetAddress ia1, int i1)
			throws IOException {
		throw new UnsupportedOperationException("Not supported yet.");
		// To change body of generated methods, choose Tools | Templates.
	}
}
