package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-10-9.
 */
public class EnumName {
    private  Integer nameid ;

    private Integer bandid;

    private  String  moduleType ; //

    private String namedetail ;

    public Integer getNameid() {return nameid;}

    public void setNameid(Integer nameid) {this.nameid = nameid;}

    public Integer getBandid(){ return bandid; }

    public void setBandid(Integer bandid) {this.bandid = bandid;}

    public String getModuleType(){return moduleType;}

    public void setModuleType(String moduleType) {this.moduleType = moduleType;}

    public String getNamedetail() {return namedetail;}

    public void setNamedetail(String namedetail){this.namedetail = namedetail;}
}
