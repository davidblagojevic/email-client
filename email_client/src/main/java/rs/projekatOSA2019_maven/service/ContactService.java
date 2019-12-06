package rs.projekatOSA2019_maven.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.projekatOSA2019_maven.entity.Contact;
import rs.projekatOSA2019_maven.repository.ContactRepository;


@Service
public class ContactService implements ContactServiceInterface {
	
	@Autowired
	ContactRepository contactRepository;

//	@Override
//	public List<Folder> findByParent(Folder parent) {
//		return folderRepository.findByParent(parent);
//	}

	@Override
	public Optional<Contact> findOne(Integer contactId) {
		return contactRepository.findById(contactId);
	}

	@Override
	public List<Contact> findAll() {
		return contactRepository.findAll();
	}

	@Override
	public Contact save(Contact folder) {
		return contactRepository.save(folder);
	}

	@Override
	public void remove(Integer id) {
		contactRepository.deleteById(id);
		
	}

}
