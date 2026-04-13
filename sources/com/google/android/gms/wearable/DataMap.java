package com.google.android.gms.wearable;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.constant.SpInputType;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.wearable.zze;
import com.google.android.gms.internal.wearable.zzf;
import com.google.android.gms.internal.wearable.zzg;
import com.google.android.gms.internal.wearable.zzs;
import com.google.android.gms.internal.wearable.zzt;
import com.meituan.robust.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@VisibleForTesting
public class DataMap {
    public static final String TAG = "DataMap";
    private final HashMap<String, Object> zzq = new HashMap<>();

    public static DataMap fromBundle(@NonNull Bundle bundle) {
        bundle.setClassLoader(Asset.class.getClassLoader());
        DataMap dataMap = new DataMap();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof String) {
                dataMap.putString(str, (String) obj);
            } else if (obj instanceof Integer) {
                dataMap.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                dataMap.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                dataMap.putDouble(str, ((Double) obj).doubleValue());
            } else if (obj instanceof Float) {
                dataMap.putFloat(str, ((Float) obj).floatValue());
            } else if (obj instanceof Boolean) {
                dataMap.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Byte) {
                dataMap.putByte(str, ((Byte) obj).byteValue());
            } else if (obj instanceof byte[]) {
                dataMap.putByteArray(str, (byte[]) obj);
            } else if (obj instanceof String[]) {
                dataMap.putStringArray(str, (String[]) obj);
            } else if (obj instanceof long[]) {
                dataMap.putLongArray(str, (long[]) obj);
            } else if (obj instanceof float[]) {
                dataMap.putFloatArray(str, (float[]) obj);
            } else if (obj instanceof Asset) {
                dataMap.putAsset(str, (Asset) obj);
            } else if (obj instanceof Bundle) {
                dataMap.putDataMap(str, fromBundle((Bundle) obj));
            } else if (obj instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) obj;
                switch (zza(arrayList)) {
                    case 0:
                        dataMap.putStringArrayList(str, arrayList);
                        break;
                    case 1:
                        dataMap.putStringArrayList(str, arrayList);
                        break;
                    case 2:
                        dataMap.putIntegerArrayList(str, arrayList);
                        break;
                    case 3:
                        dataMap.putStringArrayList(str, arrayList);
                        break;
                    case 5:
                        dataMap.putDataMapArrayList(str, arrayListFromBundleArrayList(arrayList));
                        break;
                }
            }
        }
        return dataMap;
    }

    public static DataMap fromByteArray(@NonNull byte[] bArr) {
        try {
            return zze.zza(new zzf(zzg.zza(bArr), new ArrayList()));
        } catch (zzs e) {
            throw new IllegalArgumentException("Unable to convert data", e);
        }
    }

    public static ArrayList<DataMap> arrayListFromBundleArrayList(@NonNull ArrayList<Bundle> arrayList) {
        ArrayList<DataMap> arrayList2 = new ArrayList<>();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Bundle bundle = arrayList.get(i);
            i++;
            arrayList2.add(fromBundle(bundle));
        }
        return arrayList2;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        for (String next : this.zzq.keySet()) {
            Object obj = this.zzq.get(next);
            if (obj instanceof String) {
                bundle.putString(next, (String) obj);
            } else if (obj instanceof Integer) {
                bundle.putInt(next, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                bundle.putLong(next, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                bundle.putDouble(next, ((Double) obj).doubleValue());
            } else if (obj instanceof Float) {
                bundle.putFloat(next, ((Float) obj).floatValue());
            } else if (obj instanceof Boolean) {
                bundle.putBoolean(next, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Byte) {
                bundle.putByte(next, ((Byte) obj).byteValue());
            } else if (obj instanceof byte[]) {
                bundle.putByteArray(next, (byte[]) obj);
            } else if (obj instanceof String[]) {
                bundle.putStringArray(next, (String[]) obj);
            } else if (obj instanceof long[]) {
                bundle.putLongArray(next, (long[]) obj);
            } else if (obj instanceof float[]) {
                bundle.putFloatArray(next, (float[]) obj);
            } else if (obj instanceof Asset) {
                bundle.putParcelable(next, (Asset) obj);
            } else if (obj instanceof DataMap) {
                bundle.putParcelable(next, ((DataMap) obj).toBundle());
            } else if (obj instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) obj;
                switch (zza(arrayList)) {
                    case 0:
                        bundle.putStringArrayList(next, arrayList);
                        break;
                    case 1:
                        bundle.putStringArrayList(next, arrayList);
                        break;
                    case 2:
                        bundle.putIntegerArrayList(next, arrayList);
                        break;
                    case 3:
                        bundle.putStringArrayList(next, arrayList);
                        break;
                    case 4:
                        ArrayList arrayList2 = new ArrayList();
                        int size = arrayList.size();
                        int i = 0;
                        while (i < size) {
                            Object obj2 = arrayList.get(i);
                            i++;
                            arrayList2.add(((DataMap) obj2).toBundle());
                        }
                        bundle.putParcelableArrayList(next, arrayList2);
                        break;
                }
            }
        }
        return bundle;
    }

    public byte[] toByteArray() {
        return zzt.zzb(zze.zza(this).zzfw);
    }

    public int size() {
        return this.zzq.size();
    }

    public boolean isEmpty() {
        return this.zzq.isEmpty();
    }

    public void clear() {
        this.zzq.clear();
    }

    public boolean containsKey(@NonNull String str) {
        return this.zzq.containsKey(str);
    }

    public <T> T get(@NonNull String str) {
        return this.zzq.get(str);
    }

    public Object remove(@NonNull String str) {
        return this.zzq.remove(str);
    }

    public void putAll(@NonNull DataMap dataMap) {
        for (String next : dataMap.keySet()) {
            this.zzq.put(next, dataMap.get(next));
        }
    }

    public Set<String> keySet() {
        return this.zzq.keySet();
    }

    public void putBoolean(@NonNull String str, boolean z) {
        this.zzq.put(str, Boolean.valueOf(z));
    }

    public void putByte(@NonNull String str, byte b) {
        this.zzq.put(str, Byte.valueOf(b));
    }

    public void putInt(@NonNull String str, int i) {
        this.zzq.put(str, Integer.valueOf(i));
    }

    public void putLong(@NonNull String str, long j) {
        this.zzq.put(str, Long.valueOf(j));
    }

    public void putFloat(@NonNull String str, float f) {
        this.zzq.put(str, Float.valueOf(f));
    }

    public void putDouble(@NonNull String str, double d) {
        this.zzq.put(str, Double.valueOf(d));
    }

    public void putString(@NonNull String str, String str2) {
        this.zzq.put(str, str2);
    }

    public void putAsset(@NonNull String str, Asset asset) {
        this.zzq.put(str, asset);
    }

    public void putDataMap(@NonNull String str, DataMap dataMap) {
        this.zzq.put(str, dataMap);
    }

    public void putDataMapArrayList(@NonNull String str, ArrayList<DataMap> arrayList) {
        this.zzq.put(str, arrayList);
    }

    public void putIntegerArrayList(@NonNull String str, ArrayList<Integer> arrayList) {
        this.zzq.put(str, arrayList);
    }

    public void putStringArrayList(@NonNull String str, ArrayList<String> arrayList) {
        this.zzq.put(str, arrayList);
    }

    public void putByteArray(@NonNull String str, byte[] bArr) {
        this.zzq.put(str, bArr);
    }

    public void putLongArray(@NonNull String str, long[] jArr) {
        this.zzq.put(str, jArr);
    }

    public void putFloatArray(@NonNull String str, float[] fArr) {
        this.zzq.put(str, fArr);
    }

    public void putStringArray(@NonNull String str, String[] strArr) {
        this.zzq.put(str, strArr);
    }

    public boolean getBoolean(@NonNull String str) {
        return getBoolean(str, false);
    }

    public boolean getBoolean(@NonNull String str, boolean z) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return z;
        }
        try {
            return ((Boolean) obj).booleanValue();
        } catch (ClassCastException e) {
            zza(str, obj, SpInputType.BOOLEAN, Boolean.valueOf(z), e);
            return z;
        }
    }

    public byte getByte(@NonNull String str) {
        return getByte(str, (byte) 0);
    }

    public byte getByte(@NonNull String str, byte b) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return b;
        }
        try {
            return ((Byte) obj).byteValue();
        } catch (ClassCastException e) {
            zza(str, obj, "Byte", Byte.valueOf(b), e);
            return b;
        }
    }

    public int getInt(@NonNull String str) {
        return getInt(str, 0);
    }

    public int getInt(@NonNull String str, int i) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return i;
        }
        try {
            return ((Integer) obj).intValue();
        } catch (ClassCastException e) {
            zza(str, obj, SpInputType.INTEGER, e);
            return i;
        }
    }

    public long getLong(@NonNull String str) {
        return getLong(str, 0);
    }

    public long getLong(@NonNull String str, long j) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return j;
        }
        try {
            return ((Long) obj).longValue();
        } catch (ClassCastException e) {
            zza(str, obj, Constants.LONG, e);
            return j;
        }
    }

    public float getFloat(@NonNull String str) {
        return getFloat(str, 0.0f);
    }

    public float getFloat(@NonNull String str, float f) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return f;
        }
        try {
            return ((Float) obj).floatValue();
        } catch (ClassCastException e) {
            zza(str, obj, SpInputType.FLOAT, Float.valueOf(f), e);
            return f;
        }
    }

    public double getDouble(@NonNull String str) {
        return getDouble(str, 0.0d);
    }

    public double getDouble(@NonNull String str, double d) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return d;
        }
        try {
            return ((Double) obj).doubleValue();
        } catch (ClassCastException e) {
            zza(str, obj, "Double", Double.valueOf(d), e);
            return d;
        }
    }

    public String getString(@NonNull String str, String str2) {
        String string = getString(str);
        return string == null ? str2 : string;
    }

    public String getString(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String) obj;
        } catch (ClassCastException e) {
            zza(str, obj, SpInputType.STRING, e);
            return null;
        }
    }

    public Asset getAsset(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (Asset) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "Asset", e);
            return null;
        }
    }

    public DataMap getDataMap(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (DataMap) obj;
        } catch (ClassCastException e) {
            zza(str, obj, TAG, e);
            return null;
        }
    }

    public ArrayList<Integer> getIntegerArrayList(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "ArrayList<Integer>", e);
            return null;
        }
    }

    public ArrayList<String> getStringArrayList(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "ArrayList<String>", e);
            return null;
        }
    }

    public ArrayList<DataMap> getDataMapArrayList(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "ArrayList<DataMap>", e);
            return null;
        }
    }

    public byte[] getByteArray(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (byte[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "byte[]", e);
            return null;
        }
    }

    public long[] getLongArray(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (long[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "long[]", e);
            return null;
        }
    }

    public float[] getFloatArray(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (float[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "float[]", e);
            return null;
        }
    }

    public String[] getStringArray(@NonNull String str) {
        Object obj = this.zzq.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String[]) obj;
        } catch (ClassCastException e) {
            zza(str, obj, "String[]", e);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x00cd A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.google.android.gms.wearable.DataMap
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            com.google.android.gms.wearable.DataMap r7 = (com.google.android.gms.wearable.DataMap) r7
            int r0 = r6.size()
            int r2 = r7.size()
            if (r0 == r2) goto L_0x0013
            return r1
        L_0x0013:
            java.util.Set r0 = r6.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x001b:
            boolean r2 = r0.hasNext()
            r3 = 1
            if (r2 == 0) goto L_0x00ce
            java.lang.Object r2 = r0.next()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r4 = r6.get(r2)
            java.lang.Object r2 = r7.get(r2)
            boolean r5 = r4 instanceof com.google.android.gms.wearable.Asset
            if (r5 == 0) goto L_0x006d
            boolean r5 = r2 instanceof com.google.android.gms.wearable.Asset
            if (r5 != 0) goto L_0x0039
            return r1
        L_0x0039:
            com.google.android.gms.wearable.Asset r4 = (com.google.android.gms.wearable.Asset) r4
            com.google.android.gms.wearable.Asset r2 = (com.google.android.gms.wearable.Asset) r2
            if (r4 == 0) goto L_0x0066
            if (r2 != 0) goto L_0x0042
            goto L_0x0066
        L_0x0042:
            java.lang.String r3 = r4.getDigest()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0059
            java.lang.String r3 = r4.getDigest()
            java.lang.String r2 = r2.getDigest()
            boolean r3 = r3.equals(r2)
            goto L_0x006a
        L_0x0059:
            byte[] r3 = r4.getData()
            byte[] r2 = r2.getData()
            boolean r3 = java.util.Arrays.equals(r3, r2)
            goto L_0x006a
        L_0x0066:
            if (r4 != r2) goto L_0x0069
            goto L_0x006a
        L_0x0069:
            r3 = r1
        L_0x006a:
            if (r3 != 0) goto L_0x00c9
            return r1
        L_0x006d:
            boolean r5 = r4 instanceof java.lang.String[]
            if (r5 == 0) goto L_0x0081
            boolean r3 = r2 instanceof java.lang.String[]
            if (r3 != 0) goto L_0x0076
            return r1
        L_0x0076:
            java.lang.String[] r4 = (java.lang.String[]) r4
            java.lang.String[] r2 = (java.lang.String[]) r2
            boolean r2 = java.util.Arrays.equals(r4, r2)
            if (r2 != 0) goto L_0x00c9
            return r1
        L_0x0081:
            boolean r5 = r4 instanceof long[]
            if (r5 == 0) goto L_0x0095
            boolean r3 = r2 instanceof long[]
            if (r3 != 0) goto L_0x008a
            return r1
        L_0x008a:
            long[] r4 = (long[]) r4
            long[] r2 = (long[]) r2
            boolean r2 = java.util.Arrays.equals(r4, r2)
            if (r2 != 0) goto L_0x00c9
            return r1
        L_0x0095:
            boolean r5 = r4 instanceof float[]
            if (r5 == 0) goto L_0x00a9
            boolean r3 = r2 instanceof float[]
            if (r3 != 0) goto L_0x009e
            return r1
        L_0x009e:
            float[] r4 = (float[]) r4
            float[] r2 = (float[]) r2
            boolean r2 = java.util.Arrays.equals(r4, r2)
            if (r2 != 0) goto L_0x00c9
            return r1
        L_0x00a9:
            boolean r5 = r4 instanceof byte[]
            if (r5 == 0) goto L_0x00bd
            boolean r3 = r2 instanceof byte[]
            if (r3 != 0) goto L_0x00b2
            return r1
        L_0x00b2:
            byte[] r4 = (byte[]) r4
            byte[] r2 = (byte[]) r2
            boolean r2 = java.util.Arrays.equals(r4, r2)
            if (r2 != 0) goto L_0x00c9
            return r1
        L_0x00bd:
            if (r4 == 0) goto L_0x00cb
            if (r2 != 0) goto L_0x00c2
            goto L_0x00cb
        L_0x00c2:
            boolean r2 = r4.equals(r2)
            if (r2 != 0) goto L_0x00c9
            return r1
        L_0x00c9:
            goto L_0x001b
        L_0x00cb:
            if (r4 == r2) goto L_0x00ce
            return r1
        L_0x00ce:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.wearable.DataMap.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return this.zzq.hashCode() * 29;
    }

    public String toString() {
        return this.zzq.toString();
    }

    private static void zza(String str, Object obj, String str2, ClassCastException classCastException) {
        zza(str, obj, str2, "<null>", classCastException);
    }

    private static void zza(String str, Object obj, String str2, Object obj2, ClassCastException classCastException) {
        Log.w(TAG, "Key " + str + " expected " + str2 + " but value was a " + obj.getClass().getName() + ".  The default value " + obj2 + " was returned.");
        Log.w(TAG, "Attempt to cast generated internal exception:", classCastException);
    }

    private static int zza(ArrayList<?> arrayList) {
        int i = 0;
        if (arrayList.isEmpty()) {
            return 0;
        }
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (obj != null) {
                if (obj instanceof Integer) {
                    return 2;
                }
                if (obj instanceof String) {
                    return 3;
                }
                if (obj instanceof DataMap) {
                    return 4;
                }
                if (obj instanceof Bundle) {
                    return 5;
                }
            }
        }
        return 1;
    }
}
