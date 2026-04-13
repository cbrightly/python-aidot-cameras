package org.glassfish.grizzly.filterchain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Appendable;
import org.glassfish.grizzly.Appender;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.Holder;

public class FilterChainContext implements AttributeStorage {
    private static final ThreadCache.CachedTypeIndex<FilterChainContext> CACHE_IDX;
    private static final NextAction INVOKE_ACTION = new InvokeAction();
    public static final int NO_FILTER_INDEX = Integer.MIN_VALUE;
    private static final NextAction RERUN_FILTER_ACTION = new RerunFilterAction();
    private static final NextAction STOP_ACTION = new StopAction();
    private static final NextAction SUSPEND_ACTION = new SuspendAction();
    /* access modifiers changed from: private */
    public static final Logger logger;
    private Holder<?> addressHolder;
    private final InvokeAction cachedInvokeAction = new InvokeAction();
    private final StopAction cachedStopAction = new StopAction();
    private Closeable closeable;
    private final List<CompletionListener> completionListeners = new ArrayList(2);
    private final Runnable contextRunnable = new Runnable() {
        public void run() {
            try {
                if (FilterChainContext.this.state == State.SUSPEND) {
                    State unused = FilterChainContext.this.state = State.RUNNING;
                }
                ProcessorExecutor.execute(FilterChainContext.this.internalContext);
            } catch (Exception e) {
                FilterChainContext.logger.log(Level.FINE, "Exception during running Processor", e);
            }
        }
    };
    private final List<CopyListener> copyListeners = new ArrayList(2);
    private int endIdx;
    protected FilterChainEvent event;
    private int filterIdx = Integer.MIN_VALUE;
    final InternalContextImpl internalContext = new InternalContextImpl(this);
    private Object message;
    private Operation operation = Operation.NONE;
    protected CompletionHandler<FilterChainContext> operationCompletionHandler;
    NextAction predefinedNextAction;
    private int startIdx;
    /* access modifiers changed from: private */
    public volatile State state;
    final TransportContext transportFilterContext = new TransportContext();

    public interface CompletionListener {
        void onComplete(FilterChainContext filterChainContext);
    }

    public interface CopyListener {
        void onCopy(FilterChainContext filterChainContext, FilterChainContext filterChainContext2);
    }

    public enum Operation {
        NONE,
        ACCEPT,
        CONNECT,
        READ,
        WRITE,
        EVENT,
        CLOSE
    }

    public enum State {
        RUNNING,
        SUSPEND
    }

    static {
        Class<FilterChainContext> cls = FilterChainContext.class;
        logger = Grizzly.logger(cls);
        CACHE_IDX = ThreadCache.obtainIndex(cls, 8);
    }

    public static FilterChainContext create(Connection connection) {
        return create(connection, connection);
    }

    public static FilterChainContext create(Connection connection, Closeable closeable2) {
        FilterChainContext context = (FilterChainContext) ThreadCache.takeFromCache(CACHE_IDX);
        if (context == null) {
            context = new FilterChainContext();
        }
        context.setConnection(connection);
        context.setCloseable(closeable2);
        boolean unused = context.getTransportContext().isBlocking = connection.isBlocking();
        return context;
    }

    public Runnable suspend() {
        this.internalContext.suspend();
        this.state = State.SUSPEND;
        return getRunnable();
    }

    public void resume() {
        this.internalContext.resume();
        getRunnable().run();
    }

    public void resumeNext() {
        resume(getInvokeAction());
    }

    public void resume(NextAction nextAction) {
        this.internalContext.resume();
        try {
            if (this.state == State.SUSPEND) {
                this.state = State.RUNNING;
            }
            this.predefinedNextAction = nextAction;
            ProcessorExecutor.execute(this.internalContext);
        } catch (Exception e) {
            logger.log(Level.FINE, "Exception during running Processor", e);
        }
    }

    public void fork() {
        fork((NextAction) null);
    }

    public void fork(NextAction nextAction) {
        try {
            this.predefinedNextAction = getForkAction(nextAction);
            ProcessorExecutor.execute(this.internalContext);
        } catch (Exception e) {
            logger.log(Level.FINE, "Exception during running Processor", e);
        }
    }

    public State state() {
        return this.state;
    }

    public int nextFilterIdx() {
        int i = this.filterIdx + 1;
        this.filterIdx = i;
        return i;
    }

    public int previousFilterIdx() {
        int i = this.filterIdx - 1;
        this.filterIdx = i;
        return i;
    }

    public int getFilterIdx() {
        return this.filterIdx;
    }

