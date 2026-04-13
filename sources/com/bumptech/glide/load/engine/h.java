package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import com.bumptech.glide.load.resource.transcode.e;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DecodePath */
public class h<DataType, ResourceType, Transcode> {
    private final Class<DataType> a;
    private final List<? extends k<DataType, ResourceType>> b;
    private final e<ResourceType, Transcode> c;
    private final Pools.Pool<List<Throwable>> d;
    private final String e;

    /* compiled from: DecodePath */
    public interface a<ResourceType> {
        @NonNull
        t<ResourceType> a(@NonNull t<ResourceType> tVar);
    }

    public h(Class<DataType> dataClass, Class<ResourceType> resourceClass, Class<Transcode> transcodeClass, List<? extends k<DataType, ResourceType>> decoders, e<ResourceType, Transcode> transcoder, Pools.Pool<List<Throwable>> listPool) {
        this.a = dataClass;
        this.b = decoders;
        this.c = transcoder;
        this.d = listPool;
        this.e = "Failed DecodePath{" + dataClass.getSimpleName() + "->" + resourceClass.getSimpleName() + "->" + transcodeClass.getSimpleName() + "}";
    }

    public t<Transcode> a(com.bumptech.glide.load.data.e<DataType> rewinder, int width, int height, @NonNull i options, a<ResourceType> callback) {
        return this.c.a(callback.a(b(rewinder, width, height, options)), options);
    }

    @NonNull
    private t<ResourceType> b(com.bumptech.glide.load.data.e<DataType> rewinder, int width, int height, @NonNull i options) {
        List<Throwable> exceptions = (List) com.bumptech.glide.util.i.d(this.d.acquire());
        try {
            return c(rewinder, width, height, options, exceptions);
        } finally {
            this.d.release(exceptions);
        }
    }

    @NonNull
    private t<ResourceType> c(com.bumptech.glide.load.data.e<DataType> rewinder, int width, int height, @NonNull i options, List<Throwable> exceptions) {
        Resource<ResourceType> result = null;
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            ResourceDecoder<DataType, ResourceType> decoder = (k) this.b.get(i);
            try {
                if (decoder.a(rewinder.a(), options)) {
                    result = decoder.b(rewinder.a(), width, height, options);
                }
            } catch (IOException | OutOfMemoryError | RuntimeException e2) {
                if (Log.isLoggable("DecodePath", 2)) {
                    Log.v("DecodePath", "Failed to decode data for " + decoder, e2);
                }
                exceptions.add(e2);
            }
            if (result != null) {
                break;
            }
        }
        if (result != null) {
            return result;
        }
        throw new GlideException(this.e, (List<Throwable>) new ArrayList(exceptions));
    }

    public String toString() {
        return "DecodePath{ dataClass=" + this.a + ", decoders=" + this.b + ", transcoder=" + this.c + '}';
    }
}
