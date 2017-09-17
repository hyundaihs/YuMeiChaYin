package com.sp.shangpin.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/17/017.
 */

public class AddressInfo implements Parcelable{
    private String title; // 收货人 张三
    private String phone; // 电话 18614221874
    private String pca; // 收货地区 湖北省武汉市江夏区
    private String address; // 详细地址 武大科技园
    private String contents; // 备注

    public AddressInfo() {
        super();
    }

    public AddressInfo(String title, String phone, String pca, String address, String contents) {
        this.title = title;
        this.phone = phone;
        this.pca = pca;
        this.address = address;
        this.contents = contents;
    }

    protected AddressInfo(Parcel in) {
        title = in.readString();
        phone = in.readString();
        pca = in.readString();
        address = in.readString();
        contents = in.readString();
    }

    public static final Creator<AddressInfo> CREATOR = new Creator<AddressInfo>() {
        @Override
        public AddressInfo createFromParcel(Parcel in) {
            return new AddressInfo(in);
        }

        @Override
        public AddressInfo[] newArray(int size) {
            return new AddressInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPca() {
        return pca;
    }

    public void setPca(String pca) {
        this.pca = pca;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "title='" + title + '\'' +
                ", phone='" + phone + '\'' +
                ", pca='" + pca + '\'' +
                ", address='" + address + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(phone);
        parcel.writeString(pca);
        parcel.writeString(address);
        parcel.writeString(contents);
    }
}
