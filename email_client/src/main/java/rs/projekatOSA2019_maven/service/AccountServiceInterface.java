package rs.projekatOSA2019_maven.service;

import java.util.List;
import java.util.Optional;

import rs.projekatOSA2019_maven.entity.Account;


public interface AccountServiceInterface {
	
	Account save(Account account);
    
	Account findByUsername(String username);

	Optional<Account> findOne(Integer accountId);
	
	List<Account> findAll();
	
	Account findByUsernameAndPassword(String username, String password);
	
	
}
