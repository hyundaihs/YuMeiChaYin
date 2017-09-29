package com.sp.shangpin.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/29/029.
 */

public class WXUserInfo implements Parcelable{
    private String openid; // "OPENID",
    private String nickname; // "NICKNAME",
    private int sex; // 1,
    private String province; // "PROVINCE",
    private String city; // "CITY",
    private String country; // "COUNTRY",
    private String headimgurl; //  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
    private List<String> privilege; // ["PRIVILEGE1","PRIVILEGE2"],
    private String unionid; //  "o6_bmasdasdsad6_2sgVt7hMZOPfL"
    private int errcode; //
    private String errmsg; //

    public WXUserInfo() {
        super();
    }

    public WXUserInfo(String openid, String nickname, int sex, String province, String city, String country, String headimgurl, List<String> privilege, String unionid, int errcode, String errmsg) {
        this.openid = openid;
        this.nickname = nickname;
        this.sex = sex;
        this.province = province;
        this.city = city;
        this.country = country;
        this.headimgurl = headimgurl;
        this.privilege = privilege;
        this.unionid = unionid;
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    protected WXUserInfo(Parcel in) {
        openid = in.readString();
        nickname = in.readString();
        sex = in.readInt();
        province = in.readString();
        city = in.readString();
        country = in.readString();
        headimgurl = in.readString();
        privilege = in.createStringArrayList();
        unionid = in.readString();
        errcode = in.readInt();
        errmsg = in.readString();
    }

    public static final Creator<WXUserInfo> CREATOR = new Creator<WXUserInfo>() {
        @Override
        public WXUserInfo createFromParcel(Parcel in) {
            return new WXUserInfo(in);
        }

        @Override
        public WXUserInfo[] newArray(int size) {
            return new WXUserInfo[size];
        }
    };

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public List<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "WXUserInfo{" +
                "openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", privilege=" + privilege +
                ", unionid='" + unionid + '\'' +
                ", errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(openid);
        parcel.writeString(nickname);
        parcel.writeInt(sex);
        parcel.writeString(province);
        parcel.writeString(city);
        parcel.writeString(country);
        parcel.writeString(headimgurl);
        parcel.writeStringList(privilege);
        parcel.writeString(unionid);
        parcel.writeInt(errcode);
        parcel.writeString(errmsg);
    }
}
