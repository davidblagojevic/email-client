package rs.projekatOSA2019_maven.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.entity.Tag;
import rs.projekatOSA2019_maven.repository.MessageRepository;

@Service
public class MessageService implements MessageServiceInterface {
	
	@Autowired
	MessageRepository messageRepository;



	@Override
	public Optional<Message> findOne(Integer folderId) {
		return messageRepository.findById(folderId);
	}

	@Override
	public List<Message> findAll() {
		return messageRepository.findAll();
	}

	@Override
	public Message save(Message message) {
		return messageRepository.save(message);
	}

	@Override
	public void remove(Integer id) {
		messageRepository.deleteById(id);
		
	}

	@Override
	public List<Message> findBySubject(String subject) {
		return messageRepository.findBySubject(subject);
	}



}

