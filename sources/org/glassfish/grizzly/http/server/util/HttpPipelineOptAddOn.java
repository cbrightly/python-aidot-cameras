package org.glassfish.grizzly.http.server.util;

import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.http.server.AddOn;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.memory.CompositeBuffer;

public class HttpPipelineOptAddOn implements AddOn {
    private static final int DEFAULT_MAX_BUFFER_SIZE = 16384;
    private final int maxBufferSize;

    public HttpPipelineOptAddOn() {
        this(16384);
    }

    public HttpPipelineOptAddOn(int maxBufferSize2) {
        this.maxBufferSize = maxBufferSize2;
    }

    public void setup(NetworkListener networkListener, FilterChainBuilder builder) {
        builder.add(builder.indexOfType(TransportFilter.class) + 1, new PlugFilter(this.maxBufferSize, networkListener.getTransport().getAttributeBuilder()));
    }

    public static class PlugFilter extends BaseFilter {
        private final int maxBufferSize;
        /* access modifiers changed from: private */
        public final Attribute<Plug> plugAttr;

        public PlugFilter(int maxBufferSize2, AttributeBuilder builder) {
            this.maxBufferSize = maxBufferSize2;
            this.plugAttr = builder.createAttribute(PlugFilter.class + ".plug");
        }

        public NextAction handleRead(FilterChainContext ctx) {
            if (!ctx.getTransportContext().isBlocking()) {
                Plug plug = Plug.create(ctx, this);
                ctx.getInternalContext().addLifeCycleListener(plug);
                this.plugAttr.set((AttributeStorage) ctx, plug);
            }
            return ctx.getInvokeAction();
        }

        public NextAction handleWrite(FilterChainContext ctx) {
            Plug plug = this.plugAttr.get((AttributeStorage) ctx);
            if (plug != null && plug.isPlugged) {
                WritableMessage msg = (WritableMessage) ctx.getMessage();
                if (!msg.isExternal()) {
                    Buffer buf = (Buffer) msg;
                    MessageCloner<Buffer> cloner = ctx.getTransportContext().getMessageCloner();
                    boolean unused = plug.append(cloner == null ? buf : cloner.clone(ctx.getConnection(), buf), ctx.getTransportContext().getCompletionHandler());
                    if (plug.size() > this.maxBufferSize) {
                        plug.flush();
                    }
                    return ctx.getStopAction();
                }
                plug.flush();
            }
            return ctx.getInvokeAction();
        }

        public static class Plug extends IOEventLifeCycleListener.Adapter {
            private static final ThreadCache.CachedTypeIndex<Plug> CACHE_IDX = ThreadCache.obtainIndex(Plug.class, 4);
            private AggrCompletionHandler aggrCompletionHandler;
            private CompositeBuffer buffer;
            private final MessageCloner<Buffer> cloner = new MessageCloner<Buffer>() {
                public Buffer clone(Connection connection, Buffer originalMessage) {
                    boolean unused = Plug.this.isWrittenInThisThread = false;
                    return originalMessage;
                }
            };
            private FilterChainContext ctx;
            /* access modifiers changed from: private */
            public boolean isPlugged;
            /* access modifiers changed from: private */
            public boolean isWrittenInThisThread;
            private PlugFilter plugFilter;

            public static Plug create(FilterChainContext ctx2, PlugFilter plugFilter2) {
                Plug plug = (Plug) ThreadCache.takeFromCache(CACHE_IDX);
                if (plug == null) {
                    plug = new Plug();
                }
                return plug.init(ctx2, plugFilter2);
            }

            /* access modifiers changed from: package-private */
            public Plug init(FilterChainContext ctx2, PlugFilter plugFilter2) {
                this.ctx = ctx2.copy();
                this.plugFilter = plugFilter2;
                this.isPlugged = true;
                return this;
            }

            /* access modifiers changed from: private */
            public boolean append(Buffer msg, CompletionHandler completionHandler) {
                if (!this.isPlugged) {
                    return false;
                }
                obtainCompositeBuffer().append(msg);
                if (completionHandler == null) {
                    return true;
                }
                obtainAggrCompletionHandler().add(completionHandler);
                return true;
            }

