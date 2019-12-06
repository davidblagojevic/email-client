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
@Table (name="folders")
public class Folder implements Serializable {
	
	public Folder() {}
	
	public Folder(Integer id, String name, Account account, Set<Rule> rules, Set<Message> messages, Folder parent,
			Set<Folder> children) {
		super();
		this.id = id;
		this.name = name;
		this.account = account;
		this.rules = rules;
		this.messages = messages;
		this.parent = parent;
		this.children = children;
	}



	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="folder_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="folder_name", unique=false,nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="account_id", referencedColumnName="account_id", nullable=false)
	private Account account;
	
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="folder" )
	private Set<Rule> rules = new HashSet<Rule>();
	
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="folder" )
	private Set<Message> messages = new HashSet<Message>();
	
	@ManyToOne
	@JoinColumn(name="parent_folder_id", referencedColumnName="folder_id", nullable=true)
	private Folder parent;
	  
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="parent")
	private Set<Folder> children = new HashSet<Folder>();
	
	public void add(Rule rule) {
		if (rule.getFolder() != null) {
			rule.getFolder().getRules().remove(rule);
		}
		rule.setFolder(this);
		getRules().add(rule);
	}
	
	public void add(Message message) {
		if (message.getFolder() != null) {
			message.getFolder().getMessages().remove(message);
		}
		message.setFolder(this);
		getMessages().add(message);
	}
	
	public void add(Folder folder) {
		if (folder.getParent() != null) {
			folder.getParent().getChildren().remove(folder);
		}
		folder.setParent(this);
		getChildren().add(folder);
	}
	
	public void remove(Rule rule) {
		rule.setFolder(null);
		getRules().remove(rule);
	}
	
	public void remove(Message message) {
		message.setFolder(null);
		getMessages().remove(message);
	}
	
	public void remove(Folder folder) {
		folder.setParent(null);
		getChildren().remove(folder);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Folder getParent() {
		return parent;
	}

	public void setParent(Folder parent) {
		this.parent = parent;
	}

	public Set<Folder> getChildren() {
		return children;
	}

	public void setChildren(Set<Folder> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Folder [id=" + id + ", name=" + name + ", account=" + account + ", rules=" + rules + ", messages="
				+ messages + ", parent=" + parent + ", children=" + children + "]";
	}
	
	
	
}
