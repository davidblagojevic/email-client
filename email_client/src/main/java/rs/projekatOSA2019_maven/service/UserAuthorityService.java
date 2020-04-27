package rs.projekatOSA2019_maven.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import rs.projekatOSA2019_maven.entity.User;
import rs.projekatOSA2019_maven.repository.UserRepository;
import rs.projekatOSA2019_maven.security.SecurityUtils;

@Service
@Transactional
public class UserAuthorityService implements UserAuthorityServiceInterface {

	private final UserRepository userRepository;
	
    public UserAuthorityService(UserRepository userRepository) {
    	this.userRepository = userRepository;
	}
	
    @Transactional(readOnly = true)
	@Override
	public Optional<User> getUserWithAuthorities() {
		return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
	}

}
