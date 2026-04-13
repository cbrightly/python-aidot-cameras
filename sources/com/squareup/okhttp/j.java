package com.squareup.okhttp;

import androidx.work.PeriodicWorkRequest;
import com.amazonaws.kinesisvideo.producer.Time;
import com.squareup.okhttp.internal.d;
import com.squareup.okhttp.internal.http.q;
import com.squareup.okhttp.internal.i;
import com.squareup.okhttp.internal.io.b;
import java.lang.ref.Reference;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/* compiled from: ConnectionPool */
public final class j {
    private static final j a;
    private final Executor b;
    private final int c;
    private final long d;
    private Runnable e;
    private final Deque<b> f;
    final i g;

    static {
        String keepAlive = System.getProperty("http.keepAlive");
        String keepAliveDuration = System.getProperty("http.keepAliveDuration");
        String maxIdleConnections = System.getProperty("http.maxConnections");
        long keepAliveDurationMs = keepAliveDuration != null ? Long.parseLong(keepAliveDuration) : PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS;
        if (keepAlive != null && !Boolean.parseBoolean(keepAlive)) {
            a = new j(0, keepAliveDurationMs);
        } else if (maxIdleConnections != null) {
            a = new j(Integer.parseInt(maxIdleConnections), keepAliveDurationMs);
        } else {
            a = new j(5, keepAliveDurationMs);
        }
    }

    /* compiled from: ConnectionPool */
    public class a implements Runnable {
        a() {
        }

        public void run() {
            while (true) {
                long waitNanos = j.this.a(System.nanoTime());
                if (waitNanos != -1) {
                    if (waitNanos > 0) {
                        long waitMillis = waitNanos / Time.NANOS_IN_A_MILLISECOND;
                        long waitNanos2 = waitNanos - (Time.NANOS_IN_A_MILLISECOND * waitMillis);
                        synchronized (j.this) {
                            try {
                                j.this.wait(waitMillis, (int) waitNanos2);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    public j(int maxIdleConnections, long keepAliveDurationMs) {
        this(maxIdleConnections, keepAliveDurationMs, TimeUnit.MILLISECONDS);
    }

    public j(int maxIdleConnections, long keepAliveDuration, TimeUnit timeUnit) {
        this.b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), com.squareup.okhttp.internal.j.s("OkHttp ConnectionPool", true));
        this.e = new a();
        this.f = new ArrayDeque();
        this.g = new i();
        this.c = maxIdleConnections;
        this.d = timeUnit.toNanos(keepAliveDuration);
        if (keepAliveDuration <= 0) {
            throw new IllegalArgumentException("keepAliveDuration <= 0: " + keepAliveDuration);
        }
    }

    public static j d() {
        return a;
    }

    /* access modifiers changed from: package-private */
    public b c(a address, q streamAllocation) {
        if (Thread.holdsLock(this)) {
            for (b connection : this.f) {
                if (connection.l.size() < connection.a() && address.equals(connection.e().a) && !connection.m) {
                    streamAllocation.a(connection);
                    return connection;
                }
            }
            return null;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void f(b connection) {
        if (Thread.holdsLock(this)) {
            if (this.f.isEmpty()) {
                this.b.execute(this.e);
            }
            this.f.add(connection);
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public boolean b(b connection) {
        if (!Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (connection.m || this.c == 0) {
            this.f.remove(connection);
            return true;
        } else {
            notifyAll();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public long a(long now) {
        int inUseConnectionCount = 0;
        int idleConnectionCount = 0;
        b longestIdleConnection = null;
        long longestIdleDurationNs = Long.MIN_VALUE;
        synchronized (this) {
            for (b connection : this.f) {
                if (e(connection, now) > 0) {
                    inUseConnectionCount++;
                } else {
                    idleConnectionCount++;
                    long idleDurationNs = now - connection.n;
                    if (idleDurationNs > longestIdleDurationNs) {
                        longestIdleDurationNs = idleDurationNs;
                        longestIdleConnection = connection;
                    }
                }
            }
            long j = this.d;
            if (longestIdleDurationNs < j) {
                if (idleConnectionCount <= this.c) {
                    if (idleConnectionCount > 0) {
                        long j2 = j - longestIdleDurationNs;
                        return j2;
                    } else if (inUseConnectionCount > 0) {
                        return j;
                    } else {
                        return -1;
                    }
                }
            }
            this.f.remove(longestIdleConnection);
            com.squareup.okhttp.internal.j.d(longestIdleConnection.i());
            return 0;
        }
    }

    private int e(b connection, long now) {
        List<Reference<q>> list = connection.l;
        int i = 0;
        while (i < list.size()) {
            if (list.get(i).get() != null) {
                i++;
            } else {
                Logger logger = d.a;
                logger.warning("A connection to " + connection.e().a().m() + " was leaked. Did you forget to close a response body?");
                list.remove(i);
                connection.m = true;
                if (list.isEmpty()) {
                    connection.n = now - this.d;
                    return 0;
                }
            }
        }
        return list.size();
    }
}
