package com.bumptech.glide.manager;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.c;
import com.bumptech.glide.util.j;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: RequestTracker */
public class q {
    private final Set<c> a = Collections.newSetFromMap(new WeakHashMap());
    private final List<c> b = new ArrayList();
    private boolean c;

    public void g(@NonNull c request) {
        this.a.add(request);
        if (!this.c) {
            request.h();
            return;
        }
        request.clear();
        if (Log.isLoggable("RequestTracker", 2)) {
            Log.v("RequestTracker", "Paused, delaying request");
        }
        this.b.add(request);
    }

    public boolean a(@Nullable c request) {
        boolean isOwnedByUs = true;
        if (request == null) {
            return true;
        }
        boolean isOwnedByUs2 = this.a.remove(request);
        if (!this.b.remove(request) && !isOwnedByUs2) {
            isOwnedByUs = false;
        }
        if (isOwnedByUs) {
            request.clear();
        }
        return isOwnedByUs;
    }

    public void d() {
        this.c = true;
        for (T request : j.i(this.a)) {
            if (request.isRunning()) {
                request.pause();
                this.b.add(request);
            }
        }
    }

    public void c() {
        this.c = true;
        for (T request : j.i(this.a)) {
            if (request.isRunning() || request.isComplete()) {
                request.clear();
                this.b.add(request);
            }
        }
    }

    public void f() {
        this.c = false;
        for (T request : j.i(this.a)) {
            if (!request.isComplete() && !request.isRunning()) {
                request.h();
            }
        }
        this.b.clear();
    }

    public void b() {
        for (T request : j.i(this.a)) {
            a(request);
        }
        this.b.clear();
    }

    public void e() {
        for (T request : j.i(this.a)) {
            if (!request.isComplete() && !request.e()) {
                request.clear();
                if (!this.c) {
                    request.h();
                } else {
                    this.b.add(request);
                }
            }
        }
    }

    public String toString() {
        return super.toString() + "{numRequests=" + this.a.size() + ", isPaused=" + this.c + "}";
    }
}
