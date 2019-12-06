package rs.projekatOSA2019_maven.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="accounts")
public class Account implements Serializable {
	public Account() {}
	
	public Account(Integer id, String smtpAdress, Integer smtpPort, Integer inServerType,String pop3Imap ,String inServerAddress,
			Integer inServerPort, String username, String password, String displayname, User user,
			Set<Message> messages, Set<Folder> folders) {
		super();
		this.id = id;
		this.smtpAdress = smtpAdress;
		this.smtpPort = smtpPort;
		this.inServerType = inServerType;
		this.pop3Imap = pop3Imap;
		this.inServerAddress = inServerAddress;
		this.inServerPort = inServerPort;
		this.username = username;
		this.password = password;
		this.displayname = displayname;
		this.user = user;
		this.messages = messages;
		this.folders = folders;
	}


	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="account_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name = "account_smtp_adress", unique=true, nullable=false, length=250)
	private String smtpAdress;
	
	@Column(name = "account_smtp_port", unique=false, nullable=false)
	private Integer smtpPort;
	
	@Column(name = "account_in_server_type", unique=false, nullable=false)
	private Integer inServerType;
	
	@Column(name = "account_pop3_imap", unique=false, nullable=false, length=10)
	private String pop3Imap;
	
	@Column(name = "account_in_server_address", unique=false, nullable=false, length=250)
	private String inServerAddress;
	
	@Column(name="account_in_server_port",unique=false,nullable=false)
	private Integer inServerPort;
	
	@Column(name="account_username",unique=true,nullable=true, length=50)
	private String username;
	
	@Column(name="account_password",unique=true,nullable=true,length=20)
	private String password;
	
	@Column(name="account_displayname",unique=true,nullable=false,length=100)
	private String displayname;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="user_id", nullable=false)
	private User user;
	
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="account" )
	private Set<Message> messages = new HashSet<Message>();
	
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="account" )
	private Set<Folder> folders = new HashSet<Folder>();

	public void add(Message message) {
		if (message.getAccount() != null) {
			message.getAccount().getMessages().remove(message);
		}
		message.setAccount(this);
		getMessages().add(message);
	}
	
	public void add(Folder folder) {
		if (folder.getAccount() != null) {
			folder.getAccount().getFolders().remove(folder);
		}
		folder.setAccount(this);
		getFolders().add(folder);
	}
	
	public void remove(Message message) {
		message.setAccount(null);
		getMessages().remove(message);
	}
	
	public void remove(Folder folder) {
		folder.setAccount(null);
		getFolders().remove(folder);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSmtpAdress() {
		return smtpAdress;
	}

	public void setSmtpAdress(String smtpAdress) {
		this.smtpAdress = smtpAdress;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public Integer getInServerType() {
		return inServerType;
	}

	public void setInServerType(Integer inServerType) {
		this.inServerType = inServerType;
	}

	public String getPop3Imap() {
		return pop3Imap;
	}

	public void setPop3Imap(String pop3Imap) {
		this.pop3Imap = pop3Imap;
	}

	public String getInServerAddress() {
		return inServerAddress;
	}

	public void setInServerAddress(String inServerAddress) {
		this.inServerAddress = inServerAddress;
	}

	public Integer getInServerPort() {
		return inServerPort;
	}

	public void setInServerPort(Integer inServerPort) {
		this.inServerPort = inServerPort;
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

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Set<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Set<Folder> folders) {
		this.folders = folders;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", smtpAdress=" + smtpAdress + ", smtpPort=" + smtpPort + ", inServerType="
				+ inServerType + ", pop3Imap=" + pop3Imap + ", inServerAddress=" + inServerAddress + ", inServerPort="
				+ inServerPort + ", username=" + username + ", password=" + password + ", displayname=" + displayname
				+ ", user=" + user + ", messages=" + messages + ", folders=" + folders + "]";
	}

	
	
}
