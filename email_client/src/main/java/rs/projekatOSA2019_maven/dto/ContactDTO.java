package rs.projekatOSA2019_maven.dto;

import java.io.Serializable;
import java.util.List;

import rs.projekatOSA2019_maven.entity.Contact;
import rs.projekatOSA2019_maven.entity.Contact.Format;


public class ContactDTO implements Serializable {

    private int id;
    private String first;
    private String last;
    private String display;
    private String email;
    private Format format;
    private PhotoDTO photo;

    public ContactDTO() {
    }

    public ContactDTO(int id, String first, String last, String display, String email,
                   Format format,PhotoDTO photo) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.display = display;
        this.email = email;
        this.format = format;
        this.photo = photo;
    }
    
    public ContactDTO(Contact contact) {
//    	this(contact.getId(), contact.getFirstname(), contact.getLastname(), contact.getDisplayname(), contact.getEmail(),
//    			contact.getFormat(),null);
    	this.setId(contact.getId());
    	this.setFirst(contact.getFirstname());
    	this.setLast(contact.getLastname());
    	this.setDisplay(contact.getDisplayname());
    	this.setEmail(contact.getEmail());
    	this.setFormat(contact.getFormat());
    	
//		System.out.println("pukni 1");
//    	this.setPhoto(new PhotoDTO());
//		System.out.println("pukni 2");
    	if (contact.getPhotos() != null && contact.getPhotos().iterator().hasNext()) {
//    		System.out.println("pukni 3");
    		this.setPhoto(new PhotoDTO(contact.getPhotos().iterator().next()));
		} else {
			this.setPhoto(new PhotoDTO());
		}
    	
    	
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }
}
