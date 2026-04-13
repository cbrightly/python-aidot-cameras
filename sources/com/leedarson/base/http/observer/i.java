package com.leedarson.base.http.observer;

import android.text.TextUtils;
import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.manage.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.disposables.b;
import io.reactivex.q;

/* compiled from: HttpRxObserver */
public abstract class i<T> implements q<T> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String mTag;

    public abstract void onError(ApiException apiException);

    public abstract void onStart(b bVar);

    public abstract void onSuccess(T t);

    public void setTag(String mTag2) {
        this.mTag = mTag2;
    }

    public void onError(Throwable e) {
        if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, Opcodes.INVOKEINTERFACE, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            a.c().e(this.mTag);
            if (e instanceof ApiException) {
                onError((ApiException) e);
            } else {
                onError(new ApiException(e, -1000));
            }
        }
    }

    public void onComplete() {
    }

    public void onNext(T t) {
        if (!PatchProxy.proxy(new Object[]{t}, this, changeQuickRedirect, false, 186, new Class[]{Object.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(this.mTag)) {
                a.c().e(this.mTag);
            }
            onSuccess(t);
        }
    }

    public void onSubscribe(b d) {
        if (!PatchProxy.proxy(new Object[]{d}, this, changeQuickRedirect, false, Opcodes.NEW, new Class[]{b.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(this.mTag)) {
                a.c().a(this.mTag, d);
            }
            onStart(d);
        }
    }

    public void cancel() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 188, new Class[0], Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(this.mTag)) {
                a.c().b(this.mTag);
            }
        }
    }

    public boolean isDisposed() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 189, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (TextUtils.isEmpty(this.mTag)) {
            return true;
        }
        return a.c().d(this.mTag);
    }
}
