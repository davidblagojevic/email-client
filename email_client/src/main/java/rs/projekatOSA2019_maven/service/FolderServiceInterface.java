package rs.projekatOSA2019_maven.service;


import java.util.List;
import java.util.Optional;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Folder;

public interface FolderServiceInterface {
	List<Folder> findByParent(Folder parent);
	
	Optional<Folder> findOne(Integer folderId);
	
	List<Folder> findAll();
	
	Folder save(Folder folder);
	
	void remove(Integer id);
	
	Folder findByName(String name);
	
	Folder findByNameAndAccount(String name, Account account);
}
