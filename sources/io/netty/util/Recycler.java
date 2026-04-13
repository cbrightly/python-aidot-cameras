package io.netty.util;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Recycler<T> {
    private static final int DEFAULT_INITIAL_MAX_CAPACITY = 32768;
    private static final int DEFAULT_MAX_CAPACITY;
    /* access modifiers changed from: private */
    public static final FastThreadLocal<Map<Stack<?>, WeakOrderQueue>> DELAYED_RECYCLED = new FastThreadLocal<Map<Stack<?>, WeakOrderQueue>>() {
        /* access modifiers changed from: protected */
        public Map<Stack<?>, WeakOrderQueue> initialValue() {
            return new WeakHashMap();
        }
    };
    /* access modifiers changed from: private */
    public static final AtomicInteger ID_GENERATOR;
    /* access modifiers changed from: private */
    public static final int INITIAL_CAPACITY;
    /* access modifiers changed from: private */
    public static final int LINK_CAPACITY;
    private static final int MAX_DELAYED_QUEUES_PER_THREAD = Math.max(0, SystemPropertyUtil.getInt("io.netty.recycler.maxDelayedQueuesPerThread", NettyRuntime.availableProcessors() * 2));
    private static final int MAX_SHARED_CAPACITY_FACTOR;
    private static final Handle NOOP_HANDLE = new Handle() {
    };
    /* access modifiers changed from: private */
    public static final int OWN_THREAD_ID;
    private static final int RATIO;
    private static final InternalLogger logger;
    /* access modifiers changed from: private */
    public final int maxCapacity;
    /* access modifiers changed from: private */
    public final int maxDelayedQueuesPerThread;
    /* access modifiers changed from: private */
    public final int maxSharedCapacityFactor;
    /* access modifiers changed from: private */
    public final int ratioMask;
    private final FastThreadLocal<Stack<T>> threadLocal;

    public interface Handle {
    }

    /* access modifiers changed from: protected */
    public abstract T newObject(Handle handle);

    static {
        InternalLogger instance = InternalLoggerFactory.getInstance((Class<?>) Recycler.class);
        logger = instance;
        AtomicInteger atomicInteger = new AtomicInteger(Integer.MIN_VALUE);
        ID_GENERATOR = atomicInteger;
        OWN_THREAD_ID = atomicInteger.getAndIncrement();
        int maxCapacity2 = SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity.default", 32768);
        if (maxCapacity2 < 0) {
            maxCapacity2 = 32768;
        }
        DEFAULT_MAX_CAPACITY = maxCapacity2;
        int max = Math.max(2, SystemPropertyUtil.getInt("io.netty.recycler.maxSharedCapacityFactor", 2));
        MAX_SHARED_CAPACITY_FACTOR = max;
        int safeFindNextPositivePowerOfTwo = MathUtil.safeFindNextPositivePowerOfTwo(Math.max(SystemPropertyUtil.getInt("io.netty.recycler.linkCapacity", 16), 16));
        LINK_CAPACITY = safeFindNextPositivePowerOfTwo;
        int safeFindNextPositivePowerOfTwo2 = MathUtil.safeFindNextPositivePowerOfTwo(SystemPropertyUtil.getInt("io.netty.recycler.ratio", 8));
        RATIO = safeFindNextPositivePowerOfTwo2;
        if (instance.isDebugEnabled()) {
            if (maxCapacity2 == 0) {
                instance.debug("-Dio.netty.recycler.maxCapacity.default: disabled");
                instance.debug("-Dio.netty.recycler.maxSharedCapacityFactor: disabled");
                instance.debug("-Dio.netty.recycler.linkCapacity: disabled");
                instance.debug("-Dio.netty.recycler.ratio: disabled");
            } else {
                instance.debug("-Dio.netty.recycler.maxCapacity.default: {}", (Object) Integer.valueOf(maxCapacity2));
                instance.debug("-Dio.netty.recycler.maxSharedCapacityFactor: {}", (Object) Integer.valueOf(max));
                instance.debug("-Dio.netty.recycler.linkCapacity: {}", (Object) Integer.valueOf(safeFindNextPositivePowerOfTwo));
                instance.debug("-Dio.netty.recycler.ratio: {}", (Object) Integer.valueOf(safeFindNextPositivePowerOfTwo2));
            }
        }
        INITIAL_CAPACITY = Math.min(maxCapacity2, 256);
    }

    protected Recycler() {
        this(DEFAULT_MAX_CAPACITY);
    }

    protected Recycler(int maxCapacity2) {
        this(maxCapacity2, MAX_SHARED_CAPACITY_FACTOR);
    }

    protected Recycler(int maxCapacity2, int maxSharedCapacityFactor2) {
        this(maxCapacity2, maxSharedCapacityFactor2, RATIO, MAX_DELAYED_QUEUES_PER_THREAD);
    }

    protected Recycler(int maxCapacity2, int maxSharedCapacityFactor2, int ratio, int maxDelayedQueuesPerThread2) {
        this.threadLocal = new FastThreadLocal<Stack<T>>() {
            /* access modifiers changed from: protected */
            public Stack<T> initialValue() {
                return new Stack(Recycler.this, Thread.currentThread(), Recycler.this.maxCapacity, Recycler.this.maxSharedCapacityFactor, Recycler.this.ratioMask, Recycler.this.maxDelayedQueuesPerThread);
            }

            /* access modifiers changed from: protected */
            public void onRemoval(Stack<T> value) {
                if (value.threadRef.get() == Thread.currentThread() && Recycler.DELAYED_RECYCLED.isSet()) {
                    ((Map) Recycler.DELAYED_RECYCLED.get()).remove(value);
                }
            }
        };
        this.ratioMask = MathUtil.safeFindNextPositivePowerOfTwo(ratio) - 1;
        if (maxCapacity2 <= 0) {
            this.maxCapacity = 0;
            this.maxSharedCapacityFactor = 1;
            this.maxDelayedQueuesPerThread = 0;
            return;
        }
        this.maxCapacity = maxCapacity2;
        this.maxSharedCapacityFactor = Math.max(1, maxSharedCapacityFactor2);
        this.maxDelayedQueuesPerThread = Math.max(0, maxDelayedQueuesPerThread2);
    }

    public final T get() {
        if (this.maxCapacity == 0) {
            return newObject(NOOP_HANDLE);
        }
        Stack<T> stack = this.threadLocal.get();
        DefaultHandle handle = stack.pop();
        if (handle == null) {
            handle = stack.newHandle();
            Object unused = handle.value = newObject(handle);
        }
        return handle.value;
    }

    public final boolean recycle(T o, Handle handle) {
        if (handle == NOOP_HANDLE) {
            return false;
        }
        DefaultHandle h = (DefaultHandle) handle;
        if (h.stack.parent != this) {
            return false;
        }
        if (o == h.value) {
            h.recycle();
            return true;
        }
        throw new IllegalArgumentException("o does not belong to handle");
    }

    /* access modifiers changed from: package-private */
    public final int threadLocalCapacity() {
        return this.threadLocal.get().elements.length;
    }

    /* access modifiers changed from: package-private */
    public final int threadLocalSize() {
        return this.threadLocal.get().size;
    }

    public static final class DefaultHandle implements Handle {
        boolean hasBeenRecycled;
        /* access modifiers changed from: private */
        public int lastRecycledId;
        /* access modifiers changed from: private */
        public int recycleId;
        /* access modifiers changed from: private */
        public Stack<?> stack;
        /* access modifiers changed from: private */
        public Object value;

        DefaultHandle(Stack<?> stack2) {
            this.stack = stack2;
        }

        public void recycle() {
            this.stack.push(this);
        }
    }

    public static final class WeakOrderQueue {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final WeakOrderQueue DUMMY = new WeakOrderQueue();
        private final AtomicInteger availableSharedCapacity;
        private Link head;
        private final int id;
        /* access modifiers changed from: private */
        public WeakOrderQueue next;
        /* access modifiers changed from: private */
        public final WeakReference<Thread> owner;
        private Link tail;

        public static final class Link extends AtomicInteger {
            /* access modifiers changed from: private */
            public final DefaultHandle[] elements;
            /* access modifiers changed from: private */
            public Link next;
            /* access modifiers changed from: private */
            public int readIndex;

            private Link() {
                this.elements = new DefaultHandle[Recycler.LINK_CAPACITY];
            }
        }

        private WeakOrderQueue() {
            this.id = Recycler.ID_GENERATOR.getAndIncrement();
            this.owner = null;
            this.availableSharedCapacity = null;
        }

        private WeakOrderQueue(Stack<?> stack, Thread thread) {
            this.id = Recycler.ID_GENERATOR.getAndIncrement();
            Link link = new Link();
            this.tail = link;
            this.head = link;
            this.owner = new WeakReference<>(thread);
            this.availableSharedCapacity = stack.availableSharedCapacity;
        }

        static WeakOrderQueue newQueue(Stack<?> stack, Thread thread) {
            WeakOrderQueue queue = new WeakOrderQueue(stack, thread);
            stack.setHead(queue);
            return queue;
        }

        /* access modifiers changed from: private */
        public void setNext(WeakOrderQueue next2) {
            if (next2 != this) {
                this.next = next2;
                return;
            }
            throw new AssertionError();
        }

        static WeakOrderQueue allocate(Stack<?> stack, Thread thread) {
            if (reserveSpace(stack.availableSharedCapacity, Recycler.LINK_CAPACITY)) {
                return newQueue(stack, thread);
            }
            return null;
        }

        private static boolean reserveSpace(AtomicInteger availableSharedCapacity2, int space) {
            int available;
            if (space >= 0) {
                do {
                    available = availableSharedCapacity2.get();
                    if (available < space) {
                        return false;
                    }
                } while (!availableSharedCapacity2.compareAndSet(available, available - space));
                return true;
            }
            throw new AssertionError();
        }

        private void reclaimSpace(int space) {
            if (space >= 0) {
                this.availableSharedCapacity.addAndGet(space);
                return;
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public void add(DefaultHandle handle) {
            int unused = handle.lastRecycledId = this.id;
            Link tail2 = this.tail;
            int i = tail2.get();
            int writeIndex = i;
            if (i == Recycler.LINK_CAPACITY) {
                if (reserveSpace(this.availableSharedCapacity, Recycler.LINK_CAPACITY)) {
                    Link access$1302 = tail2.next = new Link();
                    tail2 = access$1302;
                    this.tail = access$1302;
                    writeIndex = tail2.get();
                } else {
                    return;
                }
            }
            tail2.elements[writeIndex] = handle;
            Stack unused2 = handle.stack = null;
            tail2.lazySet(writeIndex + 1);
        }

        /* access modifiers changed from: package-private */
        public boolean hasFinalData() {
            return this.tail.readIndex != this.tail.get();
        }

        /* access modifiers changed from: package-private */
        public boolean transfer(Stack<?> dst) {
            Link head2 = this.head;
            if (head2 == null) {
                return false;
            }
            if (head2.readIndex == Recycler.LINK_CAPACITY) {
                if (head2.next == null) {
                    return false;
                }
                Link access$1300 = head2.next;
                head2 = access$1300;
                this.head = access$1300;
            }
            int srcStart = head2.readIndex;
            int srcEnd = head2.get();
            int srcSize = srcEnd - srcStart;
            if (srcSize == 0) {
                return false;
            }
            int dstSize = dst.size;
            int expectedCapacity = dstSize + srcSize;
            if (expectedCapacity > dst.elements.length) {
                srcEnd = Math.min((srcStart + dst.increaseCapacity(expectedCapacity)) - dstSize, srcEnd);
            }
            if (srcStart == srcEnd) {
                return false;
            }
            DefaultHandle[] srcElems = head2.elements;
            DefaultHandle[] dstElems = dst.elements;
            int newDstSize = dstSize;
            for (int i = srcStart; i < srcEnd; i++) {
                DefaultHandle element = srcElems[i];
                if (element.recycleId == 0) {
                    int unused = element.recycleId = element.lastRecycledId;
                } else if (element.recycleId != element.lastRecycledId) {
                    throw new IllegalStateException("recycled already");
                }
                srcElems[i] = null;
                if (!dst.dropHandle(element)) {
                    Stack unused2 = element.stack = dst;
                    dstElems[newDstSize] = element;
                    newDstSize++;
                }
            }
            if (srcEnd == Recycler.LINK_CAPACITY && head2.next != null) {
                reclaimSpace(Recycler.LINK_CAPACITY);
                this.head = head2.next;
            }
            int unused3 = head2.readIndex = srcEnd;
            if (dst.size == newDstSize) {
                return false;
            }
            int unused4 = dst.size = newDstSize;
            return true;
        }

        /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
            jadx.core.utils.exceptions.JadxOverflowException: 
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        protected void finalize() {
            /*
                r3 = this;
                super.finalize()     // Catch:{ all -> 0x0015 }
                io.netty.util.Recycler$WeakOrderQueue$Link r0 = r3.head
            L_0x0005:
                if (r0 == 0) goto L_0x0013
                int r1 = io.netty.util.Recycler.LINK_CAPACITY
                r3.reclaimSpace(r1)
                io.netty.util.Recycler$WeakOrderQueue$Link r0 = r0.next
                goto L_0x0005
            L_0x0013:
                return
            L_0x0015:
                r0 = move-exception
                io.netty.util.Recycler$WeakOrderQueue$Link r1 = r3.head
            L_0x0018:
                if (r1 == 0) goto L_0x0026
                int r2 = io.netty.util.Recycler.LINK_CAPACITY
                r3.reclaimSpace(r2)
                io.netty.util.Recycler$WeakOrderQueue$Link r1 = r1.next
                goto L_0x0018
            L_0x0026:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.util.Recycler.WeakOrderQueue.finalize():void");
        }
    }

    public static final class Stack<T> {
        final AtomicInteger availableSharedCapacity;
        private WeakOrderQueue cursor;
        /* access modifiers changed from: private */
        public DefaultHandle[] elements;
        private int handleRecycleCount = -1;
        private volatile WeakOrderQueue head;
        private final int maxCapacity;
        final int maxDelayedQueues;
        final Recycler<T> parent;
        private WeakOrderQueue prev;
        private final int ratioMask;
        /* access modifiers changed from: private */
        public int size;
        final WeakReference<Thread> threadRef;

        Stack(Recycler<T> parent2, Thread thread, int maxCapacity2, int maxSharedCapacityFactor, int ratioMask2, int maxDelayedQueues2) {
            this.parent = parent2;
            this.threadRef = new WeakReference<>(thread);
            this.maxCapacity = maxCapacity2;
            this.availableSharedCapacity = new AtomicInteger(Math.max(maxCapacity2 / maxSharedCapacityFactor, Recycler.LINK_CAPACITY));
            this.elements = new DefaultHandle[Math.min(Recycler.INITIAL_CAPACITY, maxCapacity2)];
            this.ratioMask = ratioMask2;
            this.maxDelayedQueues = maxDelayedQueues2;
        }

        /* access modifiers changed from: package-private */
        public synchronized void setHead(WeakOrderQueue queue) {
            queue.setNext(this.head);
            this.head = queue;
        }

        /* access modifiers changed from: package-private */
        public int increaseCapacity(int expectedCapacity) {
            int newCapacity = this.elements.length;
            int maxCapacity2 = this.maxCapacity;
            do {
                newCapacity <<= 1;
                if (newCapacity >= expectedCapacity) {
                    break;
                }
            } while (newCapacity < maxCapacity2);
            int newCapacity2 = Math.min(newCapacity, maxCapacity2);
            DefaultHandle[] defaultHandleArr = this.elements;
            if (newCapacity2 != defaultHandleArr.length) {
                this.elements = (DefaultHandle[]) Arrays.copyOf(defaultHandleArr, newCapacity2);
            }
            return newCapacity2;
        }

        /* access modifiers changed from: package-private */
        public DefaultHandle pop() {
            int size2 = this.size;
            if (size2 == 0) {
                if (!scavenge()) {
                    return null;
                }
                size2 = this.size;
            }
            int size3 = size2 - 1;
            DefaultHandle[] defaultHandleArr = this.elements;
            DefaultHandle ret = defaultHandleArr[size3];
            defaultHandleArr[size3] = null;
            if (ret.lastRecycledId == ret.recycleId) {
                int unused = ret.recycleId = 0;
                int unused2 = ret.lastRecycledId = 0;
                this.size = size3;
                return ret;
            }
            throw new IllegalStateException("recycled multiple times");
        }

        /* access modifiers changed from: package-private */
        public boolean scavenge() {
            if (scavengeSome()) {
                return true;
            }
            this.prev = null;
            this.cursor = this.head;
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean scavengeSome() {
            WeakOrderQueue prev2;
            WeakOrderQueue cursor2 = this.cursor;
            if (cursor2 == null) {
                prev2 = null;
                cursor2 = this.head;
                if (cursor2 == null) {
                    return false;
                }
            } else {
                prev2 = this.prev;
            }
            boolean success = false;
            while (true) {
                if (!cursor2.transfer(this)) {
                    WeakOrderQueue next = cursor2.next;
                    if (cursor2.owner.get() == null) {
                        if (cursor2.hasFinalData()) {
                            while (cursor2.transfer(this)) {
                                success = true;
                            }
                        }
                        if (prev2 != null) {
                            prev2.setNext(next);
                        }
                    } else {
                        prev2 = cursor2;
                    }
                    cursor2 = next;
                    if (cursor2 != null) {
                        if (success) {
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    success = true;
                    break;
                }
            }
            this.prev = prev2;
            this.cursor = cursor2;
            return success;
        }

        /* access modifiers changed from: package-private */
        public void push(DefaultHandle item) {
            Thread currentThread = Thread.currentThread();
            if (this.threadRef.get() == currentThread) {
                pushNow(item);
            } else {
                pushLater(item, currentThread);
            }
        }

        private void pushNow(DefaultHandle item) {
            if ((item.recycleId | item.lastRecycledId) == 0) {
                int unused = item.recycleId = item.lastRecycledId = Recycler.OWN_THREAD_ID;
                int size2 = this.size;
                if (size2 < this.maxCapacity && !dropHandle(item)) {
                    DefaultHandle[] defaultHandleArr = this.elements;
                    if (size2 == defaultHandleArr.length) {
                        this.elements = (DefaultHandle[]) Arrays.copyOf(defaultHandleArr, Math.min(size2 << 1, this.maxCapacity));
                    }
                    this.elements[size2] = item;
                    this.size = size2 + 1;
                    return;
                }
                return;
            }
            throw new IllegalStateException("recycled already");
        }

        private void pushLater(DefaultHandle item, Thread thread) {
            Map<Stack<?>, WeakOrderQueue> delayedRecycled = (Map) Recycler.DELAYED_RECYCLED.get();
            WeakOrderQueue queue = delayedRecycled.get(this);
            if (queue == null) {
                if (delayedRecycled.size() >= this.maxDelayedQueues) {
                    delayedRecycled.put(this, WeakOrderQueue.DUMMY);
                    return;
                }
                WeakOrderQueue allocate = WeakOrderQueue.allocate(this, thread);
                queue = allocate;
                if (allocate != null) {
                    delayedRecycled.put(this, queue);
                } else {
                    return;
                }
            } else if (queue == WeakOrderQueue.DUMMY) {
                return;
            }
            queue.add(item);
        }

        /* access modifiers changed from: package-private */
        public boolean dropHandle(DefaultHandle handle) {
            if (handle.hasBeenRecycled) {
                return false;
            }
            int i = this.handleRecycleCount + 1;
            this.handleRecycleCount = i;
            if ((i & this.ratioMask) != 0) {
                return true;
            }
            handle.hasBeenRecycled = true;
            return false;
        }

        /* access modifiers changed from: package-private */
        public DefaultHandle newHandle() {
            return new DefaultHandle(this);
        }
    }
}
