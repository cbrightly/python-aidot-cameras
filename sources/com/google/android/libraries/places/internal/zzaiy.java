package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public enum zzaiy {
    DOUBLE(zzaiz.DOUBLE, 1),
    FLOAT(zzaiz.FLOAT, 5),
    INT64(r5, 0),
    UINT64(r5, 0),
    INT32(r11, 0),
    FIXED64(r5, 1),
    FIXED32(r11, 5),
    BOOL(zzaiz.BOOLEAN, 0),
    STRING(zzaiz.STRING, 2),
    GROUP(r13, 3),
    MESSAGE(r13, 2),
    BYTES(zzaiz.BYTE_STRING, 2),
    UINT32(r11, 0),
    ENUM(zzaiz.ENUM, 0),
    SFIXED32(r11, 5),
    SFIXED64(r5, 1),
    SINT32(r11, 0),
    SINT64(r5, 0);
    
    private final zzaiz zzt;

    private zzaiy(zzaiz zzaiz, int i) {
        this.zzt = zzaiz;
    }

    public final zzaiz zza() {
        return this.zzt;
    }
}
