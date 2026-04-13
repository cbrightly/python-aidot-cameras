package com.google.android.gms.internal.wearable;

import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

@VisibleForTesting
public final class zze {
    public static zzf zza(DataMap dataMap) {
        zzg zzg = new zzg();
        ArrayList arrayList = new ArrayList();
        TreeSet treeSet = new TreeSet(dataMap.keySet());
        zzh[] zzhArr = new zzh[treeSet.size()];
        Iterator it = treeSet.iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            Object obj = dataMap.get(str);
            zzhArr[i] = new zzh();
            zzhArr[i].name = str;
            zzhArr[i].zzga = zza(arrayList, obj);
            i++;
        }
        zzg.zzfy = zzhArr;
        return new zzf(zzg, arrayList);
    }

    public static DataMap zza(zzf zzf) {
        DataMap dataMap = new DataMap();
        for (zzh zzh : zzf.zzfw.zzfy) {
            zza(zzf.zzfx, dataMap, zzh.name, zzh.zzga);
        }
        return dataMap;
    }

    private static zzi zza(List<Asset> list, Object obj) {
        zzi zzi = new zzi();
        if (obj == null) {
            zzi.type = 14;
            return zzi;
        }
        zzj zzj = new zzj();
        zzi.zzgc = zzj;
        if (obj instanceof String) {
            zzi.type = 2;
            zzj.zzge = (String) obj;
        } else if (obj instanceof Integer) {
            zzi.type = 6;
            zzj.zzgi = ((Integer) obj).intValue();
        } else if (obj instanceof Long) {
            zzi.type = 5;
            zzj.zzgh = ((Long) obj).longValue();
        } else if (obj instanceof Double) {
            zzi.type = 3;
            zzj.zzgf = ((Double) obj).doubleValue();
        } else if (obj instanceof Float) {
            zzi.type = 4;
            zzj.zzgg = ((Float) obj).floatValue();
        } else if (obj instanceof Boolean) {
            zzi.type = 8;
            zzj.zzgk = ((Boolean) obj).booleanValue();
        } else if (obj instanceof Byte) {
            zzi.type = 7;
            zzj.zzgj = ((Byte) obj).byteValue();
        } else if (obj instanceof byte[]) {
            zzi.type = 1;
            zzj.zzgd = (byte[]) obj;
        } else if (obj instanceof String[]) {
            zzi.type = 11;
            zzj.zzgn = (String[]) obj;
        } else if (obj instanceof long[]) {
            zzi.type = 12;
            zzj.zzgo = (long[]) obj;
        } else if (obj instanceof float[]) {
            zzi.type = 15;
            zzj.zzgp = (float[]) obj;
        } else if (obj instanceof Asset) {
            zzi.type = 13;
            list.add((Asset) obj);
            zzj.zzgq = (long) (list.size() - 1);
        } else {
            int i = 0;
            if (obj instanceof DataMap) {
                zzi.type = 9;
                DataMap dataMap = (DataMap) obj;
                TreeSet treeSet = new TreeSet(dataMap.keySet());
                zzh[] zzhArr = new zzh[treeSet.size()];
                Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    zzhArr[i] = new zzh();
                    zzhArr[i].name = str;
                    zzhArr[i].zzga = zza(list, dataMap.get(str));
                    i++;
                }
                zzi.zzgc.zzgl = zzhArr;
            } else if (obj instanceof ArrayList) {
                zzi.type = 10;
                ArrayList arrayList = (ArrayList) obj;
                zzi[] zziArr = new zzi[arrayList.size()];
                Object obj2 = null;
                int size = arrayList.size();
                int i2 = 14;
                while (i < size) {
                    Object obj3 = arrayList.get(i);
                    zzi zza = zza(list, obj3);
                    int i3 = zza.type;
                    if (i3 == 14 || i3 == 2 || i3 == 6 || i3 == 9) {
                        if (i2 == 14 && i3 != 14) {
                            obj2 = obj3;
                            i2 = i3;
                        } else if (i3 != i2) {
                            String valueOf = String.valueOf(obj2.getClass());
                            String valueOf2 = String.valueOf(obj3.getClass());
                            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 80 + String.valueOf(valueOf2).length());
                            sb.append("ArrayList elements must all be of the sameclass, but this one contains a ");
                            sb.append(valueOf);
                            sb.append(" and a ");
                            sb.append(valueOf2);
                            throw new IllegalArgumentException(sb.toString());
                        }
                        zziArr[i] = zza;
                        i++;
                    } else {
                        String valueOf3 = String.valueOf(obj3.getClass());
                        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + NeedPermissionEvent.PER_IPC_ALBUM_PERM);
                        sb2.append("The only ArrayList element types supported by DataBundleUtil are String, Integer, Bundle, and null, but this ArrayList contains a ");
                        sb2.append(valueOf3);
                        throw new IllegalArgumentException(sb2.toString());
                    }
                }
                zzi.zzgc.zzgm = zziArr;
            } else {
                String valueOf4 = String.valueOf(obj.getClass().getSimpleName());
                throw new RuntimeException(valueOf4.length() != 0 ? "newFieldValueFromValue: unexpected value ".concat(valueOf4) : new String("newFieldValueFromValue: unexpected value "));
            }
        }
        return zzi;
    }

    private static void zza(List<Asset> list, DataMap dataMap, String str, zzi zzi) {
        List<Asset> list2 = list;
        DataMap dataMap2 = dataMap;
        String str2 = str;
        zzi zzi2 = zzi;
        int i = zzi2.type;
        Object obj = null;
        int i2 = 14;
        if (i == 14) {
            dataMap2.putString(str2, (String) null);
            return;
        }
        zzj zzj = zzi2.zzgc;
        if (i == 1) {
            dataMap2.putByteArray(str2, zzj.zzgd);
        } else if (i == 11) {
            dataMap2.putStringArray(str2, zzj.zzgn);
        } else if (i == 12) {
            dataMap2.putLongArray(str2, zzj.zzgo);
        } else if (i == 15) {
            dataMap2.putFloatArray(str2, zzj.zzgp);
        } else if (i == 2) {
            dataMap2.putString(str2, zzj.zzge);
        } else if (i == 3) {
            dataMap2.putDouble(str2, zzj.zzgf);
        } else if (i == 4) {
            dataMap2.putFloat(str2, zzj.zzgg);
        } else if (i == 5) {
            dataMap2.putLong(str2, zzj.zzgh);
        } else if (i == 6) {
            dataMap2.putInt(str2, zzj.zzgi);
        } else if (i == 7) {
            dataMap2.putByte(str2, (byte) zzj.zzgj);
        } else if (i == 8) {
            dataMap2.putBoolean(str2, zzj.zzgk);
        } else if (i != 13) {
            int i3 = 9;
            if (i == 9) {
                DataMap dataMap3 = new DataMap();
                for (zzh zzh : zzj.zzgl) {
                    zza(list2, dataMap3, zzh.name, zzh.zzga);
                }
                dataMap2.putDataMap(str2, dataMap3);
            } else if (i == 10) {
                int i4 = 14;
                for (zzi zzi3 : zzj.zzgm) {
                    if (i4 == 14) {
                        int i5 = zzi3.type;
                        if (i5 == 9 || i5 == 2 || i5 == 6) {
                            i4 = i5;
                        } else if (i5 != 14) {
                            int i6 = zzi3.type;
                            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 48);
                            sb.append("Unexpected TypedValue type: ");
                            sb.append(i6);
                            sb.append(" for key ");
                            sb.append(str2);
                            throw new IllegalArgumentException(sb.toString());
                        }
                    } else if (zzi3.type != i4) {
                        int i7 = zzi3.type;
                        StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 126);
                        sb2.append("The ArrayList elements should all be the same type, but ArrayList with key ");
                        sb2.append(str2);
                        sb2.append(" contains items of type ");
                        sb2.append(i4);
                        sb2.append(" and ");
                        sb2.append(i7);
                        throw new IllegalArgumentException(sb2.toString());
                    }
                }
                ArrayList arrayList = new ArrayList(zzj.zzgm.length);
                zzi[] zziArr = zzj.zzgm;
                int length = zziArr.length;
                int i8 = 0;
                while (i8 < length) {
                    zzi zzi4 = zziArr[i8];
                    if (zzi4.type == i2) {
                        arrayList.add(obj);
                    } else if (i4 == i3) {
                        DataMap dataMap4 = new DataMap();
                        for (zzh zzh2 : zzi4.zzgc.zzgl) {
                            zza(list2, dataMap4, zzh2.name, zzh2.zzga);
                        }
                        arrayList.add(dataMap4);
                    } else if (i4 == 2) {
                        arrayList.add(zzi4.zzgc.zzge);
                    } else if (i4 == 6) {
                        arrayList.add(Integer.valueOf(zzi4.zzgc.zzgi));
                    } else {
                        StringBuilder sb3 = new StringBuilder(39);
                        sb3.append("Unexpected typeOfArrayList: ");
                        sb3.append(i4);
                        throw new IllegalArgumentException(sb3.toString());
                    }
                    i8++;
                    obj = null;
                    i2 = 14;
                    i3 = 9;
                }
                if (i4 == 14) {
                    dataMap2.putStringArrayList(str2, arrayList);
                } else if (i4 == 9) {
                    dataMap2.putDataMapArrayList(str2, arrayList);
                } else if (i4 == 2) {
                    dataMap2.putStringArrayList(str2, arrayList);
                } else if (i4 == 6) {
                    dataMap2.putIntegerArrayList(str2, arrayList);
                } else {
                    StringBuilder sb4 = new StringBuilder(39);
                    sb4.append("Unexpected typeOfArrayList: ");
                    sb4.append(i4);
                    throw new IllegalStateException(sb4.toString());
                }
            } else {
                StringBuilder sb5 = new StringBuilder(43);
                sb5.append("populateBundle: unexpected type ");
                sb5.append(i);
                throw new RuntimeException(sb5.toString());
            }
        } else if (list2 == null) {
            String valueOf = String.valueOf(str);
            throw new RuntimeException(valueOf.length() != 0 ? "populateBundle: unexpected type for: ".concat(valueOf) : new String("populateBundle: unexpected type for: "));
        } else {
            dataMap2.putAsset(str2, list2.get((int) zzj.zzgq));
        }
    }
}
