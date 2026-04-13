package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.util.LruCache;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: LruCache */
public class f<T, Y> {
    private final Map<T, a<Y>> a = new LinkedHashMap(100, 0.75f, true);
    private final long b;
    private long c;
    private long d;

    public f(long size) {
        this.b = size;
        this.c = size;
    }

    /* access modifiers changed from: protected */
    public int i(@Nullable Y y) {
        return 1;
    }

    /* access modifiers changed from: protected */
    public void j(@NonNull T t, @Nullable Y y) {
    }

    public synchronized long h() {
        return this.c;
    }

    @Nullable
    public synchronized Y g(@NonNull T key) {
        LruCache.Entry<Y> entry;
        entry = (a) this.a.get(key);
        return entry != null ? entry.a : null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004a, code lost:
        return r2;
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized Y k(@androidx.annotation.NonNull T r8, @androidx.annotation.Nullable Y r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            int r0 = r7.i(r9)     // Catch:{ all -> 0x004b }
            long r1 = (long) r0     // Catch:{ all -> 0x004b }
            long r3 = r7.c     // Catch:{ all -> 0x004b }
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            r2 = 0
            if (r1 < 0) goto L_0x0012
            r7.j(r8, r9)     // Catch:{ all -> 0x004b }
            monitor-exit(r7)
            return r2
        L_0x0012:
            if (r9 == 0) goto L_0x001a
            long r3 = r7.d     // Catch:{ all -> 0x004b }
            long r5 = (long) r0     // Catch:{ all -> 0x004b }
            long r3 = r3 + r5
            r7.d = r3     // Catch:{ all -> 0x004b }
        L_0x001a:
            java.util.Map<T, com.bumptech.glide.util.f$a<Y>> r1 = r7.a     // Catch:{ all -> 0x004b }
            if (r9 != 0) goto L_0x0020
            r3 = r2
            goto L_0x0025
        L_0x0020:
            com.bumptech.glide.util.f$a r3 = new com.bumptech.glide.util.f$a     // Catch:{ all -> 0x004b }
            r3.<init>(r9, r0)     // Catch:{ all -> 0x004b }
        L_0x0025:
            java.lang.Object r1 = r1.put(r8, r3)     // Catch:{ all -> 0x004b }
            com.bumptech.glide.util.f$a r1 = (com.bumptech.glide.util.f.a) r1     // Catch:{ all -> 0x004b }
            if (r1 == 0) goto L_0x0042
            long r3 = r7.d     // Catch:{ all -> 0x004b }
            int r5 = r1.b     // Catch:{ all -> 0x004b }
            long r5 = (long) r5     // Catch:{ all -> 0x004b }
            long r3 = r3 - r5
            r7.d = r3     // Catch:{ all -> 0x004b }
            Y r3 = r1.a     // Catch:{ all -> 0x004b }
            boolean r3 = r3.equals(r9)     // Catch:{ all -> 0x004b }
            if (r3 != 0) goto L_0x0042
            Y r3 = r1.a     // Catch:{ all -> 0x004b }
            r7.j(r8, r3)     // Catch:{ all -> 0x004b }
        L_0x0042:
            r7.f()     // Catch:{ all -> 0x004b }
            if (r1 == 0) goto L_0x0049
            Y r2 = r1.a     // Catch:{ all -> 0x004b }
        L_0x0049:
            monitor-exit(r7)
            return r2
        L_0x004b:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.util.f.k(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    @Nullable
    public synchronized Y l(@NonNull T key) {
        LruCache.Entry<Y> entry = (a) this.a.remove(key);
        if (entry == null) {
            return null;
        }
        this.d -= (long) entry.b;
        return entry.a;
    }

    public void d() {
        m(0);
    }

    /* access modifiers changed from: protected */
    public synchronized void m(long size) {
        while (this.d > size) {
            Iterator<Map.Entry<T, a<Y>>> it = this.a.entrySet().iterator();
            Map.Entry<T, LruCache.Entry<Y>> last = it.next();
            LruCache.Entry<Y> toRemove = (a) last.getValue();
            this.d -= (long) toRemove.b;
            T key = last.getKey();
            it.remove();
            j(key, toRemove.a);
        }
    }

    private void f() {
        m(this.c);
    }

    /* compiled from: LruCache */
    public static final class a<Y> {
        final Y a;
        final int b;

        a(Y value, int size) {
            this.a = value;
            this.b = size;
        }
    }
}
