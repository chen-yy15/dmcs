package edu.tsinghua.dmcs.entity;

import java.util.Date;

public class User {

    private String userid;

    private String currentAuthority;

    private String avatar;

    private String username;

    private String realname;

    private String password;

    private String usersex;

    private String useridnumber;

    private String useremail;

    private String emailcheckedflag;

    private String usertelephone;

    private String usertelephone_1;

    private String userworkplace;

    private String userweixin;

    private String userqq;

    private Date regtime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentAuthority() { return currentAuthority; }

    public void setCurrentAuthority(String currentAuthority) {this.currentAuthority = currentAuthority;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getUsersex() { return usersex;}

    public void setUsersex(String userSex) {this.usersex = userSex; }

    public String getUserIdNumber() {return useridnumber;}

    public void setUserIdNumber(String useridnumber) {this.useridnumber=useridnumber;}

    public String getRealname() {return realname;}

    public void setRealname(String realname) {this.realname = realname;}

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {this.avatar = avatar;}

    public String getUserEmail() { return useremail;}

    public void setUserEmail(String useremail){this.useremail = useremail;}

    public String getEmailCheckedFlag() {return emailcheckedflag;}

    public void setEmailCheckedFlag(String emailcheckedflag){ this.emailcheckedflag = emailcheckedflag;}

    public String getUserTelephone() {return usertelephone;}

    public void setUserTelephone(String usertelephone) {this.usertelephone = usertelephone;}

    public String getUserTelephone_1() {return usertelephone_1;}

    public void setUserTelephone_1(String usertelephone_1) {this.usertelephone_1 = usertelephone_1;}

    public String getUserworkPlace() {return userworkplace;}

    public void setUserworkPlace(String userworkplace) {this.userworkplace = userworkplace;}

    public String getUserWeixin () {return userweixin;}

    public void setUserWeixin(String weixin) {this.userweixin = weixin;}

    public String getUserQq () {return userqq;}

    public void setUserQq(String userqq){this.userqq = userqq;}

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }
}