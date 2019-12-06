package rs.projekatOSA2019_maven.controller;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Attachment;
import rs.projekatOSA2019_maven.entity.Contact;
import rs.projekatOSA2019_maven.entity.Folder;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.entity.User;
import rs.projekatOSA2019_maven.service.AccountService;
import rs.projekatOSA2019_maven.service.ContactService;
import rs.projekatOSA2019_maven.service.FolderService;
import rs.projekatOSA2019_maven.service.MessageService;
import rs.projekatOSA2019_maven.service.UserService;



@Controller
public class TestController {

	@Autowired
	FolderService folderService;
	
	@Autowired 
	ContactService contactService;
	
	@Autowired 
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/")
	@ResponseBody
	public String test() {
		
		
		Optional<Folder> folderOptional = folderService.findOne(1);
		Folder folder = folderOptional.get();
		Optional<Contact> optionalContact = contactService.findOne(1);
		Contact contact = optionalContact.get();
		Optional<Message> optionalMessage = messageService.findOne(1);
		Message message = optionalMessage.get();
		Optional<User> optionalUser = userService.findOne(1);
		User user = optionalUser.get();
		Optional<Account> optionalAccount = accountService.findOne(1);
		Account account = optionalAccount.get();
			
		System.out.println(folder.getName() + folder.getAccount().getDisplayname());
		System.out.println(contact.getDisplayname());
		System.out.println(message.getFrom());
		System.out.println(user.getUsername());
		System.out.println(account.getDisplayname());
		
		account.setDisplayname("steva");
		folder.setName("jovica");
		user.setFirstname("lepi");
		account.setDisplayname("jovanca");
		message.setFrom("jovan1233@gmail.com");
		
		accountService.save(account);
		folderService.save(folder);
		userService.save(user);
		accountService.save(account);
		messageService.save(message);
		
		List<Folder> foldersList = folderService.findAll();
		
		Set<Folder> folders = new HashSet<>();
		folders.add(folder);
		
		Set<Message> messages = new HashSet<>();
		messages.add(message);
		
		Account accountTest = new Account(67, "Ss", 21, 21, "pop3", "dd", 212, "ad", "sifra", "jovan", user, messages, folders);
		accountService.save(accountTest);
		
		Attachment attachment = folder.getMessages().iterator().next().getAttachments().iterator().next();
		
		System.out.println(attachment.getData() + attachment.getMimeType() + attachment.getName() + attachment.getId());
		return "Test";
		
	}
	
	
	
//		@Autowired
//		private GorivoService gorivoService;
//		
//		@Autowired
//		private MenjacService menjacService;
//		
//		@Autowired
//		private VoziloService voziloService;
//
//		@GetMapping("/")
//		@ResponseBody
//		public String test() {
//			// Creates object model
//			Gorivo dizel = new Gorivo();
//			dizel.setTipGoriva("dizel");
//			
//			Gorivo benzin = new Gorivo();
//			benzin.setTipGoriva("benzin");
//			
//			dizel = gorivoService.save(dizel);
//			benzin = gorivoService.save(benzin);
//			
//			Menjac automatski = new Menjac();
//			automatski.setTipMenjaca("automatski");
//			
//			Menjac rucni = new Menjac();
//			rucni.setTipMenjaca("rucni");
//			
//			rucni = menjacService.save(automatski);
//			rucni = menjacService.save(rucni);
//			
//			Vozilo fiatPunto = new Vozilo(5, 5, 3, 4500, new Date(), 1999, 	benzin, 1000, rucni, "Fiat Punto", 90000l, 60, 1200, false);
//			Vozilo zastavaYugo = new Vozilo(5, 4, 3, 1200, new Date(), 1989, benzin, 900, rucni, "Zastava Yugo", 97000l, 45, 726, false);
//			
//			fiatPunto = voziloService.save(fiatPunto);
//			zastavaYugo = voziloService.save(zastavaYugo);
//			
//			System.out.println("Entities persisted:\n" + dizel + "\n" + benzin);
//			
//			System.out.println("Entities persisted:\n" + fiatPunto + "\n" + zastavaYugo);
//				
//			return "Select * from gorivo";
//	    }

	
}
