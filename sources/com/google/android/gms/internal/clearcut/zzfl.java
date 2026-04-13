package com.google.android.gms.internal.clearcut;

public enum zzfl {
    DOUBLE(zzfq.DOUBLE, 1),
    FLOAT(zzfq.FLOAT, 5),
    INT64(r5, 0),
    UINT64(r5, 0),
    INT32(r11, 0),
    FIXED64(r5, 1),
    FIXED32(r11, 5),
    BOOL(zzfq.BOOLEAN, 0),
    STRING(zzfq.STRING, 2),
    GROUP(r13, 3),
    MESSAGE(r13, 2),
    BYTES(zzfq.BYTE_STRING, 2),
    UINT32(r11, 0),
    ENUM(zzfq.ENUM, 0),
    SFIXED32(r11, 5),
    SFIXED64(r5, 1),
    SINT32(r11, 0),
    SINT64(r5, 0);
    
    private final zzfq zzqu;
    private final int zzqv;

    private zzfl(zzfq zzfq, int i) {
        this.zzqu = zzfq;
        this.zzqv = i;
    }

    public final zzfq zzek() {
        return this.zzqu;
    }

    public final int zzel() {
        return this.zzqv;
    }
}
