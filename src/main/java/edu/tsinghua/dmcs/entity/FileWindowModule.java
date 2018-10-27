package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-10-9.
 */
public class FileWindowModule {

    private Long createid ;

    private Integer moduleid ;

    private Integer windowid ;

    private Integer orderid;

    private String filename;

    private String filesrc;

    private String imagename;

    private String imagesrc;

    private String fileimagedescrip;

    private String viewed;

    private String insertUser;

    public Long getCreateid() {return createid;}

    public void setCreateid(Long createid) {this.createid = createid;}


    public Integer getModuleid() {return moduleid;}

    public void setModuleid(Integer moduleid) {this.moduleid = moduleid;}

    public Integer getWindowid() {return windowid;}

    public void setWindowid(Integer windowid) {this.windowid = windowid;}

    public Integer getOrderid() {return orderid;}

    public void setOrderid(Integer order) {this.orderid = order;}

    public String getFilename(){return  filename;}

    public void setFilename(String filename) {this.filename = filename;}

    public String getImagename(){return  imagename;}

    public void setImagename(String imagename){this.imagename = imagename;}

    public String getFilesrc(){return filesrc;}

    public void setFilesrc(String filesrc){this.filesrc = filesrc;}

    public String getImagesrc(){return imagesrc;}

    public void setImagesrc(String imagesrc){this.imagesrc = imagesrc;}

    public String getFileimagedescrip() {return fileimagedescrip;}

    public void setFileimagedescrip(String fileimagedescrip) { this.fileimagedescrip = fileimagedescrip; }

    public String getViewed(){return viewed;}

    public void setViewed(String viewed) {this.viewed = viewed;}

    public String getInsertUser(){return  insertUser;}

    public void setInsertUser(String insertUser) {this.insertUser = insertUser;}

}
