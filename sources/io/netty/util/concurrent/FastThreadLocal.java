package io.netty.util.concurrent;

import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectCleaner;
import io.netty.util.internal.PlatformDependent;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class FastThreadLocal<V> {
    private static final int variablesToRemoveIndex = InternalThreadLocalMap.nextVariableIndex();
    private final int cleanerFlagIndex = InternalThreadLocalMap.nextVariableIndex();
    private final int index = InternalThreadLocalMap.nextVariableIndex();

    public static void removeAll() {
        InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.getIfSet();
        if (threadLocalMap != null) {
            try {
                Object v = threadLocalMap.indexedVariable(variablesToRemoveIndex);
                if (!(v == null || v == InternalThreadLocalMap.UNSET)) {
                    Set<FastThreadLocal<?>> variablesToRemove = (Set) v;
                    for (FastThreadLocal<?> tlv : (FastThreadLocal[]) variablesToRemove.toArray(new FastThreadLocal[variablesToRemove.size()])) {
                        tlv.remove(threadLocalMap);
                    }
                }
            } finally {
                InternalThreadLocalMap.remove();
            }
        }
    }

    public static int size() {
        InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.getIfSet();
        if (threadLocalMap == null) {
            return 0;
        }
        return threadLocalMap.size();
    }

    public static void destroy() {
        InternalThreadLocalMap.destroy();
    }

    private static void addToVariablesToRemove(InternalThreadLocalMap threadLocalMap, FastThreadLocal<?> variable) {
        Set<FastThreadLocal<?>> variablesToRemove;
        int i = variablesToRemoveIndex;
        Object v = threadLocalMap.indexedVariable(i);
        if (v == InternalThreadLocalMap.UNSET || v == null) {
            Set<FastThreadLocal<?>> variablesToRemove2 = Collections.newSetFromMap(new IdentityHashMap());
            threadLocalMap.setIndexedVariable(i, variablesToRemove2);
            variablesToRemove = variablesToRemove2;
        } else {
            variablesToRemove = (Set) v;
        }
        variablesToRemove.add(variable);
    }

    private static void removeFromVariablesToRemove(InternalThreadLocalMap threadLocalMap, FastThreadLocal<?> variable) {
        Object v = threadLocalMap.indexedVariable(variablesToRemoveIndex);
        if (v != InternalThreadLocalMap.UNSET && v != null) {
            ((Set) v).remove(variable);
        }
    }

    public final V get() {
        InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
        Object v = threadLocalMap.indexedVariable(this.index);
        if (v != InternalThreadLocalMap.UNSET) {
            return v;
        }
        V value = initialize(threadLocalMap);
        registerCleaner(threadLocalMap);
        return value;
    }

    private void registerCleaner(final InternalThreadLocalMap threadLocalMap) {
        Thread current = Thread.currentThread();
        if (!FastThreadLocalThread.willCleanupFastThreadLocals(current) && threadLocalMap.indexedVariable(this.cleanerFlagIndex) == InternalThreadLocalMap.UNSET) {
            threadLocalMap.setIndexedVariable(this.cleanerFlagIndex, Boolean.TRUE);
            ObjectCleaner.register(current, new Runnable() {
                public void run() {
                    FastThreadLocal.this.remove(threadLocalMap);
                }
            });
        }
    }

    public final V get(InternalThreadLocalMap threadLocalMap) {
        Object v = threadLocalMap.indexedVariable(this.index);
        if (v != InternalThreadLocalMap.UNSET) {
            return v;
        }
        return initialize(threadLocalMap);
    }

    private V initialize(InternalThreadLocalMap threadLocalMap) {
        V v = null;
        try {
            v = initialValue();
        } catch (Exception e) {
            PlatformDependent.throwException(e);
        }
        threadLocalMap.setIndexedVariable(this.index, v);
        addToVariablesToRemove(threadLocalMap, this);
        return v;
    }

    public final void set(V value) {
        if (value != InternalThreadLocalMap.UNSET) {
            InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
            if (setKnownNotUnset(threadLocalMap, value)) {
                registerCleaner(threadLocalMap);
                return;
            }
            return;
        }
        remove();
    }

    public final void set(InternalThreadLocalMap threadLocalMap, V value) {
        if (value != InternalThreadLocalMap.UNSET) {
            setKnownNotUnset(threadLocalMap, value);
        } else {
            remove(threadLocalMap);
        }
    }

    private boolean setKnownNotUnset(InternalThreadLocalMap threadLocalMap, V value) {
        if (!threadLocalMap.setIndexedVariable(this.index, value)) {
            return false;
        }
        addToVariablesToRemove(threadLocalMap, this);
        return true;
    }

    public final boolean isSet() {
        return isSet(InternalThreadLocalMap.getIfSet());
    }

    public final boolean isSet(InternalThreadLocalMap threadLocalMap) {
        return threadLocalMap != null && threadLocalMap.isIndexedVariableSet(this.index);
    }

    public final void remove() {
        remove(InternalThreadLocalMap.getIfSet());
    }

    public final void remove(InternalThreadLocalMap threadLocalMap) {
        if (threadLocalMap != null) {
            Object v = threadLocalMap.removeIndexedVariable(this.index);
            removeFromVariablesToRemove(threadLocalMap, this);
            if (v != InternalThreadLocalMap.UNSET) {
                try {
                    onRemoval(v);
                } catch (Exception e) {
                    PlatformDependent.throwException(e);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public V initialValue() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onRemoval(V v) {
    }
}
