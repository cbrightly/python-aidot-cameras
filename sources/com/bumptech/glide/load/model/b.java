package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import com.bumptech.glide.g;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* compiled from: ByteArrayLoader */
public class b<Data> implements n<byte[], Data> {
    private final C0036b<Data> a;

    /* renamed from: com.bumptech.glide.load.model.b$b  reason: collision with other inner class name */
    /* compiled from: ByteArrayLoader */
    public interface C0036b<Data> {
        Class<Data> a();

        Data b(byte[] bArr);
    }

    public b(C0036b<Data> converter) {
        this.a = converter;
    }

    /* renamed from: c */
    public n.a<Data> b(@NonNull byte[] model, int width, int height, @NonNull i options) {
        return new n.a<>(new com.bumptech.glide.signature.d(model), new c(model, this.a));
    }

    /* renamed from: d */
    public boolean a(@NonNull byte[] model) {
        return true;
    }

    /* compiled from: ByteArrayLoader */
    public static class c<Data> implements com.bumptech.glide.load.data.d<Data> {
        private final byte[] c;
        private final C0036b<Data> d;

        c(byte[] model, C0036b<Data> converter) {
            this.c = model;
            this.d = converter;
        }

        public void d(@NonNull g priority, @NonNull d.a<? super Data> callback) {
            callback.e(this.d.b(this.c));
        }

        public void b() {
        }

        public void cancel() {
        }

        @NonNull
        public Class<Data> a() {
            return this.d.a();
        }

        @NonNull
        public com.bumptech.glide.load.a getDataSource() {
            return com.bumptech.glide.load.a.LOCAL;
        }
    }

    /* compiled from: ByteArrayLoader */
    public static class a implements o<byte[], ByteBuffer> {

        /* renamed from: com.bumptech.glide.load.model.b$a$a  reason: collision with other inner class name */
        /* compiled from: ByteArrayLoader */
        public class C0035a implements C0036b<ByteBuffer> {
            C0035a() {
            }

            /* renamed from: c */
            public ByteBuffer b(byte[] model) {
                return ByteBuffer.wrap(model);
            }

            public Class<ByteBuffer> a() {
                return ByteBuffer.class;
            }
        }

        @NonNull
        public n<byte[], ByteBuffer> b(@NonNull r multiFactory) {
            return new b(new C0035a());
        }
    }

    /* compiled from: ByteArrayLoader */
    public static class d implements o<byte[], InputStream> {

        /* compiled from: ByteArrayLoader */
        public class a implements C0036b<InputStream> {
            a() {
            }

            /* renamed from: c */
            public InputStream b(byte[] model) {
                return new ByteArrayInputStream(model);
            }

            public Class<InputStream> a() {
                return InputStream.class;
            }
        }

        @NonNull
        public n<byte[], InputStream> b(@NonNull r multiFactory) {
            return new b(new a());
        }
    }
}
