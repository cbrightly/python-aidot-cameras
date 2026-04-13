package okhttp3;

import androidx.core.app.NotificationCompat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.x;
import okhttp3.internal.b;
import okhttp3.internal.connection.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Dispatcher.kt */
public final class p {
    private int a;
    private int b;
    @Nullable
    private Runnable c;
    private ExecutorService d;
    private final ArrayDeque<e.a> e;
    private final ArrayDeque<e.a> f;
    private final ArrayDeque<e> g;

    public p() {
        this.a = 64;
        this.b = 5;
        this.e = new ArrayDeque<>();
        this.f = new ArrayDeque<>();
        this.g = new ArrayDeque<>();
    }

    @NotNull
    public final synchronized ExecutorService c() {
        ExecutorService executorService;
        if (this.d == null) {
            TimeUnit timeUnit = TimeUnit.SECONDS;
            SynchronousQueue synchronousQueue = new SynchronousQueue();
            this.d = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, timeUnit, synchronousQueue, b.K(b.i + " Dispatcher", false));
        }
        executorService = this.d;
        if (executorService == null) {
            k.n();
        }
        return executorService;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public p(@NotNull ExecutorService executorService) {
        this();
        k.f(executorService, "executorService");
        this.d = executorService;
    }

    public final void a(@NotNull e.a call) {
        e.a existingCall;
        k.f(call, NotificationCompat.CATEGORY_CALL);
        synchronized (this) {
            this.e.add(call);
            if (!call.b().n() && (existingCall = d(call.d())) != null) {
                call.e(existingCall);
            }
            x xVar = x.a;
        }
        h();
    }

    private final e.a d(String host) {
        Iterator<e.a> it = this.f.iterator();
        while (it.hasNext()) {
            e.a existingCall = it.next();
            if (k.a(existingCall.d(), host)) {
                return existingCall;
            }
        }
        Iterator<e.a> it2 = this.e.iterator();
        while (it2.hasNext()) {
            e.a existingCall2 = it2.next();
            if (k.a(existingCall2.d(), host)) {
                return existingCall2;
            }
        }
        return null;
    }

    private final boolean h() {
        int i;
        boolean isRunning;
        if (!b.h || !Thread.holdsLock(this)) {
            List executableCalls = new ArrayList();
            synchronized (this) {
                Iterator i2 = this.e.iterator();
                k.b(i2, "readyAsyncCalls.iterator()");
                while (true) {
                    if (!i2.hasNext()) {
                        break;
                    }
                    e.a asyncCall = i2.next();
                    if (this.f.size() >= this.a) {
                        break;
                    } else if (asyncCall.c().get() < this.b) {
                        i2.remove();
                        asyncCall.c().incrementAndGet();
                        k.b(asyncCall, "asyncCall");
                        executableCalls.add(asyncCall);
                        this.f.add(asyncCall);
                    }
                }
                i = 0;
                isRunning = k() > 0;
                x xVar = x.a;
            }
            int size = executableCalls.size();
            while (i < size) {
                int i3 = i;
                ((e.a) executableCalls.get(i3)).a(c());
                i = i3 + 1;
            }
            return isRunning;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST NOT hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    public final synchronized void b(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        this.g.add(call);
    }

    public final void f(@NotNull e.a call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        call.c().decrementAndGet();
        e(this.f, call);
    }

    public final void g(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        e(this.g, call);
    }

    private final <T> void e(Deque<T> calls, T call) {
        Runnable idleCallback;
        synchronized (this) {
            if (calls.remove(call)) {
                idleCallback = this.c;
                x xVar = x.a;
            } else {
                throw new AssertionError("Call wasn't in-flight!");
            }
        }
        if (!h() && idleCallback != null) {
            idleCallback.run();
        }
    }

    @NotNull
    public final synchronized List<e> i() {
        List<e> unmodifiableList;
        Iterable<e.a> $this$mapTo$iv$iv = this.e;
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (e.a it : $this$mapTo$iv$iv) {
            arrayList.add(it.b());
        }
        unmodifiableList = Collections.unmodifiableList(arrayList);
        k.b(unmodifiableList, "Collections.unmodifiable…yncCalls.map { it.call })");
        return unmodifiableList;
    }

    @NotNull
    public final synchronized List<e> j() {
        List<e> unmodifiableList;
        ArrayDeque<e> arrayDeque = this.g;
        Iterable<e.a> $this$mapTo$iv$iv = this.f;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (e.a it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(it.b());
        }
        unmodifiableList = Collections.unmodifiableList(y.n0(arrayDeque, destination$iv$iv));
        k.b(unmodifiableList, "Collections.unmodifiable…yncCalls.map { it.call })");
        return unmodifiableList;
    }

    public final synchronized int k() {
        return this.f.size() + this.g.size();
    }
}
