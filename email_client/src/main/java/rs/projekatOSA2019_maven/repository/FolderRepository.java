package rs.projekatOSA2019_maven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Folder;


public interface FolderRepository extends JpaRepository<Folder, Integer> {
	
	List<Folder> findByParent(Folder parent);
	
	Folder findByName(String name);
	
	Folder findByNameAndAccount(String name, Account account);

}
