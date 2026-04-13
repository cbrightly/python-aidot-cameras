package com.leedarson.base.http.manage;

import android.annotation.TargetApi;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson.asm.Opcodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.disposables.b;

/* compiled from: RxActionManagerImpl */
public class a {
    private static volatile a a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private ArrayMap<Object, b> b = new ArrayMap<>();

    public static a c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 172, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    @TargetApi(19)
    private a() {
    }

    @TargetApi(19)
    public void a(Object tag, b disposable) {
        Class[] clsArr = {Object.class, b.class};
        if (!PatchProxy.proxy(new Object[]{tag, disposable}, this, changeQuickRedirect, false, 173, clsArr, Void.TYPE).isSupported) {
            this.b.put(tag, disposable);
        }
    }

    @TargetApi(19)
    public void e(Object tag) {
        if (!PatchProxy.proxy(new Object[]{tag}, this, changeQuickRedirect, false, 174, new Class[]{Object.class}, Void.TYPE).isSupported) {
            if (!this.b.isEmpty()) {
                this.b.remove(tag);
            }
        }
    }

    @TargetApi(19)
    public void b(Object tag) {
        if (!PatchProxy.proxy(new Object[]{tag}, this, changeQuickRedirect, false, 175, new Class[]{Object.class}, Void.TYPE).isSupported) {
            try {
                if (!this.b.isEmpty() && this.b.get(tag) != null) {
                    if (!this.b.get(tag).isDisposed()) {
                        this.b.get(tag).dispose();
                    }
                    this.b.remove(tag);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean d(Object tag) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{tag}, this, changeQuickRedirect, false, Opcodes.ARETURN, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (!this.b.isEmpty()) {
                if (this.b.get(tag) != null) {
                    return this.b.get(tag).isDisposed();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
