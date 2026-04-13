package com.google.android.libraries.places.internal;

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

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzahj {
    private static final char[] zza;

    static {
        char[] cArr = new char[80];
        zza = cArr;
        Arrays.fill(cArr, ' ');
    }

    static String zza(zzahh zzahh, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zzd(zzahh, sb, 0);
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
                sb.append(zzaih.zza(new zzafb(((String) obj).getBytes(zzagi.zzb))));
                sb.append(StringUtil.DOUBLE_QUOTE);
            } else if (obj instanceof zzafe) {
                sb.append(": \"");
                sb.append(zzaih.zza((zzafe) obj));
                sb.append(StringUtil.DOUBLE_QUOTE);
            } else if (obj instanceof zzafz) {
                sb.append(" {");
                zzd((zzafz) obj, sb, i + 2);
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

    private static void zzd(zzahh zzahh, StringBuilder sb, int i) {
        int i2;
        boolean z;
        Method method;
        Method method2;
        zzahh zzahh2 = zzahh;
        StringBuilder sb2 = sb;
        int i3 = i;
        HashSet hashSet = new HashSet();
        HashMap hashMap = new HashMap();
        TreeMap treeMap = new TreeMap();
        Method[] declaredMethods = zzahh.getClass().getDeclaredMethods();
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
                zzb(sb2, i3, substring.substring(0, substring.length() - 4), zzafz.zzE(method2, zzahh2, new Object[0]));
                i2 = 3;
            } else if (substring.endsWith(Constants.SERVICE_MAP) && !substring.equals(Constants.SERVICE_MAP) && (method = (Method) entry.getValue()) != null && method.getReturnType().equals(Map.class) && !method.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(method.getModifiers())) {
                zzb(sb2, i3, substring.substring(0, substring.length() - 3), zzafz.zzE(method, zzahh2, new Object[0]));
                i2 = 3;
            } else if (!hashSet.contains("set".concat(String.valueOf(substring)))) {
                i2 = 3;
            } else if (!substring.endsWith("Bytes") || !treeMap.containsKey("get".concat(String.valueOf(substring.substring(0, substring.length() - 5))))) {
                Method method4 = (Method) entry.getValue();
                Method method5 = (Method) hashMap.get("has".concat(String.valueOf(substring)));
                if (method4 != null) {
                    Object zzE = zzafz.zzE(method4, zzahh2, new Object[0]);
                    if (method5 == null) {
                        if (zzE instanceof Boolean) {
                            if (!((Boolean) zzE).booleanValue()) {
                                i2 = 3;
                            }
                        } else if (zzE instanceof Integer) {
                            if (((Integer) zzE).intValue() == 0) {
                                i2 = 3;
                            }
                        } else if (zzE instanceof Float) {
                            if (Float.floatToRawIntBits(((Float) zzE).floatValue()) == 0) {
                                i2 = 3;
                            }
                        } else if (!(zzE instanceof Double)) {
                            if (zzE instanceof String) {
                                z = zzE.equals("");
                            } else if (zzE instanceof zzafe) {
                                z = zzE.equals(zzafe.zzb);
                            } else if (zzE instanceof zzahh) {
                                if (zzE == ((zzahh) zzE).zzt()) {
                                    i2 = 3;
                                }
                            } else if ((zzE instanceof Enum) && ((Enum) zzE).ordinal() == 0) {
                                i2 = 3;
                            }
                            if (z) {
                                i2 = 3;
                            }
                        } else if (Double.doubleToRawLongBits(((Double) zzE).doubleValue()) == 0) {
                            i2 = 3;
                        }
                    } else if (!((Boolean) zzafz.zzE(method5, zzahh2, new Object[0])).booleanValue()) {
                        i2 = 3;
                    }
                    zzb(sb2, i3, substring, zzE);
                    i2 = 3;
                } else {
                    i2 = 3;
                }
            } else {
                i2 = 3;
            }
        }
        if (!(zzahh2 instanceof zzafx)) {
            zzaik zzaik = ((zzafz) zzahh2).zzc;
            if (zzaik != null) {
                zzaik.zzg(sb2, i3);
                return;
            }
            return;
        }
        zzafx zzafx = (zzafx) zzahh2;
        throw null;
    }
}
