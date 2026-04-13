package com.tencent.bugly.proguard;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.bean.IRhyDevice;
import com.meituan.robust.Constants;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: BUGLY */
public class a {
    private static Proxy e;
    protected HashMap<String, HashMap<String, byte[]>> a = new HashMap<>();
    protected String b;
    i c;
    private HashMap<String, Object> d;

    public static aj a(int i) {
        if (i == 1) {
            return new ai();
        }
        if (i == 3) {
            return new ah();
        }
        return null;
    }

    a() {
        new HashMap();
        this.d = new HashMap<>();
        this.b = "GBK";
        this.c = new i();
    }

    public static void a(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            e = null;
        } else {
            e = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i));
        }
    }

    public static void a(InetAddress inetAddress, int i) {
        if (inetAddress == null) {
            e = null;
        } else {
            e = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(inetAddress, i));
        }
    }

    public void a(String str) {
        this.b = str;
    }

    public static at a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        at atVar = new at();
        atVar.a = userInfoBean.e;
        atVar.e = userInfoBean.j;
        atVar.d = userInfoBean.c;
        atVar.c = userInfoBean.d;
        atVar.g = com.tencent.bugly.crashreport.common.info.a.b().i();
        atVar.h = userInfoBean.o == 1;
        int i = userInfoBean.b;
        switch (i) {
            case 1:
                atVar.b = 1;
                break;
            case 2:
                atVar.b = 4;
                break;
            case 3:
                atVar.b = 2;
                break;
            case 4:
                atVar.b = 3;
                break;
            default:
                if (i >= 10 && i < 20) {
                    atVar.b = (byte) i;
                    break;
                } else {
                    x.e("unknown uinfo type %d ", Integer.valueOf(i));
                    return null;
                }
                break;
        }
        HashMap hashMap = new HashMap();
        atVar.f = hashMap;
        if (userInfoBean.p >= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(userInfoBean.p);
            hashMap.put("C01", sb.toString());
        }
        if (userInfoBean.q >= 0) {
            Map<String, String> map = atVar.f;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(userInfoBean.q);
            map.put("C02", sb2.toString());
        }
        Map<String, String> map2 = userInfoBean.r;
        if (map2 != null && map2.size() > 0) {
            for (Map.Entry next : userInfoBean.r.entrySet()) {
                Map<String, String> map3 = atVar.f;
                map3.put("C03_" + ((String) next.getKey()), next.getValue());
            }
        }
        Map<String, String> map4 = userInfoBean.s;
        if (map4 != null && map4.size() > 0) {
            for (Map.Entry next2 : userInfoBean.s.entrySet()) {
                Map<String, String> map5 = atVar.f;
                map5.put("C04_" + ((String) next2.getKey()), next2.getValue());
            }
        }
        Map<String, String> map6 = atVar.f;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(!userInfoBean.l);
        map6.put("A36", sb3.toString());
        Map<String, String> map7 = atVar.f;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(userInfoBean.g);
        map7.put("F02", sb4.toString());
        Map<String, String> map8 = atVar.f;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(userInfoBean.h);
        map8.put("F03", sb5.toString());
        Map<String, String> map9 = atVar.f;
        map9.put("F04", userInfoBean.j);
        Map<String, String> map10 = atVar.f;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(userInfoBean.i);
        map10.put("F05", sb6.toString());
        Map<String, String> map11 = atVar.f;
        map11.put("F06", userInfoBean.m);
        Map<String, String> map12 = atVar.f;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(userInfoBean.k);
        map12.put("F10", sb7.toString());
        x.c("summary type %d vm:%d", Byte.valueOf(atVar.b), Integer.valueOf(atVar.f.size()));
        return atVar;
    }

    public static Proxy b() {
        return e;
    }

    public static String a(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (true) {
            String str = "map";
            if (i < arrayList.size()) {
                String str2 = arrayList.get(i);
                if (str2.equals(Constants.LANG_INT) || str2.equals(Constants.INT)) {
                    str = "int32";
                } else if (str2.equals(Constants.LANG_BOOLEAN) || str2.equals("boolean")) {
                    str = "bool";
                } else if (str2.equals(Constants.LANG_BYTE) || str2.equals(Constants.BYTE)) {
                    str = Constants.CHAR;
                } else if (str2.equals(Constants.LANG_DOUBLE) || str2.equals(Constants.DOUBLE)) {
                    str = Constants.DOUBLE;
                } else if (str2.equals(Constants.LANG_FLOAT) || str2.equals("float")) {
                    str = "float";
                } else if (str2.equals(Constants.LANG_LONG) || str2.equals(Constants.LONG)) {
                    str = "int64";
                } else if (str2.equals(Constants.LANG_SHORT) || str2.equals(Constants.SHORT)) {
                    str = Constants.SHORT;
                } else if (str2.equals("java.lang.Character")) {
                    throw new IllegalArgumentException("can not support java.lang.Character");
                } else if (str2.equals("java.lang.String")) {
                    str = TypedValues.Custom.S_STRING;
                } else if (str2.equals("java.util.List")) {
                    str = "list";
                } else if (!str2.equals("java.util.Map")) {
                    str = str2;
                }
                arrayList.set(i, str);
                i++;
            } else {
                Collections.reverse(arrayList);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    String str3 = arrayList.get(i2);
                    if (str3.equals("list")) {
                        int i3 = i2 - 1;
                        arrayList.set(i3, "<" + arrayList.get(i3));
                        arrayList.set(0, arrayList.get(0) + ">");
                    } else if (str3.equals(str)) {
                        int i4 = i2 - 1;
                        arrayList.set(i4, "<" + arrayList.get(i4) + ",");
                        arrayList.set(0, arrayList.get(0) + ">");
                    } else if (str3.equals("Array")) {
                        int i5 = i2 - 1;
                        arrayList.set(i5, "<" + arrayList.get(i5));
                        arrayList.set(0, arrayList.get(0) + ">");
                    }
                }
                Collections.reverse(arrayList);
                Iterator<String> it = arrayList.iterator();
                while (it.hasNext()) {
                    stringBuffer.append(it.next());
                }
                return stringBuffer.toString();
            }
        }
    }

    public <T> void a(String str, T t) {
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        } else if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        } else if (!(t instanceof Set)) {
            j jVar = new j();
            jVar.a(this.b);
            jVar.a((Object) t, 0);
            byte[] a2 = l.a(jVar.a());
            HashMap hashMap = new HashMap(1);
            ArrayList arrayList = new ArrayList(1);
            a((ArrayList<String>) arrayList, (Object) t);
            hashMap.put(a((ArrayList<String>) arrayList), a2);
            this.d.remove(str);
            this.a.put(str, hashMap);
        } else {
            throw new IllegalArgumentException("can not support Set");
        }
    }

    public static au a(List<UserInfoBean> list, int i) {
        com.tencent.bugly.crashreport.common.info.a b2;
        if (list == null || list.size() == 0 || (b2 = com.tencent.bugly.crashreport.common.info.a.b()) == null) {
            return null;
        }
        b2.t();
        au auVar = new au();
        auVar.b = b2.d;
        auVar.c = b2.h();
        ArrayList<at> arrayList = new ArrayList<>();
        for (UserInfoBean a2 : list) {
            at a3 = a(a2);
            if (a3 != null) {
                arrayList.add(a3);
            }
        }
        auVar.d = arrayList;
        HashMap hashMap = new HashMap();
        auVar.e = hashMap;
        hashMap.put("A7", b2.f);
        Map<String, String> map = auVar.e;
        map.put("A6", b2.s());
        Map<String, String> map2 = auVar.e;
        map2.put("A5", b2.r());
        Map<String, String> map3 = auVar.e;
        StringBuilder sb = new StringBuilder();
        sb.append(b2.p());
        map3.put("A2", sb.toString());
        Map<String, String> map4 = auVar.e;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(b2.p());
        map4.put("A1", sb2.toString());
        Map<String, String> map5 = auVar.e;
        map5.put("A24", b2.h);
        Map<String, String> map6 = auVar.e;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(b2.q());
        map6.put("A17", sb3.toString());
        Map<String, String> map7 = auVar.e;
        map7.put("A15", b2.w());
        Map<String, String> map8 = auVar.e;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(b2.x());
        map8.put("A13", sb4.toString());
        Map<String, String> map9 = auVar.e;
        map9.put("F08", b2.v);
        Map<String, String> map10 = auVar.e;
        map10.put("F09", b2.w);
        Map<String, String> G = b2.G();
        if (G != null && G.size() > 0) {
            for (Map.Entry next : G.entrySet()) {
                Map<String, String> map11 = auVar.e;
                map11.put("C04_" + ((String) next.getKey()), next.getValue());
            }
        }
        switch (i) {
            case 1:
                auVar.a = 1;
                break;
            case 2:
                auVar.a = 2;
                break;
            default:
                x.e("unknown up type %d ", Integer.valueOf(i));
                return null;
        }
        return auVar;
    }

    public static <T extends k> T a(byte[] bArr, Class<T> cls) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            T t = (k) cls.newInstance();
            i iVar = new i(bArr);
            iVar.a("utf-8");
            t.a(iVar);
            return t;
        } catch (Throwable th) {
            if (!x.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x02bd, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x02c2, code lost:
        if (com.tencent.bugly.proguard.x.b(r9) == false) goto L_0x02c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x02c4, code lost:
        r9.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x02c7, code lost:
        return null;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.bugly.proguard.ap a(android.content.Context r9, int r10, byte[] r11) {
        /*
            com.tencent.bugly.crashreport.common.info.a r0 = com.tencent.bugly.crashreport.common.info.a.b()
            com.tencent.bugly.crashreport.common.strategy.a r1 = com.tencent.bugly.crashreport.common.strategy.a.a()
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r1 = r1.c()
            r2 = 0
            r3 = 0
            if (r0 == 0) goto L_0x02c8
            if (r1 != 0) goto L_0x0014
            goto L_0x02c8
        L_0x0014:
            com.tencent.bugly.proguard.ap r4 = new com.tencent.bugly.proguard.ap     // Catch:{ all -> 0x02bd }
            r4.<init>()     // Catch:{ all -> 0x02bd }
            monitor-enter(r0)     // Catch:{ all -> 0x02bd }
            r5 = 1
            r4.a = r5     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r0.f()     // Catch:{ all -> 0x02ba }
            r4.b = r6     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r0.c     // Catch:{ all -> 0x02ba }
            r4.c = r6     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r0.j     // Catch:{ all -> 0x02ba }
            r4.d = r6     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r0.l     // Catch:{ all -> 0x02ba }
            r4.e = r6     // Catch:{ all -> 0x02ba }
            r0.getClass()     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = "3.1.0"
            r4.f = r6     // Catch:{ all -> 0x02ba }
            r4.g = r10     // Catch:{ all -> 0x02ba }
            if (r11 != 0) goto L_0x0041
            java.lang.String r10 = ""
            byte[] r10 = r10.getBytes()     // Catch:{ all -> 0x02ba }
            goto L_0x0042
        L_0x0041:
            r10 = r11
        L_0x0042:
            r4.h = r10     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = r0.g     // Catch:{ all -> 0x02ba }
            r4.i = r10     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = r0.h     // Catch:{ all -> 0x02ba }
            r4.j = r10     // Catch:{ all -> 0x02ba }
            java.util.HashMap r10 = new java.util.HashMap     // Catch:{ all -> 0x02ba }
            r10.<init>()     // Catch:{ all -> 0x02ba }
            r4.k = r10     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = r0.e()     // Catch:{ all -> 0x02ba }
            r4.l = r10     // Catch:{ all -> 0x02ba }
            long r6 = r1.p     // Catch:{ all -> 0x02ba }
            r4.m = r6     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = r0.h()     // Catch:{ all -> 0x02ba }
            r4.o = r10     // Catch:{ all -> 0x02ba }
            java.lang.String r9 = com.tencent.bugly.crashreport.common.info.b.c(r9)     // Catch:{ all -> 0x02ba }
            r4.p = r9     // Catch:{ all -> 0x02ba }
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x02ba }
            r4.q = r9     // Catch:{ all -> 0x02ba }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r9.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = r0.k()     // Catch:{ all -> 0x02ba }
            r9.append(r10)     // Catch:{ all -> 0x02ba }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x02ba }
            r4.r = r9     // Catch:{ all -> 0x02ba }
            java.lang.String r9 = r0.j()     // Catch:{ all -> 0x02ba }
            r4.s = r9     // Catch:{ all -> 0x02ba }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r9.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = r0.m()     // Catch:{ all -> 0x02ba }
            r9.append(r10)     // Catch:{ all -> 0x02ba }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x02ba }
            r4.t = r9     // Catch:{ all -> 0x02ba }
            java.lang.String r9 = r0.l()     // Catch:{ all -> 0x02ba }
            r4.u = r9     // Catch:{ all -> 0x02ba }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r9.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = r0.n()     // Catch:{ all -> 0x02ba }
            r9.append(r10)     // Catch:{ all -> 0x02ba }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x02ba }
            r4.v = r9     // Catch:{ all -> 0x02ba }
            java.lang.String r9 = r4.p     // Catch:{ all -> 0x02ba }
            r4.w = r9     // Catch:{ all -> 0x02ba }
            r0.getClass()     // Catch:{ all -> 0x02ba }
            java.lang.String r9 = "com.tencent.bugly"
            r4.n = r9     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "A26"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.y()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "A60"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.z()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "A61"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.A()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "A62"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            boolean r7 = r0.R()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "A63"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            boolean r7 = r0.S()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "F11"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            boolean r7 = r0.z     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "F12"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            boolean r7 = r0.y     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "G1"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.u()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "A64"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.T()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            boolean r9 = r0.B     // Catch:{ all -> 0x02ba }
            if (r9 == 0) goto L_0x0219
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "G2"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.L()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "G3"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.M()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "G4"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.N()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "G5"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.O()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "G6"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.P()     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "G7"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            long r7 = r0.Q()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = java.lang.Long.toString(r7)     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
        L_0x0219:
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "D3"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ba }
            r6.<init>()     // Catch:{ all -> 0x02ba }
            java.lang.String r7 = r0.k     // Catch:{ all -> 0x02ba }
            r6.append(r7)     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.List<com.tencent.bugly.a> r9 = com.tencent.bugly.b.b     // Catch:{ all -> 0x02ba }
            if (r9 == 0) goto L_0x0250
            java.util.Iterator r9 = r9.iterator()     // Catch:{ all -> 0x02ba }
        L_0x0236:
            boolean r10 = r9.hasNext()     // Catch:{ all -> 0x02ba }
            if (r10 == 0) goto L_0x0250
            java.lang.Object r10 = r9.next()     // Catch:{ all -> 0x02ba }
            com.tencent.bugly.a r10 = (com.tencent.bugly.a) r10     // Catch:{ all -> 0x02ba }
            java.lang.String r6 = r10.versionKey     // Catch:{ all -> 0x02ba }
            if (r6 == 0) goto L_0x024f
            java.lang.String r10 = r10.version     // Catch:{ all -> 0x02ba }
            if (r10 == 0) goto L_0x024f
            java.util.Map<java.lang.String, java.lang.String> r7 = r4.k     // Catch:{ all -> 0x02ba }
            r7.put(r6, r10)     // Catch:{ all -> 0x02ba }
        L_0x024f:
            goto L_0x0236
        L_0x0250:
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "G15"
            java.lang.String r6 = "G15"
            java.lang.String r7 = ""
            java.lang.String r6 = com.tencent.bugly.proguard.z.b((java.lang.String) r6, (java.lang.String) r7)     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            java.util.Map<java.lang.String, java.lang.String> r9 = r4.k     // Catch:{ all -> 0x02ba }
            java.lang.String r10 = "D4"
            java.lang.String r6 = "D4"
            java.lang.String r7 = "0"
            java.lang.String r6 = com.tencent.bugly.proguard.z.b((java.lang.String) r6, (java.lang.String) r7)     // Catch:{ all -> 0x02ba }
            r9.put(r10, r6)     // Catch:{ all -> 0x02ba }
            monitor-exit(r0)     // Catch:{ all -> 0x02ba }
            com.tencent.bugly.proguard.u r9 = com.tencent.bugly.proguard.u.a()     // Catch:{ all -> 0x02bd }
            if (r9 == 0) goto L_0x0291
            boolean r9 = r9.a     // Catch:{ all -> 0x02bd }
            if (r9 != 0) goto L_0x0291
            if (r11 == 0) goto L_0x0291
            byte[] r9 = r4.h     // Catch:{ all -> 0x02bd }
            r10 = 2
            java.lang.String r11 = r1.u     // Catch:{ all -> 0x02bd }
            byte[] r9 = com.tencent.bugly.proguard.z.a((byte[]) r9, (int) r10, (int) r5, (java.lang.String) r11)     // Catch:{ all -> 0x02bd }
            r4.h = r9     // Catch:{ all -> 0x02bd }
            if (r9 != 0) goto L_0x0291
            java.lang.String r9 = "reqPkg sbuffer error!"
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ all -> 0x02bd }
            com.tencent.bugly.proguard.x.e(r9, r10)     // Catch:{ all -> 0x02bd }
            return r3
        L_0x0291:
            java.util.Map r9 = r0.F()     // Catch:{ all -> 0x02bd }
            if (r9 == 0) goto L_0x02b9
            java.util.Set r9 = r9.entrySet()     // Catch:{ all -> 0x02bd }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ all -> 0x02bd }
        L_0x029f:
            boolean r10 = r9.hasNext()     // Catch:{ all -> 0x02bd }
            if (r10 == 0) goto L_0x02b9
            java.lang.Object r10 = r9.next()     // Catch:{ all -> 0x02bd }
            java.util.Map$Entry r10 = (java.util.Map.Entry) r10     // Catch:{ all -> 0x02bd }
            java.util.Map<java.lang.String, java.lang.String> r11 = r4.k     // Catch:{ all -> 0x02bd }
            java.lang.Object r0 = r10.getKey()     // Catch:{ all -> 0x02bd }
            java.lang.Object r10 = r10.getValue()     // Catch:{ all -> 0x02bd }
            r11.put(r0, r10)     // Catch:{ all -> 0x02bd }
            goto L_0x029f
        L_0x02b9:
            return r4
        L_0x02ba:
            r9 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x02bd }
            throw r9     // Catch:{ all -> 0x02bd }
        L_0x02bd:
            r9 = move-exception
            boolean r10 = com.tencent.bugly.proguard.x.b(r9)
            if (r10 != 0) goto L_0x02c7
            r9.printStackTrace()
        L_0x02c7:
            return r3
        L_0x02c8:
            java.lang.String r9 = "Can not create request pkg for parameters is invalid."
            java.lang.Object[] r10 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.x.e(r9, r10)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.a.a(android.content.Context, int, byte[]):com.tencent.bugly.proguard.ap");
    }

    private void a(ArrayList<String> arrayList, Object obj) {
        if (obj.getClass().isArray()) {
            if (!obj.getClass().getComponentType().toString().equals(Constants.BYTE)) {
                throw new IllegalArgumentException("only byte[] is supported");
            } else if (Array.getLength(obj) > 0) {
                arrayList.add("java.util.List");
                a(arrayList, Array.get(obj, 0));
            } else {
                arrayList.add("Array");
                arrayList.add("?");
            }
        } else if (obj instanceof Array) {
            throw new IllegalArgumentException("can not support Array, please use List");
        } else if (obj instanceof List) {
            arrayList.add("java.util.List");
            List list = (List) obj;
            if (list.size() > 0) {
                a(arrayList, list.get(0));
            } else {
                arrayList.add("?");
            }
        } else if (obj instanceof Map) {
            arrayList.add("java.util.Map");
            Map map = (Map) obj;
            if (map.size() > 0) {
                Object next = map.keySet().iterator().next();
                Object obj2 = map.get(next);
                arrayList.add(next.getClass().getName());
                a(arrayList, obj2);
                return;
            }
            arrayList.add("?");
            arrayList.add("?");
        } else {
            arrayList.add(obj.getClass().getName());
        }
    }

    public byte[] a() {
        j jVar = new j(0);
        jVar.a(this.b);
        jVar.a(this.a, 0);
        return l.a(jVar.a());
    }

    public void a(byte[] bArr) {
        this.c.a(bArr);
        this.c.a(this.b);
        HashMap hashMap = new HashMap(1);
        HashMap hashMap2 = new HashMap(1);
        hashMap2.put("", new byte[0]);
        hashMap.put("", hashMap2);
        this.a = this.c.a(hashMap, 0, false);
    }

    public static byte[] a(Object obj) {
        try {
            d dVar = new d();
            dVar.c();
            dVar.a("utf-8");
            dVar.b(1);
            dVar.b("RqdServer");
            dVar.c(IRhyDevice.RHYTHM_TYPE_SYNC);
            dVar.a("detail", obj);
            return dVar.a();
        } catch (Throwable th) {
            if (x.b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static aq a(byte[] bArr, boolean z) {
        aq aqVar;
        byte[] bArr2;
        if (bArr != null) {
            try {
                d dVar = new d();
                dVar.c();
                dVar.a("utf-8");
                dVar.a(bArr);
                Object b2 = dVar.b("detail", new aq());
                if (aq.class.isInstance(b2)) {
                    aqVar = aq.class.cast(b2);
                } else {
                    aqVar = null;
                }
                if (!z && aqVar != null && (bArr2 = aqVar.c) != null && bArr2.length > 0) {
                    x.c("resp buf %d", Integer.valueOf(bArr2.length));
                    byte[] b3 = z.b(aqVar.c, 2, 1, StrategyBean.d);
                    aqVar.c = b3;
                    if (b3 == null) {
                        x.e("resp sbuffer error!", new Object[0]);
                        return null;
                    }
                }
                return aqVar;
            } catch (Throwable th) {
                if (!x.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] a(k kVar) {
        try {
            j jVar = new j();
            jVar.a("utf-8");
            kVar.a(jVar);
            return jVar.b();
        } catch (Throwable th) {
            if (x.b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
