package org.glassfish.grizzly.filterchain;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Appendable;
import org.glassfish.grizzly.Appender;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.ProcessorResult;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.AsyncQueueEnabledTransport;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.Futures;
import org.glassfish.grizzly.utils.NullaryFunction;

public final class DefaultFilterChain extends ListFacadeFilterChain {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOGGER = Grizzly.logger(DefaultFilterChain.class);
    private final FiltersStateFactory filtersStateFactory;

    static {
        Class<DefaultFilterChain> cls = DefaultFilterChain.class;
    }

    public DefaultFilterChain() {
        this(new ArrayList());
    }

    public DefaultFilterChain(Collection<Filter> initialFilters) {
        super(new ArrayList(initialFilters));
        this.filtersStateFactory = new FiltersStateFactory();
    }

    public ProcessorResult process(Context context) {
        if (isEmpty()) {
            return ProcessorResult.createComplete();
        }
        InternalContextImpl internalContext = (InternalContextImpl) context;
        FilterChainContext filterChainContext = internalContext.filterChainContext;
        if (filterChainContext.getOperation() == FilterChainContext.Operation.NONE) {
            IOEvent ioEvent = internalContext.getIoEvent();
            if (ioEvent == IOEvent.WRITE) {
                return ((AsyncQueueEnabledTransport) context.getConnection().getTransport()).getAsyncQueueIO().getWriter().processAsync(context).toProcessorResult();
            }
            filterChainContext.setOperation(FilterChainContext.ioEvent2Operation(ioEvent));
        }
        return execute(filterChainContext);
    }

    public ProcessorResult execute(FilterChainContext ctx) {
        FilterExecutor executor = ExecutorResolver.resolve(ctx);
        if (ctx.getFilterIdx() == Integer.MIN_VALUE) {
            executor.initIndexes(ctx);
        }
        FiltersState filtersState = obtainFiltersState(ctx.getConnection());
        int end = ctx.getEndIdx();
        do {
            try {
                FilterExecution execution = executeChainPart(ctx, executor, ctx.getFilterIdx(), end, filtersState);
                switch (execution.type) {
                    case 1:
                        return ProcessorResult.createTerminate();
                    case 2:
                        FilterChainContext ctx2 = execution.getContext();
                        int idx = filtersState.peekUnparsedIdx(ctx2.getOperation(), ctx2.getStartIdx(), ctx2.getEndIdx());
                        if (idx == -1) {
                            return ProcessorResult.createReregister(ctx2.internalContext);
                        }
                        ctx2.setMessage((Object) null);
                        ctx2.setFilterIdx(idx);
                        return ProcessorResult.createRerun(ctx2.internalContext);
                }
            } catch (Throwable e) {
                LOGGER.log(e instanceof IOException ? Level.FINE : Level.WARNING, LogMessages.WARNING_GRIZZLY_FILTERCHAIN_EXCEPTION(), e);
                throwChain(ctx, executor, e);
                ctx.getCloseable().closeWithReason(Exceptions.makeIOException(e));
                return ProcessorResult.createError(e);
            }
        } while (prepareRemainder(ctx, filtersState));
        return ProcessorResult.createComplete();
    }

    /* access modifiers changed from: protected */
    public FilterExecution executeChainPart(FilterChainContext ctx, FilterExecutor executor, int start, int end, FiltersState filtersState) {
        Filter currentFilter;
        int lastNextActionType;
        NextAction lastNextAction;
        FilterChainContext filterChainContext = ctx;
        FilterExecutor filterExecutor = executor;
        Filter currentFilter2 = null;
        int lastNextActionType2 = 0;
        StopAction lastNextAction2 = null;
        int i = start;
        while (true) {
            if (i == end) {
                FiltersState filtersState2 = filtersState;
                currentFilter = currentFilter2;
                lastNextActionType = lastNextActionType2;
                lastNextAction = lastNextAction2;
                break;
            }
            currentFilter = get(i);
            if (filterChainContext.predefinedNextAction == null) {
                checkStoredMessage(filterChainContext, filtersState, i);
                lastNextAction = executeFilter(filterExecutor, currentFilter, filterChainContext);
            } else {
                FiltersState filtersState3 = filtersState;
                NextAction lastNextAction3 = filterChainContext.predefinedNextAction;
                filterChainContext.predefinedNextAction = null;
                lastNextAction = lastNextAction3;
            }
            lastNextActionType = lastNextAction.type();
            if (lastNextActionType != 0) {
                break;
            }
            InvokeAction invokeAction = (InvokeAction) lastNextAction;
            Object chunk = invokeAction.getChunk();
            if (chunk != null) {
                storeMessage(ctx, filtersState, invokeAction.isIncomplete(), i, chunk, invokeAction.getAppender());
            }
            i = filterExecutor.getNextFilter(filterChainContext);
            filterChainContext.setFilterIdx(i);
            currentFilter2 = currentFilter;
            lastNextAction2 = lastNextAction;
            lastNextActionType2 = lastNextActionType;
        }
        switch (lastNextActionType) {
            case 0:
                notifyComplete(ctx);
                break;
            case 1:
                if (currentFilter != null) {
                    StopAction stopAction = (StopAction) lastNextAction;
                    Object chunk2 = stopAction.getIncompleteChunk();
                    if (chunk2 != null) {
                        storeMessage(ctx, filtersState, true, i, chunk2, stopAction.getAppender());
                        break;
                    }
                } else {
                    throw new AssertionError();
                }
                break;
            case 2:
                return FilterExecution.createTerminate();
            case 5:
                return FilterExecution.createReExecute(((ForkAction) lastNextAction).getContext());
        }
        return FilterExecution.createContinue();
    }

