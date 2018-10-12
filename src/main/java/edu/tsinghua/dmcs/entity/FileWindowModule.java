package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-10-9.
 */
public class FileWindowModule {
    private Long createid ;

    private Long  fileid ;

    private Integer moduleid ;

    private Integer windowid ;

    private Long image_fileid ;

    private String viewed;

    private String file_image;

    public Long getCreateid() {return createid;}

    public void setCreateid(Long createid) {this.createid = createid;}

    public Long getFileid() {return fileid;}

    public void setFileid(Long fileid) {this.fileid = fileid;}

    public Integer getModuleid() {return moduleid;}

    public void setModuleid(Integer moduleid) {this.moduleid = moduleid;}

    public Integer getWindowid() {return windowid;}

    public void setWindowid(Integer windowid) {this.windowid = windowid;}

    public Long getImage_fileid() {return image_fileid;}

    public void setImage_fileid(Long image_fileid) {this.image_fileid = image_fileid;}

    public String getViewed(){return viewed;}

    public void setViewed(String viewed) {this.viewed = viewed;}

    public String getFile_image(){return file_image;}

    public void setFile_image(String file_image) {this.file_image = file_image;}

}
