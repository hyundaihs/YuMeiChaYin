package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/23/023.
 */

public class RealNameInfo {
    private int status; // 状态（1：审核中，2：已通过，3：拒绝）
    private String true_name; //  姓名
    private String id_numbers; //  身份证号码
    private String sex; //  性别

    public RealNameInfo() {
        super();
    }

    public RealNameInfo(int status, String true_name, String id_numbers, String sex) {
        this.status = status;
        this.true_name = true_name;
        this.id_numbers = id_numbers;
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public String getId_numbers() {
        return id_numbers;
    }

    public void setId_numbers(String id_numbers) {
        this.id_numbers = id_numbers;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "RealNameInfo{" +
                "status=" + status +
                ", true_name='" + true_name + '\'' +
                ", id_numbers='" + id_numbers + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
