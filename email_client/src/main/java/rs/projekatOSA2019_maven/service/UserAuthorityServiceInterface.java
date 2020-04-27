package rs.projekatOSA2019_maven.service;

import java.util.Optional;

import rs.projekatOSA2019_maven.entity.User;

public interface UserAuthorityServiceInterface {
	
	public Optional<User> getUserWithAuthorities();
}
