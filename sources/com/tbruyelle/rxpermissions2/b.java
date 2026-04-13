package com.tbruyelle.rxpermissions2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.functions.f;
import io.reactivex.l;
import io.reactivex.o;
import io.reactivex.p;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;

/* compiled from: RxPermissions */
public class b {
    static final Object a = new Object();
    c b;

    public b(@NonNull Activity activity) {
        this.b = e(activity);
    }

    private c e(Activity activity) {
        c rxPermissionsFragment = d(activity);
        if (!(rxPermissionsFragment == null)) {
            return rxPermissionsFragment;
        }
        c rxPermissionsFragment2 = new c();
        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager.beginTransaction().add(rxPermissionsFragment2, "RxPermissions").commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        return rxPermissionsFragment2;
    }

    private c d(Activity activity) {
        return (c) activity.getFragmentManager().findFragmentByTag("RxPermissions");
    }

    /* compiled from: RxPermissions */
    public class a implements p<T, a> {
        final /* synthetic */ String[] a;

        a(String[] strArr) {
            this.a = strArr;
        }

        public o<a> a(l<T> o) {
            return b.this.k(o, this.a);
        }
    }

    public <T> p<T, a> c(String... permissions) {
        return new a(permissions);
    }

    public l<a> l(String... permissions) {
        return l.F(a).h(c(permissions));
    }

    /* access modifiers changed from: private */
    public l<a> k(l<?> trigger, String... permissions) {
        if (permissions != null && permissions.length != 0) {
            return i(trigger, j(permissions)).u(new C0212b(permissions));
        }
        throw new IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission");
    }

    /* renamed from: com.tbruyelle.rxpermissions2.b$b  reason: collision with other inner class name */
    /* compiled from: RxPermissions */
    public class C0212b implements f<Object, l<a>> {
        final /* synthetic */ String[] c;

        C0212b(String[] strArr) {
            this.c = strArr;
        }

        /* renamed from: a */
        public l<a> apply(Object o) {
            return b.this.m(this.c);
        }
    }

    private l<?> j(String... permissions) {
        for (String p : permissions) {
            if (!this.b.a(p)) {
                return l.q();
            }
        }
        return l.F(a);
    }

    private l<?> i(l<?> trigger, l<?> pending) {
        if (trigger == null) {
            return l.F(a);
        }
        return l.H(trigger, pending);
    }

    /* access modifiers changed from: private */
    @TargetApi(23)
    public l<a> m(String... permissions) {
        List<Observable<Permission>> list = new ArrayList<>(permissions.length);
        List<String> unrequestedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            this.b.e("Requesting permission " + permission);
            if (f(permission)) {
                list.add(l.F(new a(permission, true, false)));
            } else if (h(permission)) {
                list.add(l.F(new a(permission, false, false)));
            } else {
                PublishSubject<Permission> subject = this.b.b(permission);
                if (subject == null) {
                    unrequestedPermissions.add(permission);
                    subject = io.reactivex.subjects.b.p0();
                    this.b.h(permission, subject);
                }
                list.add(subject);
            }
        }
        if (!unrequestedPermissions.isEmpty()) {
            n((String[]) unrequestedPermissions.toArray(new String[unrequestedPermissions.size()]));
        }
        return l.i(l.z(list));
    }

    public l<Boolean> o(Activity activity, String... permissions) {
        if (!g()) {
            return l.F(false);
        }
        return l.F(Boolean.valueOf(p(activity, permissions)));
    }

    @TargetApi(23)
    private boolean p(Activity activity, String... permissions) {
        for (String p : permissions) {
            if (!f(p) && !activity.shouldShowRequestPermissionRationale(p)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    @TargetApi(23)
    public void n(String[] permissions) {
        c cVar = this.b;
        cVar.e("requestPermissionsFromFragment " + TextUtils.join(", ", permissions));
        this.b.g(permissions);
    }

    public boolean f(String permission) {
        return !g() || this.b.c(permission);
    }

    public boolean h(String permission) {
        return g() && this.b.d(permission);
    }

    /* access modifiers changed from: package-private */
    public boolean g() {
        return Build.VERSION.SDK_INT >= 23;
    }
}
