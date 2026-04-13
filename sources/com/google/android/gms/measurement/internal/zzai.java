package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import java.util.EnumMap;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzai {
    public static final zzai zza = new zzai((Boolean) null, (Boolean) null);
    private final EnumMap zzb;

    public zzai(Boolean bool, Boolean bool2) {
        EnumMap enumMap = new EnumMap(zzah.class);
        this.zzb = enumMap;
        enumMap.put(zzah.AD_STORAGE, bool);
        enumMap.put(zzah.ANALYTICS_STORAGE, bool2);
    }

    public static zzai zza(Bundle bundle) {
        if (bundle == null) {
            return zza;
        }
        EnumMap enumMap = new EnumMap(zzah.class);
        for (zzah zzah : zzah.values()) {
            enumMap.put(zzah, zzn(bundle.getString(zzah.zzd)));
        }
        return new zzai(enumMap);
    }

    public static zzai zzb(String str) {
        EnumMap enumMap = new EnumMap(zzah.class);
        if (str != null) {
            int i = 0;
            while (true) {
                zzah[] zzahArr = zzah.zzc;
                int length = zzahArr.length;
                if (i < 2) {
                    zzah zzah = zzahArr[i];
                    int i2 = i + 2;
                    if (i2 < str.length()) {
                        Boolean bool = null;
                        switch (str.charAt(i2)) {
                            case '0':
                                bool = Boolean.FALSE;
                                break;
                            case '1':
                                bool = Boolean.TRUE;
                                break;
                        }
                        enumMap.put(zzah, bool);
                    }
                    i++;
                }
            }
        }
        return new zzai(enumMap);
    }

    public static String zzg(Bundle bundle) {
        String string;
        for (zzah zzah : zzah.values()) {
            if (bundle.containsKey(zzah.zzd) && (string = bundle.getString(zzah.zzd)) != null && zzn(string) == null) {
                return string;
            }
        }
        return null;
    }

    public static boolean zzj(int i, int i2) {
        return i <= i2;
    }

    static final int zzm(Boolean bool) {
        if (bool == null) {
            return 0;
        }
        return bool.booleanValue() ? 1 : 2;
    }

    private static Boolean zzn(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("granted")) {
            return Boolean.TRUE;
        }
        if (str.equals("denied")) {
            return Boolean.FALSE;
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzai)) {
            return false;
        }
        zzai zzai = (zzai) obj;
        for (zzah zzah : zzah.values()) {
            if (zzm((Boolean) this.zzb.get(zzah)) != zzm((Boolean) zzai.zzb.get(zzah))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 17;
        for (Boolean zzm : this.zzb.values()) {
            i = (i * 31) + zzm(zzm);
        }
        return i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("settings: ");
        zzah[] values = zzah.values();
        int length = values.length;
        for (int i = 0; i < length; i++) {
            zzah zzah = values[i];
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(zzah.name());
            sb.append("=");
            Boolean bool = (Boolean) this.zzb.get(zzah);
            if (bool == null) {
                sb.append("uninitialized");
            } else {
                sb.append(true != bool.booleanValue() ? "denied" : "granted");
            }
        }
        return sb.toString();
    }

    public final zzai zzc(zzai zzai) {
        EnumMap enumMap = new EnumMap(zzah.class);
        for (zzah zzah : zzah.values()) {
            Boolean bool = (Boolean) this.zzb.get(zzah);
            Boolean bool2 = (Boolean) zzai.zzb.get(zzah);
            if (bool == null) {
                bool = bool2;
            } else if (bool2 != null) {
                bool = Boolean.valueOf(bool.booleanValue() && bool2.booleanValue());
            }
            enumMap.put(zzah, bool);
        }
        return new zzai(enumMap);
    }

    public final zzai zzd(zzai zzai) {
        EnumMap enumMap = new EnumMap(zzah.class);
        for (zzah zzah : zzah.values()) {
            Boolean bool = (Boolean) this.zzb.get(zzah);
            if (bool == null) {
                bool = (Boolean) zzai.zzb.get(zzah);
            }
            enumMap.put(zzah, bool);
        }
        return new zzai(enumMap);
    }

    public final Boolean zze() {
        return (Boolean) this.zzb.get(zzah.AD_STORAGE);
    }

    public final Boolean zzf() {
        return (Boolean) this.zzb.get(zzah.ANALYTICS_STORAGE);
    }

    public final String zzh() {
        char c;
        StringBuilder sb = new StringBuilder("G1");
        zzah[] zzahArr = zzah.zzc;
        int length = zzahArr.length;
        for (int i = 0; i < 2; i++) {
            Boolean bool = (Boolean) this.zzb.get(zzahArr[i]);
            if (bool == null) {
                c = '-';
            } else {
                c = bool.booleanValue() ? '1' : '0';
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public final boolean zzi(zzah zzah) {
        Boolean bool = (Boolean) this.zzb.get(zzah);
        return bool == null || bool.booleanValue();
    }

    public final boolean zzk(zzai zzai) {
        return zzl(zzai, (zzah[]) this.zzb.keySet().toArray(new zzah[0]));
    }

    public final boolean zzl(zzai zzai, zzah... zzahArr) {
        for (zzah zzah : zzahArr) {
            Boolean bool = (Boolean) this.zzb.get(zzah);
            Boolean bool2 = (Boolean) zzai.zzb.get(zzah);
            Boolean bool3 = Boolean.FALSE;
            if (bool == bool3 && bool2 != bool3) {
                return true;
            }
        }
        return false;
    }

    public zzai(EnumMap enumMap) {
        EnumMap enumMap2 = new EnumMap(zzah.class);
        this.zzb = enumMap2;
        enumMap2.putAll(enumMap);
    }
}
