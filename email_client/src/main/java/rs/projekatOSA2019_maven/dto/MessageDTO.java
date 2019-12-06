package rs.projekatOSA2019_maven.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import rs.projekatOSA2019_maven.entity.Attachment;
import rs.projekatOSA2019_maven.entity.Message;
import rs.projekatOSA2019_maven.entity.Tag;


public class MessageDTO implements Serializable {
    private int id;
    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private Date dateTime;
    private String subject;
    private String content;
    private List<AttachmentDTO> attachments;
    private List<TagDTO> tags;
    private boolean procitano;

    public MessageDTO() {
    }

    public MessageDTO(int id, String from, List<String> to, List<String> cc, List<String> bcc,
                   Date dateTime, String subject, String content,
                   List<AttachmentDTO> attachments, List<TagDTO> tags, boolean procitano) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.dateTime = dateTime;
        this.subject = subject;
        this.content = content;
        this.attachments = attachments;
        this.tags = tags;
        this.procitano = procitano;
    }
    
    public MessageDTO(Message message) {
    	List<AttachmentDTO> attachmentDTOs = new ArrayList<>();
    	for (Attachment itAttachment : message.getAttachments()) {
    		attachmentDTOs.add(new AttachmentDTO(itAttachment));
		}
    	List<TagDTO> tagDTOs = new ArrayList<>();
    	if (message.getTags() != null) {
        	for (Tag itTag : message.getTags()) {
    			tagDTOs.add(new TagDTO(itTag));
    		}
		}
    	if (message.getId() != null) {
    		this.setId(message.getId());
		}
    	
    	this.setFrom(message.getFrom());
    	this.setTo(new ArrayList<>());
    	if (message.getTo() != null && !message.getTo().equals("")) {
			StringTokenizer token =  new StringTokenizer(message.getTo(), ";");
			while (token.hasMoreTokens()) {
				this.getTo().add(token.nextToken());
			}
		}
    	this.setCc(new ArrayList<>());
    	if (message.getCc() != null && !message.getCc().equals("")) {
			StringTokenizer token =  new StringTokenizer(message.getCc(), ";");
			while (token.hasMoreTokens()) {
				this.getCc().add(token.nextToken());
			}
		}
    	this.setBcc(new ArrayList<>());
    	if (message.getBcc() != null && !message.getBcc().equals("")) {
			StringTokenizer token =  new StringTokenizer(message.getBcc(), ";");
			while (token.hasMoreTokens()) {
				this.getBcc().add(token.nextToken());
			}
		}
    	this.setDateTime(message.getDateTime());
    	this.setSubject(message.getSubject());
    	this.setContent(message.getContent());
    	this.setAttachments(attachmentDTOs);
    	this.setTags(tagDTOs);
    	this.setProcitano(message.isUnread());
    	
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
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

    public List<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public boolean isProcitano() {
		return procitano;
	}

	public void setProcitano(boolean procitano) {
		this.procitano = procitano;
	}

	@Override
	public String toString() {
		return "MessageDTO [id=" + id + ", from=" + from + ", to=" + to + ", cc=" + cc + ", bcc=" + bcc + ", dateTime="
				+ dateTime + ", subject=" + subject  
				+ ", procitano=" + procitano + "]";
	}


}
