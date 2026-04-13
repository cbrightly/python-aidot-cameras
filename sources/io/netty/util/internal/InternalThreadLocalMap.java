package io.netty.util.internal;

import io.netty.util.concurrent.FastThreadLocalThread;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class InternalThreadLocalMap extends UnpaddedInternalThreadLocalMap {
    private static final int DEFAULT_ARRAY_LIST_INITIAL_CAPACITY = 8;
    private static final int STRING_BUILDER_INITIAL_SIZE;
    private static final int STRING_BUILDER_MAX_SIZE;
    public static final Object UNSET = new Object();
    private static final InternalLogger logger;
    public long rp1;
    public long rp2;
    public long rp3;
    public long rp4;
    public long rp5;
    public long rp6;
    public long rp7;
    public long rp8;
    public long rp9;

    static {
        InternalLogger instance = InternalLoggerFactory.getInstance((Class<?>) InternalThreadLocalMap.class);
        logger = instance;
        int i = SystemPropertyUtil.getInt("io.netty.threadLocalMap.stringBuilder.initialSize", 1024);
        STRING_BUILDER_INITIAL_SIZE = i;
        instance.debug("-Dio.netty.threadLocalMap.stringBuilder.initialSize: {}", (Object) Integer.valueOf(i));
        int i2 = SystemPropertyUtil.getInt("io.netty.threadLocalMap.stringBuilder.maxSize", 4096);
        STRING_BUILDER_MAX_SIZE = i2;
        instance.debug("-Dio.netty.threadLocalMap.stringBuilder.maxSize: {}", (Object) Integer.valueOf(i2));
    }

    public static InternalThreadLocalMap getIfSet() {
        Thread thread = Thread.currentThread();
        if (thread instanceof FastThreadLocalThread) {
            return ((FastThreadLocalThread) thread).threadLocalMap();
        }
        return UnpaddedInternalThreadLocalMap.slowThreadLocalMap.get();
    }

    public static InternalThreadLocalMap get() {
        Thread thread = Thread.currentThread();
        if (thread instanceof FastThreadLocalThread) {
            return fastGet((FastThreadLocalThread) thread);
        }
        return slowGet();
    }

    private static InternalThreadLocalMap fastGet(FastThreadLocalThread thread) {
        InternalThreadLocalMap threadLocalMap = thread.threadLocalMap();
        if (threadLocalMap != null) {
            return threadLocalMap;
        }
        InternalThreadLocalMap internalThreadLocalMap = new InternalThreadLocalMap();
        InternalThreadLocalMap threadLocalMap2 = internalThreadLocalMap;
        thread.setThreadLocalMap(internalThreadLocalMap);
        return threadLocalMap2;
    }

    private static InternalThreadLocalMap slowGet() {
        ThreadLocal<InternalThreadLocalMap> slowThreadLocalMap = UnpaddedInternalThreadLocalMap.slowThreadLocalMap;
        InternalThreadLocalMap ret = slowThreadLocalMap.get();
        if (ret != null) {
            return ret;
        }
        InternalThreadLocalMap ret2 = new InternalThreadLocalMap();
        slowThreadLocalMap.set(ret2);
        return ret2;
    }

    public static void remove() {
        Thread thread = Thread.currentThread();
        if (thread instanceof FastThreadLocalThread) {
            ((FastThreadLocalThread) thread).setThreadLocalMap((InternalThreadLocalMap) null);
        } else {
            UnpaddedInternalThreadLocalMap.slowThreadLocalMap.remove();
        }
    }

    public static void destroy() {
        UnpaddedInternalThreadLocalMap.slowThreadLocalMap.remove();
    }

    public static int nextVariableIndex() {
        AtomicInteger atomicInteger = UnpaddedInternalThreadLocalMap.nextIndex;
        int index = atomicInteger.getAndIncrement();
        if (index >= 0) {
            return index;
        }
        atomicInteger.decrementAndGet();
        throw new IllegalStateException("too many thread-local indexed variables");
    }

    public static int lastVariableIndex() {
        return UnpaddedInternalThreadLocalMap.nextIndex.get() - 1;
    }

    private InternalThreadLocalMap() {
        super(newIndexedVariableTable());
    }

    private static Object[] newIndexedVariableTable() {
        Object[] array = new Object[32];
        Arrays.fill(array, UNSET);
        return array;
    }

    public int size() {
        int count = 0;
        if (this.futureListenerStackDepth != 0) {
            count = 0 + 1;
        }
        if (this.localChannelReaderStackDepth != 0) {
            count++;
        }
        if (this.handlerSharableCache != null) {
            count++;
        }
        if (this.counterHashCode != null) {
            count++;
        }
        if (this.random != null) {
            count++;
        }
        if (this.typeParameterMatcherGetCache != null) {
            count++;
        }
        if (this.typeParameterMatcherFindCache != null) {
            count++;
        }
        if (this.stringBuilder != null) {
            count++;
        }
        if (this.charsetEncoderCache != null) {
            count++;
        }
        if (this.charsetDecoderCache != null) {
            count++;
        }
        if (this.arrayList != null) {
            count++;
        }
        for (Object o : this.indexedVariables) {
            if (o != UNSET) {
                count++;
            }
        }
        return count - 1;
    }

    public StringBuilder stringBuilder() {
        StringBuilder sb = this.stringBuilder;
        if (sb == null) {
            StringBuilder sb2 = new StringBuilder(STRING_BUILDER_INITIAL_SIZE);
            this.stringBuilder = sb2;
            return sb2;
        }
        if (sb.capacity() > STRING_BUILDER_MAX_SIZE) {
            sb.setLength(STRING_BUILDER_INITIAL_SIZE);
            sb.trimToSize();
        }
        sb.setLength(0);
        return sb;
    }

    public Map<Charset, CharsetEncoder> charsetEncoderCache() {
        Map<Charset, CharsetEncoder> cache = this.charsetEncoderCache;
        if (cache != null) {
            return cache;
        }
        Map<Charset, CharsetEncoder> identityHashMap = new IdentityHashMap<>();
        Map<Charset, CharsetEncoder> cache2 = identityHashMap;
        this.charsetEncoderCache = identityHashMap;
        return cache2;
    }

    public Map<Charset, CharsetDecoder> charsetDecoderCache() {
        Map<Charset, CharsetDecoder> cache = this.charsetDecoderCache;
        if (cache != null) {
            return cache;
        }
        Map<Charset, CharsetDecoder> identityHashMap = new IdentityHashMap<>();
        Map<Charset, CharsetDecoder> cache2 = identityHashMap;
        this.charsetDecoderCache = identityHashMap;
        return cache2;
    }

    public <E> ArrayList<E> arrayList() {
        return arrayList(8);
    }

    public <E> ArrayList<E> arrayList(int minCapacity) {
        ArrayList<Object> arrayList = this.arrayList;
        if (arrayList == null) {
            ArrayList<Object> arrayList2 = new ArrayList<>(minCapacity);
            this.arrayList = arrayList2;
            return arrayList2;
        }
        arrayList.clear();
        arrayList.ensureCapacity(minCapacity);
        return arrayList;
    }

    public int futureListenerStackDepth() {
        return this.futureListenerStackDepth;
    }

    public void setFutureListenerStackDepth(int futureListenerStackDepth) {
        this.futureListenerStackDepth = futureListenerStackDepth;
    }

    public ThreadLocalRandom random() {
        ThreadLocalRandom r = this.random;
        if (r != null) {
            return r;
        }
        ThreadLocalRandom threadLocalRandom = new ThreadLocalRandom();
        ThreadLocalRandom r2 = threadLocalRandom;
        this.random = threadLocalRandom;
        return r2;
    }

    public Map<Class<?>, TypeParameterMatcher> typeParameterMatcherGetCache() {
        Map<Class<?>, TypeParameterMatcher> cache = this.typeParameterMatcherGetCache;
        if (cache != null) {
            return cache;
        }
        Map<Class<?>, TypeParameterMatcher> identityHashMap = new IdentityHashMap<>();
        Map<Class<?>, TypeParameterMatcher> cache2 = identityHashMap;
        this.typeParameterMatcherGetCache = identityHashMap;
        return cache2;
    }

    public Map<Class<?>, Map<String, TypeParameterMatcher>> typeParameterMatcherFindCache() {
        Map<Class<?>, Map<String, TypeParameterMatcher>> cache = this.typeParameterMatcherFindCache;
        if (cache != null) {
            return cache;
        }
        Map<Class<?>, Map<String, TypeParameterMatcher>> identityHashMap = new IdentityHashMap<>();
        Map<Class<?>, Map<String, TypeParameterMatcher>> cache2 = identityHashMap;
        this.typeParameterMatcherFindCache = identityHashMap;
        return cache2;
    }

    public IntegerHolder counterHashCode() {
        return this.counterHashCode;
    }

    public void setCounterHashCode(IntegerHolder counterHashCode) {
        this.counterHashCode = counterHashCode;
    }

    public Map<Class<?>, Boolean> handlerSharableCache() {
        Map<Class<?>, Boolean> cache = this.handlerSharableCache;
        if (cache != null) {
            return cache;
        }
        Map<Class<?>, Boolean> weakHashMap = new WeakHashMap<>(4);
        Map<Class<?>, Boolean> cache2 = weakHashMap;
        this.handlerSharableCache = weakHashMap;
        return cache2;
    }

    public int localChannelReaderStackDepth() {
        return this.localChannelReaderStackDepth;
    }

    public void setLocalChannelReaderStackDepth(int localChannelReaderStackDepth) {
        this.localChannelReaderStackDepth = localChannelReaderStackDepth;
    }

    public Object indexedVariable(int index) {
        Object[] lookup = this.indexedVariables;
        return index < lookup.length ? lookup[index] : UNSET;
    }

    public boolean setIndexedVariable(int index, Object value) {
        Object[] lookup = this.indexedVariables;
        if (index < lookup.length) {
            Object oldValue = lookup[index];
            lookup[index] = value;
            if (oldValue == UNSET) {
                return true;
            }
            return false;
        }
        expandIndexedVariableTableAndSet(index, value);
        return true;
    }

    private void expandIndexedVariableTableAndSet(int index, Object value) {
        Object[] oldArray = this.indexedVariables;
        int oldCapacity = oldArray.length;
        int newCapacity = index;
        int newCapacity2 = newCapacity | (newCapacity >>> 1);
        int newCapacity3 = newCapacity2 | (newCapacity2 >>> 2);
        int newCapacity4 = newCapacity3 | (newCapacity3 >>> 4);
        int newCapacity5 = newCapacity4 | (newCapacity4 >>> 8);
        Object[] newArray = Arrays.copyOf(oldArray, (newCapacity5 | (newCapacity5 >>> 16)) + 1);
        Arrays.fill(newArray, oldCapacity, newArray.length, UNSET);
        newArray[index] = value;
        this.indexedVariables = newArray;
    }

    public Object removeIndexedVariable(int index) {
        Object[] lookup = this.indexedVariables;
        if (index >= lookup.length) {
            return UNSET;
        }
        Object v = lookup[index];
        lookup[index] = UNSET;
        return v;
    }

    public boolean isIndexedVariableSet(int index) {
        Object[] lookup = this.indexedVariables;
        return index < lookup.length && lookup[index] != UNSET;
    }
}
