package com.bumptech.glide.manager;

import androidx.annotation.NonNull;
import com.bumptech.glide.util.j;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: ActivityFragmentLifecycle */
public class a implements k {
    private final Set<l> a = Collections.newSetFromMap(new WeakHashMap());
    private boolean b;
    private boolean c;

    a() {
    }

    public void b(@NonNull l listener) {
        this.a.add(listener);
        if (this.c) {
            listener.onDestroy();
        } else if (this.b) {
            listener.onStart();
        } else {
            listener.onStop();
        }
    }

    public void a(@NonNull l listener) {
        this.a.remove(listener);
    }

    /* access modifiers changed from: package-private */
    public void d() {
        this.b = true;
        for (T lifecycleListener : j.i(this.a)) {
            lifecycleListener.onStart();
        }
    }

    /* access modifiers changed from: package-private */
    public void e() {
        this.b = false;
        for (T lifecycleListener : j.i(this.a)) {
            lifecycleListener.onStop();
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        this.c = true;
        for (T lifecycleListener : j.i(this.a)) {
            lifecycleListener.onDestroy();
        }
    }
}
