package org.glassfish.grizzly;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.logging.Logger;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.AttributeStorage;

public class Context implements AttributeStorage, Cacheable {
    private static final ThreadCache.CachedTypeIndex<Context> CACHE_IDX;
    private static final Logger LOGGER;
    private static final Processor NULL_PROCESSOR = new NullProcessor();
    private final AttributeHolder attributes = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createUnsafeAttributeHolder();
    private Connection connection;
    protected IOEvent ioEvent = IOEvent.NONE;
    protected boolean isManualIOEventControl;
    protected final MinimalisticArrayList<IOEventLifeCycleListener> lifeCycleListeners = new MinimalisticArrayList<>(IOEventLifeCycleListener.class, 2);
    private Processor processor;
    protected boolean wasSuspended;

    static {
        Class<Context> cls = Context.class;
        LOGGER = Grizzly.logger(cls);
        CACHE_IDX = ThreadCache.obtainIndex(cls, 4);
    }

    public static Context create(Connection connection2) {
        Context context = (Context) ThreadCache.takeFromCache(CACHE_IDX);
        if (context == null) {
            context = new Context();
        }
        context.setConnection(connection2);
        return context;
    }

    public static Context create(Connection connection2, Processor processor2, IOEvent ioEvent2, IOEventLifeCycleListener lifeCycleListener) {
        Context context;
        if (processor2 != null) {
            context = processor2.obtainContext(connection2);
        } else {
            context = NULL_PROCESSOR.obtainContext(connection2);
        }
        context.setIoEvent(ioEvent2);
        if (lifeCycleListener != null) {
            context.addLifeCycleListener(lifeCycleListener);
        }
        return context;
    }

    public void suspend() {
        try {
            int sz = this.lifeCycleListeners.size;
            IOEventLifeCycleListener[] array = (IOEventLifeCycleListener[]) this.lifeCycleListeners.array;
            for (int i = 0; i < sz; i++) {
                array[i].onContextSuspend(this);
            }
            this.wasSuspended = true;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void resume() {
        try {
            int sz = this.lifeCycleListeners.size;
            IOEventLifeCycleListener[] array = (IOEventLifeCycleListener[]) this.lifeCycleListeners.array;
            for (int i = 0; i < sz; i++) {
                array[i].onContextResume(this);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void complete(ProcessorResult result) {
        ProcessorExecutor.complete(this, result);
    }

    public boolean wasSuspended() {
        return this.wasSuspended;
    }

    public void setManualIOEventControl() {
        try {
            int sz = this.lifeCycleListeners.size;
            IOEventLifeCycleListener[] array = (IOEventLifeCycleListener[]) this.lifeCycleListeners.array;
            for (int i = 0; i < sz; i++) {
                array[i].onContextManualIOEventControl(this);
            }
            this.isManualIOEventControl = true;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean isManualIOEventControl() {
        return this.isManualIOEventControl;
    }

    public IOEvent getIoEvent() {
        return this.ioEvent;
    }

    public void setIoEvent(IOEvent ioEvent2) {
        this.ioEvent = ioEvent2;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection2) {
        this.connection = connection2;
    }

    public Processor getProcessor() {
        return this.processor;
    }

    public void setProcessor(Processor processor2) {
        this.processor = processor2;
    }

    public boolean hasLifeCycleListener(IOEventLifeCycleListener listener) {
        return this.lifeCycleListeners.contains(listener);
    }

    public void addLifeCycleListener(IOEventLifeCycleListener listener) {
        this.lifeCycleListeners.add(listener);
    }

    public boolean removeLifeCycleListener(IOEventLifeCycleListener listener) {
        return this.lifeCycleListeners.remove(listener);
    }

    public void removeAllLifeCycleListeners() {
        this.lifeCycleListeners.clear();
    }

    public AttributeHolder getAttributes() {
        return this.attributes;
    }

    public void reset() {
        this.attributes.recycle();
        this.processor = null;
        this.lifeCycleListeners.clear();
        this.connection = null;
        this.ioEvent = IOEvent.NONE;
        this.wasSuspended = false;
        this.isManualIOEventControl = false;
    }

    public void recycle() {
        reset();
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    /* access modifiers changed from: protected */
    public void release() {
    }

    public static final class NullProcessor implements Processor {
        private NullProcessor() {
        }

        public Context obtainContext(Connection connection) {
            Context context = Context.create(connection);
            context.setProcessor(this);
            return context;
        }

        public ProcessorResult process(Context context) {
            return ProcessorResult.createNotRun();
        }

        public void read(Connection connection, CompletionHandler completionHandler) {
            throw new UnsupportedOperationException("Not supported.");
        }

        public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler) {
            throw new UnsupportedOperationException("Not supported.");
        }

        public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, MessageCloner messageCloner) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Deprecated
        public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler) {
            throw new UnsupportedOperationException("Not supported.");
        }

        public boolean isInterested(IOEvent ioEvent) {
            return true;
        }

        public void setInterested(IOEvent ioEvent, boolean isInterested) {
        }
    }

    public static final class MinimalisticArrayList<E> {
        /* access modifiers changed from: private */
        public E[] array;
        /* access modifiers changed from: private */
        public int size;

        private MinimalisticArrayList(Class<E> clazz, int initialCapacity) {
            this.array = (Object[]) Array.newInstance(clazz, initialCapacity);
        }

        public void add(E listener) {
            ensureCapacity();
            E[] eArr = this.array;
            int i = this.size;
            this.size = i + 1;
            eArr[i] = listener;
        }

        public boolean contains(E listener) {
            return indexOf(listener) != -1;
        }

        /* access modifiers changed from: private */
        public boolean remove(E listener) {
            int idx = indexOf(listener);
            if (idx == -1) {
                return false;
            }
            int i = this.size;
            if (idx < i - 1) {
                E[] eArr = this.array;
                System.arraycopy(eArr, idx + 1, eArr, idx, (i - idx) - 1);
            }
            E[] eArr2 = this.array;
            int i2 = this.size - 1;
            this.size = i2;
            eArr2[i2] = null;
            return true;
        }

        public void copyFrom(MinimalisticArrayList<E> list) {
            int i = list.size;
            E[] eArr = this.array;
            if (i > eArr.length) {
                this.array = Arrays.copyOf(list.array, i);
                this.size = list.size;
                return;
            }
            System.arraycopy(list.array, 0, eArr, 0, i);
            for (int i2 = list.size; i2 < this.size; i2++) {
                this.array[i2] = null;
            }
            this.size = list.size;
        }

        public int size() {
            return this.size;
        }

        public E[] array() {
            return this.array;
        }

        public void clear() {
            for (int i = 0; i < this.size; i++) {
                this.array[i] = null;
            }
            this.size = 0;
        }

        private int indexOf(E listener) {
            for (int i = 0; i < this.size; i++) {
                if (this.array[i].equals(listener)) {
                    return i;
                }
            }
            return -1;
        }

        private void ensureCapacity() {
            int i = this.size;
            E[] eArr = this.array;
            if (i == eArr.length) {
                this.array = Arrays.copyOf(eArr, i + 2);
            }
        }
    }
}
