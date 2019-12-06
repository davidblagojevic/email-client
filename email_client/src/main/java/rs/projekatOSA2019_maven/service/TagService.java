package rs.projekatOSA2019_maven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.projekatOSA2019_maven.entity.Tag;
import rs.projekatOSA2019_maven.entity.User;
import rs.projekatOSA2019_maven.repository.TagRepository;

@Service
public class TagService implements TagServiceInterface {
	
	@Autowired
	TagRepository tagRepository;
	
	@Override
	public Tag findOne(Integer tagId) {
		return tagRepository.findById(tagId).get();
	}

	@Override
	public List<Tag> findAll() {
		return tagRepository.findAll();
	}

	@Override
	public Tag save(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public void remove(Integer id) {
		tagRepository.deleteById(id);
	}

	@Override
	public Tag findByNameAndUser(String name, User user) {
		return tagRepository.findByNameAndUser(name, user);
	}

}
