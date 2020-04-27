package rs.projekatOSA2019_maven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.projekatOSA2019_maven.entity.Contact;
import rs.projekatOSA2019_maven.entity.User;


public interface ContactRepository extends JpaRepository<Contact, Integer> {
	Contact findByEmail(String email);
}
