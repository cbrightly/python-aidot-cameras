package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public enum zzml {
    DOUBLE(zzmo.DOUBLE, 1),
    FLOAT(zzmo.FLOAT, 5),
    INT64(r5, 0),
    UINT64(r5, 0),
    INT32(r11, 0),
    FIXED64(r5, 1),
    FIXED32(r11, 5),
    BOOL(zzmo.BOOLEAN, 0),
    STRING(zzmo.STRING, 2),
    GROUP(r13, 3),
    MESSAGE(r13, 2),
    BYTES(zzmo.BYTE_STRING, 2),
    UINT32(r11, 0),
    ENUM(zzmo.ENUM, 0),
    SFIXED32(r11, 5),
    SFIXED64(r5, 1),
    SINT32(r11, 0),
    SINT64(r5, 0);
    
    private final zzmo zzs;
    private final int zzt;

    private zzml(zzmo zzmo, int i) {
        this.zzs = zzmo;
        this.zzt = i;
    }

    public final zzmo zza() {
        return this.zzs;
    }

    public final int zzb() {
        return this.zzt;
    }
}
