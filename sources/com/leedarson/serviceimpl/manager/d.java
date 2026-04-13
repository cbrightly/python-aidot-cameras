package com.leedarson.serviceimpl.manager;

import android.os.HandlerThread;
import com.leedarson.serviceimpl.bean.MtNode;
import com.leedarson.serviceimpl.listener.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MTNodeManager.kt */
public final class d {
    @NotNull
    public static final d a = new d();
    @NotNull
    private static final String b = "MTNodeManager";
    @Nullable
    private static HandlerThread c;
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private static HashMap<Long, MtNode> d = new HashMap<>();
    @Nullable
    private static c e;

    private d() {
    }

    @NotNull
    public final String c() {
        return b;
    }

    @Nullable
    public final HandlerThread a() {
        return c;
    }

    public final void i(@Nullable HandlerThread handlerThread) {
        c = handlerThread;
    }

    public final void h(@Nullable c cVar) {
        e = cVar;
    }

    @Nullable
    public final MtNode b(long addr) {
        Object[] objArr = {new Long(addr)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 8122, new Class[]{Long.TYPE}, MtNode.class);
        if (proxy.isSupported) {
            return (MtNode) proxy.result;
        }
        d();
        if (d.containsKey(Long.valueOf(addr))) {
            return d.get(Long.valueOf(addr));
        }
        MtNode node = new MtNode(addr);
        node.attachThread(c, e);
        d.put(Long.valueOf(addr), node);
        return node;
    }

    public final void f(long addr) {
        Object[] objArr = {new Long(addr)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8123, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            d.remove(Long.valueOf(addr));
        }
    }

    public final void k(@NotNull long[] addrs) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{addrs}, this, changeQuickRedirect, false, 8124, new Class[]{long[].class}, Void.TYPE).isSupported) {
            k.e(addrs, "addrs");
            int length = addrs.length;
            if (length > 0) {
                do {
                    int i2 = i;
                    i++;
                    MtNode b2 = b(addrs[i2]);
                    if (b2 != null) {
                        b2.syncDeviceProperties();
                        continue;
                    }
                } while (i < length);
            }
        }
    }

    public final void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8125, new Class[0], Void.TYPE).isSupported) {
            for (Map.Entry entry : d.entrySet()) {
                MtNode mtNode = (MtNode) entry.getValue();
                if (mtNode != null) {
                    mtNode.shutDown();
                }
                d.remove(entry.getKey());
            }
            HandlerThread handlerThread = c;
            if (handlerThread != null) {
                handlerThread.quit();
            }
            c = null;
        }
    }

    public final void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8126, new Class[0], Void.TYPE).isSupported) {
            for (Map.Entry<Long, MtNode> entry : d.entrySet()) {
                MtNode mtNode = (MtNode) entry.getValue();
                if (mtNode != null) {
                    mtNode.shutDown();
                }
            }
        }
    }

    public final void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8127, new Class[0], Void.TYPE).isSupported) {
            for (Map.Entry<Long, MtNode> entry : d.entrySet()) {
                MtNode mtNode = (MtNode) entry.getValue();
                if (mtNode != null) {
                    mtNode.syncDeviceProperties();
                }
            }
        }
    }

    @Nullable
    public final HandlerThread d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8128, new Class[0], HandlerThread.class);
        if (proxy.isSupported) {
            return (HandlerThread) proxy.result;
        }
        if (c == null) {
            d dVar = a;
            synchronized (dVar) {
                if (dVar.a() == null) {
                    dVar.i(new HandlerThread("Matter-Thread"));
                    HandlerThread a2 = dVar.a();
                    k.c(a2);
                    a2.start();
                    com.leedarson.serviceimpl.k.a.a("Matter-Thread start....", dVar.c());
                }
                x xVar = x.a;
            }
        }
        return c;
    }
}
