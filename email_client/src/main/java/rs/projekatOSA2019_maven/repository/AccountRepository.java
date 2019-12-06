package rs.projekatOSA2019_maven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.projekatOSA2019_maven.entity.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	Account findByUsername(String username);
	
	Account findByUsernameAndPassword(String username, String password);
	
}
