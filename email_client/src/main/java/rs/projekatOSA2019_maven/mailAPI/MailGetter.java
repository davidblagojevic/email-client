package rs.projekatOSA2019_maven.mailAPI;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Attachment;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.service.FolderServiceInterface;
import rs.projekatOSA2019_maven.service.MessageServiceInterface;

@Component
public class MailGetter {
	
	private final String TMP_PATH = "projekatOSA2019_maven/src/main/tmp";
	
	private MessageServiceInterface messageService;
	private FolderServiceInterface folderService;
	
	public MailGetter(MessageServiceInterface messageService, FolderServiceInterface folderService) {
		this.messageService = messageService;
		this.folderService = folderService;
	}

	public void loadEmails(Account account, Date lastDate) {
		try {
			Properties prop = new Properties();
	        prop.put("mail.imap.host", "imap.gmail.com");
	        prop.put("mail.imap.port", "993");
	        prop.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        prop.setProperty("mail.imap.socketFactory.fallback", "false");
	        prop.setProperty("mail.imap.socketFactory.port", "993");
	        
	        Session session = Session.getDefaultInstance(prop, null);
	        Store store = session.getStore("imaps");
	        store.connect("imap.googlemail.com", 993, account.getUsername(), account.getPassword());
	        Folder emailFolder = store.getFolder("inbox");
	        emailFolder.open(Folder.READ_ONLY);
	        javax.mail.Message[] messages = emailFolder.getMessages();
	        
//	        LocalDateTime localDateTime = LocalDateTime.now();
//	        Date lastDate = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
	        
	        for (javax.mail.Message message : messages) {
				if (message.getSentDate().after(lastDate)) {
					Message temp = new Message();
					temp.setFrom(message.getFrom()[0].toString());
					StringBuilder sb = new StringBuilder();
//					for (javax.mail.Address address : message.getReplyTo()) {
//						builder.append(str)
//					}
					for (int i = 0; i < message.getReplyTo().length; i++) {
						if (i != 0) {
							sb.append(",");
						}
						sb.append(message.getReplyTo()[i].toString());
					}
					temp.setTo(sb.toString());
					sb.setLength(0);
					temp.setCc("");
					temp.setBcc("");
					temp.setDateTime(message.getSentDate());
					temp.setSubject(message.getSubject());
					
					String result = "";
					try {
						MimeMultipart body = (MimeMultipart) message.getContent();
						for (int i = 0; i < body.getCount(); i++) {
							MimeBodyPart bodyPart= (MimeBodyPart) body.getBodyPart(i);
                            if (bodyPart.isMimeType("text/plain")) {
                                result += "\n" + bodyPart.getContent();
                                break; // without break same text appears twice in my tests
                            } else if (bodyPart.isMimeType("text/html")) {
                                result = (String) bodyPart.getContent();
                            }
                            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
								String fileName = bodyPart.getFileName();
								File file = new File(TMP_PATH + File.separator + fileName);
								bodyPart.saveFile(file);
								byte[] bFile = Files.readAllBytes(file.toPath());
								String sFile = Base64.encodeBase64String(bFile);
								Attachment attachment = new Attachment();
								attachment.setName(fileName);
								attachment.setData(sFile);
								attachment.setMessage(temp);
								attachment.setMimeType("image*/");
								temp.add(attachment);
								file.delete();
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				
					temp.setContent(result);
					rs.projekatOSA2019_maven.entity.Folder inboxFolder = folderService.findByNameAndAccount("Inbox", account);
					inboxFolder.add(temp);
					temp.setUnread(false);
					account.add(temp);
					messageService.save(temp);
				}
			}
	        emailFolder.close();
	        store.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
