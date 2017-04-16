package com.weddfix.web.util;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.classic.Session;

import com.opensymphony.xwork2.ActionSupport;

public class Util extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Random generator = new Random();

	public static String loginUrl(CommonConstants.LoginReason reason,
			HttpServletRequest request) throws Exception {
		String url = (CommonConstants.TESTING_MODE) ? "http" : "https://";
		url += request.getHeader("host");
		url += request.getRequestURI();

		return loginUrl(reason, url, request.getQueryString(), false);
	}

	public static String loginUrl(CommonConstants.LoginReason reason,
			String url, String query) throws Exception {
		return loginUrl(reason, url, query, false);
	}

	public static String loginUrl(CommonConstants.LoginReason reason,
			String url, String query, boolean includeHost) throws Exception {
		StringBuilder sb = new StringBuilder();

		if (includeHost) {
			sb.append(CommonConstants.URL);
		}

		sb.append("/login");

		if (reason != null | url != null) {
			sb.append("?");
		}

		if (reason != null) {
			sb.append("reason=").append(reason.name());
		}

		if (url != null) {
			if (query != null && query.trim().length() > 0) {
				url += "?" + query;
			}
			if (reason != null) {
				sb.append("&");
			}
			sb.append("return=").append(URLEncoder.encode(url, "UTF-8"));
		}
		return sb.toString();

	}

	public static Long getSequenceId(String name) throws Exception {
		Long id = 0L;
		Session conn = HibernateUtil.getSessionFactory().openSession();
		try {
			List<?> seqId = conn.getNamedQuery("getSequenceId")
					.setString("seqName", name).list();
			Iterator<?> itr = seqId.iterator();
			while (itr.hasNext()) {
				id = Long.parseLong(itr.next().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return id;
	}
	
	public static int generatePin() {
	    return 100000 + generator.nextInt(900000);
	}

}