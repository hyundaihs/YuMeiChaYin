package com.sp.shangpin.entity;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/8.
 */

public class InterResult {
    protected int retInt;
    protected String retErr;

    public InterResult() {
        super();
    }

    public InterResult(int retInt, String retErr) {
        this.retInt = retInt;
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
                ", retErr=" + retErr +
                '}';
    }
}
