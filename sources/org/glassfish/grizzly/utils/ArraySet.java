package org.glassfish.grizzly.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ArraySet<T> implements Set<T> {
    /* access modifiers changed from: private */
    public volatile T[] array;
    private final Class<T> clazz;
    private final boolean replaceElementIfEquals;
    private final Object sync;

    public ArraySet(Class<T> clazz2) {
        this(clazz2, true);
    }

    public ArraySet(Class<T> clazz2, boolean replaceElementIfEquals2) {
        this.sync = new Object();
        this.clazz = clazz2;
        this.replaceElementIfEquals = replaceElementIfEquals2;
    }

    public final boolean addAll(T... elements) {
        boolean result;
        if (elements == null || elements.length == 0) {
            return false;
        }
        synchronized (this.sync) {
            int startIdx = 0;
            if (this.array == null) {
                this.array = (Object[]) Array.newInstance(this.clazz, 1);
                this.array[0] = elements[0];
                startIdx = 1;
            }
            result = false;
            for (int i = startIdx; i < elements.length; i++) {
                T element = elements[i];
                T[] oldArray = this.array;
                this.array = ArrayUtils.addUnique(this.array, element, this.replaceElementIfEquals);
                result |= oldArray != this.array;
            }
        }
        return result;
    }

    public boolean addAll(Collection<? extends T> collection) {
        boolean result;
        if (collection.isEmpty()) {
            return false;
        }
        synchronized (this.sync) {
            boolean initArray = this.array == null;
            if (initArray) {
                this.array = (Object[]) Array.newInstance(this.clazz, 1);
            }
            result = false;
            for (T element : collection) {
                if (initArray) {
                    initArray = false;
                    this.array[0] = element;
                } else {
                    T[] oldArray = this.array;
                    this.array = ArrayUtils.addUnique(this.array, element, this.replaceElementIfEquals);
                    result |= oldArray != this.array;
                }
            }
        }
        return result;
    }

    public final boolean add(ArraySet<T> source) {
        T[] sourceArraySet = source.getArray();
        if (sourceArraySet == null) {
            return false;
        }
        synchronized (this.sync) {
            if (this.array == null) {
                this.array = Arrays.copyOf(sourceArraySet, sourceArraySet.length);
                return true;
            }
            boolean result = false;
            for (T element : sourceArraySet) {
                T[] oldArray = this.array;
                this.array = ArrayUtils.addUnique(this.array, element, this.replaceElementIfEquals);
                result |= oldArray != this.array;
            }
            return result;
        }
    }

    public final boolean removeAll(Object... elements) {
        if (elements.length == 0) {
            return false;
        }
        synchronized (this.sync) {
            if (this.array == null) {
                return false;
            }
            boolean result = false;
            for (Object element : elements) {
                T[] oldArray = this.array;
                this.array = ArrayUtils.remove(this.array, element);
                result |= oldArray != this.array;
            }
            return result;
        }
    }

    public final T[] getArray() {
        return this.array;
    }

    public final T[] getArrayCopy() {
        T[] localArray = this.array;
        if (localArray == null) {
            return null;
        }
        return Arrays.copyOf(localArray, localArray.length);
    }

    public final T[] obtainArrayCopy() {
        T[] localArray = this.array;
        if (localArray == null) {
            return (Object[]) Array.newInstance(this.clazz, 0);
        }
        return Arrays.copyOf(localArray, localArray.length);
    }

    public void clear() {
        this.array = null;
    }

    public int size() {
        T[] localArray = this.array;
        if (localArray != null) {
            return localArray.length;
        }
        return 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean add(T r7) {
        /*
            r6 = this;
            r0 = 0
            if (r7 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.Object r1 = r6.sync
            monitor-enter(r1)
            T[] r2 = r6.array     // Catch:{ all -> 0x002f }
            r3 = 1
            if (r2 != 0) goto L_0x001c
            java.lang.Class<T> r2 = r6.clazz     // Catch:{ all -> 0x002f }
            java.lang.Object r2 = java.lang.reflect.Array.newInstance(r2, r3)     // Catch:{ all -> 0x002f }
            java.lang.Object[] r2 = (java.lang.Object[]) r2     // Catch:{ all -> 0x002f }
            r6.array = r2     // Catch:{ all -> 0x002f }
            T[] r2 = r6.array     // Catch:{ all -> 0x002f }
            r2[r0] = r7     // Catch:{ all -> 0x002f }
            monitor-exit(r1)     // Catch:{ all -> 0x002f }
            return r3
        L_0x001c:
            T[] r2 = r6.array     // Catch:{ all -> 0x002f }
            T[] r4 = r6.array     // Catch:{ all -> 0x002f }
            boolean r5 = r6.replaceElementIfEquals     // Catch:{ all -> 0x002f }
            java.lang.Object[] r4 = org.glassfish.grizzly.utils.ArrayUtils.addUnique(r4, r7, r5)     // Catch:{ all -> 0x002f }
            r6.array = r4     // Catch:{ all -> 0x002f }
            T[] r4 = r6.array     // Catch:{ all -> 0x002f }
            if (r2 == r4) goto L_0x002d
            r0 = r3
        L_0x002d:
            monitor-exit(r1)     // Catch:{ all -> 0x002f }
            return r0
        L_0x002f:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002f }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.utils.ArraySet.add(java.lang.Object):boolean");
    }

    public boolean contains(Object o) {
        Object[] localArray = this.array;
        if (localArray == null) {
            return false;
        }
        for (Object equals : localArray) {
            if (equals.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public Object[] toArray() {
        Object[] localArray = this.array;
        return Arrays.copyOf(localArray, localArray.length);
    }

    public <K> K[] toArray(K[] a) {
        Object[] localArray = this.array;
        if (localArray == null) {
            return a;
        }
        int size = localArray.length;
        if (a.length < size) {
            return Arrays.copyOf(localArray, size, a.getClass());
        }
        System.arraycopy(localArray, 0, a, 0, localArray.length);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    public boolean remove(Object o) {
        return removeAll(o);
    }

    public boolean containsAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return true;
        }
        Object[] localArray = this.array;
        for (Object element : collection) {
            if (ArrayUtils.indexOf(localArray, element) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean retainAll(Collection<?> collection) {
        T[] localArray = this.array;
        if (localArray == null) {
            return false;
        }
        T[] newArray = (Object[]) Array.newInstance(this.clazz, Math.min(localArray.length, collection.size()));
        int newSize = 0;
        for (T elem : localArray) {
            if (collection.contains(elem)) {
                newArray[newSize] = elem;
                newSize++;
            }
        }
        if (newSize == localArray.length) {
            return false;
        }
        this.array = Arrays.copyOf(newArray, newSize);
        return true;
    }

    public boolean removeAll(Collection<?> collection) {
        T[] localArray = this.array;
        if (localArray == null) {
            return false;
        }
        T[] newArray = (Object[]) Array.newInstance(this.clazz, localArray.length);
        int newSize = 0;
        for (T elem : localArray) {
            if (!collection.contains(elem)) {
                newArray[newSize] = elem;
                newSize++;
            }
        }
        if (newSize == localArray.length) {
            return false;
        }
        this.array = Arrays.copyOf(newArray, newSize);
        return true;
    }

    public Iterator<T> iterator() {
        return new Itr();
    }

    public class Itr implements Iterator<T> {
        int cursor;
        T lastRet;
        T nextElem;

        public Itr() {
            advance();
        }

        public boolean hasNext() {
            return this.nextElem != null;
        }

        public T next() {
            T t = this.nextElem;
            if (t != null) {
                this.lastRet = t;
                advance();
                return this.lastRet;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            T t = this.lastRet;
            if (t != null) {
                ArraySet.this.remove(t);
                this.cursor--;
                this.lastRet = null;
                return;
            }
            throw new IllegalStateException();
        }

        private void advance() {
            int i;
            Object[] localArray = ArraySet.this.array;
            if (localArray == null || (i = this.cursor) >= localArray.length) {
                this.nextElem = null;
                return;
            }
            this.cursor = i + 1;
            this.nextElem = localArray[i];
        }
    }
}
