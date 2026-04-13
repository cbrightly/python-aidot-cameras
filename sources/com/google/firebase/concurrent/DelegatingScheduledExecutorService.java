package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DelegatingScheduledExecutorService implements ScheduledExecutorService {
    private final ExecutorService delegate;
    private final ScheduledExecutorService scheduler;

    DelegatingScheduledExecutorService(ExecutorService delegate2, ScheduledExecutorService scheduler2) {
        this.delegate = delegate2;
        this.scheduler = scheduler2;
    }

    public void shutdown() {
        throw new UnsupportedOperationException("Shutting down is not allowed.");
    }

    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException("Shutting down is not allowed.");
    }

    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return this.delegate.awaitTermination(timeout, unit);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return this.delegate.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return this.delegate.submit(task, result);
    }

    public Future<?> submit(Runnable task) {
        return this.delegate.submit(task);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
        return this.delegate.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        return this.delegate.invokeAll(tasks, timeout, unit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) {
        return this.delegate.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        return this.delegate.invokeAny(tasks, timeout, unit);
    }

    public void execute(Runnable command) {
        this.delegate.execute(command);
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return new DelegatingScheduledFuture(new b(this, command, delay, unit));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$schedule$2 */
    public /* synthetic */ ScheduledFuture b(Runnable command, long delay, TimeUnit unit, DelegatingScheduledFuture.Completer completer) {
        return this.scheduler.schedule(new d(this, command, completer), delay, unit);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$schedule$1 */
    public /* synthetic */ void a(Runnable command, DelegatingScheduledFuture.Completer completer) {
        this.delegate.execute(new c(command, completer));
    }

    static /* synthetic */ void lambda$schedule$0(Runnable command, DelegatingScheduledFuture.Completer completer) {
        try {
            command.run();
            completer.set(null);
        } catch (Exception ex) {
            completer.setException(ex);
        }
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return new DelegatingScheduledFuture(new h(this, callable, delay, unit));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$schedule$5 */
    public /* synthetic */ ScheduledFuture d(Callable callable, long delay, TimeUnit unit, DelegatingScheduledFuture.Completer completer) {
        return this.scheduler.schedule(new e(this, callable, completer), delay, unit);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$schedule$4 */
    public /* synthetic */ Future c(Callable callable, DelegatingScheduledFuture.Completer completer) {
        return this.delegate.submit(new m(callable, completer));
    }

    static /* synthetic */ void lambda$schedule$3(Callable callable, DelegatingScheduledFuture.Completer completer) {
        try {
            completer.set(callable.call());
        } catch (Exception ex) {
            completer.setException(ex);
        }
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return new DelegatingScheduledFuture(new f(this, command, initialDelay, period, unit));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$scheduleAtFixedRate$8 */
    public /* synthetic */ ScheduledFuture f(Runnable command, long initialDelay, long period, TimeUnit unit, DelegatingScheduledFuture.Completer completer) {
        return this.scheduler.scheduleAtFixedRate(new g(this, command, completer), initialDelay, period, unit);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$scheduleAtFixedRate$7 */
    public /* synthetic */ void e(Runnable command, DelegatingScheduledFuture.Completer completer) {
        this.delegate.execute(new i(command, completer));
    }

    static /* synthetic */ void lambda$scheduleAtFixedRate$6(Runnable command, DelegatingScheduledFuture.Completer completer) {
        try {
            command.run();
        } catch (Exception ex) {
            completer.setException(ex);
            throw ex;
        }
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return new DelegatingScheduledFuture(new j(this, command, initialDelay, delay, unit));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$scheduleWithFixedDelay$11 */
    public /* synthetic */ ScheduledFuture h(Runnable command, long initialDelay, long delay, TimeUnit unit, DelegatingScheduledFuture.Completer completer) {
        return this.scheduler.scheduleWithFixedDelay(new l(this, command, completer), initialDelay, delay, unit);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$scheduleWithFixedDelay$10 */
    public /* synthetic */ void g(Runnable command, DelegatingScheduledFuture.Completer completer) {
        this.delegate.execute(new k(command, completer));
    }

    static /* synthetic */ void lambda$scheduleWithFixedDelay$9(Runnable command, DelegatingScheduledFuture.Completer completer) {
        try {
            command.run();
        } catch (Exception ex) {
            completer.setException(ex);
        }
    }
}
