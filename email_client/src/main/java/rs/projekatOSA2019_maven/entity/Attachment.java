package rs.projekatOSA2019_maven.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Base64;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="attachments")
public class Attachment implements Serializable {
	
	public Attachment() {}
	
	public Attachment(Integer id, String data, String mimeType, String name, Message message) {
		super();
		this.id = id;
		this.data = data;
		this.mimeType = mimeType;
		this.name = name;
		this.message = message;
	}



	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="attachment_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name = "attachment_data", nullable = true, unique=false, columnDefinition = "LONGTEXT")
	private String data;
	
	@Column(name="attachment_mime_type", unique=false, nullable=false,length=20)
	private String mimeType;
	
	@Column(name="attachment_name", unique=false,nullable=false, length=100)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="message_id", referencedColumnName="message_id", nullable=false)
	private Message message;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", data=" + data + ", mimeType=" + mimeType + ", name=" + name + ", message="
				+ message + "]";
	}
	
	
	

}
