package rs.projekatOSA2019_maven.mailAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import rs.projekatOSA2019_maven.service.AccountServiceInterface;
import rs.projekatOSA2019_maven.service.FolderServiceInterface;
import rs.projekatOSA2019_maven.service.MessageServiceInterface;

public class MailLoader {
	private static final Logger log = LoggerFactory.getLogger(MailLoader.class);
	@Autowired
	private AccountServiceInterface accountService;
	@Autowired
	private MessageServiceInterface messageService;
	@Autowired 
	FolderServiceInterface folderService;
	
	@Scheduled(fixedRate = 60)
	public void startLoadingEmails() {
//		MailGetter mailGetter = new MailGetter(messageService, folderService);
//		mailGetter.loadEmails(accountService.findOne(1).get());
		log.info("New emails loaded");
		System.out.println("ajde");
	}
}
