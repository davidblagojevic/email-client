package rs.projekatOSA2019_maven.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rs.projekatOSA2019_maven.dto.PhotoDTO;
import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Photo;
import rs.projekatOSA2019_maven.service.AccountServiceInterface;
import rs.projekatOSA2019_maven.service.PhotoServiceInterface;


public class PhotoController {
	@Autowired
	PhotoServiceInterface photoService;
	
	@Autowired
	AccountServiceInterface accountService;
	
	
	@PostMapping(value="/{username}",consumes="application/json")
	public ResponseEntity<PhotoDTO> savePhoto(@RequestBody PhotoDTO contactDTO, @PathVariable("username") String username){
		Photo contact = new Photo();
		contact.setId(contactDTO.getId());
		contact.setPath(contactDTO.getPath());
		
//		Set<Photo> photos = new HashSet<>();
//		if (contactDTO.getPhoto() != null) {
//			Photo photo = new Photo();
//			photo.setPath(contactDTO.getPhoto().getPath());
//			
//			photo.setContact(contact);
//		}
		
//		contact.setPhotos(photos);
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return new ResponseEntity<PhotoDTO>(HttpStatus.BAD_REQUEST);
		}
//		contact.setUser(account.getUser());
	
//		System.out.println("NOadsadiasad sKLaslks: "+ contactDTO.getPhoto().getPath());
		photoService.save(contact);
		return new ResponseEntity<PhotoDTO>(new PhotoDTO(contact), HttpStatus.CREATED);	
	}
	

}
