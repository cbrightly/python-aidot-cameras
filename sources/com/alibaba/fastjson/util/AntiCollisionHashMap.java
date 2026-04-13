package com.alibaba.fastjson.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class AntiCollisionHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int KEY = 16777619;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int M_MASK = -2023358765;
    static final int SEED = -2128831035;
    private static final long serialVersionUID = 362498820763181265L;
    private transient Set<Map.Entry<K, V>> entrySet;
    volatile transient Set<K> keySet;
    final float loadFactor;
    volatile transient int modCount;
    final int random;
    transient int size;
    transient Entry<K, V>[] table;
    int threshold;
    volatile transient Collection<V> values;

    private int hashString(String key) {
        int hash = this.random * SEED;
        for (int i = 0; i < key.length(); i++) {
            hash = (KEY * hash) ^ key.charAt(i);
        }
        return ((hash >> 1) ^ hash) & M_MASK;
    }

    public AntiCollisionHashMap(int initialCapacity, float loadFactor2) {
        this.keySet = null;
        this.values = null;
        this.random = new Random().nextInt(99999);
        this.entrySet = null;
        if (initialCapacity >= 0) {
            initialCapacity = initialCapacity > 1073741824 ? 1073741824 : initialCapacity;
            if (loadFactor2 <= 0.0f || Float.isNaN(loadFactor2)) {
                throw new IllegalArgumentException("Illegal load factor: " + loadFactor2);
            }
            int capacity = 1;
            while (capacity < initialCapacity) {
                capacity <<= 1;
            }
            this.loadFactor = loadFactor2;
            this.threshold = (int) (((float) capacity) * loadFactor2);
            this.table = new Entry[capacity];
            init();
            return;
        }
        throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
    }

    public AntiCollisionHashMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public AntiCollisionHashMap() {
        this.keySet = null;
        this.values = null;
        this.random = new Random().nextInt(99999);
        this.entrySet = null;
        this.loadFactor = 0.75f;
        this.threshold = 12;
        this.table = new Entry[16];
        init();
    }

    public AntiCollisionHashMap(Map<? extends K, ? extends V> m) {
        this(Math.max(((int) (((float) m.size()) / 0.75f)) + 1, 16), 0.75f);
        putAllForCreate(m);
    }

    /* access modifiers changed from: package-private */
    public void init() {
    }

    static int hash(int h) {
        int h2 = h * h;
        int h3 = h2 ^ ((h2 >>> 20) ^ (h2 >>> 12));
        return ((h3 >>> 7) ^ h3) ^ (h3 >>> 4);
    }

    static int indexFor(int h, int length) {
        return (length - 1) & h;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public V get(Object key) {
        int hash;
        if (key == null) {
            return getForNullKey();
        }
        if (key instanceof String) {
            hash = hash(hashString((String) key));
        } else {
            hash = hash(key.hashCode());
        }
        Entry<K, V>[] entryArr = this.table;
        for (Entry<K, V> e = entryArr[indexFor(hash, entryArr.length)]; e != null; e = e.next) {
            if (e.hash == hash) {
                Object obj = e.key;
                Object k = obj;
                if (obj == key || key.equals(k)) {
                    return e.value;
                }
            }
        }
        return null;
    }

    private V getForNullKey() {
        for (Entry<K, V> e = this.table[0]; e != null; e = e.next) {
            if (e.key == null) {
                return e.value;
            }
        }
        return null;
    }

    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    /* access modifiers changed from: package-private */
    public final Entry<K, V> getEntry(Object key) {
        int hash;
        if (key == null) {
            hash = 0;
        } else if (key instanceof String) {
            hash = hash(hashString((String) key));
        } else {
            hash = hash(key.hashCode());
        }
        Entry<K, V>[] entryArr = this.table;
        for (Entry<K, V> e = entryArr[indexFor(hash, entryArr.length)]; e != null; e = e.next) {
            if (e.hash == hash) {
                Object obj = e.key;
                Object k = obj;
                if (obj == key || (key != null && key.equals(k))) {
                    return e;
                }
            }
        }
        return null;
    }

    public V put(K key, V value) {
        int hash;
        if (key == null) {
            return putForNullKey(value);
        }
        if (key instanceof String) {
            hash = hash(hashString((String) key));
        } else {
            hash = hash(key.hashCode());
        }
        int i = indexFor(hash, this.table.length);
        for (Entry<K, V> e = this.table[i]; e != null; e = e.next) {
            if (e.hash == hash) {
                Object obj = e.key;
                Object k = obj;
                if (obj == key || key.equals(k)) {
                    V oldValue = e.value;
                    e.value = value;
                    return oldValue;
                }
            }
        }
        this.modCount++;
        addEntry(hash, key, value, i);
        return null;
    }

    private V putForNullKey(V value) {
        for (Entry<K, V> e = this.table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        this.modCount++;
        addEntry(0, (Object) null, value, 0);
        return null;
    }

    private void putForCreate(K key, V value) {
        int hash;
        if (key == null) {
            hash = 0;
        } else if (key instanceof String) {
            hash = hash(hashString((String) key));
        } else {
            hash = hash(key.hashCode());
        }
        int i = indexFor(hash, this.table.length);
        for (Entry<K, V> e = this.table[i]; e != null; e = e.next) {
            if (e.hash == hash) {
                Object obj = e.key;
                Object k = obj;
                if (obj == key || (key != null && key.equals(k))) {
                    e.value = value;
                    return;
                }
            }
        }
        createEntry(hash, key, value, i);
    }

    private void putAllForCreate(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            putForCreate(e.getKey(), e.getValue());
        }
    }

    /* access modifiers changed from: package-private */
    public void resize(int newCapacity) {
        if (this.table.length == 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            return;
        }
        Entry<K, V>[] newTable = new Entry[newCapacity];
        transfer(newTable);
        this.table = newTable;
        this.threshold = (int) (((float) newCapacity) * this.loadFactor);
    }

    /* access modifiers changed from: package-private */
    public void transfer(Entry[] newTable) {
        Entry<K, V>[] src = this.table;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            Entry<K, V> e = src[j];
            if (e != null) {
                src[j] = null;
                do {
                    Entry<K, V> next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded != 0) {
            if (numKeysToBeAdded > this.threshold) {
                int targetCapacity = (int) ((((float) numKeysToBeAdded) / this.loadFactor) + 1.0f);
                if (targetCapacity > 1073741824) {
                    targetCapacity = 1073741824;
                }
                int newCapacity = this.table.length;
                while (newCapacity < targetCapacity) {
                    newCapacity <<= 1;
                }
                if (newCapacity > this.table.length) {
                    resize(newCapacity);
                }
            }
            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
                put(e.getKey(), e.getValue());
            }
        }
    }

    public V remove(Object key) {
        Entry<K, V> e = removeEntryForKey(key);
        if (e == null) {
            return null;
        }
        return e.value;
    }

    /* access modifiers changed from: package-private */
    public final Entry<K, V> removeEntryForKey(Object key) {
        int hash;
        if (key == null) {
            hash = 0;
        } else if (key instanceof String) {
            hash = hash(hashString((String) key));
        } else {
            hash = hash(key.hashCode());
        }
        int i = indexFor(hash, this.table.length);
        Entry<K, V> prev = this.table[i];
        Entry<K, V> e = prev;
        while (e != null) {
            Entry<K, V> next = e.next;
            if (e.hash == hash) {
                Object obj = e.key;
                Object k = obj;
                if (obj == key || (key != null && key.equals(k))) {
                    this.modCount++;
                    this.size--;
                    if (prev == e) {
                        this.table[i] = next;
                    } else {
                        prev.next = next;
                    }
                    return e;
                }
            }
            prev = e;
            e = next;
        }
        return e;
    }

    /* access modifiers changed from: package-private */
    public final Entry<K, V> removeMapping(Object o) {
        int hash;
        if (!(o instanceof Map.Entry)) {
            return null;
        }
        Map.Entry<K, V> entry = (Map.Entry) o;
        Object key = entry.getKey();
        if (key == null) {
            hash = 0;
        } else if (key instanceof String) {
            hash = hash(hashString((String) key));
        } else {
            hash = hash(key.hashCode());
        }
        int i = indexFor(hash, this.table.length);
        Entry<K, V> prev = this.table[i];
        Entry<K, V> e = prev;
        while (e != null) {
            Entry<K, V> next = e.next;
            if (e.hash != hash || !e.equals(entry)) {
                prev = e;
                e = next;
            } else {
                this.modCount++;
                this.size--;
                if (prev == e) {
                    this.table[i] = next;
                } else {
                    prev.next = next;
                }
                return e;
            }
        }
        return e;
    }

    public void clear() {
        this.modCount++;
        Entry[] tab = this.table;
        for (int i = 0; i < tab.length; i++) {
            tab[i] = null;
        }
        this.size = 0;
    }

    public boolean containsValue(Object value) {
        if (value == null) {
            return containsNullValue();
        }
        Entry[] tab = this.table;
        for (Entry e : tab) {
            while (e != null) {
                if (value.equals(e.value)) {
                    return true;
                }
                e = e.next;
            }
        }
        return false;
    }

    private boolean containsNullValue() {
        Entry[] tab = this.table;
        for (Entry e : tab) {
            while (e != null) {
                if (e.value == null) {
                    return true;
                }
                e = e.next;
            }
        }
        return false;
    }

    public Object clone() {
        AntiCollisionHashMap<K, V> result = null;
        try {
            result = (AntiCollisionHashMap) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        result.table = new Entry[this.table.length];
        result.entrySet = null;
        result.modCount = 0;
        result.size = 0;
        result.init();
        result.putAllForCreate(this);
        return result;
    }

    public static class Entry<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        Entry<K, V> next;
        V value;

        Entry(int h, K k, V v, Entry<K, V> n) {
            this.value = v;
            this.next = n;
            this.key = k;
            this.hash = h;
        }

        public final K getKey() {
            return this.key;
        }

        public final V getValue() {
            return this.value;
        }

        public final V setValue(V newValue) {
            V oldValue = this.value;
            this.value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry) o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2) {
                    return true;
                }
                if (v1 == null || !v1.equals(v2)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public final int hashCode() {
            K k = this.key;
            int i = 0;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.value;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        public final String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /* access modifiers changed from: package-private */
    public void addEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V>[] entryArr = this.table;
        entryArr[bucketIndex] = new Entry<>(hash, key, value, entryArr[bucketIndex]);
        int i = this.size;
        this.size = i + 1;
        if (i >= this.threshold) {
            resize(this.table.length * 2);
        }
    }

    /* access modifiers changed from: package-private */
    public void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V>[] entryArr = this.table;
        entryArr[bucketIndex] = new Entry<>(hash, key, value, entryArr[bucketIndex]);
        this.size++;
    }

    public abstract class HashIterator<E> implements Iterator<E> {
        Entry<K, V> current;
        int expectedModCount;
        int index;
        Entry<K, V> next;

        HashIterator() {
            Entry entry;
            this.expectedModCount = AntiCollisionHashMap.this.modCount;
            if (AntiCollisionHashMap.this.size > 0) {
                Entry[] t = AntiCollisionHashMap.this.table;
                do {
                    int i = this.index;
                    if (i < t.length) {
                        this.index = i + 1;
                        entry = t[i];
                        this.next = entry;
                    } else {
                        return;
                    }
                } while (entry == null);
            }
        }

        public final boolean hasNext() {
            return this.next != null;
        }

        /* access modifiers changed from: package-private */
        public final Entry<K, V> nextEntry() {
            Entry entry;
            if (AntiCollisionHashMap.this.modCount == this.expectedModCount) {
                Entry<K, V> e = this.next;
                if (e != null) {
                    Entry<K, V> entry2 = e.next;
                    this.next = entry2;
                    if (entry2 == null) {
                        Entry[] t = AntiCollisionHashMap.this.table;
                        do {
                            int i = this.index;
                            if (i >= t.length) {
                                break;
                            }
                            this.index = i + 1;
                            entry = t[i];
                            this.next = entry;
                        } while (entry == null);
                    }
                    this.current = e;
                    return e;
                }
                throw new NoSuchElementException();
            }
            throw new ConcurrentModificationException();
        }

        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            } else if (AntiCollisionHashMap.this.modCount == this.expectedModCount) {
                Object k = this.current.key;
                this.current = null;
                AntiCollisionHashMap.this.removeEntryForKey(k);
                this.expectedModCount = AntiCollisionHashMap.this.modCount;
            } else {
                throw new ConcurrentModificationException();
            }
        }
    }

    public final class ValueIterator extends AntiCollisionHashMap<K, V>.HashIterator<V> {
        private ValueIterator() {
            super();
        }

        public V next() {
            return nextEntry().value;
        }
    }

    public final class KeyIterator extends AntiCollisionHashMap<K, V>.HashIterator<K> {
        private KeyIterator() {
            super();
        }

        public K next() {
            return nextEntry().getKey();
        }
    }

    public final class EntryIterator extends AntiCollisionHashMap<K, V>.HashIterator<Map.Entry<K, V>> {
        private EntryIterator() {
            super();
        }

        public Map.Entry<K, V> next() {
            return nextEntry();
        }
    }

    /* access modifiers changed from: package-private */
    public Iterator<K> newKeyIterator() {
        return new KeyIterator();
    }

    /* access modifiers changed from: package-private */
    public Iterator<V> newValueIterator() {
        return new ValueIterator();
    }

    /* access modifiers changed from: package-private */
    public Iterator<Map.Entry<K, V>> newEntryIterator() {
        return new EntryIterator();
    }

    public Set<K> keySet() {
        Set<K> ks = this.keySet;
        if (ks != null) {
            return ks;
        }
        KeySet keySet2 = new KeySet();
        this.keySet = keySet2;
        return keySet2;
    }

    public final class KeySet extends AbstractSet<K> {
        private KeySet() {
        }

        public Iterator<K> iterator() {
            return AntiCollisionHashMap.this.newKeyIterator();
        }

        public int size() {
            return AntiCollisionHashMap.this.size;
        }

        public boolean contains(Object o) {
            return AntiCollisionHashMap.this.containsKey(o);
        }

        public boolean remove(Object o) {
            return AntiCollisionHashMap.this.removeEntryForKey(o) != null;
        }

        public void clear() {
            AntiCollisionHashMap.this.clear();
        }
    }

    public Collection<V> values() {
        Collection<V> vs = this.values;
        if (vs != null) {
            return vs;
        }
        Values values2 = new Values();
        this.values = values2;
        return values2;
    }

    public final class Values extends AbstractCollection<V> {
        private Values() {
        }

        public Iterator<V> iterator() {
            return AntiCollisionHashMap.this.newValueIterator();
        }

        public int size() {
            return AntiCollisionHashMap.this.size;
        }

        public boolean contains(Object o) {
            return AntiCollisionHashMap.this.containsValue(o);
        }

        public void clear() {
            AntiCollisionHashMap.this.clear();
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return entrySet0();
    }

    private Set<Map.Entry<K, V>> entrySet0() {
        Set<Map.Entry<K, V>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        EntrySet entrySet2 = new EntrySet();
        this.entrySet = entrySet2;
        return entrySet2;
    }

    public final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return AntiCollisionHashMap.this.newEntryIterator();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<K, V> e = (Map.Entry) o;
            Entry<K, V> candidate = AntiCollisionHashMap.this.getEntry(e.getKey());
            if (candidate == null || !candidate.equals(e)) {
                return false;
            }
            return true;
        }

        public boolean remove(Object o) {
            return AntiCollisionHashMap.this.removeMapping(o) != null;
        }

        public int size() {
            return AntiCollisionHashMap.this.size;
        }

        public void clear() {
            AntiCollisionHashMap.this.clear();
        }
    }

    private void writeObject(ObjectOutputStream s) {
        Iterator<Map.Entry<K, V>> i = this.size > 0 ? entrySet0().iterator() : null;
        s.defaultWriteObject();
        s.writeInt(this.table.length);
        s.writeInt(this.size);
        if (i != null) {
            while (i.hasNext()) {
                Map.Entry<K, V> e = i.next();
                s.writeObject(e.getKey());
                s.writeObject(e.getValue());
            }
        }
    }

    private void readObject(ObjectInputStream s) {
        s.defaultReadObject();
        this.table = new Entry[s.readInt()];
        init();
        int size2 = s.readInt();
        for (int i = 0; i < size2; i++) {
            putForCreate(s.readObject(), s.readObject());
        }
    }
}
