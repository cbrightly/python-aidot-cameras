package io.netty.channel.group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.BlockingOperationException;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ImmediateEventExecutor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class DefaultChannelGroupFuture extends DefaultPromise<Void> implements ChannelGroupFuture {
    private final ChannelFutureListener childListener = new ChannelFutureListener() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<DefaultChannelGroupFuture> cls = DefaultChannelGroupFuture.class;
        }

        public void operationComplete(ChannelFuture future) {
            boolean callSetDone;
            boolean success = future.isSuccess();
            synchronized (DefaultChannelGroupFuture.this) {
                if (success) {
                    DefaultChannelGroupFuture.access$008(DefaultChannelGroupFuture.this);
                } else {
                    DefaultChannelGroupFuture.access$108(DefaultChannelGroupFuture.this);
                }
                callSetDone = DefaultChannelGroupFuture.this.successCount + DefaultChannelGroupFuture.this.failureCount == DefaultChannelGroupFuture.this.futures.size();
                if (DefaultChannelGroupFuture.this.successCount + DefaultChannelGroupFuture.this.failureCount > DefaultChannelGroupFuture.this.futures.size()) {
                    throw new AssertionError();
                }
            }
            if (!callSetDone) {
                return;
            }
            if (DefaultChannelGroupFuture.this.failureCount > 0) {
                List<Map.Entry<Channel, Throwable>> failed = new ArrayList<>(DefaultChannelGroupFuture.this.failureCount);
                for (ChannelFuture f : DefaultChannelGroupFuture.this.futures.values()) {
                    if (!f.isSuccess()) {
                        failed.add(new DefaultEntry(f.channel(), f.cause()));
                    }
                }
                DefaultChannelGroupFuture.this.setFailure0(new ChannelGroupException(failed));
                return;
            }
            DefaultChannelGroupFuture.this.setSuccess0();
        }
    };
    /* access modifiers changed from: private */
    public int failureCount;
    /* access modifiers changed from: private */
    public final Map<Channel, ChannelFuture> futures;
    private final ChannelGroup group;
    /* access modifiers changed from: private */
    public int successCount;

    static /* synthetic */ int access$008(DefaultChannelGroupFuture x0) {
        int i = x0.successCount;
        x0.successCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$108(DefaultChannelGroupFuture x0) {
        int i = x0.failureCount;
        x0.failureCount = i + 1;
        return i;
    }

    DefaultChannelGroupFuture(ChannelGroup group2, Collection<ChannelFuture> futures2, EventExecutor executor) {
        super(executor);
        if (group2 == null) {
            throw new NullPointerException("group");
        } else if (futures2 != null) {
            this.group = group2;
            Map<Channel, ChannelFuture> futureMap = new LinkedHashMap<>();
            for (ChannelFuture f : futures2) {
                futureMap.put(f.channel(), f);
            }
            Map<Channel, ChannelFuture> unmodifiableMap = Collections.unmodifiableMap(futureMap);
            this.futures = unmodifiableMap;
            for (ChannelFuture f2 : unmodifiableMap.values()) {
                f2.addListener(this.childListener);
            }
            if (this.futures.isEmpty()) {
                setSuccess0();
            }
        } else {
            throw new NullPointerException("futures");
        }
    }

    DefaultChannelGroupFuture(ChannelGroup group2, Map<Channel, ChannelFuture> futures2, EventExecutor executor) {
        super(executor);
        this.group = group2;
        Map<Channel, ChannelFuture> unmodifiableMap = Collections.unmodifiableMap(futures2);
        this.futures = unmodifiableMap;
        for (ChannelFuture f : unmodifiableMap.values()) {
            f.addListener(this.childListener);
        }
        if (this.futures.isEmpty()) {
            setSuccess0();
        }
    }

    public ChannelGroup group() {
        return this.group;
    }

    public ChannelFuture find(Channel channel) {
        return this.futures.get(channel);
    }

    public Iterator<ChannelFuture> iterator() {
        return this.futures.values().iterator();
    }

    public synchronized boolean isPartialSuccess() {
        int i;
        i = this.successCount;
        return (i == 0 || i == this.futures.size()) ? false : true;
    }

    public synchronized boolean isPartialFailure() {
        int i;
        i = this.failureCount;
        return (i == 0 || i == this.futures.size()) ? false : true;
    }

    public DefaultChannelGroupFuture addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.addListener((GenericFutureListener) listener);
        return this;
    }

    public DefaultChannelGroupFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.addListeners((GenericFutureListener[]) listeners);
        return this;
    }

    public DefaultChannelGroupFuture removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.removeListener((GenericFutureListener) listener);
        return this;
    }

    public DefaultChannelGroupFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.removeListeners((GenericFutureListener[]) listeners);
        return this;
    }

    public DefaultChannelGroupFuture await() {
        super.await();
        return this;
    }

    public DefaultChannelGroupFuture awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    public DefaultChannelGroupFuture syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    public DefaultChannelGroupFuture sync() {
        super.sync();
        return this;
    }

    public ChannelGroupException cause() {
        return (ChannelGroupException) super.cause();
    }

    /* access modifiers changed from: private */
    public void setSuccess0() {
        super.setSuccess(null);
    }

    /* access modifiers changed from: private */
    public void setFailure0(ChannelGroupException cause) {
        super.setFailure(cause);
    }

    public DefaultChannelGroupFuture setSuccess(Void result) {
        throw new IllegalStateException();
    }

    public boolean trySuccess(Void result) {
        throw new IllegalStateException();
    }

    public DefaultChannelGroupFuture setFailure(Throwable cause) {
        throw new IllegalStateException();
    }

    public boolean tryFailure(Throwable cause) {
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public void checkDeadLock() {
        EventExecutor e = executor();
        if (e != null && e != ImmediateEventExecutor.INSTANCE && e.inEventLoop()) {
            throw new BlockingOperationException();
        }
    }

    public static final class DefaultEntry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private final V value;

        DefaultEntry(K key2, V value2) {
            this.key = key2;
            this.value = value2;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public V setValue(V v) {
            throw new UnsupportedOperationException("read-only");
        }
    }
}
