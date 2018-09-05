package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-9-5.
 */
public class TechDocument {
    private Long id;

    private String title;

    private String image_address;

    private String description;

    private String document_address;

    private int identityNumber;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title ;}

    public String getImage_address() { return image_address;}

    public void setImage_address(String image_address) {
        this.image_address = image_address;
    }

    public String getDescription() {
        return description;
    }

    public  void setDescription(String description) { this.description=description; }

    public String getDocument_address() { return document_address; }

    public void setDocument_addressd(String document_address) {
        this.document_address = document_address;
    }

    public int getIdentityNumber() {return identityNumber;}

    public void setIdentityNumber(int identityNumber) { this.identityNumber = identityNumber;}
}
