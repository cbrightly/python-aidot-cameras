package com.leedarson.base.presenters;

import android.app.Activity;
import android.os.Bundle;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.disposables.b;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/* compiled from: BasePresenter */
public class a<V, T> implements com.leedarson.base.listener.a {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected Reference<V> a;
    protected V b;
    protected Reference<T> c;
    protected T d;
    public io.reactivex.disposables.a e = new io.reactivex.disposables.a();

    public a(V view, T activity) {
        i(view);
        h(activity);
        q(activity);
    }

    private void q(T activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 324, new Class[]{Object.class}, Void.TYPE).isSupported) {
            if (activity instanceof BaseFragment) {
                n(((BaseFragment) activity).getActivity());
            } else if (activity instanceof Activity) {
                n((Activity) activity);
            }
        }
    }

    private void n(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 325, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            if (activity == null) {
                return;
            }
            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).W(this);
            } else if (activity instanceof BaseFragmentActivity) {
                ((BaseFragmentActivity) activity).Z(this);
            }
        }
    }

    private void i(V view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 326, new Class[]{Object.class}, Void.TYPE).isSupported) {
            WeakReference weakReference = new WeakReference(view);
            this.a = weakReference;
            this.b = weakReference.get();
        }
    }

    private void h(T activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 327, new Class[]{Object.class}, Void.TYPE).isSupported) {
            WeakReference weakReference = new WeakReference(activity);
            this.c = weakReference;
            this.d = weakReference.get();
        }
    }

    private void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 328, new Class[0], Void.TYPE).isSupported) {
            if (p()) {
                this.a.clear();
                this.a = null;
            }
        }
    }

    private void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 329, new Class[0], Void.TYPE).isSupported) {
            if (o()) {
                this.c.clear();
                this.c = null;
            }
            this.d = null;
            this.b = null;
        }
    }

    public V m() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 330, new Class[0], Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        Reference<V> reference = this.a;
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    public T l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 331, new Class[0], Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        Reference<T> reference = this.c;
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    public boolean p() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 332, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Reference<V> reference = this.a;
        return (reference == null || reference.get() == null) ? false : true;
    }

    public boolean o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 333, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Reference<T> reference = this.c;
        return (reference == null || reference.get() == null) ? false : true;
    }

    public void onCreate(Bundle savedInstanceState) {
    }

    public void onStart() {
    }

    public void a() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 334, new Class[0], Void.TYPE).isSupported) {
            k();
            j();
        }
    }

    public void b(b disposable) {
        if (!PatchProxy.proxy(new Object[]{disposable}, this, changeQuickRedirect, false, 335, new Class[]{b.class}, Void.TYPE).isSupported) {
            this.e.b(disposable);
        }
    }
}
