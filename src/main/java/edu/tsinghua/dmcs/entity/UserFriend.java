package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-9-20.
 */
public class UserFriend {
    private Long id;

    private String userSelf;

    private String userFriend;

    public Long getId() {return id;}

    public void setId(Long id) { this.id=id; }

    public String getUserSelf() { return userSelf; }

    public void setUserSelf(String userSelf) {this.userSelf=userSelf;}

    public String getUserFriend() {return userFriend;}

    public void setUserFriend(String userFriend) {this.userFriend=userFriend;}
}
