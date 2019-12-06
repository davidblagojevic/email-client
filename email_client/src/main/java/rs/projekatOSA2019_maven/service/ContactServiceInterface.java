package rs.projekatOSA2019_maven.service;

import java.util.List;
import java.util.Optional;

import rs.projekatOSA2019_maven.entity.Contact;


public interface ContactServiceInterface {
	Optional<Contact> findOne(Integer contactId);
	
	List<Contact> findAll();
	
	Contact save(Contact contact);
	
	void remove(Integer id);
	
}
