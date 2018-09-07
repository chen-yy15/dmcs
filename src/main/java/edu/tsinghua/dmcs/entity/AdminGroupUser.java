package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-9-6.
 */
public class AdminGroupUser {
    private String userid;
    private String avatar;
    private String userName;
    private String userEmail;
    private String userworkPlace;
    private String userTelephone;
    private int authorityNumber;

    public String getUsername() {return userName;}
    public void setUsername(String userName) {this.userName=userName;}
    public String getUserid() {return userid;}
    public void setUserid(String userid) {this.userid=userid;}
    public String getAvatar() {return avatar;}
    public void setAvatar(String avatar) {this.avatar=avatar;}
    public String getUserEmail() {return userEmail;}
    public void setUserEmail(String userEmail) {this.userEmail=userEmail;}
    public String getUserworkPlace() {return userworkPlace;}
    public void setUserworkPlace(String userworkPlace) {this.userworkPlace=userworkPlace;}
    public String getUserTelephone() {return userTelephone;}
    public void setUserTelephone(String userTelephone) {this.userTelephone=userTelephone;}
    public int getAuthorityNumber() {return authorityNumber;}
    public void setAuthorityNumber(int authorityNumber) {this.authorityNumber = authorityNumber;}
}
