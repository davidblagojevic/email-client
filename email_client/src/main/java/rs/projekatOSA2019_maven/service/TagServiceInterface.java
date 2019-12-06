package rs.projekatOSA2019_maven.service;

import java.util.List;

import rs.projekatOSA2019_maven.entity.Tag;
import rs.projekatOSA2019_maven.entity.User;


public interface TagServiceInterface {
	Tag findOne(Integer tagId);
	
	List<Tag> findAll();
	
	Tag save(Tag tag);
	
	void remove(Integer id);
	
	Tag findByNameAndUser(String name, User user);
	
}
