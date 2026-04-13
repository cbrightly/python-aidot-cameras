package org.spongycastle.crypto.tls;

public class SupplementalDataEntry {
    protected int a;
    protected byte[] b;

    public SupplementalDataEntry(int dataType, byte[] data) {
        this.a = dataType;
        this.b = data;
    }

    public int b() {
        return this.a;
    }

    public byte[] a() {
        return this.b;
    }
}
