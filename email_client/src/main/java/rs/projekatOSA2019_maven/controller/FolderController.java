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
import org.springframework.web.bind.annotation.RestController;

import rs.projekatOSA2019_maven.dto.FolderDTO;
import rs.projekatOSA2019_maven.dto.MessageDTO;
import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Folder;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.entity.Rule;
import rs.projekatOSA2019_maven.service.AccountServiceInterface;
import rs.projekatOSA2019_maven.service.FolderServiceInterface;
import rs.projekatOSA2019_maven.service.MessageServiceInterface;



@RestController
@RequestMapping(value="/folders")
public class FolderController {
	@Autowired
	FolderServiceInterface folderService;
	
	@Autowired
	AccountServiceInterface accountService;
	
	@Autowired
	MessageServiceInterface messageService;
	
	
	@GetMapping
	public ResponseEntity<List<FolderDTO>> getAllFolders(){
		List<Folder> folders = folderService.findAll();
		if (folders == null) {
			return new ResponseEntity<List<FolderDTO>>(HttpStatus.NOT_FOUND);
		}
		List<FolderDTO> folderDTOs = new ArrayList<>();
		for (Folder folder : folders) {
			folderDTOs.add(new FolderDTO(folder));
		}
		return new ResponseEntity<List<FolderDTO>>(folderDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/account/{username}")
	public ResponseEntity<List<FolderDTO>> getAllFoldersFromUser(@PathVariable("username") String username){
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return new ResponseEntity<List<FolderDTO>>(HttpStatus.BAD_REQUEST);
		}
		List<FolderDTO> folderDTOs = new ArrayList<>();
		for (Folder itFolder : account.getFolders()) {
			folderDTOs.add(new FolderDTO(itFolder));
		}
		return new ResponseEntity<List<FolderDTO>>(folderDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<FolderDTO> getFolder(@PathVariable("id") Integer id){
		Optional<Folder> folderOptional = folderService.findOne(id);
		Folder folder = folderOptional.get();
		if(folder == null){
			return new ResponseEntity<FolderDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteFolder(@PathVariable("id") Integer id){
		System.out.println("BRISI.....");
		Optional<Folder> folderOptional = folderService.findOne(id);
		Folder folder = folderOptional.get();
		if (folder != null){
			folderService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {		
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/{username}/{subject}",consumes="application/json")
	public ResponseEntity<FolderDTO> addNewFolder(@RequestBody FolderDTO folderDTO,
			@PathVariable("username") String username, @PathVariable("subject") String subject){
		System.out.println("Dodavanje foldera");
		Account account = accountService.findByUsername(username);
		
		if (account == null) {
			return new ResponseEntity<FolderDTO>(HttpStatus.BAD_REQUEST);
		}
		Folder folder = new Folder();
		
		

		folder.setName(folderDTO.getName());
		if (folder.getRules() == null) {
			folder.setRules(new HashSet<>());
		}
		Rule rule = new Rule();

		rule.setCondition(folderDTO.getRule().getCondition());
		rule.setValue("Doda");
		rule.setOperation(folderDTO.getRule().getOperation());
		
		account.add(folder);
		
		
		
		folder.setName(folderDTO.getName());
		List<Message> subjectMessages = new ArrayList<>();
		
		List<MessageDTO> messageDTOs = new ArrayList<>();
		subjectMessages = messageService.findBySubject(subject);
		if (subjectMessages != null ) {
			for (Message itMessage : subjectMessages) {
				if (itMessage.getFolder() != null && 
						!itMessage.getFolder().getName().equals("Drafts")) {
//					itMessage.getFolder().remove(itMessage);
					messageDTOs.add(new MessageDTO(itMessage));
					
					messageService.remove(itMessage.getId());
					folder.add(itMessage);
					folder.getMessages().add(itMessage);
				}
			}
			
		}
		folder.add(rule);
		folder = folderService.save(folder);
		
		return new ResponseEntity<FolderDTO>(new FolderDTO(folder),HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes="application/json")
	public ResponseEntity<FolderDTO> updateFolder(@RequestBody FolderDTO folderDTO, @PathVariable("id") Integer id){
		//a category must exist
		System.out.println("ajde");
		Optional<Folder> folderOptional = folderService.findOne(id);
		Folder folder = folderOptional.get();
		if (folder == null) {
			return new ResponseEntity<FolderDTO>(HttpStatus.BAD_REQUEST);
		}
		
		folder.setName(folderDTO.getName());
		Rule rule;
		if (folder.getRules().iterator().hasNext()) {
			
			rule = folder.getRules().iterator().next();
		} else {
			rule = new Rule();
		}
		
		rule.setOperation(folderDTO.getRule().getOperation());
		rule.setCondition(folderDTO.getRule().getCondition());
		
		folder.add(rule);
		
		folder = folderService.save(folder);
		
		return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK);	
	}
	

	
	
}
