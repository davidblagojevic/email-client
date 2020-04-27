package rs.projekatOSA2019_maven.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.projekatOSA2019_maven.dto.AccountDTO;
import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.User;
import rs.projekatOSA2019_maven.service.AccountServiceInterface;
import rs.projekatOSA2019_maven.service.UserServiceInterface;


@RestController
@RequestMapping(value="/accounts")
public class AccountController {
	
	@Autowired
    private AccountServiceInterface accountService;
	
	
	@Autowired
    private UserServiceInterface userService;



	@PutMapping(value = "/addAccount")
	public ResponseEntity<Void> addAccaunt(@RequestBody AccountDTO accountDTO, Principal principal){
		System.out.println("LOGIN..........");
		
		User user = userService.findByUsername(principal.getName());
		
		Account account=new Account();
		account.setFolders(null);
		account.setDisplayname("ddd"+accountDTO.getUsername());
		
		account.setInServerAddress("ddd"+accountDTO.getUsername());
		account.setInServerPort(2230);
		account.setInServerType(123);
//		account.setMessages(null);
		account.setPassword(accountDTO.getPassword());
		account.setPop3Imap("pop3");
		account.setSmtpAdress("admin"+accountDTO.getUsername());
		account.setSmtpPort(2233);
		account.setUser(user);
		account.setUsername(accountDTO.getUsername());
		
		accountService.save(account);
		
		
		
		System.out.println("ACCOUNT.....");
		
		
		return new ResponseEntity<Void>(HttpStatus.OK);

	}
	
//	 @PUT("accounts/addAccaunt/{username}")
//	    Call<Void> addAccaunt(@Body Account account ,@Path("username") String username);
//	


	
	
	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAllAccounts(Principal princpal){
		List<Account> accounts = accountService.findAll();
		
		System.out.println("username*****"+ princpal.getName());
		if (accounts == null) {
			return new ResponseEntity<List<AccountDTO>>(HttpStatus.NOT_FOUND);
		}
		System.out.println("username**5***");
		User user = userService.findByUsername(princpal.getName());
		System.out.println("username***1**"+user.getId());
		List<AccountDTO> AccountDTO = new ArrayList<>();
		for (Account account : accounts) {
			
			if(user.getId()==account.getUser().getId()) {
			
			AccountDTO.add(new AccountDTO(account));
			}
			
		}
		return new ResponseEntity<List<AccountDTO>>(AccountDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/login/{username}/{password}")
	public ResponseEntity<AccountDTO> login(@PathVariable("username") String username, @PathVariable("password") String password){
		System.out.println("LOGIN..........");
		Account account = accountService.findByUsernameAndPassword(username, password);
		if (account == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
	}
	  
	@GetMapping(value="/{id}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Integer id){
		Optional<Account> accountOptional = accountService.findOne(id);
		Account account = accountOptional.get();
		if(account == null){
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
	}
	
	@GetMapping(value="/username/{username}")
	public ResponseEntity<AccountDTO> getAccountByUsername(@PathVariable("username") String username){
		Account account = accountService.findByUsername(username);
		if(account == null){
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
	}
	
	
	
	@PutMapping(value="/{id}", consumes="application/json")
	public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("id") Integer id){
		Optional<Account> accountOptional = accountService.findOne(id);
		Account account = accountOptional.get();
		if (account == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
		}
		
		account.setDisplayname(account.getDisplayname());
		account.setPassword(accountDTO.getPassword());
		account.setPop3Imap(accountDTO.getPop3Imap());
		account.setSmtpAdress(accountDTO.getSmtp());
		account.setUsername(accountDTO.getUsername());
		
		

	
		account = accountService.save(account);
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);	
	}

}

