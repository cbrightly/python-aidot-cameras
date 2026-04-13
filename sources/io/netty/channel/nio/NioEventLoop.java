package io.netty.channel.nio;

import com.amazonaws.kinesisvideo.producer.Time;
import io.netty.channel.ChannelException;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopException;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.util.IntSupplier;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReflectionUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NioEventLoop extends SingleThreadEventLoop {
    private static final int CLEANUP_INTERVAL = 256;
    private static final boolean DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
    private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
    private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) NioEventLoop.class);
    private int cancelledKeys;
    private volatile int ioRatio = 50;
    private boolean needsToSelectAgain;
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>() {
        public Integer call() {
            return Integer.valueOf(NioEventLoop.super.pendingTasks());
        }
    };
    private final SelectorProvider provider;
    private final IntSupplier selectNowSupplier = new IntSupplier() {
        public int get() {
            return NioEventLoop.this.selectNow();
        }
    };
    private final SelectStrategy selectStrategy;
    private SelectedSelectionKeySet selectedKeys;
    private Selector selector;
    private Selector unwrappedSelector;
    private final AtomicBoolean wakenUp = new AtomicBoolean();

    static {
        if (SystemPropertyUtil.get("sun.nio.ch.bugLevel") == null) {
            try {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    public Void run() {
                        System.setProperty("sun.nio.ch.bugLevel", "");
                        return null;
                    }
                });
            } catch (SecurityException e) {
                logger.debug("Unable to get/set System Property: sun.nio.ch.bugLevel", (Throwable) e);
            }
        }
        int selectorAutoRebuildThreshold = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
        if (selectorAutoRebuildThreshold < 3) {
            selectorAutoRebuildThreshold = 0;
        }
        SELECTOR_AUTO_REBUILD_THRESHOLD = selectorAutoRebuildThreshold;
        InternalLogger internalLogger = logger;
        if (internalLogger.isDebugEnabled()) {
            internalLogger.debug("-Dio.netty.noKeySetOptimization: {}", (Object) Boolean.valueOf(DISABLE_KEYSET_OPTIMIZATION));
            internalLogger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", (Object) Integer.valueOf(selectorAutoRebuildThreshold));
        }
    }

    NioEventLoop(NioEventLoopGroup parent, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler) {
        super(parent, threadFactory, false, SingleThreadEventLoop.DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
        if (selectorProvider == null) {
            throw new NullPointerException("selectorProvider");
        } else if (strategy != null) {
            this.provider = selectorProvider;
            SelectorTuple selectorTuple = openSelector();
            this.selector = selectorTuple.selector;
            this.unwrappedSelector = selectorTuple.unwrappedSelector;
            this.selectStrategy = strategy;
        } else {
            throw new NullPointerException("selectStrategy");
        }
    }

    public static final class SelectorTuple {
        final Selector selector;
        final Selector unwrappedSelector;

        SelectorTuple(Selector unwrappedSelector2) {
            this.unwrappedSelector = unwrappedSelector2;
            this.selector = unwrappedSelector2;
        }

        SelectorTuple(Selector unwrappedSelector2, Selector selector2) {
            this.unwrappedSelector = unwrappedSelector2;
            this.selector = selector2;
        }
    }

    private SelectorTuple openSelector() {
        try {
            final Selector unwrappedSelector2 = this.provider.openSelector();
            if (DISABLE_KEYSET_OPTIMIZATION) {
                return new SelectorTuple(unwrappedSelector2);
            }
            final SelectedSelectionKeySet selectedKeySet = new SelectedSelectionKeySet();
            Object maybeSelectorImplClass = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    try {
                        return Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
                    } catch (Throwable cause) {
                        return cause;
                    }
                }
            });
            if (!(maybeSelectorImplClass instanceof Class) || !((Class) maybeSelectorImplClass).isAssignableFrom(unwrappedSelector2.getClass())) {
                if (maybeSelectorImplClass instanceof Throwable) {
                    logger.trace("failed to instrument a special java.util.Set into: {}", unwrappedSelector2, (Throwable) maybeSelectorImplClass);
                }
                return new SelectorTuple(unwrappedSelector2);
            }
            final Class<?> selectorImplClass = (Class) maybeSelectorImplClass;
            Object maybeException = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    try {
                        Field selectedKeysField = selectorImplClass.getDeclaredField("selectedKeys");
                        Field publicSelectedKeysField = selectorImplClass.getDeclaredField("publicSelectedKeys");
                        Throwable cause = ReflectionUtil.trySetAccessible(selectedKeysField, true);
                        if (cause != null) {
                            return cause;
                        }
                        Throwable cause2 = ReflectionUtil.trySetAccessible(publicSelectedKeysField, true);
                        if (cause2 != null) {
                            return cause2;
                        }
                        selectedKeysField.set(unwrappedSelector2, selectedKeySet);
                        publicSelectedKeysField.set(unwrappedSelector2, selectedKeySet);
                        return null;
                    } catch (NoSuchFieldException e) {
                        return e;
                    } catch (IllegalAccessException e2) {
                        return e2;
                    }
                }
            });
            if (maybeException instanceof Exception) {
                this.selectedKeys = null;
                logger.trace("failed to instrument a special java.util.Set into: {}", unwrappedSelector2, (Exception) maybeException);
                return new SelectorTuple(unwrappedSelector2);
            }
            this.selectedKeys = selectedKeySet;
            logger.trace("instrumented a special java.util.Set into: {}", (Object) unwrappedSelector2);
            return new SelectorTuple(unwrappedSelector2, new SelectedSelectionKeySetSelector(unwrappedSelector2, selectedKeySet));
        } catch (IOException e) {
            throw new ChannelException("failed to open a new selector", e);
        }
    }

    public SelectorProvider selectorProvider() {
        return this.provider;
    }

    /* access modifiers changed from: protected */
    public Queue<Runnable> newTaskQueue(int maxPendingTasks) {
        if (maxPendingTasks == Integer.MAX_VALUE) {
            return PlatformDependent.newMpscQueue();
        }
        return PlatformDependent.newMpscQueue(maxPendingTasks);
    }

    public int pendingTasks() {
        if (inEventLoop()) {
            return super.pendingTasks();
        }
        return ((Integer) submit(this.pendingTasksCallable).syncUninterruptibly().getNow()).intValue();
    }

    public void register(SelectableChannel ch, int interestOps, NioTask<?> task) {
        if (ch == null) {
            throw new NullPointerException("ch");
        } else if (interestOps == 0) {
            throw new IllegalArgumentException("interestOps must be non-zero.");
        } else if (((~ch.validOps()) & interestOps) != 0) {
            throw new IllegalArgumentException("invalid interestOps: " + interestOps + "(validOps: " + ch.validOps() + ')');
        } else if (task == null) {
            throw new NullPointerException("task");
        } else if (!isShutdown()) {
            try {
                ch.register(this.selector, interestOps, task);
            } catch (Exception e) {
                throw new EventLoopException("failed to register a channel", e);
            }
        } else {
            throw new IllegalStateException("event loop shut down");
        }
    }

    public int getIoRatio() {
        return this.ioRatio;
    }

    public void setIoRatio(int ioRatio2) {
        if (ioRatio2 <= 0 || ioRatio2 > 100) {
            throw new IllegalArgumentException("ioRatio: " + ioRatio2 + " (expected: 0 < ioRatio <= 100)");
        }
        this.ioRatio = ioRatio2;
    }

    public void rebuildSelector() {
        if (!inEventLoop()) {
            execute(new Runnable() {
                public void run() {
                    NioEventLoop.this.rebuildSelector0();
                }
            });
        } else {
            rebuildSelector0();
        }
    }

    /* access modifiers changed from: private */
    public void rebuildSelector0() {
        Selector oldSelector = this.selector;
        if (oldSelector != null) {
            try {
                SelectorTuple newSelectorTuple = openSelector();
                int nChannels = 0;
                for (SelectionKey key : oldSelector.keys()) {
                    Object a = key.attachment();
                    try {
                        if (key.isValid()) {
                            if (key.channel().keyFor(newSelectorTuple.unwrappedSelector) == null) {
                                int interestOps = key.interestOps();
                                key.cancel();
                                SelectionKey newKey = key.channel().register(newSelectorTuple.unwrappedSelector, interestOps, a);
                                if (a instanceof AbstractNioChannel) {
                                    ((AbstractNioChannel) a).selectionKey = newKey;
                                }
                                nChannels++;
                            }
                        }
                    } catch (Exception e) {
                        logger.warn("Failed to re-register a Channel to the new Selector.", (Throwable) e);
                        if (a instanceof AbstractNioChannel) {
                            AbstractNioChannel ch = (AbstractNioChannel) a;
                            ch.unsafe().close(ch.unsafe().voidPromise());
                        } else {
                            invokeChannelUnregistered((NioTask) a, key, e);
                        }
                    }
                }
                this.selector = newSelectorTuple.selector;
                this.unwrappedSelector = newSelectorTuple.unwrappedSelector;
                try {
                    oldSelector.close();
                } catch (Throwable t) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Failed to close the old Selector.", t);
                    }
                }
                InternalLogger internalLogger = logger;
                internalLogger.info("Migrated " + nChannels + " channel(s) to the new Selector.");
            } catch (Exception e2) {
                logger.warn("Failed to create a new Selector.", (Throwable) e2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void run() {
        int ioRatio2;
        long ioStartTime;
        while (true) {
            try {
                switch (this.selectStrategy.calculateStrategy(this.selectNowSupplier, hasTasks())) {
                    case -2:
                        continue;
                    case -1:
                        select(this.wakenUp.getAndSet(false));
                        if (this.wakenUp.get()) {
                            this.selector.wakeup();
                            break;
                        }
                        break;
                }
                this.cancelledKeys = 0;
                this.needsToSelectAgain = false;
                ioRatio2 = this.ioRatio;
                if (ioRatio2 == 100) {
                    processSelectedKeys();
                    runAllTasks();
                } else {
                    ioStartTime = System.nanoTime();
                    processSelectedKeys();
                    runAllTasks((((long) (100 - ioRatio2)) * (System.nanoTime() - ioStartTime)) / ((long) ioRatio2));
                }
            } catch (Throwable t) {
                handleLoopException(t);
            }
            try {
                if (isShuttingDown()) {
                    closeAll();
                    if (confirmShutdown()) {
                        return;
                    }
                } else {
                    continue;
                }
            } catch (Throwable t2) {
                handleLoopException(t2);
            }
        }
    }

    private static void handleLoopException(Throwable t) {
        logger.warn("Unexpected exception in the selector loop.", t);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    private void processSelectedKeys() {
        if (this.selectedKeys != null) {
            processSelectedKeysOptimized();
        } else {
            processSelectedKeysPlain(this.selector.selectedKeys());
        }
    }

    /* access modifiers changed from: protected */
    public void cleanup() {
        try {
            this.selector.close();
        } catch (IOException e) {
            logger.warn("Failed to close a selector.", (Throwable) e);
        }
    }

    /* access modifiers changed from: package-private */
    public void cancel(SelectionKey key) {
        key.cancel();
        int i = this.cancelledKeys + 1;
        this.cancelledKeys = i;
        if (i >= 256) {
            this.cancelledKeys = 0;
            this.needsToSelectAgain = true;
        }
    }

    /* access modifiers changed from: protected */
    public Runnable pollTask() {
        Runnable task = super.pollTask();
        if (this.needsToSelectAgain) {
            selectAgain();
        }
        return task;
    }

    private void processSelectedKeysPlain(Set<SelectionKey> selectedKeys2) {
        if (!selectedKeys2.isEmpty()) {
            Iterator<SelectionKey> i = selectedKeys2.iterator();
            while (true) {
                SelectionKey k = i.next();
                Object a = k.attachment();
                i.remove();
                if (a instanceof AbstractNioChannel) {
                    processSelectedKey(k, (AbstractNioChannel) a);
                } else {
                    processSelectedKey(k, (NioTask) a);
                }
                if (i.hasNext()) {
                    if (this.needsToSelectAgain) {
                        selectAgain();
                        Set<SelectionKey> selectedKeys3 = this.selector.selectedKeys();
                        if (!selectedKeys3.isEmpty()) {
                            i = selectedKeys3.iterator();
                        } else {
                            return;
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    private void processSelectedKeysOptimized() {
        int i = 0;
        while (true) {
            SelectedSelectionKeySet selectedSelectionKeySet = this.selectedKeys;
            if (i < selectedSelectionKeySet.size) {
                SelectionKey[] selectionKeyArr = selectedSelectionKeySet.keys;
                SelectionKey k = selectionKeyArr[i];
                selectionKeyArr[i] = null;
                Object a = k.attachment();
                if (a instanceof AbstractNioChannel) {
                    processSelectedKey(k, (AbstractNioChannel) a);
                } else {
                    processSelectedKey(k, (NioTask) a);
                }
                if (this.needsToSelectAgain) {
                    this.selectedKeys.reset(i + 1);
                    selectAgain();
                    i = -1;
                }
                i++;
            } else {
                return;
            }
        }
    }

    private void processSelectedKey(SelectionKey k, AbstractNioChannel ch) {
        AbstractNioChannel.NioUnsafe unsafe = ch.unsafe();
        if (!k.isValid()) {
            try {
                EventLoop eventLoop = ch.eventLoop();
                if (eventLoop == this && eventLoop != null) {
                    unsafe.close(unsafe.voidPromise());
                }
            } catch (Throwable th) {
            }
        } else {
            try {
                int readyOps = k.readyOps();
                if ((readyOps & 8) != 0) {
                    k.interestOps(k.interestOps() & -9);
                    unsafe.finishConnect();
                }
                if ((readyOps & 4) != 0) {
                    ch.unsafe().forceFlush();
                }
                if ((readyOps & 17) != 0 || readyOps == 0) {
                    unsafe.read();
                }
            } catch (CancelledKeyException e) {
                unsafe.close(unsafe.voidPromise());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0014, code lost:
        invokeChannelUnregistered(r5, r4, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0018, code lost:
        r4.cancel();
        invokeChannelUnregistered(r5, r4, (java.lang.Throwable) null);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void processSelectedKey(java.nio.channels.SelectionKey r4, io.netty.channel.nio.NioTask<java.nio.channels.SelectableChannel> r5) {
        /*
            r0 = 0
            r1 = 0
            java.nio.channels.SelectableChannel r2 = r4.channel()     // Catch:{ Exception -> 0x0022 }
            r5.channelReady(r2, r4)     // Catch:{ Exception -> 0x0022 }
            r0 = 1
            switch(r0) {
                case 0: goto L_0x0018;
                case 1: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x001f
        L_0x000e:
            boolean r2 = r4.isValid()
            if (r2 != 0) goto L_0x001f
        L_0x0014:
            invokeChannelUnregistered(r5, r4, r1)
            goto L_0x001f
        L_0x0018:
            r4.cancel()
            invokeChannelUnregistered(r5, r4, r1)
        L_0x001f:
            goto L_0x0035
        L_0x0020:
            r2 = move-exception
            goto L_0x0036
        L_0x0022:
            r2 = move-exception
            r4.cancel()     // Catch:{ all -> 0x0020 }
            invokeChannelUnregistered(r5, r4, r2)     // Catch:{ all -> 0x0020 }
            r0 = 2
            switch(r0) {
                case 0: goto L_0x0018;
                case 1: goto L_0x002e;
                default: goto L_0x002d;
            }
        L_0x002d:
            goto L_0x000d
        L_0x002e:
            boolean r2 = r4.isValid()
            if (r2 != 0) goto L_0x001f
            goto L_0x0014
        L_0x0035:
            return
        L_0x0036:
            switch(r0) {
                case 0: goto L_0x0044;
                case 1: goto L_0x003a;
                default: goto L_0x0039;
            }
        L_0x0039:
            goto L_0x004b
        L_0x003a:
            boolean r3 = r4.isValid()
            if (r3 != 0) goto L_0x004b
            invokeChannelUnregistered(r5, r4, r1)
            goto L_0x004b
        L_0x0044:
            r4.cancel()
            invokeChannelUnregistered(r5, r4, r1)
        L_0x004b:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.NioEventLoop.processSelectedKey(java.nio.channels.SelectionKey, io.netty.channel.nio.NioTask):void");
    }

    private void closeAll() {
        selectAgain();
        Set<SelectionKey> keys = this.selector.keys();
        Collection<AbstractNioChannel> channels = new ArrayList<>(keys.size());
        for (SelectionKey k : keys) {
            Object a = k.attachment();
            if (a instanceof AbstractNioChannel) {
                channels.add((AbstractNioChannel) a);
            } else {
                k.cancel();
                invokeChannelUnregistered((NioTask) a, k, (Throwable) null);
            }
        }
        for (AbstractNioChannel ch : channels) {
            ch.unsafe().close(ch.unsafe().voidPromise());
        }
    }

    private static void invokeChannelUnregistered(NioTask<SelectableChannel> task, SelectionKey k, Throwable cause) {
        try {
            task.channelUnregistered(k.channel(), cause);
        } catch (Exception e) {
            logger.warn("Unexpected exception while running NioTask.channelUnregistered()", (Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public void wakeup(boolean inEventLoop) {
        if (!inEventLoop && this.wakenUp.compareAndSet(false, true)) {
            this.selector.wakeup();
        }
    }

    /* access modifiers changed from: package-private */
    public Selector unwrappedSelector() {
        return this.unwrappedSelector;
    }

    /* access modifiers changed from: package-private */
    public int selectNow() {
        try {
            return this.selector.selectNow();
        } finally {
            if (this.wakenUp.get()) {
                this.selector.wakeup();
            }
        }
    }

    private void select(boolean oldWakenUp) {
        Selector selector2 = this.selector;
        int selectCnt = 0;
        try {
            long currentTimeNanos = System.nanoTime();
            long selectDeadLineNanos = delayNanos(currentTimeNanos) + currentTimeNanos;
            while (true) {
                long timeoutMillis = ((selectDeadLineNanos - currentTimeNanos) + 500000) / Time.NANOS_IN_A_MILLISECOND;
                if (timeoutMillis > 0) {
                    if (hasTasks() && this.wakenUp.compareAndSet(false, true)) {
                        selector2.selectNow();
                        selectCnt = 1;
                        break;
                    }
                    selectCnt++;
                    if (selector2.select(timeoutMillis) != 0 || oldWakenUp || this.wakenUp.get() || hasTasks()) {
                        break;
                    } else if (hasScheduledTasks()) {
                        break;
                    } else if (Thread.interrupted()) {
                        InternalLogger internalLogger = logger;
                        if (internalLogger.isDebugEnabled()) {
                            internalLogger.debug("Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop.");
                        }
                        selectCnt = 1;
                    } else {
                        long time = System.nanoTime();
                        if (time - TimeUnit.MILLISECONDS.toNanos(timeoutMillis) < currentTimeNanos) {
                            int i = SELECTOR_AUTO_REBUILD_THRESHOLD;
                            if (i > 0 && selectCnt >= i) {
                                logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding Selector {}.", Integer.valueOf(selectCnt), selector2);
                                rebuildSelector();
                                selector2 = this.selector;
                                selector2.selectNow();
                                selectCnt = 1;
                                break;
                            }
                        } else {
                            selectCnt = 1;
                        }
                        currentTimeNanos = time;
                    }
                } else if (selectCnt == 0) {
                    selector2.selectNow();
                    selectCnt = 1;
                }
            }
            if (selectCnt > 3) {
                InternalLogger internalLogger2 = logger;
                if (internalLogger2.isDebugEnabled()) {
                    internalLogger2.debug("Selector.select() returned prematurely {} times in a row for Selector {}.", Integer.valueOf(selectCnt - 1), selector2);
                }
            }
        } catch (CancelledKeyException e) {
            InternalLogger internalLogger3 = logger;
            if (internalLogger3.isDebugEnabled()) {
                internalLogger3.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector {} - JDK bug?", selector2, e);
            }
        }
    }

    private void selectAgain() {
        this.needsToSelectAgain = false;
        try {
            this.selector.selectNow();
        } catch (Throwable t) {
            logger.warn("Failed to update SelectionKeys.", t);
        }
    }
}
