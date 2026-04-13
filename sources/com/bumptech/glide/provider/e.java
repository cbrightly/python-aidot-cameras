package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.k;
import com.bumptech.glide.provider.ResourceDecoderRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ResourceDecoderRegistry */
public class e {
    private final List<String> a = new ArrayList();
    private final Map<String, List<a<?, ?>>> b = new HashMap();

    public synchronized void f(@NonNull List<String> buckets) {
        List<String> previousBuckets = new ArrayList<>(this.a);
        this.a.clear();
        for (String bucket : buckets) {
            this.a.add(bucket);
        }
        for (String previousBucket : previousBuckets) {
            if (!buckets.contains(previousBucket)) {
                this.a.add(previousBucket);
            }
        }
    }

    @NonNull
    public synchronized <T, R> List<k<T, R>> b(@NonNull Class<T> dataClass, @NonNull Class<R> resourceClass) {
        List<ResourceDecoder<T, R>> result;
        result = new ArrayList<>();
        for (String bucket : this.a) {
            List<ResourceDecoderRegistry.Entry<?, ?>> entries = this.b.get(bucket);
            if (entries != null) {
                for (ResourceDecoderRegistry.Entry<?, ?> entry : entries) {
                    if (entry.a(dataClass, resourceClass)) {
                        result.add(entry.c);
                    }
                }
            }
        }
        return result;
    }

    @NonNull
    public synchronized <T, R> List<Class<R>> d(@NonNull Class<T> dataClass, @NonNull Class<R> resourceClass) {
        List<Class<R>> result;
        result = new ArrayList<>();
        for (String bucket : this.a) {
            List<ResourceDecoderRegistry.Entry<?, ?>> entries = this.b.get(bucket);
            if (entries != null) {
                for (ResourceDecoderRegistry.Entry<?, ?> entry : entries) {
                    if (entry.a(dataClass, resourceClass) && !result.contains(entry.b)) {
                        result.add(entry.b);
                    }
                }
            }
        }
        return result;
    }

    public synchronized <T, R> void a(@NonNull String bucket, @NonNull k<T, R> decoder, @NonNull Class<T> dataClass, @NonNull Class<R> resourceClass) {
        c(bucket).add(new a(dataClass, resourceClass, decoder));
    }

    public synchronized <T, R> void e(@NonNull String bucket, @NonNull k<T, R> decoder, @NonNull Class<T> dataClass, @NonNull Class<R> resourceClass) {
        c(bucket).add(0, new a(dataClass, resourceClass, decoder));
    }

    @NonNull
    private synchronized List<a<?, ?>> c(@NonNull String bucket) {
        List<ResourceDecoderRegistry.Entry<?, ?>> entries;
        if (!this.a.contains(bucket)) {
            this.a.add(bucket);
        }
        entries = this.b.get(bucket);
        if (entries == null) {
            entries = new ArrayList<>();
            this.b.put(bucket, entries);
        }
        return entries;
    }

    /* compiled from: ResourceDecoderRegistry */
    public static class a<T, R> {
        private final Class<T> a;
        final Class<R> b;
        final k<T, R> c;

        public a(@NonNull Class<T> dataClass, @NonNull Class<R> resourceClass, k<T, R> decoder) {
            this.a = dataClass;
            this.b = resourceClass;
            this.c = decoder;
        }

        public boolean a(@NonNull Class<?> dataClass, @NonNull Class<?> resourceClass) {
            return this.a.isAssignableFrom(dataClass) && resourceClass.isAssignableFrom(this.b);
        }
    }
}
