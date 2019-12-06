package rs.projekatOSA2019_maven.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="photos")
public class Photo implements Serializable {
	
	public Photo() {}
	
	public Photo(Integer id, String path, Contact contact) {
		super();
		this.id = id;
		this.path = path;
		this.contact = contact;
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="photo_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="photo_path", unique=false, nullable=false, length=500 )
	private String path;
	
	@ManyToOne
	@JoinColumn(name="contact_id", referencedColumnName="contact_id", nullable=false)
	private Contact contact;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", path=" + path + ", contact=" + contact + "]";
	}
	
	
	

	
	
}
