package com.leedarson.serviceimpl;

import android.text.TextUtils;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.greenrobot.eventbus.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.a;

/* compiled from: MatterLog.kt */
public final class k {
    @NotNull
    public static final k a = new k();
    @NotNull
    private static String b = "LDSMatter";
    public static ChangeQuickRedirect changeQuickRedirect;

    private k() {
    }

    public static /* synthetic */ void m(k kVar, String str, String str2, int i, Object obj) {
        Class<String> cls = String.class;
        Object[] objArr = {kVar, str, str2, new Integer(i), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6028, new Class[]{k.class, cls, cls, Integer.TYPE, Object.class}, Void.TYPE).isSupported) {
            if ((i & 2) != 0) {
                str2 = "";
            }
            kVar.l(str, str2);
        }
    }

    public final void l(@Nullable String msg, @NotNull String subTag) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{msg, subTag}, this, changeQuickRedirect, false, 6027, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(subTag, "subTag");
            i(msg, 0, f(subTag));
        }
    }

    public static /* synthetic */ void b(k kVar, String str, String str2, int i, Object obj) {
        Class<String> cls = String.class;
        Object[] objArr = {kVar, str, str2, new Integer(i), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6030, new Class[]{k.class, cls, cls, Integer.TYPE, Object.class}, Void.TYPE).isSupported) {
            if ((i & 2) != 0) {
                str2 = "";
            }
            kVar.a(str, str2);
        }
    }

    public final void a(@Nullable String msg, @NotNull String subTag) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{msg, subTag}, this, changeQuickRedirect, false, 6029, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(subTag, "subTag");
            i(msg, 1, f(subTag));
        }
    }

    public static /* synthetic */ void h(k kVar, String str, String str2, int i, Object obj) {
        Class<String> cls = String.class;
        Object[] objArr = {kVar, str, str2, new Integer(i), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6032, new Class[]{k.class, cls, cls, Integer.TYPE, Object.class}, Void.TYPE).isSupported) {
            if ((i & 2) != 0) {
                str2 = "";
            }
            kVar.g(str, str2);
        }
    }

    public final void g(@Nullable String msg, @NotNull String subTag) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{msg, subTag}, this, changeQuickRedirect, false, 6031, clsArr, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(subTag, "subTag");
            i(msg, 2, f(subTag));
        }
    }

    public static /* synthetic */ void e(k kVar, String str, String str2, int i, Object obj) {
        Class<String> cls = String.class;
        Object[] objArr = {kVar, str, str2, new Integer(i), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6036, new Class[]{k.class, cls, cls, Integer.TYPE, Object.class}, Void.TYPE).isSupported) {
            if ((i & 2) != 0) {
                str2 = "";
            }
            kVar.c(str, str2);
        }
    }

    public final void c(@Nullable String msg, @NotNull String subTag) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{msg, subTag}, this, changeQuickRedirect, false, 6035, clsArr, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(subTag, "subTag");
            i(msg, 4, f(subTag));
        }
    }

    public final void d(@Nullable String msg, @NotNull String subTag, @NotNull Throwable tr) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{msg, subTag, tr}, this, changeQuickRedirect, false, 6037, clsArr, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(subTag, "subTag");
            kotlin.jvm.internal.k.e(tr, "tr");
            i(msg, 4, f(subTag));
        }
    }

    private final String f(String subTag) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{subTag}, this, changeQuickRedirect, false, 6039, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (TextUtils.isEmpty(subTag)) {
            return b;
        }
        return b + '-' + subTag;
    }

    public final void i(@Nullable String msg, int level, @Nullable String tag) {
        Class<String> cls = String.class;
        Object[] objArr = {msg, new Integer(level), tag};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6040, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            switch (level) {
                case 0:
                    a.g(tag).m(msg, new Object[0]);
                    return;
                case 1:
                    a.g(tag).a(msg, new Object[0]);
                    return;
                case 2:
                    a.g(tag).h(msg, new Object[0]);
                    return;
                case 3:
                    a.g(tag).n(msg, new Object[0]);
                    return;
                case 4:
                    a.g(tag).c(msg, new Object[0]);
                    return;
                default:
                    a.g(tag).a(msg, new Object[0]);
                    return;
            }
        }
    }

    public static /* synthetic */ void k(k kVar, String str, String str2, String str3, String str4, int i, Object obj) {
        k kVar2 = kVar;
        String str5 = str;
        String str6 = str3;
        int i2 = i;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{kVar2, str5, str2, str6, str4, new Integer(i2), obj}, (Object) null, changeQuickRedirect, true, 6042, new Class[]{k.class, cls, cls, cls, cls, Integer.TYPE, Object.class}, Void.TYPE).isSupported) {
            kVar2.j(str5, (i2 & 2) != 0 ? "debug" : str2, str6, (i2 & 8) != 0 ? "" : str4);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0091, code lost:
        if (r2.equals("failure") == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0051, code lost:
        if (r2.equals("silly") == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x005b, code lost:
        if (r2.equals("error") == false) goto L_0x00a0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void j(@org.jetbrains.annotations.Nullable java.lang.String r14, @org.jetbrains.annotations.NotNull java.lang.String r15, @org.jetbrains.annotations.NotNull java.lang.String r16, @org.jetbrains.annotations.NotNull java.lang.String r17) {
        /*
            r13 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r14
            r10 = 1
            r2[r10] = r15
            r11 = 2
            r2[r11] = r16
            r12 = 3
            r2[r12] = r17
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            r7[r9] = r0
            r7[r10] = r0
            r7[r11] = r0
            r7[r12] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 6041(0x1799, float:8.465E-42)
            r3 = r13
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002c
            return
        L_0x002c:
            r0 = r13
            r2 = r15
            r3 = r17
            r4 = r14
            r5 = r16
            java.lang.String r6 = "level"
            kotlin.jvm.internal.k.e(r2, r6)
            java.lang.String r6 = "event"
            kotlin.jvm.internal.k.e(r5, r6)
            java.lang.String r6 = "subTag"
            kotlin.jvm.internal.k.e(r3, r6)
            int r6 = r2.hashCode()
            switch(r6) {
                case -1867169789: goto L_0x0095;
                case -1086574198: goto L_0x008b;
                case 107332: goto L_0x0080;
                case 3237038: goto L_0x0075;
                case 3641990: goto L_0x006a;
                case 95458899: goto L_0x005f;
                case 96784904: goto L_0x0055;
                case 109440227: goto L_0x004b;
                default: goto L_0x0049;
            }
        L_0x0049:
            goto L_0x00a0
        L_0x004b:
            java.lang.String r6 = "silly"
            boolean r6 = r2.equals(r6)
            if (r6 != 0) goto L_0x0054
            goto L_0x0049
        L_0x0054:
            goto L_0x00a1
        L_0x0055:
            java.lang.String r6 = "error"
            boolean r6 = r2.equals(r6)
            if (r6 != 0) goto L_0x005e
            goto L_0x0049
        L_0x005e:
            goto L_0x00a1
        L_0x005f:
            java.lang.String r1 = "debug"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x0068
            goto L_0x0049
        L_0x0068:
            r1 = r10
            goto L_0x00a1
        L_0x006a:
            java.lang.String r1 = "warn"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x0073
            goto L_0x0049
        L_0x0073:
            r1 = r12
            goto L_0x00a1
        L_0x0075:
            java.lang.String r1 = "info"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x007e
            goto L_0x0049
        L_0x007e:
            r1 = r11
            goto L_0x00a1
        L_0x0080:
            java.lang.String r1 = "log"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x0089
            goto L_0x0049
        L_0x0089:
            r1 = r9
            goto L_0x00a1
        L_0x008b:
            java.lang.String r6 = "failure"
            boolean r6 = r2.equals(r6)
            if (r6 != 0) goto L_0x0094
            goto L_0x0049
        L_0x0094:
            goto L_0x00a1
        L_0x0095:
            java.lang.String r1 = "success"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x009e
            goto L_0x0049
        L_0x009e:
            r1 = r11
            goto L_0x00a1
        L_0x00a0:
            r1 = r10
        L_0x00a1:
            java.lang.String r6 = r0.f(r3)
            r0.i(r4, r1, r6)
            com.leedarson.log.elk.a r6 = com.leedarson.log.elk.a.y(r0)
            com.leedarson.log.elk.a r6 = r6.p(r4)
            com.leedarson.log.elk.a r6 = r6.o(r2)
            com.leedarson.log.elk.a r6 = r6.e(r5)
            java.lang.String r7 = "LdsMatter"
            com.leedarson.log.elk.a r6 = r6.t(r7)
            com.leedarson.log.reporter.d r6 = r6.a()
            r6.b()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.k.j(java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public final void n(@Nullable String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6043, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(a, msg, (String) null, 2, (Object) null);
            c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_LOGGER, "onMessage", msg));
        }
    }
}
