package rs.projekatOSA2019_maven.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="contacts")
public class Contact implements Serializable {
    
	public enum Format {
        PLAIN,
        HTML
    }
	public Contact() {}
	
	public Contact(Integer id, String firstname, String lastname, String displayname, String email, String note,
			Format format, Set<Photo> photos, User user) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.displayname = displayname;
		this.email = email;
		this.note = note;
		this.format = format;
		this.photos = photos;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="contact_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="contact_firstname", unique=false,nullable=true,length=100)
	private String firstname;
	
	@Column(name="contact_lastname", unique=false,nullable=true,length=100)
	private String lastname;
	
	@Column(name="contact_displayname", unique=false,nullable=false,length=100)
	private String displayname;
	
	@Column(name="contact_email", unique=true,nullable=false,length=100)
	private String email;
	
	@Column(name="contact_note", unique=false,nullable=true)
	@Lob
	private String note;

	@Column(name="contact_format", unique=false, nullable=true)
	private Format format;
	
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="contact" )
	private Set<Photo> photos = new HashSet<Photo>();
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="user_id", nullable=false)
	private User user;

	public void add(Photo photo) {
		if (photo.getContact() != null) {
			photo.getContact().getPhotos().remove(photo);
		}
		photo.setContact(this);
		getPhotos().add(photo);
	}
	
	public void remove(Photo photo) {
		photo.setContact(null);
		getPhotos().remove(photo);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", displayname="
				+ displayname + ", email=" + email + ", note=" + note + ", format=" + format + ", photos=" + photos
				+ ", user=" + user + "]";
	}
	
	
	
}
