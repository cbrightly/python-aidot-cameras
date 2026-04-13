package com.alibaba.android.arouter.core;

import android.content.Context;
import com.alibaba.android.arouter.facade.enums.b;
import com.alibaba.android.arouter.utils.e;
import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: LogisticsCenter */
public class a {
    private static Context a;
    static ThreadPoolExecutor b;
    private static boolean c;

    private static void e() {
        c = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00b5 A[Catch:{ Exception -> 0x0191 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void d(android.content.Context r10, java.util.concurrent.ThreadPoolExecutor r11) {
        /*
            java.lang.Class<com.alibaba.android.arouter.core.a> r0 = com.alibaba.android.arouter.core.a.class
            monitor-enter(r0)
            a = r10     // Catch:{ all -> 0x01b2 }
            b = r11     // Catch:{ all -> 0x01b2 }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0191 }
            e()     // Catch:{ Exception -> 0x0191 }
            boolean r3 = c     // Catch:{ Exception -> 0x0191 }
            r4 = 0
            if (r3 == 0) goto L_0x001e
            com.alibaba.android.arouter.facade.template.b r3 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "ARouter::"
            java.lang.String r6 = "Load router map by arouter-auto-register plugin."
            r3.c(r5, r6)     // Catch:{ Exception -> 0x0191 }
            goto L_0x011b
        L_0x001e:
            boolean r3 = com.alibaba.android.arouter.launcher.a.b()     // Catch:{ Exception -> 0x0191 }
            if (r3 != 0) goto L_0x004b
            boolean r3 = com.alibaba.android.arouter.utils.d.b(r10)     // Catch:{ Exception -> 0x0191 }
            if (r3 == 0) goto L_0x002b
            goto L_0x004b
        L_0x002b:
            com.alibaba.android.arouter.facade.template.b r3 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "ARouter::"
            java.lang.String r6 = "Load router map from cache."
            r3.c(r5, r6)     // Catch:{ Exception -> 0x0191 }
            java.util.HashSet r3 = new java.util.HashSet     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "SP_AROUTER_CACHE"
            android.content.SharedPreferences r5 = r10.getSharedPreferences(r5, r4)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = "ROUTER_MAP"
            java.util.HashSet r7 = new java.util.HashSet     // Catch:{ Exception -> 0x0191 }
            r7.<init>()     // Catch:{ Exception -> 0x0191 }
            java.util.Set r5 = r5.getStringSet(r6, r7)     // Catch:{ Exception -> 0x0191 }
            r3.<init>(r5)     // Catch:{ Exception -> 0x0191 }
            goto L_0x0078
        L_0x004b:
            com.alibaba.android.arouter.facade.template.b r3 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "ARouter::"
            java.lang.String r6 = "Run with debug mode or new install, rebuild router map."
            r3.c(r5, r6)     // Catch:{ Exception -> 0x0191 }
            android.content.Context r3 = a     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "com.alibaba.android.arouter.routes"
            java.util.Set r3 = com.alibaba.android.arouter.utils.a.a(r3, r5)     // Catch:{ Exception -> 0x0191 }
            boolean r5 = r3.isEmpty()     // Catch:{ Exception -> 0x0191 }
            if (r5 != 0) goto L_0x0075
            java.lang.String r5 = "SP_AROUTER_CACHE"
            android.content.SharedPreferences r5 = r10.getSharedPreferences(r5, r4)     // Catch:{ Exception -> 0x0191 }
            android.content.SharedPreferences$Editor r5 = r5.edit()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = "ROUTER_MAP"
            android.content.SharedPreferences$Editor r5 = r5.putStringSet(r6, r3)     // Catch:{ Exception -> 0x0191 }
            r5.apply()     // Catch:{ Exception -> 0x0191 }
        L_0x0075:
            com.alibaba.android.arouter.utils.d.c(r10)     // Catch:{ Exception -> 0x0191 }
        L_0x0078:
            com.alibaba.android.arouter.facade.template.b r5 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = "ARouter::"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0191 }
            r7.<init>()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r8 = "Find router map finished, map size = "
            r7.append(r8)     // Catch:{ Exception -> 0x0191 }
            int r8 = r3.size()     // Catch:{ Exception -> 0x0191 }
            r7.append(r8)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r8 = ", cost "
            r7.append(r8)     // Catch:{ Exception -> 0x0191 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0191 }
            long r8 = r8 - r1
            r7.append(r8)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r8 = " ms."
            r7.append(r8)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0191 }
            r5.c(r6, r7)     // Catch:{ Exception -> 0x0191 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0191 }
            r1 = r5
            java.util.Iterator r5 = r3.iterator()     // Catch:{ Exception -> 0x0191 }
        L_0x00af:
            boolean r6 = r5.hasNext()     // Catch:{ Exception -> 0x0191 }
            if (r6 == 0) goto L_0x011b
            java.lang.Object r6 = r5.next()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0191 }
            java.lang.String r7 = "com.alibaba.android.arouter.routes.ARouter$$Root"
            boolean r7 = r6.startsWith(r7)     // Catch:{ Exception -> 0x0191 }
            if (r7 == 0) goto L_0x00db
            java.lang.Class r7 = java.lang.Class.forName(r6)     // Catch:{ Exception -> 0x0191 }
            java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch:{ Exception -> 0x0191 }
            java.lang.reflect.Constructor r7 = r7.getConstructor(r8)     // Catch:{ Exception -> 0x0191 }
            java.lang.Object[] r8 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0191 }
            java.lang.Object r7 = r7.newInstance(r8)     // Catch:{ Exception -> 0x0191 }
            com.alibaba.android.arouter.facade.template.f r7 = (com.alibaba.android.arouter.facade.template.f) r7     // Catch:{ Exception -> 0x0191 }
            java.util.Map<java.lang.String, java.lang.Class<? extends com.alibaba.android.arouter.facade.template.e>> r8 = com.alibaba.android.arouter.core.b.a     // Catch:{ Exception -> 0x0191 }
            r7.loadInto(r8)     // Catch:{ Exception -> 0x0191 }
            goto L_0x011a
        L_0x00db:
            java.lang.String r7 = "com.alibaba.android.arouter.routes.ARouter$$Interceptors"
            boolean r7 = r6.startsWith(r7)     // Catch:{ Exception -> 0x0191 }
            if (r7 == 0) goto L_0x00fb
            java.lang.Class r7 = java.lang.Class.forName(r6)     // Catch:{ Exception -> 0x0191 }
            java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch:{ Exception -> 0x0191 }
            java.lang.reflect.Constructor r7 = r7.getConstructor(r8)     // Catch:{ Exception -> 0x0191 }
            java.lang.Object[] r8 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0191 }
            java.lang.Object r7 = r7.newInstance(r8)     // Catch:{ Exception -> 0x0191 }
            com.alibaba.android.arouter.facade.template.a r7 = (com.alibaba.android.arouter.facade.template.a) r7     // Catch:{ Exception -> 0x0191 }
            java.util.Map<java.lang.Integer, java.lang.Class<? extends com.alibaba.android.arouter.facade.template.IInterceptor>> r8 = com.alibaba.android.arouter.core.b.e     // Catch:{ Exception -> 0x0191 }
            r7.loadInto(r8)     // Catch:{ Exception -> 0x0191 }
            goto L_0x011a
        L_0x00fb:
            java.lang.String r7 = "com.alibaba.android.arouter.routes.ARouter$$Providers"
            boolean r7 = r6.startsWith(r7)     // Catch:{ Exception -> 0x0191 }
            if (r7 == 0) goto L_0x011a
            java.lang.Class r7 = java.lang.Class.forName(r6)     // Catch:{ Exception -> 0x0191 }
            java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch:{ Exception -> 0x0191 }
            java.lang.reflect.Constructor r7 = r7.getConstructor(r8)     // Catch:{ Exception -> 0x0191 }
            java.lang.Object[] r8 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0191 }
            java.lang.Object r7 = r7.newInstance(r8)     // Catch:{ Exception -> 0x0191 }
            com.alibaba.android.arouter.facade.template.d r7 = (com.alibaba.android.arouter.facade.template.d) r7     // Catch:{ Exception -> 0x0191 }
            java.util.Map<java.lang.String, com.alibaba.android.arouter.facade.model.a> r8 = com.alibaba.android.arouter.core.b.d     // Catch:{ Exception -> 0x0191 }
            r7.loadInto(r8)     // Catch:{ Exception -> 0x0191 }
        L_0x011a:
            goto L_0x00af
        L_0x011b:
            com.alibaba.android.arouter.facade.template.b r3 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "ARouter::"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0191 }
            r6.<init>()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r7 = "Load root element finished, cost "
            r6.append(r7)     // Catch:{ Exception -> 0x0191 }
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0191 }
            long r7 = r7 - r1
            r6.append(r7)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r7 = " ms."
            r6.append(r7)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0191 }
            r3.c(r5, r6)     // Catch:{ Exception -> 0x0191 }
            java.util.Map<java.lang.String, java.lang.Class<? extends com.alibaba.android.arouter.facade.template.e>> r3 = com.alibaba.android.arouter.core.b.a     // Catch:{ Exception -> 0x0191 }
            int r3 = r3.size()     // Catch:{ Exception -> 0x0191 }
            if (r3 != 0) goto L_0x014e
            com.alibaba.android.arouter.facade.template.b r3 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "ARouter::"
            java.lang.String r6 = "No mapping files were found, check your configuration please!"
            r3.d(r5, r6)     // Catch:{ Exception -> 0x0191 }
        L_0x014e:
            boolean r3 = com.alibaba.android.arouter.launcher.a.b()     // Catch:{ Exception -> 0x0191 }
            if (r3 == 0) goto L_0x018e
            com.alibaba.android.arouter.facade.template.b r3 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "ARouter::"
            java.util.Locale r6 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r7 = "LogisticsCenter has already been loaded, GroupIndex[%d], InterceptorIndex[%d], ProviderIndex[%d]"
            r8 = 3
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0191 }
            java.util.Map<java.lang.String, java.lang.Class<? extends com.alibaba.android.arouter.facade.template.e>> r9 = com.alibaba.android.arouter.core.b.a     // Catch:{ Exception -> 0x0191 }
            int r9 = r9.size()     // Catch:{ Exception -> 0x0191 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x0191 }
            r8[r4] = r9     // Catch:{ Exception -> 0x0191 }
            r4 = 1
            java.util.Map<java.lang.Integer, java.lang.Class<? extends com.alibaba.android.arouter.facade.template.IInterceptor>> r9 = com.alibaba.android.arouter.core.b.e     // Catch:{ Exception -> 0x0191 }
            int r9 = r9.size()     // Catch:{ Exception -> 0x0191 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x0191 }
            r8[r4] = r9     // Catch:{ Exception -> 0x0191 }
            r4 = 2
            java.util.Map<java.lang.String, com.alibaba.android.arouter.facade.model.a> r9 = com.alibaba.android.arouter.core.b.d     // Catch:{ Exception -> 0x0191 }
            int r9 = r9.size()     // Catch:{ Exception -> 0x0191 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x0191 }
            r8[r4] = r9     // Catch:{ Exception -> 0x0191 }
            java.lang.String r4 = java.lang.String.format(r6, r7, r8)     // Catch:{ Exception -> 0x0191 }
            r3.a(r5, r4)     // Catch:{ Exception -> 0x0191 }
        L_0x018e:
            monitor-exit(r0)
            return
        L_0x0191:
            r1 = move-exception
            com.alibaba.android.arouter.exception.HandlerException r2 = new com.alibaba.android.arouter.exception.HandlerException     // Catch:{ all -> 0x01b2 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01b2 }
            r3.<init>()     // Catch:{ all -> 0x01b2 }
            java.lang.String r4 = "ARouter::ARouter init logistics center exception! ["
            r3.append(r4)     // Catch:{ all -> 0x01b2 }
            java.lang.String r4 = r1.getMessage()     // Catch:{ all -> 0x01b2 }
            r3.append(r4)     // Catch:{ all -> 0x01b2 }
            java.lang.String r4 = "]"
            r3.append(r4)     // Catch:{ all -> 0x01b2 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01b2 }
            r2.<init>(r3)     // Catch:{ all -> 0x01b2 }
            throw r2     // Catch:{ all -> 0x01b2 }
        L_0x01b2:
            r10 = move-exception
            monitor-exit(r0)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.android.arouter.core.a.d(android.content.Context, java.util.concurrent.ThreadPoolExecutor):void");
    }

    public static com.alibaba.android.arouter.facade.a b(String serviceName) {
        com.alibaba.android.arouter.facade.model.a meta = b.d.get(serviceName);
        if (meta == null) {
            return null;
        }
        return new com.alibaba.android.arouter.facade.a(meta.f(), meta.d());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x007a, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x009a, code lost:
        throw new com.alibaba.android.arouter.exception.HandlerException("ARouter::Fatal exception when loading group meta. [" + r2.getMessage() + "]");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0181, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        com.alibaba.android.arouter.launcher.a.c.b("ARouter::", "Init provider failed!", r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0192, code lost:
        throw new com.alibaba.android.arouter.exception.HandlerException("Init provider failed!");
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:8:0x0020, B:38:0x0167] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void c(com.alibaba.android.arouter.facade.a r11) {
        /*
            java.lang.Class<com.alibaba.android.arouter.core.a> r0 = com.alibaba.android.arouter.core.a.class
            monitor-enter(r0)
            if (r11 == 0) goto L_0x019e
            java.util.Map<java.lang.String, com.alibaba.android.arouter.facade.model.a> r1 = com.alibaba.android.arouter.core.b.b     // Catch:{ all -> 0x019c }
            java.lang.String r2 = r11.f()     // Catch:{ all -> 0x019c }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x019c }
            com.alibaba.android.arouter.facade.model.a r1 = (com.alibaba.android.arouter.facade.model.a) r1     // Catch:{ all -> 0x019c }
            r2 = 0
            if (r1 != 0) goto L_0x00c7
            java.util.Map<java.lang.String, java.lang.Class<? extends com.alibaba.android.arouter.facade.template.e>> r3 = com.alibaba.android.arouter.core.b.a     // Catch:{ all -> 0x019c }
            java.lang.String r4 = r11.d()     // Catch:{ all -> 0x019c }
            boolean r3 = r3.containsKey(r4)     // Catch:{ all -> 0x019c }
            if (r3 == 0) goto L_0x009b
            boolean r3 = com.alibaba.android.arouter.launcher.a.b()     // Catch:{ Exception -> 0x007a }
            r4 = 1
            r5 = 2
            if (r3 == 0) goto L_0x0047
            com.alibaba.android.arouter.facade.template.b r3 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ Exception -> 0x007a }
            java.lang.String r6 = "ARouter::"
            java.util.Locale r7 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x007a }
            java.lang.String r8 = "The group [%s] starts loading, trigger by [%s]"
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x007a }
            java.lang.String r10 = r11.d()     // Catch:{ Exception -> 0x007a }
            r9[r2] = r10     // Catch:{ Exception -> 0x007a }
            java.lang.String r10 = r11.f()     // Catch:{ Exception -> 0x007a }
            r9[r4] = r10     // Catch:{ Exception -> 0x007a }
            java.lang.String r7 = java.lang.String.format(r7, r8, r9)     // Catch:{ Exception -> 0x007a }
            r3.a(r6, r7)     // Catch:{ Exception -> 0x007a }
        L_0x0047:
            java.lang.String r3 = r11.d()     // Catch:{ Exception -> 0x007a }
            r6 = 0
            a(r3, r6)     // Catch:{ Exception -> 0x007a }
            boolean r3 = com.alibaba.android.arouter.launcher.a.b()     // Catch:{ Exception -> 0x007a }
            if (r3 == 0) goto L_0x0074
            com.alibaba.android.arouter.facade.template.b r3 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ Exception -> 0x007a }
            java.lang.String r6 = "ARouter::"
            java.util.Locale r7 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x007a }
            java.lang.String r8 = "The group [%s] has already been loaded, trigger by [%s]"
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x007a }
            java.lang.String r9 = r11.d()     // Catch:{ Exception -> 0x007a }
            r5[r2] = r9     // Catch:{ Exception -> 0x007a }
            java.lang.String r2 = r11.f()     // Catch:{ Exception -> 0x007a }
            r5[r4] = r2     // Catch:{ Exception -> 0x007a }
            java.lang.String r2 = java.lang.String.format(r7, r8, r5)     // Catch:{ Exception -> 0x007a }
            r3.a(r6, r2)     // Catch:{ Exception -> 0x007a }
        L_0x0074:
            c(r11)     // Catch:{ all -> 0x019c }
            goto L_0x019a
        L_0x007a:
            r2 = move-exception
            com.alibaba.android.arouter.exception.HandlerException r3 = new com.alibaba.android.arouter.exception.HandlerException     // Catch:{ all -> 0x019c }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x019c }
            r4.<init>()     // Catch:{ all -> 0x019c }
            java.lang.String r5 = "ARouter::Fatal exception when loading group meta. ["
            r4.append(r5)     // Catch:{ all -> 0x019c }
            java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x019c }
            r4.append(r5)     // Catch:{ all -> 0x019c }
            java.lang.String r5 = "]"
            r4.append(r5)     // Catch:{ all -> 0x019c }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x019c }
            r3.<init>(r4)     // Catch:{ all -> 0x019c }
            throw r3     // Catch:{ all -> 0x019c }
        L_0x009b:
            com.alibaba.android.arouter.exception.NoRouteFoundException r2 = new com.alibaba.android.arouter.exception.NoRouteFoundException     // Catch:{ all -> 0x019c }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x019c }
            r3.<init>()     // Catch:{ all -> 0x019c }
            java.lang.String r4 = "ARouter::There is no route match the path ["
            r3.append(r4)     // Catch:{ all -> 0x019c }
            java.lang.String r4 = r11.f()     // Catch:{ all -> 0x019c }
            r3.append(r4)     // Catch:{ all -> 0x019c }
            java.lang.String r4 = "], in group ["
            r3.append(r4)     // Catch:{ all -> 0x019c }
            java.lang.String r4 = r11.d()     // Catch:{ all -> 0x019c }
            r3.append(r4)     // Catch:{ all -> 0x019c }
            java.lang.String r4 = "]"
            r3.append(r4)     // Catch:{ all -> 0x019c }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x019c }
            r2.<init>(r3)     // Catch:{ all -> 0x019c }
            throw r2     // Catch:{ all -> 0x019c }
        L_0x00c7:
            java.lang.Class r3 = r1.b()     // Catch:{ all -> 0x019c }
            r11.i(r3)     // Catch:{ all -> 0x019c }
            com.alibaba.android.arouter.facade.enums.a r3 = r1.h()     // Catch:{ all -> 0x019c }
            r11.n(r3)     // Catch:{ all -> 0x019c }
            int r3 = r1.g()     // Catch:{ all -> 0x019c }
            r11.m(r3)     // Catch:{ all -> 0x019c }
            int r3 = r1.c()     // Catch:{ all -> 0x019c }
            r11.j(r3)     // Catch:{ all -> 0x019c }
            android.net.Uri r3 = r11.z()     // Catch:{ all -> 0x019c }
            if (r3 == 0) goto L_0x0145
            java.util.Map r4 = com.alibaba.android.arouter.utils.e.c(r3)     // Catch:{ all -> 0x019c }
            java.util.Map r5 = r1.e()     // Catch:{ all -> 0x019c }
            boolean r6 = com.alibaba.android.arouter.utils.c.b(r5)     // Catch:{ all -> 0x019c }
            if (r6 == 0) goto L_0x013c
            java.util.Set r6 = r5.entrySet()     // Catch:{ all -> 0x019c }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ all -> 0x019c }
        L_0x00ff:
            boolean r7 = r6.hasNext()     // Catch:{ all -> 0x019c }
            if (r7 == 0) goto L_0x0126
            java.lang.Object r7 = r6.next()     // Catch:{ all -> 0x019c }
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7     // Catch:{ all -> 0x019c }
            java.lang.Object r8 = r7.getValue()     // Catch:{ all -> 0x019c }
            java.lang.Integer r8 = (java.lang.Integer) r8     // Catch:{ all -> 0x019c }
            java.lang.Object r9 = r7.getKey()     // Catch:{ all -> 0x019c }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ all -> 0x019c }
            java.lang.Object r10 = r7.getKey()     // Catch:{ all -> 0x019c }
            java.lang.Object r10 = r4.get(r10)     // Catch:{ all -> 0x019c }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ all -> 0x019c }
            f(r11, r8, r9, r10)     // Catch:{ all -> 0x019c }
            goto L_0x00ff
        L_0x0126:
            android.os.Bundle r6 = r11.t()     // Catch:{ all -> 0x019c }
            java.lang.String r7 = "wmHzgD4lOj5o4241"
            java.util.Set r8 = r5.keySet()     // Catch:{ all -> 0x019c }
            java.lang.String[] r9 = new java.lang.String[r2]     // Catch:{ all -> 0x019c }
            java.lang.Object[] r8 = r8.toArray(r9)     // Catch:{ all -> 0x019c }
            java.lang.String[] r8 = (java.lang.String[]) r8     // Catch:{ all -> 0x019c }
            r6.putStringArray(r7, r8)     // Catch:{ all -> 0x019c }
        L_0x013c:
            java.lang.String r6 = "NTeRQWvye18AkPd6G"
            java.lang.String r7 = r3.toString()     // Catch:{ all -> 0x019c }
            r11.T(r6, r7)     // Catch:{ all -> 0x019c }
        L_0x0145:
            int[] r4 = com.alibaba.android.arouter.core.a.C0012a.a     // Catch:{ all -> 0x019c }
            com.alibaba.android.arouter.facade.enums.a r5 = r1.h()     // Catch:{ all -> 0x019c }
            int r5 = r5.ordinal()     // Catch:{ all -> 0x019c }
            r4 = r4[r5]     // Catch:{ all -> 0x019c }
            switch(r4) {
                case 1: goto L_0x0159;
                case 2: goto L_0x0155;
                default: goto L_0x0154;
            }     // Catch:{ all -> 0x019c }
        L_0x0154:
            goto L_0x019a
        L_0x0155:
            r11.A()     // Catch:{ all -> 0x019c }
            goto L_0x019a
        L_0x0159:
            java.lang.Class r4 = r1.b()     // Catch:{ all -> 0x019c }
            java.util.Map<java.lang.Class, com.alibaba.android.arouter.facade.template.c> r5 = com.alibaba.android.arouter.core.b.c     // Catch:{ all -> 0x019c }
            java.lang.Object r5 = r5.get(r4)     // Catch:{ all -> 0x019c }
            com.alibaba.android.arouter.facade.template.c r5 = (com.alibaba.android.arouter.facade.template.c) r5     // Catch:{ all -> 0x019c }
            if (r5 != 0) goto L_0x0193
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x0181 }
            java.lang.reflect.Constructor r6 = r4.getConstructor(r6)     // Catch:{ Exception -> 0x0181 }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0181 }
            java.lang.Object r2 = r6.newInstance(r2)     // Catch:{ Exception -> 0x0181 }
            com.alibaba.android.arouter.facade.template.c r2 = (com.alibaba.android.arouter.facade.template.c) r2     // Catch:{ Exception -> 0x0181 }
            android.content.Context r6 = a     // Catch:{ Exception -> 0x0181 }
            r2.init(r6)     // Catch:{ Exception -> 0x0181 }
            java.util.Map<java.lang.Class, com.alibaba.android.arouter.facade.template.c> r6 = com.alibaba.android.arouter.core.b.c     // Catch:{ Exception -> 0x0181 }
            r6.put(r4, r2)     // Catch:{ Exception -> 0x0181 }
            r5 = r2
            goto L_0x0193
        L_0x0181:
            r2 = move-exception
            com.alibaba.android.arouter.facade.template.b r6 = com.alibaba.android.arouter.launcher.a.c     // Catch:{ all -> 0x019c }
            java.lang.String r7 = "ARouter::"
            java.lang.String r8 = "Init provider failed!"
            r6.b(r7, r8, r2)     // Catch:{ all -> 0x019c }
            com.alibaba.android.arouter.exception.HandlerException r6 = new com.alibaba.android.arouter.exception.HandlerException     // Catch:{ all -> 0x019c }
            java.lang.String r7 = "Init provider failed!"
            r6.<init>(r7)     // Catch:{ all -> 0x019c }
            throw r6     // Catch:{ all -> 0x019c }
        L_0x0193:
            r11.I(r5)     // Catch:{ all -> 0x019c }
            r11.A()     // Catch:{ all -> 0x019c }
        L_0x019a:
            monitor-exit(r0)
            return
        L_0x019c:
            r11 = move-exception
            goto L_0x01a6
        L_0x019e:
            com.alibaba.android.arouter.exception.NoRouteFoundException r1 = new com.alibaba.android.arouter.exception.NoRouteFoundException     // Catch:{ all -> 0x019c }
            java.lang.String r2 = "ARouter::No postcard!"
            r1.<init>(r2)     // Catch:{ all -> 0x019c }
            throw r1     // Catch:{ all -> 0x019c }
        L_0x01a6:
            monitor-exit(r0)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.android.arouter.core.a.c(com.alibaba.android.arouter.facade.a):void");
    }

    /* renamed from: com.alibaba.android.arouter.core.a$a  reason: collision with other inner class name */
    /* compiled from: LogisticsCenter */
    public static /* synthetic */ class C0012a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[com.alibaba.android.arouter.facade.enums.a.values().length];
            a = iArr;
            try {
                iArr[com.alibaba.android.arouter.facade.enums.a.PROVIDER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[com.alibaba.android.arouter.facade.enums.a.FRAGMENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private static void f(com.alibaba.android.arouter.facade.a postcard, Integer typeDef, String key, String value) {
        if (!e.b(key) && !e.b(value)) {
            if (typeDef != null) {
                try {
                    if (typeDef.intValue() == b.BOOLEAN.ordinal()) {
                        postcard.M(key, Boolean.parseBoolean(value));
                    } else if (typeDef.intValue() == b.BYTE.ordinal()) {
                        postcard.N(key, Byte.parseByte(value));
                    } else if (typeDef.intValue() == b.SHORT.ordinal()) {
                        postcard.S(key, Short.parseShort(value));
                    } else if (typeDef.intValue() == b.INT.ordinal()) {
                        postcard.Q(key, Integer.parseInt(value));
                    } else if (typeDef.intValue() == b.LONG.ordinal()) {
                        postcard.R(key, Long.parseLong(value));
                    } else if (typeDef.intValue() == b.FLOAT.ordinal()) {
                        postcard.P(key, Float.parseFloat(value));
                    } else if (typeDef.intValue() == b.DOUBLE.ordinal()) {
                        postcard.O(key, Double.parseDouble(value));
                    } else if (typeDef.intValue() == b.STRING.ordinal()) {
                        postcard.T(key, value);
                    } else if (typeDef.intValue() != b.PARCELABLE.ordinal()) {
                        if (typeDef.intValue() == b.OBJECT.ordinal()) {
                            postcard.T(key, value);
                        } else {
                            postcard.T(key, value);
                        }
                    }
                } catch (Throwable ex) {
                    com.alibaba.android.arouter.facade.template.b bVar = com.alibaba.android.arouter.launcher.a.c;
                    bVar.e("ARouter::", "LogisticsCenter setValue failed! " + ex.getMessage());
                }
            } else {
                postcard.T(key, value);
            }
        }
    }

    public static synchronized void a(String groupName, com.alibaba.android.arouter.facade.template.e group) {
        synchronized (a.class) {
            if (b.a.containsKey(groupName)) {
                ((com.alibaba.android.arouter.facade.template.e) b.a.get(groupName).getConstructor(new Class[0]).newInstance(new Object[0])).loadInto(b.b);
                b.a.remove(groupName);
            }
            if (group != null) {
                group.loadInto(b.b);
            }
        }
    }
}
