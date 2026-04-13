package com.leedarson.secret;

import com.meituan.robust.ChangeQuickRedirect;

public class KeyExchange {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String k1;
    private String k2;

    public String getK1() {
        return this.k1;
    }

    public void setK1(String k12) {
        this.k1 = k12;
    }

    public String getK2() {
        return this.k2;
    }

    public void setK2(String k22) {
        this.k2 = k22;
    }
}
