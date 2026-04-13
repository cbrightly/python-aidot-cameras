package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.MessageSizeEstimator;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultChannelPipeline implements ChannelPipeline {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final AtomicReferenceFieldUpdater<DefaultChannelPipeline, MessageSizeEstimator.Handle> ESTIMATOR = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelPipeline.class, MessageSizeEstimator.Handle.class, "estimatorHandle");
    /* access modifiers changed from: private */
    public static final String HEAD_NAME = generateName0(HeadContext.class);
    /* access modifiers changed from: private */
    public static final String TAIL_NAME = generateName0(TailContext.class);
    static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) DefaultChannelPipeline.class);
    private static final FastThreadLocal<Map<Class<?>, String>> nameCaches = new FastThreadLocal<Map<Class<?>, String>>() {
        /* access modifiers changed from: protected */
        public Map<Class<?>, String> initialValue() {
            return new WeakHashMap();
        }
    };
    /* access modifiers changed from: private */
    public final Channel channel;
    private Map<EventExecutorGroup, EventExecutor> childExecutors;
    private volatile MessageSizeEstimator.Handle estimatorHandle;
    private boolean firstRegistration = true;
    final AbstractChannelHandlerContext head;
    private PendingHandlerCallback pendingHandlerCallbackHead;
    private boolean registered;
    final AbstractChannelHandlerContext tail;

    static {
        Class<DefaultChannelPipeline> cls = DefaultChannelPipeline.class;
    }

    protected DefaultChannelPipeline(Channel channel2) {
        this.channel = (Channel) ObjectUtil.checkNotNull(channel2, "channel");
        TailContext tailContext = new TailContext(this);
        this.tail = tailContext;
        HeadContext headContext = new HeadContext(this);
        this.head = headContext;
        headContext.next = tailContext;
        tailContext.prev = headContext;
    }

    /* access modifiers changed from: package-private */
    public final MessageSizeEstimator.Handle estimatorHandle() {
        MessageSizeEstimator.Handle handle = this.estimatorHandle;
        if (handle != null) {
            return handle;
        }
        MessageSizeEstimator.Handle handle2 = this.channel.config().getMessageSizeEstimator().newHandle();
        if (!ESTIMATOR.compareAndSet(this, (Object) null, handle2)) {
            return this.estimatorHandle;
        }
        return handle2;
    }

    private AbstractChannelHandlerContext newContext(EventExecutorGroup group, String name, ChannelHandler handler) {
        return new DefaultChannelHandlerContext(this, childExecutor(group), name, handler);
    }

    private EventExecutor childExecutor(EventExecutorGroup group) {
        if (group == null) {
            return null;
        }
        Boolean pinEventExecutor = (Boolean) this.channel.config().getOption(ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP);
        if (pinEventExecutor != null && !pinEventExecutor.booleanValue()) {
            return group.next();
        }
        Map<EventExecutorGroup, EventExecutor> childExecutors2 = this.childExecutors;
        if (childExecutors2 == null) {
            IdentityHashMap identityHashMap = new IdentityHashMap(4);
            this.childExecutors = identityHashMap;
            childExecutors2 = identityHashMap;
        }
        EventExecutor childExecutor = childExecutors2.get(group);
        if (childExecutor != null) {
            return childExecutor;
        }
        EventExecutor childExecutor2 = group.next();
        childExecutors2.put(group, childExecutor2);
        return childExecutor2;
    }

    public final Channel channel() {
        return this.channel;
    }

    public final ChannelPipeline addFirst(String name, ChannelHandler handler) {
        return addFirst((EventExecutorGroup) null, name, handler);
    }

    public final ChannelPipeline addFirst(EventExecutorGroup group, String name, ChannelHandler handler) {
        synchronized (this) {
            checkMultiplicity(handler);
            final AbstractChannelHandlerContext newCtx = newContext(group, filterName(name, handler), handler);
            addFirst0(newCtx);
            if (!this.registered) {
                newCtx.setAddPending();
                callHandlerCallbackLater(newCtx, true);
                return this;
            }
            EventExecutor executor = newCtx.executor();
            if (!executor.inEventLoop()) {
                newCtx.setAddPending();
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
                    }
                });
                return this;
            }
            callHandlerAdded0(newCtx);
            return this;
        }
    }

    private void addFirst0(AbstractChannelHandlerContext newCtx) {
        AbstractChannelHandlerContext nextCtx = this.head.next;
        newCtx.prev = this.head;
        newCtx.next = nextCtx;
        this.head.next = newCtx;
        nextCtx.prev = newCtx;
    }

    public final ChannelPipeline addLast(String name, ChannelHandler handler) {
        return addLast((EventExecutorGroup) null, name, handler);
    }

    public final ChannelPipeline addLast(EventExecutorGroup group, String name, ChannelHandler handler) {
        synchronized (this) {
            checkMultiplicity(handler);
            final AbstractChannelHandlerContext newCtx = newContext(group, filterName(name, handler), handler);
            addLast0(newCtx);
            if (!this.registered) {
                newCtx.setAddPending();
                callHandlerCallbackLater(newCtx, true);
                return this;
            }
            EventExecutor executor = newCtx.executor();
            if (!executor.inEventLoop()) {
                newCtx.setAddPending();
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
                    }
                });
                return this;
            }
            callHandlerAdded0(newCtx);
            return this;
        }
    }

    private void addLast0(AbstractChannelHandlerContext newCtx) {
        AbstractChannelHandlerContext prev = this.tail.prev;
        newCtx.prev = prev;
        newCtx.next = this.tail;
        prev.next = newCtx;
        this.tail.prev = newCtx;
    }

    public final ChannelPipeline addBefore(String baseName, String name, ChannelHandler handler) {
        return addBefore((EventExecutorGroup) null, baseName, name, handler);
    }

    public final ChannelPipeline addBefore(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
        synchronized (this) {
            checkMultiplicity(handler);
            String name2 = filterName(name, handler);
            AbstractChannelHandlerContext ctx = getContextOrDie(baseName);
            final AbstractChannelHandlerContext newCtx = newContext(group, name2, handler);
            addBefore0(ctx, newCtx);
            if (!this.registered) {
                newCtx.setAddPending();
                callHandlerCallbackLater(newCtx, true);
                return this;
            }
            EventExecutor executor = newCtx.executor();
            if (!executor.inEventLoop()) {
                newCtx.setAddPending();
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
                    }
                });
                return this;
            }
            callHandlerAdded0(newCtx);
            return this;
        }
    }

    private static void addBefore0(AbstractChannelHandlerContext ctx, AbstractChannelHandlerContext newCtx) {
        newCtx.prev = ctx.prev;
        newCtx.next = ctx;
        ctx.prev.next = newCtx;
        ctx.prev = newCtx;
    }

    private String filterName(String name, ChannelHandler handler) {
        if (name == null) {
            return generateName(handler);
        }
        checkDuplicateName(name);
        return name;
    }

    public final ChannelPipeline addAfter(String baseName, String name, ChannelHandler handler) {
        return addAfter((EventExecutorGroup) null, baseName, name, handler);
    }

    public final ChannelPipeline addAfter(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
        synchronized (this) {
            checkMultiplicity(handler);
            String name2 = filterName(name, handler);
            AbstractChannelHandlerContext ctx = getContextOrDie(baseName);
            final AbstractChannelHandlerContext newCtx = newContext(group, name2, handler);
            addAfter0(ctx, newCtx);
            if (!this.registered) {
                newCtx.setAddPending();
                callHandlerCallbackLater(newCtx, true);
                return this;
            }
            EventExecutor executor = newCtx.executor();
            if (!executor.inEventLoop()) {
                newCtx.setAddPending();
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
                    }
                });
                return this;
            }
            callHandlerAdded0(newCtx);
            return this;
        }
    }

    private static void addAfter0(AbstractChannelHandlerContext ctx, AbstractChannelHandlerContext newCtx) {
        newCtx.prev = ctx;
        newCtx.next = ctx.next;
        ctx.next.prev = newCtx;
        ctx.next = newCtx;
    }

    public final ChannelPipeline addFirst(ChannelHandler... handlers) {
        return addFirst((EventExecutorGroup) null, handlers);
    }

    public final ChannelPipeline addFirst(EventExecutorGroup executor, ChannelHandler... handlers) {
        if (handlers == null) {
            throw new NullPointerException("handlers");
        } else if (handlers.length == 0 || handlers[0] == null) {
            return this;
        } else {
            int size = 1;
            while (size < handlers.length && handlers[size] != null) {
                size++;
            }
            for (int i = size - 1; i >= 0; i--) {
                addFirst(executor, (String) null, handlers[i]);
            }
            return this;
        }
    }

    public final ChannelPipeline addLast(ChannelHandler... handlers) {
        return addLast((EventExecutorGroup) null, handlers);
    }

    public final ChannelPipeline addLast(EventExecutorGroup executor, ChannelHandler... handlers) {
        if (handlers != null) {
            for (ChannelHandler h : handlers) {
                if (h == null) {
                    break;
                }
                addLast(executor, (String) null, h);
            }
            return this;
        }
        throw new NullPointerException("handlers");
    }

    private String generateName(ChannelHandler handler) {
        Map<Class<?>, String> cache = nameCaches.get();
        Class<?> handlerType = handler.getClass();
        String name = cache.get(handlerType);
        if (name == null) {
            name = generateName0(handlerType);
            cache.put(handlerType, name);
        }
        if (context0(name) == null) {
            return name;
        }
        String baseName = name.substring(0, name.length() - 1);
        int i = 1;
        while (true) {
            String newName = baseName + i;
            if (context0(newName) == null) {
                return newName;
            }
            i++;
        }
    }

    private static String generateName0(Class<?> handlerType) {
        return StringUtil.simpleClassName(handlerType) + "#0";
    }

    public final ChannelPipeline remove(ChannelHandler handler) {
        remove(getContextOrDie(handler));
        return this;
    }

    public final ChannelHandler remove(String name) {
        return remove(getContextOrDie(name)).handler();
    }

    public final <T extends ChannelHandler> T remove(Class<T> handlerType) {
        return remove(getContextOrDie((Class<? extends ChannelHandler>) handlerType)).handler();
    }

    private AbstractChannelHandlerContext remove(final AbstractChannelHandlerContext ctx) {
        if (ctx == this.head || ctx == this.tail) {
            throw new AssertionError();
        }
        synchronized (this) {
            remove0(ctx);
            if (!this.registered) {
                callHandlerCallbackLater(ctx, false);
                return ctx;
            }
            EventExecutor executor = ctx.executor();
            if (!executor.inEventLoop()) {
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerRemoved0(ctx);
                    }
                });
                return ctx;
            }
            callHandlerRemoved0(ctx);
            return ctx;
        }
    }

    /* access modifiers changed from: private */
    public static void remove0(AbstractChannelHandlerContext ctx) {
        AbstractChannelHandlerContext prev = ctx.prev;
        AbstractChannelHandlerContext next = ctx.next;
        prev.next = next;
        next.prev = prev;
    }

    public final ChannelHandler removeFirst() {
        if (this.head.next != this.tail) {
            return remove(this.head.next).handler();
        }
        throw new NoSuchElementException();
    }

    public final ChannelHandler removeLast() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.tail;
        if (abstractChannelHandlerContext != abstractChannelHandlerContext2) {
            return remove(abstractChannelHandlerContext2.prev).handler();
        }
        throw new NoSuchElementException();
    }

    public final ChannelPipeline replace(ChannelHandler oldHandler, String newName, ChannelHandler newHandler) {
        replace(getContextOrDie(oldHandler), newName, newHandler);
        return this;
    }

    public final ChannelHandler replace(String oldName, String newName, ChannelHandler newHandler) {
        return replace(getContextOrDie(oldName), newName, newHandler);
    }

    public final <T extends ChannelHandler> T replace(Class<T> oldHandlerType, String newName, ChannelHandler newHandler) {
        return replace(getContextOrDie((Class<? extends ChannelHandler>) oldHandlerType), newName, newHandler);
    }

    private ChannelHandler replace(final AbstractChannelHandlerContext ctx, String newName, ChannelHandler newHandler) {
        if (ctx == this.head || ctx == this.tail) {
            throw new AssertionError();
        }
        synchronized (this) {
            checkMultiplicity(newHandler);
            if (!ctx.name().equals(newName)) {
                checkDuplicateName(newName);
            }
            final AbstractChannelHandlerContext newCtx = newContext(ctx.executor, newName, newHandler);
            replace0(ctx, newCtx);
            if (!this.registered) {
                callHandlerCallbackLater(newCtx, true);
                callHandlerCallbackLater(ctx, false);
                ChannelHandler handler = ctx.handler();
                return handler;
            }
            EventExecutor executor = ctx.executor();
            if (!executor.inEventLoop()) {
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
                        DefaultChannelPipeline.this.callHandlerRemoved0(ctx);
                    }
                });
                ChannelHandler handler2 = ctx.handler();
                return handler2;
            }
            callHandlerAdded0(newCtx);
            callHandlerRemoved0(ctx);
            return ctx.handler();
        }
    }

    private static void replace0(AbstractChannelHandlerContext oldCtx, AbstractChannelHandlerContext newCtx) {
        AbstractChannelHandlerContext prev = oldCtx.prev;
        AbstractChannelHandlerContext next = oldCtx.next;
        newCtx.prev = prev;
        newCtx.next = next;
        prev.next = newCtx;
        next.prev = newCtx;
        oldCtx.prev = newCtx;
        oldCtx.next = newCtx;
    }

    private static void checkMultiplicity(ChannelHandler handler) {
        if (handler instanceof ChannelHandlerAdapter) {
            ChannelHandlerAdapter h = (ChannelHandlerAdapter) handler;
            if (h.isSharable() || !h.added) {
                h.added = true;
                return;
            }
            throw new ChannelPipelineException(h.getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
        }
    }

    /* access modifiers changed from: private */
    public void callHandlerAdded0(AbstractChannelHandlerContext ctx) {
        boolean removed;
        try {
            ctx.setAddComplete();
            ctx.handler().handlerAdded(ctx);
            return;
        } catch (Throwable th) {
            ctx.setRemoved();
            throw th;
        }
        if (removed) {
            fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerAdded() has thrown an exception; removed.", t));
            return;
        }
        fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerAdded() has thrown an exception; also failed to remove.", t));
    }

    /* access modifiers changed from: private */
    public void callHandlerRemoved0(AbstractChannelHandlerContext ctx) {
        try {
            ctx.handler().handlerRemoved(ctx);
            ctx.setRemoved();
        } catch (Throwable t) {
            fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerRemoved() has thrown an exception.", t));
        }
    }

    /* access modifiers changed from: package-private */
    public final void invokeHandlerAddedIfNeeded() {
        if (!this.channel.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (this.firstRegistration) {
            this.firstRegistration = false;
            callHandlerAddedForAllHandlers();
        }
    }

    public final ChannelHandler first() {
        ChannelHandlerContext first = firstContext();
        if (first == null) {
            return null;
        }
        return first.handler();
    }

    public final ChannelHandlerContext firstContext() {
        if (this.head.next == this.tail) {
            return null;
        }
        return this.head.next;
    }

    public final ChannelHandler last() {
        AbstractChannelHandlerContext last = this.tail.prev;
        if (last == this.head) {
            return null;
        }
        return last.handler();
    }

    public final ChannelHandlerContext lastContext() {
        AbstractChannelHandlerContext last = this.tail.prev;
        if (last == this.head) {
            return null;
        }
        return last;
    }

    public final ChannelHandler get(String name) {
        ChannelHandlerContext ctx = context(name);
        if (ctx == null) {
            return null;
        }
        return ctx.handler();
    }

    public final <T extends ChannelHandler> T get(Class<T> handlerType) {
        ChannelHandlerContext ctx = context((Class<? extends ChannelHandler>) handlerType);
        if (ctx == null) {
            return null;
        }
        return ctx.handler();
    }

    public final ChannelHandlerContext context(String name) {
        if (name != null) {
            return context0(name);
        }
        throw new NullPointerException("name");
    }

    public final ChannelHandlerContext context(ChannelHandler handler) {
        if (handler != null) {
            for (AbstractChannelHandlerContext ctx = this.head.next; ctx != null; ctx = ctx.next) {
                if (ctx.handler() == handler) {
                    return ctx;
                }
            }
            return null;
        }
        throw new NullPointerException("handler");
    }

    public final ChannelHandlerContext context(Class<? extends ChannelHandler> handlerType) {
        if (handlerType != null) {
            for (AbstractChannelHandlerContext ctx = this.head.next; ctx != null; ctx = ctx.next) {
                if (handlerType.isAssignableFrom(ctx.handler().getClass())) {
                    return ctx;
                }
            }
            return null;
        }
        throw new NullPointerException("handlerType");
    }

    public final List<String> names() {
        List<String> list = new ArrayList<>();
        for (AbstractChannelHandlerContext ctx = this.head.next; ctx != null; ctx = ctx.next) {
            list.add(ctx.name());
        }
        return list;
    }

    public final Map<String, ChannelHandler> toMap() {
        Map<String, ChannelHandler> map = new LinkedHashMap<>();
        for (AbstractChannelHandlerContext ctx = this.head.next; ctx != this.tail; ctx = ctx.next) {
            map.put(ctx.name(), ctx.handler());
        }
        return map;
    }

    public final Iterator<Map.Entry<String, ChannelHandler>> iterator() {
        return toMap().entrySet().iterator();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName((Object) this));
        StringBuilder buf = sb.append('{');
        AbstractChannelHandlerContext ctx = this.head.next;
        while (ctx != this.tail) {
            buf.append('(');
            buf.append(ctx.name());
            buf.append(" = ");
            buf.append(ctx.handler().getClass().getName());
            buf.append(')');
            ctx = ctx.next;
            if (ctx == this.tail) {
                break;
            }
            buf.append(", ");
        }
        buf.append('}');
        return buf.toString();
    }

    public final ChannelPipeline fireChannelRegistered() {
        AbstractChannelHandlerContext.invokeChannelRegistered(this.head);
        return this;
    }

    public final ChannelPipeline fireChannelUnregistered() {
        AbstractChannelHandlerContext.invokeChannelUnregistered(this.head);
        return this;
    }

    /* access modifiers changed from: private */
    public synchronized void destroy() {
        destroyUp(this.head.next, false);
    }

    /* access modifiers changed from: private */
    public void destroyUp(AbstractChannelHandlerContext ctx, boolean inEventLoop) {
        Thread currentThread = Thread.currentThread();
        AbstractChannelHandlerContext tail2 = this.tail;
        while (ctx != tail2) {
            EventExecutor executor = ctx.executor();
            if (inEventLoop || executor.inEventLoop(currentThread)) {
                ctx = ctx.next;
                inEventLoop = false;
            } else {
                final AbstractChannelHandlerContext finalCtx = ctx;
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.destroyUp(finalCtx, true);
                    }
                });
                return;
            }
        }
        destroyDown(currentThread, tail2.prev, inEventLoop);
    }

    /* access modifiers changed from: private */
    public void destroyDown(Thread currentThread, AbstractChannelHandlerContext ctx, boolean inEventLoop) {
        AbstractChannelHandlerContext head2 = this.head;
        while (ctx != head2) {
            EventExecutor executor = ctx.executor();
            if (inEventLoop || executor.inEventLoop(currentThread)) {
                synchronized (this) {
                    remove0(ctx);
                }
                callHandlerRemoved0(ctx);
                ctx = ctx.prev;
                inEventLoop = false;
            } else {
                final AbstractChannelHandlerContext finalCtx = ctx;
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.destroyDown(Thread.currentThread(), finalCtx, true);
                    }
                });
                return;
            }
        }
    }

    public final ChannelPipeline fireChannelActive() {
        AbstractChannelHandlerContext.invokeChannelActive(this.head);
        return this;
    }

    public final ChannelPipeline fireChannelInactive() {
        AbstractChannelHandlerContext.invokeChannelInactive(this.head);
        return this;
    }

    public final ChannelPipeline fireExceptionCaught(Throwable cause) {
        AbstractChannelHandlerContext.invokeExceptionCaught(this.head, cause);
        return this;
    }

    public final ChannelPipeline fireUserEventTriggered(Object event) {
        AbstractChannelHandlerContext.invokeUserEventTriggered(this.head, event);
        return this;
    }

    public final ChannelPipeline fireChannelRead(Object msg) {
        AbstractChannelHandlerContext.invokeChannelRead(this.head, msg);
        return this;
    }

    public final ChannelPipeline fireChannelReadComplete() {
        AbstractChannelHandlerContext.invokeChannelReadComplete(this.head);
        return this;
    }

    public final ChannelPipeline fireChannelWritabilityChanged() {
        AbstractChannelHandlerContext.invokeChannelWritabilityChanged(this.head);
        return this;
    }

    public final ChannelFuture bind(SocketAddress localAddress) {
        return this.tail.bind(localAddress);
    }

    public final ChannelFuture connect(SocketAddress remoteAddress) {
        return this.tail.connect(remoteAddress);
    }

    public final ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return this.tail.connect(remoteAddress, localAddress);
    }

    public final ChannelFuture disconnect() {
        return this.tail.disconnect();
    }

    public final ChannelFuture close() {
        return this.tail.close();
    }

    public final ChannelFuture deregister() {
        return this.tail.deregister();
    }

    public final ChannelPipeline flush() {
        this.tail.flush();
        return this;
    }

    public final ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
        return this.tail.bind(localAddress, promise);
    }

    public final ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
        return this.tail.connect(remoteAddress, promise);
    }

    public final ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        return this.tail.connect(remoteAddress, localAddress, promise);
    }

    public final ChannelFuture disconnect(ChannelPromise promise) {
        return this.tail.disconnect(promise);
    }

    public final ChannelFuture close(ChannelPromise promise) {
        return this.tail.close(promise);
    }

    public final ChannelFuture deregister(ChannelPromise promise) {
        return this.tail.deregister(promise);
    }

    public final ChannelPipeline read() {
        this.tail.read();
        return this;
    }

    public final ChannelFuture write(Object msg) {
        return this.tail.write(msg);
    }

    public final ChannelFuture write(Object msg, ChannelPromise promise) {
        return this.tail.write(msg, promise);
    }

    public final ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
        return this.tail.writeAndFlush(msg, promise);
    }

    public final ChannelFuture writeAndFlush(Object msg) {
        return this.tail.writeAndFlush(msg);
    }

    private void checkDuplicateName(String name) {
        if (context0(name) != null) {
            throw new IllegalArgumentException("Duplicate handler name: " + name);
        }
    }

    private AbstractChannelHandlerContext context0(String name) {
        for (AbstractChannelHandlerContext context = this.head.next; context != this.tail; context = context.next) {
            if (context.name().equals(name)) {
                return context;
            }
        }
        return null;
    }

    private AbstractChannelHandlerContext getContextOrDie(String name) {
        AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext) context(name);
        if (ctx != null) {
            return ctx;
        }
        throw new NoSuchElementException(name);
    }

    private AbstractChannelHandlerContext getContextOrDie(ChannelHandler handler) {
        AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext) context(handler);
        if (ctx != null) {
            return ctx;
        }
        throw new NoSuchElementException(handler.getClass().getName());
    }

    private AbstractChannelHandlerContext getContextOrDie(Class<? extends ChannelHandler> handlerType) {
        AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext) context(handlerType);
        if (ctx != null) {
            return ctx;
        }
        throw new NoSuchElementException(handlerType.getName());
    }

    private void callHandlerAddedForAllHandlers() {
        PendingHandlerCallback pendingHandlerCallbackHead2;
        synchronized (this) {
            if (!this.registered) {
                this.registered = true;
                pendingHandlerCallbackHead2 = this.pendingHandlerCallbackHead;
                this.pendingHandlerCallbackHead = null;
            } else {
                throw new AssertionError();
            }
        }
        for (PendingHandlerCallback task = pendingHandlerCallbackHead2; task != null; task = task.next) {
            task.execute();
        }
    }

    private void callHandlerCallbackLater(AbstractChannelHandlerContext ctx, boolean added) {
        if (!this.registered) {
            PendingHandlerCallback task = added ? new PendingHandlerAddedTask(ctx) : new PendingHandlerRemovedTask(ctx);
            PendingHandlerCallback pending = this.pendingHandlerCallbackHead;
            if (pending == null) {
                this.pendingHandlerCallbackHead = task;
                return;
            }
            while (pending.next != null) {
                pending = pending.next;
            }
            pending.next = task;
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: protected */
    public void onUnhandledInboundException(Throwable cause) {
        try {
            logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", cause);
        } finally {
            ReferenceCountUtil.release(cause);
        }
    }

    /* access modifiers changed from: protected */
    public void onUnhandledInboundMessage(Object msg) {
        try {
            logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", msg);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    public final class TailContext extends AbstractChannelHandlerContext implements ChannelInboundHandler {
        TailContext(DefaultChannelPipeline pipeline) {
            super(pipeline, (EventExecutor) null, DefaultChannelPipeline.TAIL_NAME, true, false);
            setAddComplete();
        }

        public ChannelHandler handler() {
            return this;
        }

        public void channelRegistered(ChannelHandlerContext ctx) {
        }

        public void channelUnregistered(ChannelHandlerContext ctx) {
        }

        public void channelActive(ChannelHandlerContext ctx) {
        }

        public void channelInactive(ChannelHandlerContext ctx) {
        }

        public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        }

        public void handlerAdded(ChannelHandlerContext ctx) {
        }

        public void handlerRemoved(ChannelHandlerContext ctx) {
        }

        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
            ReferenceCountUtil.release(evt);
        }

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            DefaultChannelPipeline.this.onUnhandledInboundException(cause);
        }

        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            DefaultChannelPipeline.this.onUnhandledInboundMessage(msg);
        }

        public void channelReadComplete(ChannelHandlerContext ctx) {
        }
    }

    public final class HeadContext extends AbstractChannelHandlerContext implements ChannelOutboundHandler, ChannelInboundHandler {
        private final Channel.Unsafe unsafe;

        HeadContext(DefaultChannelPipeline pipeline) {
            super(pipeline, (EventExecutor) null, DefaultChannelPipeline.HEAD_NAME, false, true);
            this.unsafe = pipeline.channel().unsafe();
            setAddComplete();
        }

        public ChannelHandler handler() {
            return this;
        }

        public void handlerAdded(ChannelHandlerContext ctx) {
        }

        public void handlerRemoved(ChannelHandlerContext ctx) {
        }

        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) {
            this.unsafe.bind(localAddress, promise);
        }

        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            this.unsafe.connect(remoteAddress, localAddress, promise);
        }

        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
            this.unsafe.disconnect(promise);
        }

        public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
            this.unsafe.close(promise);
        }

        public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) {
            this.unsafe.deregister(promise);
        }

        public void read(ChannelHandlerContext ctx) {
            this.unsafe.beginRead();
        }

        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
            this.unsafe.write(msg, promise);
        }

        public void flush(ChannelHandlerContext ctx) {
            this.unsafe.flush();
        }

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.fireExceptionCaught(cause);
        }

        public void channelRegistered(ChannelHandlerContext ctx) {
            DefaultChannelPipeline.this.invokeHandlerAddedIfNeeded();
            ctx.fireChannelRegistered();
        }

        public void channelUnregistered(ChannelHandlerContext ctx) {
            ctx.fireChannelUnregistered();
            if (!DefaultChannelPipeline.this.channel.isOpen()) {
                DefaultChannelPipeline.this.destroy();
            }
        }

        public void channelActive(ChannelHandlerContext ctx) {
            ctx.fireChannelActive();
            readIfIsAutoRead();
        }

        public void channelInactive(ChannelHandlerContext ctx) {
            ctx.fireChannelInactive();
        }

        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ctx.fireChannelRead(msg);
        }

        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.fireChannelReadComplete();
            readIfIsAutoRead();
        }

        private void readIfIsAutoRead() {
            if (DefaultChannelPipeline.this.channel.config().isAutoRead()) {
                DefaultChannelPipeline.this.channel.read();
            }
        }

        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
            ctx.fireUserEventTriggered(evt);
        }

        public void channelWritabilityChanged(ChannelHandlerContext ctx) {
            ctx.fireChannelWritabilityChanged();
        }
    }

    public static abstract class PendingHandlerCallback implements Runnable {
        final AbstractChannelHandlerContext ctx;
        PendingHandlerCallback next;

        /* access modifiers changed from: package-private */
        public abstract void execute();

        PendingHandlerCallback(AbstractChannelHandlerContext ctx2) {
            this.ctx = ctx2;
        }
    }

    public final class PendingHandlerAddedTask extends PendingHandlerCallback {
        PendingHandlerAddedTask(AbstractChannelHandlerContext ctx) {
            super(ctx);
        }

        public void run() {
            DefaultChannelPipeline.this.callHandlerAdded0(this.ctx);
        }

        /* access modifiers changed from: package-private */
        public void execute() {
            EventExecutor executor = this.ctx.executor();
            if (executor.inEventLoop()) {
                DefaultChannelPipeline.this.callHandlerAdded0(this.ctx);
                return;
            }
            try {
                executor.execute(this);
            } catch (RejectedExecutionException e) {
                if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                    DefaultChannelPipeline.logger.warn("Can't invoke handlerAdded() as the EventExecutor {} rejected it, removing handler {}.", executor, this.ctx.name(), e);
                }
                DefaultChannelPipeline.remove0(this.ctx);
                this.ctx.setRemoved();
            }
        }
    }

    public final class PendingHandlerRemovedTask extends PendingHandlerCallback {
        PendingHandlerRemovedTask(AbstractChannelHandlerContext ctx) {
            super(ctx);
        }

        public void run() {
            DefaultChannelPipeline.this.callHandlerRemoved0(this.ctx);
        }

        /* access modifiers changed from: package-private */
        public void execute() {
            EventExecutor executor = this.ctx.executor();
            if (executor.inEventLoop()) {
                DefaultChannelPipeline.this.callHandlerRemoved0(this.ctx);
                return;
            }
            try {
                executor.execute(this);
            } catch (RejectedExecutionException e) {
                if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                    DefaultChannelPipeline.logger.warn("Can't invoke handlerRemoved() as the EventExecutor {} rejected it, removing handler {}.", executor, this.ctx.name(), e);
                }
                this.ctx.setRemoved();
            }
        }
    }
}