    /* access modifiers changed from: protected */
    public NextAction executeFilter(FilterExecutor executor, Filter currentFilter, FilterChainContext ctx) {
        NextAction nextNextAction;
        do {
            Logger logger = LOGGER;
            Level level = Level.FINEST;
            if (logger.isLoggable(level)) {
                logger.log(Level.FINE, "before filter execution. filter={0} context={1}", new Object[]{currentFilter, ctx});
            }
            nextNextAction = executor.execute(currentFilter, ctx);
            if (logger.isLoggable(level)) {
                logger.log(Level.FINE, "after execute filter. filter={0} context={1} nextAction={2}", new Object[]{currentFilter, ctx, nextNextAction});
            }
        } while (nextNextAction.type() == 4);
        return nextNextAction;
    }

    private static boolean prepareRemainder(FilterChainContext ctx, FiltersState filtersState) {
        int idx = filtersState.peekUnparsedIdx(ctx.getOperation(), ctx.getStartIdx(), ctx.getEndIdx());
        if (idx == -1) {
            return false;
        }
        ctx.setFilterIdx(idx);
        ctx.setMessage((Object) null);
        return true;
    }

    public void read(Connection connection, CompletionHandler<ReadResult> completionHandler) {
        FilterChainContext context = obtainFilterChainContext(connection);
        context.setOperation(FilterChainContext.Operation.READ);
        context.getTransportContext().configureBlocking(true);
        ExecutorResolver.resolve(context).initIndexes(context);
        try {
            Futures.notifyResult((FutureImpl) null, completionHandler, read(context));
        } catch (IOException e) {
            Futures.notifyFailure((FutureImpl) null, completionHandler, e);
        }
    }

    public ReadResult read(FilterChainContext context) {
        Connection connection = context.getConnection();
        if (context.getTransportContext().isBlocking()) {
            FutureImpl<FilterChainContext> future = Futures.createUnsafeFuture();
            context.operationCompletionHandler = Futures.toCompletionHandler(future);
            FilterExecutor executor = ExecutorResolver.resolve(context);
            FiltersState filtersState = obtainFiltersState(connection);
            do {
                if (!prepareRemainder(context, filtersState)) {
                    context.setFilterIdx(0);
                    context.setMessage((Object) null);
                }
                executeChainPart(context, executor, context.getFilterIdx(), context.getEndIdx(), filtersState);
            } while (!future.isDone());
            try {
                FilterChainContext retContext = (FilterChainContext) future.get();
                ReadResult rr = ReadResult.create(connection);
                rr.setMessage(retContext.getMessage());
                rr.setSrcAddressHolder(retContext.getAddressHolder());
                future.recycle(false);
                return rr;
            } catch (ExecutionException e) {
                Throwable t = e.getCause();
                if (t instanceof IOException) {
                    throw ((IOException) t);
                }
                throw new IOException(t);
            } catch (InterruptedException e2) {
                throw new IOException(e2);
            }
        } else {
            throw new IllegalStateException("FilterChain doesn't support standalone non blocking read. Please use Filter instead.");
        }
    }

    public void write(Connection connection, Object dstAddress, Object message, CompletionHandler<WriteResult> completionHandler) {
        write(connection, dstAddress, message, completionHandler, (MessageCloner) null);
    }

    public void write(Connection connection, Object dstAddress, Object message, CompletionHandler<WriteResult> completionHandler, MessageCloner messageCloner) {
        FilterChainContext context = obtainFilterChainContext(connection);
        FilterChainContext.TransportContext transportContext = context.transportFilterContext;
        transportContext.completionHandler = completionHandler;
        transportContext.cloner = messageCloner;
        context.setAddress(dstAddress);
        context.setMessage(message);
        context.setOperation(FilterChainContext.Operation.WRITE);
        ProcessorExecutor.execute(context.internalContext);
    }

