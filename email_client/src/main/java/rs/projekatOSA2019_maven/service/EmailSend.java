package rs.projekatOSA2019_maven.service;
import java.io.File;
import java.util.ArrayList;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

@Component
public class EmailSend {
	
//	@Autowired
//	private JavaMailSender javaMailSender;
//	
//	public void send(ArrayList<String> to,String subject,String body) throws MessagingException {
//		MimeMessage mi=javaMailSender.createMimeMessage();
//		MimeMessageHelper helper;
//		helper=new MimeMessageHelper(mi,true);
//		helper.setSubject(subject);
//		helper.setTo(to);
//		helper.setText(body,true);
//		
//		javaMailSender.send(mi);
//		
//	}
	 
}
