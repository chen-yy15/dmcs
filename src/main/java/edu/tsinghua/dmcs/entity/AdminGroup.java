package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-9-6.
 */
public class AdminGroup {
    private int id;

    private String userid;

    private int authorityNumber;

    public int getId() {return id;}

    public void setId(int id) {this.id = id; }

    public String getUserid() { return userid; }

    public void setUserid(String userid) { this.userid=userid; }

    public int getAuthorityNumber() { return authorityNumber; }

    public void setAuthorityNumber(int authorityNumber) { this.authorityNumber = authorityNumber; }
}
