package com.weddfix.web.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class SendSMS extends ActionSupport implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	public String sendSms(String msg, String number) {
		try {
			// Construct data
			String authkey = "authkey=" + getText("sms.authkey");
			String mobiles = "&mobiles=" + number;
			String message = "&message=" + msg;
			String sender = "&sender=" + getText("sms.sender");
			String route = "&route=" + getText("sms.route");
			String country = "&country=" + getText("sms.country");

			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://sms.ssdindia.com/api/sendhttp.php?").openConnection();
			String data = authkey + mobiles + message + sender + route + country;
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Length",
					Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();

			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			return "Error " + e;
		}
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	public Map<String, Object> getSession() {
		return session;
	}
}
