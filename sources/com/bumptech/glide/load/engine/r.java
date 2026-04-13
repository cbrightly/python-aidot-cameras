package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.load.data.e;
import com.bumptech.glide.load.engine.h;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: LoadPath */
public class r<Data, ResourceType, Transcode> {
    private final Class<Data> a;
    private final Pools.Pool<List<Throwable>> b;
    private final List<? extends h<Data, ResourceType, Transcode>> c;
    private final String d;

    public r(Class<Data> dataClass, Class<ResourceType> resourceClass, Class<Transcode> transcodeClass, List<h<Data, ResourceType, Transcode>> decodePaths, Pools.Pool<List<Throwable>> listPool) {
        this.a = dataClass;
        this.b = listPool;
        this.c = (List) i.c(decodePaths);
        this.d = "Failed LoadPath{" + dataClass.getSimpleName() + "->" + resourceClass.getSimpleName() + "->" + transcodeClass.getSimpleName() + "}";
    }

    public t<Transcode> a(e<Data> rewinder, @NonNull com.bumptech.glide.load.i options, int width, int height, h.a<ResourceType> decodeCallback) {
        List<Throwable> throwables = (List) i.d(this.b.acquire());
        try {
            return b(rewinder, options, width, height, decodeCallback, throwables);
        } finally {
            this.b.release(throwables);
        }
    }

    private t<Transcode> b(e<Data> rewinder, @NonNull com.bumptech.glide.load.i options, int width, int height, h.a<ResourceType> decodeCallback, List<Throwable> exceptions) {
        List<Throwable> list = exceptions;
        int size = this.c.size();
        Resource<Transcode> result = null;
        for (int i = 0; i < size; i++) {
            try {
                result = ((h) this.c.get(i)).a(rewinder, width, height, options, decodeCallback);
            } catch (GlideException e) {
                list.add(e);
            }
            if (result != null) {
                break;
            }
        }
        if (result != null) {
            return result;
        }
        throw new GlideException(this.d, (List<Throwable>) new ArrayList(list));
    }

    public String toString() {
        return "LoadPath{decodePaths=" + Arrays.toString(this.c.toArray()) + '}';
    }
}
