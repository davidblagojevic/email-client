package rs.projekatOSA2019_maven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.projekatOSA2019_maven.entity.Attachment;


public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

}
