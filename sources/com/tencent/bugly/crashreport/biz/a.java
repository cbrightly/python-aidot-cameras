package com.tencent.bugly.crashreport.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.au;
import com.tencent.bugly.proguard.k;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.t;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: BUGLY */
public final class a {
    private Context a;
    /* access modifiers changed from: private */
    public long b;
    private int c;
    private boolean d = true;

    static /* synthetic */ void a(a aVar, UserInfoBean userInfoBean, boolean z) {
        List<UserInfoBean> a2;
        if (userInfoBean == null) {
            return;
        }
        if (z || userInfoBean.b == 1 || (a2 = aVar.a(com.tencent.bugly.crashreport.common.info.a.a(aVar.a).d)) == null || a2.size() < 20) {
            long a3 = p.a().a("t_ui", a(userInfoBean), (o) null, true);
            if (a3 >= 0) {
                x.c("[Database] insert %s success with ID: %d", "t_ui", Long.valueOf(a3));
                userInfoBean.a = a3;
                return;
            }
            return;
        }
        x.a("[UserInfo] There are too many user info in local: %d", Integer.valueOf(a2.size()));
    }

    public a(Context context, boolean z) {
        this.a = context;
        this.d = z;
    }

    public final void a(int i, boolean z, long j) {
        com.tencent.bugly.crashreport.common.strategy.a a2 = com.tencent.bugly.crashreport.common.strategy.a.a();
        int i2 = 0;
        if (a2 == null || a2.c().h || i == 1 || i == 3) {
            if (i == 1 || i == 3) {
                this.c++;
            }
            com.tencent.bugly.crashreport.common.info.a a3 = com.tencent.bugly.crashreport.common.info.a.a(this.a);
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.b = i;
            userInfoBean.c = a3.d;
            userInfoBean.d = a3.g();
            userInfoBean.e = System.currentTimeMillis();
            userInfoBean.f = -1;
            userInfoBean.n = a3.j;
            if (i == 1) {
                i2 = 1;
            }
            userInfoBean.o = i2;
            userInfoBean.l = a3.a();
            userInfoBean.m = a3.p;
            userInfoBean.g = a3.q;
            userInfoBean.h = a3.r;
            userInfoBean.i = a3.s;
            userInfoBean.k = a3.t;
            userInfoBean.r = a3.B();
            userInfoBean.s = a3.G();
            userInfoBean.p = a3.H();
            userInfoBean.q = a3.I();
            w.a().a(new C0221a(userInfoBean, z), 0);
            return;
        }
        x.e("UserInfo is disable", new Object[0]);
    }

