package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.util.i;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: DiskCacheWriteLocker */
public final class c {
    private final Map<String, a> a = new HashMap();
    private final b b = new b();

    c() {
    }

    /* access modifiers changed from: package-private */
    public void a(String safeKey) {
        a writeLock;
        synchronized (this) {
            writeLock = this.a.get(safeKey);
            if (writeLock == null) {
                writeLock = this.b.a();
                this.a.put(safeKey, writeLock);
            }
            writeLock.b++;
        }
        writeLock.a.lock();
    }

    /* access modifiers changed from: package-private */
    public void b(String safeKey) {
        a writeLock;
        synchronized (this) {
            writeLock = (a) i.d(this.a.get(safeKey));
            int i = writeLock.b;
            if (i >= 1) {
                int i2 = i - 1;
                writeLock.b = i2;
                if (i2 == 0) {
                    a removed = this.a.remove(safeKey);
                    if (removed.equals(writeLock)) {
                        this.b.b(removed);
                    } else {
                        throw new IllegalStateException("Removed the wrong lock, expected to remove: " + writeLock + ", but actually removed: " + removed + ", safeKey: " + safeKey);
                    }
                }
            } else {
                throw new IllegalStateException("Cannot release a lock that is not held, safeKey: " + safeKey + ", interestedThreads: " + writeLock.b);
            }
        }
        writeLock.a.unlock();
    }

    /* compiled from: DiskCacheWriteLocker */
    public static class a {
        final Lock a = new ReentrantLock();
        int b;

        a() {
        }
    }

    /* compiled from: DiskCacheWriteLocker */
    public static class b {
        private final Queue<a> a = new ArrayDeque();

        b() {
        }

        /* access modifiers changed from: package-private */
        public a a() {
            a result;
            synchronized (this.a) {
                result = this.a.poll();
            }
            if (result == null) {
                return new a();
            }
            return result;
        }

        /* access modifiers changed from: package-private */
        public void b(a writeLock) {
            synchronized (this.a) {
                if (this.a.size() < 10) {
                    this.a.offer(writeLock);
                }
            }
        }
    }
}
