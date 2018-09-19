package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-9-19.
 */
public class Avatar {
    private Long avatarId;

    private String userId;

    private String avatar;

    private String selectFlag;

    public Long getAvatarId() {return avatarId;}

    public void setAvatarId(Long avatarId){this.avatarId=avatarId;}

    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.userId=userId;}

    public String getAvatar() {return avatar;}

    public void setAvatar(String avatar) {this.avatar=avatar;}

    public String getSelectFlag() {return selectFlag;}

    public void setSelectFlag(String selectFlag) {this.selectFlag=selectFlag;}

}
