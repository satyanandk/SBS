package com.sbs.model.message;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailEmail {
	public static void main(String[] args){
		String from = "liqingyun1989@gmail.com";
		final String password = "liqingyun891129";
		String to = "sandipdeveloper@gmail.com";
		final String username = "liqingyun1989@gmail.com";
		sendMail(from, username , password,to);
		
	}

	public static void sendMail(String from,final String username, final String password, String to) {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");//ssl
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));//from email
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));//to email
			message.setSubject("Testing Subject");
			message.setText("Dear Sandip," +
					"\n\n Our mailing funcionality is done!" +
					"\n\n Tom");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
}
