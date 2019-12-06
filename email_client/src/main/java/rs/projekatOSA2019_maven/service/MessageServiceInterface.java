package rs.projekatOSA2019_maven.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.entity.Tag;


public interface MessageServiceInterface {

	Optional<Message> findOne(Integer messageId);
	
	List<Message> findAll();
	
	Message save(Message message);
	
	void remove(Integer id);
	
	List<Message> findBySubject(String subject);
	
}
