package rs.projekatOSA2019_maven.service;

import java.util.List;
import java.util.Optional;

import rs.projekatOSA2019_maven.entity.User;


public interface UserServiceInterface {
	void save(User user);
    
	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String password);
	
	Optional<User> findOne(Integer userId);
	
	List<User> findAll();
	
	
	
}
