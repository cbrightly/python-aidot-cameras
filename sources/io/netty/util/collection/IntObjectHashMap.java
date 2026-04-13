package io.netty.util.collection;

import io.netty.util.collection.IntObjectMap;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntObjectHashMap<V> implements IntObjectMap<V>, Iterable<IntObjectMap.Entry<V>> {
    private static final int DEFAULT_CAPACITY = 11;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final Object NULL_VALUE = new Object();
    /* access modifiers changed from: private */
    public int[] keys;
    private final float loadFactor;
    private int maxSize;
    /* access modifiers changed from: private */
    public int size;
    private Collection<V> valueCollection;
    /* access modifiers changed from: private */
    public V[] values;

    public IntObjectHashMap() {
        this(11, 0.5f);
    }

    public IntObjectHashMap(int initialCapacity) {
        this(initialCapacity, 0.5f);
    }

    public IntObjectHashMap(int initialCapacity, float loadFactor2) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("initialCapacity must be >= 1");
        } else if (loadFactor2 <= 0.0f || loadFactor2 > 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
        } else {
            this.loadFactor = loadFactor2;
            int capacity = adjustCapacity(initialCapacity);
            this.keys = new int[capacity];
            this.values = new Object[capacity];
            this.maxSize = calcMaxSize(capacity);
        }
    }

    /* access modifiers changed from: private */
    public static <T> T toExternal(T value) {
        if (value == NULL_VALUE) {
            return null;
        }
        return value;
    }

    /* access modifiers changed from: private */
    public static <T> T toInternal(T value) {
        return value == null ? NULL_VALUE : value;
    }

    public V get(int key) {
        int index = indexOf(key);
        if (index == -1) {
            return null;
        }
        return toExternal(this.values[index]);
    }

    public V put(int key, V value) {
        int probeNext;
        int startIndex = hashIndex(key);
        int index = startIndex;
        do {
            V[] vArr = this.values;
            if (vArr[index] == null) {
                this.keys[index] = key;
                vArr[index] = toInternal(value);
                growSize();
                return null;
            } else if (this.keys[index] == key) {
                V previousValue = vArr[index];
                vArr[index] = toInternal(value);
                return toExternal(previousValue);
            } else {
                probeNext = probeNext(index);
                index = probeNext;
            }
        } while (probeNext != startIndex);
        throw new IllegalStateException("Unable to insert");
    }

    private int probeNext(int index) {
        if (index == this.values.length - 1) {
            return 0;
        }
        return index + 1;
    }

    public void putAll(IntObjectMap<V> sourceMap) {
        if (sourceMap instanceof IntObjectHashMap) {
            IntObjectHashMap<V> source = (IntObjectHashMap) sourceMap;
            int i = 0;
            while (true) {
                V[] vArr = source.values;
                if (i < vArr.length) {
                    V sourceValue = vArr[i];
                    if (sourceValue != null) {
                        put(source.keys[i], sourceValue);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } else {
            for (IntObjectMap.Entry<V> entry : sourceMap.entries()) {
                put(entry.key(), entry.value());
            }
        }
    }

    public V remove(int key) {
        int index = indexOf(key);
        if (index == -1) {
            return null;
        }
        V prev = this.values[index];
        removeAt(index);
        return toExternal(prev);
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        Arrays.fill(this.keys, 0);
        Arrays.fill(this.values, (Object) null);
        this.size = 0;
    }

    public boolean containsKey(int key) {
        return indexOf(key) >= 0;
    }

    public boolean containsValue(V value) {
        V v1 = toInternal(value);
        for (V v2 : this.values) {
            if (v2 != null && v2.equals(v1)) {
                return true;
            }
        }
        return false;
    }

    public Iterable<IntObjectMap.Entry<V>> entries() {
        return this;
    }

    public Iterator<IntObjectMap.Entry<V>> iterator() {
        return new IteratorImpl();
    }

    public int[] keys() {
        int[] outKeys = new int[size()];
        int targetIx = 0;
        int i = 0;
        while (true) {
            V[] vArr = this.values;
            if (i >= vArr.length) {
                return outKeys;
            }
            if (vArr[i] != null) {
                outKeys[targetIx] = this.keys[i];
                targetIx++;
            }
            i++;
        }
    }

    public V[] values(Class<V> clazz) {
        V[] outValues = (Object[]) Array.newInstance(clazz, size());
        int targetIx = 0;
        for (V value : this.values) {
            if (value != null) {
                outValues[targetIx] = value;
                targetIx++;
            }
        }
        return outValues;
    }

    public Collection<V> values() {
        Collection<V> valueCollection2 = this.valueCollection;
        if (valueCollection2 != null) {
            return valueCollection2;
        }
        Collection<V> r1 = new AbstractCollection<V>() {
            public Iterator<V> iterator() {
                return new Iterator<V>() {
                    final Iterator<IntObjectMap.Entry<V>> iter;

                    {
                        this.iter = IntObjectHashMap.this.iterator();
                    }

                    public boolean hasNext() {
                        return this.iter.hasNext();
                    }

                    public V next() {
                        return this.iter.next().value();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            public int size() {
                return IntObjectHashMap.this.size;
            }
        };
        Collection<V> valueCollection3 = r1;
        this.valueCollection = r1;
        return valueCollection3;
    }

    public int hashCode() {
        int hash = this.size;
        for (int key : this.keys) {
            hash ^= key;
        }
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntObjectMap)) {
            return false;
        }
        IntObjectMap other = (IntObjectMap) obj;
        if (this.size != other.size()) {
            return false;
        }
        int i = 0;
        while (true) {
            V[] vArr = this.values;
            if (i >= vArr.length) {
                return true;
            }
            V value = vArr[i];
            if (value != null) {
                Object otherValue = other.get(this.keys[i]);
                if (value == NULL_VALUE) {
                    if (otherValue != null) {
                        return false;
                    }
                } else if (!value.equals(otherValue)) {
                    return false;
                }
            }
            i++;
        }
    }

    private int indexOf(int key) {
        int startIndex = hashIndex(key);
        int index = startIndex;
        while (this.values[index] != null) {
            if (key == this.keys[index]) {
                return index;
            }
            int probeNext = probeNext(index);
            index = probeNext;
            if (probeNext == startIndex) {
                return -1;
            }
        }
        return -1;
    }

    private int hashIndex(int key) {
        int[] iArr = this.keys;
        return ((key % iArr.length) + iArr.length) % iArr.length;
    }

    private void growSize() {
        int i = this.size + 1;
        this.size = i;
        if (i > this.maxSize) {
            rehash(adjustCapacity((int) Math.min(((double) this.keys.length) * 2.0d, 2.147483639E9d)));
            return;
        }
        int[] iArr = this.keys;
        if (i == iArr.length) {
            rehash(iArr.length);
        }
    }

    private static int adjustCapacity(int capacity) {
        return capacity | 1;
    }

    /* access modifiers changed from: private */
    public void removeAt(int index) {
        this.size--;
        this.keys[index] = 0;
        this.values[index] = null;
        int nextFree = index;
        int i = probeNext(index);
        while (this.values[i] != null) {
            int bucket = hashIndex(this.keys[i]);
            if ((i < bucket && (bucket <= nextFree || nextFree <= i)) || (bucket <= nextFree && nextFree <= i)) {
                int[] iArr = this.keys;
                iArr[nextFree] = iArr[i];
                V[] vArr = this.values;
                vArr[nextFree] = vArr[i];
                iArr[i] = 0;
                vArr[i] = null;
                nextFree = i;
            }
            i = probeNext(i);
        }
    }

    private int calcMaxSize(int capacity) {
        return Math.min(capacity - 1, (int) (((float) capacity) * this.loadFactor));
    }

    private void rehash(int newCapacity) {
        V[] vArr;
        int[] oldKeys = this.keys;
        V[] oldVals = this.values;
        this.keys = new int[newCapacity];
        this.values = new Object[newCapacity];
        this.maxSize = calcMaxSize(newCapacity);
        for (int i = 0; i < oldVals.length; i++) {
            V oldVal = oldVals[i];
            if (oldVal != null) {
                int oldKey = oldKeys[i];
                int index = hashIndex(oldKey);
                while (true) {
                    vArr = this.values;
                    if (vArr[index] == null) {
                        break;
                    }
                    index = probeNext(index);
                }
                this.keys[index] = oldKey;
                vArr[index] = toInternal(oldVal);
            }
        }
    }

    public final class IteratorImpl implements Iterator<IntObjectMap.Entry<V>>, IntObjectMap.Entry<V> {
        private int entryIndex;
        private int nextIndex;
        private int prevIndex;

        private IteratorImpl() {
            this.prevIndex = -1;
            this.nextIndex = -1;
            this.entryIndex = -1;
        }

        /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void scanNext() {
            /*
                r2 = this;
            L_0x0000:
                int r0 = r2.nextIndex
                int r0 = r0 + 1
                r2.nextIndex = r0
                io.netty.util.collection.IntObjectHashMap r1 = io.netty.util.collection.IntObjectHashMap.this
                java.lang.Object[] r1 = r1.values
                int r1 = r1.length
                if (r0 == r1) goto L_0x001b
                io.netty.util.collection.IntObjectHashMap r0 = io.netty.util.collection.IntObjectHashMap.this
                java.lang.Object[] r0 = r0.values
                int r1 = r2.nextIndex
                r0 = r0[r1]
                if (r0 == 0) goto L_0x0000
            L_0x001b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.util.collection.IntObjectHashMap.IteratorImpl.scanNext():void");
        }

        public boolean hasNext() {
            if (this.nextIndex == -1) {
                scanNext();
            }
            return this.nextIndex < IntObjectHashMap.this.keys.length;
        }

        public IntObjectMap.Entry<V> next() {
            if (hasNext()) {
                this.prevIndex = this.nextIndex;
                scanNext();
                this.entryIndex = this.prevIndex;
                return this;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            int i = this.prevIndex;
            if (i >= 0) {
                IntObjectHashMap.this.removeAt(i);
                this.prevIndex = -1;
                return;
            }
            throw new IllegalStateException("next must be called before each remove.");
        }

        public int key() {
            return IntObjectHashMap.this.keys[this.entryIndex];
        }

        public V value() {
            return IntObjectHashMap.toExternal(IntObjectHashMap.this.values[this.entryIndex]);
        }

        public void setValue(V value) {
            IntObjectHashMap.this.values[this.entryIndex] = IntObjectHashMap.toInternal(value);
        }
    }

    public String toString() {
        int i = this.size;
        if (i == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(i * 4);
        int i2 = 0;
        while (true) {
            V[] vArr = this.values;
            if (i2 < vArr.length) {
                V value = vArr[i2];
                if (value != null) {
                    sb.append(sb.length() == 0 ? "{" : ", ");
                    sb.append(keyToString(this.keys[i2]));
                    sb.append('=');
                    sb.append(value == this ? "(this Map)" : value);
                }
                i2++;
            } else {
                sb.append('}');
                return sb.toString();
            }
        }
    }

    /* access modifiers changed from: protected */
    public String keyToString(int key) {
        return Integer.toString(key);
    }
}
