package com.salesforce.utilities;


import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;

import com.salesforce.SalesForceEnvironment;

public class SendEmailUtility {
	private static String className = SendEmailUtility.class.getSimpleName();
	static Properties mailServerProperties = null;
	static String username = null;
	static String password = null;

	public static boolean sendmail(String sendto, String sendSubject, String content)    throws Exception	{
		boolean result = false;
		SalesForceEnvironment.setComment(3, className, "  inside sendmail start  ");
		username = SalesForceEnvironment.getSMTPUserId();
		password = SalesForceEnvironment.getSMTPUserPwd();
		//Message message =null;
		try {
			SalesForceEnvironment.setComment(3, className, "sendto  is  " + sendto   + "  sendSubject  is  " + sendSubject+ "  content  is  " + content); 

			mailServerProperties = new Properties();
			mailServerProperties.setProperty("mail.smtp.starttls.enable", "true");
			mailServerProperties.setProperty("mail.smtp.host", SalesForceEnvironment.getSMTPHost());
			mailServerProperties.setProperty("mail.smtp.user", username);
			mailServerProperties.setProperty("mail.smtp.password", password);
			mailServerProperties.setProperty("mail.smtp.port", SalesForceEnvironment.getSMTPTLSPort());
			mailServerProperties.setProperty("mail.smtp.auth", "true");
			mailServerProperties.setProperty("mail.smtp.ssl.trust", "*");

	        //System.out.println("Props : " + prop);
			SalesForceEnvironment.setComment(3, className, "  sending email here....  mailServerProperties  is   " +  mailServerProperties   );
		   
	        Session session = Session.getInstance(mailServerProperties, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username,
	                		password);
	            }
	        });
	  
	        SalesForceEnvironment.setComment(3, className, "  mail session is   "+session);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(SalesForceEnvironment.getSendFromEmailId()));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sendto));
			message.setSubject(sendSubject);
            message.setText(content);
            SalesForceEnvironment.setComment(3, className, "content is   " + content); 

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendto));
			//message.setContent(content,"text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect(SalesForceEnvironment.getSMTPHost(), username, password);
            transport.sendMessage(message, message.getAllRecipients());
            SalesForceEnvironment.setComment(3, className, "message is  " + message); 
			result = true;
			SalesForceEnvironment.setComment(3, className, "Email send successfully to  " + message.getAllRecipients()); 

		} catch (MessagingException e) {
			result = false;
			SalesForceEnvironment.setComment(1, className, "Exception Occured - " +e.getMessage()); 
		}finally {
			//if(message!=null) message=null;
		}
		return result;

		
	}

	public static boolean sendMultipleEmail( String sendSubject, String content, String bcc)    throws Exception	{
		boolean result = false;
		SalesForceEnvironment.setComment(3, className, "  inside sendmail start  ");
		username = SalesForceEnvironment.getSMTPUserId();
		password = SalesForceEnvironment.getSMTPUserPwd();
		//Message message =null;
		SalesForceEnvironment.setComment(3, className, "Emails are  " +   bcc); 

		try {
			SalesForceEnvironment.setComment(3, className, "sendto  is  " + "  sendSubject  is  " + sendSubject+ "  content  is  " + content); 

			mailServerProperties = new Properties();
			mailServerProperties.setProperty("mail.smtp.starttls.enable", "true");
			mailServerProperties.setProperty("mail.smtp.host", SalesForceEnvironment.getSMTPHost());
			mailServerProperties.setProperty("mail.smtp.user", username);
			mailServerProperties.setProperty("mail.smtp.password", password);
			mailServerProperties.setProperty("mail.smtp.port", SalesForceEnvironment.getSMTPTLSPort());
			mailServerProperties.setProperty("mail.smtp.auth", "true");
			mailServerProperties.setProperty("mail.smtp.ssl.trust", "*");

	        //System.out.println("Props : " + prop);
			SalesForceEnvironment.setComment(3, className, "  sending email here....  mailServerProperties  is   " +  mailServerProperties   );
		   
	        Session session = Session.getInstance(mailServerProperties, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username,
	                		password);
	            }
	        });
	  
	        SalesForceEnvironment.setComment(3, className, "  mail session is   "+session);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(SalesForceEnvironment.getSendFromEmailId()));
			message.setSubject(sendSubject);
            message.setText(content);
            SalesForceEnvironment.setComment(3, className, "content is   " + content);			
			//String receivers="benjaminshields10@gmail.com,moseskipyegonm@gmail.com";
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc.toString()));
			SalesForceEnvironment.setComment(3, className, "After BCC");			

			//message.setContent(content,"text/html");
            Transport transport = session.getTransport("smtp");
            SalesForceEnvironment.setComment(3, className, "After SMTP");			

            transport.connect(SalesForceEnvironment.getSMTPHost(), username, password);
            SalesForceEnvironment.setComment(3, className, "After STMP host");			

            transport.sendMessage(message, message.getAllRecipients());
            SalesForceEnvironment.setComment(3, className, "message is  " + message); 
			result = true;
			SalesForceEnvironment.setComment(3, className, "Email send successfully to  " + message.getAllRecipients()); 

		} catch (MessagingException e) {
			result = false;
			SalesForceEnvironment.setComment(1, className, "Exception Occured - " +e.getMessage()); 
		}finally {
			//if(message!=null) message=null;
		}
		return result;
	}
	
	public static boolean sendEmailWithCsvAttachment(String sendTo, String filePath, String sendSubject, String content) throws Exception {

		
		
		
			username = SalesForceEnvironment.getSMTPUserId();
			password = SalesForceEnvironment.getSMTPUserPwd();

		  final String SMTP_HOST =SalesForceEnvironment.getSMTPHost(); final String SMTP_PORT =   SalesForceEnvironment.getSMTPTLSPort();
		  //final String GMAIL_USERNAME = "pesaprintwallet001@gmail.com";
		 // final String GMAIL_PASSWORD = "abcd!@#$1234";
		  boolean result = false;
		  
	      try {
	    	  SalesForceEnvironment.setComment(3, className, System.lineSeparator()+"Process Started");
			  
			  Properties prop = new Properties();
			  prop.setProperty("mail.smtp.starttls.enable", "true");
			  prop.setProperty("mail.smtp.host", SMTP_HOST);
			  prop.setProperty("mail.smtp.user", username);
			  prop.setProperty("mail.smtp.password", password);
			  prop.setProperty("mail.smtp.port", SMTP_PORT);
			  prop.setProperty("mail.smtp.auth", "true");
			  prop.setProperty("mail.smtp.ssl.trust", "*"); 
			  SalesForceEnvironment.setComment(3, className,System.lineSeparator()+"Props : " +  prop);
			  //outputImagePath = StringUtils.replace(outputImagePath, "\\", "/");
			  filePath = StringUtils.replace(filePath, "\\", "/");
			//  filePath =  StringUtils.replace(filePath, "\\", "\\\\");
			  
			  File tempFile  = new File(filePath);
			  Session session = Session.getInstance(prop, new Authenticator() { protected
			  PasswordAuthentication getPasswordAuthentication() { return new
			  PasswordAuthentication(username, password); } });
			  
			  SalesForceEnvironment.setComment(3, className,System.lineSeparator()+"Got Session : " + session);
			  
			  MimeMessage message = new MimeMessage(session);
			 
	
				
			  SalesForceEnvironment.setComment(3, className, System.lineSeparator()+"before sending"); message.setFrom(new
				  InternetAddress(username));
				  message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
				  message.setSubject(sendSubject);
				//  message.setText("Hi, This mail came from Java Application.");
				  message.setRecipients(Message.RecipientType.TO,  InternetAddress.parse(sendTo)); 
				  
				  filePath =  filePath.replace(  "\\", "/");
				 
			       // Create a multipar message
			         Multipart multipart = new MimeMultipart();
			         BodyPart messageBodyPart = new MimeBodyPart();
			         messageBodyPart.setText(content);
			         // Set text message part
			         multipart.addBodyPart(messageBodyPart);
			         
			         
			         // Part two is attachment
			         messageBodyPart = new MimeBodyPart();
			        // String filename = "D:\\Arnab\\BD\\facetracking\\test.pptx";
			         SalesForceEnvironment.setComment(3, className, "filePath "+filePath);		
			         DataSource source = new FileDataSource(filePath);
			         messageBodyPart.setDataHandler(new DataHandler(source));
			         messageBodyPart.setFileName(filePath.substring(filePath.lastIndexOf("/")+1, filePath.length()));
			         multipart.addBodyPart(messageBodyPart);
	
			         // Send the complete message parts
			         message.setContent(multipart);
			         
					  Transport transport =   session.getTransport("smtp"); 
					  SalesForceEnvironment.setComment(3, className, "Got Transport" +   transport); 
					  transport.connect(SMTP_HOST, username, password);
					  transport.sendMessage(message, message.getAllRecipients());
	
					  SalesForceEnvironment.setComment(3, className, "Email Sent Successfully");
					  result = true;
				  
				  try {
					  
				  }finally {
				      if(tempFile!=null) {
					  		if(tempFile.exists()) {
					  			tempFile.delete();
					  		}
					        }
					}


      } catch (Exception e) {
      	result = false;
      	SalesForceEnvironment.setComment(1, className, "Exception in Method sendEmailWithCsvAttachment "+e.getMessage());
      	
      }	

		
		return result;
	}
	
	
	
	
}
