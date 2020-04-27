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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="users")
public class User implements Serializable {
	
	public User() {}
	
	public User(Integer id, String username, String password, String firstname, String lastname, Set<Contact> contacts,
			Set<Tag> userTags, Set<Account> accounts) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.contacts = contacts;
		this.userTags = userTags;
		this.accounts = accounts;
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="user_id", unique=true, nullable=false)
	private Integer id;
	
	
	@Column(name="user_username",unique=true,nullable=false, length=20)
	private String username;
	
	@Column(name="user_password",unique=false,nullable=false,length=200)
	private String password;
	
	@Column(name="user_firstname",unique=false,nullable=false,length=100)
	private String firstname;
	
	@Column(name="user_lastname",unique=false,nullable=false,length=100)
	private String lastname;
	
	@Column(name = "user_activated", unique=false, nullable=false)
	@ColumnDefault("1")
	private boolean activated;
	
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="user" )
	private Set<Contact> contacts = new HashSet<Contact>();
	
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="user" )
	private Set<Tag> userTags = new HashSet<Tag>();
	
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="user" )
	private Set<Account> accounts = new HashSet<Account>();
	@ManyToMany
	@JoinTable(
			name = "user_authority",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME")})
   private Set<Authority> authorities = new HashSet<>();
	
	public void add(Contact contact) {
		if (contact.getUser() != null) {
			contact.getUser().getContacts().remove(contact);
		}
		contact.setUser(this);
		getContacts().add(contact);
	}
	
	public void add(Tag userTag) {
		if (userTag.getUser() != null) {
			userTag.getUser().getUserTags().remove(userTag);
		}
		userTag.setUser(this);
		getUserTags().add(userTag);
	}
	
	public void add(Account account) {
		if (account.getUser() != null) {
			account.getUser().getAccounts().remove(account);
		}
		account.setUser(this);
		getAccounts().add(account);
	}
	
	public void remove(Contact contact) {
		contact.setUser(null);
		getContacts().remove(contact);
	}
	
	public void remove(Tag userTag) {
		userTag.setUser(null);
		getUserTags().remove(userTag);
	}
	
	public void remove(Account account) {
		account.setUser(null);
		getAccounts().remove(account);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Set<Tag> getUserTags() {
		return userTags;
	}

	public void setUserTags(Set<Tag> userTags) {
		this.userTags = userTags;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	public Set<Authority> getAuthorities() {
		   return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", contacts=" + contacts + ", userTags=" + userTags + ", accounts="
				+ accounts + "]";
	}
}
