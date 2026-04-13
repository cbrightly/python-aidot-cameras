package com.google.android.gms.internal.measurement;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.leedarson.bean.Constants;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzmk {
    private static final char[] zza;

    static {
        char[] cArr = new char[80];
        zza = cArr;
        Arrays.fill(cArr, ' ');
    }

    static String zza(zzmi zzmi, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zzd(zzmi, sb, 0);
        return sb.toString();
    }

    static void zzb(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            for (Object zzb : (List) obj) {
                zzb(sb, i, str, zzb);
            }
        } else if (obj instanceof Map) {
            for (Map.Entry zzb2 : ((Map) obj).entrySet()) {
                zzb(sb, i, str, zzb2);
            }
        } else {
            sb.append(10);
            zzc(i, sb);
            if (!str.isEmpty()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Character.toLowerCase(str.charAt(0)));
                for (int i2 = 1; i2 < str.length(); i2++) {
                    char charAt = str.charAt(i2);
                    if (Character.isUpperCase(charAt)) {
                        sb2.append("_");
                    }
                    sb2.append(Character.toLowerCase(charAt));
                }
                str = sb2.toString();
            }
            sb.append(str);
            if (obj instanceof String) {
                sb.append(": \"");
                sb.append(zzni.zza(new zzjx(((String) obj).getBytes(zzlj.zzb))));
                sb.append(StringUtil.DOUBLE_QUOTE);
            } else if (obj instanceof zzka) {
                sb.append(": \"");
                sb.append(zzni.zza((zzka) obj));
                sb.append(StringUtil.DOUBLE_QUOTE);
            } else if (obj instanceof zzlb) {
                sb.append(" {");
                zzd((zzlb) obj, sb, i + 2);
                sb.append("\n");
                zzc(i, sb);
                sb.append("}");
            } else if (obj instanceof Map.Entry) {
                sb.append(" {");
                Map.Entry entry = (Map.Entry) obj;
                int i3 = i + 2;
                zzb(sb, i3, CacheEntity.KEY, entry.getKey());
                zzb(sb, i3, "value", entry.getValue());
                sb.append("\n");
                zzc(i, sb);
                sb.append("}");
            } else {
                sb.append(": ");
                sb.append(obj);
            }
        }
    }

    private static void zzc(int i, StringBuilder sb) {
        while (i > 0) {
            int i2 = 80;
            if (i <= 80) {
                i2 = i;
            }
            sb.append(zza, 0, i2);
            i -= i2;
        }
    }

    private static void zzd(zzmi zzmi, StringBuilder sb, int i) {
        int i2;
        boolean z;
        Method method;
        Method method2;
        zzmi zzmi2 = zzmi;
        StringBuilder sb2 = sb;
        int i3 = i;
        HashSet hashSet = new HashSet();
        HashMap hashMap = new HashMap();
        TreeMap treeMap = new TreeMap();
        Method[] declaredMethods = zzmi.getClass().getDeclaredMethods();
        int length = declaredMethods.length;
        int i4 = 0;
        while (true) {
            i2 = 3;
            if (i4 >= length) {
                break;
            }
            Method method3 = declaredMethods[i4];
            if (!Modifier.isStatic(method3.getModifiers()) && method3.getName().length() >= 3) {
                if (method3.getName().startsWith("set")) {
                    hashSet.add(method3.getName());
                } else if (Modifier.isPublic(method3.getModifiers()) && method3.getParameterTypes().length == 0) {
                    if (method3.getName().startsWith("has")) {
                        hashMap.put(method3.getName(), method3);
                    } else if (method3.getName().startsWith("get")) {
                        treeMap.put(method3.getName(), method3);
                    }
                }
            }
            i4++;
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            String substring = ((String) entry.getKey()).substring(i2);
            if (substring.endsWith("List") && !substring.endsWith("OrBuilderList") && !substring.equals("List") && (method2 = (Method) entry.getValue()) != null && method2.getReturnType().equals(List.class)) {
                zzb(sb2, i3, substring.substring(0, substring.length() - 4), zzlb.zzbK(method2, zzmi2, new Object[0]));
                i2 = 3;
            } else if (substring.endsWith(Constants.SERVICE_MAP) && !substring.equals(Constants.SERVICE_MAP) && (method = (Method) entry.getValue()) != null && method.getReturnType().equals(Map.class) && !method.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(method.getModifiers())) {
                zzb(sb2, i3, substring.substring(0, substring.length() - 3), zzlb.zzbK(method, zzmi2, new Object[0]));
                i2 = 3;
            } else if (!hashSet.contains("set".concat(String.valueOf(substring)))) {
                i2 = 3;
            } else if (!substring.endsWith("Bytes") || !treeMap.containsKey("get".concat(String.valueOf(substring.substring(0, substring.length() - 5))))) {
                Method method4 = (Method) entry.getValue();
                Method method5 = (Method) hashMap.get("has".concat(String.valueOf(substring)));
                if (method4 != null) {
                    Object zzbK = zzlb.zzbK(method4, zzmi2, new Object[0]);
                    if (method5 == null) {
                        if (zzbK instanceof Boolean) {
                            if (!((Boolean) zzbK).booleanValue()) {
                                i2 = 3;
                            }
                        } else if (zzbK instanceof Integer) {
                            if (((Integer) zzbK).intValue() == 0) {
                                i2 = 3;
                            }
                        } else if (zzbK instanceof Float) {
                            if (Float.floatToRawIntBits(((Float) zzbK).floatValue()) == 0) {
                                i2 = 3;
                            }
                        } else if (!(zzbK instanceof Double)) {
                            if (zzbK instanceof String) {
                                z = zzbK.equals("");
                            } else if (zzbK instanceof zzka) {
                                z = zzbK.equals(zzka.zzb);
                            } else if (zzbK instanceof zzmi) {
                                if (zzbK == ((zzmi) zzbK).zzbV()) {
                                    i2 = 3;
                                }
                            } else if ((zzbK instanceof Enum) && ((Enum) zzbK).ordinal() == 0) {
                                i2 = 3;
                            }
                            if (z) {
                                i2 = 3;
                            }
                        } else if (Double.doubleToRawLongBits(((Double) zzbK).doubleValue()) == 0) {
                            i2 = 3;
                        }
                    } else if (!((Boolean) zzlb.zzbK(method5, zzmi2, new Object[0])).booleanValue()) {
                        i2 = 3;
                    }
                    zzb(sb2, i3, substring, zzbK);
                    i2 = 3;
                } else {
                    i2 = 3;
                }
            } else {
                i2 = 3;
            }
        }
        if (!(zzmi2 instanceof zzky)) {
            zznl zznl = ((zzlb) zzmi2).zzc;
            if (zznl != null) {
                zznl.zzi(sb2, i3);
                return;
            }
            return;
        }
        zzky zzky = (zzky) zzmi2;
        throw null;
    }
}
