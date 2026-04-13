package com.bumptech.glide.manager;

import androidx.annotation.NonNull;
import com.bumptech.glide.request.target.j;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: TargetTracker */
public final class r implements l {
    private final Set<j<?>> c = Collections.newSetFromMap(new WeakHashMap());

    public void j(@NonNull j<?> target) {
        this.c.add(target);
    }

    public void k(@NonNull j<?> target) {
        this.c.remove(target);
    }

    public void onStart() {
        for (T target : com.bumptech.glide.util.j.i(this.c)) {
            target.onStart();
        }
    }

    public void onStop() {
        for (T target : com.bumptech.glide.util.j.i(this.c)) {
            target.onStop();
        }
    }

    public void onDestroy() {
        for (T target : com.bumptech.glide.util.j.i(this.c)) {
            target.onDestroy();
        }
    }

    @NonNull
    public List<j<?>> i() {
        return com.bumptech.glide.util.j.i(this.c);
    }

    public void h() {
        this.c.clear();
    }
}
