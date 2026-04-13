package com.google.android.gms.internal.clearcut;

public final class zzbx {
    private static final zzbu<?> zzgr = new zzbv();
    private static final zzbu<?> zzgs = zzao();

    private static zzbu<?> zzao() {
        try {
            return (zzbu) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static zzbu<?> zzap() {
        return zzgr;
    }

    static zzbu<?> zzaq() {
        zzbu<?> zzbu = zzgs;
        if (zzbu != null) {
            return zzbu;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
