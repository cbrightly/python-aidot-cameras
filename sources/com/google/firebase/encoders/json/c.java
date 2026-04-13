package com.google.firebase.encoders.json;

import com.google.firebase.encoders.ValueEncoder;
import com.google.firebase.encoders.ValueEncoderContext;

/* compiled from: lambda */
public final /* synthetic */ class c implements ValueEncoder {
    public static final /* synthetic */ c a = new c();

    private /* synthetic */ c() {
    }

    public final void encode(Object obj, Object obj2) {
        ((ValueEncoderContext) obj2).add(((Boolean) obj).booleanValue());
    }
}
