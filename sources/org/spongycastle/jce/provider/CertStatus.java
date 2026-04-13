package org.spongycastle.jce.provider;

import java.util.Date;

public class CertStatus {
    int a = 11;
    Date b = null;

    CertStatus() {
    }

    public Date b() {
        return this.b;
    }

    public void d(Date revocationDate) {
        this.b = revocationDate;
    }

    public int a() {
        return this.a;
    }

    public void c(int certStatus) {
        this.a = certStatus;
    }
}