    public final void a() {
        this.b = z.b() + CostTimeUtil.DAY;
        w.a().a(new b(), (this.b - System.currentTimeMillis()) + KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
    }

    /* renamed from: com.tencent.bugly.crashreport.biz.a$a  reason: collision with other inner class name */
    /* compiled from: BUGLY */
    public final class C0221a implements Runnable {
        private boolean a;
        private UserInfoBean b;

        public C0221a(UserInfoBean userInfoBean, boolean z) {
            this.b = userInfoBean;
            this.a = z;
        }

        public final void run() {
            com.tencent.bugly.crashreport.common.info.a b2;
            try {
                UserInfoBean userInfoBean = this.b;
                if (userInfoBean != null) {
                    if (!(userInfoBean == null || (b2 = com.tencent.bugly.crashreport.common.info.a.b()) == null)) {
                        userInfoBean.j = b2.e();
                    }
                    x.c("[UserInfo] Record user info.", new Object[0]);
                    a.a(a.this, this.b, false);
                }
                if (this.a) {
                    a aVar = a.this;
                    w a2 = w.a();
                    if (a2 != null) {
                        a2.a(new Runnable() {
                            public final void run() {
                                try {
                                    a.this.c();
                                } catch (Throwable th) {
                                    x.a(th);
                                }
                            }
                        });
                    }
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void c() {
        boolean z;
        int i;
        if (this.d) {
            u a2 = u.a();
            if (a2 != null) {
                com.tencent.bugly.crashreport.common.strategy.a a3 = com.tencent.bugly.crashreport.common.strategy.a.a();
                if (a3 != null) {
                    if (!a3.b() || a2.b(1001)) {
                        String str = com.tencent.bugly.crashreport.common.info.a.a(this.a).d;
                        ArrayList arrayList = new ArrayList();
                        final List a4 = a(str);
                        if (a4 != null) {
                            int size = a4.size() - 20;
                            if (size > 0) {
                                int i2 = 0;
                                while (i2 < a4.size() - 1) {
                                    int i3 = i2 + 1;
                                    for (int i4 = i3; i4 < a4.size(); i4++) {
                                        if (((UserInfoBean) a4.get(i2)).e > ((UserInfoBean) a4.get(i4)).e) {
                                            a4.set(i2, a4.get(i4));
                                            a4.set(i4, (UserInfoBean) a4.get(i2));
                                        }
                                    }
                                    i2 = i3;
                                }
                                for (int i5 = 0; i5 < size; i5++) {
                                    arrayList.add(a4.get(i5));
                                }
                            }
                            Iterator it = a4.iterator();
                            int i6 = 0;
                            while (it.hasNext()) {
                                UserInfoBean userInfoBean = (UserInfoBean) it.next();
                                if (userInfoBean.f != -1) {
                                    it.remove();
                                    if (userInfoBean.e < z.b()) {
                                        arrayList.add(userInfoBean);
                                    }
                                }
                                if (userInfoBean.e > System.currentTimeMillis() - 600000 && ((i = userInfoBean.b) == 1 || i == 4 || i == 3)) {
                                    i6++;
                                }
                            }
                            if (i6 > 15) {
                                x.d("[UserInfo] Upload user info too many times in 10 min: %d", Integer.valueOf(i6));
                                z = false;
                            } else {
                                z = true;
                            }
                        } else {
                            a4 = new ArrayList();
                            z = true;
                        }
                        if (arrayList.size() > 0) {
                            a((List<UserInfoBean>) arrayList);
                        }
                        if (z) {
                            if (a4.size() != 0) {
                                x.c("[UserInfo] Upload user info(size: %d)", Integer.valueOf(a4.size()));
                                au a5 = com.tencent.bugly.proguard.a.a((List<UserInfoBean>) a4, this.c == 1 ? 1 : 2);
                                if (a5 == null) {
                                    x.d("[UserInfo] Failed to create UserInfoPackage.", new Object[0]);
                                    return;
                                }
                                byte[] a6 = com.tencent.bugly.proguard.a.a((k) a5);
                                if (a6 == null) {
                                    x.d("[UserInfo] Failed to encode data.", new Object[0]);
                                    return;
                                }
                                ap a7 = com.tencent.bugly.proguard.a.a(this.a, a2.a ? 840 : 640, a6);
                                if (a7 == null) {
                                    x.d("[UserInfo] Request package is null.", new Object[0]);
                                    return;
                                }
                                AnonymousClass1 r12 = new t() {
                                    public final void a(boolean z) {
                                        if (z) {
                                            x.c("[UserInfo] Successfully uploaded user info.", new Object[0]);
                                            long currentTimeMillis = System.currentTimeMillis();
                                            for (UserInfoBean userInfoBean : a4) {
                                                userInfoBean.f = currentTimeMillis;
                                                a.a(a.this, userInfoBean, true);
                                            }
                                        }
                                    }
                                };
                                StrategyBean c2 = com.tencent.bugly.crashreport.common.strategy.a.a().c();
                                boolean z2 = a2.a;
                                u.a().a(1001, a7, z2 ? c2.r : c2.t, z2 ? StrategyBean.b : StrategyBean.a, r12, this.c == 1);
                                return;
                            }
                        }
                        x.c("[UserInfo] There is no user info in local database.", new Object[0]);
                    }
                }
            }
        }
    }

    public final void b() {
        w a2 = w.a();
        if (a2 != null) {
            a2.a(new Runnable() {
                public final void run() {
                    try {
                        a.this.c();
                    } catch (Throwable th) {
                        x.a(th);
                    }
                }
            });
        }
    }

    /* compiled from: BUGLY */
    public final class b implements Runnable {
        b() {
        }

        public final void run() {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < a.this.b) {
                w.a().a(new b(), (a.this.b - currentTimeMillis) + KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
                return;
            }
            a.this.a(3, false, 0);
            a.this.a();
        }
    }

    /* compiled from: BUGLY */
    public final class c implements Runnable {
        private long a = 21600000;

        public c(long j) {
            this.a = j;
        }

        public final void run() {
            a aVar = a.this;
            w a2 = w.a();
            if (a2 != null) {
                a2.a(new Runnable() {
                    public final void run() {
                        try {
                            a.this.c();
                        } catch (Throwable th) {
                            x.a(th);
                        }
                    }
                });
            }
            a aVar2 = a.this;
            long j = this.a;
            w.a().a(new c(j), j);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b2 A[Catch:{ all -> 0x00bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b7 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean> a(java.lang.String r12) {
        /*
            r11 = this;
            r0 = 0
            boolean r1 = com.tencent.bugly.proguard.z.a((java.lang.String) r12)     // Catch:{ all -> 0x00aa }
            if (r1 == 0) goto L_0x000a
            r4 = r0
            goto L_0x001e
        L_0x000a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00aa }
            java.lang.String r2 = "_pc = '"
            r1.<init>(r2)     // Catch:{ all -> 0x00aa }
            r1.append(r12)     // Catch:{ all -> 0x00aa }
            java.lang.String r12 = "'"
            r1.append(r12)     // Catch:{ all -> 0x00aa }
            java.lang.String r12 = r1.toString()     // Catch:{ all -> 0x00aa }
            r4 = r12
        L_0x001e:
            com.tencent.bugly.proguard.p r1 = com.tencent.bugly.proguard.p.a()     // Catch:{ all -> 0x00aa }
            java.lang.String r2 = "t_ui"
            r3 = 0
            r5 = 0
            r6 = 0
            r7 = 1
            android.database.Cursor r12 = r1.a(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00aa }
            if (r12 != 0) goto L_0x0035
            if (r12 == 0) goto L_0x0034
            r12.close()
        L_0x0034:
            return r0
        L_0x0035:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a8 }
            r1.<init>()     // Catch:{ all -> 0x00a8 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x00a8 }
            r2.<init>()     // Catch:{ all -> 0x00a8 }
        L_0x003f:
            boolean r3 = r12.moveToNext()     // Catch:{ all -> 0x00a8 }
            r4 = 0
            if (r3 == 0) goto L_0x0071
            com.tencent.bugly.crashreport.biz.UserInfoBean r3 = a((android.database.Cursor) r12)     // Catch:{ all -> 0x00a8 }
            if (r3 == 0) goto L_0x0050
            r2.add(r3)     // Catch:{ all -> 0x00a8 }
            goto L_0x003f
        L_0x0050:
            java.lang.String r3 = "_id"
            int r3 = r12.getColumnIndex(r3)     // Catch:{ all -> 0x0068 }
            long r5 = r12.getLong(r3)     // Catch:{ all -> 0x0068 }
            java.lang.String r3 = " or _id"
            r1.append(r3)     // Catch:{ all -> 0x0068 }
            java.lang.String r3 = " = "
            r1.append(r3)     // Catch:{ all -> 0x0068 }
            r1.append(r5)     // Catch:{ all -> 0x0068 }
            goto L_0x003f
        L_0x0068:
            r3 = move-exception
            java.lang.String r3 = "[Database] unknown id."
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00a8 }
            com.tencent.bugly.proguard.x.d(r3, r4)     // Catch:{ all -> 0x00a8 }
            goto L_0x003f
        L_0x0071:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00a8 }
            int r3 = r1.length()     // Catch:{ all -> 0x00a8 }
            if (r3 <= 0) goto L_0x00a2
            r3 = 4
            java.lang.String r7 = r1.substring(r3)     // Catch:{ all -> 0x00a8 }
            com.tencent.bugly.proguard.p r5 = com.tencent.bugly.proguard.p.a()     // Catch:{ all -> 0x00a8 }
            java.lang.String r6 = "t_ui"
            r8 = 0
            r9 = 0
            r10 = 1
            int r1 = r5.a((java.lang.String) r6, (java.lang.String) r7, (java.lang.String[]) r8, (com.tencent.bugly.proguard.o) r9, (boolean) r10)     // Catch:{ all -> 0x00a8 }
            java.lang.String r3 = "[Database] deleted %s error data %d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00a8 }
            java.lang.String r6 = "t_ui"
            r5[r4] = r6     // Catch:{ all -> 0x00a8 }
            r4 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x00a8 }
            r5[r4] = r1     // Catch:{ all -> 0x00a8 }
            com.tencent.bugly.proguard.x.d(r3, r5)     // Catch:{ all -> 0x00a8 }
        L_0x00a2:
            r12.close()
            return r2
        L_0x00a8:
            r1 = move-exception
            goto L_0x00ac
        L_0x00aa:
            r1 = move-exception
            r12 = r0
        L_0x00ac:
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)     // Catch:{ all -> 0x00bb }
            if (r2 != 0) goto L_0x00b5
            r1.printStackTrace()     // Catch:{ all -> 0x00bb }
        L_0x00b5:
            if (r12 == 0) goto L_0x00ba
            r12.close()
        L_0x00ba:
            return r0
        L_0x00bb:
            r0 = move-exception
            if (r12 == 0) goto L_0x00c1
            r12.close()
        L_0x00c1:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.biz.a.a(java.lang.String):java.util.List");
    }

    private static void a(List<UserInfoBean> list) {
        String str;
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < list.size() && i < 50) {
                sb.append(" or _id");
                sb.append(" = ");
                sb.append(list.get(i).a);
                i++;
            }
            String sb2 = sb.toString();
            if (sb2.length() > 0) {
                str = sb2.substring(4);
            } else {
                str = sb2;
            }
            sb.setLength(0);
            try {
                x.c("[Database] deleted %s data %d", "t_ui", Integer.valueOf(p.a().a("t_ui", str, (String[]) null, (o) null, true)));
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static ContentValues a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            long j = userInfoBean.a;
            if (j > 0) {
                contentValues.put("_id", Long.valueOf(j));
            }
            contentValues.put("_tm", Long.valueOf(userInfoBean.e));
            contentValues.put("_ut", Long.valueOf(userInfoBean.f));
            contentValues.put("_tp", Integer.valueOf(userInfoBean.b));
            contentValues.put("_pc", userInfoBean.c);
            contentValues.put("_dt", z.a((Parcelable) userInfoBean));
            return contentValues;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static UserInfoBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            UserInfoBean userInfoBean = (UserInfoBean) z.a(blob, UserInfoBean.CREATOR);
            if (userInfoBean != null) {
                userInfoBean.a = j;
            }
            return userInfoBean;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
