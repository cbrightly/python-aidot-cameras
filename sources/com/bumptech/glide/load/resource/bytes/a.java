package com.bumptech.glide.load.resource.bytes;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.data.e;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferRewinder */
public class a implements e<ByteBuffer> {
    private final ByteBuffer a;

    public a(ByteBuffer buffer) {
        this.a = buffer;
    }

    @NonNull
    /* renamed from: c */
    public ByteBuffer a() {
        this.a.position(0);
        return this.a;
    }

    public void b() {
    }

    /* renamed from: com.bumptech.glide.load.resource.bytes.a$a  reason: collision with other inner class name */
    /* compiled from: ByteBufferRewinder */
    public static class C0042a implements e.a<ByteBuffer> {
        @NonNull
        /* renamed from: c */
        public e<ByteBuffer> b(ByteBuffer data) {
            return new a(data);
        }

        @NonNull
        public Class<ByteBuffer> a() {
            return ByteBuffer.class;
        }
    }
}
