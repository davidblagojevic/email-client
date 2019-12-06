package rs.projekatOSA2019_maven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.entity.Tag;


public interface MessageRepository extends JpaRepository<Message, Integer> {
	List<Message> findBySubject(String subject);
	

}
