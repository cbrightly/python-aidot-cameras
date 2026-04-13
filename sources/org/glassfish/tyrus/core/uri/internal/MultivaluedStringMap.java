package org.glassfish.tyrus.core.uri.internal;

import java.util.List;

public class MultivaluedStringMap extends MultivaluedHashMap<String, String> {
    static final long serialVersionUID = -6052320403766368902L;

    public MultivaluedStringMap(MultivaluedMap<? extends String, ? extends String> map) {
        super(map);
    }

    public MultivaluedStringMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public MultivaluedStringMap(int initialCapacity) {
        super(initialCapacity);
    }

    public MultivaluedStringMap() {
    }

    /* access modifiers changed from: protected */
    public void addFirstNull(List<String> values) {
        values.add("");
    }

    /* access modifiers changed from: protected */
    public void addNull(List<String> values) {
        values.add(0, "");
    }

    public final <A> A getFirst(String key, Class<A> type) {
        String value = (String) getFirst(key);
        if (value == null) {
            return null;
        }
        try {
            try {
                return type.getConstructor(new Class[]{String.class}).newInstance(new Object[]{value});
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(type.getName() + " has no String constructor", ex);
        }
    }

    public final <A> A getFirst(String key, A defaultValue) {
        String value = (String) getFirst(key);
        if (value == null) {
            return defaultValue;
        }
        Class<?> cls = defaultValue.getClass();
        try {
            try {
                return cls.getConstructor(new Class[]{String.class}).newInstance(new Object[]{value});
            } catch (Exception e) {
                return defaultValue;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(cls.getName() + " has no String constructor", ex);
        }
    }
}
