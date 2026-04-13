package com.google.android.libraries.places.internal;

import java.io.IOException;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzafk extends IOException {
    zzafk() {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.");
    }

    zzafk(String str, Throwable th) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.: ".concat(String.valueOf(str)), th);
    }

    zzafk(Throwable th) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
    }
}
