package rs.projekatOSA2019_maven.dto;

import java.io.Serializable;
import java.util.Base64;

import rs.projekatOSA2019_maven.entity.Attachment;


public class AttachmentDTO implements Serializable {
    private int id;
    private String data;
    private String type;
    private String name;

    public AttachmentDTO() {
    }

    public AttachmentDTO(int id, String data, String type, String name, MessageDTO message) {
        this.id = id;
        this.data = data;
        this.type = type;
        this.name = name;
    }
    
    public AttachmentDTO(Attachment attachment) {

    	if (attachment.getId() != null) {
    		this.id = attachment.getId();
		}
    	
    	this.data = attachment.getData();
    	this.type = attachment.getMimeType();
    	this.name = attachment.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
