package edu.tsinghua.dmcs.entity;

import java.util.Date;

public class Device {
    private Long id;

    private String devimage;

    private String devid;

    private String name;

    private String type;

    private String parameters;

    private String vendor;

    private Date guranteeFrom;

    private Long owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevimage() {
        return devimage;
    }

    public void setDevimage(String devimage) {
        this.devimage = devimage;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getGuranteeFrom() {
        return guranteeFrom;
    }

    public void setGuranteeFrom(Date guranteeFrom) {
        this.guranteeFrom = guranteeFrom;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}