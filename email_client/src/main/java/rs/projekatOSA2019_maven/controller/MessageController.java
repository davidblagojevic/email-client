package rs.projekatOSA2019_maven.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rs.projekatOSA2019_maven.dto.AttachmentDTO;
import rs.projekatOSA2019_maven.dto.MessageDTO;
import rs.projekatOSA2019_maven.dto.TagDTO;
import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Attachment;
import rs.projekatOSA2019_maven.entity.Folder;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.entity.Rule;
import rs.projekatOSA2019_maven.entity.Rule.Condition;
import rs.projekatOSA2019_maven.entity.Rule.Operation;
import rs.projekatOSA2019_maven.entity.Tag;
import rs.projekatOSA2019_maven.entity.User;
import rs.projekatOSA2019_maven.etc.Util;
import rs.projekatOSA2019_maven.mailAPI.MailGetter;
import rs.projekatOSA2019_maven.mailAPI.MailSender;
import rs.projekatOSA2019_maven.service.AccountServiceInterface;
import rs.projekatOSA2019_maven.service.FolderServiceInterface;
import rs.projekatOSA2019_maven.service.MessageServiceInterface;
import rs.projekatOSA2019_maven.service.TagServiceInterface;
import rs.projekatOSA2019_maven.service.UserServiceInterface;



@RestController
@RequestMapping(value="messages")
public class MessageController {
	@Autowired
	MessageServiceInterface messageService;
	
	@Autowired
	AccountServiceInterface accountService;
	
	@Autowired
	FolderServiceInterface folderService;
	
	@Autowired
	TagServiceInterface tagService;
	
