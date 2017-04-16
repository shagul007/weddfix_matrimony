package com.weddfix.web.util;

import org.apache.log4j.Logger;

public class SessionID {
	Logger logger = Logger.getLogger(SessionID.class);
	public String generateID() {
		java.util.Random r = new java.util.Random();
		char c[] = new char[6];
		for (int i = 0; i < 6; i++) {
			c[i] = (char) (r.nextInt(89) + 33);
		}
		System.out.println("Session Id original " +new String(c));
		System.out.println("Session Id replaced " +new String(c).replace("'", "^"));
		return new String(c).replace("'", "^");
	}
}
