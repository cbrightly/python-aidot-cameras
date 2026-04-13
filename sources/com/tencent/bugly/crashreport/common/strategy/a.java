package com.tencent.bugly.crashreport.common.strategy;

import android.content.Context;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.biz.b;
import com.tencent.bugly.proguard.ar;
import com.tencent.bugly.proguard.as;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.List;
import java.util.Map;
import meshsdk.model.json.RoutineRule;

/* compiled from: BUGLY */
public final class a {
    public static int a = 1000;
    private static a b = null;
    /* access modifiers changed from: private */
    public static String h = null;
    private final List<com.tencent.bugly.a> c;
    private final w d;
    private final StrategyBean e;
    /* access modifiers changed from: private */
    public StrategyBean f = null;
    /* access modifiers changed from: private */
    public Context g;

    private a(Context context, List<com.tencent.bugly.a> list) {
        this.g = context;
        this.e = new StrategyBean();
        this.c = list;
        this.d = w.a();
    }

    public static synchronized a a(Context context, List<com.tencent.bugly.a> list) {
        a aVar;
        synchronized (a.class) {
            if (b == null) {
                b = new a(context, list);
            }
            aVar = b;
        }
        return aVar;
    }

    public final void a(long j) {
        this.d.a(new Thread() {
            public final void run() {
                try {
                    Map<String, byte[]> a2 = p.a().a(a.a, (o) null, true);
                    if (a2 != null) {
                        byte[] bArr = a2.get(RoutineRule.THEN_TYPE_DEVICE);
                        byte[] bArr2 = a2.get("gateway");
                        if (bArr != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(a.this.g).e(new String(bArr));
                        }
                        if (bArr2 != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(a.this.g).d(new String(bArr2));
                        }
                    }
                    StrategyBean unused = a.this.f = a.d();
                    if (a.this.f != null && !z.a(a.h) && z.c(a.h)) {
                        a.this.f.r = a.h;
                        a.this.f.s = a.h;
                    }
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
                a aVar = a.this;
                aVar.a(aVar.f, false);
            }
        }, j);
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            aVar = b;
        }
        return aVar;
    }

    public final synchronized boolean b() {
        return this.f != null;
    }

    public final StrategyBean c() {
        StrategyBean strategyBean = this.f;
        if (strategyBean != null) {
            return strategyBean;
        }
        return this.e;
    }

    /* access modifiers changed from: protected */
    public final void a(StrategyBean strategyBean, boolean z) {
        x.c("[Strategy] Notify %s", b.class.getName());
        b.a(strategyBean, z);
        for (com.tencent.bugly.a next : this.c) {
            try {
                x.c("[Strategy] Notify %s", next.getClass().getName());
                next.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    public static void a(String str) {
        if (z.a(str) || !z.c(str)) {
            x.d("URL user set is invalid.", new Object[0]);
        } else {
            h = str;
        }
    }

    public final void a(as asVar) {
        if (asVar != null) {
            StrategyBean strategyBean = this.f;
            if (strategyBean == null || asVar.h != strategyBean.p) {
                StrategyBean strategyBean2 = new StrategyBean();
                strategyBean2.g = asVar.a;
                strategyBean2.i = asVar.c;
                strategyBean2.h = asVar.b;
                if (z.a(h) || !z.c(h)) {
                    if (z.c(asVar.d)) {
                        x.c("[Strategy] Upload url changes to %s", asVar.d);
                        strategyBean2.r = asVar.d;
                    }
                    if (z.c(asVar.e)) {
                        x.c("[Strategy] Exception upload url changes to %s", asVar.e);
                        strategyBean2.s = asVar.e;
                    }
                }
                ar arVar = asVar.f;
                if (arVar != null && !z.a(arVar.a)) {
                    strategyBean2.u = asVar.f.a;
                }
                long j = asVar.h;
                if (j != 0) {
                    strategyBean2.p = j;
                }
                Map<String, String> map = asVar.g;
                if (map != null && map.size() > 0) {
                    Map<String, String> map2 = asVar.g;
                    strategyBean2.v = map2;
                    String str = map2.get("B11");
                    if (str == null || !str.equals("1")) {
                        strategyBean2.j = false;
                    } else {
                        strategyBean2.j = true;
                    }
                    String str2 = asVar.g.get("B3");
                    if (str2 != null) {
                        strategyBean2.y = Long.valueOf(str2).longValue();
                    }
                    int i = asVar.i;
                    strategyBean2.q = (long) i;
                    strategyBean2.x = (long) i;
                    String str3 = asVar.g.get("B27");
                    if (str3 != null && str3.length() > 0) {
                        try {
                            int parseInt = Integer.parseInt(str3);
                            if (parseInt > 0) {
                                strategyBean2.w = parseInt;
                            }
                        } catch (Exception e2) {
                            if (!x.a(e2)) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    String str4 = asVar.g.get("B25");
                    if (str4 == null || !str4.equals("1")) {
                        strategyBean2.l = false;
                    } else {
                        strategyBean2.l = true;
                    }
                }
                x.a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean2.g), Boolean.valueOf(strategyBean2.i), Boolean.valueOf(strategyBean2.h), Boolean.valueOf(strategyBean2.j), Boolean.valueOf(strategyBean2.k), Boolean.valueOf(strategyBean2.n), Boolean.valueOf(strategyBean2.o), Long.valueOf(strategyBean2.q), Boolean.valueOf(strategyBean2.l), Long.valueOf(strategyBean2.p));
                this.f = strategyBean2;
                p.a().b(2);
                r rVar = new r();
                rVar.b = 2;
                rVar.a = strategyBean2.e;
                rVar.e = strategyBean2.f;
                rVar.g = z.a((Parcelable) strategyBean2);
                p.a().a(rVar);
                a(strategyBean2, true);
            }
        }
    }

    public static StrategyBean d() {
        byte[] bArr;
        List<r> a2 = p.a().a(2);
        if (a2 == null || a2.size() <= 0 || (bArr = a2.get(0).g) == null) {
            return null;
        }
        return (StrategyBean) z.a(bArr, StrategyBean.CREATOR);
    }
}
