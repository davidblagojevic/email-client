package rs.projekatOSA2019_maven.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

@Entity
@Table(name="messages")
public class Message implements Serializable {
	
	public Message() {}
	
	
	
	public Message(Integer id, String from, String to, String cc, String bcc, Date dateTime, String subject,
			String content, boolean unread, Account account, Folder folder, Set<Attachment> attachments,
			Set<Tag> tags) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.dateTime = dateTime;
		this.subject = subject;
		this.content = content;
		this.unread = unread;
		this.account = account;
		this.folder = folder;
		this.attachments = attachments;
		this.tags = tags;
	}



	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="message_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="message_from", unique=false, nullable=false, length=100)
	private String from;
	
	@Column(name="message_to", unique=false, nullable=false)
	@Lob
	private String to;
	
	@Column(name="message_cc", unique=false, nullable=true)
	@Lob
	private String cc;
	
	@Column(name="message_bcc", unique=false, nullable=true)
	@Lob
	private String bcc;
	
	@Temporal(TIMESTAMP)
	@Column(name="message_date_time", unique=false, nullable=true)
	private Date dateTime;
	 
	@Column(name="message_subject", unique=false, nullable=false, length=250)
	private String subject;
	
	@Column(name="message_content", unique=false, nullable=true)
	@Lob
	private String content;
	
	@Column(name="message_unread", unique=false,nullable=false)
	private boolean unread;
	
	@ManyToOne
	@JoinColumn(name="account_id", referencedColumnName="account_id", nullable=false)
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="folder_id", referencedColumnName="folder_id", nullable=false)
	private Folder folder;
	
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="message" )
	private Set<Attachment> attachments = new HashSet<Attachment>();
	
	@ManyToMany
	@JoinTable(
			name = "message_tags",
			joinColumns = @JoinColumn(name="message_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<Tag>();

	public void add(Attachment attachment) {
		if (attachment.getMessage() != null) {
			attachment.getMessage().getAttachments().remove(attachment);
		}
		attachment.setMessage(this);
		getAttachments().add(attachment);
	}
	
	public void remove(Attachment attachment) {
		attachment.setMessage(null);
		getAttachments().remove(attachment);
	}
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getFrom() {
		return from;
	}



	public void setFrom(String from) {
		this.from = from;
	}



	public String getTo() {
		return to;
	}



	public void setTo(String to) {
		this.to = to;
	}



	public String getCc() {
		return cc;
	}



	public void setCc(String cc) {
		this.cc = cc;
	}



	public String getBcc() {
		return bcc;
	}



	public void setBcc(String bcc) {
		this.bcc = bcc;
	}



	public Date getDateTime() {
		return dateTime;
	}



	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public boolean isUnread() {
		return unread;
	}



	public void setUnread(boolean unread) {
		this.unread = unread;
	}



	public Account getAccount() {
		return account;
	}



	public void setAccount(Account account) {
		this.account = account;
	}



	public Folder getFolder() {
		return folder;
	}



	public void setFolder(Folder folder) {
		this.folder = folder;
	}



	public Set<Attachment> getAttachments() {
		return attachments;
	}



	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}



	public Set<Tag> getTags() {
		return tags;
	}



	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}



	@Override
	public String toString() {
		return "Message [id=" + id + ", from=" + from + ", to=" + to + ", cc=" + cc + ", bcc=" + bcc + ", dateTime="
				+ dateTime + ", subject=" + subject + ", content=" + content + ", unread=" + unread + ", account="
				+ account + ", folder=" + folder + ", attachments=" + attachments + ", tags=" + tags + "]";
	}
	
	
}
