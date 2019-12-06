package rs.projekatOSA2019_maven.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rs.projekatOSA2019_maven.dto.ContactDTO;
import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Contact;
import rs.projekatOSA2019_maven.entity.Photo;
import rs.projekatOSA2019_maven.service.AccountServiceInterface;
import rs.projekatOSA2019_maven.service.ContactServiceInterface;
import rs.projekatOSA2019_maven.service.PhotoServiceInterface;


@RestController
@RequestMapping(value="/contacts")
public class ContactController {
	
	@Autowired
	ContactServiceInterface contactService;
	
	@Autowired
	AccountServiceInterface accountService;
	
	@Autowired
	PhotoServiceInterface photoService;
	
	@GetMapping
	public ResponseEntity<List<ContactDTO>> getAllContacts(){
		List<Contact> contacts = contactService.findAll();
		if (contacts == null) {
			return new ResponseEntity<List<ContactDTO>>(HttpStatus.NOT_FOUND);
		}
		List<ContactDTO> contactDTOs = new ArrayList<>();
		for (Contact contact : contacts) {
			contactDTOs.add(new ContactDTO(contact));
		}
		return new ResponseEntity<List<ContactDTO>>(contactDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/account/{username}")
	public ResponseEntity<List<ContactDTO>> getAllContactsFromUser(@PathVariable("username") String username){
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return new ResponseEntity<List<ContactDTO>>(HttpStatus.BAD_REQUEST);
		}
		List<ContactDTO> contactDTOs = new ArrayList<>();
		if (account.getUser() != null && account.getUser().getContacts() != null) {
			for (Contact itContact  : account.getUser().getContacts()) {
				contactDTOs.add(new ContactDTO(itContact));
			}
		}

		return new ResponseEntity<List<ContactDTO>>(contactDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ContactDTO> getContact(@PathVariable("id") Integer id){
		Optional<Contact> optionalContact = contactService.findOne(id);
		Contact contact = optionalContact.get();
		if(contact == null){
			return new ResponseEntity<ContactDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);
	}
	@GetMapping(value = "/emails/account/{username}")
	public ResponseEntity<List<String>> getAllEmails(@PathVariable("username") String username){
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return new ResponseEntity<List<String>>(HttpStatus.BAD_REQUEST);
		}
		List<String> emails = new ArrayList<>();
		if (account.getUser() != null && account.getUser().getContacts() != null) {
			for (Contact itContact  : account.getUser().getContacts()) {
				emails.add(itContact.getEmail());
			}
			
		}
		return new ResponseEntity<List<String>>(emails, HttpStatus.OK);
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteContact(@PathVariable("id") Integer id){
		Optional<Contact> optionalContact = contactService.findOne(id);
		Contact contact = optionalContact.get();
		if (contact != null){
			contactService.remove(id);
			
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {		
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/{username}",consumes="application/json")
	public ResponseEntity<ContactDTO> saveContact(@RequestBody ContactDTO contactDTO, @PathVariable("username") String username){
		Contact contact = new Contact();
		contact.setDisplayname(contactDTO.getDisplay());
		contact.setEmail(contactDTO.getEmail());
		contact.setFirstname(contactDTO.getFirst());
		contact.setFormat(contactDTO.getFormat());
		contact.setLastname(contactDTO.getLast());
		contact.setNote("");
		Photo photo = new Photo();
		
		if (contactDTO.getPhoto() != null) {
			if (contactDTO.getPhoto().getId() != 0) {
				photo.setId(contactDTO.getPhoto().getId());
			}
			photo.setPath(contactDTO.getPhoto().getPath());
			
			contact.add(photo);;
		}
		
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return new ResponseEntity<ContactDTO>(HttpStatus.BAD_REQUEST);
		}
		account.getUser().add(contact);
	
		contact = contactService.save(contact);
		
//		photo = photoService.save(photo);
		
		
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);	
	}
	
	@PutMapping(value="/{id}/{username}",consumes="application/json")
	public ResponseEntity<ContactDTO> editContact(@RequestBody ContactDTO contactDTO,
			@PathVariable("id") Integer id,@PathVariable("username") String username){
		System.out.println("IZMENA........");
//		System.out.println(username);
//		System.out.println(contactDTO.getDisplay() + contactDTO.getEmail() + contactDTO.getFirst() + contactDTO.getId() + contactDTO.getLast());
		Account account = accountService.findByUsername(username);
		Optional<Contact> optionalContact = contactService.findOne(id);
		Contact contact = optionalContact.get();
		if (account == null || contact == null) {
			System.out.println("BAD REQUEST");
			return new ResponseEntity<ContactDTO>(HttpStatus.BAD_REQUEST);
		}
		contact.setId(contactDTO.getId());
		contact.setDisplayname(contactDTO.getDisplay());
		contact.setEmail(contactDTO.getEmail());
		contact.setFirstname(contactDTO.getFirst());
		contact.setFormat(contactDTO.getFormat());
		contact.setLastname(contactDTO.getLast());
		contact.setNote("");
		
		Photo photo;
		if (contactDTO.getPhoto() != null && contactDTO.getId()!= 0 && contactDTO.getPhoto().getPath() != null &&
				!contactDTO.getPhoto().getPath().equals("")) {
			if (contact.getPhotos().iterator().hasNext()) {
				
				 photo = contact.getPhotos().iterator().next();
				
				photo.setPath(contactDTO.getPhoto().getPath());
				photo.setContact(contact);
				contact.add(photo);
				
			} else {
				photo = new Photo();
				photo.setPath(contactDTO.getPhoto().getPath());
				contact.add(photo);
			}
		} else {
			if (contact.getPhotos().iterator().hasNext()) {
				Photo removePhoto = contact.getPhotos().iterator().next();
				if (removePhoto.getId() != 0) {
					contact.remove(removePhoto);
				}
			}
		}


		account.getUser().add(contact);
	
		
		contactService.save(contact);
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);	
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
	
}