    @Deprecated
    public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler) {
        FilterChainContext context = obtainFilterChainContext(connection);
        FilterChainContext.TransportContext transportContext = context.transportFilterContext;
        transportContext.completionHandler = completionHandler;
        transportContext.pushBackHandler = pushBackHandler;
        context.setAddress(dstAddress);
        context.setMessage(message);
        context.setOperation(FilterChainContext.Operation.WRITE);
        ProcessorExecutor.execute(context.internalContext);
    }

    public void flush(Connection connection, CompletionHandler<WriteResult> completionHandler) {
        FilterChainContext context = obtainFilterChainContext(connection);
        context.setOperation(FilterChainContext.Operation.EVENT);
        context.event = TransportFilter.createFlushEvent(completionHandler);
        ExecutorResolver.DOWNSTREAM_EXECUTOR_SAMPLE.initIndexes(context);
        ProcessorExecutor.execute(context.internalContext);
    }

    public void fireEventDownstream(Connection connection, FilterChainEvent event, CompletionHandler<FilterChainContext> completionHandler) {
        FilterChainContext context = obtainFilterChainContext(connection);
        context.operationCompletionHandler = completionHandler;
        context.setOperation(FilterChainContext.Operation.EVENT);
        context.event = event;
        ExecutorResolver.DOWNSTREAM_EXECUTOR_SAMPLE.initIndexes(context);
        ProcessorExecutor.execute(context.internalContext);
    }

    public void fireEventUpstream(Connection connection, FilterChainEvent event, CompletionHandler<FilterChainContext> completionHandler) {
        FilterChainContext context = obtainFilterChainContext(connection);
        context.operationCompletionHandler = completionHandler;
        context.setOperation(FilterChainContext.Operation.EVENT);
        context.event = event;
        ExecutorResolver.UPSTREAM_EXECUTOR_SAMPLE.initIndexes(context);
        ProcessorExecutor.execute(context.internalContext);
    }

    public void fail(FilterChainContext context, Throwable failure) {
        throwChain(context, ExecutorResolver.resolve(context), failure);
    }

    private void throwChain(FilterChainContext ctx, FilterExecutor executor, Throwable exception) {
        int i;
        notifyFailure(ctx, exception);
        int endIdx = ctx.getStartIdx();
        if (ctx.getFilterIdx() != endIdx) {
            do {
                i = executor.getPreviousFilter(ctx);
                ctx.setFilterIdx(i);
                get(i).exceptionOccurred(ctx, exception);
            } while (i != endIdx);
        }
    }

    public DefaultFilterChain subList(int fromIndex, int toIndex) {
        return new DefaultFilterChain(this.filters.subList(fromIndex, toIndex));
    }

    private FiltersState obtainFiltersState(Connection connection) {
        return (FiltersState) connection.obtainProcessorState(this, this.filtersStateFactory);
    }

    private void checkStoredMessage(FilterChainContext ctx, FiltersState filtersState, int filterIdx) {
        if (filtersState != null) {
            Object message = ctx.getMessage();
            if (!(message instanceof Buffer) || ((Buffer) message).hasRemaining()) {
                ctx.setMessage(filtersState.append(ctx.getOperation(), filterIdx, message));
            }
        }
    }

    private <M> void storeMessage(FilterChainContext ctx, FiltersState filtersState, boolean isIncomplete, int filterIdx, M messageToStore, Appender<M> appender) {
        if (messageToStore != null) {
            filtersState.set(ctx.getOperation(), filterIdx, isIncomplete, messageToStore, appender);
            return;
        }
        throw new AssertionError();
    }

    private void notifyComplete(FilterChainContext context) {
        CompletionHandler<FilterChainContext> completionHandler = context.operationCompletionHandler;
        if (completionHandler != null) {
            completionHandler.completed(context);
        }
        CompletionHandler<?> transportCompletionHandler = context.transportFilterContext.completionHandler;
        if (transportCompletionHandler != null) {
            transportCompletionHandler.completed(null);
        }
    }

    private void notifyFailure(FilterChainContext context, Throwable e) {
        CompletionHandler completionHandler = context.operationCompletionHandler;
        if (completionHandler != null) {
            completionHandler.failed(e);
        }
        CompletionHandler transportCompletionHandler = context.transportFilterContext.completionHandler;
        if (transportCompletionHandler != null) {
            transportCompletionHandler.failed(e);
        }
    }

    public static final class FiltersState {
        private static final int OPERATIONS_NUM = FilterChainContext.Operation.values().length;
        private final FilterStateElement[][] state;

        public FiltersState(int filtersNum) {
            int i = OPERATIONS_NUM;
            int[] iArr = new int[2];
            iArr[1] = filtersNum;
            iArr[0] = i;
            this.state = (FilterStateElement[][]) Array.newInstance(FilterStateElement.class, iArr);
        }

        public FilterStateElement get(FilterChainContext.Operation operation, int filterIndex) {
            FilterStateElement elem = this.state[operation.ordinal()][filterIndex];
            if (elem == null || !elem.isValid) {
                return null;
            }
            return elem;
        }

        public <M> void set(FilterChainContext.Operation operation, int filterIndex, boolean isIncomplete, M messageToStore, Appender<M> appender) {
            int opIdx = operation.ordinal();
            FilterStateElement[][] filterStateElementArr = this.state;
            FilterStateElement elem = filterStateElementArr[opIdx][filterIndex];
            if (elem != null) {
                elem.set(isIncomplete, messageToStore, appender);
            } else {
                filterStateElementArr[opIdx][filterIndex] = FilterStateElement.create(isIncomplete, messageToStore, appender);
            }
        }

        public int peekUnparsedIdx(FilterChainContext.Operation operation, int start, int end) {
            if (start == end) {
                return -1;
            }
            int opIdx = operation.ordinal();
            int diff = end > start ? -1 : 1;
            int i = end;
            do {
                i += diff;
                FilterStateElement elem = this.state[opIdx][i];
                if (elem != null && elem.isValid && !elem.isIncomplete) {
                    return i;
                }
            } while (i != start);
            return -1;
        }

        /* access modifiers changed from: private */
        public Object append(FilterChainContext.Operation operation, int filterIdx, Object currentMessage) {
            FilterStateElement filterState = get(operation, filterIdx);
            return filterState != null ? filterState.append(currentMessage) : currentMessage;
        }
    }

    public static final class FilterStateElement {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Appender appender;
        /* access modifiers changed from: private */
        public boolean isIncomplete;
        /* access modifiers changed from: private */
        public boolean isValid = true;
        private Object state;

        static {
            Class<DefaultFilterChain> cls = DefaultFilterChain.class;
        }

        static FilterStateElement create(boolean isIncomplete2, Object remainder) {
            if (remainder instanceof Buffer) {
                return create(isIncomplete2, (Buffer) remainder, Buffers.getBufferAppender(true));
            }
            return create(isIncomplete2, (Appendable) remainder);
        }

        static FilterStateElement create(boolean isIncomplete2, Appendable state2) {
            return new FilterStateElement(isIncomplete2, state2);
        }

        static <E> FilterStateElement create(boolean isIncomplete2, E state2, Appender<E> appender2) {
            return new FilterStateElement(isIncomplete2, state2, appender2);
        }

        private FilterStateElement(boolean isIncomplete2, Appendable state2) {
            if (state2 != null) {
                this.isIncomplete = isIncomplete2;
                this.state = state2;
                this.appender = null;
                return;
            }
            throw new AssertionError();
        }

        private <E> FilterStateElement(boolean isIncomplete2, E state2, Appender<E> appender2) {
            if (state2 != null) {
                this.isIncomplete = isIncomplete2;
                this.state = state2;
                this.appender = appender2;
                return;
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: private */
        public <E> void set(boolean isIncomplete2, E state2, Appender<E> appender2) {
            if (state2 != null) {
                this.isIncomplete = isIncomplete2;
                this.state = state2;
                this.appender = appender2;
                this.isValid = true;
                return;
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: private */
        public Object append(Object currentMessage) {
            Object resultMessage;
            if (currentMessage != null) {
                Appender appender2 = this.appender;
                resultMessage = appender2 != null ? appender2.append(this.state, currentMessage) : ((Appendable) this.state).append(currentMessage);
            } else {
                resultMessage = this.state;
            }
            this.state = null;
            this.appender = null;
            this.isValid = false;
            return resultMessage;
        }
    }

    public final class FiltersStateFactory implements NullaryFunction<FiltersState> {
        private FiltersStateFactory() {
        }

        public FiltersState evaluate() {
            return new FiltersState(DefaultFilterChain.this.size());
        }
    }

    public static final class FilterExecution {
        private static final FilterExecution CONTINUE = new FilterExecution(0, (FilterChainContext) null);
        private static final int CONTINUE_TYPE = 0;
        private static final int REEXECUTE_TYPE = 2;
        private static final FilterExecution TERMINATE = new FilterExecution(1, (FilterChainContext) null);
        private static final int TERMINATE_TYPE = 1;
        private final FilterChainContext context;
        /* access modifiers changed from: private */
        public final int type;

        public static FilterExecution createContinue() {
            return CONTINUE;
        }

        public static FilterExecution createTerminate() {
            return TERMINATE;
        }

        public static FilterExecution createReExecute(FilterChainContext context2) {
            return new FilterExecution(2, context2);
        }

        public FilterExecution(int type2, FilterChainContext context2) {
            this.type = type2;
            this.context = context2;
        }

        public int getType() {
            return this.type;
        }

        public FilterChainContext getContext() {
            return this.context;
        }
    }
}
