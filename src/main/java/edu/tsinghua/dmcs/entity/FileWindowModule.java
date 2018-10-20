package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-10-9.
 */
public class FileWindowModule {
    private Long createid ;

    private Long  fileid ;

    private Integer moduleid ;

    private Integer windowid ;

    private Long imagefileid ;

    private String viewed;

    private String fileimage;

    private String filename;

    private String imagename;

    private String filesrc;

    private String imagesrc;

    public Long getCreateid() {return createid;}

    public void setCreateid(Long createid) {this.createid = createid;}

    public Long getFileid() {return fileid;}

    public void setFileid(Long fileid) {this.fileid = fileid;}

    public Integer getModuleid() {return moduleid;}

    public void setModuleid(Integer moduleid) {this.moduleid = moduleid;}

    public Integer getWindowid() {return windowid;}

    public void setWindowid(Integer windowid) {this.windowid = windowid;}

    public Long getImage_fileid() {return imagefileid;}

    public void setImage_fileid(Long imagefileid) {this.imagefileid = imagefileid;}

    public String getViewed(){return viewed;}

    public void setViewed(String viewed) {this.viewed = viewed;}

    public String getFile_image(){return fileimage;}

    public void setFile_image(String fileimage) {this.fileimage = fileimage;}

    public String getFilename(){return  filename;}

    public void setFilename(String filename) {this.filename = filename;}

    public String getImagename(){return  imagename;}

    public void setImagename(String imagename){this.imagename = imagename;}

    public String getFilesrc(){return filesrc;}

    public void setFilesrc(String filesrc){this.filesrc = filesrc;}

    public String getImagesrc(){return imagesrc;}

    public void setImagesrc(String imagesrc){this.imagesrc = imagesrc;}

}
