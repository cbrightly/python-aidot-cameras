package com.bumptech.glide.load.model;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.g;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import net.sqlcipher.database.SQLiteDatabase;

/* compiled from: FileLoader */
public class f<Data> implements n<File, Data> {
    private final d<Data> a;

    /* compiled from: FileLoader */
    public interface d<Data> {
        Class<Data> a();

        Data b(File file);

        void close(Data data);
    }

    public f(d<Data> fileOpener) {
        this.a = fileOpener;
    }

    /* renamed from: c */
    public n.a<Data> b(@NonNull File model, int width, int height, @NonNull i options) {
        return new n.a<>(new com.bumptech.glide.signature.d(model), new c(model, this.a));
    }

    /* renamed from: d */
    public boolean a(@NonNull File model) {
        return true;
    }

    /* compiled from: FileLoader */
    public static final class c<Data> implements com.bumptech.glide.load.data.d<Data> {
        private final File c;
        private final d<Data> d;
        private Data f;

        c(File file, d<Data> opener) {
            this.c = file;
            this.d = opener;
        }

        public void d(@NonNull g priority, @NonNull d.a<? super Data> callback) {
            try {
                Data b = this.d.b(this.c);
                this.f = b;
                callback.e(b);
            } catch (FileNotFoundException e) {
                if (Log.isLoggable("FileLoader", 3)) {
                    Log.d("FileLoader", "Failed to open file", e);
                }
                callback.c(e);
            }
        }

        public void b() {
            Data data = this.f;
            if (data != null) {
                try {
                    this.d.close(data);
                } catch (IOException e) {
                }
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

    /* compiled from: FileLoader */
    public static class a<Data> implements o<File, Data> {
        private final d<Data> a;

        public a(d<Data> opener) {
            this.a = opener;
        }

        @NonNull
        public final n<File, Data> b(@NonNull r multiFactory) {
            return new f(this.a);
        }
    }

    /* compiled from: FileLoader */
    public static class e extends a<InputStream> {

        /* compiled from: FileLoader */
        public class a implements d<InputStream> {
            a() {
            }

            /* renamed from: d */
            public InputStream b(File file) {
                return new FileInputStream(file);
            }

            /* renamed from: c */
            public void close(InputStream inputStream) {
                inputStream.close();
            }

            public Class<InputStream> a() {
                return InputStream.class;
            }
        }

        public e() {
            super(new a());
        }
    }

    /* compiled from: FileLoader */
    public static class b extends a<ParcelFileDescriptor> {

        /* compiled from: FileLoader */
        public class a implements d<ParcelFileDescriptor> {
            a() {
            }

            /* renamed from: d */
            public ParcelFileDescriptor b(File file) {
                return ParcelFileDescriptor.open(file, SQLiteDatabase.CREATE_IF_NECESSARY);
            }

            /* renamed from: c */
            public void close(ParcelFileDescriptor parcelFileDescriptor) {
                parcelFileDescriptor.close();
            }

            public Class<ParcelFileDescriptor> a() {
                return ParcelFileDescriptor.class;
            }
        }

        public b() {
            super(new a());
        }
    }
}
