package com.alibaba.fastjson.util;

import java.util.Arrays;

public class IdentityHashMap<K, V> {
    public static final int DEFAULT_SIZE = 8192;
    private final Entry<K, V>[] buckets;
    private final int indexMask;

    public IdentityHashMap() {
        this(8192);
    }

    public IdentityHashMap(int tableSize) {
        this.indexMask = tableSize - 1;
        this.buckets = new Entry[tableSize];
    }

    public final V get(K key) {
        int hash = System.identityHashCode(key);
        for (Entry<K, V> entry = this.buckets[this.indexMask & hash]; entry != null; entry = entry.next) {
            if (key == entry.key) {
                return entry.value;
            }
        }
        return null;
    }

    public Class findClass(String keyString) {
        int i = 0;
        while (true) {
            Entry<K, V>[] entryArr = this.buckets;
            if (i >= entryArr.length) {
                return null;
            }
            Entry<K, V> bucket = entryArr[i];
            if (bucket != null) {
                for (Entry<K, V> entry = bucket; entry != null; entry = entry.next) {
                    K k = bucket.key;
                    if (k instanceof Class) {
                        Class clazz = (Class) k;
                        if (clazz.getName().equals(keyString)) {
                            return clazz;
                        }
                    }
                }
                continue;
            }
            i++;
        }
    }

    public boolean put(K key, V value) {
        int hash = System.identityHashCode(key);
        int bucket = this.indexMask & hash;
        for (Entry<K, V> entry = this.buckets[bucket]; entry != null; entry = entry.next) {
            if (key == entry.key) {
                entry.value = value;
                return true;
            }
        }
        this.buckets[bucket] = new Entry<>(key, value, hash, this.buckets[bucket]);
        return false;
    }

    public static final class Entry<K, V> {
        public final int hashCode;
        public final K key;
        public final Entry<K, V> next;
        public V value;

        public Entry(K key2, V value2, int hash, Entry<K, V> next2) {
            this.key = key2;
            this.value = value2;
            this.next = next2;
            this.hashCode = hash;
        }
    }

    public void clear() {
        Arrays.fill(this.buckets, (Object) null);
    }

    public int size() {
        int count = 0;
        for (Entry<K, V> bucket : this.buckets) {
            while (bucket != null) {
                count++;
                bucket = bucket.next;
            }
        }
        return count;
    }
}
