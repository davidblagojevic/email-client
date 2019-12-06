package rs.projekatOSA2019_maven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.projekatOSA2019_maven.entity.Tag;
import rs.projekatOSA2019_maven.entity.User;


public interface TagRepository extends JpaRepository<Tag, Integer> {
	List<Tag> findByUser(User user);
	
	Tag findByNameAndUser(String name, User user);
}