    public void setFilterIdx(int index) {
        this.filterIdx = index;
    }

    public int getStartIdx() {
        return this.startIdx;
    }

    public void setStartIdx(int startIdx2) {
        this.startIdx = startIdx2;
    }

    public int getEndIdx() {
        return this.endIdx;
    }

    public void setEndIdx(int endIdx2) {
        this.endIdx = endIdx2;
    }

    public FilterChain getFilterChain() {
        return (FilterChain) this.internalContext.getProcessor();
    }

    public Connection getConnection() {
        return this.internalContext.getConnection();
    }

    /* access modifiers changed from: package-private */
    public void setConnection(Connection connection) {
        this.internalContext.setConnection(connection);
    }

    public Closeable getCloseable() {
        return this.closeable;
    }

    /* access modifiers changed from: package-private */
    public void setCloseable(Closeable closeable2) {
        this.closeable = closeable2 != null ? closeable2 : getConnection();
    }

    public <T> T getMessage() {
        return this.message;
    }

    public void setMessage(Object message2) {
        this.message = message2;
    }

    public Holder<?> getAddressHolder() {
        return this.addressHolder;
    }

    public void setAddressHolder(Holder<?> addressHolder2) {
        this.addressHolder = addressHolder2;
    }

    public Object getAddress() {
        Holder<?> holder = this.addressHolder;
        if (holder != null) {
            return holder.get();
        }
        return null;
    }

    public void setAddress(Object address) {
        this.addressHolder = Holder.staticHolder(address);
    }

    /* access modifiers changed from: protected */
    public final Runnable getRunnable() {
        return this.contextRunnable;
    }

    public TransportContext getTransportContext() {
        return this.transportFilterContext;
    }

    public final Context getInternalContext() {
        return this.internalContext;
    }

    /* access modifiers changed from: package-private */
    public Operation getOperation() {
        return this.operation;
    }

    /* access modifiers changed from: package-private */
    public void setOperation(Operation operation2) {
        this.operation = operation2;
    }

    public NextAction getInvokeAction() {
        return INVOKE_ACTION;
    }

