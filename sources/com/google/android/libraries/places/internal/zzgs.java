package com.google.android.libraries.places.internal;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgs extends LinkedHashMap {
    zzgs(int i, float f, boolean z) {
        super(16, 0.75f, true);
    }

    /* access modifiers changed from: protected */
    public final boolean removeEldestEntry(Map.Entry entry) {
        return size() > 10;
    }
}
