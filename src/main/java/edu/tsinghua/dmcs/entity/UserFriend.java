package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-9-20.
 */
public class UserFriend {
    private Long id;

    private String userself;

    private String userfriend;

    public Long getId() {return id;}

    public void setId(Long id) { this.id=id; }

    public String getUserSelf() { return userself; }

    public void setUserSelf(String userSelf) {this.userself=userSelf;}

    public String getUserFriend() {return userfriend;}

    public void setUserFriend(String userFriend) {this.userfriend=userFriend;}
}
