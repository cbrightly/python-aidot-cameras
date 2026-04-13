package com.clj.fastble.utils;

import com.clj.fastble.bluetooth.d;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: BleLruHashMap */
public class b<K, V> extends LinkedHashMap<K, V> {
    private final int MAX_SIZE;

    public b(int saveSize) {
        super(((int) Math.ceil(((double) saveSize) / 0.75d)) + 1, 0.75f, true);
        this.MAX_SIZE = saveSize;
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Map.Entry eldest) {
        if (size() > this.MAX_SIZE && (eldest.getValue() instanceof d)) {
            ((d) eldest.getValue()).K();
        }
        return size() > this.MAX_SIZE;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : entrySet()) {
            sb.append(String.format(Locale.US, "%s:%s ", new Object[]{entry.getKey(), entry.getValue()}));
        }
        return sb.toString();
    }
}
