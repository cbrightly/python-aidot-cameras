package com.tencent.bugly.crashreport.crash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.google.maps.android.BuildConfig;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: BUGLY */
public final class b {
    private static int a = 0;
    private Context b;
    private u c;
    private p d;
    private a e;
    private o f;
    private BuglyStrategy.a g;

    public b(int i, Context context, u uVar, p pVar, a aVar, BuglyStrategy.a aVar2, o oVar) {
        a = i;
        this.b = context;
        this.c = uVar;
        this.d = pVar;
        this.e = aVar;
        this.g = aVar2;
        this.f = oVar;
    }

    private static List<a> a(List<a> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        for (a next : list) {
            if (next.d && next.b <= currentTimeMillis - CostTimeUtil.DAY) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    private CrashDetailBean a(List<a> list, CrashDetailBean crashDetailBean) {
        List<CrashDetailBean> b2;
        String[] split;
        if (list == null || list.size() == 0) {
            return crashDetailBean;
        }
        CrashDetailBean crashDetailBean2 = null;
        ArrayList arrayList = new ArrayList(10);
        for (a next : list) {
            if (next.e) {
                arrayList.add(next);
            }
        }
        if (arrayList.size() > 0 && (b2 = b((List<a>) arrayList)) != null && b2.size() > 0) {
            Collections.sort(b2);
            for (int i = 0; i < b2.size(); i++) {
                CrashDetailBean crashDetailBean3 = b2.get(i);
                if (i == 0) {
                    crashDetailBean2 = crashDetailBean3;
                } else {
                    String str = crashDetailBean3.s;
                    if (!(str == null || (split = str.split("\n")) == null)) {
                        for (String str2 : split) {
                            if (!crashDetailBean2.s.contains(str2)) {
                                crashDetailBean2.t++;
                                crashDetailBean2.s += str2 + "\n";
                            }
                        }
                    }
                }
            }
        }
        if (crashDetailBean2 == null) {
            crashDetailBean.j = true;
            crashDetailBean.t = 0;
            crashDetailBean.s = "";
            crashDetailBean2 = crashDetailBean;
        }
        for (a next2 : list) {
            if (!next2.e && !next2.d) {
                String str3 = crashDetailBean2.s;
                StringBuilder sb = new StringBuilder();
                sb.append(next2.b);
                if (!str3.contains(sb.toString())) {
                    crashDetailBean2.t++;
                    crashDetailBean2.s += next2.b + "\n";
                }
            }
        }
        if (crashDetailBean2.r != crashDetailBean.r) {
            String str4 = crashDetailBean2.s;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(crashDetailBean.r);
            if (!str4.contains(sb2.toString())) {
                crashDetailBean2.t++;
                crashDetailBean2.s += crashDetailBean.r + "\n";
            }
        }
        return crashDetailBean2;
    }

    public final boolean a(CrashDetailBean crashDetailBean) {
        return a(crashDetailBean, -123456789);
    }

    public final boolean a(CrashDetailBean crashDetailBean, int i) {
        if (crashDetailBean == null) {
            return true;
        }
        String str = c.n;
        if (str != null && !str.isEmpty()) {
            x.c("Crash filter for crash stack is: %s", c.n);
            if (crashDetailBean.q.contains(c.n)) {
                x.d("This crash contains the filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        String str2 = c.o;
        if (str2 != null && !str2.isEmpty()) {
            x.c("Crash regular filter for crash stack is: %s", c.o);
            if (Pattern.compile(c.o).matcher(crashDetailBean.q).find()) {
                x.d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (this.f != null) {
            x.c("Calling 'onCrashSaving' of RQD crash listener.", new Object[0]);
            if (!this.f.c()) {
                x.d("Crash listener 'onCrashSaving' return 'false' thus will not handle this crash.", new Object[0]);
                return true;
            }
        }
        if (crashDetailBean.b != 2) {
            r rVar = new r();
            rVar.b = 1;
            rVar.c = crashDetailBean.A;
            rVar.d = crashDetailBean.B;
            rVar.e = crashDetailBean.r;
            this.d.b(1);
            this.d.a(rVar);
            x.b("[crash] a crash occur, handling...", new Object[0]);
        } else {
            x.b("[crash] a caught exception occur, handling...", new Object[0]);
        }
        List<a> b2 = b();
        ArrayList arrayList = null;
        if (b2 != null && b2.size() > 0) {
            arrayList = new ArrayList(10);
            ArrayList<a> arrayList2 = new ArrayList<>(10);
            arrayList.addAll(a(b2));
            b2.removeAll(arrayList);
            if (((long) b2.size()) > 20) {
                StringBuilder sb = new StringBuilder();
                sb.append("_id in ");
                sb.append("(");
                sb.append("SELECT _id");
                sb.append(" FROM t_cr");
                sb.append(" order by _id");
                sb.append(" limit 5");
                sb.append(")");
                String sb2 = sb.toString();
                sb.setLength(0);
                try {
                    x.c("deleted first record %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", sb2, (String[]) null, (o) null, true)));
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
            if (!com.tencent.bugly.b.c && c.d) {
                boolean z = false;
                for (a next : b2) {
                    if (crashDetailBean.u.equals(next.c)) {
                        if (next.e) {
                            z = true;
                        }
                        arrayList2.add(next);
                    }
                }
                if (z || arrayList2.size() >= c.c) {
                    x.a("same crash occur too much do merged!", new Object[0]);
                    CrashDetailBean a2 = a((List<a>) arrayList2, crashDetailBean);
                    for (a aVar : arrayList2) {
                        if (aVar.a != a2.a) {
                            arrayList.add(aVar);
                        }
                    }
                    d(a2);
                    c((List<a>) arrayList);
                    x.b("[crash] save crash success. For this device crash many times, it will not upload crashes immediately", new Object[0]);
                    return true;
                }
            }
        }
        d(crashDetailBean);
        if (arrayList != null && !arrayList.isEmpty()) {
            c((List<a>) arrayList);
        }
        x.b("[crash] save crash success", new Object[0]);
        return false;
    }

    public final List<CrashDetailBean> a() {
        StrategyBean c2 = a.a().c();
        if (c2 == null) {
            x.d("have not synced remote!", new Object[0]);
            return null;
        } else if (!c2.g) {
            x.d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            x.b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            long b2 = z.b();
            List<a> b3 = b();
            x.c("Size of crash list loaded from DB: %s", Integer.valueOf(b3.size()));
            if (b3.size() <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(a(b3));
            b3.removeAll(arrayList);
            Iterator<a> it = b3.iterator();
            while (it.hasNext()) {
                a next = it.next();
                long j = next.b;
                if (j < b2 - c.g) {
                    it.remove();
                    arrayList.add(next);
                } else if (next.d) {
                    if (j >= currentTimeMillis - CostTimeUtil.DAY) {
                        it.remove();
                    } else if (!next.e) {
                        it.remove();
                        arrayList.add(next);
                    }
                } else if (((long) next.f) >= 3 && j < currentTimeMillis - CostTimeUtil.DAY) {
                    it.remove();
                    arrayList.add(next);
                }
            }
            if (arrayList.size() > 0) {
                c((List<a>) arrayList);
            }
            ArrayList arrayList2 = new ArrayList();
            List<CrashDetailBean> b4 = b(b3);
            if (b4 != null && b4.size() > 0) {
                String str = com.tencent.bugly.crashreport.common.info.a.b().j;
                Iterator<CrashDetailBean> it2 = b4.iterator();
                while (it2.hasNext()) {
                    CrashDetailBean next2 = it2.next();
                    if (!str.equals(next2.f)) {
                        it2.remove();
                        arrayList2.add(next2);
                    }
                }
            }
            if (arrayList2.size() > 0) {
                d((List<CrashDetailBean>) arrayList2);
            }
            return b4;
        }
    }

    public final void b(CrashDetailBean crashDetailBean) {
        if (this.f != null) {
            x.c("Calling 'onCrashHandleEnd' of RQD crash listener.", new Object[0]);
            int i = crashDetailBean.b;
        }
    }

    public final void a(CrashDetailBean crashDetailBean, long j, boolean z) {
        if (c.l) {
            boolean z2 = false;
            x.a("try to upload right now", new Object[0]);
            ArrayList arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            if (crashDetailBean.b == 7) {
                z2 = true;
            }
            a(arrayList, GroupCtrlAdapter.RETRY_TIMEOUT, z, z2, z);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x009e A[Catch:{ all -> 0x00e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00a6 A[Catch:{ all -> 0x00e2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(final java.util.List<com.tencent.bugly.crashreport.crash.CrashDetailBean> r15, long r16, boolean r18, boolean r19, boolean r20) {
        /*
            r14 = this;
            r1 = r14
            r0 = r15
            android.content.Context r2 = r1.b
            com.tencent.bugly.crashreport.common.info.a r2 = com.tencent.bugly.crashreport.common.info.a.a((android.content.Context) r2)
            boolean r2 = r2.e
            if (r2 != 0) goto L_0x000d
            return
        L_0x000d:
            com.tencent.bugly.proguard.u r2 = r1.c
            if (r2 != 0) goto L_0x0012
            return
        L_0x0012:
            if (r20 != 0) goto L_0x001d
            int r3 = com.tencent.bugly.crashreport.crash.c.a
            boolean r2 = r2.b((int) r3)
            if (r2 != 0) goto L_0x001d
            return
        L_0x001d:
            com.tencent.bugly.crashreport.common.strategy.a r2 = r1.e
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r2 = r2.c()
            boolean r3 = r2.g
            r4 = 0
            if (r3 != 0) goto L_0x0038
            java.lang.Object[] r0 = new java.lang.Object[r4]
            java.lang.String r2 = "remote report is disable!"
            com.tencent.bugly.proguard.x.d(r2, r0)
            java.lang.Object[] r0 = new java.lang.Object[r4]
            java.lang.String r2 = "[crash] server closed bugly in this app. please check your appid if is correct, and re-install it"
            com.tencent.bugly.proguard.x.b(r2, r0)
            return
        L_0x0038:
            if (r0 == 0) goto L_0x00fc
            int r3 = r15.size()
            if (r3 != 0) goto L_0x0042
            goto L_0x00fc
        L_0x0042:
            com.tencent.bugly.proguard.u r3 = r1.c     // Catch:{ all -> 0x00e2 }
            boolean r3 = r3.a     // Catch:{ all -> 0x00e2 }
            if (r3 == 0) goto L_0x004b
            java.lang.String r2 = r2.s     // Catch:{ all -> 0x00e2 }
            goto L_0x004d
        L_0x004b:
            java.lang.String r2 = r2.t     // Catch:{ all -> 0x00e2 }
        L_0x004d:
            r8 = r2
            if (r3 == 0) goto L_0x0053
            java.lang.String r2 = com.tencent.bugly.crashreport.common.strategy.StrategyBean.c     // Catch:{ all -> 0x00e2 }
            goto L_0x0055
        L_0x0053:
            java.lang.String r2 = com.tencent.bugly.crashreport.common.strategy.StrategyBean.a     // Catch:{ all -> 0x00e2 }
        L_0x0055:
            r9 = r2
            if (r3 == 0) goto L_0x005b
            r2 = 830(0x33e, float:1.163E-42)
            goto L_0x005d
        L_0x005b:
            r2 = 630(0x276, float:8.83E-43)
        L_0x005d:
            android.content.Context r3 = r1.b     // Catch:{ all -> 0x00e2 }
            com.tencent.bugly.crashreport.common.info.a r5 = com.tencent.bugly.crashreport.common.info.a.b()     // Catch:{ all -> 0x00e2 }
            if (r3 == 0) goto L_0x0094
            int r6 = r15.size()     // Catch:{ all -> 0x00e2 }
            if (r6 == 0) goto L_0x0094
            if (r5 != 0) goto L_0x006e
            goto L_0x0094
        L_0x006e:
            com.tencent.bugly.proguard.ao r6 = new com.tencent.bugly.proguard.ao     // Catch:{ all -> 0x00e2 }
            r6.<init>()     // Catch:{ all -> 0x00e2 }
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ all -> 0x00e2 }
            r7.<init>()     // Catch:{ all -> 0x00e2 }
            r6.a = r7     // Catch:{ all -> 0x00e2 }
            java.util.Iterator r7 = r15.iterator()     // Catch:{ all -> 0x00e2 }
        L_0x007e:
            boolean r10 = r7.hasNext()     // Catch:{ all -> 0x00e2 }
            if (r10 == 0) goto L_0x009c
            java.lang.Object r10 = r7.next()     // Catch:{ all -> 0x00e2 }
            com.tencent.bugly.crashreport.crash.CrashDetailBean r10 = (com.tencent.bugly.crashreport.crash.CrashDetailBean) r10     // Catch:{ all -> 0x00e2 }
            java.util.ArrayList<com.tencent.bugly.proguard.an> r11 = r6.a     // Catch:{ all -> 0x00e2 }
            com.tencent.bugly.proguard.an r10 = a((android.content.Context) r3, (com.tencent.bugly.crashreport.crash.CrashDetailBean) r10, (com.tencent.bugly.crashreport.common.info.a) r5)     // Catch:{ all -> 0x00e2 }
            r11.add(r10)     // Catch:{ all -> 0x00e2 }
            goto L_0x007e
        L_0x0094:
            java.lang.String r3 = "enEXPPkg args == null!"
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x00e2 }
            com.tencent.bugly.proguard.x.d(r3, r5)     // Catch:{ all -> 0x00e2 }
            r6 = 0
        L_0x009c:
            if (r6 != 0) goto L_0x00a6
            java.lang.String r0 = "create eupPkg fail!"
            java.lang.Object[] r2 = new java.lang.Object[r4]     // Catch:{ all -> 0x00e2 }
            com.tencent.bugly.proguard.x.d(r0, r2)     // Catch:{ all -> 0x00e2 }
            return
        L_0x00a6:
            byte[] r3 = com.tencent.bugly.proguard.a.a((com.tencent.bugly.proguard.k) r6)     // Catch:{ all -> 0x00e2 }
            if (r3 != 0) goto L_0x00b5
            java.lang.String r0 = "send encode fail!"
            java.lang.Object[] r2 = new java.lang.Object[r4]     // Catch:{ all -> 0x00e2 }
            com.tencent.bugly.proguard.x.d(r0, r2)     // Catch:{ all -> 0x00e2 }
            return
        L_0x00b5:
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x00e2 }
            com.tencent.bugly.proguard.ap r7 = com.tencent.bugly.proguard.a.a(r5, r2, r3)     // Catch:{ all -> 0x00e2 }
            if (r7 != 0) goto L_0x00c6
            java.lang.String r0 = "request package is null."
            java.lang.Object[] r2 = new java.lang.Object[r4]     // Catch:{ all -> 0x00e2 }
            com.tencent.bugly.proguard.x.d(r0, r2)     // Catch:{ all -> 0x00e2 }
            return
        L_0x00c6:
            com.tencent.bugly.crashreport.crash.b$1 r10 = new com.tencent.bugly.crashreport.crash.b$1     // Catch:{ all -> 0x00e2 }
            r10.<init>(r15)     // Catch:{ all -> 0x00e2 }
            if (r18 == 0) goto L_0x00d9
            com.tencent.bugly.proguard.u r5 = r1.c     // Catch:{ all -> 0x00e2 }
            int r6 = a     // Catch:{ all -> 0x00e2 }
            r11 = r16
            r13 = r19
            r5.a(r6, r7, r8, r9, r10, r11, r13)     // Catch:{ all -> 0x00e2 }
            goto L_0x00fb
        L_0x00d9:
            com.tencent.bugly.proguard.u r5 = r1.c     // Catch:{ all -> 0x00e2 }
            int r6 = a     // Catch:{ all -> 0x00e2 }
            r11 = 0
            r5.a(r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x00e2 }
            return
        L_0x00e2:
            r0 = move-exception
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = r0.toString()
            r2[r4] = r3
            java.lang.String r3 = "req cr error %s"
            com.tencent.bugly.proguard.x.e(r3, r2)
            boolean r2 = com.tencent.bugly.proguard.x.b(r0)
            if (r2 != 0) goto L_0x00fb
            r0.printStackTrace()
        L_0x00fb:
            return
        L_0x00fc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.a(java.util.List, long, boolean, boolean, boolean):void");
    }

    public static void a(boolean z, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            x.c("up finish update state %b", Boolean.valueOf(z));
            for (CrashDetailBean next : list) {
                x.c("pre uid:%s uc:%d re:%b me:%b", next.c, Integer.valueOf(next.l), Boolean.valueOf(next.d), Boolean.valueOf(next.j));
                int i = next.l + 1;
                next.l = i;
                next.d = z;
                x.c("set uid:%s uc:%d re:%b me:%b", next.c, Integer.valueOf(i), Boolean.valueOf(next.d), Boolean.valueOf(next.j));
            }
            for (CrashDetailBean a2 : list) {
                c.a().a(a2);
            }
            x.c("update state size %d", Integer.valueOf(list.size()));
        }
        if (!z) {
            x.b("[crash] upload fail.", new Object[0]);
        }
    }

    public final void c(CrashDetailBean crashDetailBean) {
        int i;
        Map<String, String> map;
        String str;
        if (crashDetailBean != null) {
            if (this.g != null || this.f != null) {
                try {
                    x.a("[crash callback] start user's callback:onCrashHandleStart()", new Object[0]);
                    switch (crashDetailBean.b) {
                        case 0:
                            i = 0;
                            break;
                        case 1:
                            i = 2;
                            break;
                        case 2:
                            i = 1;
                            break;
                        case 3:
                            i = 4;
                            break;
                        case 4:
                            i = 3;
                            break;
                        case 5:
                            i = 5;
                            break;
                        case 6:
                            i = 6;
                            break;
                        case 7:
                            i = 7;
                            break;
                        default:
                            return;
                    }
                    byte[] bArr = null;
                    if (this.f != null) {
                        x.c("Calling 'onCrashHandleStart' of RQD crash listener.", new Object[0]);
                        x.c("Calling 'getCrashExtraMessage' of RQD crash listener.", new Object[0]);
                        String b2 = this.f.b();
                        if (b2 != null) {
                            map = new HashMap<>(1);
                            map.put("userData", b2);
                        } else {
                            map = null;
                        }
                    } else if (this.g != null) {
                        x.c("Calling 'onCrashHandleStart' of Bugly crash listener.", new Object[0]);
                        map = this.g.onCrashHandleStart(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    } else {
                        map = null;
                    }
                    if (map != null && map.size() > 0) {
                        crashDetailBean.O = new LinkedHashMap(map.size());
                        for (Map.Entry next : map.entrySet()) {
                            if (!z.a((String) next.getKey())) {
                                String str2 = (String) next.getKey();
                                if (str2.length() > 100) {
                                    str2 = str2.substring(0, 100);
                                    x.d("setted key length is over limit %d substring to %s", 100, str2);
                                }
                                if (z.a((String) next.getValue()) || ((String) next.getValue()).length() <= 30000) {
                                    str = ((String) next.getValue());
                                } else {
                                    str = ((String) next.getValue()).substring(((String) next.getValue()).length() - 30000);
                                    x.d("setted %s value length is over limit %d substring", str2, 30000);
                                }
                                crashDetailBean.O.put(str2, str);
                                x.a("add setted key %s value size:%d", str2, Integer.valueOf(str.length()));
                            }
                        }
                    }
                    x.a("[crash callback] start user's callback:onCrashHandleStart2GetExtraDatas()", new Object[0]);
                    if (this.f != null) {
                        x.c("Calling 'getCrashExtraData' of RQD crash listener.", new Object[0]);
                        bArr = this.f.a();
                    } else if (this.g != null) {
                        x.c("Calling 'onCrashHandleStart2GetExtraDatas' of Bugly crash listener.", new Object[0]);
                        bArr = this.g.onCrashHandleStart2GetExtraDatas(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    crashDetailBean.T = bArr;
                    if (bArr != null) {
                        if (bArr.length > 30000) {
                            x.d("extra bytes size %d is over limit %d will drop over part", Integer.valueOf(bArr.length), 30000);
                            crashDetailBean.T = Arrays.copyOf(bArr, 30000);
                        }
                        x.a("add extra bytes %d ", Integer.valueOf(bArr.length));
                    }
                } catch (Throwable th) {
                    x.d("crash handle callback something wrong! %s", th.getClass().getName());
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    private static ContentValues e(CrashDetailBean crashDetailBean) {
        if (crashDetailBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            long j = crashDetailBean.a;
            if (j > 0) {
                contentValues.put("_id", Long.valueOf(j));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.r));
            contentValues.put("_s1", crashDetailBean.u);
            int i = 1;
            contentValues.put("_up", Integer.valueOf(crashDetailBean.d ? 1 : 0));
            if (!crashDetailBean.j) {
                i = 0;
            }
            contentValues.put("_me", Integer.valueOf(i));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.l));
            contentValues.put("_dt", z.a((Parcelable) crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static CrashDetailBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            CrashDetailBean crashDetailBean = (CrashDetailBean) z.a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean != null) {
                crashDetailBean.a = j;
            }
            return crashDetailBean;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public final void d(CrashDetailBean crashDetailBean) {
        ContentValues e2;
        if (crashDetailBean != null && (e2 = e(crashDetailBean)) != null) {
            long a2 = p.a().a("t_cr", e2, (o) null, true);
            if (a2 >= 0) {
                x.c("insert %s success!", "t_cr");
                crashDetailBean.a = a2;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00fe A[Catch:{ all -> 0x0107 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0103 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.tencent.bugly.crashreport.crash.CrashDetailBean> b(java.util.List<com.tencent.bugly.crashreport.crash.a> r15) {
        /*
            r14 = this;
            r0 = 0
            if (r15 == 0) goto L_0x010e
            int r1 = r15.size()
            if (r1 != 0) goto L_0x000b
            goto L_0x010e
        L_0x000b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "_id in "
            r1.append(r2)
            java.lang.String r3 = "("
            r1.append(r3)
            java.util.Iterator r15 = r15.iterator()
        L_0x001e:
            boolean r4 = r15.hasNext()
            java.lang.String r5 = ","
            if (r4 == 0) goto L_0x0035
            java.lang.Object r4 = r15.next()
            com.tencent.bugly.crashreport.crash.a r4 = (com.tencent.bugly.crashreport.crash.a) r4
            long r6 = r4.a
            r1.append(r6)
            r1.append(r5)
            goto L_0x001e
        L_0x0035:
            java.lang.String r15 = r1.toString()
            boolean r15 = r15.contains(r5)
            r4 = 0
            if (r15 == 0) goto L_0x004e
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            int r6 = r1.lastIndexOf(r5)
            java.lang.String r1 = r1.substring(r4, r6)
            r15.<init>(r1)
            r1 = r15
        L_0x004e:
            java.lang.String r15 = ")"
            r1.append(r15)
            java.lang.String r9 = r1.toString()
            r1.setLength(r4)
            com.tencent.bugly.proguard.p r6 = com.tencent.bugly.proguard.p.a()     // Catch:{ all -> 0x00f6 }
            java.lang.String r7 = "t_cr"
            r8 = 0
            r10 = 0
            r11 = 0
            r12 = 1
            android.database.Cursor r6 = r6.a(r7, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x00f6 }
            if (r6 != 0) goto L_0x0072
            if (r6 == 0) goto L_0x0071
            r6.close()
        L_0x0071:
            return r0
        L_0x0072:
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ all -> 0x00f4 }
            r7.<init>()     // Catch:{ all -> 0x00f4 }
            r1.append(r2)     // Catch:{ all -> 0x00f4 }
            r1.append(r3)     // Catch:{ all -> 0x00f4 }
            r2 = r4
        L_0x007e:
            boolean r3 = r6.moveToNext()     // Catch:{ all -> 0x00f4 }
            if (r3 == 0) goto L_0x00ab
            com.tencent.bugly.crashreport.crash.CrashDetailBean r3 = a((android.database.Cursor) r6)     // Catch:{ all -> 0x00f4 }
            if (r3 == 0) goto L_0x008e
            r7.add(r3)     // Catch:{ all -> 0x00f4 }
            goto L_0x007e
        L_0x008e:
            java.lang.String r3 = "_id"
            int r3 = r6.getColumnIndex(r3)     // Catch:{ all -> 0x00a1 }
            long r8 = r6.getLong(r3)     // Catch:{ all -> 0x00a1 }
            r1.append(r8)     // Catch:{ all -> 0x00a1 }
            r1.append(r5)     // Catch:{ all -> 0x00a1 }
            int r2 = r2 + 1
            goto L_0x007e
        L_0x00a1:
            r3 = move-exception
            java.lang.String r3 = "unknown id!"
            java.lang.Object[] r8 = new java.lang.Object[r4]     // Catch:{ all -> 0x00f4 }
            com.tencent.bugly.proguard.x.d(r3, r8)     // Catch:{ all -> 0x00f4 }
            goto L_0x007e
        L_0x00ab:
            java.lang.String r3 = r1.toString()     // Catch:{ all -> 0x00f4 }
            boolean r3 = r3.contains(r5)     // Catch:{ all -> 0x00f4 }
            if (r3 == 0) goto L_0x00c3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f4 }
            int r5 = r1.lastIndexOf(r5)     // Catch:{ all -> 0x00f4 }
            java.lang.String r1 = r1.substring(r4, r5)     // Catch:{ all -> 0x00f4 }
            r3.<init>(r1)     // Catch:{ all -> 0x00f4 }
            r1 = r3
        L_0x00c3:
            r1.append(r15)     // Catch:{ all -> 0x00f4 }
            java.lang.String r10 = r1.toString()     // Catch:{ all -> 0x00f4 }
            if (r2 <= 0) goto L_0x00ee
            com.tencent.bugly.proguard.p r8 = com.tencent.bugly.proguard.p.a()     // Catch:{ all -> 0x00f4 }
            java.lang.String r9 = "t_cr"
            r11 = 0
            r12 = 0
            r13 = 1
            int r15 = r8.a((java.lang.String) r9, (java.lang.String) r10, (java.lang.String[]) r11, (com.tencent.bugly.proguard.o) r12, (boolean) r13)     // Catch:{ all -> 0x00f4 }
            java.lang.String r1 = "deleted %s illegal data %d"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00f4 }
            java.lang.String r3 = "t_cr"
            r2[r4] = r3     // Catch:{ all -> 0x00f4 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ all -> 0x00f4 }
            r3 = 1
            r2[r3] = r15     // Catch:{ all -> 0x00f4 }
            com.tencent.bugly.proguard.x.d(r1, r2)     // Catch:{ all -> 0x00f4 }
        L_0x00ee:
            r6.close()
            return r7
        L_0x00f4:
            r15 = move-exception
            goto L_0x00f8
        L_0x00f6:
            r15 = move-exception
            r6 = r0
        L_0x00f8:
            boolean r1 = com.tencent.bugly.proguard.x.a(r15)     // Catch:{ all -> 0x0107 }
            if (r1 != 0) goto L_0x0101
            r15.printStackTrace()     // Catch:{ all -> 0x0107 }
        L_0x0101:
            if (r6 == 0) goto L_0x0106
            r6.close()
        L_0x0106:
            return r0
        L_0x0107:
            r15 = move-exception
            if (r6 == 0) goto L_0x010d
            r6.close()
        L_0x010d:
            throw r15
        L_0x010e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.b(java.util.List):java.util.List");
    }

    private static a b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            a aVar = new a();
            aVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            aVar.b = cursor.getLong(cursor.getColumnIndex("_tm"));
            aVar.c = cursor.getString(cursor.getColumnIndex("_s1"));
            boolean z = false;
            aVar.d = cursor.getInt(cursor.getColumnIndex("_up")) == 1;
            if (cursor.getInt(cursor.getColumnIndex("_me")) == 1) {
                z = true;
            }
            aVar.e = z;
            aVar.f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return aVar;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.util.List<com.tencent.bugly.crashreport.crash.a>] */
    /* JADX WARNING: type inference failed for: r2v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00c6 A[Catch:{ all -> 0x00cf }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00cb A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.tencent.bugly.crashreport.crash.a> b() {
        /*
            r16 = this;
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 0
            java.lang.String r3 = "_id"
            java.lang.String r4 = "_tm"
            java.lang.String r5 = "_s1"
            java.lang.String r6 = "_up"
            java.lang.String r7 = "_me"
            java.lang.String r8 = "_uc"
            java.lang.String[] r11 = new java.lang.String[]{r3, r4, r5, r6, r7, r8}     // Catch:{ all -> 0x00bf }
            com.tencent.bugly.proguard.p r9 = com.tencent.bugly.proguard.p.a()     // Catch:{ all -> 0x00bf }
            java.lang.String r10 = "t_cr"
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 1
            android.database.Cursor r3 = r9.a(r10, r11, r12, r13, r14, r15)     // Catch:{ all -> 0x00bf }
            if (r3 != 0) goto L_0x002e
            if (r3 == 0) goto L_0x002d
            r3.close()
        L_0x002d:
            return r2
        L_0x002e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bc }
            r2.<init>()     // Catch:{ all -> 0x00bc }
            java.lang.String r0 = "_id in "
            r2.append(r0)     // Catch:{ all -> 0x00bc }
            java.lang.String r0 = "("
            r2.append(r0)     // Catch:{ all -> 0x00bc }
            r4 = 0
            r5 = r4
        L_0x003f:
            boolean r0 = r3.moveToNext()     // Catch:{ all -> 0x00bc }
            java.lang.String r6 = ","
            if (r0 == 0) goto L_0x006e
            com.tencent.bugly.crashreport.crash.a r0 = b((android.database.Cursor) r3)     // Catch:{ all -> 0x00bc }
            if (r0 == 0) goto L_0x0051
            r1.add(r0)     // Catch:{ all -> 0x00bc }
            goto L_0x003f
        L_0x0051:
            java.lang.String r0 = "_id"
            int r0 = r3.getColumnIndex(r0)     // Catch:{ all -> 0x0064 }
            long r7 = r3.getLong(r0)     // Catch:{ all -> 0x0064 }
            r2.append(r7)     // Catch:{ all -> 0x0064 }
            r2.append(r6)     // Catch:{ all -> 0x0064 }
            int r5 = r5 + 1
            goto L_0x003f
        L_0x0064:
            r0 = move-exception
            java.lang.String r0 = "unknown id!"
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ all -> 0x00bc }
            com.tencent.bugly.proguard.x.d(r0, r6)     // Catch:{ all -> 0x00bc }
            goto L_0x003f
        L_0x006e:
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x00bc }
            boolean r0 = r0.contains(r6)     // Catch:{ all -> 0x00bc }
            if (r0 == 0) goto L_0x0086
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bc }
            int r6 = r2.lastIndexOf(r6)     // Catch:{ all -> 0x00bc }
            java.lang.String r2 = r2.substring(r4, r6)     // Catch:{ all -> 0x00bc }
            r0.<init>(r2)     // Catch:{ all -> 0x00bc }
            r2 = r0
        L_0x0086:
            java.lang.String r0 = ")"
            r2.append(r0)     // Catch:{ all -> 0x00bc }
            java.lang.String r8 = r2.toString()     // Catch:{ all -> 0x00bc }
            r2.setLength(r4)     // Catch:{ all -> 0x00bc }
            if (r5 <= 0) goto L_0x00b6
            com.tencent.bugly.proguard.p r6 = com.tencent.bugly.proguard.p.a()     // Catch:{ all -> 0x00bc }
            java.lang.String r7 = "t_cr"
            r9 = 0
            r10 = 0
            r11 = 1
            int r0 = r6.a((java.lang.String) r7, (java.lang.String) r8, (java.lang.String[]) r9, (com.tencent.bugly.proguard.o) r10, (boolean) r11)     // Catch:{ all -> 0x00bc }
            java.lang.String r2 = "deleted %s illegal data %d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00bc }
            java.lang.String r6 = "t_cr"
            r5[r4] = r6     // Catch:{ all -> 0x00bc }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x00bc }
            r4 = 1
            r5[r4] = r0     // Catch:{ all -> 0x00bc }
            com.tencent.bugly.proguard.x.d(r2, r5)     // Catch:{ all -> 0x00bc }
        L_0x00b6:
            r3.close()
            return r1
        L_0x00bc:
            r0 = move-exception
            r2 = r3
            goto L_0x00c0
        L_0x00bf:
            r0 = move-exception
        L_0x00c0:
            boolean r3 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x00cf }
            if (r3 != 0) goto L_0x00c9
            r0.printStackTrace()     // Catch:{ all -> 0x00cf }
        L_0x00c9:
            if (r2 == 0) goto L_0x00ce
            r2.close()
        L_0x00ce:
            return r1
        L_0x00cf:
            r0 = move-exception
            if (r2 == 0) goto L_0x00d5
            r2.close()
        L_0x00d5:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.b():java.util.List");
    }

    private static void c(List<a> list) {
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("_id in ");
            sb.append("(");
            for (a aVar : list) {
                sb.append(aVar.a);
                sb.append(",");
            }
            StringBuilder sb2 = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
            sb2.append(")");
            String sb3 = sb2.toString();
            sb2.setLength(0);
            try {
                x.c("deleted %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", sb3, (String[]) null, (o) null, true)));
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static void d(List<CrashDetailBean> list) {
        String str;
        if (list != null) {
            try {
                if (list.size() != 0) {
                    StringBuilder sb = new StringBuilder();
                    for (CrashDetailBean crashDetailBean : list) {
                        sb.append(" or _id");
                        sb.append(" = ");
                        sb.append(crashDetailBean.a);
                    }
                    String sb2 = sb.toString();
                    if (sb2.length() > 0) {
                        str = sb2.substring(4);
                    } else {
                        str = sb2;
                    }
                    sb.setLength(0);
                    x.c("deleted %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", str, (String[]) null, (o) null, true)));
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static an a(Context context, CrashDetailBean crashDetailBean, com.tencent.bugly.crashreport.common.info.a aVar) {
        am a2;
        am a3;
        am amVar;
        boolean z = false;
        if (context == null || crashDetailBean == null || aVar == null) {
            x.d("enExp args == null", new Object[0]);
            return null;
        }
        an anVar = new an();
        int i = crashDetailBean.b;
        switch (i) {
            case 0:
                anVar.a = crashDetailBean.j ? "200" : "100";
                break;
            case 1:
                anVar.a = crashDetailBean.j ? "201" : "101";
                break;
            case 2:
                anVar.a = crashDetailBean.j ? "202" : "102";
                break;
            case 3:
                anVar.a = crashDetailBean.j ? "203" : "103";
                break;
            case 4:
                anVar.a = crashDetailBean.j ? "204" : "104";
                break;
            case 5:
                anVar.a = crashDetailBean.j ? "207" : "107";
                break;
            case 6:
                anVar.a = crashDetailBean.j ? "206" : "106";
                break;
            case 7:
                anVar.a = crashDetailBean.j ? "208" : "108";
                break;
            default:
                x.e("crash type error! %d", Integer.valueOf(i));
                break;
        }
        anVar.b = crashDetailBean.r;
        anVar.c = crashDetailBean.n;
        anVar.d = crashDetailBean.o;
        anVar.e = crashDetailBean.p;
        anVar.g = crashDetailBean.q;
        anVar.h = crashDetailBean.z;
        anVar.i = crashDetailBean.c;
        anVar.j = null;
        anVar.l = crashDetailBean.m;
        anVar.m = crashDetailBean.e;
        anVar.f = crashDetailBean.B;
        anVar.t = com.tencent.bugly.crashreport.common.info.a.b().i();
        anVar.n = null;
        Map<String, PlugInBean> map = crashDetailBean.i;
        if (map != null && map.size() > 0) {
            anVar.o = new ArrayList<>();
            for (Map.Entry next : crashDetailBean.i.entrySet()) {
                ak akVar = new ak();
                akVar.a = ((PlugInBean) next.getValue()).a;
                akVar.c = ((PlugInBean) next.getValue()).c;
                akVar.d = ((PlugInBean) next.getValue()).b;
                akVar.b = aVar.r();
                anVar.o.add(akVar);
            }
        }
        Map<String, PlugInBean> map2 = crashDetailBean.h;
        if (map2 != null && map2.size() > 0) {
            anVar.p = new ArrayList<>();
            for (Map.Entry next2 : crashDetailBean.h.entrySet()) {
                ak akVar2 = new ak();
                akVar2.a = ((PlugInBean) next2.getValue()).a;
                akVar2.c = ((PlugInBean) next2.getValue()).c;
                akVar2.d = ((PlugInBean) next2.getValue()).b;
                anVar.p.add(akVar2);
            }
        }
        if (crashDetailBean.j) {
            anVar.k = crashDetailBean.t;
            String str = crashDetailBean.s;
            if (str != null && str.length() > 0) {
                if (anVar.q == null) {
                    anVar.q = new ArrayList<>();
                }
                try {
                    anVar.q.add(new am((byte) 1, "alltimes.txt", crashDetailBean.s.getBytes("utf-8")));
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                    anVar.q = null;
                }
            }
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(anVar.k);
            ArrayList<am> arrayList = anVar.q;
            objArr[1] = Integer.valueOf(arrayList != null ? arrayList.size() : 0);
            x.c("crashcount:%d sz:%d", objArr);
        }
        if (crashDetailBean.w != null) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            try {
                anVar.q.add(new am((byte) 1, "log.txt", crashDetailBean.w.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e3) {
                e3.printStackTrace();
                anVar.q = null;
            }
        }
        if (crashDetailBean.x != null) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            try {
                anVar.q.add(new am((byte) 1, "jniLog.txt", crashDetailBean.x.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e4) {
                e4.printStackTrace();
                anVar.q = null;
            }
        }
        if (!z.a(crashDetailBean.U)) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            try {
                amVar = new am((byte) 1, "crashInfos.txt", crashDetailBean.U.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e5) {
                e5.printStackTrace();
                amVar = null;
            }
            if (amVar != null) {
                x.c("attach crash infos", new Object[0]);
                anVar.q.add(amVar);
            }
        }
        if (crashDetailBean.V != null) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            am a4 = a("backupRecord.zip", context, crashDetailBean.V);
            if (a4 != null) {
                x.c("attach backup record", new Object[0]);
                anVar.q.add(a4);
            }
        }
        byte[] bArr = crashDetailBean.y;
        if (bArr != null && bArr.length > 0) {
            am amVar2 = new am((byte) 2, "buglylog.zip", bArr);
            x.c("attach user log", new Object[0]);
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            anVar.q.add(amVar2);
        }
        if (crashDetailBean.b == 3) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            x.c("crashBean.userDatas:%s", crashDetailBean.O);
            Map<String, String> map3 = crashDetailBean.O;
            if (map3 != null && map3.containsKey("BUGLY_CR_01")) {
                try {
                    if (!TextUtils.isEmpty(crashDetailBean.O.get("BUGLY_CR_01"))) {
                        anVar.q.add(new am((byte) 1, "anrMessage.txt", crashDetailBean.O.get("BUGLY_CR_01").getBytes("utf-8")));
                        x.c("attach anr message", new Object[0]);
                    }
                } catch (UnsupportedEncodingException e6) {
                    e6.printStackTrace();
                    anVar.q = null;
                }
                crashDetailBean.O.remove("BUGLY_CR_01");
            }
            String str2 = crashDetailBean.v;
            if (!(str2 == null || (a3 = a("trace.zip", context, str2)) == null)) {
                x.c("attach traces", new Object[0]);
                anVar.q.add(a3);
            }
        }
        if (crashDetailBean.b == 1) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            String str3 = crashDetailBean.v;
            if (!(str3 == null || (a2 = a("tomb.zip", context, str3)) == null)) {
                x.c("attach tombs", new Object[0]);
                anVar.q.add(a2);
            }
        }
        List<String> list = aVar.C;
        if (list != null && !list.isEmpty()) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            StringBuilder sb = new StringBuilder();
            for (String append : aVar.C) {
                sb.append(append);
            }
            try {
                anVar.q.add(new am((byte) 1, "martianlog.txt", sb.toString().getBytes("utf-8")));
                x.c("attach pageTracingList", new Object[0]);
            } catch (UnsupportedEncodingException e7) {
                e7.printStackTrace();
            }
        }
        byte[] bArr2 = crashDetailBean.T;
        if (bArr2 != null && bArr2.length > 0) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            anVar.q.add(new am((byte) 1, "userExtraByteData", crashDetailBean.T));
            x.c("attach extraData", new Object[0]);
        }
        HashMap hashMap = new HashMap();
        anVar.r = hashMap;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(crashDetailBean.C);
        hashMap.put("A9", sb2.toString());
        Map<String, String> map4 = anVar.r;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(crashDetailBean.D);
        map4.put("A11", sb3.toString());
        Map<String, String> map5 = anVar.r;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(crashDetailBean.E);
        map5.put("A10", sb4.toString());
        anVar.r.put("A23", crashDetailBean.f);
        anVar.r.put("A7", aVar.f);
        anVar.r.put("A6", aVar.s());
        anVar.r.put("A5", aVar.r());
        anVar.r.put("A22", aVar.h());
        Map<String, String> map6 = anVar.r;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(crashDetailBean.G);
        map6.put("A2", sb5.toString());
        Map<String, String> map7 = anVar.r;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(crashDetailBean.F);
        map7.put("A1", sb6.toString());
        anVar.r.put("A24", aVar.h);
        Map<String, String> map8 = anVar.r;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(crashDetailBean.H);
        map8.put("A17", sb7.toString());
        anVar.r.put("A3", aVar.k());
        anVar.r.put("A16", aVar.m());
        anVar.r.put("A25", aVar.n());
        anVar.r.put("A14", aVar.l());
        anVar.r.put("A15", aVar.w());
        Map<String, String> map9 = anVar.r;
        StringBuilder sb8 = new StringBuilder();
        sb8.append(aVar.x());
        map9.put("A13", sb8.toString());
        anVar.r.put("A34", crashDetailBean.A);
        if (aVar.x != null) {
            anVar.r.put("productIdentify", aVar.x);
        }
        try {
            anVar.r.put("A26", URLEncoder.encode(crashDetailBean.I, "utf-8"));
        } catch (UnsupportedEncodingException e8) {
            e8.printStackTrace();
        }
        if (crashDetailBean.b == 1) {
            anVar.r.put("A27", crashDetailBean.K);
            anVar.r.put("A28", crashDetailBean.J);
            Map<String, String> map10 = anVar.r;
            StringBuilder sb9 = new StringBuilder();
            sb9.append(crashDetailBean.k);
            map10.put("A29", sb9.toString());
        }
        anVar.r.put("A30", crashDetailBean.L);
        Map<String, String> map11 = anVar.r;
        StringBuilder sb10 = new StringBuilder();
        sb10.append(crashDetailBean.M);
        map11.put("A18", sb10.toString());
        Map<String, String> map12 = anVar.r;
        StringBuilder sb11 = new StringBuilder();
        sb11.append(!crashDetailBean.N);
        map12.put("A36", sb11.toString());
        Map<String, String> map13 = anVar.r;
        StringBuilder sb12 = new StringBuilder();
        sb12.append(aVar.q);
        map13.put("F02", sb12.toString());
        Map<String, String> map14 = anVar.r;
        StringBuilder sb13 = new StringBuilder();
        sb13.append(aVar.r);
        map14.put("F03", sb13.toString());
        anVar.r.put("F04", aVar.e());
        Map<String, String> map15 = anVar.r;
        StringBuilder sb14 = new StringBuilder();
        sb14.append(aVar.s);
        map15.put("F05", sb14.toString());
        anVar.r.put("F06", aVar.p);
        anVar.r.put("F08", aVar.v);
        anVar.r.put("F09", aVar.w);
        Map<String, String> map16 = anVar.r;
        StringBuilder sb15 = new StringBuilder();
        sb15.append(aVar.t);
        map16.put("F10", sb15.toString());
        if (crashDetailBean.P >= 0) {
            Map<String, String> map17 = anVar.r;
            StringBuilder sb16 = new StringBuilder();
            sb16.append(crashDetailBean.P);
            map17.put("C01", sb16.toString());
        }
        if (crashDetailBean.Q >= 0) {
            Map<String, String> map18 = anVar.r;
            StringBuilder sb17 = new StringBuilder();
            sb17.append(crashDetailBean.Q);
            map18.put("C02", sb17.toString());
        }
        Map<String, String> map19 = crashDetailBean.R;
        if (map19 != null && map19.size() > 0) {
            for (Map.Entry next3 : crashDetailBean.R.entrySet()) {
                anVar.r.put("C03_" + ((String) next3.getKey()), next3.getValue());
            }
        }
        Map<String, String> map20 = crashDetailBean.S;
        if (map20 != null && map20.size() > 0) {
            for (Map.Entry next4 : crashDetailBean.S.entrySet()) {
                anVar.r.put("C04_" + ((String) next4.getKey()), next4.getValue());
            }
        }
        anVar.s = null;
        Map<String, String> map21 = crashDetailBean.O;
        if (map21 != null && map21.size() > 0) {
            Map<String, String> map22 = crashDetailBean.O;
            anVar.s = map22;
            x.a("setted message size %d", Integer.valueOf(map22.size()));
        }
        Object[] objArr2 = new Object[12];
        objArr2[0] = crashDetailBean.n;
        objArr2[1] = crashDetailBean.c;
        objArr2[2] = aVar.e();
        objArr2[3] = Long.valueOf((crashDetailBean.r - crashDetailBean.M) / 1000);
        objArr2[4] = Boolean.valueOf(crashDetailBean.k);
        objArr2[5] = Boolean.valueOf(crashDetailBean.N);
        objArr2[6] = Boolean.valueOf(crashDetailBean.j);
        if (crashDetailBean.b == 1) {
            z = true;
        }
        objArr2[7] = Boolean.valueOf(z);
        objArr2[8] = Integer.valueOf(crashDetailBean.t);
        objArr2[9] = crashDetailBean.s;
        objArr2[10] = Boolean.valueOf(crashDetailBean.d);
        objArr2[11] = Integer.valueOf(anVar.r.size());
        x.c("%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d", objArr2);
        return anVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0096 A[Catch:{ all -> 0x00b9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x009c A[SYNTHETIC, Splitter:B:35:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b0 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.tencent.bugly.proguard.am a(java.lang.String r6, android.content.Context r7, java.lang.String r8) {
        /*
            java.lang.String r0 = "del tmp"
            r1 = 0
            r2 = 0
            if (r8 == 0) goto L_0x00d9
            if (r7 != 0) goto L_0x000a
            goto L_0x00d9
        L_0x000a:
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r4[r2] = r8
            java.lang.String r5 = "zip %s"
            com.tencent.bugly.proguard.x.c(r5, r4)
            java.io.File r4 = new java.io.File
            r4.<init>(r8)
            java.io.File r8 = new java.io.File
            java.io.File r7 = r7.getCacheDir()
            r8.<init>(r7, r6)
            r6 = 5000(0x1388, float:7.006E-42)
            boolean r6 = com.tencent.bugly.proguard.z.a((java.io.File) r4, (java.io.File) r8, (int) r6)
            if (r6 != 0) goto L_0x0034
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.String r7 = "zip fail!"
            com.tencent.bugly.proguard.x.d(r7, r6)
            return r1
        L_0x0034:
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream
            r6.<init>()
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ all -> 0x008e }
            r7.<init>(r8)     // Catch:{ all -> 0x008e }
            r4 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x008c }
        L_0x0044:
            int r5 = r7.read(r4)     // Catch:{ all -> 0x008c }
            if (r5 <= 0) goto L_0x0051
            r6.write(r4, r2, r5)     // Catch:{ all -> 0x008c }
            r6.flush()     // Catch:{ all -> 0x008c }
            goto L_0x0044
        L_0x0051:
            byte[] r6 = r6.toByteArray()     // Catch:{ all -> 0x008c }
            java.lang.String r4 = "read bytes :%d"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x008c }
            int r5 = r6.length     // Catch:{ all -> 0x008c }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x008c }
            r3[r2] = r5     // Catch:{ all -> 0x008c }
            com.tencent.bugly.proguard.x.c(r4, r3)     // Catch:{ all -> 0x008c }
            com.tencent.bugly.proguard.am r3 = new com.tencent.bugly.proguard.am     // Catch:{ all -> 0x008c }
            r4 = 2
            java.lang.String r5 = r8.getName()     // Catch:{ all -> 0x008c }
            r3.<init>(r4, r5, r6)     // Catch:{ all -> 0x008c }
            r7.close()     // Catch:{ IOException -> 0x0073 }
            goto L_0x007d
        L_0x0073:
            r6 = move-exception
            boolean r7 = com.tencent.bugly.proguard.x.a(r6)
            if (r7 != 0) goto L_0x007d
            r6.printStackTrace()
        L_0x007d:
            boolean r6 = r8.exists()
            if (r6 == 0) goto L_0x008b
            java.lang.Object[] r6 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.x.c(r0, r6)
            r8.delete()
        L_0x008b:
            return r3
        L_0x008c:
            r6 = move-exception
            goto L_0x0090
        L_0x008e:
            r6 = move-exception
            r7 = r1
        L_0x0090:
            boolean r3 = com.tencent.bugly.proguard.x.a(r6)     // Catch:{ all -> 0x00b9 }
            if (r3 != 0) goto L_0x0099
            r6.printStackTrace()     // Catch:{ all -> 0x00b9 }
        L_0x0099:
            if (r7 == 0) goto L_0x00aa
            r7.close()     // Catch:{ IOException -> 0x00a0 }
            goto L_0x00aa
        L_0x00a0:
            r6 = move-exception
            boolean r7 = com.tencent.bugly.proguard.x.a(r6)
            if (r7 != 0) goto L_0x00aa
            r6.printStackTrace()
        L_0x00aa:
            boolean r6 = r8.exists()
            if (r6 == 0) goto L_0x00b8
            java.lang.Object[] r6 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.x.c(r0, r6)
            r8.delete()
        L_0x00b8:
            return r1
        L_0x00b9:
            r6 = move-exception
            if (r7 == 0) goto L_0x00ca
            r7.close()     // Catch:{ IOException -> 0x00c0 }
            goto L_0x00ca
        L_0x00c0:
            r7 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r7)
            if (r1 != 0) goto L_0x00ca
            r7.printStackTrace()
        L_0x00ca:
            boolean r7 = r8.exists()
            if (r7 == 0) goto L_0x00d8
            java.lang.Object[] r7 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.x.c(r0, r7)
            r8.delete()
        L_0x00d8:
            throw r6
        L_0x00d9:
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.String r7 = "rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}"
            com.tencent.bugly.proguard.x.d(r7, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.a(java.lang.String, android.content.Context, java.lang.String):com.tencent.bugly.proguard.am");
    }

    public static void a(String str, String str2, String str3, String str4, String str5, CrashDetailBean crashDetailBean) {
        String str6;
        com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
        if (b2 != null) {
            x.e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
            x.e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
            x.e("# PKG NAME: %s", b2.c);
            x.e("# APP VER: %s", b2.j);
            x.e("# LAUNCH TIME: %s", z.a(new Date(com.tencent.bugly.crashreport.common.info.a.b().a)));
            x.e("# CRASH TYPE: %s", str);
            x.e("# CRASH TIME: %s", str2);
            x.e("# CRASH PROCESS: %s", str3);
            x.e("# CRASH THREAD: %s", str4);
            if (crashDetailBean != null) {
                x.e("# REPORT ID: %s", crashDetailBean.c);
                Object[] objArr = new Object[2];
                objArr[0] = b2.g;
                objArr[1] = b2.x().booleanValue() ? "ROOTED" : "UNROOT";
                x.e("# CRASH DEVICE: %s %s", objArr);
                x.e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.C), Long.valueOf(crashDetailBean.D), Long.valueOf(crashDetailBean.E));
                x.e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.F), Long.valueOf(crashDetailBean.G), Long.valueOf(crashDetailBean.H));
                if (!z.a(crashDetailBean.K)) {
                    x.e("# EXCEPTION FIRED BY %s %s", crashDetailBean.K, crashDetailBean.J);
                } else if (crashDetailBean.b == 3) {
                    Object[] objArr2 = new Object[1];
                    if (crashDetailBean.O == null) {
                        str6 = BuildConfig.TRAVIS;
                    } else {
                        str6 = crashDetailBean.O.get("BUGLY_CR_01");
                    }
                    objArr2[0] = str6;
                    x.e("# EXCEPTION ANR MESSAGE:\n %s", objArr2);
                }
            }
            if (!z.a(str5)) {
                x.e("# CRASH STACK: ", new Object[0]);
                x.e(str5, new Object[0]);
            }
            x.e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
        }
    }
}