    public NextAction getInvokeAction(Object unparsedChunk) {
        if (unparsedChunk == null) {
            return INVOKE_ACTION;
        }
        this.cachedInvokeAction.setUnparsedChunk(unparsedChunk);
        return this.cachedInvokeAction;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0050, code lost:
        throw new java.lang.IllegalArgumentException("Remainder has to be either " + org.glassfish.grizzly.Buffer.class.getName() + " or " + org.glassfish.grizzly.Appendable.class.getName() + " but was " + r4.getClass().getName());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0051, code lost:
        r3.cachedInvokeAction.setIncompleteChunk(r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0058, code lost:
        return r3.cachedInvokeAction;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0005, code lost:
        if (r5 != null) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        if ((r4 instanceof org.glassfish.grizzly.Buffer) == false) goto L_0x0011;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000b, code lost:
        r5 = org.glassfish.grizzly.memory.Buffers.getBufferAppender(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0011, code lost:
        r5 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
        if ((r4 instanceof org.glassfish.grizzly.Appendable) == false) goto L_0x0016;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <E> org.glassfish.grizzly.filterchain.NextAction getInvokeAction(E r4, org.glassfish.grizzly.Appender<E> r5) {
        /*
            r3 = this;
            if (r4 != 0) goto L_0x0005
            org.glassfish.grizzly.filterchain.NextAction r0 = INVOKE_ACTION
            return r0
        L_0x0005:
            if (r5 != 0) goto L_0x0051
            boolean r0 = r4 instanceof org.glassfish.grizzly.Buffer
            if (r0 == 0) goto L_0x0011
            r0 = 1
            org.glassfish.grizzly.Appender r5 = org.glassfish.grizzly.memory.Buffers.getBufferAppender(r0)
            goto L_0x0051
        L_0x0011:
            boolean r0 = r4 instanceof org.glassfish.grizzly.Appendable
            if (r0 == 0) goto L_0x0016
            goto L_0x0051
        L_0x0016:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Remainder has to be either "
            r1.append(r2)
            java.lang.Class<org.glassfish.grizzly.Buffer> r2 = org.glassfish.grizzly.Buffer.class
            java.lang.String r2 = r2.getName()
            r1.append(r2)
            java.lang.String r2 = " or "
            r1.append(r2)
            java.lang.Class<org.glassfish.grizzly.Appendable> r2 = org.glassfish.grizzly.Appendable.class
            java.lang.String r2 = r2.getName()
            r1.append(r2)
            java.lang.String r2 = " but was "
            r1.append(r2)
            java.lang.Class r2 = r4.getClass()
            java.lang.String r2 = r2.getName()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0051:
            org.glassfish.grizzly.filterchain.InvokeAction r0 = r3.cachedInvokeAction
            r0.setIncompleteChunk(r4, r5)
            org.glassfish.grizzly.filterchain.InvokeAction r0 = r3.cachedInvokeAction
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.filterchain.FilterChainContext.getInvokeAction(java.lang.Object, org.glassfish.grizzly.Appender):org.glassfish.grizzly.filterchain.NextAction");
    }

    public NextAction getStopAction() {
        return STOP_ACTION;
    }

    public NextAction getStopAction(Object incompleteChunk) {
        if (incompleteChunk instanceof Buffer) {
            return getStopAction((Buffer) incompleteChunk, Buffers.getBufferAppender(true));
        }
        return getStopAction(incompleteChunk, (Appender) null);
    }

    public <E> NextAction getStopAction(E incompleteChunk, Appender<E> appender) {
        if (incompleteChunk == null) {
            return STOP_ACTION;
        }
        if (appender != null || (incompleteChunk instanceof Appendable)) {
            this.cachedStopAction.setIncompleteChunk(incompleteChunk, appender);
            return this.cachedStopAction;
        }
        throw new IllegalArgumentException("Remainder has to be either " + Buffer.class.getName() + " or " + Appendable.class.getName() + " but was " + incompleteChunk.getClass().getName());
    }

    public NextAction getForkAction() {
        return getForkAction((NextAction) null);
    }

    public NextAction getForkAction(NextAction nextAction) {
        FilterChainContext contextCopy = copy();
        contextCopy.addressHolder = this.addressHolder;
        contextCopy.predefinedNextAction = nextAction;
        return new ForkAction(contextCopy);
    }

    @Deprecated
    public NextAction getSuspendingStopAction() {
        return getForkAction();
    }

    public NextAction getSuspendAction() {
        return SUSPEND_ACTION;
    }

    public NextAction getRerunFilterAction() {
        return RERUN_FILTER_ACTION;
    }

    public ReadResult read() {
        FilterChainContext newContext = getFilterChain().obtainFilterChainContext(getConnection());
        newContext.closeable = this.closeable;
        newContext.operation = Operation.READ;
        newContext.transportFilterContext.configureBlocking(true);
        newContext.startIdx = 0;
        newContext.filterIdx = 0;
        newContext.endIdx = this.filterIdx;
        getAttributes().copyTo(newContext.getAttributes());
        ReadResult rr = getFilterChain().read(newContext);
        newContext.completeAndRecycle();
        return rr;
    }

    public void write(Object message2) {
        write((Object) null, message2, (CompletionHandler<WriteResult>) null, (PushBackHandler) null, (MessageCloner) null, this.transportFilterContext.isBlocking());
    }

    public void write(Object message2, boolean blocking) {
        write((Object) null, message2, (CompletionHandler<WriteResult>) null, (PushBackHandler) null, (MessageCloner) null, blocking);
    }

    public void write(Object message2, CompletionHandler<WriteResult> completionHandler) {
        write((Object) null, message2, completionHandler, (PushBackHandler) null, (MessageCloner) null, this.transportFilterContext.isBlocking());
    }

    public void write(Object message2, CompletionHandler<WriteResult> completionHandler, boolean blocking) {
        write((Object) null, message2, completionHandler, (PushBackHandler) null, (MessageCloner) null, blocking);
    }

    public void write(Object address, Object message2, CompletionHandler<WriteResult> completionHandler) {
        write(address, message2, completionHandler, (PushBackHandler) null, (MessageCloner) null, this.transportFilterContext.isBlocking());
    }

    public void write(Object address, Object message2, CompletionHandler<WriteResult> completionHandler, boolean blocking) {
        write(address, message2, completionHandler, (PushBackHandler) null, (MessageCloner) null, blocking);
    }

    @Deprecated
    public void write(Object address, Object message2, CompletionHandler<WriteResult> completionHandler, PushBackHandler pushBackHandler) {
        write(address, message2, completionHandler, pushBackHandler, this.transportFilterContext.isBlocking());
    }

    @Deprecated
    public void write(Object address, Object message2, CompletionHandler<WriteResult> completionHandler, PushBackHandler pushBackHandler, boolean blocking) {
        write(address, message2, completionHandler, pushBackHandler, (MessageCloner) null, blocking);
    }

    public void write(Object address, Object message2, CompletionHandler<WriteResult> completionHandler, MessageCloner cloner) {
        write(address, message2, completionHandler, (PushBackHandler) null, cloner, this.transportFilterContext.isBlocking());
    }

    @Deprecated
    public void write(Object address, Object message2, CompletionHandler<WriteResult> completionHandler, PushBackHandler pushBackHandler, MessageCloner cloner) {
        write(address, message2, completionHandler, pushBackHandler, cloner, this.transportFilterContext.isBlocking());
    }

    public void write(Object address, Object message2, CompletionHandler<WriteResult> completionHandler, MessageCloner cloner, boolean blocking) {
        write(address, message2, completionHandler, (PushBackHandler) null, cloner, blocking);
    }

    @Deprecated
    public void write(Object address, Object message2, CompletionHandler<WriteResult> completionHandler, PushBackHandler pushBackHandler, MessageCloner cloner, boolean blocking) {
        FilterChainContext newContext = getFilterChain().obtainFilterChainContext(getConnection());
        newContext.operation = Operation.WRITE;
        newContext.transportFilterContext.configureBlocking(blocking);
        newContext.message = message2;
        newContext.addressHolder = address == null ? this.addressHolder : Holder.staticHolder(address);
        newContext.closeable = this.closeable;
        TransportContext transportContext = newContext.transportFilterContext;
        transportContext.completionHandler = completionHandler;
        transportContext.pushBackHandler = pushBackHandler;
        transportContext.cloner = cloner;
        int i = this.filterIdx;
        newContext.startIdx = i - 1;
        newContext.filterIdx = i - 1;
        newContext.endIdx = -1;
        getAttributes().copyTo(newContext.getAttributes());
        ProcessorExecutor.execute(newContext.internalContext);
    }

    public void flush(CompletionHandler completionHandler) {
        FilterChainContext newContext = getFilterChain().obtainFilterChainContext(getConnection());
        newContext.operation = Operation.EVENT;
        newContext.closeable = this.closeable;
        newContext.event = TransportFilter.createFlushEvent(completionHandler);
        newContext.transportFilterContext.configureBlocking(this.transportFilterContext.isBlocking());
        newContext.addressHolder = this.addressHolder;
        int i = this.filterIdx;
        newContext.startIdx = i - 1;
        newContext.filterIdx = i - 1;
        newContext.endIdx = -1;
        getAttributes().copyTo(newContext.getAttributes());
        ProcessorExecutor.execute(newContext.internalContext);
    }

    public void notifyUpstream(FilterChainEvent event2) {
        notifyUpstream(event2, (CompletionHandler<FilterChainContext>) null);
    }

    public void notifyUpstream(FilterChainEvent event2, CompletionHandler<FilterChainContext> completionHandler) {
        FilterChainContext newContext = getFilterChain().obtainFilterChainContext(getConnection());
        newContext.setOperation(Operation.EVENT);
        newContext.event = event2;
        newContext.closeable = this.closeable;
        newContext.addressHolder = this.addressHolder;
        int i = this.filterIdx;
        newContext.startIdx = i + 1;
        newContext.filterIdx = i + 1;
        newContext.endIdx = this.endIdx;
        getAttributes().copyTo(newContext.getAttributes());
        newContext.operationCompletionHandler = completionHandler;
        ProcessorExecutor.execute(newContext.internalContext);
    }

    public void notifyDownstream(FilterChainEvent event2) {
        notifyDownstream(event2, (CompletionHandler<FilterChainContext>) null);
    }

    public void notifyDownstream(FilterChainEvent event2, CompletionHandler<FilterChainContext> completionHandler) {
        FilterChainContext newContext = getFilterChain().obtainFilterChainContext(getConnection());
        newContext.setOperation(Operation.EVENT);
        newContext.event = event2;
        newContext.closeable = this.closeable;
        newContext.addressHolder = this.addressHolder;
        int i = this.filterIdx;
        newContext.startIdx = i - 1;
        newContext.filterIdx = i - 1;
        newContext.endIdx = -1;
        getAttributes().copyTo(newContext.getAttributes());
        newContext.operationCompletionHandler = completionHandler;
        ProcessorExecutor.execute(newContext.internalContext);
    }

    public void fail(Throwable error) {
        getFilterChain().fail(this, error);
    }

    public AttributeHolder getAttributes() {
        return this.internalContext.getAttributes();
    }

    public final void addCompletionListener(CompletionListener listener) {
        this.completionListeners.add(listener);
    }

    public final boolean removeCompletionListener(CompletionListener listener) {
        return this.completionListeners.remove(listener);
    }

    public final void addCopyListener(CopyListener listener) {
        this.copyListeners.add(listener);
    }

    public final boolean removeCopyListener(CopyListener listener) {
        return this.copyListeners.remove(listener);
    }

    public final MemoryManager getMemoryManager() {
        return getConnection().getMemoryManager();
    }

    public FilterChainContext copy() {
        FilterChainContext newContext = getFilterChain().obtainFilterChainContext(getConnection());
        newContext.setOperation(getOperation());
        newContext.setCloseable(getCloseable());
        this.internalContext.softCopyTo(newContext.internalContext);
        newContext.setStartIdx(getStartIdx());
        newContext.setEndIdx(getEndIdx());
        newContext.setFilterIdx(getFilterIdx());
        getAttributes().copyTo(newContext.getAttributes());
        notifyCopy(this, newContext, this.copyListeners);
        return newContext;
    }

    public void reset() {
        this.cachedInvokeAction.reset();
        this.cachedStopAction.reset();
        this.message = null;
        this.closeable = null;
        this.event = null;
        this.addressHolder = null;
        this.filterIdx = Integer.MIN_VALUE;
        this.state = State.RUNNING;
        this.operationCompletionHandler = null;
        this.operation = Operation.NONE;
        this.internalContext.reset();
        this.transportFilterContext.reset();
        this.copyListeners.clear();
        this.predefinedNextAction = null;
    }

    public void completeAndRecycle() {
        notifyComplete(this, this.completionListeners);
        reset();
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public void completeAndRelease() {
        notifyComplete(this, this.completionListeners);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(384);
        sb.append("FilterChainContext [");
        sb.append("connection=");
        sb.append(getConnection());
        sb.append(", closeable=");
        sb.append(getCloseable());
        sb.append(", operation=");
        sb.append(getOperation());
        sb.append(", message=");
        sb.append(String.valueOf((char[]) getMessage()));
        sb.append(", address=");
        sb.append(getAddress());
        sb.append(']');
        return sb.toString();
    }

    /* renamed from: org.glassfish.grizzly.filterchain.FilterChainContext$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$IOEvent;

        static {
            int[] iArr = new int[IOEvent.values().length];
            $SwitchMap$org$glassfish$grizzly$IOEvent = iArr;
            try {
                iArr[IOEvent.READ.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$IOEvent[IOEvent.WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$IOEvent[IOEvent.ACCEPTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$IOEvent[IOEvent.CONNECTED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$IOEvent[IOEvent.CLOSED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static Operation ioEvent2Operation(IOEvent ioEvent) {
        switch (AnonymousClass2.$SwitchMap$org$glassfish$grizzly$IOEvent[ioEvent.ordinal()]) {
            case 1:
                return Operation.READ;
            case 2:
                return Operation.WRITE;
            case 3:
                return Operation.ACCEPT;
            case 4:
                return Operation.CONNECT;
            case 5:
                return Operation.CLOSE;
            default:
                return Operation.NONE;
        }
    }

    public static final class TransportContext {
        MessageCloner cloner;
        CompletionHandler completionHandler;
        /* access modifiers changed from: private */
        public boolean isBlocking;
        @Deprecated
        PushBackHandler pushBackHandler;

        public void configureBlocking(boolean isBlocking2) {
            this.isBlocking = isBlocking2;
        }

        public boolean isBlocking() {
            return this.isBlocking;
        }

        public CompletionHandler getCompletionHandler() {
            return this.completionHandler;
        }

        public void setCompletionHandler(CompletionHandler completionHandler2) {
            this.completionHandler = completionHandler2;
        }

        @Deprecated
        public PushBackHandler getPushBackHandler() {
            return this.pushBackHandler;
        }

        @Deprecated
        public void setPushBackHandler(PushBackHandler pushBackHandler2) {
            this.pushBackHandler = pushBackHandler2;
        }

        public MessageCloner getMessageCloner() {
            return this.cloner;
        }

        public void setMessageCloner(MessageCloner cloner2) {
            this.cloner = cloner2;
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.isBlocking = false;
            this.completionHandler = null;
            this.pushBackHandler = null;
            this.cloner = null;
        }
    }

    static void notifyComplete(FilterChainContext context, List<CompletionListener> completionListeners2) {
        for (int i = completionListeners2.size() - 1; i >= 0; i--) {
            completionListeners2.get(i).onComplete(context);
        }
        completionListeners2.clear();
    }

    static void notifyCopy(FilterChainContext srcContext, FilterChainContext copiedContext, List<CopyListener> copyListeners2) {
        int size = copyListeners2.size();
        for (int i = 0; i < size; i++) {
            copyListeners2.get(i).onCopy(srcContext, copiedContext);
        }
    }
}
