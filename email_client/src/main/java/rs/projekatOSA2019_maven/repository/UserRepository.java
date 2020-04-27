package rs.projekatOSA2019_maven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import rs.projekatOSA2019_maven.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	
	User findByUsernameAndPassword(String username, String password);
   
	@EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
