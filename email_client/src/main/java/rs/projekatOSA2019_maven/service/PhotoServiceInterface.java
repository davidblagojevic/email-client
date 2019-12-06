package rs.projekatOSA2019_maven.service;

import java.util.Optional;

import rs.projekatOSA2019_maven.entity.Photo;

public interface PhotoServiceInterface {
	Photo save(Photo photo);
	
	Optional<Photo> findById(int id);

	void remove(Integer id);
}
