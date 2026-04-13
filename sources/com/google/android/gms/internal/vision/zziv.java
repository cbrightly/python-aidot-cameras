package com.google.android.gms.internal.vision;

import java.lang.reflect.Type;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public enum zziv {
    DOUBLE(0, r4, r8),
    FLOAT(1, r4, r9),
    INT64(2, r4, r5),
    UINT64(3, r4, r5),
    INT32(4, r4, r11),
    FIXED64(5, r4, r10),
    FIXED32(6, r4, r11),
    BOOL(7, r4, r12),
    STRING(8, r4, r13),
    MESSAGE(9, r4, r14),
    BYTES(10, r4, r15),
    UINT32(11, r4, r11),
    ENUM(12, r4, r16),
    SFIXED32(13, r4, r11),
    SFIXED64(14, r4, r10),
    SINT32(15, r4, r11),
    SINT64(16, r4, r10),
    GROUP(17, r4, r14),
    DOUBLE_LIST(18, r4, r8),
    FLOAT_LIST(19, r4, r9),
    INT64_LIST(20, r4, r5),
    UINT64_LIST(21, r4, r5),
    INT32_LIST(22, r4, r11),
    FIXED64_LIST(23, r4, r10),
    FIXED32_LIST(24, r4, r11),
    BOOL_LIST(25, r4, r12),
    STRING_LIST(26, r4, r13),
    MESSAGE_LIST(27, r4, r14),
    BYTES_LIST(28, r4, r15),
    UINT32_LIST(29, r4, r11),
    ENUM_LIST(30, r4, r16),
    SFIXED32_LIST(31, r4, r11),
    SFIXED64_LIST(32, r4, r10),
    SINT32_LIST(33, r4, r11),
    SINT64_LIST(34, r4, r10),
    DOUBLE_LIST_PACKED(35, r4, r8),
    FLOAT_LIST_PACKED(36, r4, r9),
    INT64_LIST_PACKED(37, r4, r5),
    UINT64_LIST_PACKED(38, r4, r5),
    INT32_LIST_PACKED(39, r4, r11),
    FIXED64_LIST_PACKED(40, r4, r10),
    FIXED32_LIST_PACKED(41, r4, r11),
    BOOL_LIST_PACKED(42, r4, r12),
    UINT32_LIST_PACKED(43, r4, r11),
    ENUM_LIST_PACKED(44, r4, r16),
    SFIXED32_LIST_PACKED(45, r4, r11),
    SFIXED64_LIST_PACKED(46, r4, r8),
    SINT32_LIST_PACKED(47, r4, zzjm.INT),
    SINT64_LIST_PACKED(48, r4, r8),
    GROUP_LIST(49, r7, r14),
    MAP(50, zzix.MAP, zzjm.VOID);
    
    private static final zziv[] zzbe = null;
    private static final Type[] zzbf = null;
    private final zzjm zzaz;
    private final int zzba;
    private final zzix zzbb;
    private final Class<?> zzbc;
    private final boolean zzbd;

    private zziv(int i, zzix zzix, zzjm zzjm) {
        this.zzba = i;
        this.zzbb = zzix;
        this.zzaz = zzjm;
        switch (zziy.zza[zzix.ordinal()]) {
            case 1:
                this.zzbc = zzjm.zza();
                break;
            case 2:
                this.zzbc = zzjm.zza();
                break;
            default:
                this.zzbc = null;
                break;
        }
        boolean z = false;
        if (zzix == zzix.SCALAR) {
            switch (zziy.zzb[zzjm.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    break;
                default:
                    z = true;
                    break;
            }
        }
        this.zzbd = z;
    }

    public final int zza() {
        return this.zzba;
    }

    static {
        int i;
        zzbf = new Type[0];
        zziv[] values = values();
        zzbe = new zziv[values.length];
        for (zziv zziv : values) {
            zzbe[zziv.zzba] = zziv;
        }
    }
}
