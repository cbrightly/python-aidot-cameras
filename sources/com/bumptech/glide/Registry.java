package com.bumptech.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.data.e;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.LoadPath;
import com.bumptech.glide.load.engine.h;
import com.bumptech.glide.load.engine.r;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.k;
import com.bumptech.glide.load.l;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.load.model.o;
import com.bumptech.glide.load.model.p;
import com.bumptech.glide.provider.a;
import com.bumptech.glide.provider.b;
import com.bumptech.glide.provider.c;
import com.bumptech.glide.provider.d;
import com.bumptech.glide.provider.e;
import com.bumptech.glide.provider.f;
import com.bumptech.glide.util.pool.FactoryPools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Registry {
    private final p a;
    private final a b;
    private final e c;
    private final f d;
    private final com.bumptech.glide.load.data.f e;
    private final com.bumptech.glide.load.resource.transcode.f f;
    private final b g;
    private final d h = new d();
    private final c i = new c();
    private final Pools.Pool<List<Throwable>> j;

    public Registry() {
        Pools.Pool<List<Throwable>> e2 = FactoryPools.e();
        this.j = e2;
        this.a = new p(e2);
        this.b = new a();
        this.c = new e();
        this.d = new f();
        this.e = new com.bumptech.glide.load.data.f();
        this.f = new com.bumptech.glide.load.resource.transcode.f();
        this.g = new b();
        u(Arrays.asList(new String[]{"Gif", "Bitmap", "BitmapDrawable"}));
    }

    @NonNull
    public <Data> Registry a(@NonNull Class<Data> dataClass, @NonNull com.bumptech.glide.load.d<Data> encoder) {
        this.b.a(dataClass, encoder);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry c(@NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull k<Data, TResource> decoder) {
        e("legacy_append", dataClass, resourceClass, decoder);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry e(@NonNull String bucket, @NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull k<Data, TResource> decoder) {
        this.c.a(bucket, decoder, dataClass, resourceClass);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry p(@NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull k<Data, TResource> decoder) {
        q("legacy_prepend_all", dataClass, resourceClass, decoder);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry q(@NonNull String bucket, @NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull k<Data, TResource> decoder) {
        this.c.e(bucket, decoder, dataClass, resourceClass);
        return this;
    }

    @NonNull
    public final Registry u(@NonNull List<String> buckets) {
        List<String> modifiedBuckets = new ArrayList<>(buckets.size());
        modifiedBuckets.add("legacy_prepend_all");
        for (String bucket : buckets) {
            modifiedBuckets.add(bucket);
        }
        modifiedBuckets.add("legacy_append");
        this.c.f(modifiedBuckets);
        return this;
    }

    @NonNull
    public <TResource> Registry b(@NonNull Class<TResource> resourceClass, @NonNull l<TResource> encoder) {
        this.d.a(resourceClass, encoder);
        return this;
    }

    @NonNull
    public <TResource> Registry o(@NonNull Class<TResource> resourceClass, @NonNull l<TResource> encoder) {
        this.d.c(resourceClass, encoder);
        return this;
    }

    @NonNull
    public Registry s(@NonNull e.a<?> factory) {
        this.e.b(factory);
        return this;
    }

    @NonNull
    public <TResource, Transcode> Registry t(@NonNull Class<TResource> resourceClass, @NonNull Class<Transcode> transcodeClass, @NonNull com.bumptech.glide.load.resource.transcode.e<TResource, Transcode> transcoder) {
        this.f.c(resourceClass, transcodeClass, transcoder);
        return this;
    }

    @NonNull
    public Registry r(@NonNull ImageHeaderParser parser) {
        this.g.a(parser);
        return this;
    }

    @NonNull
    public <Model, Data> Registry d(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull o<Model, Data> factory) {
        this.a.a(modelClass, dataClass, factory);
        return this;
    }

    @Nullable
    public <Data, TResource, Transcode> r<Data, TResource, Transcode> h(@NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull Class<Transcode> transcodeClass) {
        LoadPath<Data, TResource, Transcode> result = this.i.a(dataClass, resourceClass, transcodeClass);
        if (this.i.c(result)) {
            return null;
        }
        if (result == null) {
            List<DecodePath<Data, TResource, Transcode>> decodePaths = f(dataClass, resourceClass, transcodeClass);
            if (decodePaths.isEmpty()) {
                result = null;
            } else {
                result = new r<>(dataClass, resourceClass, transcodeClass, decodePaths, this.j);
            }
            this.i.d(dataClass, resourceClass, transcodeClass, result);
        }
        return result;
    }

    @NonNull
    private <Data, TResource, Transcode> List<h<Data, TResource, Transcode>> f(@NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull Class<Transcode> transcodeClass) {
        Class<Data> cls = dataClass;
        List<DecodePath<Data, TResource, Transcode>> decodePaths = new ArrayList<>();
        for (Class<TResource> registeredResourceClass : this.c.d(cls, resourceClass)) {
            for (Class<Transcode> registeredTranscodeClass : this.f.b(registeredResourceClass, transcodeClass)) {
                Class<Transcode> cls2 = registeredTranscodeClass;
                decodePaths.add(new h<>(dataClass, registeredResourceClass, registeredTranscodeClass, this.c.b(cls, registeredResourceClass), this.f.a(registeredResourceClass, registeredTranscodeClass), this.j));
            }
        }
        Class<Transcode> cls3 = transcodeClass;
        return decodePaths;
    }

    @NonNull
    public <Model, TResource, Transcode> List<Class<?>> j(@NonNull Class<Model> modelClass, @NonNull Class<TResource> resourceClass, @NonNull Class<Transcode> transcodeClass) {
        List<Class<?>> result = this.h.a(modelClass, resourceClass, transcodeClass);
        if (result == null) {
            result = new ArrayList<>();
            for (Class<?> dataClass : this.a.c(modelClass)) {
                for (Class<?> registeredResourceClass : this.c.d(dataClass, resourceClass)) {
                    if (!this.f.b(registeredResourceClass, transcodeClass).isEmpty() && !result.contains(registeredResourceClass)) {
                        result.add(registeredResourceClass);
                    }
                }
            }
            this.h.b(modelClass, resourceClass, transcodeClass, Collections.unmodifiableList(result));
        }
        return result;
    }

    public boolean n(@NonNull t<?> resource) {
        return this.d.b(resource.a()) != null;
    }

    @NonNull
    public <X> l<X> k(@NonNull t<X> resource) {
        ResourceEncoder<X> resourceEncoder = this.d.b(resource.a());
        if (resourceEncoder != null) {
            return resourceEncoder;
        }
        throw new NoResultEncoderAvailableException(resource.a());
    }

    @NonNull
    public <X> com.bumptech.glide.load.d<X> m(@NonNull X data) {
        Encoder<X> encoder = this.b.b(data.getClass());
        if (encoder != null) {
            return encoder;
        }
        throw new NoSourceEncoderAvailableException(data.getClass());
    }

    @NonNull
    public <X> com.bumptech.glide.load.data.e<X> l(@NonNull X data) {
        return this.e.a(data);
    }

    @NonNull
    public <Model> List<n<Model, ?>> i(@NonNull Model model) {
        return this.a.d(model);
    }

    @NonNull
    public List<ImageHeaderParser> g() {
        List<ImageHeaderParser> result = this.g.b();
        if (!result.isEmpty()) {
            return result;
        }
        throw new NoImageHeaderParserException();
    }

    public static class NoModelLoaderAvailableException extends MissingComponentException {
        public NoModelLoaderAvailableException(@NonNull Object model) {
            super("Failed to find any ModelLoaders registered for model class: " + model.getClass());
        }

        public <M> NoModelLoaderAvailableException(@NonNull M model, @NonNull List<n<M, ?>> matchingButNotHandlingModelLoaders) {
            super("Found ModelLoaders for model class: " + matchingButNotHandlingModelLoaders + ", but none that handle this specific model instance: " + model);
        }

        public NoModelLoaderAvailableException(@NonNull Class<?> modelClass, @NonNull Class<?> dataClass) {
            super("Failed to find any ModelLoaders for model: " + modelClass + " and data: " + dataClass);
        }
    }

    public static class NoResultEncoderAvailableException extends MissingComponentException {
        public NoResultEncoderAvailableException(@NonNull Class<?> resourceClass) {
            super("Failed to find result encoder for resource class: " + resourceClass + ", you may need to consider registering a new Encoder for the requested type or DiskCacheStrategy.DATA/DiskCacheStrategy.NONE if caching your transformed resource is unnecessary.");
        }
    }

    public static class NoSourceEncoderAvailableException extends MissingComponentException {
        public NoSourceEncoderAvailableException(@NonNull Class<?> dataClass) {
            super("Failed to find source encoder for data class: " + dataClass);
        }
    }

    public static class MissingComponentException extends RuntimeException {
        public MissingComponentException(@NonNull String message) {
            super(message);
        }
    }

    public static final class NoImageHeaderParserException extends MissingComponentException {
        public NoImageHeaderParserException() {
            super("Failed to find image header parser.");
        }
    }
}