	@Autowired
	UserServiceInterface userService;
	
	
	@GetMapping
	public ResponseEntity<List<MessageDTO>> getAllMessages(){
		List<Message> messages = messageService.findAll();
		if (messages == null) {
			return new ResponseEntity<List<MessageDTO>>(HttpStatus.NOT_FOUND);
		}
		List<MessageDTO> messageDTOs = new ArrayList<>();
		for (Message message : messages) {
			messageDTOs.add(new MessageDTO(message));
		}
		return new ResponseEntity<List<MessageDTO>>(messageDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/account/{username}")
	public ResponseEntity<List<MessageDTO>> getAllMessagesFromUser(@PathVariable("username") String username, Principal principal){
		User user = userService.findByUsername(principal.getName());
		
		MailGetter mailGetter = new MailGetter(messageService, folderService);
		Account account = accountService.findByUsername(username);
		if (account == null || !account.getUser().getUsername().equals(user.getUsername())) {
			return new ResponseEntity<List<MessageDTO>>(HttpStatus.BAD_REQUEST);
		}
		Date maxDate = account.getMessages().stream().map(Message::getDateTime).max(Date::compareTo).get();
		mailGetter.loadEmails(account, maxDate);
		List<MessageDTO> messageDTOs = new ArrayList<>();
		for (Message itMessage : account.getMessages()) {
			if (itMessage.getFolder().getName().equals("Inbox")) {
				messageDTOs.add(new MessageDTO(itMessage));
			}

		}
		return new ResponseEntity<List<MessageDTO>>(messageDTOs, HttpStatus.OK);
	}
	@GetMapping(value = "/account/sortAsc/{username}")
	public ResponseEntity<List<MessageDTO>> getAllMessagesFromUserSortAsc(@PathVariable("username") String username){
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return new ResponseEntity<List<MessageDTO>>(HttpStatus.BAD_REQUEST);
		}
		List<MessageDTO> messageDTOs = new ArrayList<>();
		for (Message itMessage : account.getMessages()) {
			if (itMessage.getFolder().getName().equals("Inbox")) {
				messageDTOs.add(new MessageDTO(itMessage));
			}
		}
		
		messageDTOs.sort(Comparator.comparing(MessageDTO :: getDateTime));
		return new ResponseEntity<List<MessageDTO>>(messageDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/account/sortDes/{username}")
	public ResponseEntity<List<MessageDTO>> getAllMessagesFromUserSortDesc(@PathVariable("username") String username){
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return new ResponseEntity<List<MessageDTO>>(HttpStatus.BAD_REQUEST);
		}
		List<MessageDTO> messageDTOs = new ArrayList<>();
		for (Message itMessage : account.getMessages()) {
			if (itMessage.getFolder().getName().equals("Inbox")) {
				messageDTOs.add(new MessageDTO(itMessage));
			}
		}
		
		messageDTOs.sort(Comparator.comparing(MessageDTO :: getDateTime).reversed());
		return new ResponseEntity<List<MessageDTO>>(messageDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<MessageDTO> getMessage(@PathVariable("id") Integer id){
		Optional<Message> optionalMessage = messageService.findOne(id);
		Message message = optionalMessage.get();
		if(message == null){
			return new ResponseEntity<MessageDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MessageDTO>(new MessageDTO(message), HttpStatus.OK);
	}
	
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable("id") Integer id){
		Optional<Message> optionalMessage = messageService.findOne(id);
		Message message = optionalMessage.get();
		if (message != null){
			messageService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {		
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping(value = "/addToDraft/{username}", consumes="application/json")
	public ResponseEntity<MessageDTO> addToDraft(@RequestBody MessageDTO messageDTO, @PathVariable("username") String username){
		System.out.println("ADD TO DRAFT");
		Account account = accountService.findByUsername(username);
		
		if (account == null) {
			return new ResponseEntity<MessageDTO>(HttpStatus.BAD_REQUEST);
		}
		User user = account.getUser();
		Folder folder = folderService.findByNameAndAccount("Drafts", account);
		if (folder == null) {
			Rule rule = new Rule(55, Condition.SUBJECT, "DRAFT", Operation.MOVE, null);
			Set<Rule> rules = new HashSet<Rule>();
			rules.add(rule);
			folder = new Folder(55, "Drafts", account, rules, new HashSet<Message>(), null, null);
			rule.setFolder(folder);
		}
		Message message = new Message();
		Util.getToCCBccToString(messageDTO, message);
//		message.setAccount(account);
		account.add(message);
		
		Attachment attachment = new Attachment();
		if (messageDTO.getAttachments() != null) {			
			for (AttachmentDTO itAttachmentDTO : messageDTO.getAttachments()) {
				
				attachment.setData(itAttachmentDTO.getData());
				attachment.setId(itAttachmentDTO.getId());
				attachment.setMessage(message);
				attachment.setMimeType(itAttachmentDTO.getType());
				attachment.setName(itAttachmentDTO.getName());
				
				message.add(attachment);
			}
		}
		message.setDateTime(messageDTO.getDateTime());
		message.setFolder(folder);
		message.setFrom(messageDTO.getFrom());
//		message.setId(0);
		message.setSubject(messageDTO.getSubject());
		message.setContent(messageDTO.getContent());
		Tag tag = new Tag();
		if (messageDTO.getTags() != null) {
			for (TagDTO itTagDTO : messageDTO.getTags()) {
				tag.setId(itTagDTO.getId());
				tag.setName(itTagDTO.getName());
				tag.setUser(user);
				message.getTags().add(tag);
			}
		}
		message.setUnread(messageDTO.isProcitano());
		message.setSubject(messageDTO.getSubject());
		
		message = messageService.save(message);
		
//		Message message = new Message(55, account.getUsername(), "", "", "", dateTime, subject, content, unread, account, folder, attachments, tags)
		
		return new ResponseEntity<MessageDTO>(new MessageDTO(message) ,HttpStatus.OK);
	}
	
	@PostMapping(value = "/send/{username}", consumes="application/json")
	public ResponseEntity<MessageDTO> send(@RequestBody MessageDTO messageDTO, @PathVariable("username") String username){
		System.out.println("SEND SEND SEND");
		
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return new ResponseEntity<MessageDTO>(HttpStatus.BAD_REQUEST);
		}
		
		
		
		User user = account.getUser();
		Folder folder = folderService.findByNameAndAccount("Sent", account);
		if (folder == null) {
			Rule rule = new Rule(55, Condition.SUBJECT, "Sent", Operation.MOVE, null);
			Set<Rule> rules = new HashSet<Rule>();
			rules.add(rule);
			folder = new Folder(55, "Sent", account, rules, new HashSet<Message>(), null, null);
			rule.setFolder(folder);
		}
		Optional<Message> messageOptional = messageService.findOne(messageDTO.getId());
		Message message = null;
		if (messageOptional.isPresent()) {
			message = messageOptional.get();
		} 
		
		if (message != null && message.getFolder().getName().equals("Drafts")) {
			messageService.remove(message.getId());
		} else {
			message = new Message();
		}
		
		Util.getToCCBccToString(messageDTO, message);
//		message.setAccount(account);
		account.add(message);
		
		
		if (messageDTO.getAttachments() != null) {			
			for (AttachmentDTO itAttachmentDTO : messageDTO.getAttachments()) {
				Attachment attachment = new Attachment();
				System.out.println(itAttachmentDTO.getName());
				attachment.setData(itAttachmentDTO.getData());
				attachment.setMessage(message);
				attachment.setMimeType(itAttachmentDTO.getType());
				attachment.setName(itAttachmentDTO.getName());
				
				message.add(attachment);
			}
		}
		message.setDateTime(messageDTO.getDateTime());
		folder.add(message);
		message.setFrom(messageDTO.getFrom());
		System.out.println(messageDTO);
		message.setSubject(messageDTO.getSubject());
		message.setContent(messageDTO.getContent());
		Tag tag = new Tag();
		if (messageDTO.getTags() != null) {
			for (TagDTO itTagDTO : messageDTO.getTags()) {
				tag.setId(itTagDTO.getId());
				tag.setName(itTagDTO.getName());
				tag.setUser(user);
				message.getTags().add(tag);
			}
		}
		message.setUnread(messageDTO.isProcitano());
		message.setSubject(messageDTO.getSubject());
		MailSender mailSender = new MailSender(messageService);
		mailSender.sendEmail(message);
//		message = messageService.save(message);
		
//		Message message = new Message(55, account.getUsername(), "", "", "", dateTime, subject, content, unread, account, folder, attachments, tags)
		
		return new ResponseEntity<MessageDTO>(new MessageDTO(message) ,HttpStatus.OK);
	}
    @PutMapping(value = "/viewed/{id}")
    ResponseEntity<Void> getviewedMessages(@PathVariable("id") int id){
    	System.out.println("aj");
		Optional<Message> messageOptional = messageService.findOne(id);
		Message message = messageOptional.get();
    	if (message == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
    	message.setUnread(true);
    	messageService.save(message);
    	
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
	@PostMapping("/upload")
	@ResponseBody
	public void upoladImage(@RequestPart("description") String story, @RequestPart("picture") MultipartFile file) {
		System.out.println("ajde majke ti");
		try {
			System.out.println(story);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}
		System.out.println("start");
	}
	


	@GetMapping(value="/tag/{username}/{tagName}")
	public ResponseEntity<List<MessageDTO>> saveContact(@PathVariable("username") String username,
			@PathVariable("tagName") String tagName){
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return new ResponseEntity<List<MessageDTO>>(HttpStatus.BAD_REQUEST);
		}
		List<MessageDTO> messageDTOs = new ArrayList<>();
		for (Tag itTag : account.getUser().getUserTags()) {
			if(itTag.getName().equals(tagName)) {
				for (Message itMessage : itTag.getMessages()) {
					if(itMessage.getAccount().getUsername().equals(username)) {
						messageDTOs.add(new MessageDTO(itMessage));
					}
				}
			}
		}
				
		return new ResponseEntity<List<MessageDTO>>(messageDTOs, HttpStatus.OK);	
	}
	
	@PostMapping(value="/tag/{messageId}/{tagName}/{add}")
	public ResponseEntity<Void> addOrRemoveTag(@PathVariable("messageId") int messageId,
			@PathVariable("tagName") String tagName, @PathVariable("add") boolean add){
		
		Message message = messageService.findOne(messageId).get();
		if (message == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}	
		Tag tag = tagService.findByNameAndUser(tagName, message.getAccount().getUser());
		if (tag == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		for (Tag itTag : message.getTags()) {
			if (!add &&itTag.getName().equals(tagName)) {
				message.getTags().remove(itTag);
				break;
			}
			if (add && itTag.getName().equals(tagName)) {
				message.getTags().add(tag);
				break;
			} 
		}
		messageService.save(message);

		
				
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
		
	
    
 
    

}

