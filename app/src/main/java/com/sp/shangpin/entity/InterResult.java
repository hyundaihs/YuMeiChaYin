package com.sp.shangpin.entity;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/8.
 */

public class InterResult {
    protected int retInt;
    protected String retErr;
    protected int retCounts;

    public InterResult() {
        super();
    }

    public InterResult(int retInt,String retErr) {
        this.retInt = retInt;
        this.retErr = retErr;
    }

    public InterResult(int retInt, int retCounts, String retErr) {
        this.retInt = retInt;
        this.retCounts = retCounts;
        this.retErr = retErr;
    }

    public boolean isSuccessed() {
        return retInt == 1;
    }

    public int getRetInt() {
        return retInt;
    }

    public void setRetInt(int retInt) {
        this.retInt = retInt;
    }

    public int getRetCounts() {
        return retCounts;
    }

    public void setRetCounts(int retCounts) {
        this.retCounts = retCounts;
    }

    public String getRetErr() {
        return retErr;
    }

    public void setRetErr(String retErr) {
        this.retErr = retErr;
    }

    @Override
    public String toString() {
        return "InterResult{" +
                "retInt=" + retInt +
                ", retCounts=" + retCounts +
                ", retErr='" + retErr + '\'' +
                '}';
    }
}
