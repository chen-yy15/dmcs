package edu.tsinghua.dmcs.entity;

import java.util.Date;

public class User {

    private String userId;

    private String currentAuthority;

    private String avatar;

    private String userName;

    private String realName;

    private String password;

    private String userSex;

    private String userIdNumber;

    private String userEmail;

    private String emailCheckedFlag;

    private String userTelephone;

    private String userTelephone_1;

    private String userWorkPlace;

    private String userWeixin;

    private String userQq;

    private Date regtime;

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getCurrentAuthority() { return currentAuthority; }

    public void setCurrentAuthority(String currentAuthority) {this.currentAuthority = currentAuthority;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getUsersex() { return userSex;}

    public void setUsersex(String userSex) {this.userSex = userSex; }

    public String getUserIdNumber() {return userIdNumber;}

    public void setUserIdNumber(String userIdNumber) {this.userIdNumber=userIdNumber;}

    public String getRealname() {return realName;}

    public void setRealname(String realName) {this.realName = realName;}

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {this.avatar = avatar;}

    public String getUserEmail() { return userEmail;}

    public void setUserEmail(String userEmail){this.userEmail = userEmail;}

    public String getEmailCheckedFlag() {return emailCheckedFlag;}

    public void setEmailCheckedFlag(String emailCheckedFlag){ this.emailCheckedFlag = emailCheckedFlag;}

    public String getUserTelephone() {return userTelephone;}

    public void setUserTelephone(String userTelephone) {this.userTelephone = userTelephone;}

    public String getUserTelephone_1() {return userTelephone_1;}

    public void setUserTelephone_1(String userTelephone_1) {this.userTelephone_1 = userTelephone_1;}

    public String getUserworkPlace() {return userWorkPlace;}

    public void setUserworkPlace(String userWorkPlace) {this.userWorkPlace = userWorkPlace;}

    public String getUserWeixin () {return userWeixin;}

    public void setUserWeixin(String weixin) {this.userWeixin = this.userWeixin;}

    public String getUserQq () {return userQq;}

    public void setUserQq(String userQq){this.userQq = userQq;}

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }
}