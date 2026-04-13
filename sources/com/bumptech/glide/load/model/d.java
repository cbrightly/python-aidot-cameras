package com.bumptech.glide.load.model;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.g;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferFileLoader */
public class d implements n<File, ByteBuffer> {
    /* renamed from: c */
    public n.a<ByteBuffer> b(@NonNull File file, int width, int height, @NonNull i options) {
        return new n.a<>(new com.bumptech.glide.signature.d(file), new a(file));
    }

    /* renamed from: d */
    public boolean a(@NonNull File file) {
        return true;
    }

    /* compiled from: ByteBufferFileLoader */
    public static class b implements o<File, ByteBuffer> {
        @NonNull
        public n<File, ByteBuffer> b(@NonNull r multiFactory) {
            return new d();
        }
    }

    /* compiled from: ByteBufferFileLoader */
    public static final class a implements com.bumptech.glide.load.data.d<ByteBuffer> {
        private final File c;

        a(File file) {
            this.c = file;
        }

        public void d(@NonNull g priority, @NonNull d.a<? super ByteBuffer> callback) {
            try {
                callback.e(com.bumptech.glide.util.a.a(this.c));
            } catch (IOException e) {
                if (Log.isLoggable("ByteBufferFileLoader", 3)) {
                    Log.d("ByteBufferFileLoader", "Failed to obtain ByteBuffer for file", e);
                }
                callback.c(e);
            }
        }

        public void b() {
        }

        public void cancel() {
        }

        @NonNull
        public Class<ByteBuffer> a() {
            return ByteBuffer.class;
        }

        @NonNull
        public com.bumptech.glide.load.a getDataSource() {
            return com.bumptech.glide.load.a.LOCAL;
        }
    }
}
