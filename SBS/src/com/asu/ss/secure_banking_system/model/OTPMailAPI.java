package com.asu.ss.secure_banking_system.model;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class OTPMailAPI {


public static void sendMail(String toEmail,String subject,String msg) throws Exception
{
	final String email="happyttbank@gmail.com";
	final String password="ssproject";
	Properties props = new Properties();
	
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
			"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");

	Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email,password);
				}
			});
	try {
		 
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("HappyTTBanking"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
		message.setSubject(subject);
		message.setText(msg);

		Transport.send(message);

		System.out.println("Done");

	} catch (Exception e) {
		throw e;
	}
}
public static void sendMailWithAttachment(String toEmail,String subject,String msg, String filePath, String fileName) throws Exception
{
	final String email="happyttbank@gmail.com";
	final String password="ssproject";
	Properties props = new Properties();
	
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
			"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");

	Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email,password);
				}
			});
	try {
		 
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("HappyTTBanking"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
		message.setSubject(subject);
		message.setText(msg);
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();

		Multipart multipart = new MimeMultipart(); 
		messageBodyPart = new MimeBodyPart();
		//String file = "/tmp/test.csv";
		//String fileName = "test.csv"; 
		DataSource source = new FileDataSource(filePath);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(fileName);
		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);
		Transport.send(message);

		System.out.println("Done");

	} catch (Exception e) {
		throw e;
	}
}
}


