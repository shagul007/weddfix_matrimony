package com.weddfix.web.util;

import java.io.StringWriter;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.opensymphony.xwork2.ActionSupport;

public class MailMessage extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Properties vars;
	private String template;
	private String recipient;
	private String subject;
	private String from;

	public MailMessage(Properties vars, String template, String recipient,
			String subject, String from) {
		this.vars = vars;
		this.template = template;
		this.recipient = recipient;
		this.subject = subject;
		this.from = from;
	}

	public MailMessage(Properties vars, String template, String recipient,
			String subject) {
		this.vars = vars;
		this.template = template;
		this.recipient = recipient;
		this.subject = subject;
	}

	public void send() throws Exception {
		// Properties props = new Properties();
		// props.put("mail.smtp.host", Config.getString("smtp"));
		//
		// Session session = Session.getDefaultInstance(props);

		final String username = getText("admin.email");
		final String password = getText("admin.email.password");

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		// MailMessage msg = new MailMessage(props, "pwreset.vm",
		// "shagul001@gmail.com", "Password reset request");
		Message m = new MimeMessage(session);
		if (this.from != null) {
			m.setFrom(new InternetAddress(this.from));
		} else {
			m.setFrom(new InternetAddress(getText("email.admin.from")));
		}
		m.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(this.recipient, false));

		m.setSubject(this.subject);
		m.setSentDate(new Date());

		VelocityContext ctx = new VelocityContext();
		for (Object key : this.vars.keySet()) {
			String name = (String) key;
			ctx.put(name, this.vars.getProperty(name));
		}
		Template tpl = Velocity.getTemplate(this.template);
		StringWriter sw = new StringWriter();
		tpl.merge(ctx, sw);

		String body = sw.toString();
		if (body.contains("<html>")) {
			m.setContent(sw.toString(), "text/html; charset=\"UTF-8\"");
		} else {
			m.setContent(sw.toString(), "text/plain; charset=\"UTF-8\"");
		}
		if (CommonConstants.TESTING_MODE) {
			System.out.println("Would have sent " + this.recipient
					+ " this message:\n" + sw.toString());
		} else {
			Transport.send(m);
		}
	}

	public void sendWithAttachment(String url, String filename)
			throws Exception {
		// Properties props = new Properties();
		// props.put("mail.smtp.host", Config.getString("smtp"));
		//
		// Session session = Session.getDefaultInstance(props);

		final String username = getText("admin.email");
		final String password = getText("admin.email.password");

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		Message m = new MimeMessage(session);
		if (this.from != null) {
			m.setFrom(new InternetAddress(this.from));
		} else {
			m.setFrom(new InternetAddress(getText("email.admin.from")));
		}
		m.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(this.recipient, false));
		m.setSubject(this.subject);
		m.setSentDate(new Date());

		VelocityContext ctx = new VelocityContext();
		for (Object key : this.vars.keySet()) {
			String name = (String) key;
			ctx.put(name, this.vars.getProperty(name));
		}
		Template tpl = Velocity.getTemplate(this.template);
		StringWriter sw = new StringWriter();
		tpl.merge(ctx, sw);

		Multipart mp = new MimeMultipart();
		BodyPart bp = new MimeBodyPart();

		String body = sw.toString();
		if (body.contains("<html>")) {
			bp.setContent(sw.toString(), "text/html; charset=\"UTF-8\"");
		} else {
			bp.setContent(sw.toString(), "text/plain; charset=\"UTF-8\"");
		}
		mp.addBodyPart(bp);

		bp = new MimeBodyPart();
		DataSource source = new URLDataSource(new URL(url));
		bp.setDataHandler(new DataHandler(source));
		bp.setFileName(filename);
		mp.addBodyPart(bp);

		m.setContent(mp);

		Transport.send(m);
	}
}
