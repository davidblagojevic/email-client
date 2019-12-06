package rs.projekatOSA2019_maven.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Folder;
import rs.projekatOSA2019_maven.repository.FolderRepository;


@Service
public class FolderService implements FolderServiceInterface {
	
	@Autowired
	FolderRepository folderRepository;

	@Override
	public List<Folder> findByParent(Folder parent) {
		return folderRepository.findByParent(parent);
	}


	@Override
	public Optional<Folder> findOne(Integer folderId) {
		return folderRepository.findById(folderId);
	}

	@Override
	public Folder save(Folder folder) {
		return folderRepository.save(folder);
	}

	@Override
	public void remove(Integer id) {
		folderRepository.deleteById(id);
		
	}

	@Override
	public Folder findByName(String name) {
		return folderRepository.findByName(name);
	}

	@Override
	public Folder findByNameAndAccount(String name, Account account) {
		return folderRepository.findByNameAndAccount(name, account);
	}


	@Override
	public List<Folder> findAll() {
		return folderRepository.findAll();
	}
	
	
}
