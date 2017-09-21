package com.sp.shangpin.entity;

import java.util.List;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/9/21.
 */

public class YHQS_Sup extends InterResult {
    private List<YHQ> retRes;

    public YHQS_Sup() {
        super();
    }

    public YHQS_Sup(List<YHQ> retRes) {
        this.retRes = retRes;
    }

    public List<YHQ> getRetRes() {
        return retRes;
    }

    public void setRetRes(List<YHQ> retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "YHQS_Sup{" +
                "retRes=" + retRes +
                '}';
    }
}
