package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import com.bumptech.glide.g;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.signature.d;

/* compiled from: UnitModelLoader */
public class v<Model> implements n<Model, Model> {
    private static final v<?> a = new v<>();

    public static <T> v<T> c() {
        return a;
    }

    public n.a<Model> b(@NonNull Model model, int width, int height, @NonNull i options) {
        return new n.a<>(new d(model), new b(model));
    }

    public boolean a(@NonNull Model model) {
        return true;
    }

    /* compiled from: UnitModelLoader */
    public static class b<Model> implements com.bumptech.glide.load.data.d<Model> {
        private final Model c;

        b(Model resource) {
            this.c = resource;
        }

        public void d(@NonNull g priority, @NonNull d.a<? super Model> callback) {
            callback.e(this.c);
        }

        public void b() {
        }

        public void cancel() {
        }

        @NonNull
        public Class<Model> a() {
            return this.c.getClass();
        }

        @NonNull
        public com.bumptech.glide.load.a getDataSource() {
            return com.bumptech.glide.load.a.LOCAL;
        }
    }

    /* compiled from: UnitModelLoader */
    public static class a<Model> implements o<Model, Model> {
        private static final a<?> a = new a<>();

        public static <T> a<T> a() {
            return a;
        }

        @NonNull
        public n<Model, Model> b(r multiFactory) {
            return v.c();
        }
    }
}
