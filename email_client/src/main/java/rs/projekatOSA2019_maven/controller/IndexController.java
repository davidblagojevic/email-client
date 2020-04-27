package rs.projekatOSA2019_maven.controller;

import java.io.IOException;
import java.security.Principal;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import rs.projekatOSA2019_maven.entity.Contact;
import rs.projekatOSA2019_maven.entity.User;
import rs.projekatOSA2019_maven.lucene.IndexableDocumentContact;
import rs.projekatOSA2019_maven.lucene.index.IndexerContact;
import rs.projekatOSA2019_maven.service.AccountServiceInterface;
import rs.projekatOSA2019_maven.service.ContactServiceInterface;
import rs.projekatOSA2019_maven.service.MessageServiceInterface;
import rs.projekatOSA2019_maven.service.UserServiceInterface;

@RestController
@RequestMapping(value="index")
public class IndexController {

	@Autowired
	UserServiceInterface userService;
	
	@Autowired
	AccountServiceInterface accountService;

	@Autowired 
	MessageServiceInterface messageService;
	
	@Autowired
	ContactServiceInterface contactService;
	
	@GetMapping(value = "/contacts")
	public ResponseEntity<Void> indexContacts(Principal principal) throws IOException{
		User user = userService.findByUsername(principal.getName());
		if (user == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		for (Contact itContact : user.getContacts()) {
			IndexableDocumentContact indexableContact = new IndexableDocumentContact(itContact);
			Document documentContact = IndexerContact.createDocument(indexableContact);
			IndexerContact.indexDocument(documentContact);
			
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/messages")
	public ResponseEntity<Void> indexMessages(Principal principal){
		User user = userService.findByUsername(principal.getName());
		if (user == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		for (iterable_type iterable_element : iterable) {
			
		}
		
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
