package rs.projekatOSA2019_maven.mailAPI;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import rs.projekatOSA2019_maven.entity.Attachment;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.service.ContactServiceInterface;
import rs.projekatOSA2019_maven.service.MessageServiceInterface;

public class MailSender {
	private final MessageServiceInterface messageService;
	
	public MailSender(MessageServiceInterface messageService) {
		this.messageService = messageService;
	}
	
	public void sendEmail(Message message) {
		
		//Declare to
		String EMAIL_TO = message.getTo();
		System.out.println(EMAIL_TO);
		System.out.println("LOGGLLGLGLGLLGLGLGLGLLGLLGLGLGLLGLGLGL");
		String EMAIL_CC = message.getCc();
		String EMAIL_BCC = message.getBcc();
		String EMAIL_FROM = message.getFrom();
		
		final String username = message.getAccount().getUsername();
		final String password = message.getAccount().getPassword();
		
		//Set properties and their values
		Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        prop.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        prop.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.port", "465"); //SMTP Port
        prop.put("mail.smtp.ssl.enable", "true");
        
        //Create a Session object & authenticate uid and pwd
        Session session = Session.getInstance(prop, new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(username, password);
        	}
		});
        
        try {
			//Create MimeMessage object & set values
        	MimeMessage messageObj = new MimeMessage(session);
        	
        	messageObj.setFrom(new InternetAddress(EMAIL_FROM));
            messageObj.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
//            messageObj.setRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(EMAIL_CC));
//            messageObj.setRecipients(javax.mail.Message.RecipientType.BCC, InternetAddress.parse(EMAIL_BCC));
            messageObj.setSubject(message.getSubject());
            
            //Declare text values
            StringBuilder builder = new StringBuilder();
            String messageContent = message.getContent() + "\n";
            message.getTags().forEach(t -> builder.append(t.getName()));
            messageContent += builder.toString();
            
            Multipart mp = new MimeMultipart();
            BodyPart messageBody = new MimeBodyPart();
            
            for (Attachment attachment : message.getAttachments()) {
				MimeBodyPart mbp2 = new MimeBodyPart();
				mbp2.setContent(attachment.getData(), "image/*");
				mbp2.setFileName(attachment.getName());
				mp.addBodyPart(mbp2);
			}
            
            messageBody.setText(messageContent);
            mp.addBodyPart(messageBody);
            messageObj.setContent(mp);
            Transport.send(messageObj);
            message = messageService.save(message);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}

