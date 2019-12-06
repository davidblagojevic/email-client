package rs.projekatOSA2019_maven.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.repository.AccountRepository;


@Service
public class AccountService implements AccountServiceInterface {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
		
	}

	@Override
	public Account findByUsername(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	public Optional<Account> findOne(Integer accountId) {
		
		return accountRepository.findById(accountId);
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account findByUsernameAndPassword(String username, String password) {
		return accountRepository.findByUsernameAndPassword(username, password);
	}



}
