package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-9-19.
 */
public class Avatar {
    private Long avatarid;

    private String userid;

    private String avatar;

    private String selectflag;

    public Long getAvatarId() {return avatarid;}

    public void setAvatarId(Long avatarId){this.avatarid=avatarid;}

    public String getUserId() {return userid;}

    public void setUserId(String userId) {this.userid=userId;}

    public String getAvatar() {return avatar;}

    public void setAvatar(String avatar) {this.avatar=avatar;}

    public String getSelectFlag() {return selectflag;}

    public void setSelectFlag(String selectFlag) {this.selectflag=selectFlag;}

}
