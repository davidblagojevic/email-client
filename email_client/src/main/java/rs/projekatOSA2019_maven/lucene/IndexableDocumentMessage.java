package rs.projekatOSA2019_maven.lucene;

import rs.projekatOSA2019_maven.entity.Message;

public class IndexableDocumentMessage {
	
	private String id;
	private String messageTo;
	private String messageBcc;
	private String messageFrom;
	private String subject;
	private String content;
	private String folder;
	private Boolean unread;
	
	public IndexableDocumentMessage(Message message) {
		this.id = message.getId().toString();
		this.messageTo = message.getTo();
		this.messageBcc = message.getBcc();
		this.messageFrom = message.getFrom();  
		this.subject = message.getSubject();
		this.content = message.getContent();
		this.folder = message.getFolder().getName();
		this.unread =message.isUnread();
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessageTo() {
		return messageTo;
	}
	public void setMessageTo(String messageTo) {
		this.messageTo = messageTo;
	}
	public String getMessageBcc() {
		return messageBcc;
	}
	public void setMessageBcc(String messageBcc) {
		this.messageBcc = messageBcc;
	}
	public String getMessageFrom() {
		return messageFrom;
	}
	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
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
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public Boolean getUnread() {
		return unread;
	}
	public void setUnread(Boolean unread) {
		this.unread = unread;
	}
	
}
