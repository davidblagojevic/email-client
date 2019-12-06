package rs.projekatOSA2019_maven.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.projekatOSA2019_maven.entity.Photo;
import rs.projekatOSA2019_maven.repository.PhotoRepository;


@Service
public class PhotoService implements PhotoServiceInterface{

	@Autowired
	private PhotoRepository photoRepository;
	
	@Override
	public Photo save(Photo photo) {
		return photoRepository.save(photo);
		
	}
	
	@Override
	public Optional<Photo> findById(int id) {
		return photoRepository.findById(id);
	}

	@Override
	public void remove(Integer id) {
		photoRepository.deleteById(id);
		
	}
	
}
