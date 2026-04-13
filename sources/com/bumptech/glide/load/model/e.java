package com.bumptech.glide.load.model;

import android.util.Base64;
import androidx.annotation.NonNull;
import com.bumptech.glide.g;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.signature.d;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: DataUrlLoader */
public final class e<Model, Data> implements n<Model, Data> {
    private final a<Data> a;

    /* compiled from: DataUrlLoader */
    public interface a<Data> {
        Class<Data> a();

        void close(Data data);

        Data decode(String str);
    }

    public e(a<Data> dataDecoder) {
        this.a = dataDecoder;
    }

    public n.a<Data> b(@NonNull Model model, int width, int height, @NonNull i options) {
        return new n.a<>(new d(model), new b(model.toString(), this.a));
    }

    public boolean a(@NonNull Model model) {
        return model.toString().startsWith("data:image");
    }

    /* compiled from: DataUrlLoader */
    public static final class b<Data> implements com.bumptech.glide.load.data.d<Data> {
        private final String c;
        private final a<Data> d;
        private Data f;

        b(String dataUri, a<Data> reader) {
            this.c = dataUri;
            this.d = reader;
        }

        public void d(@NonNull g priority, @NonNull d.a<? super Data> callback) {
            try {
                Data decode = this.d.decode(this.c);
                this.f = decode;
                callback.e(decode);
            } catch (IllegalArgumentException e) {
                callback.c(e);
            }
        }

        public void b() {
            try {
                this.d.close(this.f);
            } catch (IOException e) {
            }
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

    /* compiled from: DataUrlLoader */
    public static final class c<Model> implements o<Model, InputStream> {
        private final a<InputStream> a = new a();

        /* compiled from: DataUrlLoader */
        public class a implements a<InputStream> {
            a() {
            }

            /* renamed from: c */
            public InputStream decode(String url) {
                if (url.startsWith("data:image")) {
                    int commaIndex = url.indexOf(44);
                    if (commaIndex == -1) {
                        throw new IllegalArgumentException("Missing comma in data URL.");
                    } else if (url.substring(0, commaIndex).endsWith(";base64")) {
                        return new ByteArrayInputStream(Base64.decode(url.substring(commaIndex + 1), 0));
                    } else {
                        throw new IllegalArgumentException("Not a base64 image data URL.");
                    }
                } else {
                    throw new IllegalArgumentException("Not a valid image data URL.");
                }
            }

            /* renamed from: b */
            public void close(InputStream inputStream) {
                inputStream.close();
            }

            public Class<InputStream> a() {
                return InputStream.class;
            }
        }

        @NonNull
        public n<Model, InputStream> b(@NonNull r multiFactory) {
            return new e(this.a);
        }
    }
}
