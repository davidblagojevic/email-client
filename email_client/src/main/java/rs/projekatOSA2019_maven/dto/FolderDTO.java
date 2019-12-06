package rs.projekatOSA2019_maven.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rs.projekatOSA2019_maven.entity.Folder;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.entity.Rule;

public class FolderDTO implements Serializable {
    private int id;
    private String name;
    private List<FolderDTO> folders;
    private RuleDTO rule;
    private List<MessageDTO> messages;

    public FolderDTO(){}

    public FolderDTO(int id, String name, List<FolderDTO> folders, RuleDTO rule, List<MessageDTO> messages){
        this.id = id;
        this.name = name;
        this.folders = folders;
        this.rule = rule;
        this.messages = messages;
    }
    
    public FolderDTO (Folder folder) {
    	Rule rule= null;
    	if (folder.getRules() != null) {
    		System.out.println(folder.getRules().size());
    		Iterator<Rule> iterator = folder.getRules().iterator();
    		while(iterator.hasNext()) {
    			System.out.println("while1");
    			rule = iterator.next();
    		}
        	
		} 
    	List<FolderDTO> folderDTOs = new ArrayList<>();
    	//folderi
    	if (folder.getChildren() != null && !folder.getChildren().isEmpty()) {
        	for (Folder itFolder : folder.getChildren()) {
        		List<MessageDTO> messageDTOs2 = new ArrayList<>();
            	for (Message itMessage: folder.getMessages()) {
        			messageDTOs2.add(new MessageDTO(itMessage));
        		}
        		Rule itRule = null;
        		Iterator<Rule> iterator = itFolder.getRules().iterator();
        		while(iterator.hasNext()) {
        			itRule = iterator.next();
        		}
        		RuleDTO itRuleDTO =(itRule == null)?null: new RuleDTO(itRule);
        		
    			FolderDTO itFolderDTO = new FolderDTO(itFolder.getId(), itFolder.getName(),
    					new ArrayList<>(), itRuleDTO, messageDTOs2);
    			folderDTOs.add(itFolderDTO);
    		}
		}

    	//poruke
    	List<MessageDTO> messageDTOs = new ArrayList<>();
    	if (folder.getMessages() != null) {
        	for (Message itMessage: folder.getMessages()) {
    			messageDTOs.add(new MessageDTO(itMessage));
    		}
		}

    	//
    	
    	RuleDTO ruleDTO = (rule == null)?null: new RuleDTO(rule);

    	this.setId(folder.getId());
    	this.setName(folder.getName());
    	this.setFolders(folderDTOs);
    	this.setRule(ruleDTO);
    	this.setMessage(messageDTOs);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FolderDTO> getFolders() {
        return folders;
    }

    public void setFolders(List<FolderDTO> folders) {
        this.folders = folders;
    }

    public RuleDTO getRule() {
        return rule;
    }

    public void setRule(RuleDTO rule) {
        this.rule = rule;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessage(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

}
