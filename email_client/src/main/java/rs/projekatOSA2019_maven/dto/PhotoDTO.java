package rs.projekatOSA2019_maven.dto;

import java.io.Serializable;

import org.apache.tomcat.util.codec.binary.Base64;

import rs.projekatOSA2019_maven.entity.Photo;


public class PhotoDTO implements Serializable {
	private int id;
	private String path;
	private String data;
	

	public PhotoDTO() {
	}
	
	public PhotoDTO(int id, String path) {
	    this.id = id;
	    this.path = path;
	}
	
	public PhotoDTO(Photo photo) {
		this.setId(photo.getId());
		this.setPath(photo.getPath());
	}
	
	public int getId() {
	    return id;
	}
	
	public void setId(int id) {
	    this.id = id;
	}
	
	public String getPath() {
	    return path;
	}
	
	public void setPath(String path) {
	    this.path = path;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
