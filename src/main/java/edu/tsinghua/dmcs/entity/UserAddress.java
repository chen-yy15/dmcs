package edu.tsinghua.dmcs.entity;

/**
 * Created by caizj on 18-9-20.
 */
public class UserAddress {
    private Long addressid;

    private String userid;

    private String name;//收件人

    private String title;//称呼

    private String country;

    private String city;

    private String area;

    private String place;

    private String mobilephone;

    private String fixedphone;

    private String email;

    public Long getAddressId() {return addressid;}

    public void setAddressId(Long addressId) {this.addressid=addressId;}

    public String getUserId() {return userid;}

    public void setUserId(String userid) {this.userid = userid;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getCountry() {return country;}

    public void setCountry(String country) {this.country=country;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city=city;}

    public String getArea() {return area;}

    public void setArea(String area) {this.area = area;}

    public String getPlace() {return place;}

    public void setPlace(String place) {this.place=place;}

    public String getMobilePhone() {return mobilephone;}

    public void setMobilePhone(String mobilePhone) {this.mobilephone = mobilePhone;}

    public String getFixedPhone() {return fixedphone;}

    public void setFixedPhone(String fixedPhone) {this.fixedphone = fixedPhone;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}
}
