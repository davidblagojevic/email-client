package rs.projekatOSA2019_maven.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.projekatOSA2019_maven.entity.User;
import rs.projekatOSA2019_maven.repository.UserRepository;


@Service
public class UserService implements UserServiceInterface {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void save(User user) {
		userRepository.save(user);
		
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	public Optional<User> findOne(Integer userId) {
		return userRepository.findById(userId);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
