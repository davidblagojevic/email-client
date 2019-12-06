package rs.projekatOSA2019_maven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.projekatOSA2019_maven.entity.Photo;


public interface PhotoRepository extends JpaRepository<Photo, Integer> {

	
	@Override
	public Photo save(Photo photo);
}
