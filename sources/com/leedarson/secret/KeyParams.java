package com.leedarson.secret;

import com.meituan.robust.ChangeQuickRedirect;

public class KeyParams {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long bigNumA;
    private int offset;
    private int random;

    public int getRandom() {
        return this.random;
    }

    public void setRandom(int random2) {
        this.random = random2;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset2) {
        this.offset = offset2;
    }

    public long getBigNumA() {
        return this.bigNumA;
    }

    public void setBigNumA(long bigNumA2) {
        this.bigNumA = bigNumA2;
    }
}
