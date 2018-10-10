package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-10-9.
 */
public class FileWindowModule {
    private Long createid ;

    private String  fileid ;

    private Integer moduleid ;

    private Integer windowid ;

    private String image_fileid ;

    private String viewed;

    public Long getCreateid() {return createid;}

    public void setCreateid(Long createid) {this.createid = createid;}

    public String getFileid() {return fileid;}

    public void setFileid(String fileid) {this.fileid = fileid;}

    public Integer getModuleid() {return moduleid;}

    public void setModuleid(Integer moduleid) {this.moduleid = moduleid;}

    public Integer getWindowid() {return windowid;}

    public void setWindowid(Integer windowid) {this.windowid = windowid;}

    public String getImage_fileid() {return image_fileid;}

    public void setImage_fileid(String image_fileid) {this.image_fileid = image_fileid;}

    public String getViewed(){return viewed;}

    public void setViewed(String viewed) {this.viewed = viewed;}

}
