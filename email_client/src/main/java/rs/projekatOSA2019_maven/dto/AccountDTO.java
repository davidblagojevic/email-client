package rs.projekatOSA2019_maven.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rs.projekatOSA2019_maven.entity.Account;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.entity.User;


public class AccountDTO implements Serializable {
    private int id;
    private String smtp;
    private String pop3Imap;
    private String username;
    private String password;
    private List<MessageDTO> messages;


    public AccountDTO() {
    }

    public AccountDTO(int id, String smtp, String pop3Imap, String username, String password, List<MessageDTO> messages) {
        this.id = id;
        this.smtp = smtp;
        this.pop3Imap = pop3Imap;
        this.username = username;
        this.password = password;
        this.messages = messages;
    }
    
    public AccountDTO(Account account) {
    	User user = account.getUser();
    	List<MessageDTO> messageDTOs = new ArrayList<>(); 
    	if (account.getMessages() != null) {
	    	for (Message itMessage : account.getMessages()) {
				messageDTOs.add(new MessageDTO(itMessage));
			}	
		}
    	this.setId(user.getId());
    	this.setSmtp(account.getSmtpAdress());
    	this.setPop3Imap(account.getPop3Imap());
    	this.setUsername(account.getUsername());
    	this.setPassword(account.getPassword());
    	this.setMessages(messageDTOs);
    }

    public AccountDTO(String username,String password) {
        this.username = username;
        this.password=password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getPop3Imap() {
        return pop3Imap;
    }

    public void setPop3Imap(String pop3Imap) {
        this.pop3Imap = pop3Imap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
