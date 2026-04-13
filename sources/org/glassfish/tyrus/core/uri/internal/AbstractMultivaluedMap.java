package org.glassfish.tyrus.core.uri.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMultivaluedMap<K, V> implements MultivaluedMap<K, V> {
    protected final Map<K, List<V>> store;

    public AbstractMultivaluedMap(Map<K, List<V>> store2) {
        if (store2 != null) {
            this.store = store2;
            return;
        }
        throw new NullPointerException("Underlying store must not be 'null'.");
    }

    public final void putSingle(K key, V value) {
        List<V> values = getValues(key);
        values.clear();
        if (value != null) {
            values.add(value);
        } else {
            addNull(values);
        }
    }

    /* access modifiers changed from: protected */
    public void addNull(List<V> list) {
    }

    /* access modifiers changed from: protected */
    public void addFirstNull(List<V> list) {
    }

    public final void add(K key, V value) {
        List<V> values = getValues(key);
        if (value != null) {
            values.add(value);
        } else {
            addNull(values);
        }
    }

    public final void addAll(K key, V... newValues) {
        if (newValues == null) {
            throw new NullPointerException("Supplied array of values must not be null.");
        } else if (newValues.length != 0) {
            List<V> values = getValues(key);
            for (V value : newValues) {
                if (value != null) {
                    values.add(value);
                } else {
                    addNull(values);
                }
            }
        }
    }

    public final void addAll(K key, List<V> valueList) {
        if (valueList == null) {
            throw new NullPointerException("Supplied list of values must not be null.");
        } else if (!valueList.isEmpty()) {
            List<V> values = getValues(key);
            for (V value : valueList) {
                if (value != null) {
                    values.add(value);
                } else {
                    addNull(values);
                }
            }
        }
    }

    public final V getFirst(K key) {
        List<V> values = this.store.get(key);
        if (values == null || values.size() <= 0) {
            return null;
        }
        return values.get(0);
    }

    public final void addFirst(K key, V value) {
        List<V> values = getValues(key);
        if (value != null) {
            values.add(0, value);
        } else {
            addFirstNull(values);
        }
    }

    /* access modifiers changed from: protected */
    public final List<V> getValues(K key) {
        List<V> l = this.store.get(key);
        if (l != null) {
            return l;
        }
        List<V> l2 = new LinkedList<>();
        this.store.put(key, l2);
        return l2;
    }

    public String toString() {
        return this.store.toString();
    }

    public int hashCode() {
        return this.store.hashCode();
    }

    public boolean equals(Object o) {
        return this.store.equals(o);
    }

    public Collection<List<V>> values() {
        return this.store.values();
    }

    public int size() {
        return this.store.size();
    }

    public List<V> remove(Object key) {
        return this.store.remove(key);
    }

    public void putAll(Map<? extends K, ? extends List<V>> m) {
        this.store.putAll(m);
    }

    public List<V> put(K key, List<V> value) {
        return this.store.put(key, value);
    }

    public Set<K> keySet() {
        return this.store.keySet();
    }

    public boolean isEmpty() {
        return this.store.isEmpty();
    }

    public List<V> get(Object key) {
        return this.store.get(key);
    }

    public Set<Map.Entry<K, List<V>>> entrySet() {
        return this.store.entrySet();
    }

    public boolean containsValue(Object value) {
        return this.store.containsValue(value);
    }

    public boolean containsKey(Object key) {
        return this.store.containsKey(key);
    }

    public void clear() {
        this.store.clear();
    }

    public boolean equalsIgnoreValueOrder(MultivaluedMap<K, V> omap) {
        if (this == omap) {
            return true;
        }
        if (!keySet().equals(omap.keySet())) {
            return false;
        }
        for (Map.Entry<K, List<V>> e : entrySet()) {
            List<V> olist = omap.get(e.getKey());
            if (e.getValue().size() != olist.size()) {
                return false;
            }
            Iterator it = e.getValue().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (!olist.contains(it.next())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
