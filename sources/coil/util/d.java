package coil.util;

import coil.b;
import coil.decode.e;
import coil.fetch.g;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ComponentRegistries.kt */
public final class d {
    @NotNull
    public static final Object a(@NotNull b $this$mapData, @NotNull Object data) {
        k.e($this$mapData, "<this>");
        k.e(data, "data");
        Object mappedData = data;
        List $this$forEachIndices$iv = $this$mapData.d();
        int size = $this$forEachIndices$iv.size() - 1;
        if (size >= 0) {
            int i = 0;
            do {
                int i$iv = i;
                i++;
                n $dstr$mapper$type = $this$forEachIndices$iv.get(i$iv);
                coil.map.b mapper = (coil.map.b) $dstr$mapper$type.component1();
                if (((Class) $dstr$mapper$type.component2()).isAssignableFrom(mappedData.getClass()) && mapper.a(mappedData)) {
                    mappedData = mapper.map(mappedData);
                    continue;
                }
            } while (i <= size);
        }
        return mappedData;
    }

    @NotNull
    public static final <T> g<T> c(@NotNull b $this$requireFetcher, @NotNull T data) {
        n<g<? extends Object>, Class<? extends Object>> nVar;
        k.e($this$requireFetcher, "<this>");
        k.e(data, "data");
        List $this$findIndices$iv = $this$requireFetcher.b();
        int size = $this$findIndices$iv.size() - 1;
        if (size >= 0) {
            int i = 0;
            while (true) {
                int i$iv = i;
                boolean z = true;
                i++;
                nVar = $this$findIndices$iv.get(i$iv);
                n $dstr$fetcher$type = nVar;
                g fetcher = (g) $dstr$fetcher$type.component1();
                if (!((Class) $dstr$fetcher$type.component2()).isAssignableFrom(data.getClass()) || !fetcher.a(data)) {
                    z = false;
                }
                if (!z) {
                    if (i > size) {
                        break;
                    }
                } else {
                    break;
                }
            }
            nVar = null;
        } else {
            nVar = null;
        }
        n result = nVar;
        if (result != null) {
            return (g) result.getFirst();
        }
        throw new IllegalStateException(k.l("Unable to fetch data. No fetcher supports: ", data).toString());
    }

    @NotNull
    public static final <T> e b(@NotNull b $this$requireDecoder, @NotNull T data, @NotNull okio.e source, @Nullable String mimeType) {
        e it;
        k.e($this$requireDecoder, "<this>");
        k.e(data, "data");
        k.e(source, "source");
        List $this$findIndices$iv = $this$requireDecoder.a();
        int size = $this$findIndices$iv.size() - 1;
        if (size >= 0) {
            int i = 0;
            while (true) {
                int i$iv = i;
                i++;
                it = $this$findIndices$iv.get(i$iv);
                if (!it.b(source, mimeType)) {
                    if (i > size) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            it = null;
        }
        e decoder = it;
        if (decoder != null) {
            return decoder;
        }
        throw new IllegalStateException(k.l("Unable to decode data. No decoder supports: ", data).toString());
    }
}