            private CompositeBuffer obtainCompositeBuffer() {
                if (this.buffer == null) {
                    CompositeBuffer newBuffer = CompositeBuffer.newBuffer(this.ctx.getMemoryManager());
                    this.buffer = newBuffer;
                    newBuffer.allowBufferDispose(true);
                    this.buffer.allowInternalBuffersDispose(true);
                    this.buffer.disposeOrder(CompositeBuffer.DisposeOrder.LAST_TO_FIRST);
                }
                return this.buffer;
            }

            private AggrCompletionHandler obtainAggrCompletionHandler() {
                if (this.aggrCompletionHandler == null) {
                    this.aggrCompletionHandler = new AggrCompletionHandler();
                }
                return this.aggrCompletionHandler;
            }

            public void onContextSuspend(Context context) {
                unplug(context);
            }

            public void onContextManualIOEventControl(Context context) {
                unplug(context);
            }

            public void onComplete(Context context, Object data) {
                unplug(context);
            }

            private void unplug(Context context) {
                if (this.isPlugged) {
                    flush();
                    this.ctx.completeAndRecycle();
                    this.isPlugged = false;
                    context.removeLifeCycleListener(this);
                    this.plugFilter.plugAttr.remove((AttributeStorage) context);
                    recycle();
                }
            }

            /* access modifiers changed from: private */
            public void flush() {
                CompositeBuffer compositeBuffer;
                AggrCompletionHandler aggrCompletionHandler2;
                if (this.isPlugged && (compositeBuffer = this.buffer) != null) {
                    this.isWrittenInThisThread = true;
                    this.ctx.write((Object) null, (Object) compositeBuffer, (CompletionHandler<WriteResult>) this.aggrCompletionHandler, (MessageCloner) this.cloner);
                    this.buffer = null;
                    if (!this.isWrittenInThisThread || (aggrCompletionHandler2 = this.aggrCompletionHandler) == null) {
                        this.aggrCompletionHandler = null;
                    } else {
                        aggrCompletionHandler2.clear();
                    }
                }
            }

            private void recycle() {
                AggrCompletionHandler aggrCompletionHandler2 = this.aggrCompletionHandler;
                if (aggrCompletionHandler2 != null) {
                    aggrCompletionHandler2.clear();
                }
                this.ctx = null;
                this.plugFilter = null;
                ThreadCache.putToCache(CACHE_IDX, this);
            }

            /* access modifiers changed from: private */
            public int size() {
                CompositeBuffer compositeBuffer;
                if (!this.isPlugged || (compositeBuffer = this.buffer) == null) {
                    return 0;
                }
                return compositeBuffer.remaining();
            }
        }

        public static final class AggrCompletionHandler implements CompletionHandler {
            private CompletionHandler[] handlers = new CompletionHandler[16];
            private int sz;

            public void add(CompletionHandler handler) {
                ensureSize();
                CompletionHandler[] completionHandlerArr = this.handlers;
                int i = this.sz;
                this.sz = i + 1;
                completionHandlerArr[i] = handler;
            }

            public void cancelled() {
                for (int i = 0; i < this.sz; i++) {
                    this.handlers[i].cancelled();
                }
            }

            public void failed(Throwable throwable) {
                for (int i = 0; i < this.sz; i++) {
                    this.handlers[i].failed(throwable);
                }
            }

            public void completed(Object result) {
                for (int i = 0; i < this.sz; i++) {
                    this.handlers[i].completed(result);
                }
            }

            public void updated(Object result) {
                for (int i = 0; i < this.sz; i++) {
                    this.handlers[i].updated(result);
                }
            }

            public void clear() {
                for (int i = 0; i < this.sz; i++) {
                    this.handlers[i] = null;
                }
                this.sz = 0;
            }

            private void ensureSize() {
                CompletionHandler[] completionHandlerArr = this.handlers;
                int length = completionHandlerArr.length;
                int i = this.sz;
                if (length == i) {
                    this.handlers = (CompletionHandler[]) Arrays.copyOf(completionHandlerArr, ((i * 3) / 2) + 1);
                }
            }
        }
    }
}
