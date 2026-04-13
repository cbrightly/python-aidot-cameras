package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaiw extends IllegalArgumentException {
    zzaiw(int i, int i2) {
        super("Unpaired surrogate at index " + i + " of " + i2);
    }
}
