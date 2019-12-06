package rs.projekatOSA2019_maven.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tags")
public class Tag implements Serializable {
	
	public Tag () {}
	
	public Tag(Integer id, String name, User user, Set<Message> messages) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
		this.messages = messages;
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="tag_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="tag_name", unique=true, nullable=false, length=100)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="user_id", nullable=false)
	private User user;
	
	@ManyToMany(mappedBy = "tags")
	private Set<Message> messages = new HashSet<Message>();

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

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", user=" + user + ", messages=" + messages + "]";
	}
	
	
}
