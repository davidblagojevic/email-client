package rs.projekatOSA2019_maven.lucene;

import rs.projekatOSA2019_maven.entity.Contact;

public class IndexableDocumentContact {
	private String firstname;
	private String lastname;
	private String note;
	private String email;
	
	public IndexableDocumentContact() {}
	
	public IndexableDocumentContact(Contact contact) {
		this.firstname = contact.getFirstname();
		this.lastname = contact.getLastname();
		this.note = contact.getNote();
		this.email = contact.getEmail();
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
