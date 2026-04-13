package com.google.firebase.encoders.json;

import com.google.firebase.encoders.ValueEncoder;
import com.google.firebase.encoders.ValueEncoderContext;

/* compiled from: lambda */
public final /* synthetic */ class b implements ValueEncoder {
    public static final /* synthetic */ b a = new b();

    private /* synthetic */ b() {
    }

    public final void encode(Object obj, Object obj2) {
        ((ValueEncoderContext) obj2).add((String) obj);
    }
}
