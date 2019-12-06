package rs.projekatOSA2019_maven.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rs.projekatOSA2019_maven.entity.Tag;


public class TagDTO implements Serializable {
    private int id;
    private String name;

    public TagDTO(){}

    public TagDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public TagDTO(Tag tag) {

    	this.setId(tag.getId());
    	this.setName(tag.getName());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
